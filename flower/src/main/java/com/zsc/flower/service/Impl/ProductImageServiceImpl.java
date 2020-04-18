package com.zsc.flower.service.Impl;

import com.zsc.flower.dao.ProductImageDao;
import com.zsc.flower.service.ProductImageService;
import model.entity.ProductImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("ProductImageService")
public class ProductImageServiceImpl implements ProductImageService {
    @Autowired
    ProductImageDao productImageDao;

    @Override
    public int findInsertProductImages(ProductImage productImage) {
        return productImageDao.selectInsertProductImages(productImage);
    }

    @Override
    public List<String> findPicListByPID(long pid) {
        return productImageDao.selectPicListByPID(pid);
    }

    @Override
    public List<String> findProductImageUrlById(long id) {
        return productImageDao.selectProductImageUrlById(id);
    }

    @Override
    public Integer findTopSort(long pid, byte type) {
        return productImageDao.selectTopSelect(pid,type);
    }
}
