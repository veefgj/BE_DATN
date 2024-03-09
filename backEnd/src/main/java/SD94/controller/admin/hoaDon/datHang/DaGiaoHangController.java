package SD94.controller.admin.hoaDon.datHang;

import SD94.dto.HoaDonDTO;
import SD94.entity.hoaDon.HoaDon;
import SD94.entity.nhanVien.NhanVien;
import SD94.repository.nhanVien.NhanVienRepository;
import SD94.service.service.HoaDonDatHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping("/hoaDon/datHang/daGiaoHang")
public class DaGiaoHangController {
    @Autowired
    HoaDonDatHangService hoaDonDatHangService;

    @Autowired
    NhanVienRepository nhanVienRepository;

    @GetMapping("/danhSach")
    public List<HoaDon> listBill4() {
        return hoaDonDatHangService.findHoaDonByTrangThai(4);
    }

    @RequestMapping("/timKiem={search}")
    public List<HoaDon> searchAllBill4(@PathVariable("search") String search) {
        return hoaDonDatHangService.searchAllBill(4, search);
    }

    @RequestMapping("/timKiemNgay={searchDate}")
    public List<HoaDon> searchDateBill4(@PathVariable("searchDate") String searchDate) {
        return hoaDonDatHangService.searchDateBill(4, searchDate);
    }

    @PostMapping("/capNhatTrangThai/huyDon")
    public ResponseEntity<Map<String, Boolean>> updateStatus5(@RequestBody HoaDonDTO hoaDonDTO) {
        Long id = hoaDonDTO.getId();
        String ghiChu = hoaDonDTO.getGhiChu();
        String email = hoaDonDTO.getEmail_user();
        NhanVien nhanVien = nhanVienRepository.findByEmail(email);
        hoaDonDatHangService.capNhatTrangThaiHuyDon(5, id, ghiChu);
        hoaDonDatHangService.createTimeLine("Huỷ đơn", 5, id, nhanVien.getHoTen());
        return ResponseEntity.ok().build();
    }

}
