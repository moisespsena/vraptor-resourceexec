/**
 * 
 */
package com.moisespsena.vraptor.resourceexec;

import java.util.HashSet;
import java.util.Set;

import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.RequestScoped;

/**
 * @author Moises P. Sena (http://moisespsena.com)
 * @since 1.0 25/11/2011
 */
@Component
@RequestScoped
public class ResourceExecResult {
	private final Set<PreResultGenerateListener> preResultGenerateListeners = new HashSet<PreResultGenerateListener>();
	private boolean resultGenerated = false;
	private boolean using = false;

	public void addPreResultGenerateListener(
			final PreResultGenerateListener listener) {
		preResultGenerateListeners.add(listener);
	}

	public void dispatchPreResultGenerateListeners() {
		for (final PreResultGenerateListener listener : preResultGenerateListeners) {
			listener.execute();
		}
	}

	public void generated() {
		resultGenerated = true;
	}

	public boolean isResultGenerated() {
		return resultGenerated;
	}

	public boolean isUsing() {
		return using;
	}

	public void setUsing(final boolean using) {
		this.using = using;
	}
}
