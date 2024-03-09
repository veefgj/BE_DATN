package SD94.service.impl;

import SD94.controller.message.Message;
import SD94.entity.sanPham.NhaSanXuat;
import SD94.repository.sanPham.NhaSanXuatRepository;
import SD94.service.service.NhaSanXuatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class NhaSanXuatServiceImpl implements NhaSanXuatService {

    @Autowired
    NhaSanXuatRepository producerRepository;

    @Override
    public List<NhaSanXuat> findAllProducer() {
        List<NhaSanXuat> nhaSanXuats = producerRepository.findAllnha_san_xuat();
        return nhaSanXuats;
    }

    @SuppressWarnings({ "unchecked", "rawtypes", "null" })
    @Override
    public ResponseEntity<NhaSanXuat> saveEdit(NhaSanXuat nhaSanXuatUpdate) {
        NhaSanXuat optionalNhaSanXuat = producerRepository.findByName(nhaSanXuatUpdate.getName());
        String errorMessage;
        Message errorResponse;

        if (optionalNhaSanXuat != null) {
            errorMessage = " Trùng nhà sản xuất";
            errorResponse = new Message(errorMessage, TrayIcon.MessageType.ERROR);
            return new ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST);
        }
        if (nhaSanXuatUpdate.getName() == null || !isValid(nhaSanXuatUpdate.getName())) {
            errorMessage = "Nhập không hợp lệ";
            errorResponse = new Message(errorMessage, TrayIcon.MessageType.ERROR);
            return new ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST);
        }

        try {
            Optional<NhaSanXuat> optionalProducer = producerRepository.findById(nhaSanXuatUpdate.getId());
            if (optionalProducer.isPresent()) {
                NhaSanXuat nhaSanXuat = optionalProducer.get();
                nhaSanXuat.setName(nhaSanXuatUpdate.getName());
                producerRepository.save(nhaSanXuat);
                return ResponseEntity.ok(nhaSanXuat);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return new ResponseEntity(new Message(e.getMessage(), TrayIcon.MessageType.ERROR), HttpStatus.BAD_REQUEST);
        }
    }

    @SuppressWarnings("null")
    @Override
    public ResponseEntity<List<NhaSanXuat>> deleteProducer(Long id) {
        try {
            Optional<NhaSanXuat> optionalProducer = producerRepository.findById(id);
            if (optionalProducer.isPresent()) {
                NhaSanXuat nhaSanXuat = optionalProducer.get();
                nhaSanXuat.setDeleted(true);
                producerRepository.save(nhaSanXuat);
                List<NhaSanXuat> nhaSanXuatList = findAllProducer();
                return ResponseEntity.ok(nhaSanXuatList);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public ResponseEntity<NhaSanXuat> saveCreate(NhaSanXuat nhaSanXuatCreate) {
        NhaSanXuat optionalNhaSanXuat = producerRepository.findByName(nhaSanXuatCreate.getName());
        String errorMessage;
        Message errorResponse;

        if (optionalNhaSanXuat != null) {
            errorMessage = " Trùng nhà sản xuất";
            errorResponse = new Message(errorMessage, TrayIcon.MessageType.ERROR);
            return new ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST);
        }
        if (nhaSanXuatCreate.getName() == null || !isValid(nhaSanXuatCreate.getName())) {
            errorMessage = "Nhập không hợp lệ";
            errorResponse = new Message(errorMessage, TrayIcon.MessageType.ERROR);
            return new ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST);
        }

        try {
            NhaSanXuat nhaSanXuat = new NhaSanXuat();
            nhaSanXuat.setName(nhaSanXuatCreate.getName());
            producerRepository.save(nhaSanXuat);
            return ResponseEntity.ok(nhaSanXuat);
        } catch (Exception e) {
            return new ResponseEntity(new Message(e.getMessage(), TrayIcon.MessageType.ERROR), HttpStatus.BAD_REQUEST);
        }

    }

    private boolean isValid(String str) {
        return str.matches("^[a-zA-Z\\d\\s\\S]+$");
    }

    @Override
    public List<NhaSanXuat> searchAllProducer(String search) {
        List<NhaSanXuat> nhaSanXuatList = producerRepository.findnha_san_xuatByAll(search);
        return nhaSanXuatList;
    }

    @Override
    public List<NhaSanXuat> searchDateProducer(String searchDate) {
        LocalDate search = LocalDate.parse(searchDate);
        List<NhaSanXuat> nhaSanXuatList = producerRepository.findnha_san_xuatByDate(search);
        return nhaSanXuatList;
    }
}
