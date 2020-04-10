package model.entity;
import lombok.Data;

import java.io.Serializable;

@Data
public class Cart implements Serializable {
    private static final long serialVersionUID = -1807753670092051173L;
    Long id;
    Long uid;
    Long pid;
    String fileurlpath;
    String pname;
    float simplePrice;
    int count;
    float totalPrice;
}
