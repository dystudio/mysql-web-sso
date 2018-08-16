/*
PivotalMySQLWeb

Copyright (c) 2017-Present Pivotal Software, Inc. All Rights Reserved.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
package com.pivotal.pcf.mysqlweb.controllers;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.io.IOException;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LogoutController {

	// property set by spring-cloud-sso-connector
	@Value("${ssoServiceUrl:placeholder}")
	private String ssoServiceUrl;

	@Value("${security.oauth2.client.clientId:placeholder}")
	private String clientId;

	// protected static Logger logger = Logger.getLogger(LogoutController.class);
	//
	// @RequestMapping(value = "/exit", method = RequestMethod.GET)
	// public String logout
	// (Model model, HttpSession session, HttpServletResponse response,
	// HttpServletRequest request) throws Exception
	// {
	// logger.info("Received request to logout of PivotalMySQL*Web");
	//
	// session.invalidate();
	//
	// model.addAttribute("loginObj", new Login("", "", "", ""));
	//
	// return "login";
	// }

	@RequestMapping(value = "/exit", method = GET)
	public String logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		URL url = new URL(request.getRequestURL().toString());
		String urlStr = url.getProtocol() + "://" + url.getAuthority();
		return "redirect:" + ssoServiceUrl + "/logout.do?redirect=" + urlStr + "&client_id=" + clientId;
	}

}
