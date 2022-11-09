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

package com.liferay.project.templates.rest;

import com.liferay.maven.executor.MavenExecutor;
import com.liferay.project.templates.BaseProjectTemplatesTestCase;
import com.liferay.project.templates.extensions.util.Validator;
import com.liferay.project.templates.extensions.util.VersionUtil;
import com.liferay.project.templates.util.FileTestUtil;

import java.io.File;

import java.net.URI;

import java.util.Arrays;
import java.util.Properties;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

/**
 * @author Lawrence Lee
 */
@RunWith(Parameterized.class)
public class ProjectTemplatesRestTest implements BaseProjectTemplatesTestCase {

	@ClassRule
	public static final MavenExecutor mavenExecutor = new MavenExecutor();

	@Parameterized.Parameters(name = "Testcase-{index}: testing {0}")
	public static Iterable<Object[]> data() {
		return Arrays.asList(
			new Object[][] {
				{"7.0.10.17", "dxp"}, {"7.1.10.7", "dxp"}, {"7.2.10.7", "dxp"},
				{"7.3.7", "portal"}, {"7.4.1-1", "portal"}
			});
	}

	@BeforeClass
	public static void setUpClass() throws Exception {
		String gradleDistribution = System.getProperty("gradle.distribution");

		if (Validator.isNull(gradleDistribution)) {
			Properties properties = FileTestUtil.readProperties(
				"gradle-wrapper/gradle/wrapper/gradle-wrapper.properties");

			gradleDistribution = properties.getProperty("distributionUrl");
		}

		Assert.assertTrue(gradleDistribution.contains(GRADLE_WRAPPER_VERSION));

		_gradleDistribution = URI.create(gradleDistribution);
	}

	public ProjectTemplatesRestTest(String liferayVersion, String product) {
		_liferayVersion = liferayVersion;
		_product = product;
	}

	@Test
	public void testBuildTemplateRest() throws Exception {
		String template = "rest";
		String name = "my-rest";

		File gradleWorkspaceDir = buildWorkspace(
			temporaryFolder, "gradle", "gradleWS", _liferayVersion,
			mavenExecutor);

		File gradleWorkspaceModulesDir = new File(
			gradleWorkspaceDir, "modules");

		File gradleProjectDir = buildTemplateWithGradle(
			gradleWorkspaceModulesDir, template, name, "--liferay-version",
			_liferayVersion, "--product", _product);

		testExists(gradleProjectDir, "bnd.bnd");

		if (VersionUtil.getMinorVersion(_liferayVersion) < 3) {
			testContains(
				gradleProjectDir, "build.gradle", DEPENDENCY_RELEASE_DXP_API);
		}
		else {
			testContains(
				gradleProjectDir, "build.gradle",
				DEPENDENCY_RELEASE_PORTAL_API);
		}

		if (!_liferayVersion.startsWith("7.0")) {
			testContains(
				gradleProjectDir, "build.gradle",
				"compileOnly group: \"org.osgi\", name: " +
					"\"org.osgi.service.jaxrs");
		}

		if (_liferayVersion.startsWith("7.0")) {
			testContains(
				gradleProjectDir,
				"src/main/resources/configuration" +
					"/com.liferay.portal.remote.cxf.common.configuration." +
						"CXFEndpointPublisherConfiguration-cxf.properties",
				"contextPath=/my-rest");
			testContains(
				gradleProjectDir,
				"src/main/resources/configuration/com.liferay.portal.remote." +
					"rest.extender.configuration.RestExtenderConfiguration-" +
						"rest.properties",
				"contextPaths=/my-rest",
				"jaxRsApplicationFilterStrings=(component.name=" +
					"my.rest.application.MyRestApplication)");
		}
		else {
			testNotExists(
				gradleProjectDir,
				"src/main/resources/configuration" +
					"/com.liferay.portal.remote.cxf.common.configuration." +
						"CXFEndpointPublisherConfiguration-cxf.properties");
			testNotExists(
				gradleProjectDir,
				"src/main/resources/configuration/com.liferay.portal.remote." +
					"rest.extender.configuration.RestExtenderConfiguration-" +
						"rest.properties");
			testNotExists(gradleProjectDir, "src/main/resources/configuration");
		}

		testContains(
			gradleProjectDir,
			"src/main/java/my/rest/application/MyRestApplication.java",
			"public class MyRestApplication extends Application");

		testNotContains(gradleProjectDir, "build.gradle", "version: \"[0-9].*");

		File mavenWorkspaceDir = buildWorkspace(
			temporaryFolder, "maven", "mavenWS", _liferayVersion,
			mavenExecutor);

		File mavenModulesDir = new File(mavenWorkspaceDir, "modules");

		File mavenProjectDir = buildTemplateWithMaven(
			mavenModulesDir, mavenModulesDir, template, name, "com.test",
			mavenExecutor, "-DclassName=MyRest",
			"-DliferayVersion=" + _liferayVersion, "-Dpackage=my.rest",
			"-Dproduct=" + _product);

		testContains(
			mavenProjectDir, "bnd.bnd",
			"-plugin.metatype: com.liferay.ant.bnd.metatype.MetatypePlugin");

		if (isBuildProjects()) {
			File gradleOutputDir = new File(gradleProjectDir, "build/libs");
			File mavenOutputDir = new File(mavenProjectDir, "target");

			buildProjects(
				_gradleDistribution, mavenExecutor, gradleWorkspaceDir,
				mavenProjectDir, gradleOutputDir, mavenOutputDir,
				":modules:" + name + GRADLE_TASK_PATH_BUILD);
		}
	}

	@Rule
	public TemporaryFolder temporaryFolder = new TemporaryFolder();

	private static URI _gradleDistribution;

	private final String _liferayVersion;
	private final String _product;

}