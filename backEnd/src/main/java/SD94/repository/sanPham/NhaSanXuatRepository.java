package SD94.repository.sanPham;

import SD94.entity.sanPham.NhaSanXuat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface NhaSanXuatRepository extends JpaRepository<NhaSanXuat, Long> {

    @Query(value = "SELECT * FROM nha_san_xuat WHERE is_deleted = false ORDER BY id DESC", nativeQuery = true)
    List<NhaSanXuat> findAllnha_san_xuat();

    @Query(value = "select * from nha_san_xuat where id = ? and is_deleted = false", nativeQuery = true)
    NhaSanXuat findByID(Long id);

    @Query(value = "SELECT * FROM nha_san_xuat WHERE is_deleted = false AND (name LIKE %?1% OR maximumvalue LIKE %?1% OR percent_discount LIKE %?1%)", nativeQuery = true)
    List<NhaSanXuat> findnha_san_xuatByAll(String input);

    @Query(value = "SELECT * FROM nha_san_xuat WHERE is_deleted = false AND DATE(started_date) = ?", nativeQuery = true)
    List<NhaSanXuat> findnha_san_xuatByDate(LocalDate ngayTao);

    @Query(value = "select * from nha_san_xuat where is_deleted = false and nha_san_xuat = ?;", nativeQuery = true)
    NhaSanXuat findByName(String name);
}
