package com.zsc.flower.dao;

import model.entity.Cart;
import model.entity.CartDetail;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartDao{
    public CartDetail selectCartDetail(long id);
    public int selectAddCart(Cart cart);
    public List<Cart> selectListCartByUid(@Param("uid") long uid);
    public Cart selectCartByUidAndPid(@Param("uid") long uid, @Param("pid") long pid);
    public Cart selectCartById(@Param("id") long id);
    public void selectUpdateCart(Cart cart);
    public int selectDeleteCartById(@Param("id") long id);
    public void selectDeleteCartByUidAndPid(@Param("uid") long uid, @Param("pid") long pid);

    Cart selectCartByUidAndId(@Param("uid")long uid, @Param("id") long id);
}
