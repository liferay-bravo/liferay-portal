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

import ClayLoadingIndicator from '@clayui/loading-indicator';
import {openToast} from 'frontend-js-web';
import React, {useEffect, useMemo, useState} from 'react';

import {ALLOWED_INPUT_TYPES} from '../../../../../../app/config/constants/allowedInputTypes';
import {FREEMARKER_FRAGMENT_ENTRY_PROCESSOR} from '../../../../../../app/config/constants/freemarkerFragmentEntryProcessor';
import {
	useDispatch,
	useSelector,
	useSelectorCallback,
	useSelectorRef,
} from '../../../../../../app/contexts/StoreContext';
import selectFormConfiguration from '../../../../../../app/selectors/selectFormConfiguration';
import selectFragmentEntryLink from '../../../../../../app/selectors/selectFragmentEntryLink';
import selectLanguageId from '../../../../../../app/selectors/selectLanguageId';
import selectSegmentsExperienceId from '../../../../../../app/selectors/selectSegmentsExperienceId';
import InfoItemService from '../../../../../../app/services/InfoItemService';
import updateEditableValues from '../../../../../../app/thunks/updateEditableValues';
import {deepEqual} from '../../../../../../app/utils/checkDeepEqual';
import {setIn} from '../../../../../../app/utils/setIn';
import Collapse from '../../../../../../common/components/Collapse';
import MappingFieldSelector from '../../../../../../common/components/MappingFieldSelector';
import {FieldSet} from './FieldSet';
import {FragmentGeneralPanel} from './FragmentGeneralPanel';

const FIELD_ID_CONFIGURATION_KEY = 'inputFieldId';
const HELP_TEXT_CONFIGURATION_KEY = 'inputHelpText';
const SHOW_HELP_TEXT_CONFIGURATION_KEY = 'inputShowHelpText';

const INPUT_COMMON_CONFIGURATION = [
	{
		defaultValue: false,
		label: Liferay.Language.get('mark-as-required'),
		name: 'inputRequired',
		type: 'checkbox',
	},
	{
		defaultValue: true,
		label: Liferay.Language.get('show-label'),
		name: 'inputShowLabel',
		type: 'checkbox',
		typeOptions: {displayType: 'toggle'},
	},
	{
		defaultValue: '',
		label: Liferay.Language.get('label'),
		localizable: true,
		name: 'inputLabel',
		type: 'text',
	},
	{
		defaultValue: true,
		label: Liferay.Language.get('show-help-text'),
		name: SHOW_HELP_TEXT_CONFIGURATION_KEY,
		type: 'checkbox',
		typeOptions: {displayType: 'toggle'},
	},
	{
		defaultValue: '',
		label: Liferay.Language.get('help-text'),
		localizable: true,
		name: HELP_TEXT_CONFIGURATION_KEY,
		type: 'text',
	},
];

export function FormInputGeneralPanel({item}) {
	const dispatch = useDispatch();
	const languageId = useSelector(selectLanguageId);
	const segmentsExperienceId = useSelector(selectSegmentsExperienceId);

	const fragmentEntryLinkRef = useSelectorRef((state) =>
		selectFragmentEntryLink(state, item)
	);

	const configurationValues = useSelectorCallback(
		(state) =>
			selectFragmentEntryLink(state, item).editableValues[
				FREEMARKER_FRAGMENT_ENTRY_PROCESSOR
			] || {},
		[item.itemId],
		deepEqual
	);

	const fields = useMemo(() => {
		let nextFields = INPUT_COMMON_CONFIGURATION;

		if (configurationValues[SHOW_HELP_TEXT_CONFIGURATION_KEY] === false) {
			nextFields = nextFields.filter(
				(field) => field.name !== HELP_TEXT_CONFIGURATION_KEY
			);
		}

		return nextFields;
	}, [configurationValues]);

	const handleValueSelect = (key, value) =>
		dispatch(
			updateEditableValues({
				editableValues: setIn(
					fragmentEntryLinkRef.current.editableValues,
					[FREEMARKER_FRAGMENT_ENTRY_PROCESSOR, key],
					value
				),
				fragmentEntryLinkId:
					fragmentEntryLinkRef.current.fragmentEntryLinkId,
				languageId,
				segmentsExperienceId,
			})
		);

	return (
		<>
			<div className="mb-3">
				<Collapse
					label={Liferay.Language.get('form-input-options')}
					open
				>
					<FormInputMappingOptions
						configurationValues={configurationValues}
						item={item}
						onValueSelect={handleValueSelect}
					/>

					<FieldSet
						fields={fields}
						item={item}
						label=""
						languageId={languageId}
						onValueSelect={handleValueSelect}
						values={configurationValues}
					/>
				</Collapse>
			</div>

			<FragmentGeneralPanel item={item} />
		</>
	);
}

function FormInputMappingOptions({configurationValues, item, onValueSelect}) {
	const [fields, setFields] = useState(null);

	const formConfiguration = useSelectorCallback(
		(state) => selectFormConfiguration(item, state.layoutData),
		[item.itemId]
	);

	const inputType = useSelectorCallback(
		(state) => {
			const element = document.createElement('div');
			element.innerHTML = selectFragmentEntryLink(state, item).content;

			if (element.querySelector('select')) {
				return 'select';
			}
			else if (element.querySelector('textarea')) {
				return 'textarea';
			}

			return element.querySelector('input')?.type || 'text';
		},
		[item.itemId]
	);

	useEffect(() => {
		const {classNameId, classTypeId} = formConfiguration || {};

		if (!classNameId || !classTypeId) {
			return;
		}

		InfoItemService.getAvailableStructureMappingFields({
			classNameId,
			classTypeId,
			onNetworkStatus: () => {},
		})
			.then((nextFields) =>
				setFields(
					nextFields
						.map((fieldset) => ({
							...fieldset,
							fields: fieldset.fields.filter((field) =>
								ALLOWED_INPUT_TYPES[field.type]?.includes(
									inputType
								)
							),
						}))
						.filter((fieldset) => fieldset.fields.length)
				)
			)
			.catch((error) => {
				if (process.env.NODE_ENV === 'development') {
					console.error(error);
				}

				openToast({
					message: Liferay.Language.get(
						'an-unexpected-error-occurred'
					),
					type: 'danger',
				});

				setFields([]);
			});
	}, [formConfiguration, inputType]);

	if (!formConfiguration?.classNameId || !formConfiguration?.classTypeId) {
		return null;
	}

	return fields ? (
		<MappingFieldSelector
			fieldType={inputType}
			fields={fields}
			onValueSelect={(event) =>
				onValueSelect(FIELD_ID_CONFIGURATION_KEY, event.target.value)
			}
			value={configurationValues[FIELD_ID_CONFIGURATION_KEY] || ''}
		/>
	) : (
		<ClayLoadingIndicator />
	);
}
