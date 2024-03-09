package SD94.convert;

import SD94.dto.SanPhamDTO;
import SD94.entity.sanPham.SanPham;
import org.springframework.stereotype.Component;

@Component
public class ProductConvert {

    public SanPhamDTO toDto(SanPham entity) {
        SanPhamDTO dto = new SanPhamDTO();
        dto.setId(entity.getId());
        dto.setTenSanPham(entity.getTenSanPham());
        dto.setGia(entity.getGia());
        dto.setTrangThai(entity.getTrangThai());
        dto.setNgayTao(entity.getCreatedDate());
        dto.setNguoiTao(entity.getCreatedby());
        dto.setChatLieu(entity.getChatLieu());
        dto.setLoaiSanPham(entity.getLoaiSanPham());
        dto.setNhaSanXuat(entity.getNhaSanXuat());
        return dto;
    }

    public SanPham toEntity(SanPhamDTO dto) {
        SanPham entity = new SanPham();
        entity.setId(dto.getId());
        entity.setTenSanPham(dto.getTenSanPham());
        entity.setGia(dto.getGia());
        entity.setTrangThai(dto.getTrangThai());
        entity.setCreatedDate(dto.getNgayTao());
        entity.setCreatedby(dto.getNguoiTao());
        entity.setChatLieu(dto.getChatLieu());
        entity.setLoaiSanPham(dto.getLoaiSanPham());
        entity.setNhaSanXuat(dto.getNhaSanXuat());
        return entity;
    }
}
