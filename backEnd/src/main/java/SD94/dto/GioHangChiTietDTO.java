package SD94.dto;

import SD94.entity.sanPham.SanPhamChiTiet;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class GioHangChiTietDTO {

    private  long id;

    private SanPhamChiTiet sanPhamChiTiet;

    private Integer soLuong;

    private Integer donGia;

    private BigDecimal thanhTien;

    private String anh_san_pham;

    private Integer soLuongBanDau;

    private Integer soLuongCapNhat;

    private String email_user;
}
