package model.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderParam implements Serializable {

    private static final long serialVersionUID = -8054492789344616630L;

    private String receiver;
    private String phone;
    private String address;
    private String msg;
    private String orderCode;
}
