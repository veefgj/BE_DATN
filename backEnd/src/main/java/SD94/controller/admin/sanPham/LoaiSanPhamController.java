package SD94.controller.admin.sanPham;

import SD94.entity.sanPham.LoaiSanPham;
import SD94.repository.sanPham.LoaiSanPhamRepository;
import SD94.service.service.LoaiSanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/loaiSanPham")
public class LoaiSanPhamController {

    @Autowired
    LoaiSanPhamRepository repository;

    @Autowired
    LoaiSanPhamService loaiSanPhamService;

    @GetMapping("/danhSach")
    public ResponseEntity<List<LoaiSanPham>> getLine() {
        List<LoaiSanPham> list = repository.findAll();
        return ResponseEntity.ok().body(list);
    }

    @PostMapping("/themMoi")
    public ResponseEntity<LoaiSanPham> saveCreate(@RequestBody LoaiSanPham loaiSanPham) {
        return loaiSanPhamService.saveCreate(loaiSanPham);
    }

    @DeleteMapping("/xoa/{id}")
    public ResponseEntity<List<LoaiSanPham>> delete(@PathVariable("id") Long id) {
        return loaiSanPhamService.deleteProductLine(id);
    }

    @GetMapping("/chinhSua/{id}")
    public LoaiSanPham edit(@PathVariable("id") Long id) {
        return repository.findByID(id);
    }

    @PutMapping("/luuChinhSua")
    public ResponseEntity<LoaiSanPham> saveUpdate(@RequestBody LoaiSanPham loaiSanPham) {
        return loaiSanPhamService.saveEdit(loaiSanPham);
    }

}
