
package com.example.foodorder.repository;

import com.example.foodorder.model.FoodOrder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class FoodOrderRepository {

    private final Map<Integer, FoodOrder> orders =
            new HashMap<>();

    private final AtomicInteger idGenerator =
            new AtomicInteger(1);

    public List<FoodOrder> findAll() {
        return new ArrayList<>(orders.values());
    }

    public Optional<FoodOrder> findById(int id) {
        return Optional.ofNullable(orders.get(id));
    }

    public FoodOrder save(FoodOrder order) {
        if (order.getOrderId() == 0) {
            order.setOrderId(idGenerator.getAndIncrement());
        }

        orders.put(order.getOrderId(), order);
        return order;
    }

    public void deleteById(int id) {
        orders.remove(id);
    }
}
