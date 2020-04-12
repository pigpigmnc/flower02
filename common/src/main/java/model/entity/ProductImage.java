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
    Byte type; //0-banner图,1-主图;banner图和主图都能有多张,图片上架时只上传主图
    Integer sort;//图片顺序,从0开始,越小越主要
}
