package SD94;

import SD94.entity.nhanVien.NhanVien;
import SD94.entity.security.Role;
import SD94.entity.security.UserRole;
import SD94.repository.nhanVien.NhanVienRepository;
import SD94.repository.role.RoleRepository;
import SD94.service.service.NhanVienService;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.HashSet;
import java.util.Set;

@EnableWebMvc
@SpringBootApplication
public class SD94Application implements CommandLineRunner {
    @Autowired
    private NhanVienService nhanVienService;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    NhanVienRepository nhanVienRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.configure().load();
        String databaseUrl = dotenv.get("DB_URL");
        String databaseUsername = dotenv.get("USER_DB");
        String databasePassword = dotenv.get("PASSWORD_DB");

        System.setProperty("spring.datasource.url", databaseUrl);
        System.setProperty("spring.datasource.username", databaseUsername);
        System.setProperty("spring.datasource.password", databasePassword);
        SpringApplication.run(SD94Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        if (roleRepository.find("ADMIN") == null) {
            Role role = new Role();
            role.setRoleName("ADMIN");
            roleRepository.save(role);
        }

        if (roleRepository.find("STAFF") == null) {
            Role role = new Role();
            role.setRoleName("STAFF");
            roleRepository.save(role);
        }

        if (roleRepository.find("CUSTOMER") == null) {
            Role role = new Role();
            role.setRoleName("CUSTOMER");
            roleRepository.save(role);
        }

        NhanVien nhanVien = null;
        nhanVien = nhanVienRepository.findByEmail("admin@gmail.com");
        if (nhanVien != null) {
            // String account = nhanVien.getEmail();
        } else {
            nhanVien = new NhanVien();
            nhanVien.setDiaChi("Hà Nội");
            nhanVien.setGioiTinh(true);
            nhanVien.setMatKhau(this.bCryptPasswordEncoder.encode("123123"));
            nhanVien.setEmail("admin@gmail.com");
            nhanVien.setHoTen("SD94");

            Role rolee = roleRepository.find("ADMIN");

            Set<UserRole> userRoleSet = new HashSet<>();
            UserRole userRole = new UserRole();
            userRole.setRole(rolee);
            userRole.setStaff(nhanVien);
            userRoleSet.add(userRole);

            nhanVienService.createStaffV1(nhanVien, userRoleSet);
        }
    }
}