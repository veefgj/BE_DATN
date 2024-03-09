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
@Table(name = "SanPham")
public class SanPham extends Base implements Serializable {

    @Column(name = "tenSanPham", columnDefinition = "nvarchar(256) null")
    private String tenSanPham;

    @Column(name = "mota", columnDefinition = "nvarchar(256) null")
    private String moTa;

    @Column(name = "gia", columnDefinition = "nvarchar(256) null")
    private Float gia;

    @Column(name = "trangThai", columnDefinition = "int null")
    private Integer trangThai;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "chatLieu_id", referencedColumnName = "id")
    private ChatLieu chatLieu;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "loaiSanPham_id", referencedColumnName = "id")
    private LoaiSanPham loaiSanPham;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "nhaSanXuat_id", referencedColumnName = "id")
    private NhaSanXuat nhaSanXuat;

}
