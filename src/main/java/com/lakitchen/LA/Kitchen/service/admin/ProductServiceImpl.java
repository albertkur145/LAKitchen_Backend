package com.lakitchen.LA.Kitchen.service.admin;


import com.lakitchen.LA.Kitchen.api.response.ResponseTemplate;
import com.lakitchen.LA.Kitchen.model.entity.ProductPhoto;
import com.lakitchen.LA.Kitchen.repository.ProductPhotoRepository;
import com.lakitchen.LA.Kitchen.service.admin.impl.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductPhotoRepository productPhotoRepository;

    private String projectDir = "D:/project/LA Kitchen/Backend/src/uploads/";
    private String pathLink = "http://localhost:4000/";

    @Override
    public ResponseTemplate uploadPhoto(ArrayList<MultipartFile> files) {
        files.forEach((file) -> {
            String uuid = UUID.randomUUID().toString();

            String filename = StringUtils.cleanPath(file.getOriginalFilename());
            String[] splitFilename = filename.split("\\.");
            String ext = "." + splitFilename[splitFilename.length-1];

            ProductPhoto productPhoto = new ProductPhoto();
            productPhoto.setLink(pathLink + uuid + ext);
            productPhotoRepository.save(productPhoto);

            try {
                this.saveFile(projectDir, uuid + ext, file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        return new ResponseTemplate(200, "OK", null, null, null);
    }

    private void saveFile(String uploadDir, String filename, MultipartFile file) throws IOException {
        Path path = Paths.get(uploadDir);

        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }

        try (InputStream inputStream = file.getInputStream()) {
            Path filePath = path.resolve(filename);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new IOException("Can't save image file : " + filename, e);
        }
    }
}







