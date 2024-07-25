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
    public OrderDetail updateOderDetail(long idOrder, OrderDetailDTO orderDetailDTO) throws DataNotFoundException {
        Order existingOrder = orderRepository.findById(idOrder)
                .orElseThrow(() -> new
                        DataNotFoundException("Cannot find order with id : "+idOrder));
        OrderDetail existingOrderDetail = orderDetailRepository.findById(orderDetailDTO.getOrderId())
                .orElseThrow(() -> new
                        DataNotFoundException("Cannot find order detail with id : "+orderDetailDTO.getOrderId()));
        Product existingProduct = productRepository.findById(orderDetailDTO.getProductId())
                .orElseThrow(() -> new
                        DataNotFoundException("Cannot find product with id : "+orderDetailDTO.getProductId()));
        existingOrderDetail.setOrder(existingOrder);
        existingOrderDetail.setProduct(existingProduct);
        existingOrderDetail.setPrice(orderDetailDTO.getPrice());
        existingOrderDetail.setColor(orderDetailDTO.getColor());
        existingOrderDetail.setTotalMoney(orderDetailDTO.getTotalMoney());
        existingOrderDetail.setNumberOfProducts(orderDetailDTO.getNumberOfProduct());
        return orderDetailRepository.save(existingOrderDetail);
    }

    @Override
    public OrderDetail getOrderDetailById(long idOrderDetail) throws DataNotFoundException {
        return orderDetailRepository.findById(idOrderDetail)
                .orElseThrow(() -> new DataNotFoundException("Cannot find order-detail with id : "+idOrderDetail));
    }

    @Override
    public List<OrderDetail> getAllOrderDetail(long idOrder) throws DataNotFoundException {
        return orderDetailRepository.findByOrderId(idOrder);
    }

    @Override
    public void deleteOrderDetail(long idOrderDetail) throws DataNotFoundException {
        orderDetailRepository.delete(orderDetailRepository.findById(idOrderDetail)
                .orElseThrow(() -> new DataNotFoundException("Cannot find order detail with id : "+idOrderDetail)));
    }
}
