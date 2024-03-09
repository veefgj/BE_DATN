package SD94.validator;

import SD94.dto.HoaDonDTO;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ThanhToanValidate {
    public enum ErrorCode {
        Null_email,

        Null_soDienThoai,

        Null_tienShip,

        Null_email_user,

        Null_diaChi,

        Null_nguoiTao,
    }

    public static ResponseEntity<?> thanhtoan(HoaDonDTO hoaDonDTO) {
        Map<String, String> errors = new HashMap<>();
        checkNguoiTao(hoaDonDTO.getNguoiTao(), errors);
        checksoDienThoai(hoaDonDTO.getSoDienThoai(), errors);
        checkEmail(hoaDonDTO.getEmail(), errors);
        checkdiaChi(hoaDonDTO.getDiaChi(), errors);
        if (errors.isEmpty()) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.badRequest().body(errors);
    }

    public static void checkNguoiTao(String nguoiTao, Map<String, String> errors) {
        if (nguoiTao == null || nguoiTao == "") {
            errors.put(DatHangCheckoutValidate.ErrorCode.Null_nguoiTao.name(), "Bạn chưa nhập họ tên");
        }
    }

    public static void checkEmail(String email, Map<String, String> errors) {
        if (email == null || email == "") {
            errors.put(DatHangCheckoutValidate.ErrorCode.Null_email.name(), "Bạn chưa nhập Email");
        }
    }

    public static void checktienShip(Integer tienShip, Map<String, String> errors) {
        if (tienShip == null || tienShip == 0) {
            errors.put(DatHangCheckoutValidate.ErrorCode.Null_tienShip.name(), "Bạn chưa chọn địa chỉ giao hàng");
        }
    }

    public static void checksoDienThoai(String soDienThoai, Map<String, String> errors) {
        if (soDienThoai == null || soDienThoai == "") {
            errors.put(DatHangCheckoutValidate.ErrorCode.Null_soDienThoai.name(), "Bạn chưa nhập số điện thoại");
        }
    }

    public static void checkdiaChi(String diaChi, Map<String, String> errors) {
        if (diaChi == null || diaChi == "") {
            errors.put(DatHangCheckoutValidate.ErrorCode.Null_diaChi.name(), "Bạn chưa nhập địa chỉ");
        }
    }
}
