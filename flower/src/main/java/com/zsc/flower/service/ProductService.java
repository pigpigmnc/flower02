package com.zsc.flower.service;

import com.github.pagehelper.PageInfo;
import model.entity.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {
    public int findAddProduct(Product product);
    public int findAddCategory(String name);
    public List<Category> findListCategory();
    public int findAddProperty(long cid, String name);
    public List<BaseDetail> findShowBaseDetail(long id);
    public List<ExtendDetail> findShowExtendDetail(long id);
    public PageInfo<ListProduct> findListProduct(int page, int size);
    public int findCategoryCount(long cid);
    public long findOldStock(long id);
    public int findUpdateStock(long id, long stock);
    public long findOldSaleCount(long id);
    public int findUpdateSaleCount(long id, long stock);

    List<ListProduct> findProductsByCId(long cid);//yi

    public SimpleDetail findSimpleDetail(long id);
    public List<ListProduct> findListProductByDimSearch(String name);

    public Product findProductById(long id);
    public int findUpdateProduct(Product product);

    public int findDeleteProduct(long id);

    public void closeforeign();

    public List<ListProduct> findListProductByCreateDate();
    public List<ListProduct> findListProductBySaleCount();

    public PageInfo<ListProduct> findSort(int page, int size, long cid, String sort);

    long findPidByTopInsert();
}
