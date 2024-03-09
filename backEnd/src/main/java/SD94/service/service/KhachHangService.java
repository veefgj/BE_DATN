package SD94.service.service;

import SD94.entity.khachHang.KhachHang;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface KhachHangService {
    List<KhachHang> findAllCustomer();

    ResponseEntity<KhachHang> createCustomer(KhachHang khachHangCreate);

    ResponseEntity<KhachHang> editCustomer(KhachHang khachHangEdit);

    ResponseEntity<List<KhachHang>> deleteCustomer(Long id);

    List<KhachHang> searchAllCustomer(String search);

    List<KhachHang> searchDateCustomer(String searchDate);

    ResponseEntity<?> Register(KhachHang create);
}
