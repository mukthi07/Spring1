
package com.example.foodorder.controller;

import com.example.foodorder.model.FoodOrder;
import com.example.foodorder.service.FoodOrderService;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class FoodOrderController {

    private final FoodOrderService service;

    public FoodOrderController(FoodOrderService service) {
        this.service = service;
    }

    // GET: Get all orders
    @GetMapping
    public List<FoodOrder> getAllOrders() {
        return service.getAllOrders();
    }

    // GET by ID
    @GetMapping("/{id}")
    public FoodOrder getOrderById(@PathVariable int id) {
        return service.getOrderById(id);
    }

    // POST: Add order
    @PostMapping
    public ResponseEntity<FoodOrder> addOrder(
            @Valid @RequestBody FoodOrder order) {

        FoodOrder saved = service.addOrder(order);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(saved);
    }

    // PUT: Update complete order
    @PutMapping("/{id}")
    public FoodOrder updateOrder(
            @PathVariable int id,
            @Valid @RequestBody FoodOrder order) {

        return service.updateOrder(id, order);
    }

    // PATCH: Partial update
    @PatchMapping("/{id}")
    public FoodOrder patchOrder(
            @PathVariable int id,
            @RequestBody FoodOrder updates) {

        if (updates.getCustomerName() != null
                && updates.getCustomerName().isBlank()) {
            throw new IllegalArgumentException(
                    "Customer name is required");
        }

        if (updates.getFoodItem() != null
                && updates.getFoodItem().isBlank()) {
            throw new IllegalArgumentException(
                    "Food item cannot be empty");
        }

        if (updates.getQuantity() != null
                && updates.getQuantity() < 1) {
            throw new IllegalArgumentException(
                    "Quantity must be at least 1");
        }

        if (updates.getPrice() != null
                && updates.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                    "Price must be positive");
        }

        return service.patchOrder(id, updates);
    }

    // DELETE: Delete order
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(
            @PathVariable int id) {

        service.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}
