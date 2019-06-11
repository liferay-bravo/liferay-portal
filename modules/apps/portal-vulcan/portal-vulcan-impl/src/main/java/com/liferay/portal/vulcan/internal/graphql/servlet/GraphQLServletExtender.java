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

package com.liferay.portal.vulcan.internal.graphql.servlet;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.graphql.servlet.ServletData;
import com.liferay.portal.vulcan.internal.accept.language.AcceptLanguageImpl;

import graphql.annotations.GraphQLFieldDefinitionWrapper;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.processor.ProcessingElementsContainer;
import graphql.annotations.processor.graphQLProcessors.GraphQLInputProcessor;
import graphql.annotations.processor.graphQLProcessors.GraphQLOutputProcessor;
import graphql.annotations.processor.retrievers.GraphQLExtensionsHandler;
import graphql.annotations.processor.retrievers.GraphQLFieldRetriever;
import graphql.annotations.processor.retrievers.GraphQLInterfaceRetriever;
import graphql.annotations.processor.retrievers.GraphQLObjectHandler;
import graphql.annotations.processor.retrievers.GraphQLObjectInfoRetriever;
import graphql.annotations.processor.retrievers.GraphQLTypeRetriever;
import graphql.annotations.processor.retrievers.fieldBuilders.ArgumentBuilder;
import graphql.annotations.processor.retrievers.fieldBuilders.DeprecateBuilder;
import graphql.annotations.processor.retrievers.fieldBuilders.DescriptionBuilder;
import graphql.annotations.processor.retrievers.fieldBuilders.method.MethodNameBuilder;
import graphql.annotations.processor.retrievers.fieldBuilders.method.MethodTypeBuilder;
import graphql.annotations.processor.searchAlgorithms.BreadthFirstSearch;
import graphql.annotations.processor.searchAlgorithms.ParentalSearch;
import graphql.annotations.processor.typeFunctions.DefaultTypeFunction;
import graphql.annotations.processor.util.NamingKit;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLOutputType;
import graphql.schema.GraphQLSchema;

import graphql.servlet.GraphQLContext;
import graphql.servlet.SimpleGraphQLHttpServlet;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Dictionary;
import java.util.Map;
import java.util.Optional;

import javax.servlet.Servlet;
import javax.servlet.http.HttpServletRequest;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.http.context.ServletContextHelper;
import org.osgi.service.http.whiteboard.HttpWhiteboardConstants;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Preston Crary
 */
@Component(immediate = true, service = {})
public class GraphQLServletExtender {

	@Activate
	protected void activate(BundleContext bundleContext) {
		GraphQLFieldRetriever graphQLFieldRetriever =
			new LiferayGraphQLFieldRetriever();
		GraphQLInterfaceRetriever graphQLInterfaceRetriever =
			new GraphQLInterfaceRetriever();

		GraphQLObjectInfoRetriever graphQLObjectInfoRetriever =
			new GraphQLObjectInfoRetriever();

		BreadthFirstSearch breadthFirstSearch = new BreadthFirstSearch(
			graphQLObjectInfoRetriever);
		ParentalSearch parentalSearch = new ParentalSearch(
			graphQLObjectInfoRetriever);

		GraphQLTypeRetriever graphQLTypeRetriever = new GraphQLTypeRetriever() {
			{
				setExtensionsHandler(
					new GraphQLExtensionsHandler() {
						{
							setFieldRetriever(graphQLFieldRetriever);
							setFieldSearchAlgorithm(parentalSearch);
							setGraphQLObjectInfoRetriever(
								graphQLObjectInfoRetriever);
							setMethodSearchAlgorithm(breadthFirstSearch);
						}
					});
				setFieldSearchAlgorithm(parentalSearch);
				setGraphQLFieldRetriever(graphQLFieldRetriever);
				setGraphQLInterfaceRetriever(graphQLInterfaceRetriever);
				setGraphQLObjectInfoRetriever(graphQLObjectInfoRetriever);
				setMethodSearchAlgorithm(breadthFirstSearch);
			}
		};

		// Handle Circular reference between GraphQLInterfaceRetriever and
		// GraphQLTypeRetriever

		graphQLInterfaceRetriever.setGraphQLTypeRetriever(graphQLTypeRetriever);

		_defaultTypeFunction = new DefaultTypeFunction(
			new GraphQLInputProcessor() {
				{
					setGraphQLTypeRetriever(graphQLTypeRetriever);
				}
			},
			new GraphQLOutputProcessor() {
				{
					setGraphQLTypeRetriever(graphQLTypeRetriever);
				}
			});
		_graphQLObjectHandler = new GraphQLObjectHandler() {
			{
				setTypeRetriever(graphQLTypeRetriever);
			}
		};

		_serviceTracker = new ServiceTracker<>(
			bundleContext, ServletData.class,
			new ServletDataServiceTrackerCustomizer(bundleContext));

		_serviceTracker.open();
	}

	@Deactivate
	protected void deactivate() {
		_serviceTracker.close();
	}

	@Reference
	private CompanyLocalService _companyLocalService;

	private DefaultTypeFunction _defaultTypeFunction;
	private GraphQLObjectHandler _graphQLObjectHandler;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

	private ServiceTracker<?, ?> _serviceTracker;

	private class LiferayGraphQLFieldRetriever extends GraphQLFieldRetriever {

		@Override
		public GraphQLFieldDefinition getField(
			Method method,
			ProcessingElementsContainer processingElementsContainer) {

			GraphQLFieldDefinition.Builder builder =
				GraphQLFieldDefinition.newFieldDefinition();

			MethodTypeBuilder methodTypeBuilder = new MethodTypeBuilder(
				method, processingElementsContainer.getDefaultTypeFunction(),
				processingElementsContainer, false);

			GraphQLOutputType graphQLOutputType =
				(GraphQLOutputType)methodTypeBuilder.build();

			ArgumentBuilder argumentBuilder = new ArgumentBuilder(
				method, processingElementsContainer.getDefaultTypeFunction(),
				builder, processingElementsContainer, graphQLOutputType);

			builder.argument(argumentBuilder.build());

			builder.dataFetcher(new LiferayMethodDataFetcher(method));

			DeprecateBuilder deprecateBuilder = new DeprecateBuilder(method);

			builder.deprecate(deprecateBuilder.build());

			DescriptionBuilder descriptionBuilder = new DescriptionBuilder(
				method);

			builder.description(descriptionBuilder.build());

			MethodNameBuilder methodNameBuilder = new MethodNameBuilder(method);

			builder.name(methodNameBuilder.build());

			builder.type(graphQLOutputType);

			return new GraphQLFieldDefinitionWrapper(builder.build());
		}

	}

	private class LiferayMethodDataFetcher implements DataFetcher<Object> {

		@Override
		public Object get(DataFetchingEnvironment dataFetchingEnvironment) {
			try {
				Class<?> clazz = _method.getDeclaringClass();

				Object instance = clazz.newInstance();

				GraphQLContext graphQLContext =
					dataFetchingEnvironment.getContext();

				Optional<HttpServletRequest> httpServletRequestOptional =
					graphQLContext.getHttpServletRequest();

				for (Field field : clazz.getDeclaredFields()) {
					if (Modifier.isStatic(field.getModifiers()) ||
						Modifier.isFinal(field.getModifiers())) {

						continue;
					}

					Class<?> fieldType = field.getType();

					if (fieldType.isAssignableFrom(AcceptLanguage.class)) {
						field.setAccessible(true);

						field.set(
							instance,
							new AcceptLanguageImpl(
								httpServletRequestOptional.orElse(null),
								_language, _portal));
					}
					else if (fieldType.isAssignableFrom(Company.class)) {
						field.setAccessible(true);

						field.set(
							instance,
							_companyLocalService.getCompany(
								CompanyThreadLocal.getCompanyId()));
					}
				}

				Parameter[] parameters = _method.getParameters();

				Map<String, Object> arguments =
					dataFetchingEnvironment.getArguments();

				Object[] args = new Object[arguments.size()];

				for (int i = 0; i < args.length; i++) {
					Parameter parameter = parameters[i];

					String parameterName = null;

					GraphQLName graphQLName = parameter.getAnnotation(
						GraphQLName.class);

					if (graphQLName == null) {
						parameterName = NamingKit.toGraphqlName(
							parameter.getName());
					}
					else {
						parameterName = NamingKit.toGraphqlName(
							graphQLName.value());
					}

					args[i] = arguments.get(parameterName);
				}

				return _method.invoke(instance, args);
			}
			catch (PortalException | ReflectiveOperationException e) {
				throw new RuntimeException(e);
			}
		}

		private LiferayMethodDataFetcher(Method method) {
			_method = method;
		}

		private final Method _method;

	}

	private class ServletDataServiceTrackerCustomizer
		implements ServiceTrackerCustomizer
			<ServletData, Collection<ServiceRegistration<?>>> {

		@Override
		public Collection<ServiceRegistration<?>> addingService(
			ServiceReference<ServletData> serviceReference) {

			// Schema

			GraphQLSchema.Builder schemaBuilder = GraphQLSchema.newSchema();

			ServletData servletData = _bundleContext.getService(
				serviceReference);

			Object mutation = servletData.getMutation();

			ProcessingElementsContainer processingElementsContainer =
				new ProcessingElementsContainer(_defaultTypeFunction);

			schemaBuilder.mutation(
				_graphQLObjectHandler.getObject(
					mutation.getClass(), processingElementsContainer));

			Object query = servletData.getQuery();

			schemaBuilder.query(
				_graphQLObjectHandler.getObject(
					query.getClass(), processingElementsContainer));

			Dictionary<String, Object> properties = new HashMapDictionary<>();

			Class<? extends ServletData> clazz = servletData.getClass();

			String path = servletData.getPath();

			String servletContextName = path.split("/")[1];

			properties.put(
				HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_NAME,
				clazz.getName());

			properties.put(
				HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_PATTERN, "/*");

			properties.put(
				HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_SELECT,
				servletContextName);

			Dictionary<String, Object> helperProperties =
				new HashMapDictionary<>();

			helperProperties.put(
				HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_NAME,
				servletContextName);
			helperProperties.put(
				HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_PATH, path);
			helperProperties.put(
				HttpWhiteboardConstants.HTTP_WHITEBOARD_FILTER_SERVLET,
				clazz.getName());

			Collection<ServiceRegistration<?>> serviceRegistrations =
				new ArrayList<>();

			serviceRegistrations.add(
				_bundleContext.registerService(
					ServletContextHelper.class,
					new ServletContextHelper(_bundleContext.getBundle()) {
					},
					helperProperties));

			// Servlet

			SimpleGraphQLHttpServlet.Builder servletBuilder =
				SimpleGraphQLHttpServlet.newBuilder(schemaBuilder.build());

			Servlet servlet = servletBuilder.build();

			serviceRegistrations.add(
				_bundleContext.registerService(
					Servlet.class, servlet, properties));

			return serviceRegistrations;
		}

		@Override
		public void modifiedService(
			ServiceReference<ServletData> serviceReference,
			Collection<ServiceRegistration<?>> serviceRegistrations) {
		}

		@Override
		public void removedService(
			ServiceReference<ServletData> serviceReference,
			Collection<ServiceRegistration<?>> serviceRegistrations) {

			for (ServiceRegistration<?> serviceRegistration :
					serviceRegistrations) {

				serviceRegistration.unregister();
			}

			_bundleContext.ungetService(serviceReference);
		}

		private ServletDataServiceTrackerCustomizer(
			BundleContext bundleContext) {

			_bundleContext = bundleContext;
		}

		private final BundleContext _bundleContext;

	}

}