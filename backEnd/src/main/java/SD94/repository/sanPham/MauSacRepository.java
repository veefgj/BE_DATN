package SD94.repository.sanPham;

import SD94.entity.sanPham.MauSac;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MauSacRepository extends JpaRepository<MauSac, Long> {
    @SuppressWarnings("null")
    @Query(value = "select * from mau_sac where is_deleted =false ORDER BY id DESC", nativeQuery = true)
    List<MauSac> findAll();

    @Query(value = "select * from mau_sac where id = ? and is_deleted = false", nativeQuery = true)
    MauSac findByID(Long id);

    @Query(value = "SELECT * FROM mau_sac WHERE is_deleted = false AND (name LIKE %?1% OR maximumvalue LIKE %?1% OR percent_discount LIKE %?1%)", nativeQuery = true)
    List<MauSac> findByAll(String input);

    @Query(value = "SELECT * FROM mau_sac WHERE is_deleted = false AND DATE(started_date) = ?", nativeQuery = true)
    List<MauSac> findByDate(LocalDate ngayTao);

    @Query(value = "select * from mau_sac where is_deleted = false and ten_mau_sac = ? or ma_mau_sac = ?;", nativeQuery = true)
    MauSac findByName(String tenMauSac, String maMauSac);

    @Query(value = "SELECT pc.ma_mau_sac AS color_name FROM san_pham_chi_tiet pd JOIN mau_sac pc ON pd.mau_sac_id = pc.id WHERE pd.san_pham_id = ? GROUP BY pd.mau_sac_id;", nativeQuery = true)
    List<String> getColor(long id);

    @Query(value = "select * from mau_sac where ma_mau_sac = ?", nativeQuery = true)
    MauSac findByMaMauSac(String maMauSac);
}
