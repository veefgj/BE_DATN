package SD94.service.service;

import SD94.entity.khuyenMai.KhuyenMai;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface KhuyenMaiService {
    List<KhuyenMai> findAllkhuyenMai();

    ResponseEntity<KhuyenMai> saveEdit(KhuyenMai khuyenMaiUpdate);

    ResponseEntity<List<KhuyenMai>> deletekhuyenMai(Long id);

    ResponseEntity<KhuyenMai> saveCreate(KhuyenMai khuyenMaiCreate);

    List<KhuyenMai> searchAllkhuyenMai(String search);

    List<KhuyenMai> searchDatekhuyenMai(String searchDate);
}
