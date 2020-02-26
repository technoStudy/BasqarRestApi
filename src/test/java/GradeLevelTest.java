import io.restassured.http.ContentType;
import model.GradeLevel;
import model.Notification;
import org.testng.annotations.Test;
import utility.BaseTest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class GradeLevelTest extends BaseTest {
    private String apiPath = "/school-service/api/grade-levels";

    @Test
    public void createTest() {
        GradeLevel model = getBody();

        // creating entity
        String entityId = given()
                .cookies( cookies )
                .body( model )
                .contentType( ContentType.JSON )
                .when()
                .log().body()
                .post( apiPath )
                .then()
                .log().body()
                .statusCode( 201 )
                .extract().jsonPath().getString( "id" );

        // deleting entity
        given()
                .cookies( cookies )
                .when()
                .log().body()
                .delete( apiPath + "/" + entityId )
                .then()
                .log().body()
                .statusCode( 200 )
        ;
    }

    private GradeLevel getBody() {
        GradeLevel model = new GradeLevel();
        model.setName( name );
        model.setShortName( code );
        model.setOrder( "1223" );
        model.setActive( true );
        return model;
    }

    @Test
    public void editTest() {
        GradeLevel entity = getBody();

        // creating entity
        String entityId = given()
                .cookies( cookies )
                .body( entity )
                .contentType( ContentType.JSON )
                .when()
                .log().body()
                .post( apiPath )
                .then()
                .log().body()
                .statusCode( 201 )
                .extract().jsonPath().getString( "id" );

        // Editing entity
        entity.setId( entityId );
        entity.setName( nameEdited );
        entity.setShortName( codeEdited );
        given()
                .cookies( cookies )
                .body( entity )
                .contentType( ContentType.JSON )
                .when()
                .log().body()
                .put( apiPath )
                .then()
                .log().body()
                .statusCode( 200 )
                .body( "name", equalTo( entity.getName() ) )
                .body( "shortName", equalTo( entity.getShortName() ) )
        ;

        // deleting entity
        given()
                .cookies( cookies )
                .when()
                .log().body()
                .delete( apiPath + "/" + entityId )
                .then()
                .log().body()
                .statusCode( 200 )
        ;
    }

    @Test
    public void createNegativeTest() {
        GradeLevel entity = getBody();

        // creating entity
        String entityId = given()
                .cookies( cookies )
                .body( entity )
                .contentType( ContentType.JSON )
                .when()
                .log().body()
                .post( apiPath )
                .then()
                .log().body()
                .statusCode( 201 )
                .extract().jsonPath().getString( "id" );

        // entity creation negative test
        given()
                .cookies( cookies )
                .body( entity )
                .contentType( ContentType.JSON )
                .when()
                .log().body()
                .post( apiPath )
                .then()
                .log().body()
                .statusCode( 400 );

        // deleting entity
        given()
                .cookies( cookies )
                .when()
                .log().body()
                .delete( apiPath + "/" + entityId )
                .then()
                .log().body()
                .statusCode( 200 )
        ;
    }

    @Test
    public void deleteNegativeTest() {
        GradeLevel entity = getBody();

        // creating entity
        String entityId = given()
                .cookies( cookies )
                .body( entity )
                .contentType( ContentType.JSON )
                .when()
                .log().body()
                .post( apiPath )
                .then()
                .log().body()
                .statusCode( 201 )
                .extract().jsonPath().getString( "id" );

        // deleting entity
        given()
                .cookies( cookies )
                .when()
                .log().body()
                .delete( apiPath + "/" + entityId )
                .then()
                .log().body()
                .statusCode( 200 )
        ;

        // deleting entity again
        given()
                .cookies( cookies )
                .when()
                .log().body()
                .delete( apiPath + "/" + entityId )
                .then()
                .log().body()
                .statusCode( 404 )
        ;
    }
}
