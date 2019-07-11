package Tests;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

import Controllers.CoursesController;
import Controllers.DiverController;
public class CoursesControllerTest {

	static CoursesController junit;
	@BeforeClass
	public static void setUpBeforeClass()
	{
		junit = new CoursesController();
	}
	@Test
	public void testCheckNameValidity() {
		Map<Integer, String> res = junit.checkCourseAdd("Maor", "צלילה 1", "יוסי", "20", "800", new Date(), new Date(), "קורס צלילה 1");
		Map<Integer, String> res2 = junit.checkCourseAdd("", "צלילה 1", "יוסי", "20", "800", new Date(), new Date(), "קורס צלילה 1");
		assertEquals(0, res.size());
		assertNotEquals(0,res2.size());
	}


}
