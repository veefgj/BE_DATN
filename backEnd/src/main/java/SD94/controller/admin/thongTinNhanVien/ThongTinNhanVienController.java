package SD94.controller.admin.thongTinNhanVien;

import SD94.dto.NhanVienDTO;
import SD94.entity.nhanVien.NhanVien;
import SD94.repository.nhanVien.NhanVienRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class ThongTinNhanVienController {
    @Autowired
    NhanVienRepository nhanVienRepository;

    @GetMapping("/thongTin/{email}")
    public ResponseEntity<?> getThongTin(@PathVariable("email") String email){
        NhanVien nhanVien = nhanVienRepository.findByEmail(email);
        return ResponseEntity.ok().body(nhanVien);
    }

    @PostMapping("/suaThongTin")
    public ResponseEntity<?> suaThongTin(@RequestBody NhanVienDTO nhanVienDTO){
        NhanVien nhanVien = nhanVienRepository.findByEmail(nhanVienDTO.getEmail());
        nhanVien.setHoTen(nhanVienDTO.getName());
        nhanVien.setGioiTinh(nhanVienDTO.getGioiTinh());
        nhanVien.setSoDienThoai(nhanVienDTO.getPhoneNumber());
        nhanVien.setNgaySinh(nhanVienDTO.getDateOfBirth());
        nhanVienRepository.save(nhanVien);

        nhanVienRepository.findByEmail(nhanVien.getEmail());
        return ResponseEntity.ok().body(nhanVien);
    }
}
