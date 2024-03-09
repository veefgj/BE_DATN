package SD94.convert;

import SD94.dto.SanPhamChiTietDTO;
import SD94.entity.sanPham.SanPhamChiTiet;
import org.springframework.stereotype.Component;

@Component
public class ProductDetailsConvert {

    public SanPhamChiTietDTO toDto(SanPhamChiTiet entity) {
        SanPhamChiTietDTO dto = new SanPhamChiTietDTO();
        dto.setId(entity.getId());
        dto.setSoLuong(entity.getSoLuong());
        dto.setStatus(entity.isTrangThai());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setCreatedby(entity.getCreatedby());
        dto.setLastModifiedDate(entity.getLastModifiedDate());
        dto.setLastModifiedBy(entity.getLastModifiedBy());
        dto.setSanPham(entity.getSanPham());
        dto.setMauSac(entity.getMauSac());
        dto.setSize(entity.getKichCo());
        return dto;
    }

    public SanPhamChiTiet toEntity(SanPhamChiTietDTO dto) {
        SanPhamChiTiet entity = new SanPhamChiTiet();
        entity.setId(dto.getId());
        entity.setSoLuong(dto.getSoLuong());
        entity.setTrangThai(dto.isStatus());
        entity.setCreatedDate(dto.getCreatedDate());
        entity.setCreatedby(dto.getCreatedby());
        entity.setLastModifiedDate(dto.getLastModifiedDate());
        entity.setLastModifiedBy(dto.getLastModifiedBy());
        entity.setSanPham(dto.getSanPham());
        entity.setMauSac(dto.getMauSac());
        entity.setKichCo(dto.getSize());
        return entity;
    }

}