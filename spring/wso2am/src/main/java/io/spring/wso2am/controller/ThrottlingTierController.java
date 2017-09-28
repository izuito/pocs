package io.spring.wso2am.controller;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.spring.wso2am.dto.TokenResponse;
import io.spring.wso2am.properties.ThrottlingTierProperties;
import io.spring.wso2am.properties.ThrottlingTierProperties.Create;
import io.spring.wso2am.properties.ThrottlingTierProperties.Delete;
import io.spring.wso2am.properties.ThrottlingTierProperties.Get;
import io.spring.wso2am.properties.ThrottlingTierProperties.GetAll;
import io.spring.wso2am.properties.ThrottlingTierProperties.Update;
import io.spring.wso2am.utils.HttpEntityUtils;
import io.swagger.client.publisher.model.Tier;
import io.swagger.client.publisher.model.TierList;

@RestController
@RequestMapping("/tiers")
@EnableConfigurationProperties(value = { ThrottlingTierProperties.class })
public class ThrottlingTierController {

	private final RestTemplate rt;
	private final ApimController ac;
	private final ThrottlingTierProperties ttp;
	private final HttpEntityUtils heu;

	public ThrottlingTierController(RestTemplate rt, ApimController ac, ThrottlingTierProperties ttp,
			HttpEntityUtils heu) {
		this.rt = rt;
		this.ac = ac;
		this.ttp = ttp;
		this.heu = heu;
	}

	@PostMapping
	public @ResponseBody ResponseEntity<Tier> create(@RequestBody Tier t) {
		Create c = ttp.getCreate();
		c.setAuthorization(getAuthorization(c.getScope()));
		HttpEntity<Tier> he = heu.toHttpEntity(t, c.getAuthorization(), c.getContenttype());
		return rt.exchange(c.getUrl(), HttpMethod.POST, he, Tier.class);
	}

	@PutMapping(value = { "/{tierName}" })
	public @ResponseBody ResponseEntity<Tier> update(@PathVariable("tierName") String tierName, @RequestBody Tier t) {
		Update u = ttp.getUpdate();
		u.setUrl(u.getUrl() + "/" + tierName);
		u.setAuthorization(getAuthorization(u.getScope()));
		HttpEntity<Tier> he = heu.toHttpEntity(t, u.getAuthorization(), u.getContenttype());
		return rt.exchange(u.getUrl(), HttpMethod.PUT, he, Tier.class);
	}

	@DeleteMapping(value = { "/{tierName}" })
	public @ResponseBody ResponseEntity<Void> delete(@PathVariable("tierName") String tierName) {
		Delete d = ttp.getDelete();
		d.setAuthorization(getAuthorization(d.getScope()));
		String url = d.getUrl() + "/" + tierName;
		HttpEntity<Tier> he = heu.toHttpEntity(d.getAuthorization());
		return rt.exchange(url, HttpMethod.DELETE, he, Void.class);
	}

	@GetMapping(value = { "/{tierName}" })
	public @ResponseBody ResponseEntity<Tier> get(@PathVariable("tierName") String tierName) {
		Get g = ttp.getGet();
		g.setUrl(g.getUrl() + "/" + tierName);
		g.setAuthorization(getAuthorization(g.getScope()));
		HttpEntity<Tier> he = heu.toHttpEntity(g.getAuthorization());
		return rt.exchange(g.getUrl(), HttpMethod.GET, he, Tier.class);
	}

	@GetMapping
	public @ResponseBody ResponseEntity<TierList> get() {
		GetAll ga = ttp.getGetAll();
		ga.setAuthorization(getAuthorization(ga.getScope()));
		HttpEntity<Tier> he = heu.toHttpEntity(ga.getAuthorization());
		return rt.exchange(ga.getUrl(), HttpMethod.GET, he, TierList.class);
	}

	private String getAuthorization(String scope) {
		ResponseEntity<TokenResponse> retr = ac.token(scope);
		TokenResponse tr = retr.getBody();
		return tr.getTokenType() + " " + tr.getAccessToken();
	}

}
