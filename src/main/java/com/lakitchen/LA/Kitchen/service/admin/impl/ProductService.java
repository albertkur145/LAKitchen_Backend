package com.lakitchen.LA.Kitchen.service.admin.impl;

import com.lakitchen.LA.Kitchen.api.response.ResponseTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

public interface ProductService {
    ResponseTemplate uploadPhoto(ArrayList<MultipartFile> files);
}
