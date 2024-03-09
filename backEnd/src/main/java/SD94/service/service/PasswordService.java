package SD94.service.service;

import SD94.dto.password.ChangePasswordDTO;
import SD94.dto.password.FogotPasswordDTO;
import SD94.entity.khachHang.KhachHang;
import SD94.entity.nhanVien.NhanVien;
import SD94.repository.khachHang.KhachHangRepository;
import SD94.repository.nhanVien.NhanVienRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PasswordService {
    @Autowired
    private NhanVienRepository nhanVienRepository;

    @Autowired
    private KhachHangRepository khachHangRepository;

    @Autowired
    private MailService mailService;

    @Autowired
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    //Fogot password
    public char[] sendKey(FogotPasswordDTO fogotPasswordDTO) {
        char[] keys = RandomUtil.randomFull();
        mailService.sendMailRanDom(
                "linhnkph22810@fpt.edu.vn",
                fogotPasswordDTO.getEmail(),
                "Bạn đã yêu cầu đổi mật khẩu !",
                "Vui lòng nhập mã sau  :"
                        + new String(keys));
        return keys;
    }

    public void fogotPassword(FogotPasswordDTO fogotPasswordDTO, String email) {
        NhanVien nhanVien = nhanVienRepository.findByEmail(email);
        KhachHang khachHang = khachHangRepository.findByEmail(email);

        if (nhanVien != null) {
            nhanVien.setMatKhau(passwordEncoder.encode(fogotPasswordDTO.getPassword_new()));
            nhanVienRepository.save(nhanVien);
        } else {
            khachHang.setMatKhau(passwordEncoder.encode(fogotPasswordDTO.getPassword_new()));
            khachHangRepository.save(khachHang);
        }

    }

    //Staff
    public ResponseEntity<?> changePasswordStaff(ChangePasswordDTO changePasswordDTO){
        NhanVien nhanVien = nhanVienRepository.findByEmail(changePasswordDTO.getEmail());
        Map<String, String> respone = new HashMap<>();
        boolean checkPass =  passwordEncoder.matches(changePasswordDTO.getPassword_old(), nhanVien.getPassword());
        if(checkPass){
            nhanVien.setMatKhau(passwordEncoder.encode(changePasswordDTO.getPassword_new()));
            nhanVienRepository.save(nhanVien);
            respone.put("sucess", "Doi mat khau thanh cong");
            return ResponseEntity.ok().body(respone);
        }else {
            respone.put("err", "Mat khau cu khong dung");
            return ResponseEntity.badRequest().body(respone);
        }
    }

    //Customer
    public ResponseEntity<?> changePasswordCustomer(ChangePasswordDTO changePasswordDTO){
        KhachHang khachHang = khachHangRepository.findByEmail(changePasswordDTO.getEmail());
        Map<String, String> respone = new HashMap<>();
        boolean checkPass =  passwordEncoder.matches(changePasswordDTO.getPassword_old(), khachHang.getPassword());
        if(checkPass){
            khachHang.setMatKhau(passwordEncoder.encode(changePasswordDTO.getPassword_new()));
            khachHangRepository.save(khachHang);
            respone.put("sucess", "Doi mat khau thanh cong");
            return ResponseEntity.ok().body(respone);
        }else {
            respone.put("err", "Mat khau cu khong dung");
            return ResponseEntity.badRequest().body(respone);
        }
    }
}
