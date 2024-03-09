package SD94.entity.gioHang;

import SD94.entity.Base;
import SD94.entity.sanPham.SanPhamChiTiet;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;

@Entity
@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "GioHangChiTiet")
public class GioHangChiTiet extends Base implements Serializable {

    @ManyToOne
    @JoinColumn(name = "gioHang_id")
    private GioHang gioHang;

    @ManyToOne
    @JoinColumn(name = "sanPhamChiTiet_id")
    private SanPhamChiTiet sanPhamChiTiet;

    @Column(name = "soLuong", columnDefinition = "int null")
    private int soLuong;

    @Column(name = "donGia", columnDefinition = "int ")
    private int donGia;

    @Column(name = "thanhTien", columnDefinition = "varchar(50) not null")
    private BigDecimal thanhTien;
}
