package SD94.controller.admin.sanPham;

import SD94.entity.sanPham.KichCo;

import SD94.repository.sanPham.KichCoRepository;
import SD94.service.service.KichCoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/kichCo")
public class KichCoController {

    @Autowired
    KichCoRepository repository;

    @Autowired
    KichCoService kichCoService;

    @GetMapping("/danhSach")
    public ResponseEntity<List<KichCo>> getKichCo() {
        List<KichCo> list = repository.findAll();
        return ResponseEntity.ok().body(list);
    }

    @PostMapping("/themMoi")
    public ResponseEntity<KichCo> saveCreate(@RequestBody KichCo KichCo) {
        return kichCoService.saveCreate(KichCo);
    }

    @DeleteMapping("/xoa/{id}")
    public ResponseEntity<List<KichCo>> delete(@PathVariable("id") Long id) {
        return kichCoService.deleteProductSize(id);
    }

    @GetMapping("/chinhSua/{id}")
    public KichCo edit(@PathVariable("id") Long id) {
        return repository.findByID(id);
    }

    @PutMapping("/luuChinhSua")
    public ResponseEntity<KichCo> saveUpdate(@RequestBody KichCo KichCo) {
        return kichCoService.saveEdit(KichCo);
    }
}
