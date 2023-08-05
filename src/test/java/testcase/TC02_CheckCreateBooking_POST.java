package testcase;

import io.restassured.response.Response;
import io.restassured.specification.Argument;
import model.M2_CreateBooking;
import org.testng.Assert;
import org.testng.annotations.Test;
import utility.Utilities;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.number.OrderingComparison.lessThanOrEqualTo;
import static utility.Utilities.selectRandomString;

public class TC02_CheckCreateBooking_POST extends Testbase {
    // 1- given (param - auth - header - body)
    // 2- when (method including path)
    // 3- then: assert response

    String firstName = faker.name().firstName();
    String lastName = faker.name().lastName();
    int totalPrice = utility.Utilities.generateRandomInteger(1, 1000);
    boolean depositpaid = utility.Utilities.generateRandomBoolean();
    String checkin = utility.Utilities.generateRandomDate();

    String checkout = utility.Utilities.generateRandomDatecheckout();

    String additionalneeds = utility.Utilities.generateRandomAdditionalNeeds();
    Response response;

    @Test(priority = 1, description = "Check Create Booking Request with Valid inputs")
    public void checkCreateBookingWithValidInput_P() {
        response = given().log().all()
//                .param("","").auth().basic("","")
                .header("Content-Type", "application/json")
                .body(M2_CreateBooking.createBookingBody(firstName, lastName, totalPrice,depositpaid,checkin ,checkout ,additionalneeds)).when().post("/booking").then().log().all().statusCode(200).extract().response();
    }


    @Test(priority = 2, description = "Check Booking ID not Null")
    public void checkBookingIDNotNull_P() {
        Assert.assertFalse(Integer.toString(response.jsonPath().getInt("bookingid")).isEmpty());
    }


    @Test(priority = 3, description = "Check First Name not Empty")
    public void checkFirstNameNotEmpty_P() {
        Assert.assertFalse(response.jsonPath().getString("booking.firstname").isEmpty());
    }


    @Test(priority = 4, description = "Check First name value")
    public void checkFirstNameAsExpected_P() {
        Assert.assertEquals(response.jsonPath().getString("booking.firstname"), firstName);
    }


    ////////// last name
    @Test(priority = 5, description = "Check Lirst Name not Empty")
    public void checkLirstNameNotEmpty_P() {
        Assert.assertFalse(response.jsonPath().getString("booking.lastname").isEmpty());
    }


    @Test(priority = 6, description = "Check last name is Brown")
    public void checkLastNameAsExpected_P() {
        Assert.assertEquals(response.jsonPath().getString("booking.lastname"), lastName);
    }


    /////////////////////////////////////////


    ///////////////// Total price

    @Test(priority = 7, description = "Check First Name not Empty")
    public void checkTotalpriceNotEmpty_P() {
        Assert.assertFalse(response.jsonPath().getString("booking.totalprice").isEmpty());
    }


    @Test(priority = 8, description = "Check totalprice is 844")
    public void checkTotalpriceAsExpected_P() {
        Assert.assertEquals(response.jsonPath().getInt("booking.totalprice"), totalPrice);
    }


    //////////////////////////////
    ///////////  depositpaid
    @Test(priority = 9, description = "Check depositpaid Name not Empty")
    public void checkDepositpaidNotEmpty_P() {
        Assert.assertFalse(response.jsonPath().getString("booking.depositpaid").isEmpty());
    }


    @Test(priority = 10, description = "Check depositpaid is true")
    public void checkDepositpaidAsExpected_P() {
        Assert.assertEquals(response.jsonPath().getBoolean("booking.depositpaid"), depositpaid);
    }

    //////////////////////////////
    ////////////////// checkin

    @Test(priority = 11, description = "Check checkin Name not Empty")
    public void checkCheckinNotEmpty_P() {
        Assert.assertFalse(response.jsonPath().getString("booking.bookingdates.checkin").isEmpty());
    }

    @Test(priority = 12, description = "Check checkin is 2018-09-05")
    public void checkCheCkinAsExpected_P() {
        Assert.assertEquals(response.jsonPath().getString("booking.bookingdates.checkin"),checkin);
    }
////////////////////


    //////////// checkout

    @Test(priority = 13, description = "Check checkout Name not Empty")
    public void checkCheckoutNotEmpty_P() {
        Assert.assertFalse(response.jsonPath().getString("booking.bookingdates.checkout").isEmpty());
    }

    @Test(priority = 14, description = "Check checkout is 2023-12-13")
    public void checkCheckoutExpected_P() {
        Assert.assertEquals(response.jsonPath().getString("booking.bookingdates.checkout"), checkout);
    }


    ///////////////////////
    ////////// additionalneeds
    @Test(priority = 15, description = "Check additionalneeds Name not Empty")
    public void checkAdditionalneedsNotEmpty_P() {
        Assert.assertFalse(response.jsonPath().getString("booking.additionalneeds").isEmpty());
    }

    @Test(priority = 16, description = "Check additionalneeds is mobility impaired")
    public void checkAdditionalneedsExpected_P() {
        Assert.assertEquals(response.jsonPath().getString("booking.additionalneeds"), additionalneeds);
    }



////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////// INVALID essential cases
    // invalid method
/*@Test(priority = 17 , description = "checkCreateBookingWithInvalidMethod")
public void checkCreateBookingWithInvalidMethod(){
    given().log().all().header("Content-Type","application/json")
            .body(M2_CreateBooking.createBookingBody(firstName, lastName, totalPrice,depositpaid,checkin ,checkout ,additionalneeds)).when()
            .delete("/booking").then().log().all().statusCode(405);
}
    // 3- invalid request path
    @Test(priority = 18 ,description = "")
    public void checkCreateBookingWithInvalidPath(){
        given().log().all().header("Content-Type","application/json")
                .body(M2_CreateBooking.createBookingBody(firstName, lastName, totalPrice,depositpaid,checkin ,checkout ,additionalneeds))
                .when().post("/BBokking").then().log().all().statusCode(404);
    }
    // 4- invalid query parameter
    @Test(priority = 19,description = "")
    public void checkCreateBookingInvalidQueryParametr(){
        given().log().all().queryParam("name","test").header("Content-Type","application/json") .body(M2_CreateBooking.createBookingBody(firstName, lastName, totalPrice,depositpaid,checkin ,checkout ,additionalneeds))
                .when().post("/booking").then().log().all().statusCode(400);
    }
    // 5- invalid authentication
    @Test(priority = 20)
    public void checkCreateBookingWithInvalidAuth(){
        given().log().all().auth().basic("username","password").header("Content-Type","application/json").body(M2_CreateBooking.createBookingBody(firstName, lastName, totalPrice,depositpaid,checkin ,checkout ,additionalneeds))
                .when().post("/booking").then().log().all().statusCode(401);
    }
    // 6- invalid header
    @Test(priority = 21)
    public void checkCreateBookingWithInvalidHeader(){
        given().log().all().header("Content-Type","application/xml") .body(M2_CreateBooking.createBookingBody(firstName, lastName, totalPrice,depositpaid,checkin ,checkout ,additionalneeds))
                .when().post("/booking").then().log().all().statusCode(400);
    }

*/
 /////////////////////// Test cases









    //1-  first name test not more than 50
    //2-  dont have special char char or speces
    @Test (priority = 20, description = "Check additionalneeds is mobility impaired")
    public void testFirstNameMustOnlyContainLettersAndSpaces() {
        // Specify a value for the first name field that contains a special character.
        String firstNamelenght = "John&Doe";

        // Make a request to the API endpoint that creates a new user.
        given()
                .contentType("application/json")
                .body("{\"firstname\" : \"John&$^^Doe\"\n" +
                        "}")
                .when()
                .post("/booking")
                .then()
                .statusCode(400)
                .body("message", containsString("The first name field can only contain letters and spaces."));
    }

    /*@Test(priority = 17, description = "Check additionalneeds is mobility impaired")
    public void testFirstNameMoreThan50() {

        given().contentType("application/json")
                .body(M2_CreateBooking.createBookingBody(firstName, lastName, totalPrice,depositpaid,checkin ,checkout ,additionalneeds))
                .when()
                .post("/booking")
                .then()
                .statusCode(400)

                .body("firstName", equalTo("firsName"));
    }


   @Test(priority = 1, description = "")
    public void checkFirstNamemorethan50() {
       firstName = Utilities.generateRandomName(50);
       lastName=Utilities.generateRandomName(100);
        given().log().all()
                .header("Content-Type", "application/json")
                .body(M2_CreateBooking.createBookingBody(firstName, lastName, totalPrice)).when().post("/booking")
                .then().log().all().statusCode(400).body("message", containsString("The first name must be less than or equal to 50 characters long.")).body("firstName.length()", lessThanOrEqualTo(50));
    }

*/

}