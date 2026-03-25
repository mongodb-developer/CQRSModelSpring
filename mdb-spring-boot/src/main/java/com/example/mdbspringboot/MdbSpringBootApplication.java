package com.example.mdbspringboot;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.example.mdbspringboot.model.GroceryItem;
import com.example.mdbspringboot.repository.ItemWriteRepository;
import com.example.mdbspringboot.repository.ItemReadRepository;

@SpringBootApplication
@EnableMongoRepositories
public class MdbSpringBootApplication implements CommandLineRunner{
	
	@Autowired
	ItemReadRepository groceryItemReadRepo;
	
	@Autowired
	ItemWriteRepository groceryItemWritemRepo;
	
	List<GroceryItem> itemList = new ArrayList<GroceryItem>();

	public static void main(String[] args) {
		SpringApplication.run(MdbSpringBootApplication.class, args);
	}
	
	public void run(String... args) {
		

		System.out.println("-------------DELETE ALL GROCERY ITEMS-------------------------------\n");
		groceryItemWritemRepo.deleteAll(); // Doesn't delete the collection
		
		System.out.println("\n-------------CREATE GROCERY ITEMS-------------------------------\n");
		createGroceryItems();
		
		System.out.println("\n----------------SHOW ALL GROCERY ITEMS---------------------------\n");
		showAllGroceryItems();
		
		System.out.println("\n--------------GET ITEM BY NAME-----------------------------------\n");
		getGroceryItemByName("Whole Wheat Biscuit");
		
		System.out.println("\n-----------GET ITEMS BY CATEGORY---------------------------------\n");
		getItemsByCategory("millets");
		
		System.out.println("\n-----------UPDATE CATEGORY NAME OF ALL GROCERY ITEMS----------------\n");
		updateCategoryName("snacks");
		
		System.out.println("\n-----------UPDATE QUANTITY OF A GROCERY ITEM------------------------\n");
		updateItemQuantity("Bonny Cheese Crackers Plain", 10);
		
		System.out.println("\n----------DELETE A GROCERY ITEM----------------------------------\n");
		deleteGroceryItem("Kodo Millet");
		
		System.out.println("\n------------FINAL COUNT OF GROCERY ITEMS-------------------------\n");
		findCountOfGroceryItems();
		
		System.out.println("\n-------------------THANK YOU---------------------------");

		return;
	}
	
	// CRUD operations

	//CREATE
	void createGroceryItems() {
		System.out.println("Data creation started...");

		groceryItemWritemRepo.insert(new GroceryItem("Whole Wheat Biscuit", "Whole Wheat Biscuit", 5, "snacks"));
		groceryItemWritemRepo.insert(new GroceryItem("Kodo Millet", "XYZ Kodo Millet healthy", 2, "millets"));
		groceryItemWritemRepo.insert(new GroceryItem("Dried Red Chilli", "Dried Whole Red Chilli", 2, "spices"));
		groceryItemWritemRepo.insert(new GroceryItem("Pearl Millet", "Healthy Pearl Millet", 0, "millets"));
		groceryItemWritemRepo.insert(new GroceryItem("Cheese Crackers", "Bonny Cheese Crackers Plain", 6, "snacks"));
		
		System.out.println("Data creation complete");
	}
	
	// READ
	// 1. Show all the data
	 public void showAllGroceryItems() {
		 itemList = groceryItemReadRepo.findAll();
		 
		 itemList.forEach(item -> System.out.println(getItemDetails(item)));
	 }
	 
	 // 2. Get item by name
	 public void getGroceryItemByName(String name) {
		 System.out.println("Getting item by name: " + name);
		 GroceryItem item = groceryItemReadRepo.findItemByName(name);
		 System.out.println(getItemDetails(item));
	 }
	 
	 // 3. Get name and items of a all items of a particular category
	 public void getItemsByCategory(String category) {
		 System.out.println("Getting items for the category " + category);
		 List<GroceryItem> list = groceryItemReadRepo.findAll(category);
		 
		 list.forEach(item -> System.out.println("Name: " + item.getName() + ", Quantity: " + item.getItemQuantity()));
	 }
	 
	 // 4. Get count of documents in the collection
	 public void findCountOfGroceryItems() {
		 long count = groceryItemReadRepo.count();
		 System.out.println("Number of documents in the collection = " + count);
	 }

	 public void updateCategoryName(String category) {
		 
		 // Change to this new value
		 String newCategory = "munchies";
		 groceryItemWritemRepo.bulkUpdateItemCategories(category, newCategory);
	 }

	 public void updateItemQuantity(String name, float newQuantity) {
		 System.out.println("Updating quantity for " + name);
		 groceryItemWritemRepo.updateItemQuantity(name, newQuantity);
	 }
	 
	 // DELETE
	 public void deleteGroceryItem(String id) {
		 groceryItemWritemRepo.deleteById(id);
		 System.out.println("Item with id " + id + " deleted...");
	 }
	 // Print details in readable form
	 
	 public String getItemDetails(GroceryItem item) {

		 System.out.println(
		 "Item Name: " + item.getName() + 
		 ", \nItem Quantity: " + item.getItemQuantity() + 
		 ", \nItem Category: " + item.getCategory()
		 );
		 
		 return "";
	 }
}

