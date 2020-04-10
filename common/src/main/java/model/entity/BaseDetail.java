package model.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class BaseDetail implements Serializable {
    private static final long serialVersionUID = 5089458150965286127L;
    long id;
    String name;
    String subTitle;
    String originalPrice;
    String promotePrice;
    long stock;
    Date createDate;
    String fileurlpath;
    String cname;
}
