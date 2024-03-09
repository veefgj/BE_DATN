package SD94.repository.sanPham;

import SD94.entity.sanPham.SanPham;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SanPhamRepository extends JpaRepository<SanPham, Long> {
        @SuppressWarnings("null")
        @Query(value = "SELECT * FROM san_pham WHERE is_deleted = false and trang_thai = 0 ORDER BY id DESC", nativeQuery = true)
        List<SanPham> findAll();

        @Query(value = "select * from san_pham where id = ? and is_deleted = false", nativeQuery = true)
        SanPham findByID(Long id);

        @Query(value = "select * from san_pham\n" +
                        "where is_deleted = false and (ten_san_pham LIKE %?1% or gia LIKE %?1%) and trang_thai = 0;", nativeQuery = true)
        List<SanPham> findByAll(String input);

        @Query(value = "SELECT * FROM san_pham WHERE is_deleted = false AND DATE(started_date) = ?", nativeQuery = true)
        List<SanPham> findByDate(LocalDate ngayTao);

        @Query(value = "SELECT * FROM san_pham WHERE is_deleted = false AND ten_san_pham LIKE %?1% and trang_thai = 0", nativeQuery = true)
        List<SanPham> findByName(String name);

        @Query(value = "SELECT * FROM san_pham WHERE is_deleted = false AND ten_san_pham = ? and trang_thai = 0;", nativeQuery = true)
        SanPham checkLap(String tenSP);

        @Query(value = "select * from san_pham where loai_san_pham_id = ? and is_deleted = false and trang_thai = 0", nativeQuery = true)
        List<SanPham> findByLoaiSanPham(long loaiSanPham_id);

        @Query(value = "select * from san_pham where chat_lieu_id = ? and is_deleted = false and trang_thai = 0", nativeQuery = true)
        List<SanPham> findByChatLieu(long chatLieu_id);

        @Query(value = "select * from san_pham where nha_san_xuat_id = ? and is_deleted = false and trang_thai = 0", nativeQuery = true)
        List<SanPham> findByNSX(long nhaSanXuat_id);

        @Query(value = "SELECT DISTINCT sp.* FROM san_pham sp\n" +
                        "JOIN san_pham_chi_tiet spct ON sp.id = spct.san_pham_id\n" +
                        "WHERE spct.mau_sac_id = ? AND sp.is_deleted = false AND sp.trang_thai = 0;\n", nativeQuery = true)
        List<SanPham> findByMau(long mauSac_id);

        @Query(value = "SELECT DISTINCT sp.* FROM san_pham sp\n" +
                        "JOIN san_pham_chi_tiet spct ON sp.id = spct.san_pham_id\n" +
                        "WHERE spct.kich_co_id = ? AND sp.is_deleted = false AND sp.trang_thai = 0;", nativeQuery = true)
        List<SanPham> findByKichCo(long kichCo_id);

        @Query(value = "SELECT * FROM san_pham WHERE gia BETWEEN :gia1 AND :gia2 AND is_deleted = false and trang_thai = 0", nativeQuery = true)
        List<SanPham> findTheoGia(@Param("gia1") Float gia1, @Param("gia2") Float gia2);
}