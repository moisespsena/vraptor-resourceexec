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

import br.com.caelum.vraptor.Lazy;
import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.RequestScoped;

import com.moisespsena.vraptor.listenerexecution.ExecutionStack;
import com.moisespsena.vraptor.listenerexecution.topological.ListenerOrder;
import com.moisespsena.vraptor.validatorlisteners.PreValidationViewListener;
import com.moisespsena.vraptor.validatorlisteners.PreValidationViewListenerExecutor;
import com.moisespsena.vraptor.validatorlisteners.PreValidationViewRender;

/**
 * @author Moises P. Sena (http://moisespsena.com)
 * @since 1.0 16/09/2011
 */
@PreValidationViewRender
@RequestScoped
@ListenerOrder
@Lazy
@Component
public class ResourceExecutionPreValidationListener implements
		PreValidationViewListener {

	public ResourceExecutionPreValidationListener() {
	}

	@Override
	public boolean accepts(final PreValidationViewListenerExecutor executor) {
		if (ResourceMethodExecutionStatic.isValid(executor.getRequestInfo()
				.getRequest())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void preViewRenderer(
			final ExecutionStack<PreValidationViewListener> stack,
			final PreValidationViewListenerExecutor executor) {
		executor.notViewRender();
	}
}
