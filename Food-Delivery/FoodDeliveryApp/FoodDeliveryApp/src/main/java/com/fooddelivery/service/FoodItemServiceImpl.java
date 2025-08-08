package com.fooddelivery.service;

import com.fooddelivery.entity.FoodItem;
import com.fooddelivery.exception.ResourceNotFoundException;
import com.fooddelivery.repository.FoodItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodItemServiceImpl implements FoodItemService {

    private final FoodItemRepository foodItemRepository;
    public FoodItemServiceImpl(FoodItemRepository foodItemRepository) {
        this.foodItemRepository = foodItemRepository;
    }

    @Override
    public FoodItem createFoodItem(FoodItem foodItem) {
        return foodItemRepository.save(foodItem);
    }

    @Override
    public FoodItem getFoodItemById(Long id) {
        return foodItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Food Item not found"));
    }

    @Override
    public List<FoodItem> getAllFoodItems() {
        return foodItemRepository.findAll();
    }

    @Override
    public FoodItem updateFoodItem(Long id, FoodItem foodItem) {
        FoodItem existing = getFoodItemById(id);
        existing.setPrice(foodItem.getPrice());
        return foodItemRepository.save(existing);
    }

    @Override
    public void deleteFoodItem(Long id) {
        FoodItem existing = getFoodItemById(id);
        foodItemRepository.delete(existing);
    }
}

