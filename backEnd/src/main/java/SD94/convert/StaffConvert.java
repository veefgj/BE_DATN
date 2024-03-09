package SD94.convert;

import SD94.dto.NhanVienDTO;
import SD94.entity.nhanVien.NhanVien;
import org.springframework.stereotype.Component;

@Component
public class StaffConvert {

    public NhanVienDTO toDto(NhanVien entity){
        NhanVienDTO dto = new NhanVienDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getHoTen());
        dto.setAddress(entity.getDiaChi());
        dto.setEmail(entity.getEmail());
        dto.setGioiTinh(Boolean.valueOf(entity.getGioiTinh()));
        dto.setPhoneNumber((entity.getSoDienThoai()));
        dto.setPassword(entity.getPassword());
        dto.setStatus(entity.getTrangThai());
        dto.setCreatedby(entity.getCreatedby());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setLastModifiedBy(entity.getLastModifiedBy());
        dto.setLastModifiedDate(entity.getLastModifiedDate());
        dto.setDeleted(entity.isDeleted());
        dto.setDateOfBirth(entity.getNgaySinh());
        return dto;

    }


    public NhanVien toEntity(NhanVienDTO dto){
        NhanVien entity = new NhanVien();
        entity.setId(dto.getId());
        entity.setHoTen(dto.getName());
        entity.setDiaChi(dto.getAddress());
        entity.setEmail(dto.getEmail());
        entity.setGioiTinh(Boolean.valueOf(dto.getGioiTinh()));
        entity.setSoDienThoai(dto.getPhoneNumber());
        entity.setMatKhau(dto.getPassword());
        entity.setTrangThai(dto.getStatus());
        entity.setCreatedby(dto.getCreatedby());
        entity.setCreatedDate(dto.getCreatedDate());
        entity.setLastModifiedBy(dto.getLastModifiedBy());
        entity.setLastModifiedDate(dto.getLastModifiedDate());
        entity.setDeleted(dto.isDeleted());
        entity.setNgaySinh(dto.getDateOfBirth());
        return entity;
    }
}
