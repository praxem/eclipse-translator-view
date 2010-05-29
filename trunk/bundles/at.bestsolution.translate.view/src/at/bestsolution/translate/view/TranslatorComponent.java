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

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.databinding.beans.PojoProperties;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.property.list.IListProperty;
import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.jface.databinding.viewers.ViewerProperties;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

import at.bestsolution.translate.services.ITranslator;
import at.bestsolution.translate.services.ITranslatorProvider;
import at.bestsolution.translate.services.ITranslator.FromTo;

public class TranslatorComponent {
	private Text term;

	public TranslatorComponent() {

	}

	public void createUI(Composite parent) {
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

		IObservableList translatorsObs = getTranslators();

		final ComboViewer translator = new ComboViewer(parent);
		translator.getControl().setLayoutData(
				new GridData(GridData.FILL_HORIZONTAL));
		translator.setContentProvider(new ObservableListContentProvider());
		translator.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(Object element) {
				return ((ITranslator) element).getName();
			}
		});
		translator.setInput(translatorsObs);

		l = new Label(parent, SWT.NONE);
		l.setText("Language");

		final ComboViewer language = new ComboViewer(parent);
		language.getControl().setLayoutData(
				new GridData(GridData.FILL_HORIZONTAL));
		language.setContentProvider(new ObservableListContentProvider());
		language.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(Object element) {
				return ((FromTo) element).from + " - " + ((FromTo) element).to;
			}
		});

		IListProperty fromToProp = PojoProperties.list("fromTo");
		IValueProperty selectionProp = ViewerProperties.singleSelection();

		IObservableList input = fromToProp.observeDetail(selectionProp
				.observe(translator));
		language.setInput(input);

		Button b = new Button(parent, SWT.PUSH);
		b.setText("Translate");
		b.setLayoutData(new GridData(GridData.END, GridData.CENTER, false,
				false, 2, 1));

		l = new Label(parent, SWT.SEPARATOR | SWT.HORIZONTAL);
		l.setLayoutData(new GridData(GridData.FILL, GridData.CENTER, true,
				false, 2, 1));

		l = new Label(parent, SWT.NONE);
		l.setText("Translation");

		final Label translation = new Label(parent, SWT.NONE);
		translation.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		b.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				IStructuredSelection transSelection = (IStructuredSelection) translator
						.getSelection();
				IStructuredSelection fromToSelection = (IStructuredSelection) language
						.getSelection();

				if (!transSelection.isEmpty() && !fromToSelection.isEmpty()) {
					try {
						String trans = ((ITranslator) transSelection
								.getFirstElement()).translate(
								(FromTo) fromToSelection.getFirstElement(),
								term.getText());
						translation.setText(trans);
					} catch (InvocationTargetException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
	}

	private IObservableList getTranslators() {
		Bundle bundle = FrameworkUtil.getBundle(getClass());
		BundleContext context = bundle.getBundleContext();
		ServiceReference reference = context
				.getServiceReference(ITranslatorProvider.class.getName());
		ITranslatorProvider pv = (ITranslatorProvider) context
				.getService(reference);

		return pv.getTranslators();
	}

	public void setFocus() {
		term.setFocus();
	}
}
