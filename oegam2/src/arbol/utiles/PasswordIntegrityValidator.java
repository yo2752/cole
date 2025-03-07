package arbol.utiles;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.opensymphony.xwork2.validator.ValidationException;
import com.opensymphony.xwork2.validator.validators.FieldValidatorSupport;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

/**
 * 
 * @author chadmichael
 *
 * This validator checks for password integrity by checking to see whether the password conforms
 * to a few simple well-known rules of password strength.
 * 
 */

public class PasswordIntegrityValidator extends FieldValidatorSupport {

	static Pattern digitPattern = Pattern.compile("[0-9]"); // \\d
	static Pattern letterPattern = Pattern.compile("[a-zA-Z]");
	static Pattern specialCharsDefaultPattern = Pattern.compile("!@#$");

	private static final ILoggerOegam log = LoggerOegam.getLogger(PasswordIntegrityValidator.class);

	public void validate(Object object) throws ValidationException {
		log.debug("object being validated = " + object.getClass().getName());
		String fieldName = getFieldName();
		String fieldValue = (String) getFieldValue(fieldName, object);

		//trim the password in case some spaces were added
		fieldValue = fieldValue.trim();

		//check security level and do the integrity checks

		//if 
		Matcher digitMatcher = digitPattern.matcher(fieldValue);
		Matcher letterMatcher = letterPattern.matcher(fieldValue);
		Matcher specialCharacterMatcher;

		if (getSpecialCharacters() != null){
			Pattern specialPattern = Pattern.compile("[" + getSpecialCharacters() + "]");
			specialCharacterMatcher = specialPattern.matcher(fieldValue);
		} else{
			specialCharacterMatcher = specialCharsDefaultPattern.matcher(fieldValue);
		}

		if (!digitMatcher.find() || !letterMatcher.find() || !specialCharacterMatcher.find()) {
			addFieldError(fieldName, object);
		}
	}

	/* JavaBeans property to recieve the parameter from the validator
	 * mapping. Special charaters are a set of characters, at least one of
	 * which the password must contain.
	 */
	private String specialCharacters;

	public String getSpecialCharacters() {
		return specialCharacters;
	}

	public void setSpecialCharacters(String securityLevel) {
		this.specialCharacters = securityLevel;
	}
}