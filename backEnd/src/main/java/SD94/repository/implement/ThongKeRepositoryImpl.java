package SD94.repository.implement;

import SD94.dto.thongKe.*;
import SD94.repository.thongKe.ThongKeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ThongKeRepositoryImpl implements ThongKeRepository {
        @Autowired
        private JdbcTemplate jdbcTemplate;

        @Override
        public List<ThongKeAll> getThongKe() {
                return jdbcTemplate.query(
                                "call thongKeAll()",
                                ((rs, rowNum) -> new ThongKeAll(
                                                rs.getLong("trangThai_id"),
                                                rs.getLong("tong_so_hoadon"),
                                                rs.getBigDecimal("tong_tien_hoadon"))));
        }

        @Override
        public List<ThongKeAllOnl> getThongKe_online() {
                return jdbcTemplate.query(
                                "call thongKeAll_online()",
                                ((rs, rowNum) -> new ThongKeAllOnl(
                                                rs.getLong("trangThai_id"),
                                                rs.getLong("tong_so_hoadon"),
                                                rs.getBigDecimal("tong_tien_hoadon"))));
        }

        @Override
        public List<ThongKeAllOff> getThongKe_offline() {
                return jdbcTemplate.query(
                                "call thongKeAll_offline()",
                                ((rs, rowNum) -> new ThongKeAllOff(
                                                rs.getLong("trangThai_id"),
                                                rs.getLong("tong_so_hoadon"),
                                                rs.getBigDecimal("tong_tien_hoadon"))));
        }

        @Override
        public List<TamTinhTongTienDaBanTheoNgay> getThongKeTheoNgay() {
                return jdbcTemplate.query(
                                "call thongKeTheoNgay();",
                                ((rs, rowNum) -> new TamTinhTongTienDaBanTheoNgay(
                                                rs.getLong("trangThai_id"),
                                                rs.getLong("tong_so_hoadon"),
                                                rs.getBigDecimal("tong_tien_hoadon"),
                                                rs.getInt("so_san_pham_da_ban"))));
        }

        @SuppressWarnings("deprecation")
        @Override
        public List<ThongKeTheoThang> getThongKeTheoThang(int thang) {
                return jdbcTemplate.query(
                                "CALL thongKeTheoThang(?)",
                                new Object[] { thang },
                                ((rs, rowNum) -> new ThongKeTheoThang(
                                                rs.getLong("trangThaI_id"),
                                                rs.getLong("tong_so_hoadon"),
                                                rs.getBigDecimal("tong_tien_hoadon"),
                                                rs.getInt("so_san_pham_da_ban"))));
        }

        @SuppressWarnings("deprecation")
        @Override
        public List<ThongKeTheoNam> getThongKeTheoNam(int nam) {
                return jdbcTemplate.query(
                                "CALL thongKeTheoNam(?)",
                                new Object[] { nam },
                                ((rs, rowNum) -> new ThongKeTheoNam(
                                                rs.getLong("trangThaI_id"),
                                                rs.getLong("thang"),
                                                rs.getLong("tong_so_hoadon"),
                                                rs.getBigDecimal("tong_tien_hoadon"),
                                                rs.getInt("so_san_pham_da_ban"))));
        }

        @Override
        public List<TopSanPhamBanChay> getThongKeSanPhamBanChay() {
                return jdbcTemplate.query(
                                "CALL top5SanPhamBanChay()",
                                ((rs, rowNum) -> new TopSanPhamBanChay(
                                                rs.getLong("sanpham_id"),
                                                rs.getString("ten_sanpham"),
                                                rs.getString("mau_sac"),
                                                rs.getString("kich_thuoc"),
                                                rs.getLong("so_luong_ban"),
                                                rs.getBigDecimal("doanh_thu"),
                                                rs.getString("anh_mac_dinh"))));
        }
}