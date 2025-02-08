package com.thinkingtesters.apirequest;

import io.restassured.response.Response;

import static com.thinkingtesters.apirequest.ThinkingTestersBaseUrl.spec;
import static io.restassured.RestAssured.given;

public class UserApiRequests {
    public static Response deleteUser(){
        spec.pathParams("first","me");
        return given(spec).delete("{first}");
    }

    public static void main(String[] args) {
        deleteUser();
    }
}
