package api.specs;


import api.requests.authorization.AuthorizationApi;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;

import static io.restassured.http.ContentType.JSON;

public class DefaultRequestSpec {

    protected static RequestSpecification defaultRequestSpec() {
        return new RequestSpecBuilder()
                .setContentType(JSON)
                .addHeader("Authorization", "Bearer " + AuthorizationApi.authorize().getToken())
                .log(LogDetail.URI)
                .log(LogDetail.METHOD)
                .log(LogDetail.BODY)
                .build();
    }
}

