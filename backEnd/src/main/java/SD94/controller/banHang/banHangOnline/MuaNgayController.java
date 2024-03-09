package SD94.controller.banHang.banHangOnline;

import SD94.dto.HoaDonChiTietDTO;
import SD94.dto.HoaDonDTO;
import SD94.dto.SanPhamDTO;
import SD94.entity.hoaDon.HoaDon;
import SD94.entity.hoaDon.HoaDonChiTiet;
import SD94.entity.khuyenMai.KhuyenMai;
import SD94.entity.sanPham.SanPhamChiTiet;
import SD94.repository.hoaDon.HoaDonChiTietRepository;
import SD94.repository.hoaDon.HoaDonRepository;
import SD94.repository.khuyenMai.KhuyenMaiRepository;
import SD94.repository.sanPham.HinhAnhRepository;
import SD94.service.service.MuaNgayService;

import SD94.validator.DatHangValidate;
import SD94.validator.SanPhamValidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/muaNgay")
public class MuaNgayController {
    @Autowired
    MuaNgayService muaNgayService;

    @Autowired
    HoaDonRepository hoaDonRepository;

    @Autowired
    HoaDonChiTietRepository hoaDonChiTietRepository;

    @Autowired
    HinhAnhRepository hinhAnhRepository;

    @Autowired
    KhuyenMaiRepository khuyenMaiRepository;

    @PostMapping("/check-out")
    public ResponseEntity<?> muaNgayCheckOut(@RequestBody SanPhamDTO dto) {
        ResponseEntity<?> response = SanPhamValidate.checkOut(dto);
        if (!response.getStatusCode().is2xxSuccessful()) {
            return response;
        } else {
//            Long id_hoaDon = muaNgayService.muaNgayCheckOut(dto);
            return muaNgayService.muaNgayCheckOut(dto);
        }
    }

    @GetMapping("/getHoaDon/{id}")
    public ResponseEntity<HoaDon> getHoaDonMuaNgay(@PathVariable("id") long id_HoaDon) {
        HoaDon hoaDon = hoaDonRepository.findByID(id_HoaDon);
        return ResponseEntity.ok(hoaDon);
    }

    @GetMapping("/getHoaDonChiTiet/{id}")
    public ResponseEntity<?> getHoaDonChiTiet(@PathVariable("id") long id_HoaDon) {
        List<HoaDonChiTiet> hoaDonChiTiets = hoaDonChiTietRepository.findByIDBill(id_HoaDon);
        List<HoaDonChiTietDTO> dto = new ArrayList<>();
        for (HoaDonChiTiet hoaDonChiTiet : hoaDonChiTiets) {
            SanPhamChiTiet sanPhamChiTiet = hoaDonChiTiet.getSanPhamChiTiet();
            HoaDon hoaDon = hoaDonChiTiet.getHoaDon();
            String anh_san_pham = hinhAnhRepository.getAnhMacDinh(sanPhamChiTiet.getSanPham().getId(), sanPhamChiTiet.getMauSac().getId());

            HoaDonChiTietDTO hoaDonChiTietDTO = new HoaDonChiTietDTO();
            hoaDonChiTietDTO.setId(hoaDonChiTiet.getId());
            hoaDonChiTietDTO.setIdProduct(sanPhamChiTiet.getSanPham().getId());
            hoaDonChiTietDTO.setIdColor(sanPhamChiTiet.getMauSac().getId());
            hoaDonChiTietDTO.setIdSize(sanPhamChiTiet.getKichCo().getId());
            hoaDonChiTietDTO.setSoLuong(hoaDonChiTiet.getSoLuong());
            hoaDonChiTietDTO.setDonGia(hoaDonChiTiet.getDonGia());
            hoaDonChiTietDTO.setThanhTien(hoaDonChiTiet.getThanhTien());
            hoaDonChiTietDTO.setHoaDon(hoaDon);
            hoaDonChiTietDTO.setSanPhamChiTiet(sanPhamChiTiet);
            hoaDonChiTietDTO.setAnhSanPham(anh_san_pham);

            dto.add(hoaDonChiTietDTO);
        }
        return ResponseEntity.ok().body(dto);
    }

    @GetMapping("/check-out")
    public ResponseEntity<HoaDon> getBill() {
        return muaNgayService.getBill();
    }

    @PostMapping("/add/khuyenMai")
    public ResponseEntity<?> addDiscount(@RequestBody HoaDonDTO hoaDonDTO) {
        return muaNgayService.addDiscount(hoaDonDTO);
    }

    @PostMapping("/datHang")
    public ResponseEntity<?> datHang(@RequestBody HoaDonDTO dto) {
        ResponseEntity<?> response = DatHangValidate.datHang(dto);
        if (!response.getStatusCode().is2xxSuccessful()) {
            return response;
        } else {
            return muaNgayService.datHang(dto);
        }
    }

    @GetMapping("/khuyenMai/list")
    public ResponseEntity<List<KhuyenMai>> listKhuyenMai() {
        List<KhuyenMai> khuyenMais = khuyenMaiRepository.findALl2();
        return ResponseEntity.ok(khuyenMais);
    }
}
