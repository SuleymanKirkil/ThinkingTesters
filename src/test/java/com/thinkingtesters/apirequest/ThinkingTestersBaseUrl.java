package com.thinkingtesters.apirequest;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static com.thinkingtesters.apirequest.Authentication.generateToken;

public class ThinkingTestersBaseUrl {
        public  static RequestSpecification spec;
    static {
        spec = new RequestSpecBuilder()
                .setBaseUri("https://thinking-tester-contact-list.herokuapp.com/users")
                .addHeader("Authorization","Bearer "+generateToken())
                .setContentType(ContentType.JSON)
                .build();
    }

}
