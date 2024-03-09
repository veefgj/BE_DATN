package SD94.service.service;

import SD94.entity.sanPham.ChatLieu;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ChatLieuService {

    List<ChatLieu> findAllProductMaterial();

    ResponseEntity<ChatLieu> saveEdit(ChatLieu chatLieuUpdate);

    ResponseEntity<List<ChatLieu>> deleteProductMaterial(Long id);

    ResponseEntity<?> saveCreate(ChatLieu chatLieuCreate);

    List<ChatLieu> searchAllProductMaterial(String search);

    List<ChatLieu> searchDateProductMaterial(String searchDate);
}
