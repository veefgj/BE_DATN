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
@Table(name = "LoaiSanPham")
public class LoaiSanPham extends Base implements Serializable {

    @Column(name = "loaiSanPham", columnDefinition = "nvarchar(256) null")
    private String loaiSanPham;
}
