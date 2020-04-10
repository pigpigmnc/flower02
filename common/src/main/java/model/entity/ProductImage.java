package model.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class ProductImage implements Serializable {
    private static final long serialVersionUID = -2654470349068894228L;
    Long id;
    Long pid;
    String filename;
    String fileurlpath;
}
