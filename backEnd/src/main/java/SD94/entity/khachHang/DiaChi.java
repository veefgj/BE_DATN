package SD94.entity.khachHang;

import SD94.entity.Base;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "diaChi")
@Entity
public class DiaChi extends Base implements Serializable {
    @Column(name = "diaChi", columnDefinition = "nvarchar(500) null")
    private String diaChi;

    @ManyToOne
    @JoinColumn(name = "khachHang_id")
    private KhachHang khachHang;

    @Column(name = "diaChiMacDinh")
    private boolean diaChiMacDinh = false;

}
