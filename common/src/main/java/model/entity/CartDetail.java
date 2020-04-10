package model.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class CartDetail implements Serializable {
    private static final long serialVersionUID = 6320033678253540390L;
    long id;
    String name;
    Float promotePrice;
    String fileurlpath;
}
