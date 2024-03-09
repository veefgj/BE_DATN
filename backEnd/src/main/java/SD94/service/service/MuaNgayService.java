package SD94.service.service;

import SD94.dto.HoaDonDTO;
import SD94.dto.SanPhamDTO;
import SD94.entity.hoaDon.HoaDon;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface MuaNgayService {
    //Mua ngay
    ResponseEntity<?> muaNgayCheckOut(SanPhamDTO dto);

    ResponseEntity<HoaDon> getBill();

    ResponseEntity<?> addDiscount(HoaDonDTO hoaDonDTO);

    ResponseEntity<?> datHang(HoaDonDTO dto);

}
