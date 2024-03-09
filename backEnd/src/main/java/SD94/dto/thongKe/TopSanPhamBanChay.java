package SD94.dto.thongKe;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class TopSanPhamBanChay {
    private Long sanpham_id;

    private String ten_sanpham;

    private String mau_sac;

    private String kich_thuoc;

    private Long so_luong_ban;

    private BigDecimal doanh_thu;

    private String anh_mac_dinh;
}
