# mongodb-springboot
The project demonstrates Command Query Responsibility Segregation (CQRS) using MongoDB and SpringBoot for the repository pattern

For this [tutorial](https://www.mongodb.com/compatibility/spring-boot), we need to create a Spring Boot project, which can be done easily using Spring Initialzr. It is advantageous to use an IDE (Integrated Development Environment) like Eclipse for this tutorial.

# Project Setup
Once the project is setup, we need to create Java class and fields that will be mapped into MongoDB collection and fields.

Edit the 'application.properties' file to point to a MongoDB deployment.

# CRUD Examples using MongoRepository
### To create a document (item) in MongoDB, use the save() method:

  `groceryItemWritemRepo.save(new GroceryItem("Dried Red Chilli", "Dried Whole Red Chilli", 2, "spices"));`
  
### To Read all the documents, use findAll() method:
  `groceryItemReadRepo.findAll().forEach(item-> item.getName(),item.getQuantity(),item.getCategory());`
  
### To Read documents based on a particular field, like name or category, by specifying the query parameters:
  	@Query("{name:'?0'}")
	GroceryItem findItemByName(String name);
	
	@Query(value="{category:'?0'}", fields="{'name' : 1, 'quantity' : 1}")
	List<GroceryItem> findAll(String category);
  
  
 ### To delete a document, use the delete method. For example, delete an item by ID using the deleteById() method:
  `groceryItemWritemRepo.deleteById(id);`
	
 # Update using MongoTemplate 
 Updates using MongoTemplate are much easier with the out-of-box classes provided by MongoTemplate.
 Let us update the quantity of a particular item: 
  	
	Query query = new Query(Criteria.where("name").is(name));
   	Update update = new Update();
   	update.set("quantity", newQuantity);		
   	UpdateResult result = mongoTemplate.updateFirst(query, update, GroceryItem.class);
  	
# Disclaimer
This software is not supported by MongoDB, Inc under any of their commercial support subscriptions or otherwise. Any usage is at your own risk.

