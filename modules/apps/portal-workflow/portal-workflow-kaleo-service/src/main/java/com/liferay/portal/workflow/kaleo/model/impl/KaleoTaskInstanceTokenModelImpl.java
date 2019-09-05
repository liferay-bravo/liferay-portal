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

package com.liferay.portal.workflow.kaleo.model.impl;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.util.ExpandoBridgeFactoryUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.impl.BaseModelImpl;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceTokenModel;

import java.io.Serializable;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;

import java.sql.Types;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * The base model implementation for the KaleoTaskInstanceToken service. Represents a row in the &quot;KaleoTaskInstanceToken&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface </code>KaleoTaskInstanceTokenModel</code> exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link KaleoTaskInstanceTokenImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see KaleoTaskInstanceTokenImpl
 * @generated
 */
public class KaleoTaskInstanceTokenModelImpl
	extends BaseModelImpl<KaleoTaskInstanceToken>
	implements KaleoTaskInstanceTokenModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a kaleo task instance token model instance should use the <code>KaleoTaskInstanceToken</code> interface instead.
	 */
	public static final String TABLE_NAME = "KaleoTaskInstanceToken";

	public static final Object[][] TABLE_COLUMNS = {
		{"mvccVersion", Types.BIGINT},
		{"kaleoTaskInstanceTokenId", Types.BIGINT}, {"groupId", Types.BIGINT},
		{"companyId", Types.BIGINT}, {"userId", Types.BIGINT},
		{"userName", Types.VARCHAR}, {"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP},
		{"kaleoDefinitionVersionId", Types.BIGINT},
		{"kaleoInstanceId", Types.BIGINT},
		{"kaleoInstanceTokenId", Types.BIGINT}, {"kaleoTaskId", Types.BIGINT},
		{"kaleoTaskName", Types.VARCHAR}, {"className", Types.VARCHAR},
		{"classPK", Types.BIGINT}, {"completionUserId", Types.BIGINT},
		{"completed", Types.BOOLEAN}, {"completionDate", Types.TIMESTAMP},
		{"dueDate", Types.TIMESTAMP}, {"workflowContext", Types.CLOB}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
		new HashMap<String, Integer>();

	static {
		TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("kaleoTaskInstanceTokenId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("kaleoDefinitionVersionId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("kaleoInstanceId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("kaleoInstanceTokenId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("kaleoTaskId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("kaleoTaskName", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("className", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("classPK", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("completionUserId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("completed", Types.BOOLEAN);
		TABLE_COLUMNS_MAP.put("completionDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("dueDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("workflowContext", Types.CLOB);
	}

	public static final String TABLE_SQL_CREATE =
		"create table KaleoTaskInstanceToken (mvccVersion LONG default 0 not null,kaleoTaskInstanceTokenId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(200) null,createDate DATE null,modifiedDate DATE null,kaleoDefinitionVersionId LONG,kaleoInstanceId LONG,kaleoInstanceTokenId LONG,kaleoTaskId LONG,kaleoTaskName VARCHAR(200) null,className VARCHAR(200) null,classPK LONG,completionUserId LONG,completed BOOLEAN,completionDate DATE null,dueDate DATE null,workflowContext TEXT null)";

	public static final String TABLE_SQL_DROP =
		"drop table KaleoTaskInstanceToken";

	public static final String ORDER_BY_JPQL =
		" ORDER BY kaleoTaskInstanceToken.kaleoTaskInstanceTokenId ASC";

	public static final String ORDER_BY_SQL =
		" ORDER BY KaleoTaskInstanceToken.kaleoTaskInstanceTokenId ASC";

	public static final String DATA_SOURCE = "liferayDataSource";

	public static final String SESSION_FACTORY = "liferaySessionFactory";

	public static final String TX_MANAGER = "liferayTransactionManager";

	public static final long CLASSNAME_COLUMN_BITMASK = 1L;

	public static final long CLASSPK_COLUMN_BITMASK = 2L;

	public static final long COMPANYID_COLUMN_BITMASK = 4L;

	public static final long KALEODEFINITIONVERSIONID_COLUMN_BITMASK = 8L;

	public static final long KALEOINSTANCEID_COLUMN_BITMASK = 16L;

	public static final long KALEOTASKID_COLUMN_BITMASK = 32L;

	public static final long KALEOTASKINSTANCETOKENID_COLUMN_BITMASK = 64L;

	public static void setEntityCacheEnabled(boolean entityCacheEnabled) {
		_entityCacheEnabled = entityCacheEnabled;
	}

	public static void setFinderCacheEnabled(boolean finderCacheEnabled) {
		_finderCacheEnabled = finderCacheEnabled;
	}

	public KaleoTaskInstanceTokenModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _kaleoTaskInstanceTokenId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setKaleoTaskInstanceTokenId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _kaleoTaskInstanceTokenId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return KaleoTaskInstanceToken.class;
	}

	@Override
	public String getModelClassName() {
		return KaleoTaskInstanceToken.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		Map<String, Function<KaleoTaskInstanceToken, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		for (Map.Entry<String, Function<KaleoTaskInstanceToken, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<KaleoTaskInstanceToken, Object> attributeGetterFunction =
				entry.getValue();

			attributes.put(
				attributeName,
				attributeGetterFunction.apply((KaleoTaskInstanceToken)this));
		}

		attributes.put("entityCacheEnabled", isEntityCacheEnabled());
		attributes.put("finderCacheEnabled", isFinderCacheEnabled());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Map<String, BiConsumer<KaleoTaskInstanceToken, Object>>
			attributeSetterBiConsumers = getAttributeSetterBiConsumers();

		for (Map.Entry<String, Object> entry : attributes.entrySet()) {
			String attributeName = entry.getKey();

			BiConsumer<KaleoTaskInstanceToken, Object>
				attributeSetterBiConsumer = attributeSetterBiConsumers.get(
					attributeName);

			if (attributeSetterBiConsumer != null) {
				attributeSetterBiConsumer.accept(
					(KaleoTaskInstanceToken)this, entry.getValue());
			}
		}
	}

	public Map<String, Function<KaleoTaskInstanceToken, Object>>
		getAttributeGetterFunctions() {

		return _attributeGetterFunctions;
	}

	public Map<String, BiConsumer<KaleoTaskInstanceToken, Object>>
		getAttributeSetterBiConsumers() {

		return _attributeSetterBiConsumers;
	}

	private static Function<InvocationHandler, KaleoTaskInstanceToken>
		_getProxyProviderFunction() {

		Class<?> proxyClass = ProxyUtil.getProxyClass(
			KaleoTaskInstanceToken.class.getClassLoader(),
			KaleoTaskInstanceToken.class, ModelWrapper.class);

		try {
			Constructor<KaleoTaskInstanceToken> constructor =
				(Constructor<KaleoTaskInstanceToken>)proxyClass.getConstructor(
					InvocationHandler.class);

			return invocationHandler -> {
				try {
					return constructor.newInstance(invocationHandler);
				}
				catch (ReflectiveOperationException roe) {
					throw new InternalError(roe);
				}
			};
		}
		catch (NoSuchMethodException nsme) {
			throw new InternalError(nsme);
		}
	}

	private static final Map<String, Function<KaleoTaskInstanceToken, Object>>
		_attributeGetterFunctions;
	private static final Map<String, BiConsumer<KaleoTaskInstanceToken, Object>>
		_attributeSetterBiConsumers;

	static {
		Map<String, Function<KaleoTaskInstanceToken, Object>>
			attributeGetterFunctions =
				new LinkedHashMap
					<String, Function<KaleoTaskInstanceToken, Object>>();
		Map<String, BiConsumer<KaleoTaskInstanceToken, ?>>
			attributeSetterBiConsumers =
				new LinkedHashMap
					<String, BiConsumer<KaleoTaskInstanceToken, ?>>();

		attributeGetterFunctions.put(
			"mvccVersion", KaleoTaskInstanceToken::getMvccVersion);
		attributeSetterBiConsumers.put(
			"mvccVersion",
			(BiConsumer<KaleoTaskInstanceToken, Long>)
				KaleoTaskInstanceToken::setMvccVersion);
		attributeGetterFunctions.put(
			"kaleoTaskInstanceTokenId",
			KaleoTaskInstanceToken::getKaleoTaskInstanceTokenId);
		attributeSetterBiConsumers.put(
			"kaleoTaskInstanceTokenId",
			(BiConsumer<KaleoTaskInstanceToken, Long>)
				KaleoTaskInstanceToken::setKaleoTaskInstanceTokenId);
		attributeGetterFunctions.put(
			"groupId", KaleoTaskInstanceToken::getGroupId);
		attributeSetterBiConsumers.put(
			"groupId",
			(BiConsumer<KaleoTaskInstanceToken, Long>)
				KaleoTaskInstanceToken::setGroupId);
		attributeGetterFunctions.put(
			"companyId", KaleoTaskInstanceToken::getCompanyId);
		attributeSetterBiConsumers.put(
			"companyId",
			(BiConsumer<KaleoTaskInstanceToken, Long>)
				KaleoTaskInstanceToken::setCompanyId);
		attributeGetterFunctions.put(
			"userId", KaleoTaskInstanceToken::getUserId);
		attributeSetterBiConsumers.put(
			"userId",
			(BiConsumer<KaleoTaskInstanceToken, Long>)
				KaleoTaskInstanceToken::setUserId);
		attributeGetterFunctions.put(
			"userName", KaleoTaskInstanceToken::getUserName);
		attributeSetterBiConsumers.put(
			"userName",
			(BiConsumer<KaleoTaskInstanceToken, String>)
				KaleoTaskInstanceToken::setUserName);
		attributeGetterFunctions.put(
			"createDate", KaleoTaskInstanceToken::getCreateDate);
		attributeSetterBiConsumers.put(
			"createDate",
			(BiConsumer<KaleoTaskInstanceToken, Date>)
				KaleoTaskInstanceToken::setCreateDate);
		attributeGetterFunctions.put(
			"modifiedDate", KaleoTaskInstanceToken::getModifiedDate);
		attributeSetterBiConsumers.put(
			"modifiedDate",
			(BiConsumer<KaleoTaskInstanceToken, Date>)
				KaleoTaskInstanceToken::setModifiedDate);
		attributeGetterFunctions.put(
			"kaleoDefinitionVersionId",
			KaleoTaskInstanceToken::getKaleoDefinitionVersionId);
		attributeSetterBiConsumers.put(
			"kaleoDefinitionVersionId",
			(BiConsumer<KaleoTaskInstanceToken, Long>)
				KaleoTaskInstanceToken::setKaleoDefinitionVersionId);
		attributeGetterFunctions.put(
			"kaleoInstanceId", KaleoTaskInstanceToken::getKaleoInstanceId);
		attributeSetterBiConsumers.put(
			"kaleoInstanceId",
			(BiConsumer<KaleoTaskInstanceToken, Long>)
				KaleoTaskInstanceToken::setKaleoInstanceId);
		attributeGetterFunctions.put(
			"kaleoInstanceTokenId",
			KaleoTaskInstanceToken::getKaleoInstanceTokenId);
		attributeSetterBiConsumers.put(
			"kaleoInstanceTokenId",
			(BiConsumer<KaleoTaskInstanceToken, Long>)
				KaleoTaskInstanceToken::setKaleoInstanceTokenId);
		attributeGetterFunctions.put(
			"kaleoTaskId", KaleoTaskInstanceToken::getKaleoTaskId);
		attributeSetterBiConsumers.put(
			"kaleoTaskId",
			(BiConsumer<KaleoTaskInstanceToken, Long>)
				KaleoTaskInstanceToken::setKaleoTaskId);
		attributeGetterFunctions.put(
			"kaleoTaskName", KaleoTaskInstanceToken::getKaleoTaskName);
		attributeSetterBiConsumers.put(
			"kaleoTaskName",
			(BiConsumer<KaleoTaskInstanceToken, String>)
				KaleoTaskInstanceToken::setKaleoTaskName);
		attributeGetterFunctions.put(
			"className", KaleoTaskInstanceToken::getClassName);
		attributeSetterBiConsumers.put(
			"className",
			(BiConsumer<KaleoTaskInstanceToken, String>)
				KaleoTaskInstanceToken::setClassName);
		attributeGetterFunctions.put(
			"classPK", KaleoTaskInstanceToken::getClassPK);
		attributeSetterBiConsumers.put(
			"classPK",
			(BiConsumer<KaleoTaskInstanceToken, Long>)
				KaleoTaskInstanceToken::setClassPK);
		attributeGetterFunctions.put(
			"completionUserId", KaleoTaskInstanceToken::getCompletionUserId);
		attributeSetterBiConsumers.put(
			"completionUserId",
			(BiConsumer<KaleoTaskInstanceToken, Long>)
				KaleoTaskInstanceToken::setCompletionUserId);
		attributeGetterFunctions.put(
			"completed", KaleoTaskInstanceToken::getCompleted);
		attributeSetterBiConsumers.put(
			"completed",
			(BiConsumer<KaleoTaskInstanceToken, Boolean>)
				KaleoTaskInstanceToken::setCompleted);
		attributeGetterFunctions.put(
			"completionDate", KaleoTaskInstanceToken::getCompletionDate);
		attributeSetterBiConsumers.put(
			"completionDate",
			(BiConsumer<KaleoTaskInstanceToken, Date>)
				KaleoTaskInstanceToken::setCompletionDate);
		attributeGetterFunctions.put(
			"dueDate", KaleoTaskInstanceToken::getDueDate);
		attributeSetterBiConsumers.put(
			"dueDate",
			(BiConsumer<KaleoTaskInstanceToken, Date>)
				KaleoTaskInstanceToken::setDueDate);
		attributeGetterFunctions.put(
			"workflowContext", KaleoTaskInstanceToken::getWorkflowContext);
		attributeSetterBiConsumers.put(
			"workflowContext",
			(BiConsumer<KaleoTaskInstanceToken, String>)
				KaleoTaskInstanceToken::setWorkflowContext);

		_attributeGetterFunctions = Collections.unmodifiableMap(
			attributeGetterFunctions);
		_attributeSetterBiConsumers = Collections.unmodifiableMap(
			(Map)attributeSetterBiConsumers);
	}

	@Override
	public long getMvccVersion() {
		return _mvccVersion;
	}

	@Override
	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	@Override
	public long getKaleoTaskInstanceTokenId() {
		return _kaleoTaskInstanceTokenId;
	}

	@Override
	public void setKaleoTaskInstanceTokenId(long kaleoTaskInstanceTokenId) {
		_columnBitmask = -1L;

		_kaleoTaskInstanceTokenId = kaleoTaskInstanceTokenId;
	}

	@Override
	public long getGroupId() {
		return _groupId;
	}

	@Override
	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	@Override
	public long getCompanyId() {
		return _companyId;
	}

	@Override
	public void setCompanyId(long companyId) {
		_columnBitmask |= COMPANYID_COLUMN_BITMASK;

		if (!_setOriginalCompanyId) {
			_setOriginalCompanyId = true;

			_originalCompanyId = _companyId;
		}

		_companyId = companyId;
	}

	public long getOriginalCompanyId() {
		return _originalCompanyId;
	}

	@Override
	public long getUserId() {
		return _userId;
	}

	@Override
	public void setUserId(long userId) {
		_userId = userId;
	}

	@Override
	public String getUserUuid() {
		try {
			User user = UserLocalServiceUtil.getUserById(getUserId());

			return user.getUuid();
		}
		catch (PortalException pe) {
			return "";
		}
	}

	@Override
	public void setUserUuid(String userUuid) {
	}

	@Override
	public String getUserName() {
		if (_userName == null) {
			return "";
		}
		else {
			return _userName;
		}
	}

	@Override
	public void setUserName(String userName) {
		_userName = userName;
	}

	@Override
	public Date getCreateDate() {
		return _createDate;
	}

	@Override
	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	@Override
	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public boolean hasSetModifiedDate() {
		return _setModifiedDate;
	}

	@Override
	public void setModifiedDate(Date modifiedDate) {
		_setModifiedDate = true;

		_modifiedDate = modifiedDate;
	}

	@Override
	public long getKaleoDefinitionVersionId() {
		return _kaleoDefinitionVersionId;
	}

	@Override
	public void setKaleoDefinitionVersionId(long kaleoDefinitionVersionId) {
		_columnBitmask |= KALEODEFINITIONVERSIONID_COLUMN_BITMASK;

		if (!_setOriginalKaleoDefinitionVersionId) {
			_setOriginalKaleoDefinitionVersionId = true;

			_originalKaleoDefinitionVersionId = _kaleoDefinitionVersionId;
		}

		_kaleoDefinitionVersionId = kaleoDefinitionVersionId;
	}

	public long getOriginalKaleoDefinitionVersionId() {
		return _originalKaleoDefinitionVersionId;
	}

	@Override
	public long getKaleoInstanceId() {
		return _kaleoInstanceId;
	}

	@Override
	public void setKaleoInstanceId(long kaleoInstanceId) {
		_columnBitmask |= KALEOINSTANCEID_COLUMN_BITMASK;

		if (!_setOriginalKaleoInstanceId) {
			_setOriginalKaleoInstanceId = true;

			_originalKaleoInstanceId = _kaleoInstanceId;
		}

		_kaleoInstanceId = kaleoInstanceId;
	}

	public long getOriginalKaleoInstanceId() {
		return _originalKaleoInstanceId;
	}

	@Override
	public long getKaleoInstanceTokenId() {
		return _kaleoInstanceTokenId;
	}

	@Override
	public void setKaleoInstanceTokenId(long kaleoInstanceTokenId) {
		_kaleoInstanceTokenId = kaleoInstanceTokenId;
	}

	@Override
	public long getKaleoTaskId() {
		return _kaleoTaskId;
	}

	@Override
	public void setKaleoTaskId(long kaleoTaskId) {
		_columnBitmask |= KALEOTASKID_COLUMN_BITMASK;

		if (!_setOriginalKaleoTaskId) {
			_setOriginalKaleoTaskId = true;

			_originalKaleoTaskId = _kaleoTaskId;
		}

		_kaleoTaskId = kaleoTaskId;
	}

	public long getOriginalKaleoTaskId() {
		return _originalKaleoTaskId;
	}

	@Override
	public String getKaleoTaskName() {
		if (_kaleoTaskName == null) {
			return "";
		}
		else {
			return _kaleoTaskName;
		}
	}

	@Override
	public void setKaleoTaskName(String kaleoTaskName) {
		_kaleoTaskName = kaleoTaskName;
	}

	@Override
	public String getClassName() {
		if (_className == null) {
			return "";
		}
		else {
			return _className;
		}
	}

	@Override
	public void setClassName(String className) {
		_columnBitmask |= CLASSNAME_COLUMN_BITMASK;

		if (_originalClassName == null) {
			_originalClassName = _className;
		}

		_className = className;
	}

	public String getOriginalClassName() {
		return GetterUtil.getString(_originalClassName);
	}

	@Override
	public long getClassPK() {
		return _classPK;
	}

	@Override
	public void setClassPK(long classPK) {
		_columnBitmask |= CLASSPK_COLUMN_BITMASK;

		if (!_setOriginalClassPK) {
			_setOriginalClassPK = true;

			_originalClassPK = _classPK;
		}

		_classPK = classPK;
	}

	public long getOriginalClassPK() {
		return _originalClassPK;
	}

	@Override
	public long getCompletionUserId() {
		return _completionUserId;
	}

	@Override
	public void setCompletionUserId(long completionUserId) {
		_completionUserId = completionUserId;
	}

	@Override
	public String getCompletionUserUuid() {
		try {
			User user = UserLocalServiceUtil.getUserById(getCompletionUserId());

			return user.getUuid();
		}
		catch (PortalException pe) {
			return "";
		}
	}

	@Override
	public void setCompletionUserUuid(String completionUserUuid) {
	}

	@Override
	public boolean getCompleted() {
		return _completed;
	}

	@Override
	public boolean isCompleted() {
		return _completed;
	}

	@Override
	public void setCompleted(boolean completed) {
		_completed = completed;
	}

	@Override
	public Date getCompletionDate() {
		return _completionDate;
	}

	@Override
	public void setCompletionDate(Date completionDate) {
		_completionDate = completionDate;
	}

	@Override
	public Date getDueDate() {
		return _dueDate;
	}

	@Override
	public void setDueDate(Date dueDate) {
		_dueDate = dueDate;
	}

	@Override
	public String getWorkflowContext() {
		if (_workflowContext == null) {
			return "";
		}
		else {
			return _workflowContext;
		}
	}

	@Override
	public void setWorkflowContext(String workflowContext) {
		_workflowContext = workflowContext;
	}

	public long getColumnBitmask() {
		return _columnBitmask;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(
			getCompanyId(), KaleoTaskInstanceToken.class.getName(),
			getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public KaleoTaskInstanceToken toEscapedModel() {
		if (_escapedModel == null) {
			Function<InvocationHandler, KaleoTaskInstanceToken>
				escapedModelProxyProviderFunction =
					EscapedModelProxyProviderFunctionHolder.
						_escapedModelProxyProviderFunction;

			_escapedModel = escapedModelProxyProviderFunction.apply(
				new AutoEscapeBeanHandler(this));
		}

		return _escapedModel;
	}

	@Override
	public Object clone() {
		KaleoTaskInstanceTokenImpl kaleoTaskInstanceTokenImpl =
			new KaleoTaskInstanceTokenImpl();

		kaleoTaskInstanceTokenImpl.setMvccVersion(getMvccVersion());
		kaleoTaskInstanceTokenImpl.setKaleoTaskInstanceTokenId(
			getKaleoTaskInstanceTokenId());
		kaleoTaskInstanceTokenImpl.setGroupId(getGroupId());
		kaleoTaskInstanceTokenImpl.setCompanyId(getCompanyId());
		kaleoTaskInstanceTokenImpl.setUserId(getUserId());
		kaleoTaskInstanceTokenImpl.setUserName(getUserName());
		kaleoTaskInstanceTokenImpl.setCreateDate(getCreateDate());
		kaleoTaskInstanceTokenImpl.setModifiedDate(getModifiedDate());
		kaleoTaskInstanceTokenImpl.setKaleoDefinitionVersionId(
			getKaleoDefinitionVersionId());
		kaleoTaskInstanceTokenImpl.setKaleoInstanceId(getKaleoInstanceId());
		kaleoTaskInstanceTokenImpl.setKaleoInstanceTokenId(
			getKaleoInstanceTokenId());
		kaleoTaskInstanceTokenImpl.setKaleoTaskId(getKaleoTaskId());
		kaleoTaskInstanceTokenImpl.setKaleoTaskName(getKaleoTaskName());
		kaleoTaskInstanceTokenImpl.setClassName(getClassName());
		kaleoTaskInstanceTokenImpl.setClassPK(getClassPK());
		kaleoTaskInstanceTokenImpl.setCompletionUserId(getCompletionUserId());
		kaleoTaskInstanceTokenImpl.setCompleted(isCompleted());
		kaleoTaskInstanceTokenImpl.setCompletionDate(getCompletionDate());
		kaleoTaskInstanceTokenImpl.setDueDate(getDueDate());
		kaleoTaskInstanceTokenImpl.setWorkflowContext(getWorkflowContext());

		kaleoTaskInstanceTokenImpl.resetOriginalValues();

		return kaleoTaskInstanceTokenImpl;
	}

	@Override
	public int compareTo(KaleoTaskInstanceToken kaleoTaskInstanceToken) {
		int value = 0;

		if (getKaleoTaskInstanceTokenId() <
				kaleoTaskInstanceToken.getKaleoTaskInstanceTokenId()) {

			value = -1;
		}
		else if (getKaleoTaskInstanceTokenId() >
					kaleoTaskInstanceToken.getKaleoTaskInstanceTokenId()) {

			value = 1;
		}
		else {
			value = 0;
		}

		if (value != 0) {
			return value;
		}

		return 0;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof KaleoTaskInstanceToken)) {
			return false;
		}

		KaleoTaskInstanceToken kaleoTaskInstanceToken =
			(KaleoTaskInstanceToken)obj;

		long primaryKey = kaleoTaskInstanceToken.getPrimaryKey();

		if (getPrimaryKey() == primaryKey) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return (int)getPrimaryKey();
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _entityCacheEnabled;
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _finderCacheEnabled;
	}

	@Override
	public void resetOriginalValues() {
		KaleoTaskInstanceTokenModelImpl kaleoTaskInstanceTokenModelImpl = this;

		kaleoTaskInstanceTokenModelImpl._originalCompanyId =
			kaleoTaskInstanceTokenModelImpl._companyId;

		kaleoTaskInstanceTokenModelImpl._setOriginalCompanyId = false;

		kaleoTaskInstanceTokenModelImpl._setModifiedDate = false;

		kaleoTaskInstanceTokenModelImpl._originalKaleoDefinitionVersionId =
			kaleoTaskInstanceTokenModelImpl._kaleoDefinitionVersionId;

		kaleoTaskInstanceTokenModelImpl._setOriginalKaleoDefinitionVersionId =
			false;

		kaleoTaskInstanceTokenModelImpl._originalKaleoInstanceId =
			kaleoTaskInstanceTokenModelImpl._kaleoInstanceId;

		kaleoTaskInstanceTokenModelImpl._setOriginalKaleoInstanceId = false;

		kaleoTaskInstanceTokenModelImpl._originalKaleoTaskId =
			kaleoTaskInstanceTokenModelImpl._kaleoTaskId;

		kaleoTaskInstanceTokenModelImpl._setOriginalKaleoTaskId = false;

		kaleoTaskInstanceTokenModelImpl._originalClassName =
			kaleoTaskInstanceTokenModelImpl._className;

		kaleoTaskInstanceTokenModelImpl._originalClassPK =
			kaleoTaskInstanceTokenModelImpl._classPK;

		kaleoTaskInstanceTokenModelImpl._setOriginalClassPK = false;

		kaleoTaskInstanceTokenModelImpl._columnBitmask = 0;
	}

	@Override
	public CacheModel<KaleoTaskInstanceToken> toCacheModel() {
		KaleoTaskInstanceTokenCacheModel kaleoTaskInstanceTokenCacheModel =
			new KaleoTaskInstanceTokenCacheModel();

		kaleoTaskInstanceTokenCacheModel.mvccVersion = getMvccVersion();

		kaleoTaskInstanceTokenCacheModel.kaleoTaskInstanceTokenId =
			getKaleoTaskInstanceTokenId();

		kaleoTaskInstanceTokenCacheModel.groupId = getGroupId();

		kaleoTaskInstanceTokenCacheModel.companyId = getCompanyId();

		kaleoTaskInstanceTokenCacheModel.userId = getUserId();

		kaleoTaskInstanceTokenCacheModel.userName = getUserName();

		String userName = kaleoTaskInstanceTokenCacheModel.userName;

		if ((userName != null) && (userName.length() == 0)) {
			kaleoTaskInstanceTokenCacheModel.userName = null;
		}

		Date createDate = getCreateDate();

		if (createDate != null) {
			kaleoTaskInstanceTokenCacheModel.createDate = createDate.getTime();
		}
		else {
			kaleoTaskInstanceTokenCacheModel.createDate = Long.MIN_VALUE;
		}

		Date modifiedDate = getModifiedDate();

		if (modifiedDate != null) {
			kaleoTaskInstanceTokenCacheModel.modifiedDate =
				modifiedDate.getTime();
		}
		else {
			kaleoTaskInstanceTokenCacheModel.modifiedDate = Long.MIN_VALUE;
		}

		kaleoTaskInstanceTokenCacheModel.kaleoDefinitionVersionId =
			getKaleoDefinitionVersionId();

		kaleoTaskInstanceTokenCacheModel.kaleoInstanceId = getKaleoInstanceId();

		kaleoTaskInstanceTokenCacheModel.kaleoInstanceTokenId =
			getKaleoInstanceTokenId();

		kaleoTaskInstanceTokenCacheModel.kaleoTaskId = getKaleoTaskId();

		kaleoTaskInstanceTokenCacheModel.kaleoTaskName = getKaleoTaskName();

		String kaleoTaskName = kaleoTaskInstanceTokenCacheModel.kaleoTaskName;

		if ((kaleoTaskName != null) && (kaleoTaskName.length() == 0)) {
			kaleoTaskInstanceTokenCacheModel.kaleoTaskName = null;
		}

		kaleoTaskInstanceTokenCacheModel.className = getClassName();

		String className = kaleoTaskInstanceTokenCacheModel.className;

		if ((className != null) && (className.length() == 0)) {
			kaleoTaskInstanceTokenCacheModel.className = null;
		}

		kaleoTaskInstanceTokenCacheModel.classPK = getClassPK();

		kaleoTaskInstanceTokenCacheModel.completionUserId =
			getCompletionUserId();

		kaleoTaskInstanceTokenCacheModel.completed = isCompleted();

		Date completionDate = getCompletionDate();

		if (completionDate != null) {
			kaleoTaskInstanceTokenCacheModel.completionDate =
				completionDate.getTime();
		}
		else {
			kaleoTaskInstanceTokenCacheModel.completionDate = Long.MIN_VALUE;
		}

		Date dueDate = getDueDate();

		if (dueDate != null) {
			kaleoTaskInstanceTokenCacheModel.dueDate = dueDate.getTime();
		}
		else {
			kaleoTaskInstanceTokenCacheModel.dueDate = Long.MIN_VALUE;
		}

		kaleoTaskInstanceTokenCacheModel.workflowContext = getWorkflowContext();

		String workflowContext =
			kaleoTaskInstanceTokenCacheModel.workflowContext;

		if ((workflowContext != null) && (workflowContext.length() == 0)) {
			kaleoTaskInstanceTokenCacheModel.workflowContext = null;
		}

		return kaleoTaskInstanceTokenCacheModel;
	}

	@Override
	public String toString() {
		Map<String, Function<KaleoTaskInstanceToken, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			4 * attributeGetterFunctions.size() + 2);

		sb.append("{");

		for (Map.Entry<String, Function<KaleoTaskInstanceToken, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<KaleoTaskInstanceToken, Object> attributeGetterFunction =
				entry.getValue();

			sb.append(attributeName);
			sb.append("=");
			sb.append(
				attributeGetterFunction.apply((KaleoTaskInstanceToken)this));
			sb.append(", ");
		}

		if (sb.index() > 1) {
			sb.setIndex(sb.index() - 1);
		}

		sb.append("}");

		return sb.toString();
	}

	@Override
	public String toXmlString() {
		Map<String, Function<KaleoTaskInstanceToken, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			5 * attributeGetterFunctions.size() + 4);

		sb.append("<model><model-name>");
		sb.append(getModelClassName());
		sb.append("</model-name>");

		for (Map.Entry<String, Function<KaleoTaskInstanceToken, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<KaleoTaskInstanceToken, Object> attributeGetterFunction =
				entry.getValue();

			sb.append("<column><column-name>");
			sb.append(attributeName);
			sb.append("</column-name><column-value><![CDATA[");
			sb.append(
				attributeGetterFunction.apply((KaleoTaskInstanceToken)this));
			sb.append("]]></column-value></column>");
		}

		sb.append("</model>");

		return sb.toString();
	}

	private static class EscapedModelProxyProviderFunctionHolder {

		private static final Function<InvocationHandler, KaleoTaskInstanceToken>
			_escapedModelProxyProviderFunction = _getProxyProviderFunction();

	}

	private static boolean _entityCacheEnabled;
	private static boolean _finderCacheEnabled;

	private long _mvccVersion;
	private long _kaleoTaskInstanceTokenId;
	private long _groupId;
	private long _companyId;
	private long _originalCompanyId;
	private boolean _setOriginalCompanyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private boolean _setModifiedDate;
	private long _kaleoDefinitionVersionId;
	private long _originalKaleoDefinitionVersionId;
	private boolean _setOriginalKaleoDefinitionVersionId;
	private long _kaleoInstanceId;
	private long _originalKaleoInstanceId;
	private boolean _setOriginalKaleoInstanceId;
	private long _kaleoInstanceTokenId;
	private long _kaleoTaskId;
	private long _originalKaleoTaskId;
	private boolean _setOriginalKaleoTaskId;
	private String _kaleoTaskName;
	private String _className;
	private String _originalClassName;
	private long _classPK;
	private long _originalClassPK;
	private boolean _setOriginalClassPK;
	private long _completionUserId;
	private boolean _completed;
	private Date _completionDate;
	private Date _dueDate;
	private String _workflowContext;
	private long _columnBitmask;
	private KaleoTaskInstanceToken _escapedModel;

}