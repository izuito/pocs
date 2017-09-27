package io.spring.wso2.steps;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.client.RestTemplate;

import cucumber.api.java.pt.Dado;
import cucumber.api.java.pt.Entao;
import cucumber.api.java.pt.Quando;
import io.spring.wso2.SpringContextConfiguration;
import io.spring.wso2.model.dto.Tier;

@ContextConfiguration(classes = SpringContextConfiguration.class)
public class ThrottlingTierSteps {

	private static final Logger LOGGER = LoggerFactory.getLogger(ThrottlingTierSteps.class);

	@Autowired
	private RestTemplate rt;

	private Tier tier;

	private ResponseEntity<Tier> ret;

	@Dado("^que seja informado os dados do Throttling Tier abaixo:$")
	public void queSejaInformadoOsDadosDoThrottlingTierAbaixo(List<Tier> tiers) throws Throwable {
		tier = tiers.get(0);
		Map<String, String> attributes = new HashMap<>();
		attributes.put("a", "1");
		tier.attributes(attributes);
		tier.setTimeUnit("min");
	}

	@Quando("^executar o servico de criacao\\.$")
	public void executarOServicoDeCriacao() throws Throwable {
		LOGGER.info(">>> executarOServicoDeCriacao");
		String url = "http://localhost:8080/throttling/tier";
		ret = rt.exchange(url, HttpMethod.POST, new HttpEntity<>(tier), Tier.class);
	}

	@Entao("^o servico retornara o \"([^\"]*)\"\\.$")
	public void oServicoRetornaraO(Integer statusCode) throws Throwable {
		LOGGER.info(">>> executarOServicoDeCriacao: {}", statusCode);
		Assert.assertEquals(HttpStatus.valueOf(statusCode), ret.getStatusCode());
	}

}
