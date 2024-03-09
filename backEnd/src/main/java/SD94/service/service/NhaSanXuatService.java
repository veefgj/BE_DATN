package SD94.service.service;

import SD94.entity.sanPham.NhaSanXuat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface NhaSanXuatService {

    List<NhaSanXuat> findAllProducer();

    ResponseEntity<NhaSanXuat> saveEdit(NhaSanXuat nhaSanXuatUpdate);

    ResponseEntity<List<NhaSanXuat>> deleteProducer(Long id);

    ResponseEntity<NhaSanXuat> saveCreate(NhaSanXuat nhaSanXuatCreate);

    List<NhaSanXuat> searchAllProducer(String search);

    List<NhaSanXuat> searchDateProducer(String searchDate);

}
