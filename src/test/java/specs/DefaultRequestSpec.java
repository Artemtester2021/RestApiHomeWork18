package specs;


import api.AccountApiRequests;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.with;
import static io.restassured.http.ContentType.JSON;

public class DefaultRequestSpec {

    public static RequestSpecification defaultRequestSpec() {
        return new RequestSpecBuilder()
                .setContentType(JSON)
                .addHeader("Authorization", "Bearer " + AccountApiRequests.authorize().getToken())
                .log(LogDetail.URI)
                .log(LogDetail.METHOD)
                .log(LogDetail.BODY)
                .build();
    }

    public static RequestSpecification authRequestSpec = with()
            .filter(withCustomTemplates())
            .log().uri()
            .log().method()
            .log().body()
            .log().headers()
            .contentType(JSON);

}

