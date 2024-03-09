package SD94.controller.vnpay;

import SD94.config.VnpayConflig;
import SD94.dto.HoaDonDTO;
import SD94.entity.gioHang.GioHang;
import SD94.entity.gioHang.GioHangChiTiet;
import SD94.entity.hoaDon.HoaDon;
import SD94.entity.hoaDon.HoaDonChiTiet;
import SD94.entity.hoaDon.LSHoaDon;
import SD94.entity.hoaDon.TrangThai;
import SD94.entity.khachHang.KhachHang;
import SD94.entity.sanPham.SanPhamChiTiet;
import SD94.repository.gioHang.GioHangChiTietRepository;
import SD94.repository.gioHang.GioHangRepository;
import SD94.repository.hoaDon.HoaDonChiTietRepository;
import SD94.repository.hoaDon.HoaDonRepository;
import SD94.repository.hoaDon.LSHoaDonRepository;
import SD94.repository.hoaDon.TrangThaiRepository;
import SD94.repository.khachHang.KhachHangRepository;
import SD94.repository.sanPham.SanPhamChiTietRepository;
import SD94.service.service.HoaDonDatHangService;
import SD94.service.service.MailService;
import SD94.service.service.VnpayService;
import SD94.validator.ThanhToanValidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.Date;
import java.util.List;

@RestController
public class VnpayController {
    @Autowired
    VnpayService vnpayService;

    @Autowired
    HoaDonRepository hoaDonRepository;

    @Autowired
    HoaDonChiTietRepository hoaDonChiTietRepository;

    @Autowired
    KhachHangRepository khachHangRepository;

    @Autowired
    GioHangRepository gioHangRepository;

    @Autowired
    GioHangChiTietRepository gioHangChiTietRepository;

    @Autowired
    HoaDonDatHangService hoaDonDatHangService;

    @Autowired
    TrangThaiRepository trangThaiRepository;

    @Autowired
    SanPhamChiTietRepository sanPhamChiTietRepository;

    @Autowired
    MailService mailService;

    @Autowired
    LSHoaDonRepository lsHoaDonRepository;

    HoaDonDTO dto = null;

    @PostMapping("/payment/create")
    public ResponseEntity<?> createUrl(@RequestBody HoaDonDTO hoaDonDTO) {
        ResponseEntity<?> response = ThanhToanValidate.thanhtoan(hoaDonDTO);
        if (!response.getStatusCode().is2xxSuccessful()) {
            return response;
        } else {
            dto = hoaDonDTO;
            return vnpayService.createPayment(hoaDonDTO);
        }
    }

    @PostMapping("/payment/MuaNgay/create")
    public ResponseEntity<?> MuaNgaycreateUrl(@RequestBody HoaDonDTO hoaDonDTO) {
        ResponseEntity<?> response = ThanhToanValidate.thanhtoan(hoaDonDTO);
        if (!response.getStatusCode().is2xxSuccessful()) {
            return response;
        } else {
            dto = hoaDonDTO;
            return vnpayService.createPaymentMuaNgay(hoaDonDTO);
        }
    }

    @SuppressWarnings("null")
    @Transactional
    @RequestMapping("/payment/return")
    public ResponseEntity<String> returnPayment(HttpServletRequest request) {
        int paymentStatus = VnpayConflig.orderReturn(request);
        HoaDon hoaDon = hoaDonRepository.findByID(dto.getId());
        KhachHang khachHang = khachHangRepository.findByEmail(dto.getEmail_user());
        GioHang gioHang = gioHangRepository.findbyCustomerID(khachHang.getId());
        List<GioHangChiTiet> gioHangChiTiets = gioHangChiTietRepository.findByCartID(gioHang.getId());
        List<HoaDonChiTiet> hoaDonChiTiets = hoaDonChiTietRepository.findByIDBill(hoaDon.getId());
        for (HoaDonChiTiet hoaDonChiTiet : hoaDonChiTiets) {
            gioHangChiTietRepository.deleteGioHangChiTiet(hoaDonChiTiet.getSanPhamChiTiet().getId());
        }

        for (GioHangChiTiet gioHangChiTiet : gioHangChiTiets) {
            SanPhamChiTiet sanPhamChiTiet = sanPhamChiTietRepository.findByID(gioHangChiTiet.getSanPhamChiTiet().getId());
            sanPhamChiTiet.setSoLuong(sanPhamChiTiet.getSoLuong() - gioHangChiTiet.getSoLuong());
            if (sanPhamChiTiet.getSoLuong() <= 0) {
                sanPhamChiTiet.setTrangThai(false);

                List<HoaDonChiTiet> hdct = hoaDonChiTietRepository.findBySPCTID(sanPhamChiTiet.getId());
                for (HoaDonChiTiet ListHDCT : hdct) {
                    hoaDonChiTietRepository.deleteById(ListHDCT.getId());
                }

                List<GioHangChiTiet> ghct = gioHangChiTietRepository.findCartBySPCTID(sanPhamChiTiet.getId());
                for (GioHangChiTiet gioHangChiTiet1 : ghct) {
                    gioHangChiTietRepository.deleteById(gioHangChiTiet1.getId());
                }

            } else {
                sanPhamChiTiet.setTrangThai(true);
            }
        }

        TrangThai trangThai = trangThaiRepository.findByID(1L);

        hoaDon.setGhiChu(dto.getGhiChu());
        hoaDon.setTongTienHoaDon(dto.getTongTienHoaDon());
        hoaDon.setTongTienDonHang(dto.getTongTienDonHang());
        hoaDon.setEmailNguoiNhan(dto.getEmail());
        hoaDon.setSDTNguoiNhan(dto.getSoDienThoai());
        hoaDon.setTienShip(dto.getTienShip());
        hoaDon.setDiaChiGiaoHang(dto.getDiaChi());
        hoaDon.setTrangThai(trangThai);
        hoaDon.setKhachHang(khachHang);
        hoaDon.setNguoiNhan(khachHang.getHoTen());
        hoaDon.setLoaiHoaDon(2);
        hoaDonRepository.save(hoaDon);

        hoaDonDatHangService.createTimeLine("Tạo đơn hàng", 1L, hoaDon.getId(), khachHang.getHoTen());

        //Lưu lịch sử hóa đơn
        LSHoaDon ls = new LSHoaDon();
        ls.setNguoiThaoTac(khachHang.getHoTen());
        ls.setNgayTao(new Date());
        ls.setHoaDon(hoaDon);
        ls.setThaoTac("Đã thanh toán qua VNPay");
        lsHoaDonRepository.save(ls);

        try {
            mailService.sendOrderConfirmationEmail(hoaDon.getEmailNguoiNhan(), hoaDon);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        if (paymentStatus == 1) {
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(URI.create("http://127.0.0.1:5501/templates/banHang/online/vnpay/Success.html"));
            return new ResponseEntity<>(headers, HttpStatus.FOUND);
        } else {
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(URI.create("http://127.0.0.1:5501/templates/banHang/online/vnpay/Error.html"));
            return new ResponseEntity<>(headers, HttpStatus.FOUND);
        }
    }

    @SuppressWarnings("null")
    @Transactional
    @RequestMapping("/payment/MuaNgay/return")
    public ResponseEntity<String> returnPaymentMuaNgay(HttpServletRequest request) {
        int paymentStatus = VnpayConflig.orderReturn(request);
        HoaDon hoaDon = hoaDonRepository.findByID(dto.getId());
        List<HoaDonChiTiet> hoaDonChiTiets = hoaDonChiTietRepository.findByIDBill(hoaDon.getId());
        for (HoaDonChiTiet hoaDonChiTiet : hoaDonChiTiets) {
            SanPhamChiTiet sanPhamChiTiet = sanPhamChiTietRepository.findByID(hoaDonChiTiet.getSanPhamChiTiet().getId());
            sanPhamChiTiet.setSoLuong(sanPhamChiTiet.getSoLuong() - hoaDonChiTiet.getSoLuong());
            if (sanPhamChiTiet.getSoLuong() <= 0) {
                sanPhamChiTiet.setTrangThai(false);

                List<HoaDonChiTiet> hdct = hoaDonChiTietRepository.findBySPCTID(sanPhamChiTiet.getId());
                for (HoaDonChiTiet ListHDCT : hdct) {
                    hoaDonChiTietRepository.deleteById(ListHDCT.getId());
                }

                List<GioHangChiTiet> ghct = gioHangChiTietRepository.findCartBySPCTID(sanPhamChiTiet.getId());
                for (GioHangChiTiet gioHangChiTiet1 : ghct) {
                    gioHangChiTietRepository.deleteById(gioHangChiTiet1.getId());
                }

            } else {
                sanPhamChiTiet.setTrangThai(true);
            }
            sanPhamChiTietRepository.save(sanPhamChiTiet);
        }

        TrangThai trangThai = trangThaiRepository.findByID(1L);

        hoaDon.setGhiChu(dto.getGhiChu());
        hoaDon.setTongTienHoaDon(dto.getTongTienHoaDon());
        hoaDon.setTongTienDonHang(dto.getTongTienDonHang());
        hoaDon.setEmailNguoiNhan(dto.getEmail());
        hoaDon.setSDTNguoiNhan(dto.getSoDienThoai());
        hoaDon.setTienShip(dto.getTienShip());
        hoaDon.setDiaChiGiaoHang(dto.getDiaChi());
        hoaDon.setTrangThai(trangThai);
        hoaDon.setCreatedby(dto.getNguoiTao());
        hoaDon.setNguoiNhan(dto.getNguoiTao());
        hoaDon.setLoaiHoaDon(2);
        hoaDonRepository.save(hoaDon);

        hoaDonDatHangService.createTimeLine("Tạo đơn hàng", 1L, hoaDon.getId(), dto.getNguoiTao());

        //Lưu lịch sử hóa đơn
        LSHoaDon ls = new LSHoaDon();
        ls.setNguoiThaoTac(hoaDon.getNguoiNhan());
        ls.setHoaDon(hoaDon);
        ls.setNgayTao(new Date());
        ls.setThaoTac("Đã thanh toán qua VNPay");
        lsHoaDonRepository.save(ls);

        try {
            mailService.sendOrderConfirmationEmail(hoaDon.getEmailNguoiNhan(), hoaDon);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        if (paymentStatus == 1) {
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(URI.create("http://127.0.0.1:5501/templates/banHang/online/vnpay/Success.html"));
            return new ResponseEntity<>(headers, HttpStatus.FOUND);
        } else {
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(URI.create("http://127.0.0.1:5501/templates/banHang/online/vnpay/Error.html"));
            return new ResponseEntity<>(headers, HttpStatus.FOUND);
        }
    }
}
