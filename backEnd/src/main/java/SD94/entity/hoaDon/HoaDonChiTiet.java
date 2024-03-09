package SD94.entity.hoaDon;

import SD94.entity.Base;
import SD94.entity.sanPham.SanPhamChiTiet;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "HoaDonChiTiet")
public class HoaDonChiTiet extends Base implements Serializable {

    @Column(name = "soLuong", columnDefinition = "int null")
    private int soLuong;

    @Column(name = "donGia", columnDefinition = "int null")
    private int donGia;

    @Column(name = "thanhTien", columnDefinition = "int null")
    private int thanhTien;

    @ManyToOne
    @JoinColumn(name = "hoaDon_id", referencedColumnName = "id")
    private HoaDon hoaDon;

    @ManyToOne
    @JoinColumn(name = "sanPhamChiTiet_id", referencedColumnName = "id")
    private SanPhamChiTiet sanPhamChiTiet;

}
