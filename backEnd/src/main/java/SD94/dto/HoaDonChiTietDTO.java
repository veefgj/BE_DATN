package SD94.dto;

import SD94.entity.hoaDon.HoaDon;
import SD94.entity.sanPham.SanPhamChiTiet;
import lombok.Data;

@Data
public class HoaDonChiTietDTO {
    private Long id;

    private Long idProduct;

    private Long idColor;

    private Long idSize;

    private String product;

    private Integer quantity;

    private Integer soLuong;

    private Integer donGia;

    private Integer thanhTien;

    private HoaDon hoaDon;

    private SanPhamChiTiet sanPhamChiTiet;

    private String anhSanPham;

    private int soLuongcapNhat;

    private String email_user;
}
