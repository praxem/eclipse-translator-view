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

import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.internal.services.IWorkbenchLocationService;
import org.eclipse.ui.services.AbstractServiceFactory;
import org.eclipse.ui.services.IServiceLocator;

@SuppressWarnings("restriction")
public class DIContextServiceFactory extends AbstractServiceFactory {

	@Override
	public Object create(@SuppressWarnings("rawtypes") Class serviceInterface, IServiceLocator parentLocator,
			IServiceLocator locator) {
		if( ! IEclipseContextProvider.class.equals(serviceInterface) ) {
			return null;
		}
		
		Object o = parentLocator.getService(serviceInterface);
		
		IWorkbenchLocationService wls = (IWorkbenchLocationService) locator.getService(IWorkbenchLocationService.class);
		final IWorkbenchWindow window = wls.getWorkbenchWindow();
		final IWorkbenchPartSite site = wls.getPartSite();

		if( window == null && site == null ) {
			return new IEclipseContextProviderImpl();
		} else if( o != null && site == null ) {
			return new IEclipseContextProviderImpl((IEclipseContextProvider) o);
		}
		
		return o;
	}

}
