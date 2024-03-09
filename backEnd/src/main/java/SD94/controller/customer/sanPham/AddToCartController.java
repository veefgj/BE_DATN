package SD94.controller.customer.sanPham;

import SD94.dto.GioHangDTO;
import SD94.entity.gioHang.GioHang;
import SD94.entity.gioHang.GioHangChiTiet;
import SD94.entity.khachHang.KhachHang;
import SD94.entity.sanPham.KichCo;
import SD94.entity.sanPham.MauSac;
import SD94.entity.sanPham.SanPham;
import SD94.entity.sanPham.SanPhamChiTiet;
import SD94.repository.gioHang.GioHangChiTietRepository;
import SD94.repository.gioHang.GioHangRepository;
import SD94.repository.khachHang.KhachHangRepository;
import SD94.repository.sanPham.KichCoRepository;
import SD94.repository.sanPham.MauSacRepository;
import SD94.repository.sanPham.SanPhamChiTietRepository;
import SD94.repository.sanPham.SanPhamRepository;
import SD94.validator.GioHangValidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/customer/cart")
public class AddToCartController {

    @Autowired
    KichCoRepository kichCoRepository;

    @Autowired
    MauSacRepository mauSacRepository;

    @Autowired
    SanPhamRepository sanPhamRepository;

    @Autowired
    KhachHangRepository khachHangRepository;

    @Autowired
    GioHangRepository gioHangRepository;

    @Autowired
    GioHangChiTietRepository gioHangChiTietRepository;

    @Autowired
    SanPhamChiTietRepository sanPhamChiTietRepository;

    @PostMapping("/addToCart")
    public ResponseEntity<?> addToCart(@RequestBody GioHangDTO dto) {
        ResponseEntity<?> response = GioHangValidate.addToCartCheck(dto);
        if (!response.getStatusCode().is2xxSuccessful()) {
            return response;
        } else {
            MauSac mauSac = mauSacRepository.findByMaMauSac(dto.getMaMauSac());
            KichCo kichCo = kichCoRepository.findByKichCo(dto.getKichCo());
            SanPhamChiTiet sanPhamChiTiet = sanPhamChiTietRepository.getSanPhamChiTiet(mauSac.getId(), kichCo.getId(),
                    dto.getSan_pham_id());
            if (sanPhamChiTiet.isTrangThai() == true) {

                KhachHang khachHang = khachHangRepository.findByEmail(dto.getEmail());
                SanPham sanPham = sanPhamRepository.findByID(dto.getSan_pham_id());
                GioHang gioHang = gioHangRepository.findbyCustomerID(khachHang.getId());
                Optional<GioHangChiTiet> optionalGioHangChiTiet = gioHangChiTietRepository
                        .checkGioHangChiTiet(sanPhamChiTiet.getId(), gioHang.getId());

                if (optionalGioHangChiTiet.isPresent()) {
                    GioHangChiTiet gioHangChiTiet = optionalGioHangChiTiet.get();
                    int soLuongDuocThemTiep = sanPhamChiTiet.getSoLuong() - gioHangChiTiet.getSoLuong();
                    int check = soLuongDuocThemTiep - dto.getSoLuong();
                    if (gioHangChiTiet.getSoLuong() == sanPhamChiTiet.getSoLuong()) {
                        Map<String, String> respone = new HashMap<>();
                        respone.put("err", "Bạn đã có " + sanPhamChiTiet.getSoLuong()
                                + " sản phẩm này trong giỏ hàng, bạn không thể thêm tiếp vì vượt quá số lượng của sản phẩm");
                        return ResponseEntity.badRequest().body(respone);
                    } else if (check < 0) {
                        Map<String, String> respone = new HashMap<>();
                        respone.put("err",
                                "Bạn đã có " + gioHangChiTiet.getSoLuong()
                                        + " sản phẩm này trong giỏ hàng, bạn chỉ có thể thêm tiếp được tối đa "
                                        + soLuongDuocThemTiep + " sản phẩm này");
                        return ResponseEntity.badRequest().body(respone);
                    } else {
                        int soLuongMoi = gioHangChiTiet.getSoLuong() + dto.getSoLuong();
                        float thanhTienMoi = sanPham.getGia() * soLuongMoi;
                        gioHangChiTiet.setSoLuong(soLuongMoi);
                        gioHangChiTiet.setThanhTien(BigDecimal.valueOf(thanhTienMoi));
                        gioHangChiTietRepository.save(gioHangChiTiet);
                    }
                } else {
                    float thanhTien = dto.getSoLuong() * sanPham.getGia();
                    GioHangChiTiet gioHangChiTiet = new GioHangChiTiet();
                    gioHangChiTiet.setGioHang(gioHang);
                    gioHangChiTiet.setSanPhamChiTiet(sanPhamChiTiet);
                    gioHangChiTiet.setDonGia(dto.getDonGia());
                    gioHangChiTiet.setSoLuong(dto.getSoLuong());
                    gioHangChiTiet.setThanhTien(BigDecimal.valueOf(thanhTien));
                    gioHangChiTietRepository.save(gioHangChiTiet);
                }
                Map<String, String> respone = new HashMap<>();
                respone.put("done", "Thêm sản phẩm vào giỏ hàng thành công");
                return ResponseEntity.ok().body(respone);
            } else {
                Map<String, String> respone = new HashMap<>();
                respone.put("err", "Chúng tôi đã ngừng kinh doanh sản phẩm này");
                return ResponseEntity.ok().body(respone);
            }
        }
    }
}
