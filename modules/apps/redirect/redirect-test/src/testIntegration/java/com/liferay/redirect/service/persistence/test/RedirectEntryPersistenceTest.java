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

package com.liferay.redirect.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;
import com.liferay.redirect.exception.NoSuchEntryException;
import com.liferay.redirect.model.RedirectEntry;
import com.liferay.redirect.service.RedirectEntryLocalServiceUtil;
import com.liferay.redirect.service.persistence.RedirectEntryPersistence;
import com.liferay.redirect.service.persistence.RedirectEntryUtil;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @generated
 */
@RunWith(Arquillian.class)
public class RedirectEntryPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED, "com.liferay.redirect.service"));

	@Before
	public void setUp() {
		_persistence = RedirectEntryUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<RedirectEntry> iterator = _redirectEntries.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		RedirectEntry redirectEntry = _persistence.create(pk);

		Assert.assertNotNull(redirectEntry);

		Assert.assertEquals(redirectEntry.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		RedirectEntry newRedirectEntry = addRedirectEntry();

		_persistence.remove(newRedirectEntry);

		RedirectEntry existingRedirectEntry = _persistence.fetchByPrimaryKey(
			newRedirectEntry.getPrimaryKey());

		Assert.assertNull(existingRedirectEntry);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addRedirectEntry();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		RedirectEntry newRedirectEntry = _persistence.create(pk);

		newRedirectEntry.setMvccVersion(RandomTestUtil.nextLong());

		newRedirectEntry.setUuid(RandomTestUtil.randomString());

		newRedirectEntry.setGroupId(RandomTestUtil.nextLong());

		newRedirectEntry.setCompanyId(RandomTestUtil.nextLong());

		newRedirectEntry.setUserId(RandomTestUtil.nextLong());

		newRedirectEntry.setUserName(RandomTestUtil.randomString());

		newRedirectEntry.setCreateDate(RandomTestUtil.nextDate());

		newRedirectEntry.setModifiedDate(RandomTestUtil.nextDate());

		newRedirectEntry.setDestinationURL(RandomTestUtil.randomString());

		newRedirectEntry.setSourceURL(RandomTestUtil.randomString());

		newRedirectEntry.setTemporary(RandomTestUtil.randomBoolean());

		_redirectEntries.add(_persistence.update(newRedirectEntry));

		RedirectEntry existingRedirectEntry = _persistence.findByPrimaryKey(
			newRedirectEntry.getPrimaryKey());

		Assert.assertEquals(
			existingRedirectEntry.getMvccVersion(),
			newRedirectEntry.getMvccVersion());
		Assert.assertEquals(
			existingRedirectEntry.getUuid(), newRedirectEntry.getUuid());
		Assert.assertEquals(
			existingRedirectEntry.getRedirectEntryId(),
			newRedirectEntry.getRedirectEntryId());
		Assert.assertEquals(
			existingRedirectEntry.getGroupId(), newRedirectEntry.getGroupId());
		Assert.assertEquals(
			existingRedirectEntry.getCompanyId(),
			newRedirectEntry.getCompanyId());
		Assert.assertEquals(
			existingRedirectEntry.getUserId(), newRedirectEntry.getUserId());
		Assert.assertEquals(
			existingRedirectEntry.getUserName(),
			newRedirectEntry.getUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(existingRedirectEntry.getCreateDate()),
			Time.getShortTimestamp(newRedirectEntry.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(existingRedirectEntry.getModifiedDate()),
			Time.getShortTimestamp(newRedirectEntry.getModifiedDate()));
		Assert.assertEquals(
			existingRedirectEntry.getDestinationURL(),
			newRedirectEntry.getDestinationURL());
		Assert.assertEquals(
			existingRedirectEntry.getSourceURL(),
			newRedirectEntry.getSourceURL());
		Assert.assertEquals(
			existingRedirectEntry.isTemporary(),
			newRedirectEntry.isTemporary());
	}

	@Test
	public void testCountByUuid() throws Exception {
		_persistence.countByUuid("");

		_persistence.countByUuid("null");

		_persistence.countByUuid((String)null);
	}

	@Test
	public void testCountByUUID_G() throws Exception {
		_persistence.countByUUID_G("", RandomTestUtil.nextLong());

		_persistence.countByUUID_G("null", 0L);

		_persistence.countByUUID_G((String)null, 0L);
	}

	@Test
	public void testCountByUuid_C() throws Exception {
		_persistence.countByUuid_C("", RandomTestUtil.nextLong());

		_persistence.countByUuid_C("null", 0L);

		_persistence.countByUuid_C((String)null, 0L);
	}

	@Test
	public void testCountByG_S() throws Exception {
		_persistence.countByG_S(RandomTestUtil.nextLong(), "");

		_persistence.countByG_S(0L, "null");

		_persistence.countByG_S(0L, (String)null);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		RedirectEntry newRedirectEntry = addRedirectEntry();

		RedirectEntry existingRedirectEntry = _persistence.findByPrimaryKey(
			newRedirectEntry.getPrimaryKey());

		Assert.assertEquals(existingRedirectEntry, newRedirectEntry);
	}

	@Test(expected = NoSuchEntryException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<RedirectEntry> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"RedirectEntry", "mvccVersion", true, "uuid", true,
			"redirectEntryId", true, "groupId", true, "companyId", true,
			"userId", true, "userName", true, "createDate", true,
			"modifiedDate", true, "destinationURL", true, "sourceURL", true,
			"temporary", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		RedirectEntry newRedirectEntry = addRedirectEntry();

		RedirectEntry existingRedirectEntry = _persistence.fetchByPrimaryKey(
			newRedirectEntry.getPrimaryKey());

		Assert.assertEquals(existingRedirectEntry, newRedirectEntry);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		RedirectEntry missingRedirectEntry = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingRedirectEntry);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		RedirectEntry newRedirectEntry1 = addRedirectEntry();
		RedirectEntry newRedirectEntry2 = addRedirectEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newRedirectEntry1.getPrimaryKey());
		primaryKeys.add(newRedirectEntry2.getPrimaryKey());

		Map<Serializable, RedirectEntry> redirectEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, redirectEntries.size());
		Assert.assertEquals(
			newRedirectEntry1,
			redirectEntries.get(newRedirectEntry1.getPrimaryKey()));
		Assert.assertEquals(
			newRedirectEntry2,
			redirectEntries.get(newRedirectEntry2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, RedirectEntry> redirectEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(redirectEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		RedirectEntry newRedirectEntry = addRedirectEntry();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newRedirectEntry.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, RedirectEntry> redirectEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, redirectEntries.size());
		Assert.assertEquals(
			newRedirectEntry,
			redirectEntries.get(newRedirectEntry.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, RedirectEntry> redirectEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(redirectEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		RedirectEntry newRedirectEntry = addRedirectEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newRedirectEntry.getPrimaryKey());

		Map<Serializable, RedirectEntry> redirectEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, redirectEntries.size());
		Assert.assertEquals(
			newRedirectEntry,
			redirectEntries.get(newRedirectEntry.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			RedirectEntryLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<RedirectEntry>() {

				@Override
				public void performAction(RedirectEntry redirectEntry) {
					Assert.assertNotNull(redirectEntry);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		RedirectEntry newRedirectEntry = addRedirectEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			RedirectEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"redirectEntryId", newRedirectEntry.getRedirectEntryId()));

		List<RedirectEntry> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(1, result.size());

		RedirectEntry existingRedirectEntry = result.get(0);

		Assert.assertEquals(existingRedirectEntry, newRedirectEntry);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			RedirectEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"redirectEntryId", RandomTestUtil.nextLong()));

		List<RedirectEntry> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		RedirectEntry newRedirectEntry = addRedirectEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			RedirectEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("redirectEntryId"));

		Object newRedirectEntryId = newRedirectEntry.getRedirectEntryId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"redirectEntryId", new Object[] {newRedirectEntryId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingRedirectEntryId = result.get(0);

		Assert.assertEquals(existingRedirectEntryId, newRedirectEntryId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			RedirectEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("redirectEntryId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"redirectEntryId", new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		RedirectEntry newRedirectEntry = addRedirectEntry();

		_persistence.clearCache();

		RedirectEntry existingRedirectEntry = _persistence.findByPrimaryKey(
			newRedirectEntry.getPrimaryKey());

		Assert.assertTrue(
			Objects.equals(
				existingRedirectEntry.getUuid(),
				ReflectionTestUtil.invoke(
					existingRedirectEntry, "getOriginalUuid",
					new Class<?>[0])));
		Assert.assertEquals(
			Long.valueOf(existingRedirectEntry.getGroupId()),
			ReflectionTestUtil.<Long>invoke(
				existingRedirectEntry, "getOriginalGroupId", new Class<?>[0]));

		Assert.assertEquals(
			Long.valueOf(existingRedirectEntry.getGroupId()),
			ReflectionTestUtil.<Long>invoke(
				existingRedirectEntry, "getOriginalGroupId", new Class<?>[0]));
		Assert.assertTrue(
			Objects.equals(
				existingRedirectEntry.getSourceURL(),
				ReflectionTestUtil.invoke(
					existingRedirectEntry, "getOriginalSourceURL",
					new Class<?>[0])));
	}

	protected RedirectEntry addRedirectEntry() throws Exception {
		long pk = RandomTestUtil.nextLong();

		RedirectEntry redirectEntry = _persistence.create(pk);

		redirectEntry.setMvccVersion(RandomTestUtil.nextLong());

		redirectEntry.setUuid(RandomTestUtil.randomString());

		redirectEntry.setGroupId(RandomTestUtil.nextLong());

		redirectEntry.setCompanyId(RandomTestUtil.nextLong());

		redirectEntry.setUserId(RandomTestUtil.nextLong());

		redirectEntry.setUserName(RandomTestUtil.randomString());

		redirectEntry.setCreateDate(RandomTestUtil.nextDate());

		redirectEntry.setModifiedDate(RandomTestUtil.nextDate());

		redirectEntry.setDestinationURL(RandomTestUtil.randomString());

		redirectEntry.setSourceURL(RandomTestUtil.randomString());

		redirectEntry.setTemporary(RandomTestUtil.randomBoolean());

		_redirectEntries.add(_persistence.update(redirectEntry));

		return redirectEntry;
	}

	private List<RedirectEntry> _redirectEntries =
		new ArrayList<RedirectEntry>();
	private RedirectEntryPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}