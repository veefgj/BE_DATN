package SD94.dto;

import SD94.entity.hoaDon.HoaDon;
import SD94.entity.hoaDon.HoaDonChiTiet;
import SD94.entity.hoaDon.LichSuHoaDon;
import lombok.Data;

import java.util.List;

@Data
public class ChiTietHoaDonDTO {
    List<HoaDonChiTiet> hoaDonChiTiets;
    List<LichSuHoaDon> lichSuHoaDons;
    HoaDon hoaDon;
}
