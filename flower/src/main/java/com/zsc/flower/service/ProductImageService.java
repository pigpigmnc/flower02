package com.zsc.flower.service;

import model.entity.ProductImage;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductImageService {
    public int findInsertProductImages(ProductImage productImage);
    //2019-7-13 10:00
    public List<String> findPicListByPID(long pid);

    public List<String> findProductImageUrlById(long id);

    int findTopSort(long pid,byte type);
}
