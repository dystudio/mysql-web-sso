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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.pivotal.pcf.mysqlweb.UserPref;
import com.pivotal.pcf.mysqlweb.utils.Utils;

@Controller
public class PreferencesController {
	protected static Logger logger = Logger.getLogger(PreferencesController.class);

	@RequestMapping(value = "/prefs", method = RequestMethod.GET)
	public String showPrefs(Model model, HttpServletResponse response, HttpServletRequest request, HttpSession session)
			throws Exception {

		if (Utils.verifyConnection(response, session)) {
			logger.info("user_key is null OR Connection stale so new Login required");
			return null;
		}

		logger.info("Received request to view preferences");

		UserPref userPref = (UserPref) session.getAttribute("prefs");

		model.addAttribute("userPref", userPref);

		return "preferences";
	}

	@RequestMapping(value = "/prefs", method = RequestMethod.POST)
	public String handlePreferencesUpdates(
			@RequestParam(value = "maxrecordsinsqlworksheet", required = true) String maxrecordsinsqlworksheet,
			@RequestParam(value = "historysize", required = true) String historysize, Model model,
			HttpServletResponse response, HttpSession session) throws Exception {

		if (Utils.verifyConnection(response, session)) {
			logger.info("user_key is null OR Connection stale so new Login required");
			return null;
		}

		logger.info("Received request to Update Prefernces");

		UserPref userPref = (UserPref) session.getAttribute("prefs");
		userPref.setHistorySize(Integer.parseInt(historysize));
		userPref.setMaxRecordsinSQLQueryWindow(Integer.parseInt(maxrecordsinsqlworksheet));

		session.setAttribute("userPref", userPref);

		model.addAttribute("userPref", userPref);
		model.addAttribute("success", "Successfully updated preferences");

		return "preferences";

	}
}
