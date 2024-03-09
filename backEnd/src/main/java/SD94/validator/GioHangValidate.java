package SD94.validator;

import SD94.dto.GioHangDTO;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class GioHangValidate {
    public enum ErrorCode {
        Null_maMauSac,

        Null_kichCoDaChon,

        Null_san_pham_id,

        Null_soLuongDaChon,

        Null_donGia,

        Null_soLuong,

        Null_tongTien,

        Null_id_gioHangChiTiet,

        Null_id_hoaDon,



        Null_soLuongHienCo
    }

        public static ResponseEntity<?> addToCartCheck(GioHangDTO GioHangDTO) {
            Map<String, String> errors = new HashMap<>();
            checkMaMauSac(GioHangDTO.getMaMauSac(), errors);
            checkKichCoDaChon(GioHangDTO.getKichCo(), errors);
            checkSanPhamID(GioHangDTO.getSan_pham_id(), errors);
            checkSoLuongHienCo(GioHangDTO.getSoLuongHienCo(), errors);
            checkSoLuongDaChon(GioHangDTO.getSoLuong(), GioHangDTO.getSoLuongHienCo(), errors);
            if (errors.isEmpty()) {
                return ResponseEntity.ok().build();
            }

            return ResponseEntity.badRequest().body(errors);
        }
    public static void checkSoLuongHienCo(Integer SoLuongHienCO, Map<String, String> errors) {
        if (SoLuongHienCO == null) {
            errors.put(GioHangValidate.ErrorCode.Null_soLuongHienCo.name(), "Không có số lượng hiện có");
        }
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

    public static void checkDonGia(Integer donGia, Map<String, String> errors) {
        if (donGia == null || donGia == 0) {
            errors.put(GioHangValidate.ErrorCode.Null_donGia.name(), "Đơn giá không được bỏ trống");
        }
    }

    public static void checkTongTien(Integer tongTien, Map<String, String> errors) {
        if (tongTien == null) {
            errors.put(GioHangValidate.ErrorCode.Null_tongTien.name(), "Tổng tiền không được bỏ trống");
        }
    }

    public static void checkSanPhamID(Long sanPham_id, Map<String, String> errors) {
        if (sanPham_id == null) {
            errors.put(GioHangValidate.ErrorCode.Null_san_pham_id.name(), "Không có sản phẩm nào được tìm thấy");
        }
    }
    public static ResponseEntity<?> checkout(GioHangDTO gioHangDTO) {
        Map<String, String> errors = new HashMap<>();
        checkGioHangChiTiet(gioHangDTO.getId_gioHangChiTiet(), errors);
        checkTongTien(gioHangDTO.getTongTien() ,errors);
        if (errors.isEmpty()) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().body(errors);
    }
    public static void checkGioHangChiTiet(long[] id_gioHangChiTiet, Map<String, String> errors) {
        if (id_gioHangChiTiet == null || id_gioHangChiTiet.length == 0) {
            errors.put(GioHangValidate.ErrorCode.Null_id_gioHangChiTiet.name(), "Bạn chưa chọn sản phẩm để đặt hàng");
        }
    }
    public static void checkTongTien1(Integer tongTien, Map<String, String> errors) {
        if (tongTien == null) {
            errors.put(GioHangValidate.ErrorCode.Null_tongTien.name(), "Tổng tiền không được bỏ trống");
        }
    }
}
