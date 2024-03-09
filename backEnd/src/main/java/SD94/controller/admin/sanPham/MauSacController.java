package SD94.controller.admin.sanPham;

import SD94.entity.sanPham.MauSac;
import SD94.repository.sanPham.MauSacRepository;
import SD94.service.service.MauSacService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/mauSac")
public class MauSacController {
    @Autowired
    MauSacRepository repository;

    @Autowired
    MauSacService mauSacService;

    @GetMapping("/danhSach")
    public ResponseEntity<List<MauSac>> getColor() {
        List<MauSac> list = repository.findAll();
        return ResponseEntity.ok().body(list);
    }

    @PostMapping("/themMoi")
    public ResponseEntity<MauSac> saveCreate(@RequestBody MauSac mauSac) {
        return mauSacService.saveCreate(mauSac);
    }

    @DeleteMapping("/xoa/{id}")
    public ResponseEntity<List<MauSac>> delete(@PathVariable("id") Long id) {
        return mauSacService.deleteProductColor(id);
    }

    @GetMapping("/chinhSua/{id}")
    public MauSac edit(@PathVariable("id") Long id) {
        return repository.findByID(id);
    }

    @PutMapping("/luuChinhSua")
    public ResponseEntity<MauSac> saveUpdate(@RequestBody MauSac mauSac) {
        return mauSacService.saveEdit(mauSac);
    }
}
