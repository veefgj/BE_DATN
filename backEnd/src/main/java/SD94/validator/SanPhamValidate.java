package SD94.validator;

import SD94.dto.SanPhamDTO;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SanPhamValidate {

    public enum ErrorCode {
        Null_maMauSac,

        Null_kichCoDaChon,

        Null_san_pham_id,

        Null_soLuongDaChon,

        Null_donGia,

        Null_soLuong,

        Null_tongTien,

        Null_id_hoaDon,

        Null_soLuongHienCo,

        Null_tenSanPham,

        Null_loai_san_pham,

        Null_nha_san_xuat,

        Null_chat_lieu,

        Null_gia_ban,

        Null_so_luong,

        Null_mau_sac,

        Null_kich_co,

        Invalid_gia_ban,

        Invalid_so_luong

    }

    //Mua ngay
    public static ResponseEntity<?> checkOut(SanPhamDTO sanPhamDTO) {
        Map<String, String> errors = new HashMap<>();
        checkMaMauSac(sanPhamDTO.getMaMauSac(), errors);
        checkKichCoDaChon(sanPhamDTO.getKichCoDaChon(), errors);
        checkSanPhamID(sanPhamDTO.getSan_pham_id(), errors);
        checkSoLuongHienCo(sanPhamDTO.getSoLuongHienCo(), errors);
        checkSoLuongDaChon(sanPhamDTO.getSoLuong(), sanPhamDTO.getSoLuongHienCo(), errors);

        if (errors.isEmpty()) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.badRequest().body(errors);

    }

    public static void checkSoLuongHienCo(Integer SoLuongHienCO, Map<String, String> errors) {
        if (SoLuongHienCO == null) {
            errors.put(ErrorCode.Null_soLuongHienCo.name(), "Không có số lượng hiện có");
        }
    }

    public static void checkMaMauSac(String maMauSac, Map<String, String> errors) {
        if (maMauSac == null || maMauSac.trim().isEmpty()) {
            errors.put(ErrorCode.Null_maMauSac.name(), "Phải chọn màu sắc và kích cỡ");
        }
    }

    public static void checkKichCoDaChon(String kichCoDaChon, Map<String, String> errors) {
        if (kichCoDaChon == null || kichCoDaChon.trim().isEmpty()) {
            errors.put(ErrorCode.Null_kichCoDaChon.name(), "Phải chọn màu sắc và kích cỡ");
        }
    }

    @SuppressWarnings("null")
    public static void checkSoLuongDaChon(Integer soLuongDaChon, Integer soLuongDaCo, Map<String, String> errors) {
        if (soLuongDaChon == null) {
            errors.put(ErrorCode.Null_soLuongDaChon.name(), "Số lượng không được để trống");
        }

        if (soLuongDaChon <= 0) {
            errors.put(ErrorCode.Null_soLuongDaChon.name(), "Số lượng phải lớn hơn 0");
        }

        if (soLuongDaCo != null && soLuongDaChon > soLuongDaCo) {
            errors.put(SanPhamValidate.ErrorCode.Null_soLuongDaChon.name(), "Số lượng đã chọn không được lớn hơn số lượng đang có");
        }
    }

    public static void checkDonGia(Integer donGia, Map<String, String> errors) {
        if (donGia == null || donGia == 0) {
            errors.put(SanPhamValidate.ErrorCode.Null_donGia.name(), "Đơn giá không được bỏ trống");
        }
    }

    public static void checkTongTien(Integer tongTien, Map<String, String> errors) {
        if (tongTien == null) {
            errors.put(SanPhamValidate.ErrorCode.Null_tongTien.name(), "Tổng tiền không được bỏ trống");
        }
    }

    public static void checkSanPhamID(Long sanPham_id, Map<String, String> errors) {
        if (sanPham_id == null) {
            errors.put(SanPhamValidate.ErrorCode.Null_san_pham_id.name(), "Không có sản phẩm nào được tìm thấy");
        }
    }

    public static ResponseEntity<?> checkTaoSanPham(SanPhamDTO sanPhamDTO) {
        Map<String, String> errors = new HashMap<>();
        checkTenSanPham(sanPhamDTO.getTenSanPham(), errors);
        checkGiaBan(sanPhamDTO.getGia(), errors);
        checkSoLuong(sanPhamDTO.getSoLuong(), errors);
        checkLoaiSanPham(sanPhamDTO.getLoaiSanPham_id(), errors);
        checkNhaSanXuat(sanPhamDTO.getNhaSanXuat_id(), errors);
        checkChatLieu(sanPhamDTO.getChatLieu_id(), errors);
        checkMauSac(sanPhamDTO.getMauSac(), errors);
        checkKichCo(sanPhamDTO.getKichCo(), errors);

        if (errors.isEmpty()) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.badRequest().body(errors);
    }

    private static void checkKichCo(List<Long> kichCo, Map<String, String> errors) {
        if (kichCo == null || kichCo.isEmpty()) {
            errors.put(SanPhamValidate.ErrorCode.Null_kich_co.name(), "Vui lòng chọn kích cỡ");
        }
    }

    private static void checkMauSac(List<Long> mauSac, Map<String, String> errors) {
        if (mauSac == null || mauSac.isEmpty()) {
            errors.put(SanPhamValidate.ErrorCode.Null_mau_sac.name(), "Vui lòng chọn màu sắc");
        }
    }

    private static void checkSoLuong(Integer soLuong, Map<String, String> errors) {
        if (soLuong == 0) {
            errors.put(SanPhamValidate.ErrorCode.Null_so_luong.name(), "Bạn chưa nhập số lượng sản phẩm");
        } else {
            if (soLuong < 0) {
                errors.put(SanPhamValidate.ErrorCode.Invalid_so_luong.name(), "Số lượng sản phẩm phải lớn hơn 0");
            }
        }
    }

    private static void checkGiaBan(Float gia, Map<String, String> errors) {
        if (gia == null) {
            errors.put("gia", "Bạn chưa nhập giá bán");
        } else {
            try {
                Float giaFloat = gia;
                if (giaFloat <= 0) {
                    errors.put("gia", "Giá tiền phải lớn hơn 0");
                }
            } catch (NumberFormatException e) {
                errors.put("gia", "Giá tiền không hợp lệ, vui lòng nhập số");
            }
        }
    }

    private static void checkChatLieu(Long chatLieu_id, Map<String, String> errors) {
        if (chatLieu_id == null) {
            errors.put(SanPhamValidate.ErrorCode.Null_chat_lieu.name(), "Vui lòng chọn chất liệu");
        }
    }

    private static void checkNhaSanXuat(Long nhaSanXuat_id, Map<String, String> errors) {
        if (nhaSanXuat_id == null) {
            errors.put(SanPhamValidate.ErrorCode.Null_nha_san_xuat.name(), "Vui lòng chọn nhà sản xuất");
        }
    }

    private static void checkLoaiSanPham(Long loaiSanPham_id, Map<String, String> errors) {
        if (loaiSanPham_id == null) {
            errors.put(SanPhamValidate.ErrorCode.Null_loai_san_pham.name(), "Vui lòng chọn loại sản phẩm");
        }
    }

    private static void checkTenSanPham(String tenSanPham, Map<String, String> errors) {
        if (tenSanPham == null || tenSanPham == "") {
            errors.put(SanPhamValidate.ErrorCode.Null_tenSanPham.name(), "Bạn chưa nhập tên sản phẩm");
        }
    }

}