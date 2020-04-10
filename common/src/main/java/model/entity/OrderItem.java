package model.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderItem implements Serializable {
    private static final long serialVersionUID = 995436145025388045L;
    Long id;
    Long pid;
    Long oid;
    Long uid;
    Long number;
    Float simplePrice;
    Float totalPrice;
}
