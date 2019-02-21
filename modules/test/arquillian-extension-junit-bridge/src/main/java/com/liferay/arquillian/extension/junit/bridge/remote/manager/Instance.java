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

package com.liferay.arquillian.extension.junit.bridge.remote.manager;

/**
 * @author Matthew Tambara
 */
public class Instance<T> {

	public Instance(Class<T> clazz, Registry registry) {
		_clazz = clazz;
		_registry = registry;
	}

	public T get() {
		return _registry.get(_clazz);
	}

	public void set(T value) {
		_registry.set(_clazz, value);
	}

	private final Class<T> _clazz;
	private final Registry _registry;

}