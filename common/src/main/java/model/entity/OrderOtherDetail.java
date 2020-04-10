package model.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class OrderOtherDetail implements Serializable {
    private static final long serialVersionUID = -5215419856006085553L;

    String orderCode;
    Float orderPrice;
    String receiver;
    String mobile;
    String address;
    String userMessage;
    Date createDate;
}
