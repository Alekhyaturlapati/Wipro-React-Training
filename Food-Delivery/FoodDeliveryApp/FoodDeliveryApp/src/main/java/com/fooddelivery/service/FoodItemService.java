package com.fooddelivery.service;

import com.fooddelivery.entity.FoodItem;
import java.util.List;

public interface FoodItemService {
    FoodItem createFoodItem(FoodItem foodItem);
    FoodItem getFoodItemById(Long id);
    List<FoodItem> getAllFoodItems();
    FoodItem updateFoodItem(Long id, FoodItem foodItem);
    void deleteFoodItem(Long id);
}
