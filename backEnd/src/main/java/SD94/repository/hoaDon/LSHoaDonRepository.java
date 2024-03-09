package SD94.repository.hoaDon;

import SD94.entity.hoaDon.LSHoaDon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LSHoaDonRepository extends JpaRepository<LSHoaDon, Long> {
    @Query(value = "select * from ls_hoa_don where hoa_don_id = ?", nativeQuery = true)
    List<LSHoaDon> getLSHD(long hoa_don_id);
}
