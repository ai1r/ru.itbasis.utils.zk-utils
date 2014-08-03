package ru.itbasis.utils.zkoss.entity;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.time.Month;
import java.util.Calendar;

public class BetweenCalendarTest {
	@DataProvider(name = "dataIsValid")
	public static Object[][] dataIsValid() {
		final Calendar.Builder builder = new Calendar.Builder();

		final int januaryIndex = Month.JANUARY.ordinal();
		final int februaryIndex = Month.FEBRUARY.ordinal();

		final Object[] item_00 = {builder.setDate(2014, januaryIndex, 1).build(), builder.setDate(2014, januaryIndex, 1).build(), true};

		final Object[] item_01 = {builder.setDate(2014, januaryIndex, 1).build(), builder.setDate(2013, januaryIndex, 1).build(), false};
		final Object[] item_02 = {builder.setDate(2012, januaryIndex, 1).build(), builder.setDate(2013, januaryIndex, 1).build(), true};
		final Object[] item_03 = {builder.setDate(2014, februaryIndex, 1).build(), builder.setDate(2014, januaryIndex, 1).build(), false};
		final Object[] item_04 = {builder.setDate(2014, januaryIndex, 1).build(), builder.setDate(2014, februaryIndex, 1).build(), true};
		final Object[] item_05 = {builder.setDate(2014, januaryIndex, 2).build(), builder.setDate(2014, januaryIndex, 1).build(), false};
		final Object[] item_06 = {builder.setDate(2014, januaryIndex, 1).build(), builder.setDate(2014, januaryIndex, 2).build(), true};

		return new Object[][]{item_00, item_01, item_02, item_03, item_04, item_05, item_06};
	}

	@Test
	public void testInit() throws Exception {
		final BetweenCalendar calendar = new BetweenCalendar();
		Assert.assertTrue(calendar.isValid());
	}

	@Test(dataProvider = "dataIsValid", dependsOnMethods = {"testInit"})
	public void testIsValid(final Calendar start, final Calendar end, final boolean exceptedValid) throws Exception {
		final BetweenCalendar calendar = new BetweenCalendar(start, end);
		Assert.assertEquals(calendar.isValid(), exceptedValid);

		final BetweenCalendar calendar1 = new BetweenCalendar();
		calendar1.setStart(start).setEnd(end);
		Assert.assertEquals(calendar1.isValid(), exceptedValid);
	}
}