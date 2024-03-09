package SD94.entity.sanPham;

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
@Table(name = "SanPhamChiTiet")
public class SanPhamChiTiet extends Base implements Serializable {

    @Column(name = "soLuong", columnDefinition = "int null")
    private Integer soLuong;

    @Column(name = "trangThai", columnDefinition = "bit")
    private boolean trangThai;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sanPham_id", referencedColumnName = "id")
    private SanPham sanPham;

    @ManyToOne
    @JoinColumn(name = "mauSac_id", referencedColumnName = "id")
    private MauSac mauSac;

    @ManyToOne
    @JoinColumn(name = "kichCo_id", referencedColumnName = "id")
    private KichCo kichCo;

}
