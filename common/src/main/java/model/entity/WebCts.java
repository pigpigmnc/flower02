package model.entity;

public class WebCts {
    public final static String LOGIN_USER="user";//存放登录的用户对象属性名称
    public final static  int ROLE_ADMIN=0;//管理员角色
    public final static  int ROLE_USER=1;//普通用户角色

    public  final static int ACTIVE=1;  //用户激活状态
    public  final static int NO_ACTIVE=0;  //用户未激活状态

    //    用于封装客户端响应
    public final  static String RESP_SUCCESS="success";
    public final  static String RESP_FAIL="fail";
}
