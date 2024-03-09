
package SD94.entity.nhanVien;

import SD94.entity.Base;
import SD94.entity.security.Authority;
import SD94.entity.security.UserRole;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "NhanVien")
public class NhanVien extends Base implements UserDetails {

    @Column(name = "hoTen", columnDefinition = "nvarchar(256) null")
    private String hoTen;

    @Column(name = "soDienThoai", columnDefinition = "nvarchar(50) null")
    private String soDienThoai;

    @Column(name = "email", columnDefinition = "nvarchar(256) not null unique")
    private String email;

    @Column(name = "matKhau", columnDefinition = "nvarchar(256) null ")
    private String matKhau;

    @Column(name = "gioiTinh", columnDefinition = "bit")
    private Boolean gioiTinh;

    @Column(name = "diaChi", columnDefinition = "nvarchar(256) null ")
    private String diaChi;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "ngaySinh", columnDefinition = "Datetime null")
    private Date ngaySinh;

    @Column(name = "trangThai", columnDefinition = "int null")
    private int trangThai;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "staff")
    @JsonIgnore
    private Set<UserRole> userRoles = new HashSet<>();

    @Column(name = "soCanCuoc", columnDefinition = "nvarchar(256)")
    private String soCanCuoc;

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
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

