package SD94.convert;

import SD94.dto.MauSacDTO;
import SD94.entity.sanPham.MauSac;
import org.springframework.stereotype.Component;

@Component
public class ProductColorConvert {

    public MauSacDTO toDto(MauSac entity) {
        MauSacDTO dto = new MauSacDTO();
        dto.setId(entity.getId());
        dto.setMaMauSac(entity.getMaMauSac());
        return dto;
    }

    public MauSac toEntity(MauSacDTO dto) {
        MauSac entity = new MauSac();
        entity.setId(dto.getId());
        entity.setMaMauSac(dto.getMaMauSac());
        return entity;
    }
}
