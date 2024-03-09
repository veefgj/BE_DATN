package SD94.service.service;


import SD94.entity.hoaDon.HoaDonChiTiet;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface HoaDonTaiQuayService {
    List<HoaDonChiTiet> findAllDetailedInvoice();
    List<HoaDonChiTiet> findByIDBill(Long id);
}
