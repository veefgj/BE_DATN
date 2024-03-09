package SD94.controller.banHang.taiQuay;

import SD94.dto.*;
import SD94.entity.hoaDon.HoaDon;
import SD94.entity.hoaDon.HoaDonChiTiet;
import SD94.entity.khachHang.KhachHang;
import SD94.entity.khuyenMai.KhuyenMai;
import SD94.entity.sanPham.SanPhamChiTiet;
import SD94.repository.hoaDon.HoaDonChiTietRepository;
import SD94.repository.hoaDon.HoaDonRepository;
import SD94.repository.khachHang.KhachHangRepository;
import SD94.repository.khuyenMai.KhuyenMaiRepository;
import SD94.repository.sanPham.HinhAnhRepository;
import SD94.service.service.BanHangTaiQuayService;
import SD94.service.service.InHoaDonService;
import SD94.validator.TaiQuayValidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/banHang/taiQuay")
public class BanHangTaiQuayController {
    @Autowired
    BanHangTaiQuayService banHangTaiQuayService;

    @Autowired
    InHoaDonService inHoaDonService;

    @Autowired
    HoaDonRepository hoaDonRepository;

    @Autowired
    HoaDonChiTietRepository hoaDonChiTietRepository;

    @Autowired
    KhachHangRepository khachHangRepository;

    @Autowired
    KhuyenMaiRepository khuyenMaiRepository;

    @Autowired
    HinhAnhRepository hinhAnhRepository;

    @GetMapping("/danhSachHoaDon")
    public List<HoaDon> danhSachHoaDonCho() {
        List<HoaDon> hoaDonList = hoaDonRepository.getDanhSachHoaDonCho();
        return hoaDonList;
    }

    @GetMapping("/getHoaDonChitiet/{id}")
    public ResponseEntity<?> getHoaDonChiTiet(@PathVariable("id") long id) {
        List<HoaDonChiTiet> hoaDonChiTiets = hoaDonChiTietRepository.findByIDBill(id);
        List<HoaDonChiTietDTO> dto = new ArrayList<>();
        for (HoaDonChiTiet hoaDonChiTiet : hoaDonChiTiets) {
            SanPhamChiTiet sanPhamChiTiet = hoaDonChiTiet.getSanPhamChiTiet();
            HoaDon hoaDon = hoaDonChiTiet.getHoaDon();
            String anh_san_pham = hinhAnhRepository.getAnhMacDinh(sanPhamChiTiet.getSanPham().getId(),
                    sanPhamChiTiet.getMauSac().getId());

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

    @PostMapping("/taoHoaDon")
    public ResponseEntity<?> taoHoaDon(@RequestBody HoaDonDTO hoaDonDTO) {
        return banHangTaiQuayService.taoHoaDon(hoaDonDTO);
    }

    @PostMapping("/themSanPham")
    public ResponseEntity<?> themSanPham(@RequestBody SanPhamDTO dto) {
        ResponseEntity<?> response = TaiQuayValidate.Taiquay(dto);
        if (!response.getStatusCode().is2xxSuccessful()) {
            return response;
        } else {
            return banHangTaiQuayService.themSanPham(dto);
        }
    }

    @GetMapping("/getHoaDon/{id}")
    public HoaDon getHoaDon(@PathVariable("id") long id) {
        HoaDon hoaDon = hoaDonRepository.findByID(id);
        return hoaDon;
    }

    @PostMapping("/xoaHoaDon")
    public List<HoaDon> xoaHoaDon(@RequestBody HoaDonDTO hoaDonDTO) {
        return banHangTaiQuayService.xoaHoaDon(hoaDonDTO);
    }

    @GetMapping("/khachHang/list")
    public ResponseEntity<List<KhachHang>> listKhachHang() {
        List<KhachHang> khachHangs = khachHangRepository.findAll();
        return ResponseEntity.ok(khachHangs);
    }

    @GetMapping("/khuyenMai/list")
    public ResponseEntity<List<KhuyenMai>> listKhuyenMai() {
        List<KhuyenMai> khuyenMais = khuyenMaiRepository.findAll();
        return ResponseEntity.ok(khuyenMais);
    }

    @PostMapping("/add/khuyenMai")
    public ResponseEntity<?> themMaGiamGiaTaiQuay(@RequestBody HoaDonDTO hoaDonDTO) {
        return banHangTaiQuayService.themMaGiamGiaTaiQuay(hoaDonDTO);
    }

    @PostMapping("/add/KhachHang")
    public ResponseEntity<?> addKhachHang(@RequestBody KhachHangDTO dto) {
        return banHangTaiQuayService.addKhachHang(dto);
    }

    @PostMapping("/huyDon")
    public ResponseEntity<?> huyDon(@RequestBody HoaDonDTO hoaDonDTO) {
        return banHangTaiQuayService.huyDon(hoaDonDTO);
    }

    @PostMapping("/thanhToan")
    public ResponseEntity<?> thanhToan(@RequestBody HoaDonDTO hoaDonDTO) {
        return banHangTaiQuayService.thanhToan(hoaDonDTO);
    }

    @PostMapping("/inHoaDon")
    public ResponseEntity<?> inHoaDon(@RequestBody HoaDonDTO hoaDonDTO) {
        return inHoaDonService.generatePdf(hoaDonDTO);
    }

    @PostMapping("/inHoaDon/daThanhToan")
    public ResponseEntity<?> inHoaDonDaThanhToan(@RequestBody HoaDonDTO hoaDonDTO) {
        return inHoaDonService.HdDaThanhToanPdf(hoaDonDTO);
    }

    @PostMapping("/xoaHDCT")
    public ResponseEntity<?> xoaHDCT(@RequestBody HoaDonChiTietDTO dto) {
        return banHangTaiQuayService.xoaHDCT(dto);
    }
}
