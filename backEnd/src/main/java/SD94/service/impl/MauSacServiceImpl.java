package SD94.service.impl;

import SD94.controller.message.Message;
import SD94.entity.sanPham.MauSac;
import SD94.repository.sanPham.MauSacRepository;
import SD94.service.service.MauSacService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class MauSacServiceImpl implements MauSacService {

    @Autowired
    MauSacRepository repository;

    @Override
    public List<MauSac> findAllProductColor() {
        List<MauSac> mauSacs = repository.findAll();
        return mauSacs;
    }

    @SuppressWarnings({ "unchecked", "rawtypes", "null" })
    @Override
    public ResponseEntity<MauSac> saveEdit(MauSac mauSacUpdate) {
        String errorMessage;
        Message errorResponse;

        if (mauSacUpdate.getMaMauSac() == null) {
            errorMessage = "Nhập đầy đủ thông tin";
            errorResponse = new Message(errorMessage, TrayIcon.MessageType.ERROR);
            return new ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST);
        }

        try {
            Optional<MauSac> optionalProductColor = repository.findById(mauSacUpdate.getId());
            if (optionalProductColor.isPresent()) {
                MauSac mauSac = optionalProductColor.get();
                mauSac.setMaMauSac(mauSacUpdate.getMaMauSac());
                mauSac.setTenMauSac(mauSacUpdate.getTenMauSac());
                repository.save(mauSac);
                return ResponseEntity.ok(mauSac);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return new ResponseEntity(new Message(e.getMessage(), TrayIcon.MessageType.ERROR), HttpStatus.BAD_REQUEST);
        }
    }

    @SuppressWarnings("null")
    @Override
    public ResponseEntity<List<MauSac>> deleteProductColor(Long id) {
        try {
            Optional<MauSac> optionalProductColor = repository.findById(id);
            if (optionalProductColor.isPresent()) {
                MauSac mauSac = optionalProductColor.get();
                mauSac.setDeleted(true);
                repository.save(mauSac);

                List<MauSac> mauSacList = findAllProductColor();
                return ResponseEntity.ok(mauSacList);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public ResponseEntity<MauSac> saveCreate(MauSac productMauSac) {
        MauSac optionalMauSac = repository.findByName(productMauSac.getTenMauSac(), productMauSac.getMaMauSac());
        String errorMessage;
        Message errorResponse;

        if (optionalMauSac != null) {
            if (optionalMauSac.getTenMauSac().equals(productMauSac.getTenMauSac())) {
                errorMessage = "Trùng tên màu";
            } else if (optionalMauSac.getMaMauSac().equals(productMauSac.getMaMauSac())) {
                errorMessage = "Trùng mã màu";
            } else {
                errorMessage = "Trùng tên hoặc mã";
            }
            errorResponse = new Message(errorMessage, TrayIcon.MessageType.ERROR);
            return new ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST);
        }
        if (productMauSac.getTenMauSac() == null) {
            errorMessage = "Nhập đầy đủ thông tin";
            errorResponse = new Message(errorMessage, TrayIcon.MessageType.ERROR);
            return new ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST);
        }

        try {
            MauSac mauSac = new MauSac();
            mauSac.setTenMauSac(productMauSac.getTenMauSac());
            mauSac.setMaMauSac(productMauSac.getMaMauSac());
            repository.save(mauSac);
            return ResponseEntity.ok(mauSac);
        } catch (Exception e) {
            return new ResponseEntity(new Message(e.getMessage(), TrayIcon.MessageType.ERROR), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public List<MauSac> searchAllProductColor(String search) {
        List<MauSac> mauSacList = repository.findByAll(search);
        return mauSacList;
    }

    @Override
    public List<MauSac> searchDateProductColor(String searchDate) {
        LocalDate search = LocalDate.parse(searchDate);
        List<MauSac> mauSacList = repository.findByDate(search);
        return mauSacList;
    }
}
