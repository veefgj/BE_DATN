package SD94.dto;

import lombok.Data;
import java.util.List;

@Data
public class ThemMoiSanPhamDTO {
    private String tenSanPham;
    private Float gia;
    private long chatLieu_id;
    private long loaiSanPham_id;
    private long nhaSanXuat_id;
    private List<String> kichCo;
    private List<Integer> mauSac;
    private int soLuong;
    private int trangThai;
}
