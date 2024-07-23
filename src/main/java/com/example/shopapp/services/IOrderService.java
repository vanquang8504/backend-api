package com.example.shopapp.services;

import com.example.shopapp.dataNotFoundException.DataNotFoundException;
import com.example.shopapp.dtos.OrderDTO;
import com.example.shopapp.models.Order;
import com.example.shopapp.response.OrderResponse;

import java.util.List;

public interface IOrderService {
    OrderResponse createOder(OrderDTO orderDTO) throws Exception;

    Order getOrderById(long id) throws Exception;

    Order updateOrder(long id, OrderDTO orderDTO) throws DataNotFoundException;

    void deleteOrder(long id) throws DataNotFoundException;

    List<Order> getAllOrderByUser(long idUser);
}
