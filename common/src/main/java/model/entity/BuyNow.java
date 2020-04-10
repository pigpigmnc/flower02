package model.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class BuyNow implements Serializable {
    private static final long serialVersionUID = -2739975744616556558L;
    Long uid;
    String address;
    String receiver;
    String mobile;
    String userMessage;
    Long pid;
    Long number;
}
