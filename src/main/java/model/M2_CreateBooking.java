package model;

public class M2_CreateBooking {
    public static String createBookingBody(String firstname, String lastname, int totalprice, boolean depositpaid ,String checkin ,String checkout , String additionalneeds){
        return "{\n" +
                "    \"firstname\" : \""+firstname+"\",\n" +
                "    \"lastname\" : \""+lastname+"\",\n" +
                "    \"totalprice\" : "+totalprice+",\n" +
                "    \"depositpaid\" : "+depositpaid+",\n" +
                "    \"bookingdates\" : {\n" +
                "        \"checkin\" : \""+checkin+"\",\n" +
                "        \"checkout\" : \""+checkout+"\"\n" +
                "    },\n" +
                "    \"additionalneeds\" : \""+additionalneeds+"\"\n" +
                "}";
    }
}
