/* eslint-disable @liferay/portal/no-global-fetch */
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

import {Liferay} from '../liferay';
import FetcherError from './FetchError';

const liferayHost = window.location.origin;

const fetcher = async (resource: RequestInfo, options?: RequestInit) => {
	const response = await fetch(`${liferayHost}/o/c${resource}`, {
		...options,
		headers: {
			...options?.headers,
			'Content-Type': 'application/json',
			'x-csrf-token': Liferay.authToken,
		},
	});

	if (!response.ok) {
		const error = new FetcherError(
			'An error occurred while fetching the data.'
		);

		error.info = await response.json();
		error.status = response.status;
		throw error;
	}

	if (options?.method !== 'DELETE') {
		return response.json();
	}
};

fetcher.delete = (resource: RequestInfo) =>
	fetcher(resource, {
		method: 'DELETE',
	});

fetcher.patch = (resource: RequestInfo, data: any, options?: RequestInit) =>
	fetcher(resource, {
		...options,
		body: JSON.stringify(data),
		method: 'PATCH',
	});

fetcher.post = (resource: RequestInfo, data: any, options?: RequestInit) =>
	fetcher(resource, {
		...options,
		body: JSON.stringify(data),
		method: 'POST',
	});

fetcher.put = (resource: RequestInfo, data: any, options?: RequestInit) =>
	fetcher(resource, {
		...options,
		body: JSON.stringify(data),
		method: 'PUT',
	});

export default fetcher;
