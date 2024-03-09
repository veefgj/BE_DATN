package SD94.convert;

import SD94.dto.KichCoDTO;
import SD94.entity.sanPham.KichCo;
import org.springframework.stereotype.Component;

@Component
public class ProductSizeConvert {

    public KichCoDTO toDto(KichCo entity) {
        KichCoDTO dto = new KichCoDTO();
        dto.setId(entity.getId());
        dto.setKichCo(entity.getKichCo());
        return dto;
    }

    public KichCo toEntity(KichCoDTO dto) {
        KichCo entity = new KichCo();
        entity.setId(dto.getId());
        entity.setKichCo(dto.getKichCo());
        return entity;
    }
}
