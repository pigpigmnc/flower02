package model.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class OrderAttach implements Serializable {
    private static final long serialVersionUID = -8173180048675539913L;
    Long uid;
    String address;
    String receiver;
    String mobile;
    String userMessage;
    List<Long> cartIdList;
}
