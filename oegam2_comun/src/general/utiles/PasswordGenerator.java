package general.utiles;

import java.util.Random;


public final class PasswordGenerator {

	private static final Random genRa = new Random();

	private static final String validChars = "ABCEFGHJKMNPRSTUVWXYZabcdefghkmnpqrstuvwxyz23456789";
	private static final String validExtra = ".*:";

	/**
	 * Generate a password string with 8 characters and 1 special character.
	 *
	 * Does not generate any "difficult" chars whic may be misunderstood with others
	 * when printed in paper or screen, which are: '0,O,o,D,Q', '1,I,i,j'.
	 */
    public static String generatePassword() {
		return generatePassword(8,1);
	}

	/**
	 * Generate a password string of variable length and number of special characters.
	 *
	 * Does not generate any "difficult" chars whic may be misunderstood with others
	 * when printed in paper or screen, which are: '0,O,o,D,Q', '1,I,i,j'.
	 */
	public static String generatePassword(int size, int special) {

		int				position = 0;
		StringBuffer 	password = new StringBuffer(8);

		// creating password
		for (int i=0;i<size;i++) {
			password.append(validChars.charAt(genRa.nextInt(validChars.length())));
		}
		// placing special characters
		for (int i=0;i<special;i++) {
			position = 0;
			while (position < 1 || position > size - 2 ) {
			position = genRa.nextInt(size);
		    }
			password.setCharAt(position,validExtra.charAt(genRa.nextInt(validExtra.length())));
		}
		return password.toString();
	}

}
