package com.zsc.flower.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zsc.flower.dao.OrderDao;
import com.zsc.flower.service.OrderService;
import model.entity.OrderItem;
import model.entity.OrderItemDetail;
import model.entity.OrderOtherDetail;
import model.entity.Orders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("orderService")
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderDao orderDao;

    @Override
    public int findAddOrder(Orders order) {
        return orderDao.selectAddOrder(order);
    }

    @Override
    public int findAddOrderItem(OrderItem orderItem) {
        return orderDao.selectAddOrderItem(orderItem);
    }

    @Override
    public long findNewOrderId() {
        return orderDao.selectNewOrderId();
    }

    @Override
    public float findOrderPrice(long oid) {
        return orderDao.selectOrderPrice(oid);
    }

    @Override
    public int findUpdateOrderPrice(float orderPrice,long id) {
        return orderDao.selectUpdateOrderPrice(orderPrice,id);
    }

    @Override
    public PageInfo<Orders> findListOrderByUid(int page, int size, long uid) {
        PageHelper.startPage(page,size);
        PageHelper.orderBy("id desc");
        PageInfo<Orders> pageInfo=new PageInfo<Orders>(orderDao.selectListOrderByUid(uid));
        return pageInfo;
    }

    @Override
    public PageInfo<Orders> findListAllOrder(int page, int size) {
        PageHelper.startPage(page,size);
        PageHelper.orderBy("id desc");
        PageInfo<Orders> pageInfo=new PageInfo<Orders>(orderDao.selectListAllOrder());
        return pageInfo;
    }

    @Override
    public List<OrderItemDetail> findOrderItemDetail(long uid, long id) {
        return orderDao.selectOrderItemDetail(uid,id);
    }

    @Override
    public OrderOtherDetail findOrderOtherDetail(long uid, long id) {
        return orderDao.selectOrderOtherDetail(uid,id);
    }

    @Override
    public Orders findOrderByOrderCode(String orderCode) {
        return orderDao.selectOrderByOrderCode(orderCode);
    }

    @Override
    public int findUpdatePayDate(Orders order) {
        return orderDao.selectUpdatePayDate(order);
    }

    @Override
    public Orders findOrderByUIdAndCode(String orderCode) {
        return orderDao.selectOrderByUIdAndCode(orderCode);
    }

    @Override
    public int findUpdateOrderStatus(String status,
                                     String confirmDate,
                                     String orderCode) {
        return orderDao.selectUpdateOrderStatus(status,confirmDate,orderCode);
    }

    @Override
    public int findUpdateOrder(Orders order) {
        return orderDao.selectUpdateOrder(order);
    }

    @Override
    public List<Orders> findOrderByStatus(String status) {
        return orderDao.selectOrderByStatus(status);
    }

    @Override
    public void findDeleteOrderById(long id) {
        orderDao.selectDeleteOrderById(id);
    }
    @Override
    public void findDelByIdAndUid(long id, long uid) {
        orderDao.deleteOrderByStatusAndUid(id,uid,"");
    }
    @Override
    public Orders findOrderById(long id) {
        return orderDao.selectOrderById(id);
    }
    @Override
    public int findUpdateOders(Orders orders) {
        return orderDao.selectUpdateOrders(orders);
    }

    @Override
    public List<OrderItemDetail> findOrderItemDetailById(long orderId) {
        return orderDao.findOrderItemDetailById(orderId);
    }

    @Override
    public float findTurnOver() {
        return orderDao.selectTurnOver();
    }
}
