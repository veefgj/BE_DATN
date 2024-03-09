package SD94.convert;

import SD94.dto.NhaSanXuatDTO;
import SD94.entity.sanPham.NhaSanXuat;
import org.springframework.stereotype.Component;

@Component
public class ProducerConvert {

    public NhaSanXuatDTO toDto(NhaSanXuat entity) {
        NhaSanXuatDTO dto = new NhaSanXuatDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setAddress(entity.getDiaChi());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setCreatedby(entity.getCreatedby());
        dto.setLastModifiedDate(entity.getLastModifiedDate());
        dto.setLastModifiedBy(entity.getLastModifiedBy());
        return dto;
    }

    public NhaSanXuat toEntity(NhaSanXuatDTO dto) {
        NhaSanXuat entity = new NhaSanXuat();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setDiaChi(dto.getAddress());
        entity.setCreatedDate(dto.getCreatedDate());
        entity.setCreatedby(dto.getCreatedby());
        entity.setLastModifiedDate(dto.getLastModifiedDate());
        entity.setLastModifiedBy(dto.getLastModifiedBy());
        return entity;
    }

}
