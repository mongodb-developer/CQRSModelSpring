package com.example.mdbspringboot.repository;

import com.example.mdbspringboot.model.GroceryItem;
import org.springframework.data.mongodb.repository.DeleteQuery;
import org.springframework.data.mongodb.repository.Query;

public interface ItemWriteRepository {
	
	void updateItemQuantity(String itemName, float newQuantity);

	void bulkUpdateItemCategories(String category, String newCategory);

	void deleteAll();

	void deleteById(String id);

	void insert(GroceryItem item);

}
