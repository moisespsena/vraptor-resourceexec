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

import java.lang.reflect.Method;

import br.com.caelum.vraptor.core.MethodInfo;
import br.com.caelum.vraptor.proxy.SuperMethod;

import com.moisespsena.vraptor.advancedrequest.RequestMethodInfo;
import com.moisespsena.vraptor.advproxifier.AdvancedMethodInvocation;

/**
 * @author Moises P. Sena (http://moisespsena.com)
 * @since 1.0 21/09/2011
 */
public class DelegatorAdvancedMethodInvocation<T> implements
		AdvancedMethodInvocation<T, ResourceMethodExecution> {
	private final boolean renderView;
	private final ResourceMethodExecution resourceMethodExecution;
	private final Class<T> type;

	public DelegatorAdvancedMethodInvocation(final Class<T> type,
			final ResourceMethodExecution resourceMethodExecution,
			final boolean renderView) {
		this.resourceMethodExecution = resourceMethodExecution;
		this.type = type;
		this.renderView = renderView;
	}

	@Override
	public ResourceMethodExecution getSharedObject() {
		return resourceMethodExecution;
	}

	@Override
	public Object intercept(final T proxy, final Method method,
			final Object[] parameters, final SuperMethod superMethod) {

		final RequestMethodInfo requestMethodInfo = new RequestMethodInfo(type,
				method, parameters, renderView);

		final MethodInfo methodInfo = resourceMethodExecution
				.execute(requestMethodInfo);

		final Object result = methodInfo.getResult();

		return result;
	}

}