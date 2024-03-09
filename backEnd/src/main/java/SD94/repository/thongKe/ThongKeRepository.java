package SD94.repository.thongKe;

import SD94.dto.thongKe.*;

import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ThongKeRepository {

    @Procedure(name = "thongKeAll()")
    List<ThongKeAll> getThongKe();

    @Procedure(name = "thongKeAll_online()")
    List<ThongKeAllOnl> getThongKe_online();

    @Procedure(name = "thongKeAll_offline()")
    List<ThongKeAllOff> getThongKe_offline();

    @Procedure(name = "thongKeTheoNgay()")
    List<TamTinhTongTienDaBanTheoNgay> getThongKeTheoNgay();

    @Procedure(name = "thongKeTheoThang()")
    List<ThongKeTheoThang> getThongKeTheoThang(@Param("thang") int thang);

    @Procedure(name = "thongKeTheoNam()")
    List<ThongKeTheoNam> getThongKeTheoNam(@Param("nam") int nam);

    @Procedure(name = "top5SanPhamBanChay()")
    List<TopSanPhamBanChay> getThongKeSanPhamBanChay();

}