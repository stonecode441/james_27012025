package com.wema.test.api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class APITests {

    static {
        RestAssured.baseURI = "http://dummy.restapiexample.com/api/v1";
//        RestAssured.baseURI = "http://localhost:8080/api/v1";// URL used to run test by running test project locally on system because of issues with remote URL
    }

    @Test
    public void testCreateEmployerValidPayload() {
        String payload = """
                {
                    "name": "Sample Name",
                    "salary": "1230999",
                    "age": "52"
                }
                """;

        Response response = given()
                .contentType(ContentType.JSON)
                .body(payload)
                .post("/create");

        response.then().statusCode(200);
//        Assertions.assertEquals("success", response.jsonPath().getString("status"));
    }

    @Test
    public void testCreateEmployerMissingFields() {
        String payload = """
                {
                    "salary": "1230999"
                }
                """;

        Response response = given()
                .contentType(ContentType.JSON)
                .body(payload)
                .post("/create");

        response.then().statusCode(400);
    }

    @Test
    public void testFetchAllEmployees() {
        Response response = given()
                .get("/employees");

        response.then().statusCode(200);
        Assertions.assertTrue(response.jsonPath().getList("data").size() > 0);
    }

    @Test
    public void testFetchSingleEmployeeValidID() {
        Response response = given()
                .get("/employee/5");

        response.then().statusCode(200);
        Assertions.assertEquals(5, response.jsonPath().getInt("id"));
    }

    @Test
    public void testFetchSingleEmployeeInvalidID() {
        Response response = given()
                .get("/employee/99999");

        response.then().statusCode(404);
    }

    @Test
    public void testDeleteSingleEmployeeValidID() {
        Response response = given()
                .delete("/employee/1");

        response.then().statusCode(200);
    }

    @Test
    public void testDeleteSingleEmployeeInvalidID() {
        Response response = given()
                .delete("/employee/99999");

        response.then().statusCode(404);
    }

    @Test
    public void testRateLimiting() {
        for (int i = 0; i < 10; i++) {
            Response response = given()
                    .get("/employees");

            if (response.getStatusCode() == 429) {
                System.out.println("Rate limit hit: Retry after " + response.header("Retry-After") + " seconds.");
                break;
            }
        }
    }
}
