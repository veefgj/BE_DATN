package SD94.validator;

import SD94.dto.SanPhamDTO;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class TaiQuayValidate {
    public enum ErrorCode {

        Null_maMauSac,

        Null_kichCoDaChon,

        Null_san_pham_id,

        Null_soLuong,

        Null_soLuongHienCo

    }
    public static ResponseEntity<?> Taiquay(SanPhamDTO dto) {
        Map<String, String> errors = new HashMap<>();
        checkMaMauSac(dto.getMaMauSac(), errors);
        checkKichCoDaChon(dto.getKichCoDaChon(), errors);
        checkSanPhamID(dto.getSan_pham_id(), errors);
        checkSoLuongDaChon(dto.getSoLuong(), dto.getSoLuongHienCo(), errors);
        if (errors.isEmpty()) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.badRequest().body(errors);
    }


    public static void checkMaMauSac(String maMauSac, Map<String, String> errors) {
        if (maMauSac == null || maMauSac.trim().isEmpty()) {
            errors.put(GioHangValidate.ErrorCode.Null_maMauSac.name(), "Phải chọn màu sắc và kích cỡ");
        }
    }

    public static void checkKichCoDaChon(String kichCoDaChon, Map<String, String> errors) {
        if (kichCoDaChon == null || kichCoDaChon.trim().isEmpty()) {
            errors.put(GioHangValidate.ErrorCode.Null_kichCoDaChon.name(), "Phải chọn màu sắc và kích cỡ");
        }
    }

    @SuppressWarnings("null")
    public static void checkSoLuongDaChon(Integer soLuongDaChon, Integer soLuongDaCo, Map<String, String> errors) {
        if (soLuongDaChon == null) {
            errors.put(GioHangValidate.ErrorCode.Null_soLuongDaChon.name(), "Số lượng không được để trống");
        }

        if (soLuongDaChon <= 0) {
            errors.put(GioHangValidate.ErrorCode.Null_soLuongDaChon.name(), "Số lượng phải lớn hơn 0");
        }

        if (soLuongDaCo != null && soLuongDaChon > soLuongDaCo) {
            errors.put(GioHangValidate.ErrorCode.Null_soLuongDaChon.name(), "Số lượng đã chọn không được lớn hơn số lượng đang có");
        }
    }
    public static void checkSanPhamID(Long sanPham_id, Map<String, String> errors) {
        if (sanPham_id == null) {
            errors.put(GioHangValidate.ErrorCode.Null_san_pham_id.name(), "Không có sản phẩm nào được tìm thấy");
        }
    }
}
