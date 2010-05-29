package at.bestsolution.translate.view;


import java.lang.reflect.InvocationTargetException;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.*;
import org.eclipse.core.databinding.beans.PojoProperties;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.property.list.IListProperty;
import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.jface.databinding.viewers.ViewerProperties;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.SWT;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

import at.bestsolution.translate.services.ITranslator;
import at.bestsolution.translate.services.ITranslatorProvider;
import at.bestsolution.translate.services.ITranslator.FromTo;


public class TranslatorView extends ViewPart {

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "at.bestsolution.translate.view.views.TranslatorView";


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
		
		IObservableList translatorsObs = getTranslators();
		
		final ComboViewer translator = new ComboViewer(parent);
		translator.getControl().setLayoutData(
				new GridData(GridData.FILL_HORIZONTAL));
		translator.setContentProvider(new ObservableListContentProvider());
		translator.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(Object element) {
				return ((ITranslator)element).getName();
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
				return ((FromTo)element).from + " - " + ((FromTo)element).to;
			}
		});
		
		IListProperty fromToProp = PojoProperties.list("fromTo");
		IValueProperty selectionProp = ViewerProperties.singleSelection();
		
		IObservableList input = fromToProp.observeDetail(selectionProp.observe(translator));
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
				IStructuredSelection transSelection =  (IStructuredSelection) translator.getSelection();
				IStructuredSelection fromToSelection = (IStructuredSelection) language.getSelection();
				
				if( ! transSelection.isEmpty() && ! fromToSelection.isEmpty() ) {
					try {
						String trans = ((ITranslator)transSelection.getFirstElement()).translate((FromTo) fromToSelection.getFirstElement(), term.getText());
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
		ServiceReference reference = context.getServiceReference(ITranslatorProvider.class.getName());
		ITranslatorProvider pv = (ITranslatorProvider) context.getService(reference);
		
		return pv.getTranslators();
	}
	
	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		term.setFocus();
	}
}