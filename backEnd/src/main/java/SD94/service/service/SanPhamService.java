package SD94.service.service;

import SD94.dto.SanPhamDTO;
import SD94.entity.sanPham.SanPham;
import SD94.entity.sanPham.SanPhamChiTiet;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SanPhamService {

    List<SanPham> findAllProduct();

    ResponseEntity<SanPham> saveEdit(SanPhamDTO sanPhamDTO);

    ResponseEntity<List<SanPham>> deleteProduct(Long id);

    ResponseEntity<?> searchAllProduct(String search);

    List<SanPham> searchDateProduct(String searchDate);

    ResponseEntity<?> taoSanPham(SanPhamDTO sanPhamDTO);

    List<Object> chiTietSanPham(long id_SanPham);

    List<SanPhamChiTiet> spct_list();

    ResponseEntity<?> getSanPhamTheoGia(Float gia1, Float gia2);

}
