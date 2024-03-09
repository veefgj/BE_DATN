package SD94.repository.hoaDon;

import SD94.entity.hoaDon.LichSuHoaDon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LichSuHoaDonRepository extends JpaRepository<LichSuHoaDon, Long> {
    @Query(value = "select * from lich_su_hoa_don where hoa_don_id = ?  group by id", nativeQuery = true)
    List<LichSuHoaDon> findTimeLineByHoaDonID(long id_hoa_don);

    @Query(value = "select * from lich_su_hoa_don where hoa_don_id = ?1 and trang_thai_id = ?2 order by id desc limit 1", nativeQuery = true)
    LichSuHoaDon getTimeLine( long id_hoaDon, long trang_thai_id);

    @Modifying
    @Query(value = "delete from lich_su_hoa_don where hoa_don_id = ?", nativeQuery = true)
    void deleteHistoryByIDBill(long hoa_don_id);
}

