package io.spring.wso2;

import java.io.File;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jayway.jsonpath.Criteria;
import com.jayway.jsonpath.Filter;
import com.jayway.jsonpath.JsonPath;

import io.spring.wso2.controller.ApiController;

public class JSON {

	private static final Logger LOGGER = LoggerFactory.getLogger(ApiController.class);

	@Test
	public void testName() throws Exception {

		String url = "http://www.mocky.io/v2/59c3b5c6110000320399cc25";
		
//		ObjectMapper om = new ObjectMapper();
//		om.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
//		APIList list = om.readValue(new URL(url), APIList.class);
//
//		LOGGER.info("*** {}", list);

//		String json = om.readValue(new URL(url), String.class);
		
//		LOGGER.info(":: {}", json);
		
		String json = FileUtils.readFileToString(new File("file.json"));
		
		LOGGER.info("{}", json);
		
		Filter filters = Filter.filter(Criteria.where("id").eq("34a73c55-d1b5-494f-97d8-704dc07b6e2c"));
		
		String jsonPath = "$.list[?]";
		
		Object o = JsonPath.read(json, jsonPath , filters);
		
		LOGGER.info("*** {}", o);
		
	}

}
