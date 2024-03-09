package SD94.service.service;

import SD94.entity.nhanVien.NhanVien;
import SD94.entity.security.UserRole;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public interface NhanVienService {
    List<NhanVien>findAllStaff();

    ResponseEntity<NhanVien> createStaff(NhanVien staffCreate);

    ResponseEntity<NhanVien> editStaff(NhanVien staffEdit);

    ResponseEntity<List<NhanVien>> deleteStaff(Long id);

    List<NhanVien> searchAllStaff(String search);

    List<NhanVien> searchDateStaff(String searchDate);

    public NhanVien createStaffV1(NhanVien user, Set<UserRole> userRoles) throws Exception;

    NhanVien getStaff(String email);

    void deleteUser(Long userId);
}
