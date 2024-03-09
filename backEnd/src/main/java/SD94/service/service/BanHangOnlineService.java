package SD94.service.service;

import SD94.dto.GioHangDTO;
import SD94.dto.HoaDonDTO;
import SD94.entity.hoaDon.HoaDon;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface BanHangOnlineService {
    ResponseEntity<?> checkout(GioHangDTO dto);

    ResponseEntity<HoaDon> getHoaDon(long id_hoa_don);

    ResponseEntity<?> getHoaDonChiTiet(long id_hoa_don);

    ResponseEntity<HoaDon> getBill();

    ResponseEntity<?> addDiscount(HoaDonDTO hoaDonDTO);

    ResponseEntity<?> datHang(HoaDonDTO dto);

}
