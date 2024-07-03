package Users;
import org.hamcrest.Condition;
import org.testng.annotations.Test;

import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class RandomUsers {
    @Test
    public void GetRandomUser() {

        given().baseUri("https://randomuser.me/")
                .header("Content-Type", "application/json")
                .when().get("https://randomuser.me/api/")
                .then().log().all()
                .assertThat().statusCode(200)
                .body("results", notNullValue());

    }


    @Test
    public void GetMultipleUsers() {

        given().baseUri("https://randomuser.me/api/")
                .param("results", 5)
                .when().get("https://randomuser.me/api/?results=5")
                .then().log().all()
                .assertThat().statusCode(200)
                .body("results", hasSize(5));

    }

    @Test
    public void GetUserByGender() {

        given().baseUri("https://randomuser.me/api/")
                .param("gender", "female")
                .when().get("https://randomuser.me/api/?gender=female")
                .then().log().all()
                .assertThat().statusCode(200)
                .body(containsString("female"));

    }

    @Test
    public void CreateUser() {

        HashMap<String, Object> body = new HashMap<>();
        body.put("name", "John Doe");
        body.put("username", "johndoe");
        body.put("email", "john.doe@example.com");
        body.put("phone", "123-456-7890");
        body.put("website", "http://johndoe.com");

        HashMap<String, Object> addressMap = new HashMap<>();
        addressMap.put("street", "123 Main St");
        addressMap.put("city", "New York");
        addressMap.put("zipcode", "10001");
        body.put("address", addressMap);

        HashMap<String, Object> Company = new HashMap<>();
        Company.put("name", "Doe Enterprises");
        body.put("company", Company);

        given().baseUri("https://jsonplaceholder.typicode.com/users")
                .body(body)
                .when().post()
                .then().log().all()
                .assertThat().statusCode(201);
    }

    @Test
    public void UpdateUser() {

        HashMap<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("name", "John Doe Updated");
        bodyMap.put("username", "john.doe.updated");
        bodyMap.put("email", "john.doe.updated@example.com");
        bodyMap.put("phone", "987-654-3210");
        bodyMap.put("website", "http://johndoeupdated.com");


        HashMap<String, Object> addressMap = new HashMap<>();
        addressMap.put("street", "456 Main St");
        addressMap.put("city", "New York");
        addressMap.put("zipcode", "10002");
        bodyMap.put("address", addressMap);

        HashMap<String, Object> Company = new HashMap<>();
        Company.put("name", "Doe Enterprises Updated");
        bodyMap.put("company", Company);
        bodyMap.put("id",1);

        given().baseUri("https://jsonplaceholder.typicode.com")
                .header("Content-Type" , "application/json")
                .body(bodyMap)
                .when().put("/users/1")
                .then().log().all()
                .assertThat().statusCode(200)
                .body("name", equalTo("John Doe Updated"))
                .body("username", equalTo("john.doe.updated"))
                .body("email", equalTo("john.doe.updated@example.com"))
                .body("address.street", equalTo("456 Main St"))
                .body("address.city", equalTo("New York"))
                .body("address.zipcode", equalTo("10002"))
                .body("phone", equalTo("987-654-3210"))
                .body("website", equalTo("http://johndoeupdated.com"))
                .body("company.name", equalTo("Doe Enterprises Updated"));


    }

    @Test
    public void UpdatePartialUsersInfo(){

        HashMap<String,String> Body = new HashMap<>();
        Body.put("email", "john.new.email@example.com");
        Body.put("phone", "111-222-3333");
        given().baseUri("https://jsonplaceholder.typicode.com")
                .header("Content-Type" , "application/json")
                .body(Body)
                .when().patch("/users/1")
                .then().log().all()
                .assertThat().statusCode(200)
                .body("email", equalTo("john.new.email@example.com"))
                .body("phone", equalTo("111-222-3333"));

    }

    @Test
    public void DeleteUser(){
        given().baseUri("https://jsonplaceholder.typicode.com")
                .when().delete("/users/1")
                .then().log().all()
                .assertThat().statusCode(200);


    }

}

