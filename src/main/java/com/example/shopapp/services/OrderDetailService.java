package com.example.shopapp.services;

import com.example.shopapp.dataNotFoundException.DataNotFoundException;
import com.example.shopapp.dtos.OrderDetailDTO;
import com.example.shopapp.models.Order;
import com.example.shopapp.models.OrderDetail;
import com.example.shopapp.models.Product;
import com.example.shopapp.repositories.OrderDetailRepository;
import com.example.shopapp.repositories.OrderRepository;
import com.example.shopapp.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderDetailService implements IOrderDetailService {
    private final OrderDetailRepository orderDetailRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    @Override
    public OrderDetail createOderDetail(OrderDetailDTO orderDetailDTO) throws DataNotFoundException {
        Product existingProduct = productRepository.findById(orderDetailDTO.getProductId())
                .orElseThrow(() -> new
                        DataNotFoundException("Cannot find product with id : "+orderDetailDTO.getProductId()));
        Order existingOrder = orderRepository.findById(orderDetailDTO.getOrderId())
                .orElseThrow(() -> new
                        DataNotFoundException("Cannot find order with id : "+orderDetailDTO.getOrderId()));
        OrderDetail newOrderDetail = OrderDetail.builder()
                .order(existingOrder)
                .product(existingProduct)
                .price(orderDetailDTO.getPrice())
                .color(orderDetailDTO.getColor())
                .numberOfProducts(orderDetailDTO.getNumberOfProduct())
                .totalMoney(orderDetailDTO.getTotalMoney())
                .build();
        return orderDetailRepository.save(newOrderDetail);
    }

    @Override
    public OrderDetail updateOderDetail(long idOrder, OrderDetailDTO orderDetailDTO) {
        return null;
    }

    @Override
    public OrderDetail getOrderDetailById(long idOrderDetail) {
        return null;
    }

    @Override
    public List<OrderDetail> getAllOrderDetail(long idOrder) {
        return null;
    }
}
