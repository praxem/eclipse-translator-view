/*******************************************************************************
 * Copyright (c) 2010 BestSolution.at and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Tom Schindl <tom.schindl@bestsolution.at> - initial API and implementation
 *******************************************************************************/
package at.bestsolution.translate.view;

import org.eclipse.e4.core.contexts.EclipseContextFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

public class IEclipseContextProviderImpl implements IEclipseContextProvider {
	private IEclipseContext context;
	
	public IEclipseContextProviderImpl(IEclipseContextProvider parent) {
		this.context = parent.getContext().createChild();
	}

	public IEclipseContextProviderImpl() {
		Bundle bundle = FrameworkUtil.getBundle(IEclipseContextProviderImpl.class);
		BundleContext bundleContext = bundle.getBundleContext();
		IEclipseContext serviceContext = EclipseContextFactory.getServiceContext(bundleContext);

		context = serviceContext.createChild("WorkbenchContext"); //$NON-NLS-1$
	}
	
	public IEclipseContext getContext() {
		return context;
	}
}
