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
	'liferay-admin',
	(A) => {
		const Lang = A.Lang;

		const MAP_DATA_PARAMS = {
			classname: 'className',
		};

		const STR_CLICK = 'click';

		const STR_FORM = 'form';

		const STR_URL = 'url';

		const Admin = A.Component.create({
			ATTRS: {
				form: {
					setter: A.one,
					value: null,
				},

				redirectUrl: {
					validator: Lang.isString,
					value: null,
				},

				submitButton: {
					validator: Lang.isString,
					value: null,
				},

				url: {
					value: null,
				},
			},

			AUGMENTS: [Liferay.PortletBase],

			EXTENDS: A.Base,

			NAME: 'admin',

			prototype: {
				_addInputsFromData(data) {
					const instance = this;

					const form = instance.get(STR_FORM);

					// eslint-disable-next-line @liferay/aui/no-object
					const inputsArray = A.Object.map(data, (value, key) => {
						key = MAP_DATA_PARAMS[key] || key;

						const nsKey = instance.ns(key);

						return (
							'<input id="' +
							nsKey +
							'" name="' +
							nsKey +
							'" type="hidden" value="' +
							value +
							'" />'
						);
					});

					form.append(inputsArray.join(''));
				},

				_onSubmit(event) {
					const instance = this;

					const data = event.currentTarget.getData();
					const form = instance.get(STR_FORM);

					const redirect = instance.one('#redirect', form);

					if (redirect) {
						redirect.val(instance.get('redirectURL'));
					}

					instance._addInputsFromData(data);

					submitForm(form, instance.get(STR_URL));
				},

				bindUI() {
					const instance = this;

					instance._eventHandles.push(
						instance
							.get(STR_FORM)
							.delegate(
								STR_CLICK,
								A.bind('_onSubmit', instance),
								instance.get('submitButton')
							)
					);
				},

				destructor() {
					const instance = this;

					A.Array.invoke(instance._eventHandles, 'detach');

					instance._eventHandles = null;

					A.clearTimeout(instance._laterTimeout);
				},

				initializer() {
					const instance = this;

					instance._eventHandles = [];

					instance.bindUI();
				},
			},
		});

		Liferay.Portlet.Admin = Admin;
	},
	'',
	{
		requires: [
			'aui-io-plugin-deprecated',
			'io',
			'liferay-portlet-base',
			'querystring-parse',
		],
	}
);
