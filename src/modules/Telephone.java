package modules;

public class Telephone {

	// This method for correct phone number
	public static String corrector(String phone) {
		if (phone.charAt(0) == '0' && phone.charAt(1) == '9') {

			if (phone.length() != 11) {
				throw new IllegalArgumentException("Phone number is invalid");
			}

			try {
				phone = String.format("+98%d", Long.parseLong(phone));
			} catch (Exception e) {
				throw new IllegalArgumentException("Phone number is invalid");
			}

		} else if (phone.charAt(0) == '9' && phone.charAt(1) == '8') {

			if (phone.length() != 12) {
				throw new IllegalArgumentException("Phone number is invalid");
			}

			try {
				phone = String.format("+%d", Long.parseLong(phone));
			} catch (Exception e) {
				throw new IllegalArgumentException("Phone number is invalid");
			}

		} else if (phone.charAt(0) == '+' && phone.charAt(1) == '9' && phone.charAt(2) == '8') {

			if (phone.length() != 13) {
				throw new IllegalArgumentException("Phone number is invalid");
			}

			try {
				phone = String.format("+%d", Long.parseLong(phone.substring(0, 13)));
			} catch (Exception e) {
				throw new IllegalArgumentException("Phone number is invalid");
			}

		} else {
			throw new IllegalArgumentException("Phone number is invalid");
		}

		return phone;
	}

}
