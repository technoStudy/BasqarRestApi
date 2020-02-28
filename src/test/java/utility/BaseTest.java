package utility;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import org.testng.annotations.BeforeClass;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static io.restassured.RestAssured.given;

public class BaseTest {
    protected String name;
    protected Cookies cookies;
    protected String nameEdited;
    protected String code;
    protected String codeEdited;
    protected String schoolId;

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

        // TODO:
        //List<String> schoolList = given(); //extract school ids from /school-service/api/schools/search
        //pick a random id from schoolList and assign to schoolId
        // use this schoolId in all test where needed
        // research json ignore unknown fields

        List<String> listOfSchoolIds = given()
                .body("{}")
                .contentType(ContentType.JSON)
                .cookies(cookies)
                .when()
                .post("/school-service/api/schools/search")
                .then()
                .statusCode(200)
                .extract().jsonPath().getList("id", String.class);

        int randomIndex = new Random().nextInt(listOfSchoolIds.size());
        schoolId = listOfSchoolIds.get(randomIndex);

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
