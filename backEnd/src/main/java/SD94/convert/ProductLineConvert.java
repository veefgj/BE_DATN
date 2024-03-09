//package SD94.convert;
//
//import SD94.entity.sanPham.LoaiSanPham;
//import org.springframework.stereotype.Component;
//
//@Component
//public class ProductLineConvert {
//
//    public LoaiSan toDto(LoaiSanPham entity) {
//        ProductLineDTO dto = new ProductLineDTO();
//        dto.setId(entity.getId());
//        dto.setName(entity.getLoaiSanPham());
//        dto.setCreatedDate(entity.getCreatedDate());
//        dto.setCreatedby(entity.getCreatedby());
//        dto.setLastModifiedDate(entity.getLastModifiedDate());
//        dto.setLastModifiedBy(entity.getLastModifiedBy());
//        dto.setDeleted(entity.isDeleted());
//        return dto;
//    }
//
//    public LoaiSanPham toEntity(ProductLineDTO dto) {
//        LoaiSanPham entity = new LoaiSanPham();
//        entity.setId(dto.getId());
//        entity.setLoaiSanPham(dto.getName());
//        entity.setCreatedDate(dto.getCreatedDate());
//        entity.setCreatedby(dto.getCreatedby());
//        entity.setLastModifiedDate(dto.getLastModifiedDate());
//        entity.setLastModifiedBy(dto.getLastModifiedBy());
//        entity.setDeleted(dto.isDeleted());
//        return entity;
//    }
//}
