package SD94.dto;

import SD94.entity.sanPham.ChatLieu;
import SD94.entity.sanPham.LoaiSanPham;
import SD94.entity.sanPham.NhaSanXuat;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class SanPhamDTO {

    private Long id;

    private Long id_SPCT;

    private String tenSanPham;

    private Float gia;

    private String origin;

    private Integer trangThai;

    private Date ngayTao;

    private String nguoiTao;

    private ChatLieu chatLieu;

    private LoaiSanPham loaiSanPham;

    private NhaSanXuat nhaSanXuat;

    private List<Long> kichCo;

    private List<Long> mauSac;

    private Long  chatLieu_id;

    private Long  loaiSanPham_id;

    private Long  nhaSanXuat_id;

    //Dùng để add to cart bên phía customer
    private String maMauSac;

    private String kichCoDaChon;

    private Long san_pham_id;

    private int soLuongDaChon;

    private int donGia;

    private int soLuong;

    private int tongTien;

    private Long id_hoaDon;

    private Integer soLuongHienCo;

    private Long anh_id;

    private String anh_san_pham;

    private Long mauSac_id;

    private Long kichCo_id;

    private String email_user;
}
