package SD94.service.impl;

import SD94.controller.message.Message;
import SD94.entity.gioHang.GioHangChiTiet;
import SD94.entity.hoaDon.HoaDonChiTiet;
import SD94.entity.sanPham.SanPham;
import SD94.entity.sanPham.SanPhamChiTiet;
import SD94.repository.gioHang.GioHangChiTietRepository;
import SD94.repository.hoaDon.HoaDonChiTietRepository;
import SD94.repository.sanPham.HinhAnhRepository;
import SD94.repository.sanPham.SanPhamChiTietRepository;
import SD94.repository.sanPham.SanPhamRepository;
import SD94.service.service.SanPhamChiTietService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class SanPhamChiTietServiceImpl implements SanPhamChiTietService {

    @Autowired
    SanPhamChiTietRepository repository;

    @Autowired
    HinhAnhRepository hinhAnhRepository;

    @Autowired
    SanPhamRepository sanPhamRepository;

    @Autowired
    GioHangChiTietRepository gioHangChiTietRepository;

    @Autowired
    HoaDonChiTietRepository hoaDonChiTietRepository;

    @Override
    public List<SanPhamChiTiet> findAllProductDetails() {
        List<SanPhamChiTiet> list = repository.findAll();
        return list;
    }

    @Override
    public List<SanPhamChiTiet> findProductDetails() {
        List<SanPhamChiTiet> list = repository.findProductDetails();
        return list;
    }

    @SuppressWarnings({ "unchecked", "rawtypes", "null" })
    @Override
    public ResponseEntity<SanPhamChiTiet> saveEdit(SanPhamChiTiet sanPhamChiTietUpdate) {
        String errorMessage;
        Message errorResponse;
        if (sanPhamChiTietUpdate.getSoLuong() < 0) {
            errorMessage = "Số lượng không được nhỏ hơn 0";
            errorResponse = new Message(errorMessage, TrayIcon.MessageType.ERROR);
            return new ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST);
        }
        try {
            Optional<SanPhamChiTiet> optional = repository.findById(sanPhamChiTietUpdate.getId());
            if (optional.isPresent()) {
                SanPhamChiTiet sanPhamChiTiet = optional.get();
                if (sanPhamChiTietUpdate.getSoLuong() <= 0) {
                    sanPhamChiTiet.setTrangThai(false);
                } else {
                    sanPhamChiTiet.setTrangThai(true);
                }
                sanPhamChiTiet.setSoLuong(sanPhamChiTietUpdate.getSoLuong());
                sanPhamChiTiet.setMauSac(sanPhamChiTietUpdate.getMauSac());
                sanPhamChiTiet.setKichCo(sanPhamChiTietUpdate.getKichCo());
                SanPham sanPham = sanPhamChiTietUpdate.getSanPham();

                if (sanPham != null) {
                    sanPhamChiTiet.setSanPham(sanPham);
                }
                if (sanPham.getGia() <= 0) {
                    errorMessage = "Giá tiền phải lớn hơn 0";
                    errorResponse = new Message(errorMessage, TrayIcon.MessageType.ERROR);
                    return new ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST);
                }
                repository.save(sanPhamChiTiet);
                return ResponseEntity.ok(sanPhamChiTiet);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return new ResponseEntity(new Message(e.getMessage(), TrayIcon.MessageType.ERROR), HttpStatus.BAD_REQUEST);
        }
    }

    @SuppressWarnings("null")
    public ResponseEntity<List<SanPhamChiTiet>> deleteProductDetails(Long id) {
        try {
            Optional<SanPhamChiTiet> optional = repository.findById(id);
            SanPham sp = sanPhamRepository.findByID(optional.get().getSanPham().getId());
            if (optional.isPresent()) {
                SanPhamChiTiet sanPhamChiTiet = optional.get();
                sanPhamChiTiet.setDeleted(true);
                repository.save(sanPhamChiTiet);
                List<GioHangChiTiet> gioHangChiTiets = gioHangChiTietRepository.findCartBySPCTID(sanPhamChiTiet.getId());
                for (GioHangChiTiet gioHangChiTiet : gioHangChiTiets) {
                    gioHangChiTiet.setDeleted(true);
                    gioHangChiTietRepository.save(gioHangChiTiet);
                }

                List<HoaDonChiTiet> hoaDonChiTiets = hoaDonChiTietRepository.findBySPCTID(sanPhamChiTiet.getId());
                for (HoaDonChiTiet hoaDonChiTiet : hoaDonChiTiets) {
                    hoaDonChiTiet.setDeleted(true);
                    hoaDonChiTietRepository.save(hoaDonChiTiet);
                }
                List<SanPhamChiTiet> list = repository.findSpctByIdSp(sp.getId());
                return ResponseEntity.ok(list);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public ResponseEntity<SanPhamChiTiet> saveCreate(SanPhamChiTiet sanPhamChiTietCreate) {
        try {
            SanPhamChiTiet sanPhamChiTiet = new SanPhamChiTiet();

            return ResponseEntity.ok(sanPhamChiTiet);
        } catch (Exception e) {
            return new ResponseEntity(new Message(e.getMessage(), TrayIcon.MessageType.ERROR), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public List<SanPhamChiTiet> searchAllProductDetails(String search) {
        List<SanPhamChiTiet> list = repository.findByAll(search);
        return list;
    }

    @Override
    public List<SanPhamChiTiet> searchDateProductDetails(String searchDate) {
        LocalDate search = LocalDate.parse(searchDate);
        List<SanPhamChiTiet> list = repository.findByDate(search);
        return list;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public ResponseEntity<?> chinhSuaSoLuongSPCT(SanPhamChiTiet sanPhamChiTiet) {
        SanPhamChiTiet sanPhamChiTiets = repository.findByID(sanPhamChiTiet.getId());
        sanPhamChiTiets.setSoLuong(sanPhamChiTiet.getSoLuong());
        repository.save(sanPhamChiTiets);
        return new ResponseEntity(HttpStatus.OK);
    }
}