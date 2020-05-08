package com.zsc.flower.dao;

import com.github.pagehelper.PageInfo;
import io.swagger.models.auth.In;
import model.entity.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductDao{
    public int selectAddProduct(Product product);
    public int selectAddCategory(String name);
    public List<Category> selectListCategory();
    public int selectAddProperty(@Param("cid") long cid, @Param("name") String name);
    public List<BaseDetail> selectShowBaseDetail(long id);
    public List<ExtendDetail> selectShowExtendDetail(long id);
    public List<ListProduct> selectListProduct();
    public int selectCategoryCount(long cid);

    public long selectOldStock(@Param("id") long id);

    public int selectUpdateStock(@Param("id") long id, @Param("stock") long stock);


    public long selectOldSaleCount(@Param("id") long id);

    public int selectUpdateSaleCount(@Param("id") long id, @Param("saleCount") long saleCount);

    public List<ListProduct> selectProductByCId(@Param("cid") long cid);

    public SimpleDetail selectSimpleDetail(@Param("pid") long pid);


    public List<ListProduct> selectListProductByDimSearch(@Param("name") String name);

    public Product selectProductById(@Param("id") long id);
    public int selectUpdateProduct(Product product);

    public int selectDeleteProduct(@Param("id") long id);

    public void closeforeign();

    public List<ListProduct> selectListProductByCreateDate();
    public List<ListProduct> selectListProductBySaleCount();


    long findPidByTopInsert();

    List<ListProduct> selectProductForBanner();

    int insertNewReview(Review review);

    List<Review> getProductReviewList(@Param("pid") long pid);

    Integer getProductAvgStar(@Param("pid") long pid);

    String getBannerPic(@Param("pid") long id);

    Integer getProductReviewNum(long pid);
}
