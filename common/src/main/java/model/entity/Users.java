package model.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Mybatis只和基本类型的对象类型进行自动映射，因此属性应该写对象类型
 */
//用户类
@Data
public class Users implements Serializable {
    private static final long serialVersionUID = 5321189909668110513L;

    Long id;             //id
    String username;   //用户名
    String password;   //密码
    String phone;       //电话
    String address;     //地址
    String email;      //电子邮件
    Integer status;         //表示用户状态、status=0表示用户停用，status=1表示用户激活
    Integer role;           //用户角色、role=0表示管理员、role=1表示普通用户
    Date registerDate;
}
