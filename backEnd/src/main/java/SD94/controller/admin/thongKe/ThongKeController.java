package SD94.controller.admin.thongKe;

import SD94.repository.thongKe.ThongKeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/thongKe")
public class ThongKeController {
    @Autowired
    private ThongKeRepository thongKeRepository;

    //Get dữ liệu
    @GetMapping("/tongDoanhSo")
    public ResponseEntity<?> thongKeAll() {
        return ResponseEntity.ok().body(thongKeRepository.getThongKe());
    }

    @GetMapping("/tongDoanhSoonline")
    public ResponseEntity<?> thongKeAllOnl() {
        return ResponseEntity.ok().body(thongKeRepository.getThongKe_online());
    }

    @GetMapping("/tongDoanhSooffline")
    public ResponseEntity<?> ThongKeAllOff() {
        return ResponseEntity.ok().body(thongKeRepository.getThongKe_offline());
    }

    @GetMapping("/thongKeTheoNgay")
    public ResponseEntity<?> thongKeTheoNgay() {
        return ResponseEntity.ok().body(thongKeRepository.getThongKeTheoNgay());
    }

    @GetMapping("/thongKeTheoNam")
    public ResponseEntity<?> thongKeTheoNam() {
        return ResponseEntity.ok().body(thongKeRepository.getThongKeTheoNam(2023));
    }

    @GetMapping("/thongKeTheoThang")
    public ResponseEntity<?> thongKeTheoThang() {
        LocalDateTime now = LocalDateTime.now();
        int currentMonth = now.getMonthValue();
        return ResponseEntity.ok().body(thongKeRepository.getThongKeTheoThang(currentMonth));
    }

    @GetMapping("/sanPhambanChay")
    public ResponseEntity<?> sanPhambanChay() {
        return ResponseEntity.ok().body(thongKeRepository.getThongKeSanPhamBanChay());
    }


    //Chức năng
    @GetMapping("/thongKeTheoNamTruyenVao/{nam}")
    public ResponseEntity<?> thongKeTheoNamTruyenVao(@PathVariable("nam") int nam) {
        return ResponseEntity.ok().body(thongKeRepository.getThongKeTheoNam(nam));
    }

    @GetMapping("/thongKeTheothangTruyenVao/{thang}")
    public ResponseEntity<?> thongKeTheothangTruyenVao(@PathVariable("thang") int thang) {
        return ResponseEntity.ok().body(thongKeRepository.getThongKeTheoThang(thang));
    }

}
