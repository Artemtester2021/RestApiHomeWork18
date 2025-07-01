package api;

import api.requests.crud.GetAccountUserBooksByIdResponseDto;
import api.requests.models.AddBookRequest;
import specs.DefaultRequestSpec;

import java.util.Collections;

import static api.endpoint.EndPoints.ACCOUNT_USER;
import static api.endpoint.EndPoints.BOOKSTORE_BOOKS;
import static io.restassured.RestAssured.given;
import static specs.CrudResponseSpecs.*;

public class BookApiRequests extends DefaultRequestSpec {

    public void addBookToProfile(String userId, String isbn) {
        AddBookRequest request = new AddBookRequest(userId, Collections.singletonList(new AddBookRequest.Isbn(isbn)));

        given()
                .spec(defaultRequestSpec())
                .body(request)
                .when()
                .post(BOOKSTORE_BOOKS)
                .then()
                .spec(addBookToProfileResponseSpec201);
    }


    public void deleteAllBooksFromProfileById(String userId) {
        given()
                .spec(defaultRequestSpec())
                .queryParam("UserId", userId)
                .when()
                .delete(BOOKSTORE_BOOKS)
                .then()
                .spec(deleteAllBooksFromProfileByIdResponseSpec204);
    }

    public GetAccountUserBooksByIdResponseDto getAccountUserBooksById(String userId) {
        return given()
                .spec(defaultRequestSpec())
                .queryParam("UserId", userId)
                .when()
                .get(ACCOUNT_USER + "/" + userId)
                .then()
                .spec(getAccountUserBooksByIdResponseSpec200)
                .extract()
                .as(GetAccountUserBooksByIdResponseDto.class);
    }
}

