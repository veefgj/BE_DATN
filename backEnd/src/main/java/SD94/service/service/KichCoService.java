package SD94.service.service;

import SD94.entity.sanPham.KichCo;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface KichCoService {

    List<KichCo> findAllProductSize();

    ResponseEntity<KichCo> saveEdit(KichCo kichCoUpdate);

    ResponseEntity<List<KichCo>> deleteProductSize(Long id);

    ResponseEntity<KichCo> saveCreate(KichCo kichCoCreate);

    List<KichCo> searchAllProductSize(String search);

    List<KichCo> searchDateProductSize(String searchDate);

}
