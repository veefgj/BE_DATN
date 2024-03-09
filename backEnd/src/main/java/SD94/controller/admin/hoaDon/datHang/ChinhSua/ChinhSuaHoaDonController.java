package SD94.controller.admin.hoaDon.datHang.ChinhSua;

import SD94.dto.HoaDonChiTietDTO;
import SD94.dto.SanPhamDTO;
import SD94.entity.gioHang.GioHangChiTiet;
import SD94.entity.hoaDon.HoaDon;
import SD94.entity.hoaDon.HoaDonChiTiet;
import SD94.entity.hoaDon.LSHoaDon;
import SD94.entity.nhanVien.NhanVien;
import SD94.entity.sanPham.KichCo;
import SD94.entity.sanPham.MauSac;
import SD94.entity.sanPham.SanPham;
import SD94.entity.sanPham.SanPhamChiTiet;
import SD94.repository.gioHang.GioHangChiTietRepository;
import SD94.repository.hoaDon.HoaDonChiTietRepository;
import SD94.repository.hoaDon.HoaDonRepository;
import SD94.repository.hoaDon.LSHoaDonRepository;
import SD94.repository.hoaDon.LichSuHoaDonRepository;
import SD94.repository.nhanVien.NhanVienRepository;
import SD94.repository.sanPham.KichCoRepository;
import SD94.repository.sanPham.MauSacRepository;
import SD94.repository.sanPham.SanPhamChiTietRepository;
import SD94.repository.sanPham.SanPhamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/hoaDon/ChinhSua")
public class ChinhSuaHoaDonController {
    @Autowired
    HoaDonRepository hoaDonRepository;

    @Autowired
    MauSacRepository mauSacRepository;

    @Autowired
    KichCoRepository kichCoRepository;

    @Autowired
    SanPhamChiTietRepository sanPhamChiTietRepository;

    @Autowired
    SanPhamRepository sanPhamRepository;

    @Autowired
    HoaDonChiTietRepository hoaDonChiTietRepository;

    @Autowired
    GioHangChiTietRepository gioHangChiTietRepository;

    @Autowired
    LichSuHoaDonRepository lichSuHoaDonRepository;

    @Autowired
    NhanVienRepository nhanVienRepository;

    @Autowired
    LSHoaDonRepository lsHoaDonRepository;

    @PostMapping("/themSanPham")
    public ResponseEntity<?> themSanPham(@RequestBody SanPhamDTO dto) {
        Map<String, String> respone = new HashMap<>();
        MauSac mauSac = mauSacRepository.findByMaMauSac(dto.getMaMauSac());
        KichCo kichCo = kichCoRepository.findByKichCo(dto.getKichCoDaChon());
        SanPhamChiTiet sanPhamChiTiet = sanPhamChiTietRepository.getSanPhamChiTiet(mauSac.getId(), kichCo.getId(),
                dto.getSan_pham_id());
        SanPham sanPham = sanPhamRepository.findByID(dto.getSan_pham_id());
        HoaDon hoaDon = hoaDonRepository.findByID(dto.getId_hoaDon());
        Optional<HoaDonChiTiet> optionalHDCT = hoaDonChiTietRepository.checkHDCT(hoaDon.getId(),
                sanPhamChiTiet.getId());

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
                    respone.put("err", "Bạn đã có " + sanPhamChiTiet.getSoLuong()
                            + " sản phẩm này trong giỏ hàng, bạn không thể thêm tiếp vì vượt quá số lượng của sản phẩm");
                    return ResponseEntity.badRequest().body(respone);
                } else if (check < 0) {
                    respone.put("err",
                            "Bạn đã có " + hoaDonChiTiet.getSoLuong()
                                    + " sản phẩm này trong hóa đơn, bạn chỉ có thể thêm tiếp được tối đa "
                                    + soLuongDuocThemTiep + " sản phẩm này");
                    return ResponseEntity.badRequest().body(respone);
                } else {
                    int soLuongMoi = hoaDonChiTiet.getSoLuong() + dto.getSoLuong();
                    int thanhTienMoi = (int) (sanPham.getGia() * soLuongMoi);
                    int thanhTienThem = thanhTienMoi - hoaDonChiTiet.getThanhTien();
                    hoaDonChiTiet.setSoLuong(soLuongMoi);
                    hoaDonChiTiet.setThanhTien(thanhTienMoi);
                    hoaDonChiTietRepository.save(hoaDonChiTiet);

                    List<HoaDonChiTiet> hoaDonChiTiets = hoaDonChiTietRepository.findByIDBill(dto.getId_hoaDon());

                    int totalAmount = 0;
                    int total = hoaDon.getTongTienDonHang();
                    for (HoaDonChiTiet hdct : hoaDonChiTiets) {
                        totalAmount += hdct.getThanhTien();
                    }
                    hoaDon.setTongTienHoaDon(totalAmount);
                    hoaDon.setTongTienDonHang(total + thanhTienThem);
                    hoaDonRepository.save(hoaDon);

                    sanPhamChiTiet.setSoLuong(soLuongBanDau - dto.getSoLuong());
                    sanPhamChiTietRepository.save(sanPhamChiTiet);

                    NhanVien nhanVien = nhanVienRepository.findByEmail(dto.getEmail_user());

                    // Lưu lịch sử hóa đơn
                    LSHoaDon lichSuHoaDon = new LSHoaDon();
                    lichSuHoaDon.setNguoiThaoTac(nhanVien.getHoTen());
                    lichSuHoaDon.setHoaDon(hoaDon);
                    lichSuHoaDon.setThaoTac("Thêm mới " + dto.getSoLuong() + " sản phẩm "
                            + sanPhamChiTiet.getSanPham().getTenSanPham() + " vào hóa đơn");
                    lsHoaDonRepository.save(lichSuHoaDon);
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

                List<HoaDonChiTiet> hoaDonChiTiets = hoaDonChiTietRepository.findByIDBill(dto.getId_hoaDon());

                int totalAmount = 0;
                int total = hoaDon.getTongTienDonHang();
                for (HoaDonChiTiet hdct : hoaDonChiTiets) {
                    totalAmount += hdct.getThanhTien();
                }
                hoaDon.setTongTienHoaDon(totalAmount);

                hoaDon.setTongTienDonHang(total + thanhTien);
                hoaDonRepository.save(hoaDon);

                sanPhamChiTiet.setSoLuong(soLuongBanDau - dto.getSoLuong());
                sanPhamChiTietRepository.save(sanPhamChiTiet);

                NhanVien nhanVien = nhanVienRepository.findByEmail(dto.getEmail_user());

                // Lưu lịch sử hóa đơn
                LSHoaDon lichSuHoaDon = new LSHoaDon();
                lichSuHoaDon.setNguoiThaoTac(nhanVien.getHoTen());
                lichSuHoaDon.setNgayTao(new Date());
                lichSuHoaDon.setHoaDon(hoaDon);
                lichSuHoaDon.setThaoTac("Thêm mới " + dto.getSoLuong() + " sản phẩm "
                        + sanPhamChiTiet.getSanPham().getTenSanPham() + " vào hóa đơn");
                lsHoaDonRepository.save(lichSuHoaDon);
            }

            return ResponseEntity.ok(HttpStatus.OK);
        }
    }

    @SuppressWarnings("null")
    @PostMapping("/update-soluong")
    public ResponseEntity<?> updateSL(@RequestBody HoaDonChiTietDTO dto) {
        Map<String, String> respone = new HashMap<>();
        HoaDonChiTiet hoaDonChiTiet = hoaDonChiTietRepository.findById(dto.getId()).get();

        SanPhamChiTiet sanPhamChiTiet = dto.getSanPhamChiTiet();
        int tongSoLuong = sanPhamChiTiet.getSoLuong() + hoaDonChiTiet.getSoLuong();
        int soLuongUpdate = dto.getSoLuongcapNhat();
        int soLuongDuocThemTiep = (sanPhamChiTiet.getSoLuong() + hoaDonChiTiet.getSoLuong())
                - hoaDonChiTiet.getSoLuong();
        int thanhTienUpdate = (int) (soLuongUpdate * sanPhamChiTiet.getSanPham().getGia());
        int thanhTienMoiThem = thanhTienUpdate - hoaDonChiTiet.getThanhTien();

        if (tongSoLuong < soLuongUpdate) {
            respone.put("err", "Bạn đã có " + hoaDonChiTiet.getSoLuong()
                    + " sản phẩm này trong giỏ hàng, bạn không thể thêm tiếp vì vượt quá số lượng của sản phẩm");
            return ResponseEntity.badRequest().body(respone);
        } else if (sanPhamChiTiet.isTrangThai() == false) {
            respone.put("err", "Sản phẩm đã ngừng kinh doanh ");
            return ResponseEntity.badRequest().body(respone);
        } else if (soLuongUpdate > tongSoLuong && soLuongUpdate > soLuongDuocThemTiep) {
            respone.put("err",
                    "Bạn đã có " + hoaDonChiTiet.getSoLuong()
                            + " sản phẩm này trong giỏ hàng, bạn chỉ có thể thêm tiếp được tối đa "
                            + soLuongDuocThemTiep + " sản phẩm này");
            return ResponseEntity.badRequest().body(respone);
        } else {

            int soLuongBanDau = hoaDonChiTiet.getSoLuong();
            hoaDonChiTiet.setSoLuong(soLuongUpdate);
            hoaDonChiTiet.setThanhTien(thanhTienUpdate);
            hoaDonChiTietRepository.save(hoaDonChiTiet);

            List<HoaDonChiTiet> hoaDonChiTiets = hoaDonChiTietRepository
                    .findByIDBill(hoaDonChiTiet.getHoaDon().getId());
            HoaDon hoaDon = hoaDonChiTiet.getHoaDon();
            int totalAmount = 0;
            int total = hoaDon.getTongTienDonHang();
            for (HoaDonChiTiet hdct : hoaDonChiTiets) {
                totalAmount += hdct.getThanhTien();
            }
            hoaDon.setTongTienHoaDon(totalAmount);

            hoaDon.setTongTienDonHang(total + thanhTienMoiThem);
            hoaDonRepository.save(hoaDon);

            sanPhamChiTiet.setSoLuong(tongSoLuong - soLuongUpdate);
            sanPhamChiTietRepository.save(sanPhamChiTiet);

            NhanVien nhanVien = nhanVienRepository.findByEmail(dto.getEmail_user());

            // Lưu lịch sử hóa đơn
            LSHoaDon lichSuHoaDon = new LSHoaDon();
            lichSuHoaDon.setNguoiThaoTac(nhanVien.getHoTen());
            lichSuHoaDon.setNgayTao(new Date());
            lichSuHoaDon.setHoaDon(hoaDon);
            lichSuHoaDon.setThaoTac(
                    "Cập nhật số lượng của sản phẩm " + hoaDonChiTiet.getSanPhamChiTiet().getSanPham().getTenSanPham()
                            + " từ " + soLuongBanDau + " thành " + soLuongUpdate);
            lsHoaDonRepository.save(lichSuHoaDon);

            if (sanPhamChiTiet.getSoLuong() <= 0) {
                sanPhamChiTiet.setTrangThai(false);
                sanPhamChiTietRepository.save(sanPhamChiTiet);

                List<HoaDonChiTiet> hdct = hoaDonChiTietRepository.findBySPCTID(sanPhamChiTiet.getId());
                for (HoaDonChiTiet ListHDCT : hdct) {
                    hoaDonChiTietRepository.deleteById(ListHDCT.getId());
                }

                List<GioHangChiTiet> gioHangChiTiets = gioHangChiTietRepository
                        .findCartBySPCTID(sanPhamChiTiet.getId());
                for (GioHangChiTiet gioHangChiTiet : gioHangChiTiets) {
                    gioHangChiTietRepository.deleteById(gioHangChiTiet.getId());
                }
            }
            respone.put("success", "Cập nhật số lượng thành công");
            return ResponseEntity.ok().body(respone);
        }
    }

    @SuppressWarnings("null")
    @Transactional
    @PostMapping("/xoa-hdct")
    public ResponseEntity<?> xoahdct(@RequestBody HoaDonChiTietDTO dto) {
        Map<String, String> respone = new HashMap<>();
        HoaDonChiTiet hoaDonChiTiet = hoaDonChiTietRepository.findById(dto.getId()).get();
        HoaDon hoaDon = hoaDonChiTiet.getHoaDon();
        int thanhTienHDCT = hoaDonChiTiet.getThanhTien();

        SanPhamChiTiet sanPhamChiTiet = hoaDonChiTiet.getSanPhamChiTiet();
        sanPhamChiTiet.setSoLuong(sanPhamChiTiet.getSoLuong() + hoaDonChiTiet.getSoLuong());
        sanPhamChiTietRepository.save(sanPhamChiTiet);
        hoaDonChiTietRepository.deleteById(dto.getId());
        List<HoaDonChiTiet> hoaDonChiTiets = hoaDonChiTietRepository.findByIDBill(hoaDonChiTiet.getHoaDon().getId());
        if (hoaDonChiTiets.isEmpty()) {
            respone.put("err", "Không thể xóa vì chỉ còn duy nhất sản phẩm này trong đơn");
            return ResponseEntity.badRequest().body(respone);
        } else {
            int totalAmount = 0;
            int total = hoaDon.getTongTienDonHang();
            for (HoaDonChiTiet hdct : hoaDonChiTiets) {
                totalAmount += hdct.getThanhTien();
            }
            hoaDon.setTongTienHoaDon(totalAmount);

            hoaDon.setTongTienDonHang(total - thanhTienHDCT);
            hoaDonRepository.save(hoaDon);

            NhanVien nhanVien = nhanVienRepository.findByEmail(dto.getEmail_user());

            // Lưu lịch sử hóa đơn
            LSHoaDon lichSuHoaDon = new LSHoaDon();
            lichSuHoaDon.setNguoiThaoTac(nhanVien.getHoTen());
            lichSuHoaDon.setHoaDon(hoaDon);
            lichSuHoaDon.setNgayTao(new Date());
            lichSuHoaDon.setThaoTac("Xóa sản phẩm " + hoaDonChiTiet.getSanPhamChiTiet().getSanPham().getTenSanPham());
            lsHoaDonRepository.save(lichSuHoaDon);

            respone.put("success", "Xóa sản phẩm thành công");
            return ResponseEntity.ok().body(respone);
        }
    }
}
