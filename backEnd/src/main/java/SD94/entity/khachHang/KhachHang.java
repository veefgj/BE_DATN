package SD94.entity.khachHang;

import javax.persistence.*;

import SD94.entity.Base;
import SD94.entity.security.Authority;
import SD94.entity.security.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "khachHang")
@Entity
public class KhachHang extends Base implements UserDetails {
    @Column(name = "hoTen", columnDefinition = "nvarchar(50) not null")
    private String hoTen;

    @Column(name = "soDienThoai", columnDefinition = "nvarchar(10) null")
    private String soDienThoai;

    @Column(name = "email", columnDefinition = "nvarchar(200) null")
    private String email;

    @Column(name = "ngaySinh", columnDefinition = "Date null")
    private Date ngaySinh;

    @Column(name = "diaChi", columnDefinition = "nvarchar(250) null")
    private String diaChi;

    @Column(name = "matKhau", columnDefinition = "nvarchar(250) null")
    private String matKhau;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "khachHang")
    @JsonIgnore
    private Set<UserRole> userRoles = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<Authority> set = new HashSet<>();
        this.userRoles.forEach(userRole -> {
            set.add(new Authority(userRole.getRole().getRoleName()));
        });
        return set;
    }

    @Override
    public String getPassword() {
        return this.matKhau;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
