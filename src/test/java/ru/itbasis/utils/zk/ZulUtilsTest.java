package ru.itbasis.utils.zk;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@Test(groups = {"zul", "utils", "zulUtils"})
public class ZulUtilsTest {
	@DataProvider(name = "data")
	public static Object[][] data1() {
		return new Object[][]{{"/test.zul", "/test"},
		                      {"/test.zul", "/test.zul"},
		                      {"/test/index.zul", "/test/"},
		                      {"/WEB-INF/zul/test/index.zul", "test/"},
		                      {"", ""},
		                      {"/WEB-INF/zul/index.zul", "/"}};
	}

	@Test(dataProvider = "data1")
	public void testGetZulPage(final String expected, final String value) throws Exception {
		final String zulPage = ZulUtils.makeFullZulPath(value);
		Assert.assertEquals(zulPage, expected);
	}
}