package com.jatin.springboot.webapp.todo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Integer> {

	//using findByUsername b/c username is a field in the todo entity and not a Id.
	//if want to search by Id then use JpaRepository and if using field then need to create a method in this 
		
	public List<Todo> findByUsername(String username); //username is an attribute inside the todo bean and spring
	//DataJPA would automatically provided search by username 
	
	
	
}
 