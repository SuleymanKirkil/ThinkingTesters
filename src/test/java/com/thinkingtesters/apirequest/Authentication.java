package com.thinkingtesters.apirequest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class Authentication {
    public static String generateToken(){
        String url ="https://thinking-tester-contact-list.herokuapp.com/users/login";
        String body = "{\n" +
                "    \"email\": \"johny.doey@example.com\",\n" +
                "    \"password\": \"Test123!\"\n" +
                "}";
        Response response = given().body(body).contentType(ContentType.JSON).post(url);
        return response.jsonPath().getString("token");
    }

}
