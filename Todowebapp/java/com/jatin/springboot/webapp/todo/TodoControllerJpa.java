package com.jatin.springboot.webapp.todo;
//doing duplicate mapping of all 
import java.time.LocalDate;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import jakarta.validation.Valid;

@Controller
@SessionAttributes("name" )
public class TodoControllerJpa {
	
	public TodoControllerJpa(TodoRepository todoRepository) {
		super();
//		this.todoService = todoService;
		this.todoRepository=todoRepository;
	}
	//to autowire it add this to the above constructor 
//	private TodoService todoService;
	
	private TodoRepository todoRepository;
	
	
	@RequestMapping("list-todos")
	public String listAllTodos(ModelMap model) {
		String username=getLoggedInUsername(model);//getLoggedInUsername instead of (String)model.get("name")
        // todoRepository instead of todoService
 		//method names in the service would match to the todoRepository to a large extent 
		List<Todo> todos = todoRepository.findByUsername(username);
		model.addAttribute("todos",todos);
		return "listTodos";
	}

	
	
	@RequestMapping(value="add-todo", method=RequestMethod.GET)
	public String showNewTodoPage(ModelMap model) {
		String username=getLoggedInUsername(model);
		Todo todo=new Todo(0,username,"",LocalDate.now().plusYears(1),false);
		model.put("todo", todo);
		return "todo";
	}
	
	@RequestMapping(value="add-todo", method=RequestMethod.POST)
	public String addNewTodo(ModelMap model, @Valid Todo todo, BindingResult result) {//instead binding to model map telling spring mvc to directly bind to todo bean 
		
		if(result.hasErrors()) {
			return "todo";
		}
		//doing same with todo.jsp // this is form backing
		//modelmap to get the username 
		String username=getLoggedInUsername(model);
		todo.setUsername(username);
		//sending all the values in todo bean b/c todoRepository does not have any method which would take all the attributes. Save method  will accept todo as the input 
		todoRepository.save(todo);
//		todoService.addTodo(username,todo.getDescription(),todo.getTargetDate(),todo.isDone()); //in this line accepting each individual attribute as a parameters.  
		return "redirect:list-todos"; //it will redirect the list of todos that are stored 
	}
	
	
	@RequestMapping("delete-todo")
	public String deleteTodos(@RequestParam int id) {
		//Delete todo
		todoRepository.deleteById(id);
		//todoService.deleteById(id);
		return "redirect:list-todos"; 
	
	}	
	
	@RequestMapping(value="update-todo", method=RequestMethod.GET)
	public String showUpdateTodoPage(@RequestParam int id, ModelMap model) {
		Todo todo=todoRepository.findById(id).get();
		model.addAttribute("todo",todo);
		return "todo"; 
}
	
	@RequestMapping(value="update-todo", method=RequestMethod.POST)
	public String updateTodo(ModelMap model, @Valid Todo todo, BindingResult result) {//instead binding to model map telling spring mvc to directly bind to todo bean 
		
		if(result.hasErrors()) {
			return "todo";
		}
		String username=getLoggedInUsername(model);
		todo.setUsername(username);
		todoRepository.save(todo);
//		todoService.updateTodo(todo);
		return "redirect:list-todos"; //it will redirect the list of todos that are stored 
	}
	private String getLoggedInUsername(ModelMap model) {
	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	return authentication.getName();
//	private String getLoggedInUsername(ModelMap model) {
//		return (String)model.get("name");
	}
	}
	
