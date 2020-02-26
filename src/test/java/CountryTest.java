import io.restassured.http.ContentType;
import model.Country;
import org.testng.annotations.Test;
import utility.BaseTest;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class CountryTest extends BaseTest {
    @Test
    public void getBasePath() {
        given()
                .when()
                .log().body()
                .get()
                .then()
                .log().body()
                .statusCode( 200 )
        ;
    }

    @Test
    public void getCountries() {
        given()
                .cookies( cookies )
                .when()
                .log().body()
                .get( "/school-service/api/countries" )
                .then()
                .log().body()
                .statusCode( 200 )
        ;
    }


    @Test
    public void createCountry() {
        Country country = new Country();
        country.setName( name );
        country.setCode( code );

        // creating country
        String countryId = given()
                .cookies( cookies )
                .body( country )
                .contentType( ContentType.JSON )
                .when()
                .log().body()
                .post( "/school-service/api/countries" )
                .then()
                .log().body()
                .statusCode( 201 )
                .extract().jsonPath().getString( "id" );

        // deleting country
        given()
                .cookies( cookies )
                .when()
                .log().body()
                .delete( "/school-service/api/countries/" + countryId )
                .then()
                .log().body()
                .statusCode( 200 )
        ;
    }

    @Test
    public void editTest() {
        Country country = new Country();
        country.setName( name );
        country.setCode( code );

        // creating country
        String countryId = given()
                .cookies( cookies )
                .body( country )
                .contentType( ContentType.JSON )
                .when()
                .log().body()
                .post( "/school-service/api/countries" )
                .then()
                .log().body()
                .statusCode( 201 )
                .extract().jsonPath().getString( "id" );

        // Editing country
        country.setId( countryId );
        country.setName( nameEdited );
        country.setCode( codeEdited );
        given()
                .cookies( cookies )
                .body( country )
                .contentType( ContentType.JSON )
                .when()
                .log().body()
                .put( "/school-service/api/countries" )
                .then()
                .log().body()
                .statusCode( 200 )
                .body( "name", equalTo( country.getName() ) )
                .body( "code", equalTo( country.getCode() ) )
        ;

        // deleting country
        given()
                .cookies( cookies )
                .when()
                .log().body()
                .delete( "/school-service/api/countries/" + countryId )
                .then()
                .log().body()
                .statusCode( 200 )
        ;
    }

    @Test
    public void createCountryNegativeTest() {
        Country country = new Country();
        country.setName( name );
        country.setCode( code );

        // creating country
        String countryId = given()
                .cookies( cookies )
                .body( country )
                .contentType( ContentType.JSON )
                .when()
                .log().body()
                .post( "/school-service/api/countries" )
                .then()
                .log().body()
                .statusCode( 201 )
                .extract().jsonPath().getString( "id" );

        given()
                .cookies( cookies )
                .body( country )
                .contentType( ContentType.JSON )
                .when()
                .log().body()
                .post( "/school-service/api/countries" )
                .then()
                .log().body()
                .statusCode( 400 );

        // deleting country
        given()
                .cookies( cookies )
                .when()
                .log().body()
                .delete( "/school-service/api/countries/" + countryId )
                .then()
                .log().body()
                .statusCode( 200 )
        ;
    }

    @Test
    public void deleteCountryNegativeTest() {
        Country country = new Country();
        country.setName( name );
        country.setCode( code );

        // creating country
        String countryId = given()
                .cookies( cookies )
                .body( country )
                .contentType( ContentType.JSON )
                .when()
                .log().body()
                .post( "/school-service/api/countries" )
                .then()
                .log().body()
                .statusCode( 201 )
                .extract().jsonPath().getString( "id" );

        // deleting country
        given()
                .cookies( cookies )
                .when()
                .log().body()
                .delete( "/school-service/api/countries/" + countryId )
                .then()
                .log().body()
                .statusCode( 200 )
        ;

        // deleting country again
        given()
                .cookies( cookies )
                .when()
                .log().body()
                .delete( "/school-service/api/countries/" + countryId )
                .then()
                .log().body()
                .statusCode( 404 )
        ;
    }

}
