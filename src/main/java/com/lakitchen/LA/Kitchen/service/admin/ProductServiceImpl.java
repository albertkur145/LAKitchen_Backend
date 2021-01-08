package com.lakitchen.LA.Kitchen.service.admin;


import com.lakitchen.LA.Kitchen.api.requestbody.admin.product.NewProductRequest;
import com.lakitchen.LA.Kitchen.api.response.ResponseTemplate;
import com.lakitchen.LA.Kitchen.api.response.data.admin.CreateProduct;
import com.lakitchen.LA.Kitchen.model.entity.Product;
import com.lakitchen.LA.Kitchen.model.entity.ProductPhoto;
import com.lakitchen.LA.Kitchen.model.entity.ProductSubCategory;
import com.lakitchen.LA.Kitchen.model.entity.Shop;
import com.lakitchen.LA.Kitchen.repository.ProductPhotoRepository;
import com.lakitchen.LA.Kitchen.repository.ProductRepository;
import com.lakitchen.LA.Kitchen.repository.ProductSubCategoryRepository;
import com.lakitchen.LA.Kitchen.repository.ShopRepository;
import com.lakitchen.LA.Kitchen.service.admin.impl.ProductService;
import com.lakitchen.LA.Kitchen.validation.BasicResult;
import org.apache.commons.io.IOUtils;
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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductPhotoRepository productPhotoRepository;

    @Autowired
    private ProductSubCategoryRepository productSubCategoryRepository;

    @Autowired
    private ShopRepository shopRepository;

    private String projectDir = "D:/project/LA Kitchen/Backend/src/uploads/";

    @Override
    public ResponseTemplate saveNewProduct(NewProductRequest request) {
        Product product = new Product();
        Shop shop = shopRepository.findFirstById(1);
        ProductSubCategory subCategory = productSubCategoryRepository.findFirstById(request.getSubCategoryId());

        product.setSeen(0);
        product.setShop(shop);
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setProductSubCategory(subCategory);
        product.setDescription(request.getDescription());
        product.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        Product productSaved = productRepository.save(product);

        return new ResponseTemplate(
                200, "OK",
                new CreateProduct(productSaved.getId()),
                null, null);
    }

    @Override
    public ResponseTemplate uploadPhoto(Integer productId, ArrayList<MultipartFile> files) {
        BasicResult basicResult = this.validationUploadPhoto(productId, files);

        if (basicResult.getResult()) {
            files.forEach((file) -> {
                String uuid = UUID.randomUUID().toString();
                String filename = StringUtils.cleanPath(file.getOriginalFilename());
                String ext = this.getExtensionFile(filename);

                filename = uuid + "." + ext;

                ProductPhoto productPhoto = new ProductPhoto();
                productPhoto.setFilename(filename);
                productPhoto.setProduct(productRepository.findFirstById(productId));
                productPhotoRepository.save(productPhoto);

                try {
                    this.saveFile(projectDir, filename, file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            return new ResponseTemplate(200, "OK", null, null, null);
        }

        return new ResponseTemplate(400, "BAD REQUEST",
                null, null, basicResult.getError());
    }

    private BasicResult validationUploadPhoto(Integer productId, ArrayList<MultipartFile> files) {
        if (productId == null || files.isEmpty()) {
            return new BasicResult(false, "Form tidak lengkap");
        }

        if (!this.isExistProduct(productId)) {
            return new BasicResult(false, "Product tidak ditemukan");
        }

        return new BasicResult(true, null);
    }

    private Boolean isExistProduct(Integer id) {
        return productRepository.findFirstById(id) != null;
    }

    public byte[] getImageWithMediaType(String imageName) throws IOException {
        Path destination = Paths.get(projectDir + imageName);
        return IOUtils.toByteArray(destination.toUri());
    }

    private String getExtensionFile(String filename) {
        String[] split = filename.split("\\.");
        return split[split.length-1];
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







