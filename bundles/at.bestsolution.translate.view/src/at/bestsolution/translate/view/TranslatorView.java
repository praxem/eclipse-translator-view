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

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.*;


public class TranslatorView extends ViewPart {

	public static final String ID = "at.bestsolution.translate.view.views.TranslatorView";

	private IEclipseContext context;
	private TranslatorComponent component;

	public TranslatorView() {
		
	}
	
	public void createPartControl(Composite parent) {
		IEclipseContextProvider p = (IEclipseContextProvider) getSite().getService(IEclipseContextProvider.class);
		context = p.getContext().createChild();
		context.set(Composite.class, parent);
		component = ContextInjectionFactory.make(TranslatorComponent.class, context);
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		component.setFocus();
	}
	
	@Override
	public void dispose() {
		context.dispose();
		super.dispose();
	}
}