package SD94.service.impl;

import SD94.dto.HoaDonChiTietDTO;
import SD94.dto.HoaDonDTO;
import SD94.entity.hoaDon.*;
import SD94.entity.sanPham.SanPhamChiTiet;
import SD94.repository.hoaDon.*;
import SD94.repository.nhanVien.NhanVienRepository;
import SD94.repository.sanPham.HinhAnhRepository;
import SD94.repository.sanPham.SanPhamChiTietRepository;
import SD94.service.service.HoaDonDatHangService;
import SD94.service.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.time.LocalDate;

import java.util.*;

@Service
public class HoaDonDatHangServiceImpl implements HoaDonDatHangService {
    @Autowired
    HoaDonRepository hoaDonRepository;

    @Autowired
    TrangThaiRepository trangThaiRepository;

    @Autowired
    LichSuHoaDonRepository lichSuHoaDonRepository;

    @Autowired
    NhanVienRepository nhanVienRepository;

    @Autowired
    HoaDonChiTietRepository hoaDonChiTietRepository;

    @Autowired
    HinhAnhRepository hinhAnhRepository;

    @Autowired
    MailService mailService;

    @Autowired
    SanPhamChiTietRepository sanPhamChiTietRepository;

    @Autowired
    LSHoaDonRepository lsHoaDonRepository;

    @Override
    public List<HoaDon> findHoaDonByTrangThai(long trang_thai_id) {
        List<HoaDon> hoaDonList = hoaDonRepository.findHoaDonByTrangThai(trang_thai_id);
        return hoaDonList;
    }

    @Override
    public ResponseEntity<Map<String, Boolean>> capNhatTrangThai(long trang_thai_id, long id_bill) {
        HoaDon hoaDon = hoaDonRepository.findByID(id_bill);
        Optional<TrangThai> optionalTrangThai = trangThaiRepository.findById(trang_thai_id);
        if (optionalTrangThai.isPresent()) {
            TrangThai trangThai = optionalTrangThai.get();
            hoaDon.setTrangThai(trangThai);
            hoaDonRepository.save(hoaDon);
        }
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Map<String, Boolean>> capNhatTrangThaiHuyDon(long trang_thai_id, long id_bill,
            String ghiChu) {
        HoaDon hoaDon = hoaDonRepository.findByID(id_bill);
        Optional<TrangThai> optionalTrangThai = trangThaiRepository.findById(trang_thai_id);
        if (optionalTrangThai.isPresent()) {
            TrangThai trangThai = optionalTrangThai.get();
            hoaDon.setTrangThai(trangThai);
            hoaDon.setGhiChu(ghiChu);
            hoaDonRepository.save(hoaDon);

            List<HoaDonChiTiet> hoaDonChiTiets = hoaDonChiTietRepository.findByIDBill(hoaDon.getId());
            for (HoaDonChiTiet hoaDonChiTiet : hoaDonChiTiets) {
                SanPhamChiTiet sanPhamChiTiet = hoaDonChiTiet.getSanPhamChiTiet();
                sanPhamChiTiet.setSoLuong(hoaDonChiTiet.getSoLuong() + sanPhamChiTiet.getSoLuong());
                sanPhamChiTietRepository.save(sanPhamChiTiet);
            }
        }
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Map<String, Boolean>> capNhatTrangThai_TatCa(long trang_thai_id, long trang_thai_id_sau,
            String thaoTac, String nguoiThaoTac) {
        List<HoaDon> list = hoaDonRepository.findHoaDonByTrangThai(trang_thai_id);
        for (HoaDon hoaDon : list) {
            Optional<TrangThai> optionalTrangThai = trangThaiRepository.findById(trang_thai_id_sau);
            if (optionalTrangThai.isPresent()) {
                TrangThai trangThai = optionalTrangThai.get();
                hoaDon.setTrangThai(trangThai);
                createTimeLine(thaoTac, trang_thai_id_sau, hoaDon.getId(), nguoiThaoTac);
                hoaDonRepository.save(hoaDon);
                try {
                    mailService.guiMailKhiThaoTac(hoaDon.getEmailNguoiNhan(), hoaDon);
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }
        }
        return ResponseEntity.ok().build();
    }

    @Override
    public List<HoaDon> capNhatTrangThai_DaChon(HoaDonDTO hoaDonDTO, long trang_thai_id, String thaoTac,
            String nguoiThaoTac) {
        for (Long id_hoaDon : hoaDonDTO.getId_hoaDon()) {
            HoaDon hoaDon = hoaDonRepository.findByID(id_hoaDon);
            Optional<TrangThai> optionalTrangThai = trangThaiRepository.findById(trang_thai_id);
            if (optionalTrangThai.isPresent()) {
                TrangThai trangThai = optionalTrangThai.get();
                hoaDon.setTrangThai(trangThai);
                createTimeLine(thaoTac, trang_thai_id, id_hoaDon, nguoiThaoTac);
                hoaDonRepository.save(hoaDon);
            }
        }
        List<HoaDon> hoaDonList = hoaDonRepository.findHoaDonByTrangThai(trang_thai_id - 1);
        return hoaDonList;
    }

    @Override
    public List<HoaDon> capNhatTrangThaiHuy_DaChon(HoaDonDTO hoaDonDTO, String nguoiThaoTac, String ghiChu) {
        for (Long id_hoaDon : hoaDonDTO.getId_hoaDon()) {
            HoaDon hoaDon = hoaDonRepository.findByID(id_hoaDon);
            Optional<TrangThai> optionalTrangThai = trangThaiRepository.findById(5L);
            if (optionalTrangThai.isPresent()) {
                TrangThai trangThai = optionalTrangThai.get();
                hoaDon.setTrangThai(trangThai);
                hoaDon.setGhiChu(ghiChu);
                createTimeLine("Huỷ đơn", 5L, id_hoaDon, nguoiThaoTac);
                hoaDonRepository.save(hoaDon);

                List<HoaDonChiTiet> hoaDonChiTiets = hoaDonChiTietRepository.findByIDBill(hoaDon.getId());
                for (HoaDonChiTiet hoaDonChiTiet : hoaDonChiTiets) {
                    SanPhamChiTiet sanPhamChiTiet = hoaDonChiTiet.getSanPhamChiTiet();
                    sanPhamChiTiet.setSoLuong(hoaDonChiTiet.getSoLuong() + sanPhamChiTiet.getSoLuong());
                    sanPhamChiTietRepository.save(sanPhamChiTiet);
                }
            }
        }
        List<HoaDon> hoaDonList = hoaDonRepository.findHoaDonByTrangThai(1L);
        return hoaDonList;
    }

    @Override
    public List<HoaDon> searchAllBill(long trang_thai_id, String search) {
        List<HoaDon> hoaDonList = hoaDonRepository.findBill(trang_thai_id, search);
        return hoaDonList;
    }

    @Override
    public List<HoaDon> searchDateBill(long trang_thai_id, String searchDate) {
        LocalDate search = LocalDate.parse(searchDate);
        List<HoaDon> hoaDonList = hoaDonRepository.findBillByDate(trang_thai_id, search);
        return hoaDonList;
    }

    @Override
    public ResponseEntity<?> createTimeLine(String thaoTac, long trangThai_id, long hoaDon_id, String nguoiThaoTac) {
        HoaDon hoaDon = hoaDonRepository.findByID(hoaDon_id);
        TrangThai trangThai = trangThaiRepository.findByID(trangThai_id);

        LichSuHoaDon lichSuHoaDon = new LichSuHoaDon();
        lichSuHoaDon.setThaoTac(thaoTac);
        lichSuHoaDon.setHoaDon(hoaDon);
        lichSuHoaDon.setTrangThai(trangThai);
        lichSuHoaDon.setCreatedDate(new Date());
        lichSuHoaDon.setNguoiThaoTac(nguoiThaoTac);
        lichSuHoaDonRepository.save(lichSuHoaDon);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> CTChoXacNhan(long id_hoa_don) {
        HoaDon hoaDon = hoaDonRepository.findByID(id_hoa_don);
        List<HoaDonChiTiet> hoaDonChiTiets = hoaDonChiTietRepository.findByIDBill(hoaDon.getId());
        LichSuHoaDon timeLine_ChoXacNhan = lichSuHoaDonRepository.getTimeLine(hoaDon.getId(), 1L);
        List<HoaDonChiTietDTO> dto = new ArrayList<>();
        for (HoaDonChiTiet hoaDonChiTiet : hoaDonChiTiets) {
            SanPhamChiTiet sanPhamChiTiet = hoaDonChiTiet.getSanPhamChiTiet();
            HoaDon hoaDon2 = hoaDonChiTiet.getHoaDon();
            String anh_san_pham = hinhAnhRepository.getAnhSPByMauSacAndSPID(sanPhamChiTiet.getSanPham().getId(),
                    sanPhamChiTiet.getMauSac().getId());

            HoaDonChiTietDTO hoaDonChiTietDTO = new HoaDonChiTietDTO();
            hoaDonChiTietDTO.setId(hoaDonChiTiet.getId());
            hoaDonChiTietDTO.setIdProduct(sanPhamChiTiet.getSanPham().getId());
            hoaDonChiTietDTO.setIdColor(sanPhamChiTiet.getMauSac().getId());
            hoaDonChiTietDTO.setIdSize(sanPhamChiTiet.getKichCo().getId());
            hoaDonChiTietDTO.setSoLuong(hoaDonChiTiet.getSoLuong());
            hoaDonChiTietDTO.setDonGia(hoaDonChiTiet.getDonGia());
            hoaDonChiTietDTO.setThanhTien(hoaDonChiTiet.getThanhTien());
            hoaDonChiTietDTO.setHoaDon(hoaDon2);
            hoaDonChiTietDTO.setSanPhamChiTiet(sanPhamChiTiet);
            hoaDonChiTietDTO.setAnhSanPham(anh_san_pham);

            dto.add(hoaDonChiTietDTO);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("list_HDCT", dto);
        response.put("hoaDon", hoaDon);
        response.put("timeLine", timeLine_ChoXacNhan);

        List<LSHoaDon> lsHoaDons = lsHoaDonRepository.getLSHD(hoaDon.getId());
        response.put("lsHoaDons", lsHoaDons);
        return ResponseEntity.ok().body(response);
    }

    @Override
    public ResponseEntity<?> CTChoGiaoHang(long id_hoa_don) {
        HoaDon hoaDon = hoaDonRepository.findByID(id_hoa_don);
        List<HoaDonChiTiet> hoaDonChiTiets = hoaDonChiTietRepository.findByIDBill(hoaDon.getId());
        LichSuHoaDon timeLine_ChoXacNhan = lichSuHoaDonRepository.getTimeLine(hoaDon.getId(), 1L);
        LichSuHoaDon timeLine_ChoGiaoHang = lichSuHoaDonRepository.getTimeLine(hoaDon.getId(), 2L);
        List<HoaDonChiTietDTO> dto = new ArrayList<>();
        for (HoaDonChiTiet hoaDonChiTiet : hoaDonChiTiets) {
            SanPhamChiTiet sanPhamChiTiet = hoaDonChiTiet.getSanPhamChiTiet();
            HoaDon hoaDon2 = hoaDonChiTiet.getHoaDon();
            String anh_san_pham = hinhAnhRepository.getAnhSPByMauSacAndSPID(sanPhamChiTiet.getSanPham().getId(),
                    sanPhamChiTiet.getMauSac().getId());

            HoaDonChiTietDTO hoaDonChiTietDTO = new HoaDonChiTietDTO();
            hoaDonChiTietDTO.setId(hoaDonChiTiet.getId());
            hoaDonChiTietDTO.setIdProduct(sanPhamChiTiet.getSanPham().getId());
            hoaDonChiTietDTO.setIdColor(sanPhamChiTiet.getMauSac().getId());
            hoaDonChiTietDTO.setIdSize(sanPhamChiTiet.getKichCo().getId());
            hoaDonChiTietDTO.setSoLuong(hoaDonChiTiet.getSoLuong());
            hoaDonChiTietDTO.setDonGia(hoaDonChiTiet.getDonGia());
            hoaDonChiTietDTO.setThanhTien(hoaDonChiTiet.getThanhTien());
            hoaDonChiTietDTO.setHoaDon(hoaDon2);
            hoaDonChiTietDTO.setSanPhamChiTiet(sanPhamChiTiet);
            hoaDonChiTietDTO.setAnhSanPham(anh_san_pham);

            dto.add(hoaDonChiTietDTO);
        }
        Map<String, Object> response = new HashMap<>();
        response.put("list_HDCT", dto);
        response.put("hoaDon", hoaDon);
        response.put("timeLine_ChoXacNhan", timeLine_ChoXacNhan);
        response.put("timeLine_ChoGiaoHang", timeLine_ChoGiaoHang);

        List<LSHoaDon> lsHoaDons = lsHoaDonRepository.getLSHD(hoaDon.getId());
        response.put("lsHoaDons", lsHoaDons);
        return ResponseEntity.ok().body(response);
    }

    @Override
    public ResponseEntity<?> CTDangGiaoHang(long id_hoa_don) {
        HoaDon hoaDon = hoaDonRepository.findByID(id_hoa_don);
        List<HoaDonChiTiet> hoaDonChiTiets = hoaDonChiTietRepository.findByIDBill(hoaDon.getId());
        LichSuHoaDon timeLine_ChoXacNhan = lichSuHoaDonRepository.getTimeLine(hoaDon.getId(), 1L);
        LichSuHoaDon timeLine_ChoGiaoHang = lichSuHoaDonRepository.getTimeLine(hoaDon.getId(), 2L);
        LichSuHoaDon timeLine_DangGiaoHang = lichSuHoaDonRepository.getTimeLine(hoaDon.getId(), 3L);
        List<HoaDonChiTietDTO> dto = new ArrayList<>();
        for (HoaDonChiTiet hoaDonChiTiet : hoaDonChiTiets) {
            SanPhamChiTiet sanPhamChiTiet = hoaDonChiTiet.getSanPhamChiTiet();
            HoaDon hoaDon2 = hoaDonChiTiet.getHoaDon();
            String anh_san_pham = hinhAnhRepository.getAnhSPByMauSacAndSPID(sanPhamChiTiet.getSanPham().getId(),
                    sanPhamChiTiet.getMauSac().getId());

            HoaDonChiTietDTO hoaDonChiTietDTO = new HoaDonChiTietDTO();
            hoaDonChiTietDTO.setId(hoaDonChiTiet.getId());
            hoaDonChiTietDTO.setIdProduct(sanPhamChiTiet.getSanPham().getId());
            hoaDonChiTietDTO.setIdColor(sanPhamChiTiet.getMauSac().getId());
            hoaDonChiTietDTO.setIdSize(sanPhamChiTiet.getKichCo().getId());
            hoaDonChiTietDTO.setSoLuong(hoaDonChiTiet.getSoLuong());
            hoaDonChiTietDTO.setDonGia(hoaDonChiTiet.getDonGia());
            hoaDonChiTietDTO.setThanhTien(hoaDonChiTiet.getThanhTien());
            hoaDonChiTietDTO.setHoaDon(hoaDon2);
            hoaDonChiTietDTO.setSanPhamChiTiet(sanPhamChiTiet);
            hoaDonChiTietDTO.setAnhSanPham(anh_san_pham);

            dto.add(hoaDonChiTietDTO);
        }
        Map<String, Object> response = new HashMap<>();
        response.put("list_HDCT", dto);
        response.put("hoaDon", hoaDon);
        response.put("timeLine_ChoXacNhan", timeLine_ChoXacNhan);
        response.put("timeLine_ChoGiaoHang", timeLine_ChoGiaoHang);
        response.put("timeLine_DangGiaoHang", timeLine_DangGiaoHang);

        List<LSHoaDon> lsHoaDons = lsHoaDonRepository.getLSHD(hoaDon.getId());
        response.put("lsHoaDons", lsHoaDons);
        return ResponseEntity.ok().body(response);
    }

    @Override
    public ResponseEntity<?> CTDaGiaoHang(long id_hoa_don) {
        HoaDon hoaDon = hoaDonRepository.findByID(id_hoa_don);
        List<HoaDonChiTiet> hoaDonChiTiets = hoaDonChiTietRepository.findByIDBill(hoaDon.getId());
        LichSuHoaDon timeLine_ChoXacNhan = lichSuHoaDonRepository.getTimeLine(hoaDon.getId(), 1L);
        LichSuHoaDon timeLine_ChoGiaoHang = lichSuHoaDonRepository.getTimeLine(hoaDon.getId(), 2L);
        LichSuHoaDon timeLine_DangGiaoHang = lichSuHoaDonRepository.getTimeLine(hoaDon.getId(), 3L);
        LichSuHoaDon timeLine_DaGiaoHang = lichSuHoaDonRepository.getTimeLine(hoaDon.getId(), 4L);
        List<HoaDonChiTietDTO> dto = new ArrayList<>();
        for (HoaDonChiTiet hoaDonChiTiet : hoaDonChiTiets) {
            SanPhamChiTiet sanPhamChiTiet = hoaDonChiTiet.getSanPhamChiTiet();
            HoaDon hoaDon2 = hoaDonChiTiet.getHoaDon();
            String anh_san_pham = hinhAnhRepository.getAnhSPByMauSacAndSPID(sanPhamChiTiet.getSanPham().getId(),
                    sanPhamChiTiet.getMauSac().getId());

            HoaDonChiTietDTO hoaDonChiTietDTO = new HoaDonChiTietDTO();
            hoaDonChiTietDTO.setId(hoaDonChiTiet.getId());
            hoaDonChiTietDTO.setIdProduct(sanPhamChiTiet.getSanPham().getId());
            hoaDonChiTietDTO.setIdColor(sanPhamChiTiet.getMauSac().getId());
            hoaDonChiTietDTO.setIdSize(sanPhamChiTiet.getKichCo().getId());
            hoaDonChiTietDTO.setSoLuong(hoaDonChiTiet.getSoLuong());
            hoaDonChiTietDTO.setDonGia(hoaDonChiTiet.getDonGia());
            hoaDonChiTietDTO.setThanhTien(hoaDonChiTiet.getThanhTien());
            hoaDonChiTietDTO.setHoaDon(hoaDon2);
            hoaDonChiTietDTO.setSanPhamChiTiet(sanPhamChiTiet);
            hoaDonChiTietDTO.setAnhSanPham(anh_san_pham);

            dto.add(hoaDonChiTietDTO);
        }
        Map<String, Object> response = new HashMap<>();
        response.put("list_HDCT", dto);
        response.put("hoaDon", hoaDon);
        response.put("timeLine_ChoXacNhan", timeLine_ChoXacNhan);
        response.put("timeLine_ChoGiaoHang", timeLine_ChoGiaoHang);
        response.put("timeLine_DangGiaoHang", timeLine_DangGiaoHang);
        response.put("timeLine_DaGiaoHang", timeLine_DaGiaoHang);

        List<LSHoaDon> lsHoaDons = lsHoaDonRepository.getLSHD(hoaDon.getId());
        response.put("lsHoaDons", lsHoaDons);
        return ResponseEntity.ok().body(response);
    }

    @Override
    public ResponseEntity<?> CTDaHuy(long id_hoa_don) {
        HoaDon hoaDon = hoaDonRepository.findByID(id_hoa_don);
        List<HoaDonChiTiet> hoaDonChiTiets = hoaDonChiTietRepository.findByIDBill(hoaDon.getId());
        LichSuHoaDon timeLine_ChoXacNhan = lichSuHoaDonRepository.getTimeLine(hoaDon.getId(), 1L);
        LichSuHoaDon timeLine_DaHuy = lichSuHoaDonRepository.getTimeLine(hoaDon.getId(), 5L);
        List<HoaDonChiTietDTO> dto = new ArrayList<>();
        for (HoaDonChiTiet hoaDonChiTiet : hoaDonChiTiets) {
            SanPhamChiTiet sanPhamChiTiet = hoaDonChiTiet.getSanPhamChiTiet();
            HoaDon hoaDon2 = hoaDonChiTiet.getHoaDon();
            String anh_san_pham = hinhAnhRepository.getAnhSPByMauSacAndSPID(sanPhamChiTiet.getSanPham().getId(),
                    sanPhamChiTiet.getMauSac().getId());

            HoaDonChiTietDTO hoaDonChiTietDTO = new HoaDonChiTietDTO();
            hoaDonChiTietDTO.setId(hoaDonChiTiet.getId());
            hoaDonChiTietDTO.setIdProduct(sanPhamChiTiet.getSanPham().getId());
            hoaDonChiTietDTO.setIdColor(sanPhamChiTiet.getMauSac().getId());
            hoaDonChiTietDTO.setIdSize(sanPhamChiTiet.getKichCo().getId());
            hoaDonChiTietDTO.setSoLuong(hoaDonChiTiet.getSoLuong());
            hoaDonChiTietDTO.setDonGia(hoaDonChiTiet.getDonGia());
            hoaDonChiTietDTO.setThanhTien(hoaDonChiTiet.getThanhTien());
            hoaDonChiTietDTO.setHoaDon(hoaDon2);
            hoaDonChiTietDTO.setSanPhamChiTiet(sanPhamChiTiet);
            hoaDonChiTietDTO.setAnhSanPham(anh_san_pham);

            dto.add(hoaDonChiTietDTO);
        }
        Map<String, Object> response = new HashMap<>();
        response.put("list_HDCT", dto);
        response.put("hoaDon", hoaDon);
        response.put("timeLine_ChoXacNhan", timeLine_ChoXacNhan);
        response.put("timeLine_DaHuy", timeLine_DaHuy);

        List<LSHoaDon> lsHoaDons = lsHoaDonRepository.getLSHD(hoaDon.getId());
        response.put("lsHoaDons", lsHoaDons);
        return ResponseEntity.ok().body(response);
    }

    @Override
    public ResponseEntity<?> CTXacNhanDaGiao(long id_hoa_don) {
        HoaDon hoaDon = hoaDonRepository.findByID(id_hoa_don);
        List<HoaDonChiTiet> hoaDonChiTiets = hoaDonChiTietRepository.findByIDBill(hoaDon.getId());
        LichSuHoaDon timeLine_ChoXacNhan = lichSuHoaDonRepository.getTimeLine(hoaDon.getId(), 1L);
        LichSuHoaDon timeLine_ChoGiaoHang = lichSuHoaDonRepository.getTimeLine(hoaDon.getId(), 2L);
        LichSuHoaDon timeLine_DangGiaoHang = lichSuHoaDonRepository.getTimeLine(hoaDon.getId(), 3L);
        LichSuHoaDon timeLine_XacNhanDaGiao = lichSuHoaDonRepository.getTimeLine(hoaDon.getId(), 9L);
        List<HoaDonChiTietDTO> dto = new ArrayList<>();
        for (HoaDonChiTiet hoaDonChiTiet : hoaDonChiTiets) {
            SanPhamChiTiet sanPhamChiTiet = hoaDonChiTiet.getSanPhamChiTiet();
            HoaDon hoaDon2 = hoaDonChiTiet.getHoaDon();
            String anh_san_pham = hinhAnhRepository.getAnhSPByMauSacAndSPID(sanPhamChiTiet.getSanPham().getId(),
                    sanPhamChiTiet.getMauSac().getId());

            HoaDonChiTietDTO hoaDonChiTietDTO = new HoaDonChiTietDTO();
            hoaDonChiTietDTO.setId(hoaDonChiTiet.getId());
            hoaDonChiTietDTO.setIdProduct(sanPhamChiTiet.getSanPham().getId());
            hoaDonChiTietDTO.setIdColor(sanPhamChiTiet.getMauSac().getId());
            hoaDonChiTietDTO.setIdSize(sanPhamChiTiet.getKichCo().getId());
            hoaDonChiTietDTO.setSoLuong(hoaDonChiTiet.getSoLuong());
            hoaDonChiTietDTO.setDonGia(hoaDonChiTiet.getDonGia());
            hoaDonChiTietDTO.setThanhTien(hoaDonChiTiet.getThanhTien());
            hoaDonChiTietDTO.setHoaDon(hoaDon2);
            hoaDonChiTietDTO.setSanPhamChiTiet(sanPhamChiTiet);
            hoaDonChiTietDTO.setAnhSanPham(anh_san_pham);

            dto.add(hoaDonChiTietDTO);
        }
        Map<String, Object> response = new HashMap<>();
        response.put("list_HDCT", dto);
        response.put("hoaDon", hoaDon);
        response.put("timeLine_ChoXacNhan", timeLine_ChoXacNhan);
        response.put("timeLine_ChoGiaoHang", timeLine_ChoGiaoHang);
        response.put("timeLine_DangGiaoHang", timeLine_DangGiaoHang);
        response.put("timeLine_XacNhanDaGiao", timeLine_XacNhanDaGiao);

        List<LSHoaDon> lsHoaDons = lsHoaDonRepository.getLSHD(hoaDon.getId());
        response.put("lsHoaDons", lsHoaDons);
        return ResponseEntity.ok().body(response);
    }

    @Override
    public ResponseEntity<?> TaiQuay(long id_hoa_don) {
        HoaDon hoaDon = hoaDonRepository.findByID(id_hoa_don);
        List<HoaDonChiTiet> hoaDonChiTiets = hoaDonChiTietRepository.findByIDBill(hoaDon.getId());
        List<HoaDonChiTietDTO> dto = new ArrayList<>();
        for (HoaDonChiTiet hoaDonChiTiet : hoaDonChiTiets) {
            SanPhamChiTiet sanPhamChiTiet = hoaDonChiTiet.getSanPhamChiTiet();
            HoaDon hoaDon2 = hoaDonChiTiet.getHoaDon();
            String anh_san_pham = hinhAnhRepository.getAnhSPByMauSacAndSPID(sanPhamChiTiet.getSanPham().getId(),
                    sanPhamChiTiet.getMauSac().getId());

            HoaDonChiTietDTO hoaDonChiTietDTO = new HoaDonChiTietDTO();
            hoaDonChiTietDTO.setId(hoaDonChiTiet.getId());
            hoaDonChiTietDTO.setIdProduct(sanPhamChiTiet.getSanPham().getId());
            hoaDonChiTietDTO.setIdColor(sanPhamChiTiet.getMauSac().getId());
            hoaDonChiTietDTO.setIdSize(sanPhamChiTiet.getKichCo().getId());
            hoaDonChiTietDTO.setSoLuong(hoaDonChiTiet.getSoLuong());
            hoaDonChiTietDTO.setDonGia(hoaDonChiTiet.getDonGia());
            hoaDonChiTietDTO.setThanhTien(hoaDonChiTiet.getThanhTien());
            hoaDonChiTietDTO.setHoaDon(hoaDon2);
            hoaDonChiTietDTO.setSanPhamChiTiet(sanPhamChiTiet);
            hoaDonChiTietDTO.setAnhSanPham(anh_san_pham);

            dto.add(hoaDonChiTietDTO);
        }
        Map<String, Object> response = new HashMap<>();
        response.put("list_HDCT", dto);
        response.put("hoaDon", hoaDon);
        return ResponseEntity.ok().body(response);
    }

    @Override
    public ResponseEntity<?> CTDonHangKH(long id_hoa_don) {
        HoaDon hoaDon = hoaDonRepository.findByID(id_hoa_don);
        List<HoaDonChiTiet> hoaDonChiTiets = hoaDonChiTietRepository.findByIDBill(hoaDon.getId());
        LichSuHoaDon timeLine_ChoXacNhan = lichSuHoaDonRepository.getTimeLine(hoaDon.getId(), 1L);
        LichSuHoaDon timeLine_ChoGiaoHang = lichSuHoaDonRepository.getTimeLine(hoaDon.getId(), 2L);
        LichSuHoaDon timeLine_DangGiaoHang = lichSuHoaDonRepository.getTimeLine(hoaDon.getId(), 3L);
        LichSuHoaDon timeLine_DaGiaoHang = lichSuHoaDonRepository.getTimeLine(hoaDon.getId(), 4L);
        LichSuHoaDon timeLine_DaHuy = lichSuHoaDonRepository.getTimeLine(hoaDon.getId(), 5L);
        List<HoaDonChiTietDTO> dto = new ArrayList<>();
        for (HoaDonChiTiet hoaDonChiTiet : hoaDonChiTiets) {
            SanPhamChiTiet sanPhamChiTiet = hoaDonChiTiet.getSanPhamChiTiet();
            HoaDon hoaDon2 = hoaDonChiTiet.getHoaDon();
            String anh_san_pham = hinhAnhRepository.getAnhSPByMauSacAndSPID(sanPhamChiTiet.getSanPham().getId(),
                    sanPhamChiTiet.getMauSac().getId());

            HoaDonChiTietDTO hoaDonChiTietDTO = new HoaDonChiTietDTO();
            hoaDonChiTietDTO.setId(hoaDonChiTiet.getId());
            hoaDonChiTietDTO.setIdProduct(sanPhamChiTiet.getSanPham().getId());
            hoaDonChiTietDTO.setIdColor(sanPhamChiTiet.getMauSac().getId());
            hoaDonChiTietDTO.setIdSize(sanPhamChiTiet.getKichCo().getId());
            hoaDonChiTietDTO.setSoLuong(hoaDonChiTiet.getSoLuong());
            hoaDonChiTietDTO.setDonGia(hoaDonChiTiet.getDonGia());
            hoaDonChiTietDTO.setThanhTien(hoaDonChiTiet.getThanhTien());
            hoaDonChiTietDTO.setHoaDon(hoaDon2);
            hoaDonChiTietDTO.setSanPhamChiTiet(sanPhamChiTiet);
            hoaDonChiTietDTO.setAnhSanPham(anh_san_pham);

            dto.add(hoaDonChiTietDTO);
        }
        Map<String, Object> response = new HashMap<>();
        response.put("list_HDCT", dto);
        response.put("hoaDon", hoaDon);
        response.put("timeLine_ChoXacNhan", timeLine_ChoXacNhan);
        response.put("timeLine_ChoGiaoHang", timeLine_ChoGiaoHang);
        response.put("timeLine_DangGiaoHang", timeLine_DangGiaoHang);
        response.put("timeLine_DaGiaoHang", timeLine_DaGiaoHang);
        response.put("timeLine_DaHuy", timeLine_DaHuy);
        List<LSHoaDon> lsHoaDons = lsHoaDonRepository.getLSHD(hoaDon.getId());
        response.put("lsHoaDons", lsHoaDons);
        return ResponseEntity.ok().body(response);
    }

    @Override
    public List<HoaDon> findHoaDonByLoai(int loai_hoa_don) {
        List<HoaDon> hoaDonList = hoaDonRepository.findHoaDonByLoai(loai_hoa_don);
        return hoaDonList;
    }

}
