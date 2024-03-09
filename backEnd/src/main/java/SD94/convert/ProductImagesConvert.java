//package SD94.convert;
//
//import SD94.dto.HinhAnhDTO;
//import SD94.entity.sanPham.HinhAnh;
//import org.springframework.stereotype.Component;
//
//@Component
//public class ProductImagesConvert {
//
//    public HinhAnhDTO toDto(HinhAnh entity) {
//        HinhAnhDTO dto = new HinhAnhDTO();
//        dto.setId(entity.getId());
//        dto.setName(entity.getTenAnh());
////        dto.setImg(entity.getImg());
//        dto.setSanPhamChiTiet(entity.getSanPhamChiTiet());
//        dto.setMauSac(entity.getMauSac());
//        return dto;
//    }
//
//    public HinhAnh toEntity(HinhAnhDTO dto) {
//        HinhAnh entity = new HinhAnh();
//        entity.setId(dto.getId());
//        entity.setTenAnh(dto.getName());
////        entity.setImg(dto.getImg());
//        entity.setSanPhamChiTiet(dto.getSanPhamChiTiet());
//        entity.setMauSac(dto.getMauSac());
//        return entity;
//    }
//}
