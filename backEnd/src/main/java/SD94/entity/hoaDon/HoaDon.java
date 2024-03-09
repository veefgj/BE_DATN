package SD94.entity.hoaDon;

import SD94.entity.Base;
import SD94.entity.khachHang.KhachHang;
import SD94.entity.khuyenMai.KhuyenMai;
import SD94.entity.nhanVien.NhanVien;
import lombok.*;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "HoaDon")
public class HoaDon extends Base implements Serializable {

    @Column(name = "maHoaDon", columnDefinition = "nvarchar(256) null")
    private String maHoaDon;

    @Column(name = "ghiChu", columnDefinition = "nvarchar(50) null")
    private String ghiChu;

    @Column(name = "nguoiNhan", columnDefinition = "nvarchar(256) null")
    private String nguoiNhan;

    @Column(name = "emailNguoiNhan", columnDefinition = "nvarchar(256) null")
    private String emailNguoiNhan;

    @Column(name = "SDTNguoiNhan", columnDefinition = "nvarchar(50) null")
    private String SDTNguoiNhan;

    @Column(name = "diaChiGiaoHang", columnDefinition = "nvarchar(256) null")
    private String diaChiGiaoHang;

    @Column(name = "tienShip", columnDefinition = "int null")
    private int tienShip;

    @Column(name = "tienGiam", columnDefinition = "int null")
    private int tienGiam;

    @Column(name = "tongTienDonHang", columnDefinition = "int null")
    private int tongTienDonHang;

    @Column(name = "tongTienHoaDon", columnDefinition = "int null")
    private int tongTienHoaDon;

    @Column(name = "loaiHoaDon", columnDefinition = "int null")
    private int loaiHoaDon;

    @ManyToOne
    @JoinColumn(name = "trangThai_id", referencedColumnName = "id")
    private TrangThai trangThai;

    @ManyToOne
    @JoinColumn(name = "khachHang_id", referencedColumnName = "id")
    private KhachHang khachHang;

    @ManyToOne
    @JoinColumn(name = "nhanVien_id", referencedColumnName = "id")
    private NhanVien nhanVien;

    @ManyToOne
    @JoinColumn(name = "khuyenMai_id", referencedColumnName = "id")
    private KhuyenMai khuyenMai;
}
