import io.restassured.http.ContentType;
import model.Location;
import model.School;
import org.testng.annotations.Test;
import utility.BaseTest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class LocationTest extends BaseTest {
    private String apiPath = "/school-service/api/location";

    @Test
    public void createTest() {
        Location model = getBody();

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

    private Location getBody() {
        Location model = new Location();
        model.setName( name );
        model.setShortName( code );
        model.setCapacity( "40" );
        model.setType( "CLASS" );

        School school = new School();
        school.setId( schoolId );
        model.setSchool( school );

        return model;
    }

    @Test
    public void editTest() {
        Location entity = getBody();

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
        Location entity = getBody();

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
        Location entity = getBody();

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

    @Test
    public void locationCapacityTest() {
        Location model = getBody();
        model.setCapacity( "this is capacity" );

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
                .statusCode( 400 )
                .extract().jsonPath().getString( "id" );

        if(entityId != null) {
        // deleting entity if for some reason it was created
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
    }
}
