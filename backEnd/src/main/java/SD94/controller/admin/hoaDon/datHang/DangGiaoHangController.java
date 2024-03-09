package SD94.controller.admin.hoaDon.datHang;

import SD94.dto.HoaDonDTO;
import SD94.entity.hoaDon.HoaDon;
import SD94.entity.khachHang.KhachHang;
import SD94.entity.nhanVien.NhanVien;
import SD94.repository.khachHang.KhachHangRepository;
import SD94.repository.nhanVien.NhanVienRepository;
import SD94.service.service.HoaDonDatHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping("/hoaDon/datHang/dangGiaoHang")
public class DangGiaoHangController {
    @Autowired
    HoaDonDatHangService hoaDonDatHangService;

    @Autowired
    NhanVienRepository nhanVienRepository;

    @Autowired
    KhachHangRepository khachHangRepository;

    @GetMapping("/danhSach")
    public List<HoaDon> listBill3() {
        return hoaDonDatHangService.findHoaDonByTrangThai(3);
    }

    @PostMapping("/capNhatTrangThai/daGiaoHang")
    public List<HoaDon> updateStatus4(@RequestBody HoaDonDTO hoaDonDTO) {
        Long id = hoaDonDTO.getId();
        String email = hoaDonDTO.getEmail_user();
        NhanVien nhanVien = nhanVienRepository.findByEmail(email);
        hoaDonDatHangService.capNhatTrangThai(4, id);
        hoaDonDatHangService.createTimeLine("Xác nhận đã giao hàng", 4, id,nhanVien.getHoTen());
        return hoaDonDatHangService.findHoaDonByTrangThai(3);

    }
    @PostMapping("/capNhatTrangThai/huyDon5")
    public ResponseEntity<Map<String, Boolean>> updateStatus5(@RequestBody HoaDonDTO hoaDonDTO) {
        Long id = hoaDonDTO.getId();
        String ghiChu = hoaDonDTO.getGhiChu();
        String email = hoaDonDTO.getEmail_user();
        NhanVien nhanVien = nhanVienRepository.findByEmail(email);
        String nguoiThaoTac = null;
        if(nhanVien != null){
            nguoiThaoTac = nhanVien.getHoTen();
        }else {
            KhachHang khachHang = khachHangRepository.findByEmail(email);
            nguoiThaoTac = khachHang.getHoTen();
        }
        hoaDonDatHangService.capNhatTrangThaiHuyDon(5, id, ghiChu);
        hoaDonDatHangService.createTimeLine("Huỷ đơn", 5, id, nguoiThaoTac);
        return ResponseEntity.ok().build();
    }

    @RequestMapping("/timKiem={search}")
    public List<HoaDon> searchAllBill3(@PathVariable("search") String search) {
        return hoaDonDatHangService.searchAllBill(3, search);
    }

    @RequestMapping("/timKiemNgay={searchDate}")
    public List<HoaDon> searchDateBill3(@PathVariable("searchDate") String searchDate) {
        return hoaDonDatHangService.searchDateBill(3, searchDate);
    }
}
