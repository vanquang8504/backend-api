package com.example.shopapp.Controller;

import com.example.shopapp.dtos.CategoryDTO;
import com.example.shopapp.services.CategoryService;
import com.example.shopapp.services.ICategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/categories")
//@Validated
public class CategoryController {
    private final ICategoryService iCategoryService;
    @PostMapping("")
    public ResponseEntity<?> createCategories(@Valid @RequestBody CategoryDTO categoryDTO, BindingResult result){
        if (result.hasErrors()){
            List<String> errorMessage = result.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage)
                    .toList()
                    ;
            return ResponseEntity.badRequest().body(errorMessage);
        }
        iCategoryService.createCategory(categoryDTO);
        return ResponseEntity.ok("insert category successfully");
    }
    @GetMapping("")
    public ResponseEntity<?> getAllCategories(
            @RequestParam("page") int page,
            @RequestParam("limit") int limit
    ){
        return ResponseEntity.ok(iCategoryService.getAllCategory());
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategories(@PathVariable Long id,@Valid @RequestBody CategoryDTO categoryDTO,BindingResult result){
        if (result.hasErrors()){
            List<String> errorMessage = result.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage)
                    .toList()
                    ;
            return ResponseEntity.badRequest().body(errorMessage);
        }
        iCategoryService.updateCategory(id, categoryDTO);
        return ResponseEntity.ok("update category successfully");
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategories(@PathVariable Long id){
        iCategoryService.deleteCategory(id);
        return ResponseEntity.ok("delete category successfully");
    }

}
