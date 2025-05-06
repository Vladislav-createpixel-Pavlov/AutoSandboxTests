package services;

import com.google.common.base.Function;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;


public class RestResponse<T> {
    private Response response;
    private Function<Response, T> extractor;

    public RestResponse(Response response, Function<Response, T> extractor){
        this.response = response;
        this.extractor = extractor;
    }

    public void validate(String schemaPath){
        response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath(schemaPath));
    }
    public void assertCode(int code){
        response.then().statusCode(code);
    }


    public T extract(){
        return extractor.apply(response);
    }
}
