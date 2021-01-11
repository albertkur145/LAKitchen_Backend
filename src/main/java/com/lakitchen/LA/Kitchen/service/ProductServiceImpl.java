package com.lakitchen.LA.Kitchen.service;


import com.lakitchen.LA.Kitchen.api.dto.ProductAssessmentDTO;
import com.lakitchen.LA.Kitchen.api.dto.ProductPhotoDTO;
import com.lakitchen.LA.Kitchen.api.dto.role_user.product.ProductDetailDTO;
import com.lakitchen.LA.Kitchen.api.dto.role_user.product.ProductGeneralDTO;
import com.lakitchen.LA.Kitchen.api.requestbody.admin.product.NewProductRequest;
import com.lakitchen.LA.Kitchen.api.requestbody.admin.product.UpdateProductRequest;
import com.lakitchen.LA.Kitchen.api.requestbody.user.product.IncrementSeenRequest;
import com.lakitchen.LA.Kitchen.api.response.ResponseTemplate;
import com.lakitchen.LA.Kitchen.api.response.data.role_admin.CreateProduct;
import com.lakitchen.LA.Kitchen.api.response.data.role_user.product.GetByCategory;
import com.lakitchen.LA.Kitchen.api.response.data.role_user.product.GetByName;
import com.lakitchen.LA.Kitchen.api.response.data.role_user.product.GetByPrice;
import com.lakitchen.LA.Kitchen.api.response.data.role_user.product.GetBySubCategory;
import com.lakitchen.LA.Kitchen.model.entity.*;
import com.lakitchen.LA.Kitchen.repository.*;
import com.lakitchen.LA.Kitchen.service.impl.ProductService;
import com.lakitchen.LA.Kitchen.service.mapper.ProductMapper;
import com.lakitchen.LA.Kitchen.validation.BasicResult;
import org.apache.commons.io.IOUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductAssessmentRepository productAssessmentRepository;

    @Autowired
    private ProductPhotoRepository productPhotoRepository;

    @Autowired
    private ProductSubCategoryRepository productSubCategoryRepository;

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private ProductMapper productMapper;

    private String projectDir = "D:/project/LA Kitchen/Backend/src/uploads/";

    @Override
    public ResponseTemplate getBySubCategory(Integer subCategoryId) {
        BasicResult result = this.validationGetBySubCategory(subCategoryId);

        if (result.getResult()) {
            ArrayList<Product> products = productRepository
                    .findByProductSubCategory_IdAndDeletedAt(subCategoryId, null);
            ArrayList<ProductGeneralDTO> productDTO = new ArrayList<>();
            ProductSubCategory subCategory = productSubCategoryRepository.findFirstById(subCategoryId);

            products.forEach((val) -> {
                ProductGeneralDTO dto = this.setToFormatGeneralDTO(val);
                productDTO.add(dto);
            });

            return new ResponseTemplate(200, "OK",
                    new GetBySubCategory(subCategory.getName(), productDTO),
                    null, null);
        }

        return new ResponseTemplate(result.getCode(), result.getStatus(), null, null, result.getError());
    }

    @Override
    public ResponseTemplate getByCategory(Integer categoryId) {
        BasicResult result = this.validationGetByCategory(categoryId);

        if (result.getResult()) {
            ArrayList<Product> products = productRepository.findByCategory(categoryId);
            ArrayList<ProductGeneralDTO> productDTO = new ArrayList<>();
            ProductCategory category = productCategoryRepository.findFirstById(categoryId);

            products.forEach((val) -> {
                ProductGeneralDTO dto = this.setToFormatGeneralDTO(val);
                productDTO.add(dto);
            });

            return new ResponseTemplate(200, "OK",
                    new GetByCategory(category.getName(), productDTO),
                    null, null);
        }

        return new ResponseTemplate(result.getCode(), result.getStatus(), null, null, result.getError());
    }

    @Override
    public ResponseTemplate getByName(String productName) {
        ArrayList<Product> products = productRepository
                .findByNameIgnoreCaseContainingAndDeletedAt(productName, null);
        ArrayList<ProductGeneralDTO> productDTO = new ArrayList<>();

        products.forEach((val) -> {
            ProductGeneralDTO dto = this.setToFormatGeneralDTO(val);
            productDTO.add(dto);
        });

        return new ResponseTemplate(200, "OK", new GetByName(productDTO), null, null);
    }

    @Override
    public ResponseTemplate getByPrice(String from) {
        String title = this.getTitleSelectionPrice(from);
        ArrayList<Product> products = this.getSelectionPrice(from);
        ArrayList<ProductGeneralDTO> productDTO = new ArrayList<>();

        products.forEach((val) -> {
            ProductGeneralDTO dto = this.setToFormatGeneralDTO(val);
            productDTO.add(dto);
        });

        return new ResponseTemplate(200, "OK", new GetByPrice(title, productDTO), null, null);
    }

    @Override
    public ResponseTemplate getById(Integer productId) {
        BasicResult result = this.validationGetById(productId);

        if (result.getResult()) {
            Product product = productRepository.findFirstById(productId);
            ArrayList<ProductPhoto> photos = productPhotoRepository.findByProduct_Id(productId);
            ArrayList<ProductAssessment> assessments = productAssessmentRepository
                    .findByProduct_IdOrderByCreatedAtDesc(productId);

            ArrayList<ProductPhotoDTO> photoDTOS = new ArrayList<>();
            photos.forEach((val) -> {
                String filePhotoName = this.setFilePhotoName(val);
                String photoLink = this.setPhotoLink(filePhotoName);
                ProductPhotoDTO dto = new ProductPhotoDTO(val.getId(), photoLink);
                photoDTOS.add(dto);
            });

            Integer totalAssessment = productAssessmentRepository.countAllByProduct_Id(product.getId());
            Double rating = this.getRating(this.sumRate(assessments), totalAssessment);

            return new ResponseTemplate(200, "OK",
                    productMapper.mapToProductDetailDTO(product, photoDTOS, assessments, rating, totalAssessment),
                    null, null);
        }

        return new ResponseTemplate(result.getCode(), result.getStatus(), null, null, result.getError());
    }

    @Override
    public ResponseTemplate incrementSeen(IncrementSeenRequest request) {
        BasicResult result = this.validationIncrementSeen(request);

        if (result.getResult()) {
            Product product = productRepository.findFirstById(request.getProductId());
            product.setSeen(product.getSeen() + 1);
            productRepository.save(product);

            return new ResponseTemplate(200, "OK", null, null, null);
        }

        return new ResponseTemplate(result.getCode(), result.getStatus(), null, null, result.getError());
    }

    @Override
    public ResponseTemplate uploadPhoto(Integer productId, ArrayList<MultipartFile> files) {
        BasicResult result = this.validationUploadPhoto(productId, files);

        if (result.getResult()) {
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

        return new ResponseTemplate(result.getCode(), result.getStatus(), null, null, result.getError());
    }

    @Override
    public ResponseTemplate saveNewProduct(NewProductRequest request) {
        BasicResult result = this.validationSaveNewProduct(request);

        if (result.getResult()) {
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

            return new ResponseTemplate(200, "OK",
                    new CreateProduct(productSaved.getId()), null, null);
        }

        return new ResponseTemplate(result.getCode(), result.getStatus(),
                null, null, result.getError());
    }

    @Override
    public ResponseTemplate updateProduct(UpdateProductRequest request) {
        BasicResult result = this.validationUpdateProduct(request);

        if (result.getResult()) {
            Product product = productRepository.findFirstById(request.getId());
            ProductSubCategory subCategory = productSubCategoryRepository.findFirstById(request.getSubCategoryId());

            product.setName(request.getName());
            product.setPrice(request.getPrice());
            product.setProductSubCategory(subCategory);
            product.setDescription(request.getDescription());
            product.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
            productRepository.save(product);

            return new ResponseTemplate(200, "OK",
                    null, null, null);
        }

        return new ResponseTemplate(result.getCode(), result.getStatus(),
                null, null, result.getError());
    }

    private String getTitleSelectionPrice(String from) {
        if (from.equals("lowest")) {
            return "Harga Terendah";
        }

        return "Harga Tertinggi";
    }

    private ArrayList<Product> getSelectionPrice(String from) {
        if (from.equals("lowest")) {
            return productRepository.findAllByDeletedAtOrderByPriceAsc(null);
        }

        return productRepository.findAllByDeletedAtOrderByPriceDesc(null);
    }

    private ProductGeneralDTO setToFormatGeneralDTO(Product val) {
        ProductPhoto productPhoto = productPhotoRepository.findFirstByProduct_Id(val.getId());
        ArrayList<ProductAssessment> productAssessments = productAssessmentRepository
                .findByProduct_Id(val.getId());
        Integer totalAssessment = productAssessmentRepository.countAllByProduct_Id(val.getId());
        Double rating = this.getRating(this.sumRate(productAssessments), totalAssessment);

        String filePhotoName = this.setFilePhotoName(productPhoto);
        String photoLink = this.setPhotoLink(filePhotoName);

        return productMapper.mapToProductGeneralDTO(val, photoLink, rating, totalAssessment);
    }

    private String setFilePhotoName(ProductPhoto productPhoto) {
        if (productPhoto != null) {
            return productPhoto.getFilename();
        }

        return null;
    }

    private String setPhotoLink(String filePhotoName) {
        try {
            return this.getImage(this.getExtensionFile(filePhotoName), this.getFilename(filePhotoName));
        } catch (IOException e) {
            return null;
        }
    }

    private Double getRating(Integer sumOfRate, Integer totalAssessment) {
        if (totalAssessment > 0) {
            DecimalFormat df = new DecimalFormat("#.##");
            DecimalFormatSymbols sys = new DecimalFormatSymbols();
            sys.setDecimalSeparator('.');
            df.setDecimalFormatSymbols(sys);
            df.setRoundingMode(RoundingMode.DOWN);

            return Double.valueOf(df.format((double) sumOfRate / totalAssessment));
        }

        return null;
    }

    private BasicResult validationIncrementSeen(IncrementSeenRequest request) {
        if (request.getProductId() == null) {
            return new BasicResult(false, "Form tidak lengkap", "BAD REQUEST", 400);
        }

        if (!this.isExistProduct(request.getProductId())) {
            return new BasicResult(false, "Produk tidak ditemukan", "NOT FOUND", 404);
        }

        return new BasicResult(true, null, "OK", 200);
    }

    private BasicResult validationGetById(Integer productId) {
        if (!this.isExistProduct(productId)) {
            return new BasicResult(false, "Produk tidak ditemukan", "NOT FOUND", 404);
        }

        return new BasicResult(true, null, "OK", 200);
    }

    private BasicResult validationGetByCategory(Integer categoryId) {
        if (!this.isExistCategory(categoryId)) {
            return new BasicResult(false, "Kategori tidak ditemukan", "NOT FOUND", 404);
        }

        return new BasicResult(true, null, "OK", 200);
    }

    private BasicResult validationGetBySubCategory(Integer subCategoryId) {
        if (!this.isExistSubCategory(subCategoryId)) {
            return new BasicResult(false, "Sub kategori tidak ditemukan", "NOT FOUND", 404);
        }

        return new BasicResult(true, null, "OK", 200);
    }

    private BasicResult validationUpdateProduct(UpdateProductRequest request) {
        if (request.getId() == null || request.getName() == null || request.getDescription() == null
        || request.getPrice() == null || request.getSubCategoryId() == null) {
            return new BasicResult(false, "Form tidak lengkap", "BAD REQUEST", 400);
        }

        if (!this.isExistProduct(request.getId())) {
            return new BasicResult(false, "Produk tidak ditemukan", "NOT FOUND", 404);
        }

        if (!this.isExistSubCategory(request.getSubCategoryId())) {
            return new BasicResult(false, "Sub kategori tidak tersedia", "NOT FOUND", 404);
        }

        return new BasicResult(true, null, "OK", 200);
    }

    private BasicResult validationSaveNewProduct(NewProductRequest request) {
        if (request.getName() == null || request.getDescription() == null
        || request.getPrice() == null || request.getSubCategoryId() == null) {
            return new BasicResult(false, "Form tidak lengkap", "BAD REQUEST", 400);
        }

        if (!this.isExistSubCategory(request.getSubCategoryId())) {
            return new BasicResult(false, "Sub kategori tidak tersedia", "NOT FOUND", 404);
        }

        return new BasicResult(true, null, "OK", 200);
    }

    private BasicResult validationUploadPhoto(Integer productId, ArrayList<MultipartFile> files) {
        if (productId == null || files.isEmpty()) {
            return new BasicResult(false, "Form tidak lengkap", "BAD REQUEST", 400);
        }

        if (!this.isExistProduct(productId)) {
            return new BasicResult(false, "Product tidak ditemukan", "NOT FOUND", 404);
        }

        return new BasicResult(true, null, "OK", 200);
    }

    private Integer sumRate(ArrayList<ProductAssessment> productAssessments) {
        final int[] rate = {0};

        productAssessments.forEach((val) -> {
            rate[0] = (int) (rate[0] + val.getRate());
        });

        return rate[0];
    }

    private Boolean isExistCategory(Integer id) {
        return productCategoryRepository.findFirstById(id) != null;
    }

    private Boolean isExistSubCategory(Integer id) {
        return productSubCategoryRepository.findFirstById(id) != null;
    }

    private Boolean isExistProduct(Integer id) {
        return productRepository.findFirstById(id) != null;
    }

    private byte[] getImageWithMediaType(String imageName) throws IOException {
        Path destination = Paths.get(projectDir + imageName);
        return IOUtils.toByteArray(destination.toUri());
    }

    private String getImage(String ext, String filename) throws IOException {
        if (ext != null && filename != null) {
            byte[] bytes =  this.getImageWithMediaType(filename + "." + ext);

            StringBuilder builder = new StringBuilder();
            builder.append("data:image/" + ext + ";base64,");
            builder.append(org.apache.tomcat.util.codec.binary
                    .StringUtils.newStringUtf8(Base64.encodeBase64(bytes, false)));

            return builder.toString();
        }

        return null;
    }

    private String getExtensionFile(String filename) {
        if (filename != null) {
            String[] split = filename.split("\\.");
            return split[split.length - 1].toLowerCase();
        }

        return null;
    }

    private String getFilename(String filename) {
        if (filename != null) {
            String[] split = filename.split("\\.");
            return split[0];
        }

        return null;
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







