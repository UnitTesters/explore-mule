package com.mms.mule.explore;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.mule.DefaultMuleMessage;
import org.mule.api.MuleEvent;
import org.mule.munit.runner.functional.FunctionalMunitSuite;
import org.mule.transformer.types.MimeTypes;
import org.mule.util.FileUtils;

public class DataWeaveTests extends FunctionalMunitSuite {

	@Override
	protected String getConfigResources() {
		return "dataweave-testing-suite.xml";
	}
	
	@Test
	public void testDW() throws Exception{
		
		String payload = FileUtils.readFileToString(new File(DataWeaveTests.class.getClassLoader().getResource("sample_data/employees.xml").getPath()));
		
		MuleEvent event = testEvent(payload);
		((DefaultMuleMessage)event.getMessage()).setMimeType(MimeTypes.APPLICATION_XML);
		
		MuleEvent reply = runFlow("dataweave-testing-suiteSub_Flow", event);
		
		HashMap obj = reply.getMessage().getPayload(HashMap.class);
		List<Map> lst = (List<Map>) obj.get("employees");
		MatcherAssert.assertThat(2, Matchers.equalTo(lst.size()));
		MatcherAssert.assertThat(36, Matchers.equalTo(lst.get(0).get("age")));
		
	}
}
