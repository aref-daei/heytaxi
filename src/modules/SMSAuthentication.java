package modules;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.*;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class SMSAuthentication {

    // Instance variable
	private String path;

    // Constructor
	public SMSAuthentication(String path) {
		this.path = path;
	}

    // This method for generate code
    private String generateCode() {
        SecureRandom random = new SecureRandom();
        StringBuilder code = new StringBuilder();

        for (int i = 0; i < 5; i++) {
        	code.append(random.nextInt(10));
        }

        return code.toString();
    }

    // This method for send authentication sms and backup data
    public String sendAuthenticationSMS(String phone){
        Map<String, String> data = new LinkedHashMap<>();
        data.put("phone", phone);
        data.put("code", generateCode());
        data.put("exp", LocalDateTime.now().plusSeconds(60)
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

    // This method for lead data from backup file
    public List<String[]> loadData(){
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

    // This method for verify code
    public boolean verifyCode(String code, String phone) {
        List<String[]> allData = loadData();

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

}

