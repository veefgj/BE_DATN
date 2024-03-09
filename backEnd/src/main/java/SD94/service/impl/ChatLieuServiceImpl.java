package SD94.service.impl;

import SD94.controller.message.Message;
import SD94.entity.sanPham.ChatLieu;
import SD94.repository.sanPham.ChatLieuRepository;
import SD94.service.service.ChatLieuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ChatLieuServiceImpl implements ChatLieuService {

    @Autowired
    ChatLieuRepository repository;

    @Override
    public List<ChatLieu> findAllProductMaterial() {
        List<ChatLieu> list = repository.findAll();
        return list;
    }

    @SuppressWarnings({ "rawtypes", "unchecked", "null" })
    @Override
    public ResponseEntity<ChatLieu> saveEdit(ChatLieu chatLieuUpdate) {
        ChatLieu optionalChatLieu = repository.findByName(chatLieuUpdate.getChatLieu());
        String errorMessage;
        Message errorResponse;

        if (optionalChatLieu != null) {
            errorMessage = " Trùng loại chất liệu";
            errorResponse = new Message(errorMessage, TrayIcon.MessageType.ERROR);
            return new ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST);
        }
        if (chatLieuUpdate.getChatLieu() == null || !isValid(chatLieuUpdate.getChatLieu())) {
            errorMessage = "Nhập không hợp lệ";
            errorResponse = new Message(errorMessage, TrayIcon.MessageType.ERROR);
            return new ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST);
        }

        try {
            Optional<ChatLieu> optional = repository.findById(chatLieuUpdate.getId());
            if (optional.isPresent()) {
                ChatLieu chatLieu = optional.get();
                chatLieu.setChatLieu(chatLieuUpdate.getChatLieu());
                repository.save(chatLieu);
                return ResponseEntity.ok(chatLieu);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return new ResponseEntity(new Message(e.getMessage(), TrayIcon.MessageType.ERROR), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<List<ChatLieu>> deleteProductMaterial(Long id) {
        try {
            @SuppressWarnings("null")
            Optional<ChatLieu> optional = repository.findById(id);
            if (optional.isPresent()) {
                ChatLieu chatLieu = optional.get();
                chatLieu.setDeleted(true);
                repository.save(chatLieu);

                List<ChatLieu> list = findAllProductMaterial();
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
    public ResponseEntity<?> saveCreate(ChatLieu chatLieuCreate) {
        ChatLieu optionalChatLieu = repository.findByName(chatLieuCreate.getChatLieu());
        String errorMessage;
        Message errorResponse;

        if (optionalChatLieu != null) {
            errorMessage = " Trùng loại chất liệu";
            errorResponse = new Message(errorMessage, TrayIcon.MessageType.ERROR);
            return new ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST);
        }
        if (chatLieuCreate.getChatLieu() == null || !isValid(chatLieuCreate.getChatLieu())) {
            errorMessage = "Nhập không hợp lệ";
            errorResponse = new Message(errorMessage, TrayIcon.MessageType.ERROR);
            return new ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST);
        }

        try {
            ChatLieu chatLieu = new ChatLieu();
            chatLieu.setChatLieu(chatLieuCreate.getChatLieu());
            repository.save(chatLieu);
            List<ChatLieu> chatLieus = repository.findAll();
            return ResponseEntity.ok().body(chatLieus);
        } catch (Exception e) {
            return new ResponseEntity(new Message(e.getMessage(), TrayIcon.MessageType.ERROR), HttpStatus.BAD_REQUEST);
        }
    }

    private boolean isValid(String str) {
        return str.matches("^[a-zA-Z\\d\\s\\S]+$");
    }

    @Override
    public List<ChatLieu> searchAllProductMaterial(String search) {

        return null;
    }

    @Override
    public List<ChatLieu> searchDateProductMaterial(String searchDate) {
        LocalDate search = LocalDate.parse(searchDate);
        List<ChatLieu> list = repository.findByDate(search);
        return list;
    }
}
