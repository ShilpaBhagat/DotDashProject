package dotDashProject;

import static com.jayway.jsonpath.Criteria.where;
import static com.jayway.jsonpath.Filter.filter;
import static io.restassured.RestAssured.get;
import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.jayway.jsonpath.Filter;
import com.jayway.jsonpath.JsonPath;

import io.restassured.response.Response;
public class CheckEmptyCategoryTask {
	public static final String baseURL = "http://localhost:8080/dotdash-project/";


	@Test
	public void testAgrregateTaskName() {
	// Agreegate task names 
		Response res = get(baseURL+ "fake-api-call.php");
		  assertEquals(200, res.getStatusCode());
		  assertEquals("application/json", res.getContentType()); //checking contain type of the resposne 
		  String aggrTaskName = "";
		  assertEquals("application/json", res.contentType());
		  java.util.List<String> tasknames = res.path("task name");
		  for(String taskname :tasknames )
		  {   
			  aggrTaskName = taskname +",";  
		  }
		  System.out.println(aggrTaskName);
		}
	
	@Test
	public void testEmptyCategory() {
		// Category Empty 
		Response res = get(baseURL+ "fake-api-call.php");
		  assertEquals(200, res.getStatusCode());
		  assertEquals("application/json", res.getContentType()); //checking contain type of the resposne 
		  String json = res.asString();
		  Filter emptyCategoryFilter = filter(where("category").exists(false)).or(where("category").empty(true));
		  List<Map<String, Object>> emptyCategories = JsonPath.parse(json).read("$", emptyCategoryFilter);
		  for(Map emptyCategoryMap : emptyCategories)
		  {
			  System.out.println(emptyCategoryMap.get("task name"));
		  }
		  
		  
		  
	}
	
}
