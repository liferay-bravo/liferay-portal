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

package com.liferay.portal.security.sso.openid.connect.persistence.internal.upgrade.v1_1_0;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.security.sso.openid.connect.persistence.internal.upgrade.v1_1_0.util.OpenIdConnectSessionTable;

/**
 * @author Arthur Chan
 */
public class OpenIdConnectSessionUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		if (!hasColumn("OpenIdConnectSession", "userId")) {
			alter(
				OpenIdConnectSessionTable.class,
				new AlterTableAddColumn("userId", "LONG"));
		}

		if (!hasColumn("OpenIdConnectSession", "configurationPid")) {
			alter(
				OpenIdConnectSessionTable.class,
				new AlterTableAddColumn(
					"configurationPid", "VARCHAR(256) null"));
		}
	}

}