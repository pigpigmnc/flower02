package model.entity;

import lombok.Data;
import java.io.Serializable;
import java.util.Date;

@Data
public class Product implements Serializable {
    private static final long serialVersionUID = 1481623058484241814L;
    Long id;
    String name;
    String subTitle;
    Float originalPrice;
    Float promotePrice;
    Long stock;
    Long cid;
    Date createDate;
    Long saleCount;
    String attention;

}
