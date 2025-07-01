package api;


import api.requests.authorization.AuthRequestDto;
import api.requests.authorization.AuthResponseDto;

import static api.endpoint.EndPoints.ACCOUNT_LOGIN;
import static specs.DefaultRequestSpec.authRequestSpec;
import static io.restassured.RestAssured.given;
import static tests.TestConstants.login;
import static tests.TestConstants.password;


public class AccountApiRequests {
    public static AuthResponseDto authorize() {
        AuthRequestDto authData = new AuthRequestDto(login, password);

        return given(authRequestSpec)
                .body(authData)
                .when()
                .post(ACCOUNT_LOGIN)
                .then()
                .statusCode(200)
                .extract().as(AuthResponseDto.class);
    }
}
