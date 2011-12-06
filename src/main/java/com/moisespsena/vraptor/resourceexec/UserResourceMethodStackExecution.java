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

import br.com.caelum.vraptor.VRaptorException;
import br.com.caelum.vraptor.core.InterceptorStack;
import br.com.caelum.vraptor.core.RequestExecution;
import br.com.caelum.vraptor.interceptor.Interceptor;
import br.com.caelum.vraptor.interceptor.InterceptorRegistry;

/**
 * @author Moises P. Sena (http://moisespsena.com)
 * @since 1.0 21/09/2011
 */
public class UserResourceMethodStackExecution implements RequestExecution {
	private final InterceptorRegistry registry;
	private final InterceptorStack stack;

	public UserResourceMethodStackExecution(final InterceptorStack stack,
			final InterceptorRegistry registry) {
		this.stack = stack;
		this.registry = registry;
	}

	@Override
	public void execute() throws VRaptorException {
		for (final Class<? extends Interceptor> interceptor : registry.all()) {
			stack.add(interceptor);
		}
		stack.next(null, null);
	}
}