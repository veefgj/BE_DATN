package SD94.entity.sanPham;

import SD94.entity.Base;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@EqualsAndHashCode(callSuper = false)
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "ChatLieu")
public class ChatLieu extends Base implements Serializable {

    @Column(name = "chatLieu", columnDefinition = "nvarchar(256) null")
    private String chatLieu;

}
