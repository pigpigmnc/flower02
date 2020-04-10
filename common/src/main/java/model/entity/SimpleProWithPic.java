package model.entity;


import lombok.Data;

import java.util.List;

@Data
public class SimpleProWithPic {

    SimpleDetail simpleDetail;//该类囊括所有的信息
    List<String> fileurlpath;//这里存储该商品的所有图片路径

}
