package com.thinkingtesters.apirequest;

import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.thinkingtesters.utils.ApiEndpoints;

import static com.thinkingtesters.apirequest.ThinkingTestersBaseUrl.spec;
import static io.restassured.RestAssured.given;

public class UserApiRequests {
    private static final Logger logger = LogManager.getLogger(UserApiRequests.class);

    public static Response deleteUser() {
        logger.info("Deleting user with endpoint: /{}", ApiEndpoints.DELETE_USER);
        spec.pathParams("first", ApiEndpoints.DELETE_USER);
        Response response = given(spec).delete("{first}");
        logger.info("Delete user response status: {}", response.getStatusCode());
        
        if (response.getStatusCode() != 200) {
            logger.error("Failed to delete user. Response: {}", response.asString());
        }
        
        return response;
    }

    public static void main(String[] args) {
        deleteUser();
    }
}
