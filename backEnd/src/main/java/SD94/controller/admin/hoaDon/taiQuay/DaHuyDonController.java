package SD94.controller.admin.hoaDon.taiQuay;


import SD94.entity.hoaDon.HoaDon;
import SD94.repository.nhanVien.NhanVienRepository;
import SD94.service.service.HoaDonDatHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/hoaDon/taiQuay/daHuy")
public class DaHuyDonController {
    @Autowired
    HoaDonDatHangService hoaDonDatHangService;

    @Autowired
    NhanVienRepository nhanVienRepository;
    @GetMapping("/danhSach")
    public List<HoaDon> listBill2() {
        return hoaDonDatHangService.findHoaDonByTrangThai(8);
    }


    @RequestMapping("/timKiem={search}")
    public List<HoaDon> searchAllBill2(@PathVariable("search") String search) {
        return hoaDonDatHangService.searchAllBill(8, search);

    }

    @RequestMapping("/timKiemNgay={searchDate}")
    public List<HoaDon> searchDateBill2(@PathVariable("searchDate") String searchDate) {
        return hoaDonDatHangService.searchDateBill(8, searchDate);
    }
}
