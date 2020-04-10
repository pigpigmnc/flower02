package model.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class ExtendDetail implements Serializable {
    private static final long serialVersionUID = 3443511421530772450L;
    long id;
    String ptname;
    String value;
}
