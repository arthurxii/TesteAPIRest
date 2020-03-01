import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.json.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.core.IsEqual.equalTo;

public class testApi {
    @BeforeClass
    public void setBaseUri(){
        baseURI = "https://reqres.in/api/users";
    }

    @Test
    public void getPrimeiraPagina(){
        given().
                param("page", "1").
                when().
                get(baseURI).
                then().
                assertThat().
                statusCode(200).
                body("page", equalTo(1));

    }

    @Test
    public void getUsuario(){
        get(baseURI + "/2").
                then().
                statusCode(200).
                assertThat().
                body("data.id", equalTo(2));
    }

    @Test
    public void postCriarUsuarioTest(){
        JSONObject requestParams = new JSONObject();
        requestParams.put("name", "Arthur");
        requestParams.put("job", "Quality Assurance Analyst");

        given().
                body(requestParams.toString()).
                when().
                post(baseURI).
                then().
                statusCode(201).
                and().
                contentType(ContentType.JSON).
                assertThat().
                body(containsString("createdAt"));

    }

    @Test
    public void putUsuarioTest(){
        JSONObject requestParams = new JSONObject();
        requestParams.put("name", "Arthur Henrique");
        requestParams.put("job", "QA");

        given().
                body(requestParams.toString()).
                when().
                contentType(ContentType.JSON).
                put(baseURI + "/2").
                then().
                statusCode(200).
                assertThat().
                body(containsString("updatedAt"));
    }

    @Test
    public void patchUsuarioTest(){
        JSONObject requestParams = new JSONObject();
        requestParams.put("name", "Arthur Henrique Dias da Silva");
        requestParams.put("job", "Test Analyst");

        given().
                body(requestParams.toString()).
                when().
                contentType(ContentType.JSON).
                patch("https://reqres.in/api/users/2").
                then().
                statusCode(200).
                assertThat().
                body(containsString("updatedAt"));
    }

    @Test
    public void deletarUsuarioTest(){
        when().
                delete(baseURI + "/2").
                then().
                assertThat().
                statusCode(204);
    }

}
