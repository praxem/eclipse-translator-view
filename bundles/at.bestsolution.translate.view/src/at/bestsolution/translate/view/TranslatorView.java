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

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.*;


public class TranslatorView extends ViewPart {

	public static final String ID = "at.bestsolution.translate.view.views.TranslatorView";

	private TranslatorComponent component = new TranslatorComponent();

	public void createPartControl(Composite parent) {
		component.createUI(parent);
	}

	
	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		component.setFocus();
	}
}