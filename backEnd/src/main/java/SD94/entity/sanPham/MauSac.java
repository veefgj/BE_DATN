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
@Table(name = "MauSac")
public class MauSac extends Base implements Serializable {

    @Column(name = "maMauSac", columnDefinition = "nvarchar(256) not null unique")
    private String maMauSac;

    @Column(name = "tenMauSac", columnDefinition = "nvarchar(256) null")
    private String tenMauSac;
}