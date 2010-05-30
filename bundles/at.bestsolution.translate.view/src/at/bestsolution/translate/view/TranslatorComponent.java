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

import javax.inject.Inject;

import org.eclipse.core.databinding.beans.PojoProperties;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.core.databinding.property.list.IListProperty;
import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.e4.core.di.annotations.Optional;
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

import at.bestsolution.translate.services.ITranslator;
import at.bestsolution.translate.services.ITranslatorProvider;
import at.bestsolution.translate.services.ITranslator.TranslationLanguage;

public class TranslatorComponent {
	private Text term;
	private ComboViewer translator;

	@Inject
	public TranslatorComponent(Composite parent) {
		createUI(parent);
	}
	
	@Inject
	void setTranslationProvider(@Optional ITranslatorProvider provider) {
		if( provider == null ) {
			if( ! translator.getControl().isDisposed() ) {
				translator.setInput(new WritableList());
			}
		} else {
			translator.setInput(provider.getTranslators());
		}
	}
	
	private void createUI(Composite parent) {
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

		translator = new ComboViewer(parent);
		translator.getControl().setLayoutData(
				new GridData(GridData.FILL_HORIZONTAL));
		translator.setContentProvider(new ObservableListContentProvider());
		translator.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(Object element) {
				return ((ITranslator) element).getName();
			}
		});

		l = new Label(parent, SWT.NONE);
		l.setText("Source-Language");

		final ComboViewer sourceLanguage = new ComboViewer(parent);
		sourceLanguage.getControl().setLayoutData(
				new GridData(GridData.FILL_HORIZONTAL));
		sourceLanguage.setContentProvider(new ObservableListContentProvider());
		sourceLanguage.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(Object element) {
				return ((TranslationLanguage) element).name;
			}
		});

		IListProperty fromProp = PojoProperties.list("languages");
		IValueProperty selectionProp = ViewerProperties.singleSelection();

		IObservableList input = fromProp.observeDetail(selectionProp
				.observe(translator));
		sourceLanguage.setInput(input);

		l = new Label(parent, SWT.NONE);
		l.setText("Target-Language");

		final ComboViewer targetLanguage = new ComboViewer(parent);
		targetLanguage.getControl().setLayoutData(
				new GridData(GridData.FILL_HORIZONTAL));
		targetLanguage.setContentProvider(new ObservableListContentProvider());
		targetLanguage.setLabelProvider(new LabelProvider());

		IListProperty targetsProp = PojoProperties.list("targets");

		input = targetsProp.observeDetail(selectionProp
				.observe(sourceLanguage));
		targetLanguage.setInput(input);
		
		
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
				IStructuredSelection sourceSelection = (IStructuredSelection) sourceLanguage
						.getSelection();
				IStructuredSelection targetSelection = (IStructuredSelection) targetLanguage
				.getSelection();
		

				if (!transSelection.isEmpty() && !sourceSelection.isEmpty()) {
					try {
						String trans = ((ITranslator) transSelection
								.getFirstElement()).translate(
								((TranslationLanguage) sourceSelection.getFirstElement()).name,
								(String)targetSelection.getFirstElement(),
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

	public void setFocus() {
		term.setFocus();
	}
}
