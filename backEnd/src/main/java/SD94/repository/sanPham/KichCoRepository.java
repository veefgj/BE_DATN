package SD94.repository.sanPham;

import SD94.entity.sanPham.KichCo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface KichCoRepository extends JpaRepository<KichCo, Long> {
    @SuppressWarnings("null")
    @Query(value = "SELECT * FROM kich_co WHERE is_deleted = false ORDER BY id DESC", nativeQuery = true)
    List<KichCo> findAll();

    @Query(value = "select * from kich_co where id = ? and is_deleted = false", nativeQuery = true)
    KichCo findByID(Long id);

    @Query(value = "SELECT * FROM kich_co WHERE is_deleted = false AND DATE(started_date) = ?", nativeQuery = true)
    List<KichCo> findByDate(LocalDate ngayTao);

    @Query(value = "select * from kich_co where is_deleted = false and kich_co = ?;", nativeQuery = true)
    KichCo findByName(String name);

    @Query(value = "SELECT pc.kich_co AS ten_kich_co FROM san_pham_chi_tiet pd JOIN kich_co pc ON pd.kich_co_id = pc.id WHERE pd.san_pham_id = ? GROUP BY pd.kich_co_id", nativeQuery = true)
    List<String> getKichCo(long id_product);

    @Query(value = "select * from kich_co where kich_co = ?", nativeQuery = true)
    KichCo findByKichCo(String kichCo);
}
