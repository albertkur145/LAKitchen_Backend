package com.lakitchen.LA.Kitchen.service;


import com.lakitchen.LA.Kitchen.api.dto.*;
import com.lakitchen.LA.Kitchen.api.requestbody.role_admin.product.ActivationProductRequest;
import com.lakitchen.LA.Kitchen.api.requestbody.role_admin.product.NewProductRequest;
import com.lakitchen.LA.Kitchen.api.requestbody.role_admin.product.UpdateProductRequest;
import com.lakitchen.LA.Kitchen.api.requestbody.role_user.product.IncrementSeenRequest;
import com.lakitchen.LA.Kitchen.api.response.ResponseTemplate;
import com.lakitchen.LA.Kitchen.api.response.data.role_admin.product.*;
import com.lakitchen.LA.Kitchen.api.response.data.role_user.product.*;
import com.lakitchen.LA.Kitchen.api.dto.ProductTopFavoriteDTO;
import com.lakitchen.LA.Kitchen.model.entity.*;
import com.lakitchen.LA.Kitchen.repository.*;
import com.lakitchen.LA.Kitchen.service.global.Func;
import com.lakitchen.LA.Kitchen.service.impl.ProductService;
import com.lakitchen.LA.Kitchen.service.mapper.ProductMapper;
import com.lakitchen.LA.Kitchen.validation.BasicResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

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
    private WishlistRepository wishlistRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private Func FUNC;

    @Override
    public ResponseTemplate getBySubCategory(Integer subCategoryId) {
        BasicResult result = this.validationGetBySubCategory(subCategoryId);

        if (result.getResult()) {
            ArrayList<Product> products = productRepository
                    .findByProductSubCategory_IdAndDeletedAt(subCategoryId, null);
            ArrayList<ProductGeneralDTO> productDTO = new ArrayList<>();
            ProductSubCategory subCategory = productSubCategoryRepository.findFirstById(subCategoryId);

            products.forEach((val) -> {
                ProductGeneralDTO dto = productMapper.setToFormatGeneralDTO(val);
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
                ProductGeneralDTO dto = productMapper.setToFormatGeneralDTO(val);
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
            ProductGeneralDTO dto = productMapper.setToFormatGeneralDTO(val);
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
            ProductGeneralDTO dto = productMapper.setToFormatGeneralDTO(val);
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
                    .findByProduct_IdOrderByCreatedAtDescRateDesc(productId);

            ArrayList<ProductPhotoDTO> photoDTOS = new ArrayList<>();
            photos.forEach((val) -> {
                photoDTOS.add(productMapper.mapToProductPhotoDTO(val));
            });

            Integer totalAssessment = productAssessmentRepository.countAllByProduct_Id(product.getId());
            Double rating = productMapper.getRating(productMapper.sumRate(assessments), totalAssessment);
            ProductDetailDTO productDetailDTO = productMapper
                    .mapToProductDetailDTO(product, photoDTOS, assessments, rating, totalAssessment);
            PathDTO pathDTO = productMapper.mapToPathDTO(product);

            return new ResponseTemplate(200, "OK",
                    new GetById(pathDTO, productDetailDTO),
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
                String ext = productMapper.getExtensionFile(filename);

                filename = uuid + "." + ext;

                ProductPhoto productPhoto = new ProductPhoto();
                productPhoto.setFilename(filename);
                productPhoto.setProduct(productRepository.findFirstById(productId));
                productPhotoRepository.save(productPhoto);

                try {
                    this.saveFile(productMapper.getProjectDir(), filename, file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            return new ResponseTemplate(200, "OK", null, null, null);
        }

        return new ResponseTemplate(result.getCode(), result.getStatus(), null, null, result.getError());
    }

    @Override
    public ResponseTemplate deletePhoto(Integer photoId) {
        if (productPhotoRepository.findFirstById(photoId) != null) {
            productPhotoRepository.deleteById(photoId);
            return new ResponseTemplate(200, "OK", null, null, null);
        }
        return new ResponseTemplate(404, "NOT FOUND", null, null, "Photo tidak ditemukan");
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
            product.setCreatedAt(FUNC.getCurrentTimestamp());
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
            product.setUpdatedAt(FUNC.getCurrentTimestamp());
            productRepository.save(product);

            return new ResponseTemplate(200, "OK",
                    null, null, null);
        }

        return new ResponseTemplate(result.getCode(), result.getStatus(), null, null, result.getError());
    }

    @Override
    public ResponseTemplate getAll(Integer page) {
        Page<Product> products = productRepository
                .findAll(PageRequest.of((page-1), 10, Sort.by("name").ascending()));
        ArrayList<ProductAdminDTO> productDTOS = new ArrayList<>();

        products.getContent().forEach((val) -> {
            productDTOS.add(this.helperMapToProductAdminDTO(val));
        });
        PageableDTO pageableDTO = FUNC.mapToPageableDTO(products);

        return new ResponseTemplate(200, "OK",
                new GetAll(productDTOS), pageableDTO, null);
    }

    @Override
    public ResponseTemplate getByIdAdmin(Integer productId) {
        BasicResult result = this.validationGetByIdAdmin(productId);

        if (result.getResult()) {
            Product product = productRepository.findFirstById(productId);
            ArrayList<ProductPhoto> photos = productPhotoRepository.findByProduct_Id(productId);

            ArrayList<ProductPhotoDTO> photoDTOS = new ArrayList<>();
            photos.forEach((val) -> {
                photoDTOS.add(productMapper.mapToProductPhotoDTO(val));
            });
            ProductDetail2DTO dto = productMapper.mapToProductDetail2DTO(product, photoDTOS);

            return new ResponseTemplate(200, "OK",
                    new GetByIdAdmin(dto), null, null);
        }

        return new ResponseTemplate(result.getCode(), result.getStatus(), null, null, result.getError());
    }

    @Override
    public ResponseTemplate getTopFavorite() {
        ArrayList<ProductTopFavoriteDTO> dto = wishlistRepository.findBestFavoriteProduct(10);
        return new ResponseTemplate(200, "OK",
                new GetTopFavorite(dto), null, null);
    }

    @Override
    public ResponseTemplate getTopSelling() {
        ArrayList<ProductTopSellingDTO> dto = orderDetailRepository.findBestSellingProduct(10);
        return new ResponseTemplate(200, "OK",
                new GetTopSelling(dto), null, null);
    }

    @Override
    public ResponseTemplate getTopRating() {
        ArrayList<ProductTopRatingDTO> dto = productAssessmentRepository.findTopRatingProduct(10);
        return new ResponseTemplate(200, "OK",
                new GetTopRating(dto), null, null);
    }

    @Override
    public ResponseTemplate getTopFavoriteByCategory(Integer categoryId) {
        ArrayList<ProductTopFavoriteCategoryDTO> dto
                = wishlistRepository.findBestFavoriteProductByCategory(5, categoryId);
        return new ResponseTemplate(200, "OK",
                new GetTopFavoriteByCategory(dto), null, null);
    }

    @Override
    public ResponseTemplate getTopSellingByCategory(Integer categoryId) {
        ArrayList<ProductTopSellingCategoryDTO> dto
                = orderDetailRepository.findBestSellingProductByCategory(5, categoryId);
        return new ResponseTemplate(200, "OK",
                new GetTopSellingByCategory(dto), null, null);
    }

    @Override
    public ResponseTemplate getTopRatingByCategory(Integer categoryId) {
        ArrayList<ProductTopRatingCategoryDTO> dto
                = productAssessmentRepository.findTopRatingProductByCategory(5, categoryId);
        return new ResponseTemplate(200, "OK",
                new GetTopRatingByCategory(dto), null, null);
    }

    @Override
    public ResponseTemplate getByCategoryAdmin(Integer page, Integer categoryId) {
        Pageable paging = PageRequest.of((page-1), 10, Sort.by("name").ascending());
        Page<Product> products = productRepository
                .findByCategoryAdmin(categoryId, paging);
        ArrayList<ProductAdminDTO> productDTOS = new ArrayList<>();

        products.getContent().forEach((val) -> {
            productDTOS.add(this.helperMapToProductAdminDTO(val));
        });
        PageableDTO pageableDTO = FUNC.mapToPageableDTO(products);

        return new ResponseTemplate(200, "OK",
                new GetByCategoryAdmin(productDTOS), pageableDTO, null);
    }

    @Override
    public ResponseTemplate getByNameAdmin(Integer page, String productName) {
        Pageable paging = PageRequest.of((page-1), 10, Sort.by("name").ascending());
        Page<Product> products = productRepository
                .findByNameIgnoreCaseContaining(productName, paging);
        ArrayList<ProductAdminDTO> productDTOS = new ArrayList<>();

        products.getContent().forEach((val) -> {
            productDTOS.add(this.helperMapToProductAdminDTO(val));
        });
        PageableDTO pageableDTO = FUNC.mapToPageableDTO(products);

        return new ResponseTemplate(200, "OK", new GetByNameAdmin(productDTOS), pageableDTO, null);
    }

    @Override
    public ResponseTemplate deleteProduct(Integer productId) {
        BasicResult result = this.validationDeleteProduct(productId);

        if (result.getResult()) {
            Product product = productRepository.findFirstById(productId);
            product.setDeletedAt(FUNC.getCurrentTimestamp());
            productRepository.save(product);
            return new ResponseTemplate(200, "OK", null, null, null);
        }
        return new ResponseTemplate(result.getCode(), result.getStatus(), null, null, result.getError());
    }

    @Override
    public ResponseTemplate activationProduct(ActivationProductRequest request) {
        BasicResult result = this.validationActivationProduct(request.getId());

        if (result.getResult()) {
            Product product = productRepository.findFirstById(request.getId());
            product.setDeletedAt(null);
            productRepository.save(product);
            return new ResponseTemplate(200, "OK", null, null, null);
        }
        return new ResponseTemplate(result.getCode(), result.getStatus(), null, null, result.getError());
    }

    private ProductAdminDTO helperMapToProductAdminDTO(Product val) {
        ArrayList<ProductAssessment> assessments = productAssessmentRepository
                .findByProduct_IdOrderByCreatedAtDescRateDesc(val.getId());
        Integer totalAssessment = productAssessmentRepository.countAllByProduct_Id(val.getId());
        Integer popularity = wishlistRepository.countByProduct_Id(val.getId());
        ArrayList<OrderDetail> orderDetails = orderDetailRepository
                .findByProduct_IdAndOrder_OrderStatus_Id(val.getId(), 5);
        Integer sold = this.getSumProductSold(orderDetails);
        Double rating = productMapper.getRating(productMapper.sumRate(assessments), totalAssessment);
        return productMapper.mapToProductAdminDTO(val, rating, popularity, sold);
    }

    private Integer getSumProductSold(ArrayList<OrderDetail> orderDetails) {
        final int[] sum = {0};
        orderDetails.forEach((val) -> {
            sum[0] += val.getQuantity();
        });
        return sum[0];
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

    private BasicResult validationDeleteProduct(Integer productId) {
        if (!FUNC.isExistProduct(productId)) {
            return new BasicResult(false, "Product tidak ditemukan", "NOT FOUND", 404);
        }
        return new BasicResult(true, null, "OK", 200);
    }

    private BasicResult validationActivationProduct(Integer productId) {
        if (!FUNC.isExistProduct(productId)) {
            return new BasicResult(false, "Product tidak ditemukan", "NOT FOUND", 404);
        }
        return new BasicResult(true, null, "OK", 200);
    }

    private BasicResult validationGetByIdAdmin(Integer productId) {
        if (!FUNC.isExistProduct(productId)) {
            return new BasicResult(false, "Product tidak ditemukan", "NOT FOUND", 404);
        }
        return new BasicResult(true, null, "OK", 200);
    }

    private BasicResult validationIncrementSeen(IncrementSeenRequest request) {
        if (request.getProductId() == null) {
            return new BasicResult(false, "Form tidak lengkap", "BAD REQUEST", 400);
        }

        if (!FUNC.isExistProduct(request.getProductId())) {
            return new BasicResult(false, "Produk tidak ditemukan", "NOT FOUND", 404);
        }

        return new BasicResult(true, null, "OK", 200);
    }

    private BasicResult validationGetById(Integer productId) {
        if (!FUNC.isExistProduct(productId)) {
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

        if (!FUNC.isExistProduct(request.getId())) {
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

        if (!FUNC.isExistProduct(productId)) {
            return new BasicResult(false, "Produk tidak ditemukan", "NOT FOUND", 404);
        }

        return new BasicResult(true, null, "OK", 200);
    }

    private Boolean isExistCategory(Integer id) {
        return productCategoryRepository.findFirstById(id) != null;
    }

    private Boolean isExistSubCategory(Integer id) {
        return productSubCategoryRepository.findFirstById(id) != null;
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







