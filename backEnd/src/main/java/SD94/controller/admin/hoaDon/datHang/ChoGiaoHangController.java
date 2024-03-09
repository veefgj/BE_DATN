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
@RequestMapping("/hoaDon/datHang/choGiaoHang")
public class ChoGiaoHangController {
    @Autowired
    HoaDonDatHangService hoaDonDatHangService;

    @Autowired
    NhanVienRepository nhanVienRepository;
    @GetMapping("/danhSach")
    public List<HoaDon> listBill2() {
        return hoaDonDatHangService.findHoaDonByTrangThai(2);
    }

    @PostMapping("/capNhatTrangThai/dangGiaoHang")
    public ResponseEntity<Map<String, Boolean>> updateStatus3(@RequestBody HoaDonDTO hoaDonDTO) {
        Long id = hoaDonDTO.getId();
        String email = hoaDonDTO.getEmail_user();
        NhanVien nhanVien = nhanVienRepository.findByEmail(email);
        hoaDonDatHangService.capNhatTrangThai(3, id);
        hoaDonDatHangService.createTimeLine("Xác nhận giao đơn hàng", 3, id, nhanVien.getHoTen());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/capNhatTrangThai/dangGiaoHang-tatCa")
    public ResponseEntity<Map<String, Boolean>> updateStatusAll3(@RequestBody HoaDonDTO hoaDonDTO) {
        String email = hoaDonDTO.getEmail_user();
        NhanVien nhanVien = nhanVienRepository.findByEmail(email);
        hoaDonDatHangService.capNhatTrangThai_TatCa(2,3,"Xác nhận giao đơn hàng",nhanVien.getHoTen());
        return ResponseEntity.ok().build();
    }

    @PutMapping("/giaoDonHang/daChon")
    public List<HoaDon> updateStatusSelect2(@RequestBody HoaDonDTO hoaDonDTO) {
        String email = hoaDonDTO.getEmail_user();
        NhanVien nhanVien = nhanVienRepository.findByEmail(email);
        return hoaDonDatHangService.capNhatTrangThai_DaChon(hoaDonDTO, 3,"Xác nhận giao đơn hàng",nhanVien.getHoTen());
    }
    @RequestMapping("/timKiem={search}")
    public List<HoaDon> searchAllBill2(@PathVariable("search") String search) {
        return hoaDonDatHangService.searchAllBill(2, search);

    }

    @RequestMapping("/timKiemNgay={searchDate}")
    public List<HoaDon> searchDateBill2(@PathVariable("searchDate") String searchDate) {
        return hoaDonDatHangService.searchDateBill(2, searchDate);
    }
}
