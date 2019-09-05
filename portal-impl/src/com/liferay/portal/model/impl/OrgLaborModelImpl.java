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

package com.liferay.portal.model.impl;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.util.ExpandoBridgeFactoryUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.json.JSON;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.OrgLabor;
import com.liferay.portal.kernel.model.OrgLaborModel;
import com.liferay.portal.kernel.model.OrgLaborSoap;
import com.liferay.portal.kernel.model.impl.BaseModelImpl;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ProxyUtil;

import java.io.Serializable;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;

import java.sql.Types;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * The base model implementation for the OrgLabor service. Represents a row in the &quot;OrgLabor&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface </code>OrgLaborModel</code> exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link OrgLaborImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see OrgLaborImpl
 * @generated
 */
@JSON(strict = true)
public class OrgLaborModelImpl
	extends BaseModelImpl<OrgLabor> implements OrgLaborModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a org labor model instance should use the <code>OrgLabor</code> interface instead.
	 */
	public static final String TABLE_NAME = "OrgLabor";

	public static final Object[][] TABLE_COLUMNS = {
		{"mvccVersion", Types.BIGINT}, {"orgLaborId", Types.BIGINT},
		{"companyId", Types.BIGINT}, {"organizationId", Types.BIGINT},
		{"typeId", Types.BIGINT}, {"sunOpen", Types.INTEGER},
		{"sunClose", Types.INTEGER}, {"monOpen", Types.INTEGER},
		{"monClose", Types.INTEGER}, {"tueOpen", Types.INTEGER},
		{"tueClose", Types.INTEGER}, {"wedOpen", Types.INTEGER},
		{"wedClose", Types.INTEGER}, {"thuOpen", Types.INTEGER},
		{"thuClose", Types.INTEGER}, {"friOpen", Types.INTEGER},
		{"friClose", Types.INTEGER}, {"satOpen", Types.INTEGER},
		{"satClose", Types.INTEGER}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
		new HashMap<String, Integer>();

	static {
		TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("orgLaborId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("organizationId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("typeId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("sunOpen", Types.INTEGER);
		TABLE_COLUMNS_MAP.put("sunClose", Types.INTEGER);
		TABLE_COLUMNS_MAP.put("monOpen", Types.INTEGER);
		TABLE_COLUMNS_MAP.put("monClose", Types.INTEGER);
		TABLE_COLUMNS_MAP.put("tueOpen", Types.INTEGER);
		TABLE_COLUMNS_MAP.put("tueClose", Types.INTEGER);
		TABLE_COLUMNS_MAP.put("wedOpen", Types.INTEGER);
		TABLE_COLUMNS_MAP.put("wedClose", Types.INTEGER);
		TABLE_COLUMNS_MAP.put("thuOpen", Types.INTEGER);
		TABLE_COLUMNS_MAP.put("thuClose", Types.INTEGER);
		TABLE_COLUMNS_MAP.put("friOpen", Types.INTEGER);
		TABLE_COLUMNS_MAP.put("friClose", Types.INTEGER);
		TABLE_COLUMNS_MAP.put("satOpen", Types.INTEGER);
		TABLE_COLUMNS_MAP.put("satClose", Types.INTEGER);
	}

	public static final String TABLE_SQL_CREATE =
		"create table OrgLabor (mvccVersion LONG default 0 not null,orgLaborId LONG not null primary key,companyId LONG,organizationId LONG,typeId LONG,sunOpen INTEGER,sunClose INTEGER,monOpen INTEGER,monClose INTEGER,tueOpen INTEGER,tueClose INTEGER,wedOpen INTEGER,wedClose INTEGER,thuOpen INTEGER,thuClose INTEGER,friOpen INTEGER,friClose INTEGER,satOpen INTEGER,satClose INTEGER)";

	public static final String TABLE_SQL_DROP = "drop table OrgLabor";

	public static final String ORDER_BY_JPQL =
		" ORDER BY orgLabor.organizationId ASC, orgLabor.typeId ASC";

	public static final String ORDER_BY_SQL =
		" ORDER BY OrgLabor.organizationId ASC, OrgLabor.typeId ASC";

	public static final String DATA_SOURCE = "liferayDataSource";

	public static final String SESSION_FACTORY = "liferaySessionFactory";

	public static final String TX_MANAGER = "liferayTransactionManager";

	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(
		com.liferay.portal.util.PropsUtil.get(
			"value.object.entity.cache.enabled.com.liferay.portal.kernel.model.OrgLabor"),
		true);

	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(
		com.liferay.portal.util.PropsUtil.get(
			"value.object.finder.cache.enabled.com.liferay.portal.kernel.model.OrgLabor"),
		true);

	public static final boolean COLUMN_BITMASK_ENABLED = GetterUtil.getBoolean(
		com.liferay.portal.util.PropsUtil.get(
			"value.object.column.bitmask.enabled.com.liferay.portal.kernel.model.OrgLabor"),
		true);

	public static final long ORGANIZATIONID_COLUMN_BITMASK = 1L;

	public static final long TYPEID_COLUMN_BITMASK = 2L;

	/**
	 * Converts the soap model instance into a normal model instance.
	 *
	 * @param soapModel the soap model instance to convert
	 * @return the normal model instance
	 */
	public static OrgLabor toModel(OrgLaborSoap soapModel) {
		if (soapModel == null) {
			return null;
		}

		OrgLabor model = new OrgLaborImpl();

		model.setMvccVersion(soapModel.getMvccVersion());
		model.setOrgLaborId(soapModel.getOrgLaborId());
		model.setCompanyId(soapModel.getCompanyId());
		model.setOrganizationId(soapModel.getOrganizationId());
		model.setTypeId(soapModel.getTypeId());
		model.setSunOpen(soapModel.getSunOpen());
		model.setSunClose(soapModel.getSunClose());
		model.setMonOpen(soapModel.getMonOpen());
		model.setMonClose(soapModel.getMonClose());
		model.setTueOpen(soapModel.getTueOpen());
		model.setTueClose(soapModel.getTueClose());
		model.setWedOpen(soapModel.getWedOpen());
		model.setWedClose(soapModel.getWedClose());
		model.setThuOpen(soapModel.getThuOpen());
		model.setThuClose(soapModel.getThuClose());
		model.setFriOpen(soapModel.getFriOpen());
		model.setFriClose(soapModel.getFriClose());
		model.setSatOpen(soapModel.getSatOpen());
		model.setSatClose(soapModel.getSatClose());

		return model;
	}

	/**
	 * Converts the soap model instances into normal model instances.
	 *
	 * @param soapModels the soap model instances to convert
	 * @return the normal model instances
	 */
	public static List<OrgLabor> toModels(OrgLaborSoap[] soapModels) {
		if (soapModels == null) {
			return null;
		}

		List<OrgLabor> models = new ArrayList<OrgLabor>(soapModels.length);

		for (OrgLaborSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(
		com.liferay.portal.util.PropsUtil.get(
			"lock.expiration.time.com.liferay.portal.kernel.model.OrgLabor"));

	public OrgLaborModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _orgLaborId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setOrgLaborId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _orgLaborId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return OrgLabor.class;
	}

	@Override
	public String getModelClassName() {
		return OrgLabor.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		Map<String, Function<OrgLabor, Object>> attributeGetterFunctions =
			getAttributeGetterFunctions();

		for (Map.Entry<String, Function<OrgLabor, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<OrgLabor, Object> attributeGetterFunction =
				entry.getValue();

			attributes.put(
				attributeName, attributeGetterFunction.apply((OrgLabor)this));
		}

		attributes.put("entityCacheEnabled", isEntityCacheEnabled());
		attributes.put("finderCacheEnabled", isFinderCacheEnabled());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Map<String, BiConsumer<OrgLabor, Object>> attributeSetterBiConsumers =
			getAttributeSetterBiConsumers();

		for (Map.Entry<String, Object> entry : attributes.entrySet()) {
			String attributeName = entry.getKey();

			BiConsumer<OrgLabor, Object> attributeSetterBiConsumer =
				attributeSetterBiConsumers.get(attributeName);

			if (attributeSetterBiConsumer != null) {
				attributeSetterBiConsumer.accept(
					(OrgLabor)this, entry.getValue());
			}
		}
	}

	public Map<String, Function<OrgLabor, Object>>
		getAttributeGetterFunctions() {

		return _attributeGetterFunctions;
	}

	public Map<String, BiConsumer<OrgLabor, Object>>
		getAttributeSetterBiConsumers() {

		return _attributeSetterBiConsumers;
	}

	private static Function<InvocationHandler, OrgLabor>
		_getProxyProviderFunction() {

		Class<?> proxyClass = ProxyUtil.getProxyClass(
			OrgLabor.class.getClassLoader(), OrgLabor.class,
			ModelWrapper.class);

		try {
			Constructor<OrgLabor> constructor =
				(Constructor<OrgLabor>)proxyClass.getConstructor(
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

	private static final Map<String, Function<OrgLabor, Object>>
		_attributeGetterFunctions;
	private static final Map<String, BiConsumer<OrgLabor, Object>>
		_attributeSetterBiConsumers;

	static {
		Map<String, Function<OrgLabor, Object>> attributeGetterFunctions =
			new LinkedHashMap<String, Function<OrgLabor, Object>>();
		Map<String, BiConsumer<OrgLabor, ?>> attributeSetterBiConsumers =
			new LinkedHashMap<String, BiConsumer<OrgLabor, ?>>();

		attributeGetterFunctions.put("mvccVersion", OrgLabor::getMvccVersion);
		attributeSetterBiConsumers.put(
			"mvccVersion",
			(BiConsumer<OrgLabor, Long>)OrgLabor::setMvccVersion);
		attributeGetterFunctions.put("orgLaborId", OrgLabor::getOrgLaborId);
		attributeSetterBiConsumers.put(
			"orgLaborId", (BiConsumer<OrgLabor, Long>)OrgLabor::setOrgLaborId);
		attributeGetterFunctions.put("companyId", OrgLabor::getCompanyId);
		attributeSetterBiConsumers.put(
			"companyId", (BiConsumer<OrgLabor, Long>)OrgLabor::setCompanyId);
		attributeGetterFunctions.put(
			"organizationId", OrgLabor::getOrganizationId);
		attributeSetterBiConsumers.put(
			"organizationId",
			(BiConsumer<OrgLabor, Long>)OrgLabor::setOrganizationId);
		attributeGetterFunctions.put("typeId", OrgLabor::getTypeId);
		attributeSetterBiConsumers.put(
			"typeId", (BiConsumer<OrgLabor, Long>)OrgLabor::setTypeId);
		attributeGetterFunctions.put("sunOpen", OrgLabor::getSunOpen);
		attributeSetterBiConsumers.put(
			"sunOpen", (BiConsumer<OrgLabor, Integer>)OrgLabor::setSunOpen);
		attributeGetterFunctions.put("sunClose", OrgLabor::getSunClose);
		attributeSetterBiConsumers.put(
			"sunClose", (BiConsumer<OrgLabor, Integer>)OrgLabor::setSunClose);
		attributeGetterFunctions.put("monOpen", OrgLabor::getMonOpen);
		attributeSetterBiConsumers.put(
			"monOpen", (BiConsumer<OrgLabor, Integer>)OrgLabor::setMonOpen);
		attributeGetterFunctions.put("monClose", OrgLabor::getMonClose);
		attributeSetterBiConsumers.put(
			"monClose", (BiConsumer<OrgLabor, Integer>)OrgLabor::setMonClose);
		attributeGetterFunctions.put("tueOpen", OrgLabor::getTueOpen);
		attributeSetterBiConsumers.put(
			"tueOpen", (BiConsumer<OrgLabor, Integer>)OrgLabor::setTueOpen);
		attributeGetterFunctions.put("tueClose", OrgLabor::getTueClose);
		attributeSetterBiConsumers.put(
			"tueClose", (BiConsumer<OrgLabor, Integer>)OrgLabor::setTueClose);
		attributeGetterFunctions.put("wedOpen", OrgLabor::getWedOpen);
		attributeSetterBiConsumers.put(
			"wedOpen", (BiConsumer<OrgLabor, Integer>)OrgLabor::setWedOpen);
		attributeGetterFunctions.put("wedClose", OrgLabor::getWedClose);
		attributeSetterBiConsumers.put(
			"wedClose", (BiConsumer<OrgLabor, Integer>)OrgLabor::setWedClose);
		attributeGetterFunctions.put("thuOpen", OrgLabor::getThuOpen);
		attributeSetterBiConsumers.put(
			"thuOpen", (BiConsumer<OrgLabor, Integer>)OrgLabor::setThuOpen);
		attributeGetterFunctions.put("thuClose", OrgLabor::getThuClose);
		attributeSetterBiConsumers.put(
			"thuClose", (BiConsumer<OrgLabor, Integer>)OrgLabor::setThuClose);
		attributeGetterFunctions.put("friOpen", OrgLabor::getFriOpen);
		attributeSetterBiConsumers.put(
			"friOpen", (BiConsumer<OrgLabor, Integer>)OrgLabor::setFriOpen);
		attributeGetterFunctions.put("friClose", OrgLabor::getFriClose);
		attributeSetterBiConsumers.put(
			"friClose", (BiConsumer<OrgLabor, Integer>)OrgLabor::setFriClose);
		attributeGetterFunctions.put("satOpen", OrgLabor::getSatOpen);
		attributeSetterBiConsumers.put(
			"satOpen", (BiConsumer<OrgLabor, Integer>)OrgLabor::setSatOpen);
		attributeGetterFunctions.put("satClose", OrgLabor::getSatClose);
		attributeSetterBiConsumers.put(
			"satClose", (BiConsumer<OrgLabor, Integer>)OrgLabor::setSatClose);

		_attributeGetterFunctions = Collections.unmodifiableMap(
			attributeGetterFunctions);
		_attributeSetterBiConsumers = Collections.unmodifiableMap(
			(Map)attributeSetterBiConsumers);
	}

	@JSON
	@Override
	public long getMvccVersion() {
		return _mvccVersion;
	}

	@Override
	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	@JSON
	@Override
	public long getOrgLaborId() {
		return _orgLaborId;
	}

	@Override
	public void setOrgLaborId(long orgLaborId) {
		_orgLaborId = orgLaborId;
	}

	@JSON
	@Override
	public long getCompanyId() {
		return _companyId;
	}

	@Override
	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	@JSON
	@Override
	public long getOrganizationId() {
		return _organizationId;
	}

	@Override
	public void setOrganizationId(long organizationId) {
		_columnBitmask = -1L;

		if (!_setOriginalOrganizationId) {
			_setOriginalOrganizationId = true;

			_originalOrganizationId = _organizationId;
		}

		_organizationId = organizationId;
	}

	public long getOriginalOrganizationId() {
		return _originalOrganizationId;
	}

	@JSON
	@Override
	public long getTypeId() {
		return _typeId;
	}

	@Override
	public void setTypeId(long typeId) {
		_columnBitmask = -1L;

		_typeId = typeId;
	}

	@JSON
	@Override
	public int getSunOpen() {
		return _sunOpen;
	}

	@Override
	public void setSunOpen(int sunOpen) {
		_sunOpen = sunOpen;
	}

	@JSON
	@Override
	public int getSunClose() {
		return _sunClose;
	}

	@Override
	public void setSunClose(int sunClose) {
		_sunClose = sunClose;
	}

	@JSON
	@Override
	public int getMonOpen() {
		return _monOpen;
	}

	@Override
	public void setMonOpen(int monOpen) {
		_monOpen = monOpen;
	}

	@JSON
	@Override
	public int getMonClose() {
		return _monClose;
	}

	@Override
	public void setMonClose(int monClose) {
		_monClose = monClose;
	}

	@JSON
	@Override
	public int getTueOpen() {
		return _tueOpen;
	}

	@Override
	public void setTueOpen(int tueOpen) {
		_tueOpen = tueOpen;
	}

	@JSON
	@Override
	public int getTueClose() {
		return _tueClose;
	}

	@Override
	public void setTueClose(int tueClose) {
		_tueClose = tueClose;
	}

	@JSON
	@Override
	public int getWedOpen() {
		return _wedOpen;
	}

	@Override
	public void setWedOpen(int wedOpen) {
		_wedOpen = wedOpen;
	}

	@JSON
	@Override
	public int getWedClose() {
		return _wedClose;
	}

	@Override
	public void setWedClose(int wedClose) {
		_wedClose = wedClose;
	}

	@JSON
	@Override
	public int getThuOpen() {
		return _thuOpen;
	}

	@Override
	public void setThuOpen(int thuOpen) {
		_thuOpen = thuOpen;
	}

	@JSON
	@Override
	public int getThuClose() {
		return _thuClose;
	}

	@Override
	public void setThuClose(int thuClose) {
		_thuClose = thuClose;
	}

	@JSON
	@Override
	public int getFriOpen() {
		return _friOpen;
	}

	@Override
	public void setFriOpen(int friOpen) {
		_friOpen = friOpen;
	}

	@JSON
	@Override
	public int getFriClose() {
		return _friClose;
	}

	@Override
	public void setFriClose(int friClose) {
		_friClose = friClose;
	}

	@JSON
	@Override
	public int getSatOpen() {
		return _satOpen;
	}

	@Override
	public void setSatOpen(int satOpen) {
		_satOpen = satOpen;
	}

	@JSON
	@Override
	public int getSatClose() {
		return _satClose;
	}

	@Override
	public void setSatClose(int satClose) {
		_satClose = satClose;
	}

	public long getColumnBitmask() {
		return _columnBitmask;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(
			getCompanyId(), OrgLabor.class.getName(), getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public OrgLabor toEscapedModel() {
		if (_escapedModel == null) {
			Function<InvocationHandler, OrgLabor>
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
		OrgLaborImpl orgLaborImpl = new OrgLaborImpl();

		orgLaborImpl.setMvccVersion(getMvccVersion());
		orgLaborImpl.setOrgLaborId(getOrgLaborId());
		orgLaborImpl.setCompanyId(getCompanyId());
		orgLaborImpl.setOrganizationId(getOrganizationId());
		orgLaborImpl.setTypeId(getTypeId());
		orgLaborImpl.setSunOpen(getSunOpen());
		orgLaborImpl.setSunClose(getSunClose());
		orgLaborImpl.setMonOpen(getMonOpen());
		orgLaborImpl.setMonClose(getMonClose());
		orgLaborImpl.setTueOpen(getTueOpen());
		orgLaborImpl.setTueClose(getTueClose());
		orgLaborImpl.setWedOpen(getWedOpen());
		orgLaborImpl.setWedClose(getWedClose());
		orgLaborImpl.setThuOpen(getThuOpen());
		orgLaborImpl.setThuClose(getThuClose());
		orgLaborImpl.setFriOpen(getFriOpen());
		orgLaborImpl.setFriClose(getFriClose());
		orgLaborImpl.setSatOpen(getSatOpen());
		orgLaborImpl.setSatClose(getSatClose());

		orgLaborImpl.resetOriginalValues();

		return orgLaborImpl;
	}

	@Override
	public int compareTo(OrgLabor orgLabor) {
		int value = 0;

		if (getOrganizationId() < orgLabor.getOrganizationId()) {
			value = -1;
		}
		else if (getOrganizationId() > orgLabor.getOrganizationId()) {
			value = 1;
		}
		else {
			value = 0;
		}

		if (value != 0) {
			return value;
		}

		if (getTypeId() < orgLabor.getTypeId()) {
			value = -1;
		}
		else if (getTypeId() > orgLabor.getTypeId()) {
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

		if (!(obj instanceof OrgLabor)) {
			return false;
		}

		OrgLabor orgLabor = (OrgLabor)obj;

		long primaryKey = orgLabor.getPrimaryKey();

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
		return ENTITY_CACHE_ENABLED;
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return FINDER_CACHE_ENABLED;
	}

	@Override
	public void resetOriginalValues() {
		OrgLaborModelImpl orgLaborModelImpl = this;

		orgLaborModelImpl._originalOrganizationId =
			orgLaborModelImpl._organizationId;

		orgLaborModelImpl._setOriginalOrganizationId = false;

		orgLaborModelImpl._columnBitmask = 0;
	}

	@Override
	public CacheModel<OrgLabor> toCacheModel() {
		OrgLaborCacheModel orgLaborCacheModel = new OrgLaborCacheModel();

		orgLaborCacheModel.mvccVersion = getMvccVersion();

		orgLaborCacheModel.orgLaborId = getOrgLaborId();

		orgLaborCacheModel.companyId = getCompanyId();

		orgLaborCacheModel.organizationId = getOrganizationId();

		orgLaborCacheModel.typeId = getTypeId();

		orgLaborCacheModel.sunOpen = getSunOpen();

		orgLaborCacheModel.sunClose = getSunClose();

		orgLaborCacheModel.monOpen = getMonOpen();

		orgLaborCacheModel.monClose = getMonClose();

		orgLaborCacheModel.tueOpen = getTueOpen();

		orgLaborCacheModel.tueClose = getTueClose();

		orgLaborCacheModel.wedOpen = getWedOpen();

		orgLaborCacheModel.wedClose = getWedClose();

		orgLaborCacheModel.thuOpen = getThuOpen();

		orgLaborCacheModel.thuClose = getThuClose();

		orgLaborCacheModel.friOpen = getFriOpen();

		orgLaborCacheModel.friClose = getFriClose();

		orgLaborCacheModel.satOpen = getSatOpen();

		orgLaborCacheModel.satClose = getSatClose();

		return orgLaborCacheModel;
	}

	@Override
	public String toString() {
		Map<String, Function<OrgLabor, Object>> attributeGetterFunctions =
			getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			4 * attributeGetterFunctions.size() + 2);

		sb.append("{");

		for (Map.Entry<String, Function<OrgLabor, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<OrgLabor, Object> attributeGetterFunction =
				entry.getValue();

			sb.append(attributeName);
			sb.append("=");
			sb.append(attributeGetterFunction.apply((OrgLabor)this));
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
		Map<String, Function<OrgLabor, Object>> attributeGetterFunctions =
			getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			5 * attributeGetterFunctions.size() + 4);

		sb.append("<model><model-name>");
		sb.append(getModelClassName());
		sb.append("</model-name>");

		for (Map.Entry<String, Function<OrgLabor, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<OrgLabor, Object> attributeGetterFunction =
				entry.getValue();

			sb.append("<column><column-name>");
			sb.append(attributeName);
			sb.append("</column-name><column-value><![CDATA[");
			sb.append(attributeGetterFunction.apply((OrgLabor)this));
			sb.append("]]></column-value></column>");
		}

		sb.append("</model>");

		return sb.toString();
	}

	private static class EscapedModelProxyProviderFunctionHolder {

		private static final Function<InvocationHandler, OrgLabor>
			_escapedModelProxyProviderFunction = _getProxyProviderFunction();

	}

	private long _mvccVersion;
	private long _orgLaborId;
	private long _companyId;
	private long _organizationId;
	private long _originalOrganizationId;
	private boolean _setOriginalOrganizationId;
	private long _typeId;
	private int _sunOpen;
	private int _sunClose;
	private int _monOpen;
	private int _monClose;
	private int _tueOpen;
	private int _tueClose;
	private int _wedOpen;
	private int _wedClose;
	private int _thuOpen;
	private int _thuClose;
	private int _friOpen;
	private int _friClose;
	private int _satOpen;
	private int _satClose;
	private long _columnBitmask;
	private OrgLabor _escapedModel;

}