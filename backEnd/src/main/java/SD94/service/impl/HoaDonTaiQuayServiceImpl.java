package SD94.service.impl;



import SD94.entity.hoaDon.HoaDonChiTiet;

import SD94.repository.hoaDon.HoaDonChiTietRepository;
import SD94.service.service.HoaDonTaiQuayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HoaDonTaiQuayServiceImpl implements HoaDonTaiQuayService {

    @Autowired
    HoaDonChiTietRepository billDetailsRepository;

    @Override
    public List<HoaDonChiTiet> findAllDetailedInvoice() {
        List<HoaDonChiTiet> billDetail = billDetailsRepository.findAllDetailedInvoice();
        return billDetail;
    }

    @Override
    public List<HoaDonChiTiet> findByIDBill(Long id) {
        List<HoaDonChiTiet> billDetail = billDetailsRepository.findByIDBill(id);
        return billDetail;
    }


}
