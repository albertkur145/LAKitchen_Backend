package com.lakitchen.LA.Kitchen.service.mapper;

import com.lakitchen.LA.Kitchen.api.dto.*;
import com.lakitchen.LA.Kitchen.model.entity.*;
import com.lakitchen.LA.Kitchen.repository.ProductAssessmentRepository;
import com.lakitchen.LA.Kitchen.repository.ProductPhotoRepository;
import org.apache.commons.io.IOUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.RoundingMode;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

@Service
public class ProductMapper {

    @Autowired
    private ProductPhotoRepository productPhotoRepository;

    @Autowired
    private ProductAssessmentRepository productAssessmentRepository;

    private String projectDir = "D:/project/LA Kitchen/Backend/src/uploads/";

    public String getProjectDir() {
        return this.projectDir;
    }

    private ProductGeneralDTO mapToProductGeneralDTO(Product product, String photoLink, Double rating, Integer totalAssessment) {
        return new ProductGeneralDTO(
                product.getId(),
                product.getName(),
                product.getPrice(),
                photoLink,
                rating,
                totalAssessment
        );
    }

    public ProductAdminDTO mapToProductAdminDTO(Product product, Double rating,
                                                Integer popularity, Integer sold) {
        return new ProductAdminDTO(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getProductSubCategory().getProductCategory().getName(),
                product.getProductSubCategory().getName(),
                popularity,
                rating,
                sold,
                this.isActiveProduct(product)
        );
    }

    public ProductDetailDTO mapToProductDetailDTO(Product product, ArrayList<ProductPhotoDTO> productPhotoDTOS,
                                                  ArrayList<ProductAssessment> productAssessments,
                                                  Double rating, Integer totalAssessment) {
        ArrayList<ProductAssessmentDTO> assessmentDTOS = new ArrayList<>();
        for (int i = 0; i < productAssessments.size() ; i++) {
            if (i > 2) {
                break;
            }

            ProductAssessment val = productAssessments.get(i);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String dateOfAssessment = dateFormat.format(val.getCreatedAt());
            IdNameDTO userDTO = new IdNameDTO(val.getUser().getId(), val.getUser().getName());
            ProductAssessmentDTO dto = new ProductAssessmentDTO(val.getId(), userDTO,
                    val.getRate().intValue(), val.getComment(), dateOfAssessment);
            assessmentDTOS.add(dto);
        }

        return new ProductDetailDTO(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getDescription(),
                product.getSeen(),
                rating,
                totalAssessment,
                productPhotoDTOS,
                assessmentDTOS
        );
    }

    public ProductDetail2DTO mapToProductDetail2DTO(Product product,
                                                    ArrayList<ProductPhotoDTO> productPhotoDTOS) {
        return new ProductDetail2DTO(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getDescription(),
                product.getProductSubCategory().getProductCategory().getId(),
                product.getProductSubCategory().getId(),
                this.isActiveProduct(product),
                productPhotoDTOS
        );
    }

    public ProductPhotoDTO mapToProductPhotoDTO(ProductPhoto photo) {
        String filePhotoName = this.setFilePhotoName(photo);
        String photoLink = this.setPhotoLink(filePhotoName);
        return new ProductPhotoDTO(photo.getId(), photoLink);
    }

    public ProductCartDTO mapToProductCartDTO(Product product, Cart cart) {
        return new ProductCartDTO(
                product.getId(),
                product.getName(),
                product.getPrice(),
                this.getPhotoLink(product.getId()),
                cart.getQuantity(),
                cart.getNote()
        );
    }

    public ProductSimplifiedDTO mapToProductSimplifiedDTO(Product product) {
        return new ProductSimplifiedDTO(
                product.getId(),
                product.getName(),
                this.getPhotoLink(product.getId())
        );
    }

    public ProductOrder2DTO mapToProductOrder2DTO(OrderDetail orderDetail) {
        return new ProductOrder2DTO(
                orderDetail.getProduct().getId(),
                orderDetail.getProduct().getName(),
                orderDetail.getPrice(),
                orderDetail.getQuantity(),
                this.getPhotoLink(orderDetail.getProduct().getId()),
                orderDetail.getIsAssessment()
        );
    }

    public PathDTO mapToPathDTO(Product product) {
        ProductCategory category = product.getProductSubCategory().getProductCategory();
        ProductSubCategory subCategory = product.getProductSubCategory();
        return new PathDTO(
                new IdNameDTO(category.getId(), category.getName()),
                new IdNameDTO(subCategory.getId(), subCategory.getName())
        );
    }

    public ProductGeneralDTO setToFormatGeneralDTO(Product val) {
        ArrayList<ProductAssessment> productAssessments = productAssessmentRepository
                .findByProduct_Id(val.getId());
        Integer totalAssessment = productAssessmentRepository.countAllByProduct_Id(val.getId());
        Double rating = this.getRating(this.sumRate(productAssessments), totalAssessment);
        String photoLink = this.getPhotoLink(val.getId());

        return this.mapToProductGeneralDTO(val, photoLink, rating, totalAssessment);
    }

    public String setFilePhotoName(ProductPhoto productPhoto) {
        if (productPhoto != null) {
            return productPhoto.getFilename();
        }

        return null;
    }

    public String getPhotoLink(Integer productId) {
        ProductPhoto productPhoto = productPhotoRepository.findFirstByProduct_Id(productId);
        String filePhotoName = this.setFilePhotoName(productPhoto);
        return this.setPhotoLink(filePhotoName);
    }

    public String setPhotoLink(String filePhotoName) {
        try {
            return this.getImage(this.getExtensionFile(filePhotoName), this.getFilename(filePhotoName));
        } catch (IOException e) {
            return null;
        }
    }

    public Double getRating(Integer sumOfRate, Integer totalAssessment) {
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

    public Integer sumRate(ArrayList<ProductAssessment> productAssessments) {
        final int[] rate = {0};

        productAssessments.forEach((val) -> {
            rate[0] = (int) (rate[0] + val.getRate());
        });

        return rate[0];
    }

    public String getImage(String ext, String filename) throws IOException {
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

    public byte[] getImageWithMediaType(String imageName) throws IOException {
        Path destination = Paths.get(projectDir + imageName);
        return IOUtils.toByteArray(destination.toUri());
    }

    public String getExtensionFile(String filename) {
        if (filename != null) {
            String[] split = filename.split("\\.");
            return split[split.length - 1].toLowerCase();
        }

        return null;
    }

    public String getFilename(String filename) {
        if (filename != null) {
            String[] split = filename.split("\\.");
            return split[0];
        }

        return null;
    }

    public Integer isActiveProduct(Product product) {
        if (product.getDeletedAt() == null) {
            return 1;
        }
        return 0;
    }

}
