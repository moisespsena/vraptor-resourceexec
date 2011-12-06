/***
 * Copyright (c) 2011 Moises P. Sena - www.moisespsena.com
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/**
 * 
 */
package com.moisespsena.vraptor.resourceexec;

import javax.servlet.http.HttpServletRequest;

import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.core.DefaultInterceptorStack;
import br.com.caelum.vraptor.core.InterceptorHandlerFactory;
import br.com.caelum.vraptor.core.InterceptorStack;
import br.com.caelum.vraptor.core.MethodInfo;
import br.com.caelum.vraptor.interceptor.InterceptorRegistry;
import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.RequestScoped;
import br.com.caelum.vraptor.proxy.Proxifier;

import com.moisespsena.vraptor.advproxifier.AdvancedProxifierFactory;
import com.moisespsena.vraptor.flashparameters.FlashMessages;

/**
 * @author Moises P. Sena (http://moisespsena.com)
 * @since 1.0 20/09/2011
 */
@Component
@RequestScoped
public class UserResourceMethodExecution {
	private final FlashMessages flashMessages;
	private final InterceptorHandlerFactory interceptorHandlerFactory;
	private final MethodInfo methodInfo;
	private final AdvancedProxifierFactory proxifierFactory;
	private final InterceptorRegistry registry;
	private final ResourceExecResult resourceExecResult;
	private final HttpServletRequest servletRequest;
	private final Validator validator;

	public UserResourceMethodExecution(final MethodInfo methodInfo,
			final InterceptorRegistry registry,
			final AdvancedProxifierFactory proxifierFactory,
			final Validator validator, final FlashMessages flashMessages,
			final HttpServletRequest servletRequest,
			final InterceptorHandlerFactory interceptorHandlerFactory,
			final ResourceExecResult resourceExecResult) {
		this.methodInfo = methodInfo;
		this.registry = registry;
		this.proxifierFactory = proxifierFactory;
		this.validator = validator;
		this.flashMessages = flashMessages;
		this.servletRequest = servletRequest;
		this.interceptorHandlerFactory = interceptorHandlerFactory;
		this.resourceExecResult = resourceExecResult;
	}

	public <T> T delegate(final Class<T> resourceClass, final boolean renderView) {
		final Proxifier proxifier = proxifierFactory.getInstance();
		final DelegatorAdvancedMethodInvocation<T> methodInvocation = new DelegatorAdvancedMethodInvocation<T>(
				resourceClass, direct(), renderView);
		final T proxy = proxifier.proxify(resourceClass, methodInvocation);
		return proxy;
	}

	public ResourceMethodExecution direct() {
		// cria um novo stack para limpar a o lista de interceptors
		final InterceptorStack stack = new DefaultInterceptorStack(
				interceptorHandlerFactory);
		return new ResourceMethodExecution(servletRequest, methodInfo,
				validator, flashMessages, resourceExecResult,
				new UserResourceMethodStackExecution(stack, registry));
	}
}
