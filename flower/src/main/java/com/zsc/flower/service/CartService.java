package com.zsc.flower.service;

import model.entity.Cart;
import model.entity.CartDetail;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CartService {
    public CartDetail findCartDetail(long id);
    public int findAddCart(Cart cart);
    public List<Cart> findListCartByUid(long uid);
    public Cart findCartByUidAndId(long uid, long pid);
    public Cart findCartById(long id);
    public void findUpdateCart(Cart cart);
    public int findDeleteCartById(long id);
    public void findDeleteCartByUidAndPid(long uid, long pid);

    Cart findCartByUidAndPid(long uid, long pid);
}
