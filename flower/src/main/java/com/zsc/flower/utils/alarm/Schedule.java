package com.zsc.flower.utils.alarm;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.zsc.flower.config.AlipayConfig;
import com.zsc.flower.service.OrderService;
import model.entity.Orders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

@Component
public class Schedule {


    @Autowired
    private OrderService orderService;

//    @Scheduled(cron = "0/1 * * * * ?")
    public void checkOrderStatus() {
        List<Orders> ordersList = orderService.getAllOrders();
        ordersList.forEach(order -> {
            if (order.getStatus().equals("待付款")) {
//                Float orderPrice = order.getOrderPrice();
                //获得初始化的AlipayClient
                AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);

                //设置请求参数
                AlipayTradeQueryRequest alipayRequest = new AlipayTradeQueryRequest();
                //商户订单号，商户网站订单系统中唯一订单号
                String out_trade_no = null;
                out_trade_no = order.getOrderCode();
                //支付宝交易号
                String trade_no = "";
                //请二选一设置
                alipayRequest.setBizContent("{\"out_trade_no\":\"" + out_trade_no + "\"," + "\"trade_no\":\"" + trade_no + "\"}");
                String result;
                //请求
                try {
                    result = alipayClient.execute(alipayRequest).getBody();
                    JSONObject jsonObject = JSONObject.parseObject(result);
                    if(!jsonObject.equals("null_response")){
                        JSONObject jsonObject1 = jsonObject.getJSONObject("alipay_trade_query_response");
                        String tradeStatus = String.valueOf(jsonObject1.get("trade_status"));
                        if (tradeStatus.equals("TRADE_SUCCESS")) {
//                            order.setOrderPrice(orderPrice);
                            order.setStatus("待发货");
                            order.setPayDate(new Date());
                            orderService.findUpdateOrders(order);
                        }
                    }
                } catch (AlipayApiException e) {
                    e.printStackTrace();
                }
            }
        });
    }

//    @Scheduled(cron = "0 0/15 * * * ?")
    public void cancelOrder() {
        List<Orders> ordersList = orderService.getAllOrders();
        ordersList.forEach(order -> {
            if (order.getStatus().equals("待付款")) {
                Float orderPrice = order.getOrderPrice();
                //获得初始化的AlipayClient
                AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);
                //设置请求参数
                AlipayTradeQueryRequest alipayRequest = new AlipayTradeQueryRequest();
                //商户订单号，商户网站订单系统中唯一订单号
                String out_trade_no;
                out_trade_no = order.getOrderCode();
                //支付宝交易号
                String trade_no = "";
                //请二选一设置
                alipayRequest.setBizContent("{\"out_trade_no\":\"" + out_trade_no + "\"," + "\"trade_no\":\"" + trade_no + "\"}");
                String result;
                //请求
                try {
                    result = alipayClient.execute(alipayRequest).getBody();
                    JSONObject jsonObject = JSONObject.parseObject(result);
                    JSONObject jsonObject1 = jsonObject.getJSONObject("alipay_trade_query_response");
                    String tradeStatus = String.valueOf(jsonObject1.get("trade_status"));
                    if (!tradeStatus.equals("TRADE_SUCCESS")) {
                        order.setOrderPrice(orderPrice);
                        order.setStatus("订单被取消");
                        orderService.findUpdateOrders(order);
                    }
                } catch (AlipayApiException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
