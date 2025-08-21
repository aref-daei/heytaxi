package ir.ardastudio.shared;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class SMSAuthentication {
    private static final Logger logger = LoggerFactory.getLogger(SMSAuthentication.class);

	private final String path;

	public SMSAuthentication(String path) {
		this.path = path;
	}

    private String generateCode() {
        SecureRandom random = new SecureRandom();
        StringBuilder code = new StringBuilder();

        for (int i = 0; i < 5; i++) {
        	code.append(random.nextInt(10));
        }
        return code.toString();
    }

    public String sendAuthenticationSMS(String phone) {
        Map<String, String> data = new LinkedHashMap<>();
        data.put("phone", phone);
        data.put("code", generateCode());
        data.put("exp", LocalDateTime.now().plusSeconds(60)
        		.format(DateTimeFormatter.ofPattern("yyyy/MM/dd-HH:mm:ss")));

        File csvFile = new File(path);
        boolean fileExists = csvFile.exists() && csvFile.length() > 0;

        try (CSVWriter writer = new CSVWriter(new FileWriter(path, true))) {
            if (!fileExists) {
                writer.writeNext(new String[]{"Phone", "Code", "Expiration"});
            }

        	writer.writeNext(new String[]{
            	data.get("phone"),
            	data.get("code"),
            	data.get("exp")
            });
        } catch (IOException e) {
            logger.error("Error writing CSV file to path: ", e);
        }

        ObjectMapper objectMapper = new ObjectMapper();
        try {
        	return objectMapper.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            logger.error("Error converting data to JSON: ", e);
            return "{}";
        }
    }

    public List<String[]> loadData() {
        List<String[]> records = new ArrayList<>();

        try (CSVReader csvReader = new CSVReader(new FileReader(path))) {
        	records = csvReader.readAll();
            if (!records.isEmpty()) {
                records.remove(0);
            }
        } catch (IOException | CsvException e) {
            logger.error("Error reading CSV file from path: ", e);
        }
        return records;
    }

    public boolean verifyCode(String code, String phone) {
        List<String[]> allData = loadData();

    	for (String[] data : allData) {
    		if (data[0].equals(phone) && data[1].equals(code)) {
    			try {
        			LocalDateTime now = LocalDateTime.now();
    				LocalDateTime expirationTime =
    						LocalDateTime.parse(data[2], DateTimeFormatter.ofPattern("yyyy/MM/dd-HH:mm:ss"));
                    return expirationTime.isAfter(now);
    			} catch (Exception e) {
                    logger.error("Error processing record for phone={}: ", data[0], e);
    			}
    		}
    	}
        return false;
    }
}

