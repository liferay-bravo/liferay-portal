/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.redirect.web.internal.portlet.action;

import com.liferay.configuration.admin.constants.ConfigurationAdminPortletKeys;
import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListenerException;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.redirect.configuration.RedirectPatternConfigurationProvider;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alicia García
 */
@Component(
	property = {
		"javax.portlet.name=" + ConfigurationAdminPortletKeys.SITE_SETTINGS,
		"mvc.command.name=/redirect_settings/edit_redirect_patterns"
	},
	service = MVCActionCommand.class
)
public class EditRedirectPatternsMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		try {
			_redirectPatternConfigurationProvider.updateRedirectionPatterns(
				ParamUtil.getLong(actionRequest, "scopePK"),
				_getRedirectionPatterns(actionRequest));
		}
		catch (ConfigurationModelListenerException
					configurationModelListenerException) {

			SessionErrors.add(
				actionRequest, configurationModelListenerException.getClass());

			actionResponse.sendRedirect(
				ParamUtil.getString(actionRequest, "redirect"));
		}
	}

	private Map<String, String> _getRedirectionPatterns(
		ActionRequest actionRequest) {

		Map<String, String> redirectPatterns = new LinkedHashMap<>();

		Map<String, String[]> parameterMap = actionRequest.getParameterMap();

		for (int i = 0; parameterMap.containsKey("source_" + i); i++) {
			String source = null;

			String[] sources = parameterMap.get("source_" + i);

			if ((sources.length != 0) && Validator.isNotNull(sources[0])) {
				source = sources[0];
			}

			String destination = null;

			String[] destinations = parameterMap.get("destination_" + i);

			if ((destinations.length != 0) &&
				Validator.isNotNull(destinations[0])) {

				destination = destinations[0];
			}

			if ((source != null) || (destination != null)) {
				redirectPatterns.put(source, destination);
			}
		}

		return redirectPatterns;
	}

	@Reference
	private RedirectPatternConfigurationProvider
		_redirectPatternConfigurationProvider;

}