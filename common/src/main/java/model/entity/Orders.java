package model.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Orders implements Serializable {
    private static final long serialVersionUID = 3246845050990798960L;
    Long id;
    String orderCode;
    String address;
    String post;
    String receiver;
    String mobile;
    String userMessage;
    Date createDate;
    Date payDate;
    Date deliveryDate;
    Date confirmDate;
    Long uid;
    String status;
    Float orderPrice;
}
