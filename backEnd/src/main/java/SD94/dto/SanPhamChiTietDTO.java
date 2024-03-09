package SD94.dto;

import SD94.entity.sanPham.KichCo;
import SD94.entity.sanPham.SanPham;
import SD94.entity.sanPham.MauSac;
import lombok.Data;

import java.util.Date;

@Data
public class SanPhamChiTietDTO {

    private Long id;

    private Integer soLuong;

    private boolean status;

    private Date createdDate;

    private String createdby;

    private Date lastModifiedDate;

    private String lastModifiedBy;

    private SanPham sanPham;

    private MauSac mauSac;

    private KichCo size;

    private long mauSac_id;

    private long kichCo_id;

    private long sanPham_id;
}
