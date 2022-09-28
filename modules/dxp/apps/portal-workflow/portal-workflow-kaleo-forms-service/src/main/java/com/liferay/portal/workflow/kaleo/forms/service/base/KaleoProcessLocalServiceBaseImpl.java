/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.workflow.kaleo.forms.service.base;

import com.liferay.exportimport.kernel.lar.ExportImportHelperUtil;
import com.liferay.exportimport.kernel.lar.ManifestSummary;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DefaultActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.module.framework.service.IdentifiableOSGiService;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.BaseLocalServiceImpl;
import com.liferay.portal.kernel.service.PersistedModelLocalService;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.workflow.kaleo.forms.model.KaleoProcess;
import com.liferay.portal.workflow.kaleo.forms.service.KaleoProcessLocalService;
import com.liferay.portal.workflow.kaleo.forms.service.KaleoProcessLocalServiceUtil;
import com.liferay.portal.workflow.kaleo.forms.service.persistence.KaleoProcessFinder;
import com.liferay.portal.workflow.kaleo.forms.service.persistence.KaleoProcessPersistence;

import java.io.Serializable;

import java.lang.reflect.Field;

import java.util.List;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides the base implementation for the kaleo process local service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link com.liferay.portal.workflow.kaleo.forms.service.impl.KaleoProcessLocalServiceImpl}.
 * </p>
 *
 * @author Marcellus Tavares
 * @see com.liferay.portal.workflow.kaleo.forms.service.impl.KaleoProcessLocalServiceImpl
 * @generated
 */
public abstract class KaleoProcessLocalServiceBaseImpl
	extends BaseLocalServiceImpl
	implements AopService, IdentifiableOSGiService, KaleoProcessLocalService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Use <code>KaleoProcessLocalService</code> via injection or a <code>org.osgi.util.tracker.ServiceTracker</code> or use <code>KaleoProcessLocalServiceUtil</code>.
	 */

	/**
	 * Adds the kaleo process to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect KaleoProcessLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param kaleoProcess the kaleo process
	 * @return the kaleo process that was added
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public KaleoProcess addKaleoProcess(KaleoProcess kaleoProcess) {
		kaleoProcess.setNew(true);

		return kaleoProcessPersistence.update(kaleoProcess);
	}

	/**
	 * Creates a new kaleo process with the primary key. Does not add the kaleo process to the database.
	 *
	 * @param kaleoProcessId the primary key for the new kaleo process
	 * @return the new kaleo process
	 */
	@Override
	@Transactional(enabled = false)
	public KaleoProcess createKaleoProcess(long kaleoProcessId) {
		return kaleoProcessPersistence.create(kaleoProcessId);
	}

	/**
	 * Deletes the kaleo process with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect KaleoProcessLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param kaleoProcessId the primary key of the kaleo process
	 * @return the kaleo process that was removed
	 * @throws PortalException if a kaleo process with the primary key could not be found
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public KaleoProcess deleteKaleoProcess(long kaleoProcessId)
		throws PortalException {

		return kaleoProcessPersistence.remove(kaleoProcessId);
	}

	/**
	 * Deletes the kaleo process from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect KaleoProcessLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param kaleoProcess the kaleo process
	 * @return the kaleo process that was removed
	 * @throws PortalException
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public KaleoProcess deleteKaleoProcess(KaleoProcess kaleoProcess)
		throws PortalException {

		return kaleoProcessPersistence.remove(kaleoProcess);
	}

	@Override
	public <T> T dslQuery(DSLQuery dslQuery) {
		return kaleoProcessPersistence.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(DSLQuery dslQuery) {
		Long count = dslQuery(dslQuery);

		return count.intValue();
	}

	@Override
	public DynamicQuery dynamicQuery() {
		Class<?> clazz = getClass();

		return DynamicQueryFactoryUtil.forClass(
			KaleoProcess.class, clazz.getClassLoader());
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery) {
		return kaleoProcessPersistence.findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.workflow.kaleo.forms.model.impl.KaleoProcessModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return kaleoProcessPersistence.findWithDynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.workflow.kaleo.forms.model.impl.KaleoProcessModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<T> orderByComparator) {

		return kaleoProcessPersistence.findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(DynamicQuery dynamicQuery) {
		return kaleoProcessPersistence.countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(
		DynamicQuery dynamicQuery, Projection projection) {

		return kaleoProcessPersistence.countWithDynamicQuery(
			dynamicQuery, projection);
	}

	@Override
	public KaleoProcess fetchKaleoProcess(long kaleoProcessId) {
		return kaleoProcessPersistence.fetchByPrimaryKey(kaleoProcessId);
	}

	/**
	 * Returns the kaleo process matching the UUID and group.
	 *
	 * @param uuid the kaleo process's UUID
	 * @param groupId the primary key of the group
	 * @return the matching kaleo process, or <code>null</code> if a matching kaleo process could not be found
	 */
	@Override
	public KaleoProcess fetchKaleoProcessByUuidAndGroupId(
		String uuid, long groupId) {

		return kaleoProcessPersistence.fetchByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the kaleo process with the primary key.
	 *
	 * @param kaleoProcessId the primary key of the kaleo process
	 * @return the kaleo process
	 * @throws PortalException if a kaleo process with the primary key could not be found
	 */
	@Override
	public KaleoProcess getKaleoProcess(long kaleoProcessId)
		throws PortalException {

		return kaleoProcessPersistence.findByPrimaryKey(kaleoProcessId);
	}

	@Override
	public ActionableDynamicQuery getActionableDynamicQuery() {
		ActionableDynamicQuery actionableDynamicQuery =
			new DefaultActionableDynamicQuery();

		actionableDynamicQuery.setBaseLocalService(kaleoProcessLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(KaleoProcess.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName("kaleoProcessId");

		return actionableDynamicQuery;
	}

	@Override
	public IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		IndexableActionableDynamicQuery indexableActionableDynamicQuery =
			new IndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setBaseLocalService(
			kaleoProcessLocalService);
		indexableActionableDynamicQuery.setClassLoader(getClassLoader());
		indexableActionableDynamicQuery.setModelClass(KaleoProcess.class);

		indexableActionableDynamicQuery.setPrimaryKeyPropertyName(
			"kaleoProcessId");

		return indexableActionableDynamicQuery;
	}

	protected void initActionableDynamicQuery(
		ActionableDynamicQuery actionableDynamicQuery) {

		actionableDynamicQuery.setBaseLocalService(kaleoProcessLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(KaleoProcess.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName("kaleoProcessId");
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		final PortletDataContext portletDataContext) {

		final ExportActionableDynamicQuery exportActionableDynamicQuery =
			new ExportActionableDynamicQuery() {

				@Override
				public long performCount() throws PortalException {
					ManifestSummary manifestSummary =
						portletDataContext.getManifestSummary();

					StagedModelType stagedModelType = getStagedModelType();

					long modelAdditionCount = super.performCount();

					manifestSummary.addModelAdditionCount(
						stagedModelType, modelAdditionCount);

					long modelDeletionCount =
						ExportImportHelperUtil.getModelDeletionCount(
							portletDataContext, stagedModelType);

					manifestSummary.addModelDeletionCount(
						stagedModelType, modelDeletionCount);

					return modelAdditionCount;
				}

			};

		initActionableDynamicQuery(exportActionableDynamicQuery);

		exportActionableDynamicQuery.setAddCriteriaMethod(
			new ActionableDynamicQuery.AddCriteriaMethod() {

				@Override
				public void addCriteria(DynamicQuery dynamicQuery) {
					portletDataContext.addDateRangeCriteria(
						dynamicQuery, "modifiedDate");
				}

			});

		exportActionableDynamicQuery.setCompanyId(
			portletDataContext.getCompanyId());

		exportActionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<KaleoProcess>() {

				@Override
				public void performAction(KaleoProcess kaleoProcess)
					throws PortalException {

					StagedModelDataHandlerUtil.exportStagedModel(
						portletDataContext, kaleoProcess);
				}

			});
		exportActionableDynamicQuery.setStagedModelType(
			new StagedModelType(
				PortalUtil.getClassNameId(KaleoProcess.class.getName())));

		return exportActionableDynamicQuery;
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel createPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return kaleoProcessPersistence.create(
			((Long)primaryKeyObj).longValue());
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException {

		if (_log.isWarnEnabled()) {
			_log.warn(
				"Implement KaleoProcessLocalServiceImpl#deleteKaleoProcess(KaleoProcess) to avoid orphaned data");
		}

		return kaleoProcessLocalService.deleteKaleoProcess(
			(KaleoProcess)persistedModel);
	}

	@Override
	public BasePersistence<KaleoProcess> getBasePersistence() {
		return kaleoProcessPersistence;
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return kaleoProcessPersistence.findByPrimaryKey(primaryKeyObj);
	}

	/**
	 * Returns all the kaleo processes matching the UUID and company.
	 *
	 * @param uuid the UUID of the kaleo processes
	 * @param companyId the primary key of the company
	 * @return the matching kaleo processes, or an empty list if no matches were found
	 */
	@Override
	public List<KaleoProcess> getKaleoProcessesByUuidAndCompanyId(
		String uuid, long companyId) {

		return kaleoProcessPersistence.findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of kaleo processes matching the UUID and company.
	 *
	 * @param uuid the UUID of the kaleo processes
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of kaleo processes
	 * @param end the upper bound of the range of kaleo processes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching kaleo processes, or an empty list if no matches were found
	 */
	@Override
	public List<KaleoProcess> getKaleoProcessesByUuidAndCompanyId(
		String uuid, long companyId, int start, int end,
		OrderByComparator<KaleoProcess> orderByComparator) {

		return kaleoProcessPersistence.findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the kaleo process matching the UUID and group.
	 *
	 * @param uuid the kaleo process's UUID
	 * @param groupId the primary key of the group
	 * @return the matching kaleo process
	 * @throws PortalException if a matching kaleo process could not be found
	 */
	@Override
	public KaleoProcess getKaleoProcessByUuidAndGroupId(
			String uuid, long groupId)
		throws PortalException {

		return kaleoProcessPersistence.findByUUID_G(uuid, groupId);
	}

	/**
	 * Returns a range of all the kaleo processes.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.workflow.kaleo.forms.model.impl.KaleoProcessModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of kaleo processes
	 * @param end the upper bound of the range of kaleo processes (not inclusive)
	 * @return the range of kaleo processes
	 */
	@Override
	public List<KaleoProcess> getKaleoProcesses(int start, int end) {
		return kaleoProcessPersistence.findAll(start, end);
	}

	/**
	 * Returns the number of kaleo processes.
	 *
	 * @return the number of kaleo processes
	 */
	@Override
	public int getKaleoProcessesCount() {
		return kaleoProcessPersistence.countAll();
	}

	/**
	 * Updates the kaleo process in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect KaleoProcessLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param kaleoProcess the kaleo process
	 * @return the kaleo process that was updated
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public KaleoProcess updateKaleoProcess(KaleoProcess kaleoProcess) {
		return kaleoProcessPersistence.update(kaleoProcess);
	}

	@Deactivate
	protected void deactivate() {
		_setLocalServiceUtilService(null);
	}

	@Override
	public Class<?>[] getAopInterfaces() {
		return new Class<?>[] {
			KaleoProcessLocalService.class, IdentifiableOSGiService.class,
			PersistedModelLocalService.class
		};
	}

	@Override
	public void setAopProxy(Object aopProxy) {
		kaleoProcessLocalService = (KaleoProcessLocalService)aopProxy;

		_setLocalServiceUtilService(kaleoProcessLocalService);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return KaleoProcessLocalService.class.getName();
	}

	protected Class<?> getModelClass() {
		return KaleoProcess.class;
	}

	protected String getModelClassName() {
		return KaleoProcess.class.getName();
	}

	/**
	 * Performs a SQL query.
	 *
	 * @param sql the sql query
	 */
	protected void runSQL(String sql) {
		try {
			DataSource dataSource = kaleoProcessPersistence.getDataSource();

			DB db = DBManagerUtil.getDB();

			sql = db.buildSQL(sql);
			sql = PortalUtil.transformSQL(sql);

			SqlUpdate sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(
				dataSource, sql);

			sqlUpdate.update();
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
	}

	private void _setLocalServiceUtilService(
		KaleoProcessLocalService kaleoProcessLocalService) {

		try {
			Field field = KaleoProcessLocalServiceUtil.class.getDeclaredField(
				"_service");

			field.setAccessible(true);

			field.set(null, kaleoProcessLocalService);
		}
		catch (ReflectiveOperationException reflectiveOperationException) {
			throw new RuntimeException(reflectiveOperationException);
		}
	}

	protected KaleoProcessLocalService kaleoProcessLocalService;

	@Reference
	protected KaleoProcessPersistence kaleoProcessPersistence;

	@Reference
	protected KaleoProcessFinder kaleoProcessFinder;

	@Reference
	protected com.liferay.counter.kernel.service.CounterLocalService
		counterLocalService;

	private static final Log _log = LogFactoryUtil.getLog(
		KaleoProcessLocalServiceBaseImpl.class);

}