package Tests;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

import Controllers.DiverController;


public class DiverControllerTest extends BaseTest {

	static DiverController junit;
	@BeforeClass
	public static void setUpBeforeClass()
	{
		junit = new DiverController();
	}
	
	@Test
	public void testCheckNameValidity() {
		String res = junit.checkNameValidity("1232");
		String res2 = junit.checkNameValidity("Maor");
		assertEquals("NO", res);
		assertEquals("VALID", res2);
	}

	@Test
	public void testCheckLastNameValidity() {
		String res = junit.checkLastNameValidity("1232");
		String res2 = junit.checkLastNameValidity("konfino");
		assertEquals("NO", res);
		assertEquals("VALID", res2);
	}

	@Test
	public void testCheckEmailValidity() {
		String res = junit.checkEmailValidity("1asd232");
		String res2 = junit.checkEmailValidity("konfino@gmail.com");
		assertEquals("NO", res);
		assertEquals("VALID", res2);
	}

	@Test
	public void testCheckIDValidity() {
		String res = junit.checkIDValidity("1232");
		String res2 = junit.checkIDValidity("123123123");
		assertEquals("NO", res);
		assertEquals("VALID", res2);
	}

	@Test
	public void testCheckPhoneValidity() {
		String res = junit.checkPhoneValidity("1232");
		String res2 = junit.checkPhoneValidity("0548170292");
		assertEquals("NO", res);
		assertEquals("VALID", res2);
	}
	
	
	@Test
	public void testRegistration() {
		Map<Integer, String> map;
		int violationsAmount;
		map = junit.checkFullRegistrationForm(
				notExistingDiverID,
				firstName,
				lastName,
				notExistinglicenseID,
				validMail, 
				validPhone
		);
		violationsAmount = map.size();
		assertNotEquals(0, violationsAmount);

		map = junit.checkFullRegistrationForm(
				existingDiverID,
				firstName,
				lastName,
				notExistinglicenseID,
				validMail,
				validPhone
		);
		violationsAmount = map.size();
		assertNotEquals(0, violationsAmount);

		map = junit.checkFullRegistrationForm(
				existingDiverID,
				firstName,
				lastName,
				existinglicenseID,
				notValidMail1,
				notValidPhone1
		);
		violationsAmount = map.size();
		assertNotEquals(0, violationsAmount);

		map = junit.checkFullRegistrationForm(
				existingDiverID,
				firstName,
				lastName,
				existinglicenseID,
				validMail,
				validPhone
		);
		violationsAmount = map.size();
		assertEquals(0, violationsAmount);
	}

}
