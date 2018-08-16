package com.pivotal.pcf.mysqlweb;

import java.util.Arrays;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.AccessTokenProvider;
import org.springframework.security.oauth2.client.token.AccessTokenProviderChain;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.implicit.ImplicitAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordAccessTokenProvider;

import com.pivotal.pcf.mysqlweb.openid.OpenIDTokenProvider;

@SpringBootApplication
@EnableOAuth2Sso
public class Application {

	public static void main(String[] args) {
		if ("true".equals(System.getenv("SKIP_SSL_VALIDATION"))) {
			SSLValidationDisabler.disableSSLValidation();
		}
		SpringApplication.run(Application.class, args);
	}

	@Autowired
	private OAuth2RestTemplate oauth2RestTemplate;

	@Bean
	public OAuth2RestTemplate oauth2RestTemplate(OAuth2ProtectedResourceDetails oa2prd, OAuth2ClientContext oa2cc) {
		return new OAuth2RestTemplate(oa2prd, oa2cc);
	}

	@PostConstruct
	public void init() {
		oauth2RestTemplate.setAccessTokenProvider(accessTokenProviderChain());
	}

	@Bean
	public AccessTokenProvider accessTokenProviderChain() {
		return new AccessTokenProviderChain(Arrays.<AccessTokenProvider>asList(new OpenIDTokenProvider(),
				new AuthorizationCodeAccessTokenProvider(), new ImplicitAccessTokenProvider(),
				new ResourceOwnerPasswordAccessTokenProvider(), new ClientCredentialsAccessTokenProvider()));
	}

}
