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

package com.liferay.calendar.search.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.calendar.model.Calendar;
import com.liferay.calendar.model.CalendarBooking;
import com.liferay.calendar.model.CalendarBookingConstants;
import com.liferay.calendar.model.CalendarResource;
import com.liferay.calendar.search.CalendarBookingIndexer;
import com.liferay.calendar.service.CalendarBookingLocalServiceUtil;
import com.liferay.calendar.service.CalendarLocalServiceUtil;
import com.liferay.calendar.util.CalendarResourceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.SearchContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerTestRule;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Adam Brandizzi
 */
@RunWith(Arquillian.class)
public class CalendarBookingIndexerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
		_user = UserTestUtil.addUser();

		setUpSearchContext(_group, _user);
	}

	@Test
	public void testSearch() throws Exception {
		setUpSearchContext(_group, TestPropsValues.getUser());

		String title = RandomTestUtil.randomString();

		addCalendarBooking(title);

		assertSearchHitsLength(title, 1);
	}

	@Test
	public void testSearchInTrash() throws Exception {
		setUpSearchContext(_group, _user);

		String title = RandomTestUtil.randomString();

		CalendarBooking calendarBooking = addCalendarBooking(title);

		CalendarBookingLocalServiceUtil.moveCalendarBookingToTrash(
			TestPropsValues.getUserId(), calendarBooking);

		assertSearchHitsLength(title, 0);

		_searchContext.setAttribute(
			Field.STATUS, new int[] {WorkflowConstants.STATUS_IN_TRASH});

		assertSearchHitsLength(title, 1);
	}

	@Test
	public void testSearchNotAdmin() throws Exception {
		setUpSearchContext(_group, _user);

		String title = RandomTestUtil.randomString();

		addCalendarBooking(title);

		assertSearchHitsLength(title, 1);
	}

	protected static SearchContext getSearchContext(Group group, User user)
		throws Exception {

		SearchContext searchContext = SearchContextTestUtil.getSearchContext(
			group.getGroupId());

		searchContext.setUserId(user.getUserId());

		return searchContext;
	}

	protected CalendarBooking addCalendarBooking(String title)
		throws PortalException {

		ServiceContext serviceContext = new ServiceContext();

		CalendarResource calendarResource =
			CalendarResourceUtil.getGroupCalendarResource(
				_group.getGroupId(), serviceContext);

		Calendar calendar = CalendarLocalServiceUtil.addCalendar(
			_user.getUserId(), _group.getGroupId(),
			calendarResource.getCalendarResourceId(),
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap(), StringPool.UTC,
			RandomTestUtil.randomInt(0, 255), false, false, false,
			serviceContext);

		Map<Locale, String> titleMap = new HashMap<>();

		titleMap.put(LocaleUtil.getSiteDefault(), title);

		long startTime = DateUtil.newTime() + RandomTestUtil.randomInt();

		long endTime = startTime + Time.HOUR;

		HashMap<Locale, String> hashMap = new HashMap<>();

		return CalendarBookingLocalServiceUtil.addCalendarBooking(
			_user.getUserId(), calendar.getCalendarId(), new long[0],
			CalendarBookingConstants.PARENT_CALENDAR_BOOKING_ID_DEFAULT,
			titleMap, hashMap, null, startTime, endTime, false, null, 0,
			"email", 0, "email", serviceContext);
	}

	protected void assertSearchHitsLength(
			final String keywords, final int expectedLength)
		throws Exception {

		_searchContext.setKeywords(StringUtil.toLowerCase(keywords));

		Indexer<CalendarBooking> indexer = new CalendarBookingIndexer();

		Hits hits = indexer.search(_searchContext);

		Assert.assertEquals(hits.toString(), expectedLength, hits.getLength());
	}

	protected void setUpSearchContext(Group group, User user) throws Exception {
		_searchContext = getSearchContext(_group, _user);
	}

	@DeleteAfterTestRun
	private Group _group;

	private SearchContext _searchContext;

	@DeleteAfterTestRun
	private User _user;

}