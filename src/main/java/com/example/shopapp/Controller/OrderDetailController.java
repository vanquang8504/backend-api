package com.example.shopapp.Controller;
import com.example.shopapp.dataNotFoundException.DataNotFoundException;
import com.example.shopapp.dtos.OrderDetailDTO;
import com.example.shopapp.models.OrderDetail;
import com.example.shopapp.response.OrderDetailResponse;
import com.example.shopapp.services.IOrderDetailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/orderDetails")
@RequiredArgsConstructor
public class OrderDetailController {
    private final IOrderDetailService orderDetailService;

    @PostMapping("")
    public ResponseEntity<?> createOrderDetail(@Valid @RequestBody OrderDetailDTO orderDetailDTO, BindingResult result){
        try {
            if (result.hasErrors()){
                List<String> errorMessage = result.getFieldErrors().stream()
                        .map(FieldError::getDefaultMessage)
                        .toList()
                        ;
                return ResponseEntity.badRequest().body(errorMessage);
            }
            OrderDetail newOrderDetail = orderDetailService.createOderDetail(orderDetailDTO);
            return ResponseEntity.ok(OrderDetailResponse.formOrderDetailResponse(newOrderDetail));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderDetail(@PathVariable long id){
        try {
            OrderDetail existingOrderDetail = orderDetailService.getOrderDetailById(id);
            return ResponseEntity.ok(OrderDetailResponse.formOrderDetailResponse(existingOrderDetail));
            //return ResponseEntity.ok(existingOrderDetail);
        } catch (DataNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/order/{orderId}")
    public ResponseEntity<?> getOrderDetails(@PathVariable long orderId){
        try {
            List<OrderDetail> orderDetails = orderDetailService.getAllOrderDetail(orderId);
            List<OrderDetailResponse> orderDetailResponses = orderDetails.stream()
                    .map(OrderDetailResponse::formOrderDetailResponse)
                    .toList();
            return ResponseEntity.ok(orderDetailResponses);
        } catch (DataNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrderDetails(
            @PathVariable long id,
            @Valid  @RequestBody OrderDetailDTO orderDetailDTO,
            BindingResult result
    ){
        try {
            if (result.hasErrors()){
                List<String> errorMessage = result.getFieldErrors().stream()
                        .map(FieldError::getDefaultMessage)
                        .toList()
                        ;
                return ResponseEntity.badRequest().body(errorMessage);
            }
            OrderDetail newOrderDetail = orderDetailService.updateOderDetail(id, orderDetailDTO);
            return ResponseEntity.ok(newOrderDetail);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrderDetails(@PathVariable long id){
        try {
            orderDetailService.deleteOrderDetail(id);
            return ResponseEntity.ok("Delete successfully with id order detail : "+id);
        } catch (DataNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
