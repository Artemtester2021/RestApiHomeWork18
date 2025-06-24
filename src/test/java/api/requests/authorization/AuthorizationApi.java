package api.requests.authorization;


import static api.endpoint.EndPoints.ACCOUNT_LOGIN;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static helpers.TestData.*;

public class AuthorizationApi {
    public static AuthResponseDto authorize() {
        String authData = "{\"userName\":\"" + login + "\",\"password\":\"" + password + "\"}";
        return given()
                .log().uri()
                .log().method()
                .log().body()
                .contentType(JSON)
                .body(authData)
                .when()
                .post(ACCOUNT_LOGIN)
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract()
                .as(AuthResponseDto.class);
    }
}
