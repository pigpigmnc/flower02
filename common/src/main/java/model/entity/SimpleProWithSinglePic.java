package model.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SimpleProWithSinglePic implements Serializable {
    private static final long serialVersionUID = -6292341866418359238L;
    SimpleDetail simpleDetail;//该类囊括所有的信息
    String fileurlpath;//这里存储该商品的所有图片路径
}
