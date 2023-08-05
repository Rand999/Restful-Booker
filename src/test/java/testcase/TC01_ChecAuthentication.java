package testcase;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import model.M1_Auth;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.Test;
import utility.Utilities;

import java.io.IOException;

import static io.restassured.RestAssured.*;

public class TC01_ChecAuthentication extends Testbase{

    Response response;
    static String userName;
    static String password;


    /*public static void main(String[] args) throws IOException, ParseException {
//        userName = Utilities.getSingleJsonData(System.getProperty("user.dir")+"/src/test/resources/data/data.json","username");
//        password = Utilities.getSingleJsonData(System.getProperty("user.dir")+"/src/test/resources/data/data.json","password");

        userName=Utilities.getExcelData(0,0,"test");
        password=Utilities.getExcelData(0,1,"test");
        System.out.println(userName+ "   "+password);
    }*/



    // 1- valid senario
    @Description("")
    @Story("Login")
    @Epic("Login")
    @Test(priority = 1, description = "Check Auth with valid input")
    public  void checkAuthWithValidData(){
        // 1- given (parameters - authentication - header - body)
        // 2- when  (method include path)
        // 3- then   assert result
        response=given().log().all()
//                .param("","").auth().basic("","")
//                .header("authentication","bearer sasljdasjkdladfksdkflsd")
                .header("Content-Type","application/json").body("{\n" +
                "    \"username\" : \"admin\",\n" +
                "    \"password\" : \"password123\"\n" +
                "}").when().post("/auth").then().log().all()
//                .body("booking.fisrtname",equalTo("ahmed"))
                .statusCode(200).extract().response();
    }

    // 2- invalid method
    @Test(priority = 2)
    public void checkAuthWithInvalidMethod(){
        given().log().all().header("Content-Type","application/json").body("{\n" +
                "    \"username\" : \"admin\",\n" +
                "    \"password\" : \"password123\"\n" +
                "}").when().put("/auth").then().log().all().statusCode(405);
    }
    // 3- invalid request path
    @Test(priority = 3)
    public void checkAuthWithInvalidPath(){
        given().log().all().header("Content-Type","application/json").body("{\n" +
                "    \"username\" : \"admin\",\n" +
                "    \"password\" : \"password123\"\n" +
                "}").when().post("/authh").then().log().all().statusCode(404);
    }
    // 4- invalid query parameter
    @Test(priority = 4)
    public void checkAuthWithInvalidQueryParametr(){
        given().log().all().queryParam("name","").header("Content-Type","application/json").body("{\n" +
                "    \"username\" : \"admin\",\n" +
                "    \"password\" : \"password123\"\n" +
                "}").when().post("/auth").then().log().all().statusCode(400);
    }
    // 5- invalid authentication
    @Test(priority = 5)
    public void checkAuthRequestWithInvalidAuth(){
        bookingBody.put("firstname","ahmed");
        bookingDate.put("checkin","2018-01-01");
        bookingDate.put("checkout","2019-01-01");
        data.add(bookingDate);
        bookingBody.put("bookingdates",bookingDate);
        bookingBody.put("data",data);
        System.out.println(bookingBody);
//        given().log().all().auth().basic("username","password").header("Content-Type","application/json")
//                .body(jsonObject).when().post("/auth").then().log().all().statusCode(401);
    }
    // 6- invalid header
    @Test(priority = 6)
    public void checkAuthRequestWithInvalidHeader() throws JsonProcessingException {
        given().log().all().header("Content-Type","application/xml")
                .body(mapper.writeValueAsString(new M1_Auth().setUsername(userName).setPassword(password)))
                .when().post("/auth").then().log().all().statusCode(400);
    }
    @Test(priority = 7, description = "Check Token not null")
    public void checkTokenNotNull(){
        //        Assert.assertFalse(response.jsonPath().getString("token").isEmpty());
        Assert.assertEquals(response.jsonPath().getString("token").isEmpty(),false);
    }

}
