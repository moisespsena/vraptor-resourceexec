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

/**
 * @author Moises P. Sena (http://moisespsena.com)
 * @since 1.0 27/09/2011
 */
public class ResourceMethodExecutionStatic {
	private static final String ATTRIBUTE = ResourceMethodExecutionStatic.class
			.getPackage().getName() + ".marked";

	public static boolean isValid(final HttpServletRequest servletRequest) {
		final Object value = servletRequest.getAttribute(ATTRIBUTE);

		if (value == null) {
			return false;
		} else {
			return true;
		}
	}

	public static void mark(final HttpServletRequest servletRequest) {
		servletRequest.setAttribute(ATTRIBUTE, true);
	}

	public static void unmark(final HttpServletRequest servletRequest) {
		servletRequest.removeAttribute(ATTRIBUTE);
	}
}
