package SD94.service.impl;

import SD94.controller.message.Message;
import SD94.entity.sanPham.KichCo;
import SD94.repository.sanPham.KichCoRepository;
import SD94.service.service.KichCoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class KichCoServiceImpl implements KichCoService {

    @Autowired
    KichCoRepository kichCoRepository;

    @Override
    public List<KichCo> findAllProductSize() {
        List<KichCo> list = kichCoRepository.findAll();
        return list;
    }

    @SuppressWarnings({ "unchecked", "rawtypes", "null" })
    @Override
    public ResponseEntity<KichCo> saveEdit(KichCo kichCoUpdate) {
        KichCo optionalkichCo = kichCoRepository.findByName(kichCoUpdate.getKichCo());
        String errorMessage;
        Message errorResponse;

        if (optionalkichCo != null) {
            errorMessage = " Trùng kích cỡ";
            errorResponse = new Message(errorMessage, TrayIcon.MessageType.ERROR);
            return new ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST);
        }
        if (kichCoUpdate.getKichCo() == null || !isNumeric(kichCoUpdate.getKichCo())) {
            errorMessage = "Nhập không hợp lệ";
            errorResponse = new Message(errorMessage, TrayIcon.MessageType.ERROR);
            return new ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST);
        }

        try {
            Optional<KichCo> optional = kichCoRepository.findById(kichCoUpdate.getId());
            if (optional.isPresent()) {
                KichCo product = optional.get();
                product.setKichCo(kichCoUpdate.getKichCo());
                kichCoRepository.save(product);
                return ResponseEntity.ok(product);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return new ResponseEntity(new Message(e.getMessage(), TrayIcon.MessageType.ERROR), HttpStatus.BAD_REQUEST);
        }
    }

    @SuppressWarnings("null")
    @Override
    public ResponseEntity<List<KichCo>> deleteProductSize(Long id) {
        try {
            Optional<KichCo> optional = kichCoRepository.findById(id);
            if (optional.isPresent()) {
                KichCo product = optional.get();
                product.setDeleted(true);
                kichCoRepository.save(product);

                List<KichCo> list = findAllProductSize();
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
    public ResponseEntity<KichCo> saveCreate(KichCo kichCoCreate) {
        KichCo optionalkichCo = kichCoRepository.findByName(kichCoCreate.getKichCo());
        String errorMessage;
        Message errorResponse;

        if (optionalkichCo != null) {
            errorMessage = " Trùng kích cỡ";
            errorResponse = new Message(errorMessage, TrayIcon.MessageType.ERROR);
            return new ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST);
        }
        if (kichCoCreate.getKichCo() == null || !isNumeric(kichCoCreate.getKichCo())) {
            errorMessage = "Nhập kích cỡ không đúng định dạng";
            errorResponse = new Message(errorMessage, TrayIcon.MessageType.ERROR);
            return new ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST);
        }
        try {
            KichCo kichCo = new KichCo();
            kichCo.setKichCo(kichCoCreate.getKichCo());
            kichCoRepository.save(kichCo);
            return ResponseEntity.ok(kichCo);
        } catch (Exception e) {
            return new ResponseEntity(new Message(e.getMessage(), TrayIcon.MessageType.ERROR), HttpStatus.BAD_REQUEST);
        }
    }

    private boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public List<KichCo> searchAllProductSize(String search) {
        return null;
    }

    @Override
    public List<KichCo> searchDateProductSize(String searchDate) {
        LocalDate search = LocalDate.parse(searchDate);
        List<KichCo> list = kichCoRepository.findByDate(search);
        return list;
    }
}
