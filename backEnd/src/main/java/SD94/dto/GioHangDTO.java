package SD94.dto;

import lombok.Data;

@Data
public class GioHangDTO {
    //Dùng để add to cart bên phía customer
    private String maMauSac;

    private String kichCo;

    private String email;

    private long san_pham_id;

    private int soLuong;

    private int donGia;

    private long [] id_gioHangChiTiet;

    private int tongTien;

    private Integer soLuongHienCo;

}
