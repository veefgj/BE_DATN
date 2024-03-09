package SD94.service.service;

import SD94.entity.hoaDon.TrangThai;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface HoaDonService {
    ResponseEntity<?> getDanhSachHoaDon(String email, TrangThai trangThai);
}
