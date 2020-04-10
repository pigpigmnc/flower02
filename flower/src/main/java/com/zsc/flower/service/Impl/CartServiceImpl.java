package com.zsc.flower.service.Impl;

import com.zsc.flower.dao.CartDao;
import com.zsc.flower.service.CartService;
import model.entity.Cart;
import model.entity.CartDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("cartService")
public class CartServiceImpl implements CartService {

    @Autowired
    CartDao cartDao;

    @Override
    public CartDetail findCartDetail(long id) {
        return cartDao.selectCartDetail(id);
    }

    @Override
    public int findAddCart(Cart cart) {
        return cartDao.selectAddCart(cart);
    }

    @Override
    public List<Cart> findListCartByUid(long uid) {
        return cartDao.selectListCartByUid(uid);
    }

    @Override
    public Cart findCartByUidAndPid(long uid, long pid) {
        return cartDao.selectCartByUidAndPid(uid,pid);
    }

    @Override
    public Cart findCartById(long id) {
        return cartDao.selectCartById(id);
    }

    @Override
    public void findUpdateCart(Cart cart) {
        cartDao.selectUpdateCart(cart);
    }

    @Override
    public int findDeleteCartById(long id) {
        return cartDao.selectDeleteCartById(id);
    }

    @Override
    public void findDeleteCartByUidAndPid(long uid, long pid) {
        cartDao.selectDeleteCartByUidAndPid(uid,pid);
    }
}
