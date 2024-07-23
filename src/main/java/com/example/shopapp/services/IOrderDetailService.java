package com.example.shopapp.services;

import com.example.shopapp.dataNotFoundException.DataNotFoundException;
import com.example.shopapp.dtos.OrderDetailDTO;
import com.example.shopapp.models.OrderDetail;

import java.util.List;

public interface IOrderDetailService {
    OrderDetail createOderDetail(OrderDetailDTO orderDetailDTO) throws DataNotFoundException;

    OrderDetail updateOderDetail(long idOrder, OrderDetailDTO orderDetailDTO);

    OrderDetail getOrderDetailById(long idOrderDetail);

    List<OrderDetail> getAllOrderDetail(long idOrder);
}
