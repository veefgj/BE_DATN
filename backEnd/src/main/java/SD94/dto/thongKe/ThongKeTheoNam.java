package SD94.dto.thongKe;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class ThongKeTheoNam {
    private Long trangthai_id;

    private Long thang;

    private Long tong_so_hoadon;

    private BigDecimal tong_tien_hoadon;

    private int so_san_pham_da_ban;
}
