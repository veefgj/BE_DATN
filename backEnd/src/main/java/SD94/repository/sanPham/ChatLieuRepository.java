package SD94.repository.sanPham;

import SD94.entity.sanPham.ChatLieu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ChatLieuRepository extends JpaRepository<ChatLieu, Long> {
    @SuppressWarnings("null")
    @Query(value = "SELECT * FROM chat_lieu WHERE is_deleted = false ORDER BY id DESC", nativeQuery = true)
    List<ChatLieu> findAll();

    @Query(value = "select * from chat_lieu where id = ?", nativeQuery = true)
    ChatLieu findByID(long id);

    @Query(value = "select * from chat_lieu where id = ? and is_deleted = false", nativeQuery = true)
    ChatLieu findByID(Long id);

    @Query(value = "SELECT * FROM chat_lieu WHERE is_deleted = false AND DATE(started_date) = ?", nativeQuery = true)
    List<ChatLieu> findByDate(LocalDate ngayTao);

    @Query(value = "select * from chat_lieu where chat_lieu = ? and  is_deleted = false;", nativeQuery = true)
    ChatLieu findByName(String name);
}
