package com.zsc.flower.dao;

import model.entity.OrderItem;
import model.entity.OrderItemDetail;
import model.entity.OrderOtherDetail;
import model.entity.Orders;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDao{
    public int selectAddOrder(Orders order);
    public int selectAddOrderItem(OrderItem orderItem);
    public long selectNewOrderId();
    public float selectOrderPrice(@Param("oid") long oid);
    public int selectUpdateOrderPrice(@Param("orderPrice") float orderPrice, @Param("id") long id);
    public List<Orders> selectListOrderByUid(@Param("uid") long uid);
    public List<Orders> selectListAllOrder();
    public List<OrderItemDetail> selectOrderItemDetail(@Param("uid") long uid, @Param("id") long id);
    public OrderOtherDetail selectOrderOtherDetail(@Param("uid") long uid, @Param("id") long id);


    public Orders selectOrderByOrderCode(@Param("orderCode") String orderCode);
    public int selectUpdatePayDate(Orders order);

    public Orders selectOrderByUIdAndCode(@Param("orderCode") String orderCode);
    public int selectUpdateOrderStatus(@Param("status") String status,
                                       @Param("confirmDate") String confirmDate,
                                       @Param("orderCode") String orderCode);
    public int selectUpdateOrder(Orders order);

    public List<Orders> selectOrderByStatus(@Param("status") String status);

    public void selectDeleteOrderById(@Param("id") long id);

    //=====================
    public void deleteOrderByStatusAndUid(@Param("id") long id, @Param("uid") long uid, @Param("status") String status);
    public Orders selectOrderById(@Param("id") long id);
    public int selectUpdateOrders(Orders order);

    List<OrderItemDetail> findOrderItemDetailById(long orderId);
}
