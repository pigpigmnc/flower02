package model.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ListProduct implements Serializable {
    private static final long serialVersionUID = 6904655288340385063L;
    Long id;
    String name;
    String subTitle;
    Float originalPrice;
    Float promotePrice;
    Long stock;
    Date createDate;
    String fileurlpath;
    String cname;
    Long saleCount;
}
