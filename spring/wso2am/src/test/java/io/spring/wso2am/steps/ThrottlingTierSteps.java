package io.spring.wso2am.steps;

import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.pt.Dado;
import cucumber.api.java.pt.Entao;
import cucumber.api.java.pt.Quando;
import io.spring.wso2am.SpringContextConfiguration;
import io.swagger.client.publisher.model.Tier;
import io.swagger.client.publisher.model.Tier.TierLevelEnum;
import io.swagger.client.publisher.model.Tier.TierPlanEnum;

@ContextConfiguration(classes = SpringContextConfiguration.class)
public class ThrottlingTierSteps {

	private static final Logger LOGGER = LoggerFactory.getLogger(ThrottlingTierSteps.class);

	@Autowired
	private RestTemplate rt;

	private Tier tier;

	private ResponseEntity<Tier> ret;

	@Dado("^que seja informado os dados: \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\"\\.$")
	public void queSejaInformadoOsDados(String name, Long requestCount, Long unitTime, String tierLevel,
			String tierPlan, String stopOnQuotaReach) throws Throwable {
		tier = new Tier();
		tier.setName(name);
		tier.setRequestCount(requestCount);
		tier.setUnitTime(unitTime);
		tier.setTierLevel(TierLevelEnum.fromValue(tierLevel));
		tier.setTierPlan(TierPlanEnum.fromValue(tierPlan));
		tier.setStopOnQuotaReach(Boolean.valueOf(stopOnQuotaReach));
	}

	@Quando("^executar o servico na url \"([^\"]*)\"\\.$")
	public void executarOServicoNaUrl(String url) throws Throwable {
		try {
			ret = rt.exchange(url, HttpMethod.POST, new HttpEntity<>(tier), Tier.class);
		} catch (HttpClientErrorException e) {
			ret = new ResponseEntity<>(e.getStatusCode());
		}
	}

	@Entao("^o retorno sera \"([^\"]*)\"\\.$")
	public void oRetornoSera(Integer statusCode) throws Throwable {
		Assert.assertEquals(HttpStatus.valueOf(statusCode), ret.getStatusCode());
	}

	public void tearDown() {
		try {
			String url = "http://localhost:8080/tiers/TestTier";
			ResponseEntity<Void> rev = rt.exchange(url, HttpMethod.DELETE, HttpEntity.EMPTY, Void.class);
			LOGGER.info(">>> tearDown: {}", rev);
		} catch (Exception e) {
			LOGGER.error("{}", e);
		}
	}

	@Before
	public void before(Scenario s) {
		LOGGER.info("@Before - [{}]", s.getName());
		if(s.getName().contains("C.01")) {
			tearDown();
		}
	}

	@After
	public void after(Scenario scenario) {
		LOGGER.info("@After - [{}] - [{}] ", scenario.getStatus(), scenario.getName());
	}

}
