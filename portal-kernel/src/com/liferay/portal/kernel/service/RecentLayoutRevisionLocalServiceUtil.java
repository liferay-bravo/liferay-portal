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

package com.liferay.portal.kernel.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * Provides the local service utility for RecentLayoutRevision. This utility wraps
 * <code>com.liferay.portal.service.impl.RecentLayoutRevisionLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see RecentLayoutRevisionLocalService
 * @generated
 */
public class RecentLayoutRevisionLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.portal.service.impl.RecentLayoutRevisionLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.portal.kernel.model.RecentLayoutRevision
			addRecentLayoutRevision(
				long userId, long layoutRevisionId, long layoutSetBranchId,
				long plid)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addRecentLayoutRevision(
			userId, layoutRevisionId, layoutSetBranchId, plid);
	}

	/**
	 * Adds the recent layout revision to the database. Also notifies the appropriate model listeners.
	 *
	 * @param recentLayoutRevision the recent layout revision
	 * @return the recent layout revision that was added
	 */
	public static com.liferay.portal.kernel.model.RecentLayoutRevision
		addRecentLayoutRevision(
			com.liferay.portal.kernel.model.RecentLayoutRevision
				recentLayoutRevision) {

		return getService().addRecentLayoutRevision(recentLayoutRevision);
	}

	/**
	 * Creates a new recent layout revision with the primary key. Does not add the recent layout revision to the database.
	 *
	 * @param recentLayoutRevisionId the primary key for the new recent layout revision
	 * @return the new recent layout revision
	 */
	public static com.liferay.portal.kernel.model.RecentLayoutRevision
		createRecentLayoutRevision(long recentLayoutRevisionId) {

		return getService().createRecentLayoutRevision(recentLayoutRevisionId);
	}

	/**
	 * @throws PortalException
	 */
	public static com.liferay.portal.kernel.model.PersistedModel
			deletePersistedModel(
				com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deletePersistedModel(persistedModel);
	}

	/**
	 * Deletes the recent layout revision with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param recentLayoutRevisionId the primary key of the recent layout revision
	 * @return the recent layout revision that was removed
	 * @throws PortalException if a recent layout revision with the primary key could not be found
	 */
	public static com.liferay.portal.kernel.model.RecentLayoutRevision
			deleteRecentLayoutRevision(long recentLayoutRevisionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteRecentLayoutRevision(recentLayoutRevisionId);
	}

	/**
	 * Deletes the recent layout revision from the database. Also notifies the appropriate model listeners.
	 *
	 * @param recentLayoutRevision the recent layout revision
	 * @return the recent layout revision that was removed
	 */
	public static com.liferay.portal.kernel.model.RecentLayoutRevision
		deleteRecentLayoutRevision(
			com.liferay.portal.kernel.model.RecentLayoutRevision
				recentLayoutRevision) {

		return getService().deleteRecentLayoutRevision(recentLayoutRevision);
	}

	public static void deleteRecentLayoutRevisions(long layoutRevisionId) {
		getService().deleteRecentLayoutRevisions(layoutRevisionId);
	}

	public static void deleteUserRecentLayoutRevisions(long userId) {
		getService().deleteUserRecentLayoutRevisions(userId);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery
		dynamicQuery() {

		return getService().dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return getService().dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.RecentLayoutRevisionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {

		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.RecentLayoutRevisionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {

		return getService().dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return getService().dynamicQueryCount(dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static com.liferay.portal.kernel.model.RecentLayoutRevision
		fetchRecentLayoutRevision(long recentLayoutRevisionId) {

		return getService().fetchRecentLayoutRevision(recentLayoutRevisionId);
	}

	public static com.liferay.portal.kernel.model.RecentLayoutRevision
		fetchRecentLayoutRevision(
			long userId, long layoutSetBranchId, long plid) {

		return getService().fetchRecentLayoutRevision(
			userId, layoutSetBranchId, plid);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	public static
		com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
			getIndexableActionableDynamicQuery() {

		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.portal.kernel.model.PersistedModel
			getPersistedModel(java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	 * Returns the recent layout revision with the primary key.
	 *
	 * @param recentLayoutRevisionId the primary key of the recent layout revision
	 * @return the recent layout revision
	 * @throws PortalException if a recent layout revision with the primary key could not be found
	 */
	public static com.liferay.portal.kernel.model.RecentLayoutRevision
			getRecentLayoutRevision(long recentLayoutRevisionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getRecentLayoutRevision(recentLayoutRevisionId);
	}

	/**
	 * Returns a range of all the recent layout revisions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.RecentLayoutRevisionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of recent layout revisions
	 * @param end the upper bound of the range of recent layout revisions (not inclusive)
	 * @return the range of recent layout revisions
	 */
	public static java.util.List
		<com.liferay.portal.kernel.model.RecentLayoutRevision>
			getRecentLayoutRevisions(int start, int end) {

		return getService().getRecentLayoutRevisions(start, end);
	}

	/**
	 * Returns the number of recent layout revisions.
	 *
	 * @return the number of recent layout revisions
	 */
	public static int getRecentLayoutRevisionsCount() {
		return getService().getRecentLayoutRevisionsCount();
	}

	/**
	 * Updates the recent layout revision in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param recentLayoutRevision the recent layout revision
	 * @return the recent layout revision that was updated
	 */
	public static com.liferay.portal.kernel.model.RecentLayoutRevision
		updateRecentLayoutRevision(
			com.liferay.portal.kernel.model.RecentLayoutRevision
				recentLayoutRevision) {

		return getService().updateRecentLayoutRevision(recentLayoutRevision);
	}

	public static RecentLayoutRevisionLocalService getService() {
		if (_service == null) {
			_service =
				(RecentLayoutRevisionLocalService)PortalBeanLocatorUtil.locate(
					RecentLayoutRevisionLocalService.class.getName());
		}

		return _service;
	}

	private static RecentLayoutRevisionLocalService _service;

}