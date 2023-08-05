package utility;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Utilities {
    public static int generateRandomInteger(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }
    public static String selectRandomString(Set<String> stringSet) {
        Random random = new Random();
        int randomIndex = random.nextInt(stringSet.size());
        return stringSet.toArray(new String[0])[randomIndex];
    }

    public static void main(String[] args) {
       /* Set<String> stringSet = new HashSet<>();
        stringSet.add("Breakfast");
        stringSet.add("Launch");
        stringSet.add("Dinner");
        String randomString = selectRandomString(stringSet);
        System.out.println("The random string is: " + randomString);
        String name = generateRandomName(50);
        System.out.println(name);**/
        System.out.println(generateRandomDate());
    }



    public static boolean generateRandomBoolean() {
        Random random = new Random();
        return random.nextBoolean();
    }




    public static String generateRandomDate() {
        Random random = new Random();
        int year = random.nextInt(2023 - 2018) + 2018;
        int month = random.nextInt(12) + 1;
        int day = random.nextInt(28);

        LocalDate randomDate = LocalDate.of(year, month, day);
        return randomDate.toString();
    }


    private static final Random random = new Random();

    public static String generateRandomDatecheckout() {
        LocalDate today = LocalDate.now();
        int daysToAdd = random.nextInt(365);
        LocalDate randomDate = today.plus(daysToAdd, ChronoUnit.DAYS);

        return randomDate.toString();
    }



    private static final String[] ADDITIONAL_NEEDS = {
            "breakfast wheelchair accessible",
            "hearing impaired",
            "visually impaired",
            "mobility impaired",
            "other"
    };

    public static String generateRandomAdditionalNeeds() {
        int randomIndex = new Random().nextInt(ADDITIONAL_NEEDS.length);
        return ADDITIONAL_NEEDS[randomIndex];
    }



    private static final String CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static String generateRandomName(int length) {
        String name = "";
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(CHARS.length());
            name += CHARS.charAt(index);
        }
        return name;
    }


    public static String getSingleJsonData(String jsonFilePath,String jsonField) throws IOException, ParseException {
        JSONParser jsonParser = new JSONParser();

        FileReader fileReader = new FileReader(jsonFilePath);
        Object obj = jsonParser.parse(fileReader);

        JSONObject jsonObject = (JSONObject) obj;
        return jsonObject.get(jsonField).toString();
    }



    public static String getExcelData(int RowNum, int ColNum, String SheetName) {
        XSSFWorkbook workBook;
        XSSFSheet sheet;
        String projectPath = System.getProperty("user.dir");
        String cellData = null;
        try {
            workBook = new XSSFWorkbook(projectPath + "/src/test/resources/data/data.xlsx");
            sheet = workBook.getSheet(SheetName);
            cellData = sheet.getRow(RowNum).getCell(ColNum).getStringCellValue();

        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
            e.printStackTrace();
        }
        return cellData;
    }

    // TODO: start html report
    public static void startHtmlReport(String reportDirName, String reportFileName) throws IOException {
        ProcessBuilder builder = new ProcessBuilder(
                "cmd.exe", "/c", "cd " + reportDirName + " && " + reportFileName);
        builder.redirectErrorStream(true);
        Process p = builder.start();
    }

}
