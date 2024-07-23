package com.example.shopapp.Controller;
import com.example.shopapp.dataNotFoundException.DataNotFoundException;
import com.example.shopapp.dtos.OrderDTO;
import com.example.shopapp.models.Order;
import com.example.shopapp.response.OrderResponse;
import com.example.shopapp.services.IOrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/orders")
@RequiredArgsConstructor
public class OderController {
    private final IOrderService orderService;
    @PostMapping("")
    public ResponseEntity<?> createOrder(@Valid @RequestBody OrderDTO orderDTO, BindingResult result){
        try {
            if (result.hasErrors()){
                List<String> errorMessage = result.getFieldErrors().stream()
                        .map(FieldError::getDefaultMessage)
                        .toList()
                        ;
                return ResponseEntity.badRequest().body(errorMessage);
            }
            OrderResponse orderResponse = orderService.createOder(orderDTO);
            return ResponseEntity.ok(orderResponse);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable("id") long orderId){
        try {
            Order existingOrder = orderService.getOrderById(orderId);
            return ResponseEntity.ok(existingOrder);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/user/{user_id}")
    public ResponseEntity<?> getAllOrderByUserId(@PathVariable("user_id") long userId){
        try {
            List<Order> listOrder = orderService.getAllOrderByUser(userId);
            return ResponseEntity.ok(listOrder);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrder(
            @Valid @RequestBody OrderDTO orderDTO,
            @PathVariable long id,
            BindingResult result
    ){
        try{
            if (result.hasErrors()){
                List<String> errorMessage = result.getFieldErrors().stream()
                        .map(FieldError::getDefaultMessage)
                        .toList()
                        ;
                return ResponseEntity.badRequest().body(errorMessage);
            }
            Order newOrder = orderService.updateOrder(id, orderDTO);

            return ResponseEntity.ok(newOrder);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable long id) throws DataNotFoundException {
        orderService.deleteOrder(id);
        return ResponseEntity.ok("Order delete by id = " + id);
    }
}
