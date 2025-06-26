package modules;

//import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
//import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;

import java.io.*;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
//import java.time.temporal.ChronoUnit;
import java.util.*;

public class SMSAuthentication {


    public static String generateCode() {
        SecureRandom random = new SecureRandom();
        StringBuilder code = new StringBuilder();
        
        for (int i = 0; i < 5; i++) {
        	code.append(random.nextInt(10));
        }
        
        return code.toString();
    }


    public static String sendAuthenticationSMS(String phone, String path){
        Map<String, String> data = new LinkedHashMap<>();
        data.put("phone", phone);
        data.put("code", generateCode());
        data.put("exp", LocalDateTime.now().plusSeconds(10)
        		.format(DateTimeFormatter.ofPattern("yyyy/MM/dd-HH:mm:ss")));
        
        try (CSVWriter writer = new CSVWriter(new FileWriter(path))) {
        	writer.writeNext(new String[]{"Phone", "Code", "Expiration"});
        	
        	writer.writeNext(new String[]{
            	data.get("phone"),
            	data.get("code"),
            	data.get("exp")
            });
        	
        	System.out.println("Data successfully written to CSV file: " + path);
        } catch (IOException e) {
        	System.err.println("Error writing CSV file: " + e.getMessage());
        	e.printStackTrace()	;
        }
        
        ObjectMapper objectMapper = new ObjectMapper();
        String json = new String();
        
        try {
        	json = objectMapper.writeValueAsString(data);
        } catch (JsonProcessingException e) {
        	System.err.println("Error writing JSON: " + e.getMessage());
        }
        
        return json;
    }

    public static List<String[]> loadData(String path){
        List<String[]> records = new ArrayList<>();
        
        try (CSVReader csvReader = new CSVReader(new FileReader(path))) {
        	records = csvReader.readAll();
        	records.remove(0);
        } catch (IOException e) {
            System.err.println("Error reading CSV file: " + e.getMessage());
            e.printStackTrace();
        }
        
        return records;
    }


    public static boolean verifyCode(String code, String phone, String path) {
        List<String[]> allData = loadData(path);
    	
    	for (String[] data : allData) {
    		
    		if (data[0].equals(phone) && data[1].equals(code)) {
    			try {
        			LocalDateTime now = LocalDateTime.now();
    				LocalDateTime expirationTime =
    						LocalDateTime.parse(data[2], DateTimeFormatter.ofPattern("yyyy/MM/dd-HH:mm:ss"));
    	
    				if (expirationTime.isAfter(now)) {
    					return true;
    				} else {
    					return false;
    				}
    			} catch (Exception e) {
    				System.err.print("Error processing record %d: %s%n");
    			}
    		}
    	}
    	
        return false;
    }

    public static void main(String[] args) {
        System.out.println(sendAuthenticationSMS("09381358888", "test.csv"));
        Scanner sc = new Scanner(System.in);
        String code = sc.next();
        System.out.println(verifyCode(code,"09381358888","test.csv"));
        sc.close();
    }
}

