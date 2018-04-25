package dotDashProject;
import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import static org.hamcrest.Matchers.*;

public class TodoListTest {
	public static final String baseURL = "http://localhost:8080/dotdash-project/";
	@Test
	public void testResponseCode() {
	//Test case: Check the status code of api
	Response res = RestAssured.get(baseURL+"fake-api-call.php");
	 int code = res.getStatusCode();
	 System.out.println(code);
	 System.out.println(res.asString());
	 }
	
	@Test
	public void testVerifyCountBeforeAdd() {
	//Counting number of task in todo list 
		
	  ValidatableResponse response = given().when().get(baseURL+"fake-api-call.php").then();
	  response.statusCode(200);
	  assertThat(response.extract().jsonPath().getList("$").size(), equalTo(9));
	}
	
   
	@Test
	public void testAddTodoList() {
    // Add todo element in the list: Sending data to api using POST calls
		Map<String,Object> todoAdd = new HashMap<String,Object>();
        todoAdd.put("status", "");
        todoAdd.put("task name", "Car Cleaning");
        todoAdd.put("category", "2");
        todoAdd.put("due date", new java.util.Date());
        
        given()
        .contentType("application/json")
        .body(todoAdd)
        .when().post(baseURL+"todoAdd.php").then() //added fake endpoint for addoing elements 
        .statusCode(200);
        
	}
	
	@Test
	public void testVerifyCountAfterAdd() {
	//Counting number of task in todo list 
		
	  ValidatableResponse response = given().when().get(baseURL+"fake-api-call.php").then();
	  response.statusCode(200);
	  assertThat(response.extract().jsonPath().getList("$").size(), equalTo(10));
	}

	
	@Test
	public void testGetTodoListByName() {
	//Retriving  todo list by name using GET call
		
	  Response res = get(baseURL+ "fake-api-call.php/todolist/10");
	  assertEquals(200, res.getStatusCode());
	  String json = res.asString();
	  assertEquals("application/json", res.getContentType()); //checking contain type of the resposne 
	  JsonPath jp = new JsonPath(json);
	  assertEquals("10", jp.get("id"));
	  assertEquals("Car Cleaning", jp.get("task name"));
	  assertEquals("", jp.get("status"));
	  assertEquals("2", jp.get("category"));
	  assertEquals("201804567983", jp.get("due date"));
	}
	
	@Test
	public void testUpadteTodoListByName() {
	// Updating the todo element by name using PUT method
		 Map<String, Object> todoUpdate = new HashMap<String, Object>();
		 todoUpdate.put("status", "c");
		 todoUpdate.put("task name", "Car Cleaning");
		 todoUpdate.put("category", "2");
		 todoUpdate.put("due date", new java.util.Date());
	        
	        given()
	        .contentType("application/json")
	        .body(todoUpdate)
	        .when().put(baseURL+"fake-api-call.php/todolist/").then()
	        .statusCode(200);
	 }
	
	@Test
	public void testTodoStatusUpdate() {
	//Retriving  todo list by name using GET call
		
	  Response res = get(baseURL+ "fake-api-call.php/todolist/10");
	  assertEquals(200, res.getStatusCode());
	  String json = res.asString();
	  assertEquals("application/json", res.getContentType()); //checking contain type of the resposne 
	  JsonPath jp = new JsonPath(json);
	  assertEquals("10", jp.get("id"));
	  assertEquals("Car Cleaning", jp.get("task name"));
	  assertEquals("c", jp.get("status"));
	  assertEquals("2", jp.get("category"));
	  assertEquals("201804567983", jp.get("due date"));
	}
	
	@Test
	public void testRemoveTodo() {
	// removing task name from list using DELETE call
		Response res = get(baseURL+ "fake-api-call.php/todolist");
		  assertEquals(200, res.getStatusCode());
		  //String json = res.asString();
		  given().pathParam("task name", "Car Cleaning")
	        .when().delete("/fake-api-call.php/todolist/{Car Cleaning}")
	        .then().statusCode(200);
		}
	
   

}

