package SD94.controller.admin.sanPham;

import SD94.entity.sanPham.NhaSanXuat;
import SD94.repository.sanPham.NhaSanXuatRepository;
import SD94.service.service.NhaSanXuatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/nhaSanXuat")
public class NhaSanXuatController {

    @Autowired
    NhaSanXuatRepository repository;

    @Autowired
    NhaSanXuatService nhaSanXuatService;

    @GetMapping("/danhSach")
    public List<NhaSanXuat> getProducer() {
        return nhaSanXuatService.findAllProducer();
    }

    @PostMapping("/themMoi")
    public ResponseEntity<NhaSanXuat> saveCreate(@RequestBody NhaSanXuat nhaSanXuat) {
        return nhaSanXuatService.saveCreate(nhaSanXuat);
    }

    @DeleteMapping("/xoa/{id}")
    public ResponseEntity<List<NhaSanXuat>> delete(@PathVariable("id") Long id) {
        return nhaSanXuatService.deleteProducer(id);
    }

    @GetMapping("/chinhSua/{id}")
    public NhaSanXuat edit(@PathVariable("id") Long id) {
        return repository.findByID(id);
    }

    @PutMapping("/luuChinhSua")
    public ResponseEntity<NhaSanXuat> saveUpdate(@RequestBody NhaSanXuat nhaSanXuat) {
        return nhaSanXuatService.saveEdit(nhaSanXuat);
    }

}
