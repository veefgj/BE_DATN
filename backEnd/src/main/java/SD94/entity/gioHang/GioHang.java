package SD94.entity.gioHang;

import SD94.entity.Base;
import SD94.entity.khachHang.KhachHang;
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
@Table(name = "GioHang")
public class GioHang extends Base implements Serializable {
    @OneToOne
    @JoinColumn(name = "khachHang_id")
    private KhachHang khachHang;

    @Column(name = "trangThai", columnDefinition = "int null")
    private int trangThai;
}
