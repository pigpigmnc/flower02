package model.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Review implements Serializable {

    private static final long serialVersionUID = 1512467147979726847L;

    private Long id;
    private String content;
    private Long uid;
    private String username;
    private Long pid;
    private Date createDate;
    private Integer star;
}
