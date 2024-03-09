package SD94.controller.admin.hoaDon.datHang;

import SD94.dto.HoaDonDTO;
import SD94.entity.hoaDon.HoaDon;
import SD94.entity.nhanVien.NhanVien;
import SD94.repository.hoaDon.HoaDonRepository;
import SD94.repository.nhanVien.NhanVienRepository;
import SD94.service.service.HoaDonDatHangService;
import SD94.service.service.InHoaDonService;
import SD94.service.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping("/hoaDon/datHang/choXacNhan")
public class ChoXacNhanController {
    @Autowired
    HoaDonDatHangService hoaDonDatHangService;

    @Autowired
    NhanVienRepository nhanVienRepository;

    @Autowired
    InHoaDonService inHoaDonService;

    @Autowired
    MailService mailService;

    @Autowired
    HoaDonRepository hoaDonRepository;

    @GetMapping("/danhSach")
    public List<HoaDon> listBill1() {

        return hoaDonDatHangService.findHoaDonByTrangThai(1L);
    }

    @PostMapping("/capNhatTrangThai/daXacNhan")
    public List<HoaDon> updateStatus2(@RequestBody HoaDonDTO hoaDonDTO) {
        Long id = hoaDonDTO.getId();
        String email = hoaDonDTO.getEmail_user();
        NhanVien nhanVien = nhanVienRepository.findByEmail(email);
        hoaDonDatHangService.capNhatTrangThai(2, id);
        hoaDonDatHangService.createTimeLine("Xác nhận đơn", 2, id, nhanVien.getHoTen());
        return hoaDonDatHangService.findHoaDonByTrangThai(1);
    }

    @PostMapping("/capNhatTrangThai/huyDon")
    public ResponseEntity<Map<String, Boolean>> updateStatus5(@RequestBody HoaDonDTO hoaDonDTO) {
        Long id = hoaDonDTO.getId();
        String ghiChu = hoaDonDTO.getGhiChu();
        String email = hoaDonDTO.getEmail_user();
        NhanVien nhanVien = nhanVienRepository.findByEmail(email);
        hoaDonDatHangService.capNhatTrangThaiHuyDon(5, id, ghiChu);
        hoaDonDatHangService.createTimeLine("Huỷ đơn", 5, id, nhanVien.getHoTen());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/xacNhanDon/tatCa")
    public ResponseEntity<Map<String, Boolean>> updateStatusAll2(@RequestBody HoaDonDTO hoaDonDTO) {
        String email = hoaDonDTO.getEmail_user();
        NhanVien nhanVien = nhanVienRepository.findByEmail(email);
        hoaDonDatHangService.capNhatTrangThai_TatCa(1, 2, "Xác nhận đơn", nhanVien.getHoTen());
        return ResponseEntity.ok().build();
    }

    @PutMapping("/xacNhanDon/daChon")
    public List<HoaDon> updateStatusSelect2(@RequestBody HoaDonDTO hoaDonDTO) {
        String email = hoaDonDTO.getEmail_user();
        NhanVien nhanVien = nhanVienRepository.findByEmail(email);
        return hoaDonDatHangService.capNhatTrangThai_DaChon(hoaDonDTO, 2, "Xác nhận đơn", nhanVien.getHoTen());
    }

    @PutMapping("/huyDon/daChon")
    public List<HoaDon> updateStatusSelect5(@RequestBody HoaDonDTO hoaDonDTO) {
        String email = hoaDonDTO.getEmail_user();
        String ghiChu = hoaDonDTO.getGhiChu();
        NhanVien nhanVien = nhanVienRepository.findByEmail(email);
        return hoaDonDatHangService.capNhatTrangThaiHuy_DaChon(hoaDonDTO, nhanVien.getHoTen(), ghiChu);
    }

    @RequestMapping("/timKiem={search}")
    public List<HoaDon> searchAllBill1(@PathVariable("search") String search) {
        return hoaDonDatHangService.searchAllBill(1, search);
    }

    @RequestMapping("/timKiemNgay={searchDate}")
    public List<HoaDon> searchDateBill1(@PathVariable("searchDate") String searchDate) {
        return hoaDonDatHangService.searchDateBill(1, searchDate);
    }

    @GetMapping("/inHoaDon/{id}")
    public ResponseEntity<byte[]> inHoaDon(@PathVariable("id") long id) {
        return inHoaDonService.hoaDonDatHangPdf(id);
    }

    @GetMapping("/guiMail/{id}")
    public void guiMail(@PathVariable("id") long id) {
        HoaDon hoaDon = hoaDonRepository.findByID(id);
        try {
            mailService.guiMailKhiThaoTac(hoaDon.getEmailNguoiNhan(), hoaDon);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}
