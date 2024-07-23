package com.example.shopapp.Controller;
import com.example.shopapp.dtos.OrderDetailDTO;
import com.example.shopapp.models.OrderDetail;
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
            return ResponseEntity.ok(newOrderDetail);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderDetail(@PathVariable long id){
        return ResponseEntity.ok("Order detail with orderId = " + id);
    }
    @GetMapping("/order/{orderId}")
    public ResponseEntity<?> getOrderDetails(@PathVariable long orderId){
        return ResponseEntity.ok("Order with orderId = " + orderId);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrderDetails(
            @PathVariable long id,
            @Valid  @RequestBody OrderDetailDTO newOrderDetailDTO,
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
            return ResponseEntity.ok("Order detail update with id = " + id);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrderDetails(@PathVariable long id){
        return ResponseEntity.ok("delete order detail with Id = " + id);
    }
}
