package SD94.controller.admin.hoaDon.datHang;

import SD94.entity.hoaDon.HoaDon;
import SD94.service.service.HoaDonDatHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/hoaDon/datHang/daHuy")
public class DaHuyController {
    @Autowired
    HoaDonDatHangService hoaDonDatHangService;

    @GetMapping("/danhSach")
    public List<HoaDon> listBill5() {
        return hoaDonDatHangService.findHoaDonByTrangThai(5);
    }

    @RequestMapping("/timKiem={search}")
    public List<HoaDon> searchAllBill5(@PathVariable("search") String search) {
        return hoaDonDatHangService.searchAllBill(5, search);

    }

    @RequestMapping("/timKiemNgay={searchDate}")
    public List<HoaDon> searchDateBill5(@PathVariable("searchDate") String searchDate) {
        return hoaDonDatHangService.searchDateBill(5, searchDate);
    }
}
