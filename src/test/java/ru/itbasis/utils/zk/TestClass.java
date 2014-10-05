package ru.itbasis.utils.zk;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TestClass {
	@DataProvider(name = "data")
	public Object[] data() {
		return new Object[]{"test", "test1"};
	}

	@Test(dataProvider = "data")
	public void testData(final String value) {
		Assert.assertNotNull(value);
	}
}
