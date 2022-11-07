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

package com.liferay.fragment.model.impl;

import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.fragment.constants.FragmentExportImportConstants;
import com.liferay.fragment.constants.FragmentPortletKeys;
import com.liferay.fragment.model.FragmentComposition;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.service.FragmentCompositionLocalServiceUtil;
import com.liferay.fragment.service.FragmentEntryLocalServiceUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.zip.ZipWriter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Eudaldo Alonso
 */
public class FragmentCollectionImpl extends FragmentCollectionBaseImpl {

	@Override
	public FileEntry getResource(String path) {
		try {
			Repository repository = _getRepository();

			return PortletFileRepositoryUtil.fetchPortletFileEntry(
				getGroupId(),
				_getResourcesFolderId(
					getResourcesFolderId(true), path,
					repository.getRepositoryId()),
				_getFileName(path));
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to retrieve file entry ", portalException);
			}
		}

		return null;
	}

	@Override
	public List<FileEntry> getResources() throws PortalException {
		return _getResources(
			PortletFileRepositoryUtil.getPortletFolder(getResourcesFolderId()));
	}

	@Override
	public long getResourcesFolderId() throws PortalException {
		return getResourcesFolderId(true);
	}

	@Override
	public long getResourcesFolderId(boolean createIfAbsent)
		throws PortalException {

		if (_resourcesFolderId != 0) {
			return _resourcesFolderId;
		}

		Repository repository = _getRepository();

		Folder folder = null;

		try {
			folder = PortletFileRepositoryUtil.getPortletFolder(
				repository.getRepositoryId(), repository.getDlFolderId(),
				String.valueOf(getFragmentCollectionId()));
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			if (createIfAbsent) {
				ServiceContext serviceContext = new ServiceContext();

				serviceContext.setAddGroupPermissions(true);
				serviceContext.setAddGuestPermissions(true);

				folder = PortletFileRepositoryUtil.addPortletFolder(
					getUserId(), repository.getRepositoryId(),
					repository.getDlFolderId(),
					String.valueOf(getFragmentCollectionId()), serviceContext);
			}
			else {
				return 0;
			}
		}

		_resourcesFolderId = folder.getFolderId();

		return _resourcesFolderId;
	}

	@Override
	public boolean hasResources() throws PortalException {
		int fileEntriesCount =
			PortletFileRepositoryUtil.getPortletFileEntriesCount(
				getGroupId(), getResourcesFolderId());

		if (fileEntriesCount <= 0) {
			return false;
		}

		return true;
	}

	@Override
	public void populateZipWriter(ZipWriter zipWriter) throws Exception {
		populateZipWriter(zipWriter, StringPool.BLANK);
	}

	@Override
	public void populateZipWriter(ZipWriter zipWriter, String path)
		throws Exception {

		path = path + StringPool.SLASH + getFragmentCollectionKey();

		zipWriter.addEntry(
			path + StringPool.SLASH +
				FragmentExportImportConstants.FILE_NAME_COLLECTION,
			JSONUtil.put(
				"description", getDescription()
			).put(
				"name", getName()
			).toString());

		List<FragmentComposition> fragmentCompositions =
			FragmentCompositionLocalServiceUtil.getFragmentCompositions(
				getFragmentCollectionId());

		for (FragmentComposition fragmentComposition : fragmentCompositions) {
			fragmentComposition.populateZipWriter(
				zipWriter, path + "/fragment-compositions");
		}

		List<FragmentEntry> fragmentEntries =
			FragmentEntryLocalServiceUtil.getFragmentEntries(
				getFragmentCollectionId(), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);

		for (FragmentEntry fragmentEntry : fragmentEntries) {
			if (fragmentEntry.isTypeReact()) {
				continue;
			}

			fragmentEntry.populateZipWriter(zipWriter, path + "/fragments");
		}

		if (!hasResources()) {
			return;
		}

		for (FileEntry fileEntry : getResources()) {
			zipWriter.addEntry(
				StringBundler.concat(
					path, "/resources/", fileEntry.getFileName()),
				fileEntry.getContentStream());
		}
	}

	private String _getFileName(String path) {
		if (Validator.isNull(path) || path.endsWith(StringPool.SLASH)) {
			return StringPool.BLANK;
		}

		if (path.startsWith(StringPool.SLASH)) {
			path = path.substring(1);
		}

		int index = path.lastIndexOf(StringPool.SLASH);

		if (index == -1) {
			return path;
		}

		return path.substring(index + 1);
	}

	private Repository _getRepository() throws PortalException {
		if (_repository != null) {
			return _repository;
		}

		long groupId = getGroupId();

		if (groupId == 0) {
			User user = UserLocalServiceUtil.getUser(getUserId());

			groupId = user.getGroupId();
		}

		Repository repository =
			PortletFileRepositoryUtil.fetchPortletRepository(
				groupId, FragmentPortletKeys.FRAGMENT);

		if (repository == null) {
			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setAddGroupPermissions(true);
			serviceContext.setAddGuestPermissions(true);

			repository = PortletFileRepositoryUtil.addPortletRepository(
				groupId, FragmentPortletKeys.FRAGMENT, serviceContext);
		}

		_repository = repository;

		return _repository;
	}

	private List<FileEntry> _getResources(Folder folder)
		throws PortalException {

		List<FileEntry> resources = new ArrayList<>();

		Repository repository = _getRepository();

		List<Object> foldersAndFileEntriesAndFileShortcuts =
			DLAppServiceUtil.getFoldersAndFileEntriesAndFileShortcuts(
				repository.getRepositoryId(), folder.getFolderId(),
				WorkflowConstants.STATUS_APPROVED, false, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);

		for (Object object : foldersAndFileEntriesAndFileShortcuts) {
			if (object instanceof Folder) {
				Folder childFolder = (Folder)object;

				resources.addAll(_getResources(childFolder));
			}
			else if (object instanceof FileEntry) {
				resources.add((FileEntry)object);
			}
		}

		return resources;
	}

	private long _getResourcesFolderId(
		long folderId, String path, long repositoryId) {

		if (Validator.isNull(path) || path.endsWith(StringPool.SLASH)) {
			return folderId;
		}

		if (path.startsWith(StringPool.SLASH)) {
			path = path.substring(1);
		}

		String[] pathArray = path.split(StringPool.SLASH);

		if (pathArray.length == 1) {
			return folderId;
		}

		try {
			Folder folder = DLAppServiceUtil.getFolder(
				repositoryId, folderId, pathArray[0]);

			return _getResourcesFolderId(
				folder.getFolderId(),
				path.substring(path.indexOf(StringPool.SLASH)), repositoryId);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"No resource folder found with name " + pathArray[0],
					exception);
			}

			return 0;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FragmentCollectionImpl.class);

	private Repository _repository;
	private long _resourcesFolderId;

}