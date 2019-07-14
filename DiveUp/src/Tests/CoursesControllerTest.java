package Tests;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

import Controllers.CoursesController;

public class CoursesControllerTest extends BaseTest {

	static CoursesController junit;
	@BeforeClass
	public static void setUpBeforeClass()
	{
		junit = new CoursesController();
	}

	
	@Test
	public void testCheckNameValidity() {
		Map<Integer, String> res = junit.checkCourseAdd("Maor", "צלילה 1", "קורס צלילה 1", "20", "800", new Date(), new Date(), "צלילה 1");
		Map<Integer, String> res2 = junit.checkCourseAdd("", "צלילה 1", "קורס צלילה 1", "20", "800", new Date(), new Date(), "צלילה 1");
		assertEquals(0, res.size());
		assertNotEquals(0,res2.size());
	}
	
	@Test
	public void testRegisterNewCourse() {
		boolean isSuccess;
		
		isSuccess = junit.registerNewCourse(343348888, "423423");
		assertEquals(false, isSuccess);

		isSuccess = junit.registerNewCourse(343348888, "111111111");
		assertEquals(false, isSuccess);

		isSuccess = junit.registerNewCourse(1, notExistingDiverID);
		assertEquals(false, isSuccess);

		isSuccess = junit.registerNewCourse(1, "111111111");
		assertEquals(true, isSuccess);
	}

}
