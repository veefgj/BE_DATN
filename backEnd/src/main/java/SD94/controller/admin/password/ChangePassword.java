package SD94.controller.admin.password;

import SD94.dto.password.ChangePasswordDTO;
import SD94.service.service.PasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/changePass")
public class ChangePassword {
    @Autowired
    PasswordService passwordService;

    @PostMapping("/staff/changePass")
    public ResponseEntity<?> changeStaff(@RequestBody ChangePasswordDTO changePasswordDTO){
        return ResponseEntity.ok().body(passwordService.changePasswordStaff(changePasswordDTO));
    }

    @PostMapping("/customer/changePass")
    public ResponseEntity<?> changeCustomer(@RequestBody ChangePasswordDTO changePasswordDTO){
        return ResponseEntity.ok().body(passwordService.changePasswordCustomer(changePasswordDTO));
    }
}
