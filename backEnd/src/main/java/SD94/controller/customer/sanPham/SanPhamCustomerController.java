package SD94.controller.customer.sanPham;

import SD94.dto.*;
import SD94.entity.sanPham.*;
import SD94.repository.sanPham.*;
import SD94.service.service.SanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/customer/sanPham")
public class SanPhamCustomerController {

    @Autowired
    SanPhamRepository sanPhamRepository;

    @Autowired
    SanPhamService sanPhamService;

    @Autowired
    SanPhamChiTietRepository sanPhamChiTietRepository;

    @Autowired
    KichCoRepository kichCoRepository;

    @Autowired
    MauSacRepository mauSacRepository;

    @Autowired
    HinhAnhRepository hinhAnhRepository;

    //Lay danh sach
    @GetMapping("/danhSach")
    public ResponseEntity<?> getSanPhamCustomer() {
        List<SanPham> sanPhamList = sanPhamRepository.findAll();
        List<SanPhamDTO> sanPhamDTOList = new ArrayList<>();

        for (SanPham pham : sanPhamList) {
            SanPhamDTO sanPhamDTO = new SanPhamDTO();
            sanPhamDTO.setLoaiSanPham(pham.getLoaiSanPham());
            sanPhamDTO.setId(pham.getId());
            sanPhamDTO.setNhaSanXuat(pham.getNhaSanXuat());
            sanPhamDTO.setSan_pham_id(pham.getId());
            sanPhamDTO.setTenSanPham(pham.getTenSanPham());
            sanPhamDTO.setGia(pham.getGia());
            sanPhamDTO.setChatLieu(pham.getChatLieu());

            String anhSanPham = hinhAnhRepository.getTenAnhSanPham_HienThiDanhSach(pham.getId());
            sanPhamDTO.setAnh_san_pham(anhSanPham);

            sanPhamDTOList.add(sanPhamDTO);
        }
        return ResponseEntity.ok().body(sanPhamDTOList);
    }

    @GetMapping("/timKiemTheoTen/{search}")
    public ResponseEntity<?> timKiemTheoTen(@PathVariable("search") String search) {
        List<SanPham> list = sanPhamRepository.findByName(search);
        List<SanPhamDTO> sanPhamDTOList = new ArrayList<>();

        for (SanPham pham : list) {
            SanPhamDTO sanPhamDTO = new SanPhamDTO();
            sanPhamDTO.setLoaiSanPham(pham.getLoaiSanPham());
            sanPhamDTO.setId(pham.getId());
            sanPhamDTO.setNhaSanXuat(pham.getNhaSanXuat());
            sanPhamDTO.setSan_pham_id(pham.getId());
            sanPhamDTO.setTenSanPham(pham.getTenSanPham());
            sanPhamDTO.setGia(pham.getGia());
            sanPhamDTO.setChatLieu(pham.getChatLieu());

            String anhSanPham = hinhAnhRepository.getTenAnhSanPham_HienThiDanhSach(pham.getId());
            sanPhamDTO.setAnh_san_pham(anhSanPham);

            sanPhamDTOList.add(sanPhamDTO);
        }
        return ResponseEntity.ok().body(sanPhamDTOList);
    }

    //Loc san pham
    @GetMapping("/loc/gia")
    public ResponseEntity<?> getSanPhamTheoGia(@RequestParam Float gia1, @RequestParam Float gia2) {
        return sanPhamService.getSanPhamTheoGia(gia1, gia2);
    }

    @GetMapping("/loc/loai_san_pham")
    public ResponseEntity<?> getSanPhamTheoLoaiSanPham(@RequestParam long idloaiSanPham) {
        List<SanPham> sanPhams = sanPhamRepository.findByLoaiSanPham(idloaiSanPham);
        List<SanPhamDTO> sanPhamDTOList = new ArrayList<>();

        for (SanPham pham : sanPhams) {
            SanPhamDTO sanPhamDTO = new SanPhamDTO();
            sanPhamDTO.setLoaiSanPham(pham.getLoaiSanPham());
            sanPhamDTO.setId(pham.getId());
            sanPhamDTO.setNhaSanXuat(pham.getNhaSanXuat());
            sanPhamDTO.setSan_pham_id(pham.getId());
            sanPhamDTO.setTenSanPham(pham.getTenSanPham());
            sanPhamDTO.setGia(pham.getGia());
            sanPhamDTO.setChatLieu(pham.getChatLieu());

            String anhSanPham = hinhAnhRepository.getTenAnhSanPham_HienThiDanhSach(pham.getId());
            sanPhamDTO.setAnh_san_pham(anhSanPham);

            sanPhamDTOList.add(sanPhamDTO);
        }
        return ResponseEntity.ok().body(sanPhamDTOList);
    }

    @GetMapping("/loc/chat_lieu")
    public ResponseEntity<?> getSanPhamTheoChatLieu(@RequestParam long id_chat_lieu) {
        List<SanPham> sanPhams = sanPhamRepository.findByChatLieu(id_chat_lieu);
        List<SanPhamDTO> sanPhamDTOList = new ArrayList<>();

        for (SanPham pham : sanPhams) {
            SanPhamDTO sanPhamDTO = new SanPhamDTO();
            sanPhamDTO.setLoaiSanPham(pham.getLoaiSanPham());
            sanPhamDTO.setId(pham.getId());
            sanPhamDTO.setNhaSanXuat(pham.getNhaSanXuat());
            sanPhamDTO.setSan_pham_id(pham.getId());
            sanPhamDTO.setTenSanPham(pham.getTenSanPham());
            sanPhamDTO.setGia(pham.getGia());
            sanPhamDTO.setChatLieu(pham.getChatLieu());

            String anhSanPham = hinhAnhRepository.getTenAnhSanPham_HienThiDanhSach(pham.getId());
            sanPhamDTO.setAnh_san_pham(anhSanPham);

            sanPhamDTOList.add(sanPhamDTO);
        }
        return ResponseEntity.ok().body(sanPhamDTOList);
    }

    @GetMapping("/loc/nha_san_xuat")
    public ResponseEntity<?> getSanPhamTheoNSX(@RequestParam long id_nsx) {
        List<SanPham> sanPhams = sanPhamRepository.findByNSX(id_nsx);
        List<SanPhamDTO> sanPhamDTOList = new ArrayList<>();

        for (SanPham pham : sanPhams) {
            SanPhamDTO sanPhamDTO = new SanPhamDTO();
            sanPhamDTO.setLoaiSanPham(pham.getLoaiSanPham());
            sanPhamDTO.setId(pham.getId());
            sanPhamDTO.setNhaSanXuat(pham.getNhaSanXuat());
            sanPhamDTO.setSan_pham_id(pham.getId());
            sanPhamDTO.setTenSanPham(pham.getTenSanPham());
            sanPhamDTO.setGia(pham.getGia());
            sanPhamDTO.setChatLieu(pham.getChatLieu());

            String anhSanPham = hinhAnhRepository.getTenAnhSanPham_HienThiDanhSach(pham.getId());
            sanPhamDTO.setAnh_san_pham(anhSanPham);

            sanPhamDTOList.add(sanPhamDTO);
        }
        return ResponseEntity.ok().body(sanPhamDTOList);
    }

    @GetMapping("/loc/mau_sac")
    public ResponseEntity<?> getSanPhamTheoMauSac(@RequestParam long mauSac_id) {
        List<SanPham> sanPhams = sanPhamRepository.findByMau(mauSac_id);
        List<SanPhamDTO> sanPhamDTOList = new ArrayList<>();

        for (SanPham pham : sanPhams) {
            SanPhamDTO sanPhamDTO = new SanPhamDTO();
            sanPhamDTO.setLoaiSanPham(pham.getLoaiSanPham());
            sanPhamDTO.setId(pham.getId());
            sanPhamDTO.setNhaSanXuat(pham.getNhaSanXuat());
            sanPhamDTO.setSan_pham_id(pham.getId());
            sanPhamDTO.setTenSanPham(pham.getTenSanPham());
            sanPhamDTO.setGia(pham.getGia());
            sanPhamDTO.setChatLieu(pham.getChatLieu());

            String anhSanPham = hinhAnhRepository.getTenAnhSanPham_HienThiDanhSach(pham.getId());
            sanPhamDTO.setAnh_san_pham(anhSanPham);

            sanPhamDTOList.add(sanPhamDTO);
        }
        return ResponseEntity.ok().body(sanPhamDTOList);
    }

    @GetMapping("/loc/kich_co")
    public ResponseEntity<?> getSanPhamTheoKichCo(@RequestParam long kichCo_id) {
        List<SanPham> sanPhams = sanPhamRepository.findByKichCo(kichCo_id);
        List<SanPhamDTO> sanPhamDTOList = new ArrayList<>();

        for (SanPham pham : sanPhams) {
            SanPhamDTO sanPhamDTO = new SanPhamDTO();
            sanPhamDTO.setLoaiSanPham(pham.getLoaiSanPham());
            sanPhamDTO.setId(pham.getId());
            sanPhamDTO.setNhaSanXuat(pham.getNhaSanXuat());
            sanPhamDTO.setSan_pham_id(pham.getId());
            sanPhamDTO.setTenSanPham(pham.getTenSanPham());
            sanPhamDTO.setGia(pham.getGia());
            sanPhamDTO.setChatLieu(pham.getChatLieu());

            String anhSanPham = hinhAnhRepository.getTenAnhSanPham_HienThiDanhSach(pham.getId());
            sanPhamDTO.setAnh_san_pham(anhSanPham);

            sanPhamDTOList.add(sanPhamDTO);
        }
        return ResponseEntity.ok().body(sanPhamDTOList);
    }

    //Chuc nang
    //Select san pham chi tiet theo san pham
    @GetMapping("/getSanPham/id={id}")
    public SanPham getKichCoVaMauSac(@PathVariable("id") long id_sanPham) {
        SanPham sanPham = sanPhamRepository.findByID(id_sanPham);
        return sanPham;
    }

    @RequestMapping("/api/getSize/{id}")
    public ResponseEntity<List<String>> getKichCo(@PathVariable("id") String id) {
        Long id_product = Long.valueOf(id);
        List<String> productSizes = kichCoRepository.getKichCo(id_product);
        return ResponseEntity.ok().body(productSizes);
    }

    @RequestMapping("/api/getColor/{id}")
    public ResponseEntity<List<String>> getMauSac(@PathVariable("id") String id) {
        Long id_product = Long.valueOf(id);
        List<String> productColor = mauSacRepository.getColor(id_product);
        return ResponseEntity.ok().body(productColor);
    }

    @PostMapping("/api/getSoLuong")
    public ResponseEntity<Integer> getSoLuong(@RequestBody SPCTDTO dto) {
        MauSac mauSac = mauSacRepository.findByMaMauSac(dto.getMaMauSac());
        KichCo kichCo = kichCoRepository.findByKichCo(dto.getKichCo());
        Integer soLuong = sanPhamChiTietRepository.getSoLuongHienCp(mauSac.getId(), kichCo.getId(), dto.getSanPhamId());
        return ResponseEntity.ok().body(soLuong);
    }

    @GetMapping("/getAnhMacDinhSanPham/{id}")
    public ResponseEntity<?> getAnhMacDinhSanPham(@PathVariable("id") long id_sanPham) {
        String hinhAnhs = hinhAnhRepository.getTenAnhSanPham_HienThiDanhSach(id_sanPham);
        Map<String, String> respone = new HashMap<>();
        respone.put("anhMacDinh", hinhAnhs);
        return ResponseEntity.ok().body(respone);
    }

    @PostMapping("/getAnhByMauSac")
    public ResponseEntity<?> getAnhMacDinhSanPham(@RequestBody HinhAnhDTO hinhAnhDTO) {
        MauSac mauSac = mauSacRepository.findByMaMauSac(hinhAnhDTO.getMaMauSac());
        String hinhAnhs = hinhAnhRepository.getAnhSPByMauSacAndSPID(hinhAnhDTO.getId_SP(), mauSac.getId());
        Map<String, String> respone = new HashMap<>();
        respone.put("anh", hinhAnhs);
        return ResponseEntity.ok().body(respone);
    }

    @GetMapping("/getAnhSanPham/{id}")
    public ResponseEntity<?> getAnhMacDinh(@PathVariable("id") long id_sanPham) {
        List<HinhAnh> hinhAnhs = hinhAnhRepository.getHinhAnhByProductID(id_sanPham);
        return ResponseEntity.ok().body(hinhAnhs);
    }
}
