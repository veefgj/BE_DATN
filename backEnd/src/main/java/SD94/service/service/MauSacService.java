package SD94.service.service;

import SD94.entity.sanPham.MauSac;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MauSacService {

    List<MauSac> findAllProductColor();

    ResponseEntity<MauSac> saveEdit(MauSac mauSacUpdate);

    ResponseEntity<List<MauSac>> deleteProductColor(Long id);

    ResponseEntity<MauSac> saveCreate(MauSac mauSac);

    List<MauSac> searchAllProductColor(String search);

    List<MauSac> searchDateProductColor(String searchDate);

}
