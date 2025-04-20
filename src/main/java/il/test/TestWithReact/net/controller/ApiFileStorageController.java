package il.test.TestWithReact.net.controller;

import il.test.TestWithReact.service.FileStorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
@RestController
@RequestMapping("/api/res/img")
public class ApiFileStorageController {
    @Autowired
    private FileStorageService fss;

    @PostMapping
    public ResponseEntity<String> putImg(@RequestParam("image") MultipartFile photo) {
        String originalFilename = photo.getOriginalFilename();
        int index = originalFilename.indexOf(".");
        String substring = originalFilename.substring(0, index);
        substring += "_" + System.nanoTime();

        String name = substring + originalFilename.substring(index);
        String key = "images/" + name;

        try (InputStream inputStream = photo.getInputStream()) {
            fss.putFile(key, inputStream, photo.getSize());
            return ResponseEntity.ok(name);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading photo: " + e.getMessage());
        }
    }

    @GetMapping("/{imageId}")
    public ResponseEntity<Resource> getImg(@PathVariable String imageId) {
        String key = "images/" + imageId;

//        try {
            Resource resource = fss.getFileAsResource(key);
            return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG) // Adjust based on your image type
                .body(resource);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        }
    }

}
