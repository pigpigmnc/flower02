package model.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Category implements Serializable {
    private static final long serialVersionUID = -710735675977123538L;
    Long id;
    String name;
}
