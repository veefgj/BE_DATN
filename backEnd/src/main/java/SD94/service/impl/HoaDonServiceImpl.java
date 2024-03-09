package SD94.service.impl;

import SD94.entity.hoaDon.HoaDon;
import SD94.entity.hoaDon.TrangThai;
import SD94.entity.khachHang.KhachHang;
import SD94.repository.hoaDon.HoaDonRepository;
import SD94.repository.khachHang.KhachHangRepository;
import SD94.service.service.HoaDonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HoaDonServiceImpl implements HoaDonService {

    @Autowired
    KhachHangRepository khachHangRepository;

    @Autowired
    HoaDonRepository hoaDonRepository;

    @Override
    public ResponseEntity<?> getDanhSachHoaDon(String email, TrangThai trangThai) {
        KhachHang khachHang = khachHangRepository.findByEmail(email);
        List<HoaDon> hoaDons = hoaDonRepository.getDSChoXacNhan(khachHang.getId(), trangThai.getId());
        return ResponseEntity.ok().body(hoaDons);
    }
}
