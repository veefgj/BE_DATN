package SD94.controller.admin.sanPham;

import SD94.entity.sanPham.ChatLieu;
import SD94.repository.sanPham.ChatLieuRepository;
import SD94.service.service.ChatLieuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/chatLieu")
public class ChatLieuController {

    @Autowired
    ChatLieuRepository repository;

    @Autowired
    ChatLieuService chatLieuService;

    @GetMapping("/danhSach")
    public ResponseEntity<List<ChatLieu>> getMaterial() {
        List<ChatLieu> list = repository.findAll();
        return ResponseEntity.ok().body(list);
    }

    @PostMapping("/themMoi")
    public ResponseEntity<?> saveCreate(@RequestBody ChatLieu chatLieu) {
        return chatLieuService.saveCreate(chatLieu);
    }

    @DeleteMapping("/xoa/{id}")
    public ResponseEntity<List<ChatLieu>> delete(@PathVariable("id") Long id) {
        return chatLieuService.deleteProductMaterial(id);
    }

    @GetMapping("/chinhSua/{id}")
    public ChatLieu edit(@PathVariable("id") Long id) {
        return repository.findByID(id);
    }

    @PutMapping("/luuChinhSua")
    public ResponseEntity<ChatLieu> saveUpdate(@RequestBody ChatLieu chatLieu) {
        return chatLieuService.saveEdit(chatLieu);
    }

}
