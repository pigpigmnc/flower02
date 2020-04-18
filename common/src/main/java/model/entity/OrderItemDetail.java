package model.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderItemDetail implements Serializable {
    private static final long serialVersionUID = 7916672056680698753L;
    Long pid;
    String name;//订单项的商品名称
    Long number;
    Float simplePrice;//商品单价
    Float totalPrice;
    String fileurlpath;
}
