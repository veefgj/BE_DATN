package SD94.service.service;

import SD94.entity.sanPham.SanPhamChiTiet;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SanPhamChiTietService {

    List<SanPhamChiTiet> findAllProductDetails();

    List<SanPhamChiTiet> findProductDetails();

    ResponseEntity<SanPhamChiTiet> saveEdit(SanPhamChiTiet sanPhamChiTietUpdate);

    ResponseEntity<List<SanPhamChiTiet>> deleteProductDetails(Long id);

    ResponseEntity<SanPhamChiTiet> saveCreate(SanPhamChiTiet sanPhamChiTietCreate);

    List<SanPhamChiTiet> searchAllProductDetails(String search);

    List<SanPhamChiTiet> searchDateProductDetails(String searchDate);

    ResponseEntity<?> chinhSuaSoLuongSPCT(SanPhamChiTiet sanPhamChiTiet);

}