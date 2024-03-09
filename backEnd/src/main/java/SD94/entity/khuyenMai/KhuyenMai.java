package SD94.entity.khuyenMai;

import SD94.entity.Base;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "KhuyenMai")
public class KhuyenMai extends Base implements Serializable {

    @Column(name = "tenKhuyenMai", columnDefinition = "nvarchar(256) not null unique")
    private String tenKhuyenMai;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "ngayBatDau", columnDefinition = "Datetime null")
    private Date ngayBatDau;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "ngayKetThuc", columnDefinition = "Datetime null")
    private Date ngayKetThuc;

    @Column(name = "phanTramGiam", columnDefinition = "int null")
    private int phanTramGiam;

    @Column(name = "tienGiamToiDa", columnDefinition = "int null")
    private int tienGiamToiDa;

    @Column(name = "trangThai", columnDefinition = "int null")
    private int trangThai;
}
