package Tests;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import Controllers.DiverController;
public class DiverControllerTest {

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

}
