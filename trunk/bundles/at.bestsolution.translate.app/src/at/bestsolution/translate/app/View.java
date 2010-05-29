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
package at.bestsolution.translate.app;

import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;

public class View extends ViewPart {
	public static final String ID = "at.bestsolution.translate.app.view";

	private Text term;

	/**
	 * This is a callback that will allow us to create the viewer and initialize
	 * it.
	 */
	public void createPartControl(Composite parent) {
		GridLayout layout = new GridLayout(2, false);
		parent.setLayout(layout);

		Label l = new Label(parent, SWT.NONE);
		l.setLayoutData(new GridData(GridData.FILL, GridData.BEGINNING, true,
				false, 2, 1));
		l.setText("Translator");

		l = new Label(parent, SWT.SEPARATOR | SWT.HORIZONTAL);
		l.setLayoutData(new GridData(GridData.FILL, GridData.BEGINNING, true,
				false, 2, 1));

		l = new Label(parent, SWT.NONE);
		l.setText("Term");

		term = new Text(parent, SWT.BORDER);
		term.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		l = new Label(parent, SWT.NONE);
		l.setText("Translator");

		ComboViewer translator = new ComboViewer(parent);
		translator.getControl().setLayoutData(
				new GridData(GridData.FILL_HORIZONTAL));

		l = new Label(parent, SWT.NONE);
		l.setText("Language");

		ComboViewer language = new ComboViewer(parent);
		language.getControl().setLayoutData(
				new GridData(GridData.FILL_HORIZONTAL));

		Button b = new Button(parent, SWT.PUSH);
		b.setText("Translate");
		b.setLayoutData(new GridData(GridData.END, GridData.CENTER, false,
				false, 2, 1));

		l = new Label(parent, SWT.SEPARATOR | SWT.HORIZONTAL);
		l.setLayoutData(new GridData(GridData.FILL, GridData.CENTER, true,
				false, 2, 1));

		l = new Label(parent, SWT.NONE);
		l.setText("Translation");
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		term.setFocus();
	}
}