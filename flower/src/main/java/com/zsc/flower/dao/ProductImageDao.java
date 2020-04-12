package com.zsc.flower.dao;

import model.entity.ProductImage;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Repository
public interface ProductImageDao{
    public int selectInsertProductImages(ProductImage productImage);
    public List<String> selectPicListByPID(@RequestParam("pid") long pid);

    public List<String> selectProductImageUrlById(@RequestParam("id") long id);

    public String selectIndexImageNull(@Param("id") long id);
    public String selectViewImageNull(@Param("id") long id);

    int selectTopSelect(@Param("pid") long pid,@Param("type") byte type);
}
