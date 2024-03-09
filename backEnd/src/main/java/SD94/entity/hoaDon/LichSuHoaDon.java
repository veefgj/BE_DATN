package SD94.entity.hoaDon;

import SD94.entity.Base;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "LichSuHoaDon")
public class LichSuHoaDon extends Base implements Serializable {

    @Column(name = "thaoTac", columnDefinition = "nvarchar(256) not null")
    private String thaoTac;

    @ManyToOne
    @JoinColumn(name = "trangThai_id", referencedColumnName = "id")
    private TrangThai trangThai;

    @Column(name = "nguoiThaoTac", columnDefinition = "nvarchar(256)  null")
    private String nguoiThaoTac;

    @ManyToOne
    @JoinColumn(name = "hoaDon_id", referencedColumnName = "id")
    private HoaDon hoaDon;

}
