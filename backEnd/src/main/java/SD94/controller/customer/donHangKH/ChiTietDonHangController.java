package SD94.controller.customer.donHangKH;


import SD94.repository.hoaDon.HoaDonChiTietRepository;
import SD94.repository.hoaDon.HoaDonRepository;
import SD94.repository.hoaDon.TrangThaiRepository;
import SD94.service.service.HoaDonDatHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/hoaDon/chiTietDonHang")
public class ChiTietDonHangController {
    @Autowired
    HoaDonDatHangService hoaDonDatHangService;

    @Autowired
    HoaDonRepository hoaDonRepository;

    @Autowired
    TrangThaiRepository trangThaiRepository;

    @Autowired
    HoaDonChiTietRepository hoaDonChiTietRepository;

    @GetMapping("/donHang/id={id}")
    public ResponseEntity<?> CTDaGiaoHang(@PathVariable("id") long hoa_don_id) {
        return hoaDonDatHangService.CTDonHangKH(hoa_don_id);
    }
}
