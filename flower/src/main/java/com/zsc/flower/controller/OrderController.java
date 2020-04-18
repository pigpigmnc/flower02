package com.zsc.flower.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zsc.flower.config.AlipayConfig;
import com.zsc.flower.service.*;
import com.zsc.flower.tag.UserLoginToken;
import com.zsc.flower.utils.token.TokenUtil;
import model.entity.*;
import model.result.ResponseDataPay;
import model.result.ResponseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    OrderService orderService;
    @Autowired
    CartService cartService;
    //增加对库存和销量的处理2019-7-12 11:27
    @Autowired
    ProductService productService;
    @Autowired
    UsersService usersService;
    @Autowired
    TokenService tokenService;

    @RequestMapping(value = "/getTurnOver",method = RequestMethod.POST)
    public ResponseResult getTurnOver(){
        ResponseResult result = new ResponseResult();
        float sum = orderService.findTurnOver();
        result.setMsg(true);
        result.setData(sum);
        return result;
    }


//    @UserLoginToken
//    @RequestMapping(value = "/addOrder", method = RequestMethod.POST)
//    public ResponseResult addOrder(OrderAttach orderAttach) throws AlipayApiException {
//        ResponseResult responseResult = new ResponseResult();
//        responseResult.setMsg(false);
//        Orders order = new Orders();
//        long uid = Long.parseLong(TokenUtil.getTokenUserId());
//        order.setUid(uid);
//        order.setAddress(orderAttach.getAddress());
//        order.setReceiver(orderAttach.getReceiver());
//        order.setMobile(orderAttach.getMobile());
//        order.setUserMessage(orderAttach.getUserMessage());
//
//        order.setOrderCode(String.valueOf(System.currentTimeMillis()));
//        Date date = new Date();
//        order.setCreateDate(date);
//        order.setStatus("待付款");
//
//        int n = orderService.findAddOrder(order);//此处成功插入orders表中
//
//
//        long orderId = orderService.findNewOrderId();
//
////        接下来要做的是插入该订单的订单项
//
//        List<Long> cartIdList = orderAttach.getCartIdList();//这里两个36
//
//        OrderItem orderItem = new OrderItem();
//        int m = 0, a = 0;
//        int orderPrice = 0;
//        for (long cartId : cartIdList) {//从多选框里把选中的pid遍历出来，然后跟orderitem绑在一起
//            Cart cart = cartService.findCartByUidAndId(uid, cartId);
//            orderItem.setPid(cart.getPid());
//            orderItem.setOid(orderId);
//            orderItem.setUid(uid);
//            orderItem.setNumber((long) cart.getCount());
//            orderItem.setSimplePrice(cart.getSimplePrice());
//            orderItem.setTotalPrice(cart.getTotalPrice());
//
//            m = orderService.findAddOrderItem(orderItem);//逐项插入订单项
//
//            //增加对库存和销量的处理
//            if (m == 1) {
//                //库存
//                long oldStock = productService.findOldStock(cart.getPid());
//                productService.findUpdateStock(cart.getPid(), oldStock - cart.getCount());
//                long oldSaleCount = productService.findOldSaleCount(cart.getPid());
//                productService.findUpdateSaleCount(cart.getPid(), oldSaleCount + cart.getCount());
//            }
//            //删除对应的购物车！！！！！！！！！！！！
//            cartService.findDeleteCartByUidAndPid(uid, cart.getPid());
//        }
//        if (n == 1 && m == 1) {
//            orderPrice = (int) orderService.findOrderPrice(orderId);
//            a = orderService.findUpdateOrderPrice(orderPrice, orderId);
//        }
//        if (a == 1) {
//            ///获得初始化的AlipayClient
//            AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);
//
//            //设置请求参数
//            AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
//            alipayRequest.setReturnUrl(AlipayConfig.return_url);
//            alipayRequest.setNotifyUrl(AlipayConfig.notify_url);
//
//            //商户订单号，商户网站订单系统中唯一订单号，必填
//            String out_trade_no = String.valueOf(orderId);
//            //付款金额，必填
//            String total_amount = String.valueOf(orderPrice);
//            //订单名称，必填
//            String subject = "flowerOnline-"+orderId;
//            //商品描述，可空
//            String body = "";
//
//            alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
//                    + "\"total_amount\":\""+ total_amount +"\","
//                    + "\"subject\":\""+ subject +"\","
//                    + "\"body\":\""+ body +"\","
//                    + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
//
//            //请求
//            String result = alipayClient.pageExecute(alipayRequest).getBody();
//            if(result!=null){
//                responseResult.setMsg(true);
//                responseResult.setData(result);
//            }
//            return responseResult;
//        } else{
//            return responseResult;
//        }
//    }


    //这里相当于从购物车里选中商品，然后结算生成订单，然后生成订单项
    @UserLoginToken
    @RequestMapping(value = "/addOrder", method = RequestMethod.POST)
    public ResponseDataPay addOrder(OrderAttach orderAttach) {
        //用户可以修改和提交的内容有：地址，收货人名字，收货人电话，买家留言，都是order表的字段
        //新建这个订单
        Orders order = new Orders();
        long uid = Long.parseLong(TokenUtil.getTokenUserId());
        order.setUid(uid);
        order.setAddress(orderAttach.getAddress());
        order.setReceiver(orderAttach.getReceiver());
        order.setMobile(orderAttach.getMobile());
        order.setUserMessage(orderAttach.getUserMessage());

        order.setOrderCode(String.valueOf(System.currentTimeMillis()));
        Date date = new Date();
        order.setCreateDate(date);
        order.setStatus("待付款");

        int n = orderService.findAddOrder(order);//此处成功插入orders表中


        long orderId = orderService.findNewOrderId();

//        接下来要做的是插入该订单的订单项

        List<Long> cartIdList = orderAttach.getCartIdList();//这里两个36

        OrderItem orderItem = new OrderItem();
        int m = 0, a = 0;
        int orderPrice = 0;
        for (long cartId : cartIdList) {//从多选框里把选中的pid遍历出来，然后跟orderitem绑在一起
            Cart cart = cartService.findCartByUidAndId(uid, cartId);
            orderItem.setPid(cart.getPid());
            orderItem.setOid(orderId);
            orderItem.setUid(uid);
            orderItem.setNumber((long) cart.getCount());
            orderItem.setSimplePrice(cart.getSimplePrice());
            orderItem.setTotalPrice(cart.getTotalPrice());

            m = orderService.findAddOrderItem(orderItem);//逐项插入订单项

            //增加对库存和销量的处理
            if (m == 1) {
                //库存
                long oldStock = productService.findOldStock(cart.getPid());
                productService.findUpdateStock(cart.getPid(), oldStock - cart.getCount());
                long oldSaleCount = productService.findOldSaleCount(cart.getPid());
                productService.findUpdateSaleCount(cart.getPid(), oldSaleCount + cart.getCount());
            }
            //删除对应的购物车！！！！！！！！！！！！
            cartService.findDeleteCartByUidAndPid(uid, cart.getPid());
        }
        if (n == 1 && m == 1) {
            orderPrice = (int) orderService.findOrderPrice(orderId);
            a = orderService.findUpdateOrderPrice(orderPrice, orderId);
        }
        if (a == 1) {
            return ResponseDataPay.createBySuccess(WebCts.RESP_SUCCESS, uid, orderId, String.valueOf(order.getOrderCode()),
                    String.valueOf(order.getOrderCode()), String.valueOf(orderPrice));

        } else{
            return ResponseDataPay.createByError();
        }
    }

    //展示用户的订单列表,所有订单
    @UserLoginToken
    @RequestMapping(value = "/listOrderByUid", method = RequestMethod.GET)
    public ResponseResult listOrderByUid(@RequestParam(defaultValue = "1", required = true, value = "pn") Integer pn, HttpServletRequest request) {
        int pageSize = 10;
        PageHelper.startPage(pn, pageSize);
        long uid = Long.parseLong(TokenUtil.getTokenUserId());
        PageInfo<Orders> pageInfo = orderService.findListOrderByUid(pn, pageSize, uid);
        ResponseResult result = new ResponseResult();
        result.setMsg(true);
        result.setData(pageInfo);
        return result;
    }

    //后台订单列表
    @RequestMapping(value = "/listAllOrder", method = RequestMethod.GET)
    public ResponseResult listAllOrder(@RequestParam(defaultValue = "1", required = true, value = "pn") Integer pn) {
        int pageSize = 10;
        PageHelper.startPage(pn, pageSize);
        PageInfo<Orders> pageInfo = orderService.findListAllOrder(pn, pageSize);
        ResponseResult result = new ResponseResult();
        result.setMsg(true);
        result.setData(pageInfo);
        return result;
    }

    //用户订单项的显示
    @UserLoginToken
    @RequestMapping(value = "/OrderDetail", method = RequestMethod.GET)
    public ResponseResult listOrderAndDetail(@RequestParam("orderCode") String orderCode) {
        OrderDetail orderDetail = new OrderDetail();
        long uid = Long.parseLong(TokenUtil.getTokenUserId());
        if (!orderCode.equals("")) {
            Orders orders = orderService.findOrderByOrderCode(orderCode);
            long id = orders.getId();
            List<OrderItemDetail> orderItemDetailList;
            OrderOtherDetail orderOtherDetail;
            orderItemDetailList = orderService.findOrderItemDetail(uid, id);
            float orderPrice = 0;
            for(OrderItemDetail orderItemDetail:orderItemDetailList){
                orderPrice += orderItemDetail.getTotalPrice();
            }
            orderService.findUpdateOrderPrice(orderPrice, id);
            orderOtherDetail = orderService.findOrderOtherDetail(uid, id);
            orderDetail.setOrderItemDetailList(orderItemDetailList);
            orderDetail.setOrderOtherDetail(orderOtherDetail);
        } else {
            //此处针对用户直接把购物车里的东西全部结算
            List<Cart> cartList = cartService.findListCartByUid(uid);
            Users user = usersService.findUserById(uid);
            Orders order = new Orders();
            order.setUid(uid);
            order.setAddress(user.getAddress());
            order.setReceiver(user.getUsername());
            order.setMobile(user.getPhone());
            order.setOrderCode(String.valueOf(System.currentTimeMillis()));
            Date date = new Date();
            order.setCreateDate(date);
            order.setStatus("待付款");
            orderService.findAddOrder(order);//此处成功插入orders表中
            long orderId = orderService.findNewOrderId();
            //插入该订单的订单项
            List<Long> pidList = new ArrayList<>();
            cartList.forEach(cart -> {
                pidList.add(cart.getPid());
            });
            OrderItem orderitem = new OrderItem();
            float orderPrice = 0;
            for (long pid : pidList) {//从多选框里把选中的pid遍历出来，然后跟orderitem绑在一起
                for (Cart cart : cartList) {
                    if (cart.getPid().equals(pid)) {
                        orderitem.setPid(pid);
                        orderitem.setOid(orderId);
                        orderitem.setUid(uid);
                        orderitem.setNumber((long) cart.getCount());
                        orderitem.setSimplePrice(cart.getSimplePrice());
                        orderitem.setTotalPrice(cart.getTotalPrice());
                        orderPrice = orderPrice + orderitem.getTotalPrice();
                        orderService.findAddOrderItem(orderitem);//逐项插入订单项
                        long oldStock = productService.findOldStock(pid);
                        productService.findUpdateStock(pid, oldStock - cart.getCount());
                        long oldSaleCount = productService.findOldSaleCount(pid);
                        productService.findUpdateSaleCount(pid, oldSaleCount + cart.getCount());
                        cartService.findDeleteCartByUidAndPid(uid, pid);
                    }
                }
            }
            List<OrderItemDetail> orderItemDetailList = orderService.findOrderItemDetail(uid, orderId);
            orderService.findUpdateOrderPrice(orderPrice, orderId);
            OrderOtherDetail orderOtherDetail = orderService.findOrderOtherDetail(uid, orderId);
            orderDetail.setOrderItemDetailList(orderItemDetailList);
            orderDetail.setOrderOtherDetail(orderOtherDetail);

        }
        ResponseResult result = new ResponseResult();
        result.setData(orderDetail);
        result.setMsg(true);
        return result;
    }

    @RequestMapping(value = "/getOrderDetailByAdmin", method = RequestMethod.GET)
    public ResponseResult getOrderDetailByAdmin(@RequestParam("orderId")long orderId) {
        List<OrderItemDetail> orderItemDetailList = orderService.findOrderItemDetailById(orderId);
        ResponseResult result = new ResponseResult();
        result.setData(orderItemDetailList);
        result.setMsg(true);
        return result;
    }


    //用户自己修改订单状态，将待收货修改为确认收货
    @RequestMapping(value = "/getProduct", method = RequestMethod.GET)
    public ResponseResult getProduct(@RequestParam("orderCode") long orderId) {
        ResponseResult result = new ResponseResult();
        Orders order = orderService.findOrderById(orderId);
        if (order.getStatus().equals("已发货") ) {
            order.setConfirmDate(new Date());
            if (orderService.findUpdateOrderStatus("已完成", new Date().toLocaleString(), order.getOrderCode()) == 1){
                result.setMsg(true);
                return result;
            }
            else{
                result.setMsg(false);
                return result;
            }
        } else {
            result.setMsg(false);
            result.setData("你的商品还没发货呢");
            return result;
        }
    }

    //后台订单状态修改，改为已发货
    @RequestMapping(value = "/postProduct", method = RequestMethod.GET)
    public ResponseResult postProduct(@RequestParam("orderCode") String orderCode,
                                    @RequestParam("post") String post) {
        ResponseResult result = new ResponseResult();
        Orders order = orderService.findOrderByOrderCode(orderCode);
        order.setStatus("已发货");
        order.setPost(post);
        order.setDeliveryDate(new Date());
        if (orderService.findUpdateOrder(order) == 1){
            result.setMsg(true);
            return result;
        }
        else{
            result.setMsg(false);
            return result;
        }
    }

    //立即购买商品
    @UserLoginToken
    @RequestMapping(value = "/buyNow", method = RequestMethod.GET)
    public ResponseDataPay buyNow(BuyNow buyNow) {
        //用户可以修改和提交的内容有：地址，收货人名字，收货人电话，买家留言，都是order表的字段
        //新建这个订单
        Orders order = new Orders();
        long uid = Long.parseLong(TokenUtil.getTokenUserId());
        Users user = usersService.findUserById(uid);
        order.setUid(uid);
        order.setAddress(user.getAddress());
        order.setReceiver(user.getUsername());
        order.setMobile(user.getPhone());

        order.setOrderCode(String.valueOf(System.currentTimeMillis()));
        Date date = new Date();
        order.setCreateDate(date);
        order.setStatus("待付款");

        int n = orderService.findAddOrder(order);//此处成功插入orders表中
        long orderId = orderService.findNewOrderId();

//        接下来要做的是插入该订单的订单项

        OrderItem orderitem = new OrderItem();
        int m = 0, a = 0;
        int orderPrice = 0;
        orderitem.setPid(buyNow.getPid());
        orderitem.setOid(orderId);
        orderitem.setUid(uid);
        orderitem.setNumber(buyNow.getNumber());

        Product product = productService.findProductById(buyNow.getPid());


        orderitem.setSimplePrice(product.getPromotePrice());
        orderitem.setTotalPrice(buyNow.getNumber() * product.getPromotePrice());
        m = orderService.findAddOrderItem(orderitem);//逐项插入订单项

        //增加对库存和销量的处理2019-7-12 11:27
        if (m == 1) {
            //库存
            long oldStock = productService.findOldStock(buyNow.getPid());
            productService.findUpdateStock(buyNow.getPid(), oldStock - buyNow.getNumber());
            long oldSaleCount = productService.findOldSaleCount(buyNow.getPid());
            productService.findUpdateSaleCount(buyNow.getPid(), oldSaleCount + buyNow.getNumber());
        }
//        }
        if (n == 1 && m == 1) {
            orderPrice = (int) orderService.findOrderPrice(orderId);
            a = orderService.findUpdateOrderPrice(orderPrice, orderId);
        }
        if (a == 1) {
            return ResponseDataPay.createBySuccess(WebCts.RESP_SUCCESS, uid, orderId, String.valueOf(order.getOrderCode()),
                    String.valueOf(order.getOrderCode()), String.valueOf(orderPrice));
        } else
            return ResponseDataPay.createByError();
    }

    //按订单编号查找订单信息
    @RequestMapping(value = "/findOrderByOrderId", method = RequestMethod.GET)
    public ResponseResult findOrderByOrderId(@RequestParam("orderCode") String orderCode) {
        ResponseResult result = new ResponseResult();
        Orders order = orderService.findOrderByOrderCode(orderCode);
        result.setMsg(true);
        result.setData(order);
        return result;
    }

    @UserLoginToken
    @RequestMapping(value = "/delOrder", method = RequestMethod.GET)
    public ResponseResult delOrder(@RequestParam("id") long id) {
        ResponseResult result = new ResponseResult();
        try {
            Users users = usersService.findUserById(Long.parseLong(TokenUtil.getTokenUserId()));
            orderService.findDelByIdAndUid(id, users.getId());
            result.setMsg(true);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            result.setMsg(false);
            result.setData("删除失败，系统错误");
            return result;
        }
    }

    @RequestMapping(value = "/alterOrder", method = RequestMethod.POST)
    public ResponseResult updateOrder(@RequestParam("receiver") String receiver, @RequestParam("phone") String phone,
                                       @RequestParam("address") String address, @RequestParam("msg") String msg, @RequestParam("ordercode") String orderCode, HttpServletRequest request) throws AlipayApiException {
        Orders order = orderService.findOrderByOrderCode(orderCode);
        ResponseResult responseResult = new ResponseResult();
        responseResult.setMsg(false);
        if (order == null) {
            return responseResult;
        } else {
            order.setAddress(address);
            order.setReceiver(receiver);
            order.setMobile(phone);
            order.setUserMessage(msg);
            order.setStatus("待付款");
            int n = orderService.findUpdateOrders(order);
            if(n>0){
                //获得初始化的AlipayClient
                AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);

                //设置请求参数
                AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
                alipayRequest.setReturnUrl(AlipayConfig.return_url);
                alipayRequest.setNotifyUrl(AlipayConfig.notify_url);

                //商户订单号，商户网站订单系统中唯一订单号，必填
                String out_trade_no = String.valueOf(orderCode);
                //付款金额，必填
                String total_amount = String.valueOf(order.getOrderPrice());
                //订单名称，必填
                String subject = "flowerOnline-"+orderCode;
                //商品描述，可空
                String body = "";

                alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
                        + "\"total_amount\":\""+ total_amount +"\","
                        + "\"subject\":\""+ subject +"\","
                        + "\"body\":\""+ body +"\","
                        + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

                //请求
                String result = alipayClient.pageExecute(alipayRequest).getBody();
                if(result!=null){
                    responseResult.setMsg(true);
                    responseResult.setData(result);
                }
                return responseResult;
            }
            return responseResult;
        }
    }

    /**
     * 支付宝异步 通知页面
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    final static Logger log = LoggerFactory.getLogger(OrderController.class);

    @RequestMapping(value = "/alipayNotifyNotice")
    @ResponseBody
    public String ALiPayNotifyNotice(HttpServletRequest request, HttpServletRequest response) throws Exception {

        log.info("支付成功, 进入异步通知接口...");

        //获取支付宝POST过来反馈信息
        Map<String,String> params = new HashMap<String,String>();
        Map<String,String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }
        boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.charset, AlipayConfig.sign_type); //调用SDK验证签名
        //——请在这里编写您的程序（以下代码仅作参考）——
        /* 实际验证过程建议商户务必添加以下校验：
        1、需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
        2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），
        3、校验通知中的seller_id（或者seller_email) 是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email）
        4、验证app_id是否为该商户本身。
        */
        if(signVerified) {//验证成功
            //商户订单号
            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");

            //支付宝交易号
            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");

            //交易状态
            String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");

            //付款金额 到这里获取到这些信息就可以了，下面的不用看
            String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"),"UTF-8");

            if(trade_status.equals("TRADE_FINISHED")){
                //判断该笔订单是否在商户网站中已经做过处理
                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                //如果有做过处理，不执行商户的业务程序

                //注意： 尚自习的订单没有退款功能, 这个条件判断是进不来的, 所以此处不必写代码
                //退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
            }else if (trade_status.equals("TRADE_SUCCESS")){
                //判断该笔订单是否在商户网站中已经做过处理
                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                //如果有做过处理，不执行商户的业务程序

                //注意：
                //付款完成后，支付宝系统发送该交易状态通知

                int result=0;
                //这里根据自身的业务写代码，我这里删掉了
                String orderCode = request.getParameter("out_trade_no");
                Orders order = orderService.findOrderByOrderCode(orderCode);
                order.setPayDate(new Date());
                order.setStatus("待发货");
                int n = orderService.findUpdatePayDate(order);

                if(n>0){
                    log.info("resultinfo","支付成功！");
                }else {
                    log.info("resultinfo","更新订单失败！请业务员联系后台管理员！");
                }

                log.info("********************** 支付成功(支付宝同步通知) **********************");
                log.info("* 订单号: {}", out_trade_no);
                log.info("* 支付宝交易号: {}", trade_no);
                log.info("* 实付金额: {}", total_amount);
                log.info("***************************************************************");
            }
            log.info("支付成功...");
        }else {//验证失败
            log.info("支付, 验签失败...");
        }
        return "success";
    }
}
