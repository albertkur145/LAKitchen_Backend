package com.lakitchen.LA.Kitchen.api.requestbody.role_admin.product;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

@Data
public class UploadPhotoRequest {
    Integer productId;
    ArrayList<MultipartFile> images;
}
