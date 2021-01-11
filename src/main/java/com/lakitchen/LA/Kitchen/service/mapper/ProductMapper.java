package com.lakitchen.LA.Kitchen.service.mapper;

import com.lakitchen.LA.Kitchen.api.dto.IdNameDTO;
import com.lakitchen.LA.Kitchen.api.dto.ProductAssessmentDTO;
import com.lakitchen.LA.Kitchen.api.dto.ProductPhotoDTO;
import com.lakitchen.LA.Kitchen.api.dto.ProductDetailDTO;
import com.lakitchen.LA.Kitchen.api.dto.ProductGeneralDTO;
import com.lakitchen.LA.Kitchen.model.entity.Product;
import com.lakitchen.LA.Kitchen.model.entity.ProductAssessment;
import com.lakitchen.LA.Kitchen.model.entity.ProductPhoto;
import com.lakitchen.LA.Kitchen.repository.ProductAssessmentRepository;
import com.lakitchen.LA.Kitchen.repository.ProductPhotoRepository;
import com.lakitchen.LA.Kitchen.repository.UserRepository;
import org.apache.commons.io.IOUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
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
    UserRepository userRepository;

    @Autowired
    ProductPhotoRepository productPhotoRepository;

    @Autowired
    ProductAssessmentRepository productAssessmentRepository;

    private String projectDir = "D:/project/LA Kitchen/Backend/src/uploads/";

    public String getProjectDir() {
        return this.projectDir;
    }

    public ProductGeneralDTO mapToProductGeneralDTO(Product product, String photoLink, Double rating, Integer totalAssessment) {
        return new ProductGeneralDTO(
                product.getId(),
                product.getName(),
                product.getPrice(),
                photoLink,
                rating,
                totalAssessment
        );
    }

    public ProductDetailDTO mapToProductDetailDTO(Product product, ArrayList<ProductPhotoDTO> productPhotoDTOS,
                                                  ArrayList<ProductAssessment> productAssessments,
                                                  Double rating, Integer totalAssessment) {
        ArrayList<ProductAssessmentDTO> assessmentDTOS = new ArrayList<>();
        productAssessments.forEach((val) -> {
            if (val.getDeletedAt() == null) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                String dateOfAssessment = dateFormat.format(val.getCreatedAt());
                IdNameDTO userDTO = new IdNameDTO(val.getUser().getId(), val.getUser().getName());
                ProductAssessmentDTO dto = new ProductAssessmentDTO(val.getId(), userDTO,
                        val.getRate().intValue(), val.getComment(), dateOfAssessment);
                assessmentDTOS.add(dto);
            }
        });

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

    public ProductGeneralDTO setToFormatGeneralDTO(Product val) {
        ProductPhoto productPhoto = productPhotoRepository.findFirstByProduct_Id(val.getId());
        ArrayList<ProductAssessment> productAssessments = productAssessmentRepository
                .findByProduct_Id(val.getId());
        Integer totalAssessment = productAssessmentRepository.countAllByProduct_Id(val.getId());
        Double rating = this.getRating(this.sumRate(productAssessments), totalAssessment);

        String filePhotoName = this.setFilePhotoName(productPhoto);
        String photoLink = this.setPhotoLink(filePhotoName);

        return this.mapToProductGeneralDTO(val, photoLink, rating, totalAssessment);
    }

    public String setFilePhotoName(ProductPhoto productPhoto) {
        if (productPhoto != null) {
            return productPhoto.getFilename();
        }

        return null;
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

}