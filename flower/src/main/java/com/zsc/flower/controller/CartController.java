package com.zsc.flower.controller;

import com.zsc.flower.service.CartService;
import com.zsc.flower.service.ProductImageService;
import com.zsc.flower.service.ProductService;
import com.zsc.flower.service.TokenService;
import com.zsc.flower.tag.UserLoginToken;
import com.zsc.flower.utils.token.TokenUtil;
import model.entity.Cart;
import model.entity.Product;
import model.result.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestController
@RequestMapping(value = "/cart")
public class CartController {

    @Autowired
    CartService cartService;
    @Autowired
    ProductService productService;
    @Autowired
    ProductImageService productImageService;
    @Autowired
    TokenService tokenService;

    //用户加入购物车
    @UserLoginToken
    @RequestMapping(value = "/addCart",method = RequestMethod.POST)
    public ResponseResult addCart(HttpServletRequest request, @RequestParam("pid")long pid,
                                  @RequestParam("count")int count){
        ResponseResult result = new ResponseResult();
        long uid = Long.parseLong(TokenUtil.getTokenUserId());
        int n;
        Cart oldCart=cartService.findCartByUidAndPid(uid,pid);
        if(oldCart!=null){
            long oldCount=oldCart.getCount();
            long newCount=oldCount+count;
            oldCart.setCount((int) newCount);
            Product product=productService.findProductById(pid);
            oldCart.setTotalPrice(newCount*product.getPromotePrice());
            cartService.findUpdateCart(oldCart);
            n=1;
        }
        else{
            //根据商品ID找到商品的图片路径，商品名称，商品单价
            Product product=productService.findProductById(pid);
            List<String> productImageList=productImageService.findProductImageUrlById(pid);
            Cart cart=new Cart();
            cart.setUid(uid);
            cart.setPid(pid);
            cart.setFileurlpath(productImageList.get(0));
            cart.setPname(product.getName());
            cart.setSimplePrice(product.getPromotePrice());
            cart.setCount(count);
            cart.setTotalPrice(count*product.getPromotePrice());
            n=cartService.findAddCart(cart);
        }
        if(n==1){
            result.setMsg(true);
            return result;
        }
        else{
            result.setMsg(false);
            return result;
        }
    }
    //展示用户的购物车列表
    @UserLoginToken
    @RequestMapping(value = "/listCart",method = RequestMethod.GET)
    public ResponseResult listCart(){
        ResponseResult result = new ResponseResult();
        long uid = Long.parseLong(TokenUtil.getTokenUserId());
        List<Cart> cartList=cartService.findListCartByUid(uid);
        result.setMsg(true);
        result.setData(cartList);
        return result;
    }
    //更新购物车的信息
    @RequestMapping(value = "/updateCart",method = RequestMethod.GET)
    public ResponseResult updateCart(@RequestParam("id")long id,
                                   @RequestParam("count")int count){
        ResponseResult result = new ResponseResult();
        Cart cart=cartService.findCartById(id);
        long stock=productService.findOldStock(cart.getPid());
        int flag=0;
        if(stock>=count){//库存足够就可以更新购物车
            flag=1;
            cart.setCount(count);
            cart.setTotalPrice(count*cart.getSimplePrice());
            cartService.findUpdateCart(cart);
        }
        if(flag==1){
            result.setMsg(true);
            return result;
        }
        else{
            result.setMsg(false);
            return result;
        }
    }
    //删除购物车
    @RequestMapping(value = "/deleteCart",method = RequestMethod.GET)
    public ResponseResult deleteCart(@RequestParam("id")long id){
        ResponseResult result = new ResponseResult();
        if(cartService.findDeleteCartById(id)==1){
            result.setMsg(true);
            return result;
        }
        else{
            result.setMsg(true);
            return result;
        }
    }
}
