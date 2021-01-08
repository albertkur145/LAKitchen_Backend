package com.lakitchen.LA.Kitchen.service;

import com.lakitchen.LA.Kitchen.api.dto.CategoryDTO;
import com.lakitchen.LA.Kitchen.api.response.data.format.GetCategoriesAndSubFormat;
import com.lakitchen.LA.Kitchen.api.dto.SubCategoryDTO;
import com.lakitchen.LA.Kitchen.api.response.data.GetCategories;
import com.lakitchen.LA.Kitchen.api.response.ResponseTemplate;
import com.lakitchen.LA.Kitchen.data.ProductCategoryData;
import com.lakitchen.LA.Kitchen.data.ProductSubCategoryData;
import com.lakitchen.LA.Kitchen.model.entity.ProductCategory;
import com.lakitchen.LA.Kitchen.model.entity.ProductSubCategory;
import com.lakitchen.LA.Kitchen.repository.ProductCategoryRepository;
import com.lakitchen.LA.Kitchen.repository.ProductSubCategoryRepository;
import com.lakitchen.LA.Kitchen.service.impl.ProductCategoryService;
import com.lakitchen.LA.Kitchen.service.mapper.ProductCategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Autowired
    private ProductSubCategoryRepository productSubCategoryRepository;

    @Autowired
    private ProductCategoryMapper productCategoryMapper;

    @Override
    public ResponseTemplate resetData() {
        this.deleteAll();

        for (ProductCategoryData val : this.categoryData()) {
            ProductCategory productCategory = new ProductCategory();
            productCategory.setName(val.getName());
            productCategoryRepository.save(productCategory);

            for (ProductSubCategoryData subVal : val.getSubCategoryData()) {
                ProductSubCategory productSubCategory = new ProductSubCategory();
                ProductCategory curData = productCategoryRepository.findFirstByName(val.getName());

                productSubCategory.setName(subVal.getName());
                productSubCategory.setProductCategory(curData);

                productSubCategoryRepository.save(productSubCategory);
            }
        }

        return new ResponseTemplate(200, "OK", null, null, null);
    }

    @Override
    public ResponseTemplate getCategories() {
        ArrayList<ProductCategory> productCategoryList
                = (ArrayList<ProductCategory>) productCategoryRepository
                .findAll(Sort.by("name").ascending());

        if (!productCategoryList.isEmpty()) {
            ArrayList<CategoryDTO> categoryDTOS = new ArrayList<>();

            for (ProductCategory val : productCategoryList) {
                categoryDTOS.add(productCategoryMapper.mapToCategoryDTO(val));
            }

            return new ResponseTemplate(
                    200, "OK",
                    new GetCategories(categoryDTOS),
                    null, null
            );
        }

        return new ResponseTemplate(
                404, "NOT FOUND", null,
                null, "Data is empty"
        );
    }

    @Override
    public ResponseTemplate getCategoriesAndSub() {
        ArrayList<ProductCategory> productCategoryList
                = (ArrayList<ProductCategory>) productCategoryRepository
                .findAll(Sort.by("name").ascending());

        if (!productCategoryList.isEmpty()) {
            ArrayList<GetCategoriesAndSubFormat> getCategoriesAndSubFormats = new ArrayList<>();

            for (ProductCategory val : productCategoryList) {
                CategoryDTO categoryDTO = new CategoryDTO(val.getId(), val.getName());
                ArrayList<ProductSubCategory> productSubCategoryList
                        = (ArrayList<ProductSubCategory>) productSubCategoryRepository
                        .findByProductCategory_IdOrderByNameAsc(val.getId());

                ArrayList<SubCategoryDTO> subCategoryDTOS = new ArrayList<>();

                for (ProductSubCategory subVal : productSubCategoryList) {
                    SubCategoryDTO subCategoryDTO = new SubCategoryDTO(subVal.getId(), subVal.getName());
                    subCategoryDTOS.add(subCategoryDTO);
                }

                getCategoriesAndSubFormats.add(productCategoryMapper
                        .mapToCategoryAndSub(categoryDTO, subCategoryDTOS));
            }

            return new ResponseTemplate(
                    200, "OK",
                    getCategoriesAndSubFormats,
                    null, null
            );
        }

        return new ResponseTemplate(
                404, "NOT FOUND", null,
                null, "Data is empty"
        );
    }

    private void deleteAll() {
        productSubCategoryRepository.deleteAll();
        productCategoryRepository.deleteAll();
    }

    private ProductCategoryData[] categoryData() {
        ProductCategoryData[] data = {
                new ProductCategoryData("Bahan Kue", this.bahanKue()),
                new ProductCategoryData("Beras", this.beras()),
                new ProductCategoryData("Bumbu & Bahan Masakan", this.bumbuBahanMasakan()),
                new ProductCategoryData("Kopi", this.kopi()),
                new ProductCategoryData("Kue", this.kue()),
                new ProductCategoryData("Makanan Beku", this.makananBeku()),
                new ProductCategoryData("Makanan Jadi", this.makananJadi()),
                new ProductCategoryData("Makanan Kaleng", this.makananKaleng()),
                new ProductCategoryData("Makanan Kering", this.makananKering()),
                new ProductCategoryData("Makanan Ringan", this.makananRingan()),
                new ProductCategoryData("Makanan Sarapan", this.makananSarapan()),
                new ProductCategoryData("Mie & Pasta", this.miePasta())
        };

        return data;
    }

    private ProductSubCategoryData[] bahanKue() {
        ProductSubCategoryData[] data = {
                new ProductSubCategoryData("Bahan Puding & Agar-Agar"),
                new ProductSubCategoryData("Bahan Powder & Soda"),
                new ProductSubCategoryData("Coklat Bubuk"),
                new ProductSubCategoryData("Coklat Masak"),
                new ProductSubCategoryData("Perisa Makanan"),
                new ProductSubCategoryData("Pewarna Makanan"),
                new ProductSubCategoryData("Ragi"),
                new ProductSubCategoryData("Topping & Penghias Kue")
        };

        return data;
    }

    private ProductSubCategoryData[] beras() {
        ProductSubCategoryData[] data = {
                new ProductSubCategoryData("Beras Hitam"),
                new ProductSubCategoryData("Beras Ketan"),
                new ProductSubCategoryData("Beras Merah"),
                new ProductSubCategoryData("Beras Putih")
        };

        return data;
    }

    private ProductSubCategoryData[] bumbuBahanMasakan() {
        ProductSubCategoryData[] data = {
                new ProductSubCategoryData("Aneka Sambal"),
                new ProductSubCategoryData("Bawang"),
                new ProductSubCategoryData("Bumbu Masak Instan"),
                new ProductSubCategoryData("Cuka"),
                new ProductSubCategoryData("Garam & Merica"),
                new ProductSubCategoryData("Gula"),
                new ProductSubCategoryData("Kaldu & Penyedap Rasa"),
                new ProductSubCategoryData("Kecap"),
                new ProductSubCategoryData("Mayonnaise"),
                new ProductSubCategoryData("Minyak"),
                new ProductSubCategoryData("Rempah"),
                new ProductSubCategoryData("Santan"),
                new ProductSubCategoryData("Saus & Dressing"),
                new ProductSubCategoryData("Terasi"),
        };

        return data;
    }

    private ProductSubCategoryData[] kopi() {
        ProductSubCategoryData[] data = {
                new ProductSubCategoryData("Biji Kopi"),
                new ProductSubCategoryData("Kopi Bubuk"),
                new ProductSubCategoryData("Kopi Kemasan"),
                new ProductSubCategoryData("Krimer")
        };

        return data;
    }

    private ProductSubCategoryData[] kue() {
        ProductSubCategoryData[] data = {
                new ProductSubCategoryData("Kue Basah"),
                new ProductSubCategoryData("Kue Kering"),
                new ProductSubCategoryData("Kue Ulang Tahun")
        };

        return data;
    }

    private ProductSubCategoryData[] makananBeku() {
        ProductSubCategoryData[] data = {
                new ProductSubCategoryData("Bakso"),
                new ProductSubCategoryData("Buah Beku"),
                new ProductSubCategoryData("Camilan Beku"),
                new ProductSubCategoryData("Daging Olahan Lainnya"),
                new ProductSubCategoryData("Dessert"),
                new ProductSubCategoryData("Kentang Beku"),
                new ProductSubCategoryData("Nugget"),
                new ProductSubCategoryData("Pastry & Olahan Tepung"),
                new ProductSubCategoryData("Sayuran Beku"),
                new ProductSubCategoryData("Siomay"),
                new ProductSubCategoryData("Sosis")
        };

        return data;
    }

    private ProductSubCategoryData[] makananJadi() {
        ProductSubCategoryData[] data = {
                new ProductSubCategoryData("Buah Olahan"),
                new ProductSubCategoryData("Lauk Pauk"),
                new ProductSubCategoryData("Makanan Asin"),
                new ProductSubCategoryData("Makanan Manis"),
                new ProductSubCategoryData("Sayur Olahan")
        };

        return data;
    }

    private ProductSubCategoryData[] makananKaleng() {
        ProductSubCategoryData[] data = {
                new ProductSubCategoryData("Buah Kaleng"),
                new ProductSubCategoryData("Daging Kaleng"),
                new ProductSubCategoryData("Ikan Kaleng"),
                new ProductSubCategoryData("Makanan Instan Kaleng")
        };

        return data;
    }

    private ProductSubCategoryData[] makananKering() {
        ProductSubCategoryData[] data = {
                new ProductSubCategoryData("Abon"),
                new ProductSubCategoryData("Bawang Goreng"),
                new ProductSubCategoryData("Daging Kering & Asin"),
                new ProductSubCategoryData("Kerupuk")
        };

        return data;
    }

    private ProductSubCategoryData[] makananRingan() {
        ProductSubCategoryData[] data = {
                new ProductSubCategoryData("Biskuit & Wafer"),
                new ProductSubCategoryData("Camilan Instant"),
                new ProductSubCategoryData("Cokelat"),
                new ProductSubCategoryData("Kacang"),
                new ProductSubCategoryData("Keripik"),
                new ProductSubCategoryData("Permen"),
                new ProductSubCategoryData("Puding & Jelly")
        };

        return data;
    }

    private ProductSubCategoryData[] makananSarapan() {
        ProductSubCategoryData[] data = {
                new ProductSubCategoryData("Granola"),
                new ProductSubCategoryData("Meses"),
                new ProductSubCategoryData("Muesli"),
                new ProductSubCategoryData("Oat"),
                new ProductSubCategoryData("Quinoa"),
                new ProductSubCategoryData("Roti"),
                new ProductSubCategoryData("Selai"),
                new ProductSubCategoryData("Sereal")
        };

        return data;
    }

    private ProductSubCategoryData[] miePasta() {
        ProductSubCategoryData[] data = {
                new ProductSubCategoryData("Aneka Pasta"),
                new ProductSubCategoryData("Bihun & Soun"),
                new ProductSubCategoryData("Kwetiau"),
                new ProductSubCategoryData("Mie Impor"),
                new ProductSubCategoryData("Mie Instan"),
                new ProductSubCategoryData("Mie Telur")
        };

        return data;
    }
}
