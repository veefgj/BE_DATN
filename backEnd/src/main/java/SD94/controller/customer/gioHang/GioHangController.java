package SD94.controller.customer.gioHang;

import SD94.dto.GioHangChiTietDTO;
import SD94.entity.gioHang.GioHang;
import SD94.entity.gioHang.GioHangChiTiet;
import SD94.entity.khachHang.KhachHang;
import SD94.entity.sanPham.SanPhamChiTiet;
import SD94.repository.gioHang.GioHangChiTietRepository;
import SD94.repository.gioHang.GioHangRepository;
import SD94.repository.khachHang.KhachHangRepository;
import SD94.repository.sanPham.HinhAnhRepository;
import SD94.repository.sanPham.SanPhamChiTietRepository;
import SD94.service.service.GioHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;

@RestController
@RequestMapping("/gioHang")
public class GioHangController {
    @Autowired
    GioHangRepository cartRepository;

    @Autowired
    SanPhamChiTietRepository productDetailsRepository;

    @Autowired
    GioHangChiTietRepository cartDetailsRepository;

    @Autowired
    KhachHangRepository khachHangRepository;

    @Autowired
    HinhAnhRepository hinhAnhRepository;

    @Autowired
    GioHangService gioHangService;

    @Autowired
    GioHangChiTietRepository gioHangChiTietRepository;

    @RequestMapping("/danhSach/{email}")
    public ResponseEntity<?> listCart(@PathVariable("email") String email) {
        KhachHang khachHang = khachHangRepository.findByEmail(email);
        GioHang gioHang = cartRepository.findbyCustomerID(khachHang.getId());
        long idCart = gioHang.getId();
        List<GioHangChiTietDTO> gioHangChiTietDTOS = new ArrayList<>();
        List<GioHangChiTiet> cartList = cartDetailsRepository.findByCartID(idCart);
        for (GioHangChiTiet gioHangChiTiet : cartList) {

            SanPhamChiTiet sanPhamChiTiet = gioHangChiTiet.getSanPhamChiTiet();
            if (sanPhamChiTiet.getSoLuong() == 0) {
                gioHangChiTiet.setSoLuong(0);
                gioHangChiTiet.setThanhTien(BigDecimal.ZERO);
                gioHangChiTietRepository.save(gioHangChiTiet);
            }
            GioHangChiTietDTO dto = new GioHangChiTietDTO();
            String hinhAnhs = hinhAnhRepository.getAnhMacDinh(sanPhamChiTiet.getSanPham().getId(),
                    sanPhamChiTiet.getMauSac().getId());
            dto.setId(gioHangChiTiet.getId());
            dto.setSanPhamChiTiet(sanPhamChiTiet);
            dto.setSoLuong(gioHangChiTiet.getSoLuong());
            dto.setDonGia(gioHangChiTiet.getDonGia());
            dto.setThanhTien(gioHangChiTiet.getThanhTien());
            dto.setAnh_san_pham(hinhAnhs);

            gioHangChiTietDTOS.add(dto);
        }
        return ResponseEntity.ok().body(gioHangChiTietDTOS);
    }

    @SuppressWarnings("null")
    @PostMapping("/xoa/gioHangChiTiet")
    public List<GioHangChiTiet> deletedCartDetails(@RequestBody GioHangChiTiet request) {
        Long id_cart_details = request.getId();

        Optional<GioHangChiTiet> shoppingCart = cartDetailsRepository.findById(id_cart_details);
        long id_cart = 0;
        if (shoppingCart.isPresent()) {
            GioHangChiTiet cartDetials = shoppingCart.get();
            id_cart = cartDetials.getGioHang().getId();
            cartDetials.setDeleted(true);
            cartDetailsRepository.save(cartDetials);
        }

        List<GioHangChiTiet> cartList = cartDetailsRepository.findByCartID(id_cart);
        return cartList;
    }

    @PostMapping("/update/soLuongGioHangChiTiet")
    public ResponseEntity<?> updateSoLuongGioHangChiTiet(@RequestBody GioHangChiTietDTO dto) {
        return gioHangService.updateSoLuong(dto);
    }
}
