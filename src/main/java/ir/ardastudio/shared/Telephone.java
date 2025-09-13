package ir.ardastudio.shared;

public class Telephone {

	// This method for correct phone number
	public static String corrector(String phone) {
        if (!phone.matches("^(9\\d{9}|09\\d{9}|989\\d{9}|\\+989\\d{9})$")) {
            throw new IllegalArgumentException("Phone number is invalid");
        }

        if (phone.startsWith("9")) {
            phone = "+98" + phone;
        } else if (phone.startsWith("09")) {
            phone = "+98" + phone.substring(1);
        } else if (phone.startsWith("989")) {
            phone = "+98" + phone.substring(2);
        }

		return phone;
	}

}
