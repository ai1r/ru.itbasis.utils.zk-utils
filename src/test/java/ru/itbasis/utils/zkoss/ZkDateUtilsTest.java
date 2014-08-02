package ru.itbasis.utils.zkoss;

import org.apache.commons.lang3.StringUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.time.Month;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

@Test(groups = {"utils", "dateUtils"})
public class ZkDateUtilsTest {

	/**
	 * 0 - Calendar
	 * 1 - Date
	 * 2 - Short Date (text)
	 * 3 - Locale
	 */
	private static Object[][] _dataDateTime() {

		final int januaryIndex = Month.JANUARY.ordinal();
		final Calendar.Builder builder = new Calendar.Builder().setDate(2014, januaryIndex, 1);

		final Object[] item_00 = {builder.setTimeOfDay(1, 1, 1).build(), new Date(2014, januaryIndex, 1, 1, 1, 1), "1/1/14 1:01 AM", Locale.ENGLISH};
		final Object[] item_01 = {builder.setTimeOfDay(1, 1, 50).build(), new Date(2014, januaryIndex, 1, 1, 1, 50), "1/1/14 1:01 AM", Locale.ENGLISH};
		final Object[] item_02 = {builder.setTimeOfDay(1, 1, 60).build(), new Date(2014, januaryIndex, 1, 1, 1, 60), "1/1/14 1:02 AM", Locale.ENGLISH};
		final Object[] item_03 = {builder.setTimeOfDay(1, -1, 0).build(), new Date(2014, januaryIndex, 1, 1, -1, 0), "1/1/14 12:59 AM", Locale.ENGLISH};

		return new Object[][]{item_00, item_01, item_02, item_03};
	}

	/**
	 * 0 - Calendar
	 * 1 - Date
	 * 2 - Month
	 * 3 - Short Date (text)
	 * 4 - last day
	 */
	private static Object[][] _dataOnlyDate() {
		final Calendar.Builder builder = new Calendar.Builder();

		final Month january = Month.JANUARY;
		final Month february = Month.FEBRUARY;
		final Month march = Month.MARCH;
		final Month april = Month.APRIL;

		final int januaryIndex = january.ordinal();
		final int februaryIndex = february.ordinal();
		final int marchIndex = march.ordinal();
		final int aprilIndex = april.ordinal();

		final Object[] item_00 = {builder.setDate(2014, januaryIndex, 23).build(), new Date(2014, januaryIndex, 23), january, "1/23/14", 31};
		final Object[] item_01 = {builder.setDate(2014, januaryIndex, 32).build(), new Date(2014, januaryIndex, 32), february, "2/1/14", 28};
		final Object[] item_02 = {builder.setDate(2012, januaryIndex, 32).build(), new Date(2012, januaryIndex, 32), february, "2/1/12", 29};
		final Object[] item_03 = {builder.setDate(2014, februaryIndex, 28).build(), new Date(2014, februaryIndex, 28), february, "2/28/14", 28};
		final Object[] item_04 = {builder.setDate(2014, februaryIndex, 2).build(), new Date(2014, februaryIndex, 2), february, "2/2/14", 28};
		final Object[] item_05 = {builder.setDate(2012, februaryIndex, 2).build(), new Date(2012, februaryIndex, 2), february, "2/2/12", 29};
		final Object[] item_06 = {builder.setDate(2012, februaryIndex, 28).build(), new Date(2012, februaryIndex, 28), february, "2/28/12", 29};
		final Object[] item_07 = {builder.setDate(2014, februaryIndex, 29).build(), new Date(2014, februaryIndex, 29), march, "3/1/14", 31};
		final Object[] item_08 = {builder.setDate(2012, februaryIndex, 29).build(), new Date(2012, februaryIndex, 29), february, "2/29/12", 29};
		final Object[] item_09 = {builder.setDate(2014, aprilIndex, 0).build(), new Date(2014, aprilIndex, 0), march, "3/31/14", 31};
		final Object[] item_10 = {builder.setDate(2014, marchIndex, 0).build(), new Date(2014, marchIndex, 0), february, "2/28/14", 28};
		final Object[] item_11 = {builder.setDate(2012, marchIndex, 0).build(), new Date(2012, marchIndex, 0), february, "2/29/12", 29};

		return new Object[][]{item_00, item_01, item_02, item_03, item_04, item_05, item_06, item_07, item_08, item_09, item_10, item_11};
	}

	@DataProvider(name = "dataFormatAsShortDateFromCalendar")
	public static Object[][] dataFormatAsShortDateFromCalendar() {
		final Object[][] objects = _dataOnlyDate();
		final Object[][] result = new Object[objects.length][2];
		for (int i = 0; i < objects.length; i++) {
			result[i][0] = objects[i][0];
			result[i][1] = objects[i][3];
		}
		return result;
	}

	@DataProvider(name = "dataFormatAsShortDateFromDate")
	public static Object[][] dataFormatAsShortDateFromDate() {
		final Object[][] objects = _dataOnlyDate();
		final Object[][] result = new Object[objects.length][2];
		for (int i = 0; i < objects.length; i++) {
			result[i][0] = objects[i][1];
			result[i][1] = objects[i][3];
		}
		return result;
	}

	@DataProvider(name = "dataFormatAsShortDateTimeFromCalendar")
	public static Object[][] dataFormatAsShortDateTimeFromCalendar() {
		final Object[][] objects = _dataDateTime();
		final Object[][] result = new Object[objects.length][3];
		for (int i = 0; i < objects.length; i++) {
			result[i][0] = objects[i][0];
			result[i][1] = objects[i][2];
			result[i][2] = objects[i][3];
		}
		return result;
	}

	@DataProvider(name = "dataFormatAsShortDateTimeFromDate")
	public static Object[][] dataFormatAsShortDateTimeFromDate() {
		final Object[][] objects = _dataDateTime();
		final Object[][] result = new Object[objects.length][3];
		for (int i = 0; i < objects.length; i++) {
			result[i][0] = objects[i][1];
			result[i][1] = objects[i][2];
			result[i][2] = objects[i][3];
		}
		return result;
	}

	@DataProvider(name = "dataGetFirstDay")
	public static Object[][] dataGetFirstDay() {
		final Object[][] objects = _dataOnlyDate();
		final Object[][] result = new Object[objects.length][2];
		for (int i = 0; i < objects.length; i++) {
			result[i][0] = objects[i][0];
			result[i][1] = objects[i][2];
		}
		return result;
	}

	@DataProvider(name = "dataGetLastDay")
	public static Object[][] dataGetLastDay() {
		final Object[][] objects = _dataOnlyDate();
		final Object[][] result = new Object[objects.length][3];
		for (int i = 0; i < objects.length; i++) {
			result[i][0] = objects[i][0];
			result[i][1] = objects[i][2];
			result[i][2] = objects[i][4];
		}
		return result;
	}

	@BeforeClass
	public void setUpClass() throws Exception {
		Locale.setDefault(Locale.ENGLISH);
	}

	@Test(dataProvider = "dataFormatAsShortDateFromCalendar", dependsOnMethods = {"testFormatAsShortDateFromNull"})
	public void testFormatAsShortDate(final Calendar calendar, final String excepted) throws Exception {
		final String shortDate = ZkDateUtils.formatAsShortDate(calendar);
		Assert.assertEquals(shortDate, excepted);
	}

	@Test(dataProvider = "dataFormatAsShortDateFromDate", dependsOnMethods = {"testFormatAsShortDateFromNull"})
	public void testFormatAsShortDate(final Date date, final String excepted) throws Exception {
		final String shortDate = ZkDateUtils.formatAsShortDate(date);
		Assert.assertEquals(shortDate, excepted);
	}

	@Test
	public void testFormatAsShortDateFromNull() throws Exception {
		Assert.assertEquals(ZkDateUtils.formatAsShortDate((Calendar) null), StringUtils.EMPTY);
		Assert.assertEquals(ZkDateUtils.formatAsShortDate((Date) null), StringUtils.EMPTY);
	}

	@Test(dataProvider = "dataFormatAsShortDateTimeFromCalendar", dependsOnMethods = {"testFormatAsShortDateTimeFromNull"})
	public void testFormatAsShortDateTime(final Calendar calendar, final String excepted, final Locale locale) throws Exception {
		Locale.setDefault(locale);
		final String shortDateTime = ZkDateUtils.formatAsShortDateTime(calendar);
		Assert.assertEquals(shortDateTime, excepted);
	}

	@Test(dataProvider = "dataFormatAsShortDateTimeFromDate", dependsOnMethods = {"testFormatAsShortDateTimeFromNull"})
	public void testFormatAsShortDateTime(final Date date, final String excepted, final Locale locale) throws Exception {
		Locale.setDefault(locale);
		final String shortDateTime = ZkDateUtils.formatAsShortDateTime(date);
		Assert.assertEquals(shortDateTime, excepted);
	}

	@Test
	public void testFormatAsShortDateTimeFromNull() throws Exception {
		Assert.assertEquals(ZkDateUtils.formatAsShortDateTime((Calendar) null), StringUtils.EMPTY);
		Assert.assertEquals(ZkDateUtils.formatAsShortDateTime((Date) null), StringUtils.EMPTY);
	}

	@Test(dataProvider = "dataGetFirstDay", dependsOnMethods = {"testGetFirstDayFromNull"})
	public void testGetFirstDay(final Calendar calendar, final Month expectedMonth) throws Exception {
		final Calendar firstDay = ZkDateUtils.getFirstDay(calendar);
		Assert.assertNotNull(firstDay);
		Assert.assertEquals(firstDay.get(Calendar.DAY_OF_MONTH), 1);

		final Month month = Month.of(firstDay.get(Calendar.MONTH) + 1);
		Assert.assertEquals(month, expectedMonth);
	}

	@Test
	public void testGetFirstDayFromNull() throws Exception {
		Assert.assertNull(ZkDateUtils.getFirstDay(null));
	}

	@Test(dataProvider = "dataGetLastDay", dependsOnMethods = {"testGetLastDayFromNull"})
	public void testGetLastDay(final Calendar calendar, final Month expectedMonth, final int expectedDay) throws Exception {
		final Calendar lastDay = ZkDateUtils.getLastDay(calendar);
		Assert.assertNotNull(lastDay);
		Assert.assertEquals(lastDay.get(Calendar.DAY_OF_MONTH), expectedDay);
		final Month month = Month.of(lastDay.get(Calendar.MONTH) + 1);
		Assert.assertEquals(month, expectedMonth);
	}

	@Test
	public void testGetLastDayFromNull() throws Exception {
		Assert.assertNull(ZkDateUtils.getFirstDay(null));
	}
}