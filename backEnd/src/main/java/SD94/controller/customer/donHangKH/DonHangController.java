package SD94.controller.customer.donHangKH;


import SD94.entity.hoaDon.HoaDon;
import SD94.repository.nhanVien.NhanVienRepository;
import SD94.service.service.HoaDonDatHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/hoaDon/khachHang/donHang")
public class DonHangController {
    @Autowired
    HoaDonDatHangService hoaDonDatHangService;

    @Autowired
    NhanVienRepository nhanVienRepository;

    @GetMapping("/danhSach")
    public List<HoaDon> listBill1() {
        return hoaDonDatHangService.findHoaDonByLoai(0);
    }
}
