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

import org.springframework.beans.factory.NoSuchBeanDefinitionException;

import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.core.MethodInfo;

import com.moisespsena.vraptor.advancedrequest.RequestMethodInfo;
import com.moisespsena.vraptor.advancedrequest.RequestMethodInfoStatic;
import com.moisespsena.vraptor.advancedrequest.RequestResult;
import com.moisespsena.vraptor.advancedrequest.RequestResultImpl;
import com.moisespsena.vraptor.advancedrequest.ResourceMethodResult;
import com.moisespsena.vraptor.advancedrequest.ResourceMethodResultImpl;
import com.moisespsena.vraptor.flashparameters.FlashMessages;
import com.moisespsena.vraptor.modularvalidator.CategorizedMessagesImpl;

/**
 * @author Moises P. Sena (http://moisespsena.com)
 * @since 1.0 21/09/2011
 */
public class ResourceMethodExecution {
	private final FlashMessages flashMessages;
	private final MethodInfo methodInfo;
	private RequestResult requestResult;
	private final ResourceExecResult resourceExecResult;
	private final HttpServletRequest servletRequest;
	private final UserResourceMethodStackExecution stackExecution;
	private final Validator validator;

	public ResourceMethodExecution(final HttpServletRequest servletRequest,
			final MethodInfo methodInfo, final Validator validator,
			final FlashMessages flashMessages,
			final ResourceExecResult resourceExecResult,
			final UserResourceMethodStackExecution stackExecution) {
		this.methodInfo = methodInfo;
		this.validator = validator;
		this.flashMessages = flashMessages;
		this.stackExecution = stackExecution;
		this.servletRequest = servletRequest;
		this.resourceExecResult = resourceExecResult;
	}

	public MethodInfo execute(final RequestMethodInfo requestMethodInfo) {
		RequestMethodInfoStatic.hydatesRequest(servletRequest,
				requestMethodInfo);
		ResourceMethodExecutionStatic.mark(servletRequest);
		// clear methodInfo result
		methodInfo.setResult(null);
		// informa que ResourceExec sera utilizado
		resourceExecResult.setUsing(true);

		try {
			stackExecution.execute();

			if (validator.hasErrors()) {
				requestResult = RequestResultImpl
						.preConditionFailed(new CategorizedMessagesImpl(
								flashMessages.getMessages()));
			} else {
				resourceExecResult.dispatchPreResultGenerateListeners();

				final ResourceMethodResult resourceMethodResult = new ResourceMethodResultImpl(
						methodInfo.getResult());
				requestResult = RequestResultImpl
						.result(resourceMethodResult,
								new CategorizedMessagesImpl(flashMessages
										.getMessages()));
			}
		} catch (final NoSuchBeanDefinitionException e) {
			if (e.getBeanType().equals(requestMethodInfo.getResourceClass())) {
				requestResult = RequestResultImpl.notFound();
			} else {
				throw e;
			}
		} finally {
			ResourceMethodExecutionStatic.unmark(servletRequest);
			RequestMethodInfoStatic.dehydatesRequest(servletRequest);
			resourceExecResult.generated();
		}

		return methodInfo;
	}

	public RequestResult getRequestResult() {
		return requestResult;
	}
}
