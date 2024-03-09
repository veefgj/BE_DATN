package SD94.dto;

import lombok.Data;

import java.util.Date;

@Data
public class NhaSanXuatDTO {

    private long id;

    private String name;

    private String address;

    private Date createdDate;

    private String createdby;

    private Date lastModifiedDate;

    private String lastModifiedBy;

}
