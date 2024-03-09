package SD94.service.service;

import SD94.dto.HoaDonChiTietDTO;
import SD94.dto.HoaDonDTO;
import SD94.dto.KhachHangDTO;
import SD94.dto.SanPhamDTO;
import SD94.entity.hoaDon.HoaDon;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BanHangTaiQuayService {

    ResponseEntity<?> taoHoaDon(HoaDonDTO hoaDonDTO);

    ResponseEntity<?> themSanPham(SanPhamDTO dto);

    List<HoaDon> xoaHoaDon(HoaDonDTO hoaDonDTO);

    ResponseEntity<?> themMaGiamGiaTaiQuay(HoaDonDTO hoaDonDTO);

    ResponseEntity<?> addKhachHang(KhachHangDTO dto);

    ResponseEntity<?> huyDon(HoaDonDTO hoaDonDTO);

    ResponseEntity<?> thanhToan(HoaDonDTO hoaDonDTO);

    ResponseEntity<?> xoaHDCT(HoaDonChiTietDTO dto);
}
