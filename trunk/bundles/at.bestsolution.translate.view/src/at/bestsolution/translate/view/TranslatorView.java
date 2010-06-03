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

import org.eclipse.e4.tools.compat.parts.DIViewPart;


public class TranslatorView extends DIViewPart<TranslatorComponent> {

	public static final String ID = "at.bestsolution.translate.view.views.TranslatorView";

	public TranslatorView() {
		super(TranslatorComponent.class);
	}
	

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		getComponent().setFocus();
	}
	
}