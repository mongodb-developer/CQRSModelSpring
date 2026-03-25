package com.example.mdbspringboot.repository;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertOneResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.example.mdbspringboot.model.GroceryItem;
import com.mongodb.client.result.UpdateResult;

@Component
public class ItemWriteRepositoryImpl implements ItemWriteRepository {

	@Autowired
	MongoTemplate mongoTemplate;
	
	public void updateItemQuantity(String name, float newQuantity) {

		Query query = new Query(Criteria.where("name").is(name));
		Update update = new Update();
		update.set("quantity", newQuantity);
		
		UpdateResult result = mongoTemplate.updateFirst(query, update, GroceryItem.class);
		
		if(result == null)
			System.out.println("No documents updated");
		else
			System.out.println(result.getModifiedCount() + " document(s) updated");
	}

	public void bulkUpdateItemCategories(String category, String newCategory) {
		Query query = new Query(Criteria.where("category").is(category));
		Update update = new Update();
		update.set("category", newCategory);

		UpdateResult result = mongoTemplate.updateMulti(query, update, GroceryItem.class);

		if(result == null)
			System.out.println("No documents updated");
		else
			System.out.println(result.getModifiedCount() + " document(s) updated");
	}

	public void deleteAll() {
		DeleteResult result = mongoTemplate.remove(new Query(), GroceryItem.class);

		if(result == null)
			System.out.println("No documents deleted");
		else
			System.out.println(result.getDeletedCount() + " document(s) deleted");
	}

	public void insert(GroceryItem itm){
		if(validate(itm)) {
			GroceryItem result = mongoTemplate.insert(itm);

			if (result == null)
				System.out.println("No document inserted");
			else
				System.out.println("  " + result.getName() + " inserted");
		}
		else
			System.out.println("  Could not insert " + itm.getName() + " due to validation error above.");

	}

	public void deleteById(String id){
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(id));

		DeleteResult result = mongoTemplate.remove(query, GroceryItem.class);

		if(result == null)
			System.out.println("No document inserted");
		else
			System.out.println(result.getDeletedCount() + " documents deleted");
	}

	public boolean validate(GroceryItem itm){
		boolean result = true;
		if(itm.getItemQuantity() <= 0)
		{
			System.out.println("  **Validation error - quantity for item " +
					itm.getName() + " must be greater than zero.**");
			result = false;
		}
		return result;
	}
}
