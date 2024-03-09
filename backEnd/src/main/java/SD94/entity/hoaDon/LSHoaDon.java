package SD94.entity.hoaDon;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "ls_hoa_don")
public class LSHoaDon {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id ;

    @Column(name="ngay_tao",updatable=false)
    @CreatedDate
    private Date ngayTao;

    @ManyToOne
    @JoinColumn(name = "hoa_don_id")
    private HoaDon hoaDon;

    @Column(name = "nguoi_thao_tac", columnDefinition = "nvarchar(255) null")
    private String nguoiThaoTac;

    @Column(name = "thao_tac", columnDefinition = "nvarchar(255) null")
    private String thaoTac;
}
