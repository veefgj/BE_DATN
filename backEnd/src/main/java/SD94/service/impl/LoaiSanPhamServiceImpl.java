package SD94.service.impl;

import SD94.controller.message.Message;
import SD94.entity.sanPham.LoaiSanPham;
import SD94.repository.sanPham.LoaiSanPhamRepository;
import SD94.service.service.LoaiSanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class LoaiSanPhamServiceImpl implements LoaiSanPhamService {

    @Autowired
    LoaiSanPhamRepository repository;

    @Override
    public List<LoaiSanPham> findAllProductLine() {
        List<LoaiSanPham> list = repository.findAll();
        return list;
    }

    @SuppressWarnings({ "unchecked", "rawtypes", "null" })
    @Override
    public ResponseEntity<LoaiSanPham> saveEdit(LoaiSanPham loaiSanPhamUpdate) {
        LoaiSanPham optionaLoaiSanPham = repository.findByName(loaiSanPhamUpdate.getLoaiSanPham());
        String errorMessage;
        Message errorResponse;

        if (optionaLoaiSanPham != null) {
            errorMessage = " Trùng loại sản phẩm";
            errorResponse = new Message(errorMessage, TrayIcon.MessageType.ERROR);
            return new ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST);
        }
        if (loaiSanPhamUpdate.getLoaiSanPham() == null || !isValid(loaiSanPhamUpdate.getLoaiSanPham())) {
            errorMessage = "Nhập không hợp lệ";
            errorResponse = new Message(errorMessage, TrayIcon.MessageType.ERROR);
            return new ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST);
        }

        try {
            Optional<LoaiSanPham> optional = repository.findById(loaiSanPhamUpdate.getId());
            if (optional.isPresent()) {
                LoaiSanPham line = optional.get();
                line.setLoaiSanPham(loaiSanPhamUpdate.getLoaiSanPham());
                repository.save(line);
                return ResponseEntity.ok(line);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return new ResponseEntity(new Message(e.getMessage(), TrayIcon.MessageType.ERROR), HttpStatus.BAD_REQUEST);
        }
    }

    @SuppressWarnings("null")
    @Override
    public ResponseEntity<List<LoaiSanPham>> deleteProductLine(Long id) {
        try {
            Optional<LoaiSanPham> optional = repository.findById(id);
            if (optional.isPresent()) {
                LoaiSanPham line = optional.get();
                line.setDeleted(true);
                repository.save(line);

                List<LoaiSanPham> list = findAllProductLine();
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
    public ResponseEntity<LoaiSanPham> saveCreate(LoaiSanPham loaiSanPhamCreate) {
        LoaiSanPham optionaLoaiSanPham = repository.findByName(loaiSanPhamCreate.getLoaiSanPham());
        String errorMessage;
        Message errorResponse;

        if (optionaLoaiSanPham != null) {
            errorMessage = " Trùng loại sản phẩm";
            errorResponse = new Message(errorMessage, TrayIcon.MessageType.ERROR);
            return new ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST);
        }
        if (loaiSanPhamCreate.getLoaiSanPham() == null || !isValid(loaiSanPhamCreate.getLoaiSanPham())) {
            errorMessage = "Nhập không hợp lệ";
            errorResponse = new Message(errorMessage, TrayIcon.MessageType.ERROR);
            return new ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST);
        }

        try {
            LoaiSanPham line = new LoaiSanPham();
            line.setLoaiSanPham(loaiSanPhamCreate.getLoaiSanPham());
            repository.save(line);
            return ResponseEntity.ok(line);
        } catch (Exception e) {
            return new ResponseEntity(new Message(e.getMessage(), TrayIcon.MessageType.ERROR), HttpStatus.BAD_REQUEST);
        }
    }

    private boolean isValid(String str) {
        return str.matches("^[a-zA-Z\\d\\s\\S]+$");
    }

    @Override
    public List<LoaiSanPham> searchAllProductLine(String search) {
        List<LoaiSanPham> list = repository.findByAll(search);
        return list;
    }

    @Override
    public List<LoaiSanPham> searchDateProductLine(String searchDate) {
        LocalDate search = LocalDate.parse(searchDate);
        List<LoaiSanPham> list = repository.findByDate(search);
        return list;
    }
}
