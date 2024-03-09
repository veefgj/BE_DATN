package SD94.service.impl;

import SD94.dto.HoaDonChiTietDTO;
import SD94.dto.HoaDonDTO;
import SD94.dto.KhachHangDTO;
import SD94.dto.SanPhamDTO;
import SD94.entity.gioHang.GioHang;
import SD94.entity.gioHang.GioHangChiTiet;
import SD94.entity.hoaDon.HoaDon;
import SD94.entity.hoaDon.HoaDonChiTiet;
import SD94.entity.hoaDon.TrangThai;
import SD94.entity.khachHang.KhachHang;
import SD94.entity.khuyenMai.KhuyenMai;
import SD94.entity.nhanVien.NhanVien;
import SD94.entity.sanPham.KichCo;
import SD94.entity.sanPham.MauSac;
import SD94.entity.sanPham.SanPham;
import SD94.entity.sanPham.SanPhamChiTiet;
import SD94.repository.gioHang.GioHangChiTietRepository;
import SD94.repository.gioHang.GioHangRepository;
import SD94.repository.hoaDon.HoaDonChiTietRepository;
import SD94.repository.hoaDon.HoaDonRepository;
import SD94.repository.hoaDon.TrangThaiRepository;
import SD94.repository.khachHang.KhachHangRepository;
import SD94.repository.khuyenMai.KhuyenMaiRepository;
import SD94.repository.nhanVien.NhanVienRepository;
import SD94.repository.sanPham.KichCoRepository;
import SD94.repository.sanPham.MauSacRepository;
import SD94.repository.sanPham.SanPhamChiTietRepository;
import SD94.repository.sanPham.SanPhamRepository;
import SD94.service.service.BanHangTaiQuayService;
import SD94.service.service.InHoaDonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BanHangTaiQuayServiceImpl implements BanHangTaiQuayService {
    @Autowired
    MauSacRepository mauSacRepository;

    @Autowired
    SanPhamChiTietRepository sanPhamChiTietRepository;

    @Autowired
    NhanVienRepository nhanVienRepository;

    @Autowired
    TrangThaiRepository trangThaiRepository;

    @Autowired
    KichCoRepository kichCoRepository;

    @Autowired
    SanPhamRepository sanPhamRepository;

    @Autowired
    HoaDonRepository hoaDonRepository;

    @Autowired
    HoaDonChiTietRepository hoaDonChiTietRepository;

    @Autowired
    KhachHangRepository khachHangRepository;

    @Autowired
    KhuyenMaiRepository khuyenMaiRepository;

    @Autowired
    GioHangRepository gioHangRepository;

    @Autowired
    InHoaDonService inHoaDonService;

    @Autowired
    GioHangChiTietRepository gioHangChiTietRepository;

    @Override
    public ResponseEntity<?> taoHoaDon(HoaDonDTO hoaDonDTO) {
        List<HoaDon> hoaDonList = hoaDonRepository.getDanhSachHoaDonCho();
        int check = hoaDonList.size();
        if (check < 8) {
            NhanVien nhanVien = nhanVienRepository.findByEmail(hoaDonDTO.getEmail_user());
            TrangThai trangThai = trangThaiRepository.findByID(6L);
            HoaDon hoaDon = new HoaDon();
            hoaDon.setTrangThai(trangThai);
            hoaDon.setLoaiHoaDon(1);
            hoaDon.setCreatedby(nhanVien.getHoTen());
            hoaDon.setCreatedDate(new Date());
            hoaDonRepository.save(hoaDon);

            hoaDon.setMaHoaDon("HD" + hoaDon.getId());
            hoaDonRepository.save(hoaDon);
            return ResponseEntity.ok(hoaDon.getId());

        } else {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Số lượng hóa đơn chờ đã đạt giới hạn");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @Override
    public ResponseEntity<?> themSanPham(SanPhamDTO dto) {
        Map<String, String> respone = new HashMap<>();
        MauSac mauSac = mauSacRepository.findByMaMauSac(dto.getMaMauSac());
        KichCo kichCo = kichCoRepository.findByKichCo(dto.getKichCoDaChon());
        SanPhamChiTiet sanPhamChiTiet = sanPhamChiTietRepository.getSanPhamChiTiet(mauSac.getId(), kichCo.getId(), dto.getSan_pham_id());
        SanPham sanPham = sanPhamRepository.findByID(dto.getSan_pham_id());
        HoaDon hoaDon = hoaDonRepository.findByID(dto.getId_hoaDon());
        Optional<HoaDonChiTiet> optionalHDCT = hoaDonChiTietRepository.checkHDCT(hoaDon.getId(), sanPhamChiTiet.getId());

        int soLuongBanDau = sanPhamChiTiet.getSoLuong();
        int soLuongThem = dto.getSoLuong();
        if (soLuongThem > soLuongBanDau) {
            respone.put("err", "Số lượng nhập vào lớn hơn số lượng hiện có");
            return ResponseEntity.badRequest().body(respone);
        } else {
            if (optionalHDCT.isPresent()) {
                HoaDonChiTiet hoaDonChiTiet = optionalHDCT.get();
                int soLuongDuocThemTiep = sanPhamChiTiet.getSoLuong() - hoaDonChiTiet.getSoLuong();
                int check = soLuongDuocThemTiep - dto.getSoLuong();
                if (hoaDonChiTiet.getSoLuong() == sanPhamChiTiet.getSoLuong()) {
                    respone.put("err", "Bạn đã có " + sanPhamChiTiet.getSoLuong() + " sản phẩm này trong giỏ hàng, bạn không thể thêm tiếp vì vượt quá số lượng của sản phẩm");
                    return ResponseEntity.badRequest().body(respone);
                } else if (check < 0) {
                    respone.put("err", "Bạn đã có " + hoaDonChiTiet.getSoLuong() + " sản phẩm này trong hóa đơn, bạn chỉ có thể thêm tiếp được tối đa " + soLuongDuocThemTiep + " sản phẩm này");
                    return ResponseEntity.badRequest().body(respone);
                } else {
                    int soLuongMoi = hoaDonChiTiet.getSoLuong() + dto.getSoLuong();
                    int thanhTienMoi = (int) (sanPham.getGia() * soLuongMoi);
                    hoaDonChiTiet.setSoLuong(soLuongMoi);
                    hoaDonChiTiet.setThanhTien(thanhTienMoi);
                    hoaDonChiTietRepository.save(hoaDonChiTiet);
                }
            } else {
                int thanhTien = (int) (dto.getSoLuong() * sanPham.getGia());
                HoaDonChiTiet hoaDonChiTiet = new HoaDonChiTiet();
                hoaDonChiTiet.setHoaDon(hoaDon);
                hoaDonChiTiet.setSanPhamChiTiet(sanPhamChiTiet);
                hoaDonChiTiet.setDonGia(dto.getDonGia());
                hoaDonChiTiet.setSoLuong(dto.getSoLuong());
                hoaDonChiTiet.setThanhTien(thanhTien);
                hoaDonChiTietRepository.save(hoaDonChiTiet);
            }

            List<HoaDonChiTiet> hoaDonChiTiets = hoaDonChiTietRepository.findByIDBill(dto.getId_hoaDon());

            int totalAmount = 0;
            for (HoaDonChiTiet hdct : hoaDonChiTiets) {
                totalAmount += hdct.getThanhTien();
            }

            hoaDon.setTongTienHoaDon(totalAmount);
            hoaDon.setTongTienDonHang(totalAmount);
            hoaDonRepository.save(hoaDon);

            return ResponseEntity.ok(HttpStatus.OK);
        }
    }

    @Override
    public List<HoaDon> xoaHoaDon(HoaDonDTO hoaDonDTO) {
        HoaDon hoaDon = hoaDonRepository.findByID(hoaDonDTO.getId());
        TrangThai trangThai = trangThaiRepository.findByID(8L);
        hoaDon.setTrangThai(trangThai);
        hoaDonRepository.save(hoaDon);

        List<HoaDonChiTiet> hoaDonChiTiets = hoaDonChiTietRepository.findByIDBill(hoaDon.getId());
        for (HoaDonChiTiet hoaDonChiTiet : hoaDonChiTiets) {
            SanPhamChiTiet sanPhamChiTiet = hoaDonChiTiet.getSanPhamChiTiet();
            sanPhamChiTiet.setSoLuong(hoaDonChiTiet.getSoLuong() + sanPhamChiTiet.getSoLuong());
            sanPhamChiTietRepository.save(sanPhamChiTiet);
        }

        List<HoaDon> hoaDon2 = hoaDonRepository.getDanhSachHoaDonCho();
        return hoaDon2;
    }

    @Override
    public ResponseEntity<?> themMaGiamGiaTaiQuay(HoaDonDTO hoaDonDTO) {
        Map<String, String> response = new HashMap<>();
        KhuyenMai khuyenMai = khuyenMaiRepository.findByNameKM(hoaDonDTO.getTenMaGiamGia());
        if (khuyenMai == null) {
            response.put("mess", "Khuyến mại không tồn tại");
            return ResponseEntity.badRequest().body(response);
        } else if (khuyenMai.getTrangThai() == 1 || khuyenMai.getTrangThai() == 2) {
            response.put("mess", "Khuyến mãi đã hết hạn hoặc chưa bắt đầu");
            return ResponseEntity.badRequest().body(response);
        } else {
            HoaDon hoaDon = hoaDonRepository.findByID(hoaDonDTO.getId());
            int phanTramGiam = khuyenMai.getPhanTramGiam();
            int tienGiamToiDa = khuyenMai.getTienGiamToiDa();
            int tongTienBill = hoaDon.getTongTienHoaDon();

            int tongTienSauGiamCheck = (tongTienBill * phanTramGiam) / 100;
            if (tongTienSauGiamCheck > tienGiamToiDa) {
                int tongTienSauGiam = hoaDon.getTongTienHoaDon() - khuyenMai.getTienGiamToiDa();
                hoaDon.setTienGiam(hoaDon.getTongTienHoaDon() - tongTienSauGiam);
                hoaDon.setTongTienDonHang(tongTienSauGiam);
                hoaDon.setKhuyenMai(khuyenMai);
                hoaDonRepository.save(hoaDon);
            } else {
                int tongTien = hoaDon.getTongTienHoaDon() - tongTienSauGiamCheck;
                hoaDon.setTongTienDonHang(tongTien);
                hoaDon.setTienGiam(tongTienSauGiamCheck);
                hoaDon.setKhuyenMai(khuyenMai);
                hoaDonRepository.save(hoaDon);
            }
            HoaDon hoaDon2 = hoaDonRepository.findByID(hoaDonDTO.getId());
            return ResponseEntity.ok().body(hoaDon2);
        }
    }

    @Override
    public ResponseEntity<?> addKhachHang(KhachHangDTO dto) {
        HoaDon hoaDon = hoaDonRepository.findByID(dto.getId_hoaDon());
        if (dto.getId() != null) {
            KhachHang khachHang = khachHangRepository.findByID(dto.getId());
            hoaDon.setKhachHang(khachHang);
            hoaDon.setNguoiNhan(khachHang.getHoTen());
            hoaDon.setSDTNguoiNhan(khachHang.getSoDienThoai());
            hoaDonRepository.save(hoaDon);
        } else {
            KhachHang khachHangCheck = khachHangRepository.findBySDT(dto.getSoDienThoai());
            if (khachHangCheck != null) {
                Map<String, String> response = new HashMap<>();
                response.put("message", "Số điện thoại này đã tồn tại");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            } else {
                KhachHang kh = new KhachHang();
                kh.setHoTen(dto.getHoTen());
                kh.setSoDienThoai(dto.getSoDienThoai());
                khachHangRepository.save(kh);

                GioHang gioHang = new GioHang();
                gioHang.setKhachHang(kh);
                gioHangRepository.save(gioHang);

                hoaDon.setKhachHang(kh);
                hoaDon.setNguoiNhan(dto.getHoTen());
                hoaDon.setSDTNguoiNhan(dto.getSoDienThoai());
                hoaDonRepository.save(hoaDon);
            }
        }

        HoaDon hoaDonReturn = hoaDonRepository.findByID(dto.getId_hoaDon());
        return ResponseEntity.ok(hoaDonReturn);
    }

    @Override
    public ResponseEntity<?> huyDon(HoaDonDTO hoaDonDTO) {
        HoaDon hoaDon = hoaDonRepository.findByID(hoaDonDTO.getId());
        TrangThai trangThai = trangThaiRepository.findByID(8L);
        hoaDon.setTrangThai(trangThai);
        hoaDonRepository.save(hoaDon);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Hủy đơn thành công");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @SuppressWarnings("null")
    @Override
    public ResponseEntity<?> thanhToan(HoaDonDTO hoaDonDTO) {
        HoaDon hoaDon = hoaDonRepository.findByID(hoaDonDTO.getId());
        List<HoaDonChiTiet> hoaDonChiTiets = hoaDonChiTietRepository.findByIDBill(hoaDonDTO.getId());
        Map<String, String> response = new HashMap<>();

        if (hoaDonChiTiets.isEmpty() || hoaDonChiTiets.size() == 0) {
            response.put("err", "Hóa đơn chưa có sản phẩm");
            return ResponseEntity.badRequest().body(response);
        } else {
            for (HoaDonChiTiet hoaDonChiTiet : hoaDonChiTiets) {
                SanPhamChiTiet sanPhamChiTiet = hoaDonChiTiet.getSanPhamChiTiet();
                int soLuongBanDau = sanPhamChiTiet.getSoLuong();
                int soLuongHoaDon = hoaDonChiTiet.getSoLuong();
                int soLuongCapNhat = soLuongBanDau - soLuongHoaDon;
                if (soLuongCapNhat < 0) {
                    response.put("err", "Không thể thanh toán, số lượng sản phẩm " + sanPhamChiTiet.getSanPham().getTenSanPham() + " vượt quá số lượng hiện có");
                    return ResponseEntity.badRequest().body(response);
                } else {
                    sanPhamChiTiet.setSoLuong(soLuongBanDau - soLuongHoaDon);
                    sanPhamChiTietRepository.save(sanPhamChiTiet);
                }

                if (sanPhamChiTiet.getSoLuong() <= 0) {
                    sanPhamChiTiet.setTrangThai(false);
                    sanPhamChiTietRepository.save(sanPhamChiTiet);

                    List<HoaDonChiTiet> hdct = hoaDonChiTietRepository.findBySPCTID(sanPhamChiTiet.getId());
                    for (HoaDonChiTiet ListHDCT : hdct) {
                        hoaDonChiTietRepository.deleteById(ListHDCT.getId());
                    }

                    List<GioHangChiTiet> gioHangChiTiets = gioHangChiTietRepository.findCartBySPCTID(sanPhamChiTiet.getId());
                    for (GioHangChiTiet gioHangChiTiet : gioHangChiTiets) {
                        gioHangChiTietRepository.deleteById(gioHangChiTiet.getId());
                    }
                }
            }

            TrangThai trangThai = trangThaiRepository.findByID(7L);
            NhanVien nhanVien = nhanVienRepository.findByEmail(hoaDonDTO.getEmail_user());
            hoaDon.setTrangThai(trangThai);
            hoaDon.setNhanVien(nhanVien);
            hoaDonRepository.save(hoaDon);
            response.put("message", "Thanh toán thành công");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    @SuppressWarnings("null")
    @Override
    public ResponseEntity<?> xoaHDCT(HoaDonChiTietDTO dto) {
        Optional<HoaDonChiTiet> optionalHoaDonChiTiet = hoaDonChiTietRepository.findById(dto.getId());

        if (optionalHoaDonChiTiet.isPresent()) {
            HoaDonChiTiet hoaDonChiTiet = optionalHoaDonChiTiet.get();
            hoaDonChiTiet.setDeleted(true);
            hoaDonChiTietRepository.save(hoaDonChiTiet);

            HoaDon hoaDon = hoaDonChiTiet.getHoaDon();
            int tongTienHoaDon = 0;
            List<HoaDonChiTiet> hoaDonChiTiets = hoaDonChiTietRepository.findByIDBill(hoaDon.getId());
            for (HoaDonChiTiet hoaDonChiTiet1 : hoaDonChiTiets) {
                tongTienHoaDon += hoaDonChiTiet1.getThanhTien();
            }

            hoaDon.setTongTienHoaDon(tongTienHoaDon);
            hoaDon.setTongTienDonHang(tongTienHoaDon);
            hoaDonRepository.save(hoaDon);

            return ResponseEntity.ok().body(hoaDon);

        }
        return ResponseEntity.badRequest().build();
    }
}
