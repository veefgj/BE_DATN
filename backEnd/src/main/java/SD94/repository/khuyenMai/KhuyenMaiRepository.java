package SD94.repository.khuyenMai;

import SD94.entity.khuyenMai.KhuyenMai;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface KhuyenMaiRepository extends JpaRepository<KhuyenMai, Long> {

    @Modifying
    @Query(value = "select * from khuyen_mai where is_deleted= false order by id desc", nativeQuery = true)
    List<KhuyenMai> findAllDiscount();

    @Query(value = "select * from khuyen_mai where id = ? and is_deleted = false", nativeQuery = true)
    KhuyenMai findByID(Long id);

    @Query(value = "SELECT * from khuyen_mai WHERE is_deleted = false AND (ten_Khuyen_Mai LIKE %?1% OR tien_giam_toi_da LIKE %?1% OR phan_tram_giam LIKE %?1%)", nativeQuery = true)
    List<KhuyenMai> findDiscountByAll(String input);

    @Query(value = "SELECT * from khuyen_mai WHERE is_deleted = false AND DATE(ngay_bat_dau) = ?", nativeQuery = true)
    List<KhuyenMai> findDiscountByDate(LocalDate ngayTao);

    @Query(value = "select * from khuyen_mai where ten_khuyen_mai = ?", nativeQuery = true)
    Optional<KhuyenMai> findByName(String name);

    @Modifying
    @Query(value = "update khuyen_mai set trang_thai = ?1 where id = ?2", nativeQuery = true)
    void updateStatusDiscount(int status, long id);

    @Query(value = "select * from khuyen_mai where ten_Khuyen_Mai = ? and is_deleted = false", nativeQuery = true)
    KhuyenMai findByNameKM(String ten_Khuyen_Mai);

    @Query(value = "select * from khuyen_mai where trang_thai = 0 and is_deleted = false", nativeQuery = true)
    List<KhuyenMai> findALl2();
}
