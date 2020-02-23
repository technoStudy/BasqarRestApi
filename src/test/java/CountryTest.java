import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import model.Country;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class CountryTest {
    private String name;
    private Cookies cookies;
    private String nameEdited;
    private String code;
    private String codeEdited;

    @BeforeClass
    public void authenticate() {
        name = getAlphaNumericString( 30 );
        nameEdited = getAlphaNumericString( 30 );
        code = getAlphaNumericString( 10 );
        codeEdited = getAlphaNumericString( 10 );
        RestAssured.baseURI = "https://test-basqar.mersys.io";
        Map<String, String> credentials = new HashMap<>();
        credentials.put( "username", "nigeria_tenant_admin" );
        credentials.put( "password", "TnvLOl54WxR75vylop2A" );

        cookies = given()
                .body( credentials )
                .contentType( ContentType.JSON )
                .when()
                .log().body()
                .post( "/auth/login" )
                .then()
                .log().body()
                .statusCode( 200 )
                .extract().response().getDetailedCookies();
    }

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

    private String getAlphaNumericString(int n) {

        // chose a Character random from this String
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder( n );

        for(int i = 0; i < n; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                    = (int) (AlphaNumericString.length()
                    * Math.random());

            // add Character one by one in end of sb
            sb.append( AlphaNumericString
                    .charAt( index ) );
        }

        return sb.toString();
    }
}
