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

AUI.add(
	'liferay-portlet-base',
	(A) => {
		const PortletBase = function (config) {
			const instance = this;

			let namespace;

			if ('namespace' in config) {
				namespace = config.namespace;
			}
			else {
				namespace = A.guid();
			}

			instance.NS = namespace;
			instance.ID = namespace.replace(/^_(.*)_$/, '$1');

			if (config.rootNode) {
				instance._setRootNode(config.rootNode);
			}
		};

		PortletBase.ATTRS = {
			namespace: {
				getter: '_getNS',
				writeOnce: true,
			},
			rootNode: {
				getter: '_getRootNode',
				setter: '_setRootNode',
				valueFn() {
					const instance = this;

					return A.one('#p_p_id' + instance.NS);
				},
			},
		};

		PortletBase.prototype = {
			_formatSelectorNS(ns, selector) {
				return selector.replace(
					A.DOM._getRegExp('(#|\\[id=(\\"|\\\'))(?!' + ns + ')', 'g'),
					'$1' + ns
				);
			},

			_getNS() {
				const instance = this;

				return instance.NS;
			},

			_getRootNode() {
				const instance = this;

				return instance.rootNode;
			},

			_setRootNode(value) {
				const instance = this;

				const rootNode = A.one(value);

				instance.rootNode = rootNode;

				return rootNode;
			},

			all(selector, root) {
				const instance = this;

				root = A.one(root) || instance.rootNode || A;

				return root.all(
					instance._formatSelectorNS(instance.NS, selector)
				);
			},

			byId(id) {
				const instance = this;

				return A.one('#' + A.Lang.String.prefix(instance.NS, id));
			},

			ns(str) {
				const instance = this;

				return Liferay.Util.ns(instance.NS, str);
			},

			one(selector, root) {
				const instance = this;

				root = A.one(root) || instance.rootNode || A;

				return root.one(
					instance._formatSelectorNS(instance.NS, selector)
				);
			},
		};

		Liferay.PortletBase = PortletBase;
	},
	'',
	{
		requires: ['aui-base'],
	}
);
