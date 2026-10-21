
package com.example.foodorder.service;

import com.example.foodorder.exception.OrderNotFoundException;
import com.example.foodorder.model.FoodOrder;
import com.example.foodorder.repository.FoodOrderRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodOrderService {

    private final FoodOrderRepository repository;

    public FoodOrderService(FoodOrderRepository repository) {
        this.repository = repository;
    }

    public List<FoodOrder> getAllOrders() {
        return repository.findAll();
    }

    public FoodOrder getOrderById(int id) {
        return repository.findById(id)
                .orElseThrow(() ->
                    new OrderNotFoundException(
                        "Order not found with ID: " + id));
    }

    public FoodOrder addOrder(FoodOrder order) {
        order.setOrderId(0);
        return repository.save(order);
    }

    public FoodOrder updateOrder(int id, FoodOrder newOrder) {
        FoodOrder existing = getOrderById(id);

        existing.setCustomerName(newOrder.getCustomerName());
        existing.setFoodItem(newOrder.getFoodItem());
        existing.setQuantity(newOrder.getQuantity());
        existing.setPrice(newOrder.getPrice());

        return repository.save(existing);
    }

    public FoodOrder patchOrder(int id, FoodOrder updates) {
        FoodOrder existing = getOrderById(id);

        if (updates.getCustomerName() != null) {
            existing.setCustomerName(updates.getCustomerName());
        }

        if (updates.getFoodItem() != null) {
            existing.setFoodItem(updates.getFoodItem());
        }

        if (updates.getQuantity() != null) {
            existing.setQuantity(updates.getQuantity());
        }

        if (updates.getPrice() != null) {
            existing.setPrice(updates.getPrice());
        }

        return repository.save(existing);
    }

    public void deleteOrder(int id) {
        getOrderById(id);
        repository.deleteById(id);
    }
}
