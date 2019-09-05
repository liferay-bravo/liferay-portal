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

package com.liferay.knowledge.base.model.impl;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.util.ExpandoBridgeFactoryUtil;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.knowledge.base.model.KBComment;
import com.liferay.knowledge.base.model.KBCommentModel;
import com.liferay.knowledge.base.model.KBCommentSoap;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSON;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.impl.BaseModelImpl;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;

import java.sql.Types;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * The base model implementation for the KBComment service. Represents a row in the &quot;KBComment&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface </code>KBCommentModel</code> exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link KBCommentImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see KBCommentImpl
 * @generated
 */
@JSON(strict = true)
public class KBCommentModelImpl
	extends BaseModelImpl<KBComment> implements KBCommentModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a kb comment model instance should use the <code>KBComment</code> interface instead.
	 */
	public static final String TABLE_NAME = "KBComment";

	public static final Object[][] TABLE_COLUMNS = {
		{"uuid_", Types.VARCHAR}, {"kbCommentId", Types.BIGINT},
		{"groupId", Types.BIGINT}, {"companyId", Types.BIGINT},
		{"userId", Types.BIGINT}, {"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP}, {"modifiedDate", Types.TIMESTAMP},
		{"classNameId", Types.BIGINT}, {"classPK", Types.BIGINT},
		{"content", Types.VARCHAR}, {"userRating", Types.INTEGER},
		{"lastPublishDate", Types.TIMESTAMP}, {"status", Types.INTEGER}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
		new HashMap<String, Integer>();

	static {
		TABLE_COLUMNS_MAP.put("uuid_", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("kbCommentId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("classNameId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("classPK", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("content", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("userRating", Types.INTEGER);
		TABLE_COLUMNS_MAP.put("lastPublishDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("status", Types.INTEGER);
	}

	public static final String TABLE_SQL_CREATE =
		"create table KBComment (uuid_ VARCHAR(75) null,kbCommentId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,classNameId LONG,classPK LONG,content STRING null,userRating INTEGER,lastPublishDate DATE null,status INTEGER)";

	public static final String TABLE_SQL_DROP = "drop table KBComment";

	public static final String ORDER_BY_JPQL =
		" ORDER BY kbComment.modifiedDate DESC";

	public static final String ORDER_BY_SQL =
		" ORDER BY KBComment.modifiedDate DESC";

	public static final String DATA_SOURCE = "liferayDataSource";

	public static final String SESSION_FACTORY = "liferaySessionFactory";

	public static final String TX_MANAGER = "liferayTransactionManager";

	public static final long CLASSNAMEID_COLUMN_BITMASK = 1L;

	public static final long CLASSPK_COLUMN_BITMASK = 2L;

	public static final long COMPANYID_COLUMN_BITMASK = 4L;

	public static final long GROUPID_COLUMN_BITMASK = 8L;

	public static final long STATUS_COLUMN_BITMASK = 16L;

	public static final long USERID_COLUMN_BITMASK = 32L;

	public static final long UUID_COLUMN_BITMASK = 64L;

	public static final long MODIFIEDDATE_COLUMN_BITMASK = 128L;

	public static void setEntityCacheEnabled(boolean entityCacheEnabled) {
		_entityCacheEnabled = entityCacheEnabled;
	}

	public static void setFinderCacheEnabled(boolean finderCacheEnabled) {
		_finderCacheEnabled = finderCacheEnabled;
	}

	/**
	 * Converts the soap model instance into a normal model instance.
	 *
	 * @param soapModel the soap model instance to convert
	 * @return the normal model instance
	 */
	public static KBComment toModel(KBCommentSoap soapModel) {
		if (soapModel == null) {
			return null;
		}

		KBComment model = new KBCommentImpl();

		model.setUuid(soapModel.getUuid());
		model.setKbCommentId(soapModel.getKbCommentId());
		model.setGroupId(soapModel.getGroupId());
		model.setCompanyId(soapModel.getCompanyId());
		model.setUserId(soapModel.getUserId());
		model.setUserName(soapModel.getUserName());
		model.setCreateDate(soapModel.getCreateDate());
		model.setModifiedDate(soapModel.getModifiedDate());
		model.setClassNameId(soapModel.getClassNameId());
		model.setClassPK(soapModel.getClassPK());
		model.setContent(soapModel.getContent());
		model.setUserRating(soapModel.getUserRating());
		model.setLastPublishDate(soapModel.getLastPublishDate());
		model.setStatus(soapModel.getStatus());

		return model;
	}

	/**
	 * Converts the soap model instances into normal model instances.
	 *
	 * @param soapModels the soap model instances to convert
	 * @return the normal model instances
	 */
	public static List<KBComment> toModels(KBCommentSoap[] soapModels) {
		if (soapModels == null) {
			return null;
		}

		List<KBComment> models = new ArrayList<KBComment>(soapModels.length);

		for (KBCommentSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public KBCommentModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _kbCommentId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setKbCommentId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _kbCommentId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return KBComment.class;
	}

	@Override
	public String getModelClassName() {
		return KBComment.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		Map<String, Function<KBComment, Object>> attributeGetterFunctions =
			getAttributeGetterFunctions();

		for (Map.Entry<String, Function<KBComment, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<KBComment, Object> attributeGetterFunction =
				entry.getValue();

			attributes.put(
				attributeName, attributeGetterFunction.apply((KBComment)this));
		}

		attributes.put("entityCacheEnabled", isEntityCacheEnabled());
		attributes.put("finderCacheEnabled", isFinderCacheEnabled());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Map<String, BiConsumer<KBComment, Object>> attributeSetterBiConsumers =
			getAttributeSetterBiConsumers();

		for (Map.Entry<String, Object> entry : attributes.entrySet()) {
			String attributeName = entry.getKey();

			BiConsumer<KBComment, Object> attributeSetterBiConsumer =
				attributeSetterBiConsumers.get(attributeName);

			if (attributeSetterBiConsumer != null) {
				attributeSetterBiConsumer.accept(
					(KBComment)this, entry.getValue());
			}
		}
	}

	public Map<String, Function<KBComment, Object>>
		getAttributeGetterFunctions() {

		return _attributeGetterFunctions;
	}

	public Map<String, BiConsumer<KBComment, Object>>
		getAttributeSetterBiConsumers() {

		return _attributeSetterBiConsumers;
	}

	private static Function<InvocationHandler, KBComment>
		_getProxyProviderFunction() {

		Class<?> proxyClass = ProxyUtil.getProxyClass(
			KBComment.class.getClassLoader(), KBComment.class,
			ModelWrapper.class);

		try {
			Constructor<KBComment> constructor =
				(Constructor<KBComment>)proxyClass.getConstructor(
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

	private static final Map<String, Function<KBComment, Object>>
		_attributeGetterFunctions;
	private static final Map<String, BiConsumer<KBComment, Object>>
		_attributeSetterBiConsumers;

	static {
		Map<String, Function<KBComment, Object>> attributeGetterFunctions =
			new LinkedHashMap<String, Function<KBComment, Object>>();
		Map<String, BiConsumer<KBComment, ?>> attributeSetterBiConsumers =
			new LinkedHashMap<String, BiConsumer<KBComment, ?>>();

		attributeGetterFunctions.put("uuid", KBComment::getUuid);
		attributeSetterBiConsumers.put(
			"uuid", (BiConsumer<KBComment, String>)KBComment::setUuid);
		attributeGetterFunctions.put("kbCommentId", KBComment::getKbCommentId);
		attributeSetterBiConsumers.put(
			"kbCommentId",
			(BiConsumer<KBComment, Long>)KBComment::setKbCommentId);
		attributeGetterFunctions.put("groupId", KBComment::getGroupId);
		attributeSetterBiConsumers.put(
			"groupId", (BiConsumer<KBComment, Long>)KBComment::setGroupId);
		attributeGetterFunctions.put("companyId", KBComment::getCompanyId);
		attributeSetterBiConsumers.put(
			"companyId", (BiConsumer<KBComment, Long>)KBComment::setCompanyId);
		attributeGetterFunctions.put("userId", KBComment::getUserId);
		attributeSetterBiConsumers.put(
			"userId", (BiConsumer<KBComment, Long>)KBComment::setUserId);
		attributeGetterFunctions.put("userName", KBComment::getUserName);
		attributeSetterBiConsumers.put(
			"userName", (BiConsumer<KBComment, String>)KBComment::setUserName);
		attributeGetterFunctions.put("createDate", KBComment::getCreateDate);
		attributeSetterBiConsumers.put(
			"createDate",
			(BiConsumer<KBComment, Date>)KBComment::setCreateDate);
		attributeGetterFunctions.put(
			"modifiedDate", KBComment::getModifiedDate);
		attributeSetterBiConsumers.put(
			"modifiedDate",
			(BiConsumer<KBComment, Date>)KBComment::setModifiedDate);
		attributeGetterFunctions.put("classNameId", KBComment::getClassNameId);
		attributeSetterBiConsumers.put(
			"classNameId",
			(BiConsumer<KBComment, Long>)KBComment::setClassNameId);
		attributeGetterFunctions.put("classPK", KBComment::getClassPK);
		attributeSetterBiConsumers.put(
			"classPK", (BiConsumer<KBComment, Long>)KBComment::setClassPK);
		attributeGetterFunctions.put("content", KBComment::getContent);
		attributeSetterBiConsumers.put(
			"content", (BiConsumer<KBComment, String>)KBComment::setContent);
		attributeGetterFunctions.put("userRating", KBComment::getUserRating);
		attributeSetterBiConsumers.put(
			"userRating",
			(BiConsumer<KBComment, Integer>)KBComment::setUserRating);
		attributeGetterFunctions.put(
			"lastPublishDate", KBComment::getLastPublishDate);
		attributeSetterBiConsumers.put(
			"lastPublishDate",
			(BiConsumer<KBComment, Date>)KBComment::setLastPublishDate);
		attributeGetterFunctions.put("status", KBComment::getStatus);
		attributeSetterBiConsumers.put(
			"status", (BiConsumer<KBComment, Integer>)KBComment::setStatus);

		_attributeGetterFunctions = Collections.unmodifiableMap(
			attributeGetterFunctions);
		_attributeSetterBiConsumers = Collections.unmodifiableMap(
			(Map)attributeSetterBiConsumers);
	}

	@JSON
	@Override
	public String getUuid() {
		if (_uuid == null) {
			return "";
		}
		else {
			return _uuid;
		}
	}

	@Override
	public void setUuid(String uuid) {
		_columnBitmask |= UUID_COLUMN_BITMASK;

		if (_originalUuid == null) {
			_originalUuid = _uuid;
		}

		_uuid = uuid;
	}

	public String getOriginalUuid() {
		return GetterUtil.getString(_originalUuid);
	}

	@JSON
	@Override
	public long getKbCommentId() {
		return _kbCommentId;
	}

	@Override
	public void setKbCommentId(long kbCommentId) {
		_kbCommentId = kbCommentId;
	}

	@JSON
	@Override
	public long getGroupId() {
		return _groupId;
	}

	@Override
	public void setGroupId(long groupId) {
		_columnBitmask |= GROUPID_COLUMN_BITMASK;

		if (!_setOriginalGroupId) {
			_setOriginalGroupId = true;

			_originalGroupId = _groupId;
		}

		_groupId = groupId;
	}

	public long getOriginalGroupId() {
		return _originalGroupId;
	}

	@JSON
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

	@JSON
	@Override
	public long getUserId() {
		return _userId;
	}

	@Override
	public void setUserId(long userId) {
		_columnBitmask |= USERID_COLUMN_BITMASK;

		if (!_setOriginalUserId) {
			_setOriginalUserId = true;

			_originalUserId = _userId;
		}

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

	public long getOriginalUserId() {
		return _originalUserId;
	}

	@JSON
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

	@JSON
	@Override
	public Date getCreateDate() {
		return _createDate;
	}

	@Override
	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	@JSON
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

		_columnBitmask = -1L;

		_modifiedDate = modifiedDate;
	}

	@Override
	public String getClassName() {
		if (getClassNameId() <= 0) {
			return "";
		}

		return PortalUtil.getClassName(getClassNameId());
	}

	@Override
	public void setClassName(String className) {
		long classNameId = 0;

		if (Validator.isNotNull(className)) {
			classNameId = PortalUtil.getClassNameId(className);
		}

		setClassNameId(classNameId);
	}

	@JSON
	@Override
	public long getClassNameId() {
		return _classNameId;
	}

	@Override
	public void setClassNameId(long classNameId) {
		_columnBitmask |= CLASSNAMEID_COLUMN_BITMASK;

		if (!_setOriginalClassNameId) {
			_setOriginalClassNameId = true;

			_originalClassNameId = _classNameId;
		}

		_classNameId = classNameId;
	}

	public long getOriginalClassNameId() {
		return _originalClassNameId;
	}

	@JSON
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

	@JSON
	@Override
	public String getContent() {
		if (_content == null) {
			return "";
		}
		else {
			return _content;
		}
	}

	@Override
	public void setContent(String content) {
		_content = content;
	}

	@JSON
	@Override
	public int getUserRating() {
		return _userRating;
	}

	@Override
	public void setUserRating(int userRating) {
		_userRating = userRating;
	}

	@JSON
	@Override
	public Date getLastPublishDate() {
		return _lastPublishDate;
	}

	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		_lastPublishDate = lastPublishDate;
	}

	@JSON
	@Override
	public int getStatus() {
		return _status;
	}

	@Override
	public void setStatus(int status) {
		_columnBitmask |= STATUS_COLUMN_BITMASK;

		if (!_setOriginalStatus) {
			_setOriginalStatus = true;

			_originalStatus = _status;
		}

		_status = status;
	}

	public int getOriginalStatus() {
		return _originalStatus;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return new StagedModelType(
			PortalUtil.getClassNameId(KBComment.class.getName()),
			getClassNameId());
	}

	public long getColumnBitmask() {
		return _columnBitmask;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(
			getCompanyId(), KBComment.class.getName(), getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public KBComment toEscapedModel() {
		if (_escapedModel == null) {
			Function<InvocationHandler, KBComment>
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
		KBCommentImpl kbCommentImpl = new KBCommentImpl();

		kbCommentImpl.setUuid(getUuid());
		kbCommentImpl.setKbCommentId(getKbCommentId());
		kbCommentImpl.setGroupId(getGroupId());
		kbCommentImpl.setCompanyId(getCompanyId());
		kbCommentImpl.setUserId(getUserId());
		kbCommentImpl.setUserName(getUserName());
		kbCommentImpl.setCreateDate(getCreateDate());
		kbCommentImpl.setModifiedDate(getModifiedDate());
		kbCommentImpl.setClassNameId(getClassNameId());
		kbCommentImpl.setClassPK(getClassPK());
		kbCommentImpl.setContent(getContent());
		kbCommentImpl.setUserRating(getUserRating());
		kbCommentImpl.setLastPublishDate(getLastPublishDate());
		kbCommentImpl.setStatus(getStatus());

		kbCommentImpl.resetOriginalValues();

		return kbCommentImpl;
	}

	@Override
	public int compareTo(KBComment kbComment) {
		int value = 0;

		value = DateUtil.compareTo(
			getModifiedDate(), kbComment.getModifiedDate());

		value = value * -1;

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

		if (!(obj instanceof KBComment)) {
			return false;
		}

		KBComment kbComment = (KBComment)obj;

		long primaryKey = kbComment.getPrimaryKey();

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
		KBCommentModelImpl kbCommentModelImpl = this;

		kbCommentModelImpl._originalUuid = kbCommentModelImpl._uuid;

		kbCommentModelImpl._originalGroupId = kbCommentModelImpl._groupId;

		kbCommentModelImpl._setOriginalGroupId = false;

		kbCommentModelImpl._originalCompanyId = kbCommentModelImpl._companyId;

		kbCommentModelImpl._setOriginalCompanyId = false;

		kbCommentModelImpl._originalUserId = kbCommentModelImpl._userId;

		kbCommentModelImpl._setOriginalUserId = false;

		kbCommentModelImpl._setModifiedDate = false;

		kbCommentModelImpl._originalClassNameId =
			kbCommentModelImpl._classNameId;

		kbCommentModelImpl._setOriginalClassNameId = false;

		kbCommentModelImpl._originalClassPK = kbCommentModelImpl._classPK;

		kbCommentModelImpl._setOriginalClassPK = false;

		kbCommentModelImpl._originalStatus = kbCommentModelImpl._status;

		kbCommentModelImpl._setOriginalStatus = false;

		kbCommentModelImpl._columnBitmask = 0;
	}

	@Override
	public CacheModel<KBComment> toCacheModel() {
		KBCommentCacheModel kbCommentCacheModel = new KBCommentCacheModel();

		kbCommentCacheModel.uuid = getUuid();

		String uuid = kbCommentCacheModel.uuid;

		if ((uuid != null) && (uuid.length() == 0)) {
			kbCommentCacheModel.uuid = null;
		}

		kbCommentCacheModel.kbCommentId = getKbCommentId();

		kbCommentCacheModel.groupId = getGroupId();

		kbCommentCacheModel.companyId = getCompanyId();

		kbCommentCacheModel.userId = getUserId();

		kbCommentCacheModel.userName = getUserName();

		String userName = kbCommentCacheModel.userName;

		if ((userName != null) && (userName.length() == 0)) {
			kbCommentCacheModel.userName = null;
		}

		Date createDate = getCreateDate();

		if (createDate != null) {
			kbCommentCacheModel.createDate = createDate.getTime();
		}
		else {
			kbCommentCacheModel.createDate = Long.MIN_VALUE;
		}

		Date modifiedDate = getModifiedDate();

		if (modifiedDate != null) {
			kbCommentCacheModel.modifiedDate = modifiedDate.getTime();
		}
		else {
			kbCommentCacheModel.modifiedDate = Long.MIN_VALUE;
		}

		kbCommentCacheModel.classNameId = getClassNameId();

		kbCommentCacheModel.classPK = getClassPK();

		kbCommentCacheModel.content = getContent();

		String content = kbCommentCacheModel.content;

		if ((content != null) && (content.length() == 0)) {
			kbCommentCacheModel.content = null;
		}

		kbCommentCacheModel.userRating = getUserRating();

		Date lastPublishDate = getLastPublishDate();

		if (lastPublishDate != null) {
			kbCommentCacheModel.lastPublishDate = lastPublishDate.getTime();
		}
		else {
			kbCommentCacheModel.lastPublishDate = Long.MIN_VALUE;
		}

		kbCommentCacheModel.status = getStatus();

		return kbCommentCacheModel;
	}

	@Override
	public String toString() {
		Map<String, Function<KBComment, Object>> attributeGetterFunctions =
			getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			4 * attributeGetterFunctions.size() + 2);

		sb.append("{");

		for (Map.Entry<String, Function<KBComment, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<KBComment, Object> attributeGetterFunction =
				entry.getValue();

			sb.append(attributeName);
			sb.append("=");
			sb.append(attributeGetterFunction.apply((KBComment)this));
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
		Map<String, Function<KBComment, Object>> attributeGetterFunctions =
			getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			5 * attributeGetterFunctions.size() + 4);

		sb.append("<model><model-name>");
		sb.append(getModelClassName());
		sb.append("</model-name>");

		for (Map.Entry<String, Function<KBComment, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<KBComment, Object> attributeGetterFunction =
				entry.getValue();

			sb.append("<column><column-name>");
			sb.append(attributeName);
			sb.append("</column-name><column-value><![CDATA[");
			sb.append(attributeGetterFunction.apply((KBComment)this));
			sb.append("]]></column-value></column>");
		}

		sb.append("</model>");

		return sb.toString();
	}

	private static class EscapedModelProxyProviderFunctionHolder {

		private static final Function<InvocationHandler, KBComment>
			_escapedModelProxyProviderFunction = _getProxyProviderFunction();

	}

	private static boolean _entityCacheEnabled;
	private static boolean _finderCacheEnabled;

	private String _uuid;
	private String _originalUuid;
	private long _kbCommentId;
	private long _groupId;
	private long _originalGroupId;
	private boolean _setOriginalGroupId;
	private long _companyId;
	private long _originalCompanyId;
	private boolean _setOriginalCompanyId;
	private long _userId;
	private long _originalUserId;
	private boolean _setOriginalUserId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private boolean _setModifiedDate;
	private long _classNameId;
	private long _originalClassNameId;
	private boolean _setOriginalClassNameId;
	private long _classPK;
	private long _originalClassPK;
	private boolean _setOriginalClassPK;
	private String _content;
	private int _userRating;
	private Date _lastPublishDate;
	private int _status;
	private int _originalStatus;
	private boolean _setOriginalStatus;
	private long _columnBitmask;
	private KBComment _escapedModel;

}