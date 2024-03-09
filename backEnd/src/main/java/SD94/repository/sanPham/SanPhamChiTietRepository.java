package SD94.repository.sanPham;

import SD94.entity.sanPham.SanPhamChiTiet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface SanPhamChiTietRepository extends JpaRepository<SanPhamChiTiet, Long> {
        @Query(value = "select spct from SanPhamChiTiet spct where spct.sanPham.id=:idsp and spct.isDeleted=false")
        List<SanPhamChiTiet> findSpctByIdSp(@Param("idsp") Long idsp);

        @SuppressWarnings("null")
        @Query(value = "SELECT * FROM san_pham_chi_tiet WHERE is_deleted = false ORDER BY id DESC", nativeQuery = true)
        List<SanPhamChiTiet> findAll();

        @Query(value = "select * from san_pham_chi_tiet where id = ? and is_deleted = false", nativeQuery = true)
        SanPhamChiTiet findByID(Long id);

        @Query(value = "select * from san_pham_chi_tiet spct\n" +
                        "        join san_pham sp on spct.san_pham_id = sp.id\n" +
                        "        join chat_lieu cl on sp.chat_lieu_id = cl.id\n" +
                        "        join nha_san_xuat nsx on sp.nha_san_xuat_id = nsx.id\n" +
                        "        join loai_san_pham lsp on sp.loai_san_pham_id = lsp.id\n" +
                        "        where sp.is_deleted = false and (ten_san_pham = ?1 or gia = ?1 or chat_lieu = ?1 or nha_san_xuat = ?1 or loai_san_pham = ?1);", nativeQuery = true)
        List<SanPhamChiTiet> findByAll(String input);

        @Query(value = "SELECT * FROM san_pham_chi_tiet WHERE is_deleted = false AND DATE(started_date) = ?", nativeQuery = true)
        List<SanPhamChiTiet> findByDate(LocalDate ngayTao);

        @Query(value = "select * from san_pham_chi_tiet where name = ?", nativeQuery = true)
        Optional<SanPhamChiTiet> findByName(String name);

        @Query(value = "select * from san_pham_chi_tiet where san_pham_id = ? and is_deleted = false", nativeQuery = true)
        List<SanPhamChiTiet> findByProductID(Long id);

        @Query(value = "select * from san_pham_chi_tiet where id = ? and is_deleted = false", nativeQuery = true)
        List<SanPhamChiTiet> findByProduct(Long id);

        @Query(value = "select * from san_pham_chi_tiet spct where spct.san_pham_id = ? and spct.is_deleted = false", nativeQuery = true)
        List<SanPhamChiTiet> getProductD(@Param("san_pham_id") long san_pham_id);

        @Query(value = "SELECT * FROM san_pham_chi_tiet WHERE is_deleted = false AND san_pham_id = ?", nativeQuery = true)
        List<SanPhamChiTiet> findProductDetails();

        @Query(value = "SELECT pc.ma_mau_sac AS color_name, ps.kich_co AS size_name\n" +
                        "FROM san_pham_chi_tiet pd\n" +
                        "         JOIN mau_sac pc ON pd.mau_sac_id = pc.id\n" +
                        "         JOIN kich_co ps ON pd.kich_co_id = ps.id\n" +
                        "WHERE pd.san_pham_id = ?\n" +
                        "GROUP BY pc.ma_mau_sac, ps.kich_co;", nativeQuery = true)
        List<String> getProduct(long id_product);

        @Query(value = "select tenAnh from hinh_anh where id_product = ? and anh_mac_dinh = true", nativeQuery = true)
        String getAnhMacDinh(long sanPham_id);

        @Query(value = "select so_luong from san_pham_chi_tiet where mau_sac_id = ?1 and kich_co_id = ?2 and san_pham_id = ?3", nativeQuery = true)
        Integer getSoLuongHienCp(long mau_sac_id, long kich_co_id, long san_pham_id);

        @Query(value = "select * from san_pham_chi_tiet where mau_sac_id = ?1 and kich_co_id = ?2 and san_pham_id = ?3 and is_deleted = false", nativeQuery = true)
        SanPhamChiTiet getSanPhamChiTiet(long mau_sac_id, long kich_co_id, long san_pham_id);

        @Modifying
        @Query(value = "delete from san_pham_chi_tiet where id = ?", nativeQuery = true)
        void deleteByIDSPT(long id);
}
