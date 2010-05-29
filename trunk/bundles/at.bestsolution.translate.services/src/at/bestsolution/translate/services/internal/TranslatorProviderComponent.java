package at.bestsolution.translate.services.internal;

import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.list.WritableList;

import at.bestsolution.translate.services.ITranslator;
import at.bestsolution.translate.services.ITranslatorProvider;

public class TranslatorProviderComponent implements ITranslatorProvider {
	private IObservableList list = new WritableList(); 
	
	public IObservableList getTranslators() {
		return list;
	}

	public void addTranslator(ITranslator translator) {
		synchronized (list) {
			list.add(translator);
		}
	}
	
	public void removeTranslator( ITranslator translator ) {
		synchronized (list) {
			list.remove(translator);
		}
	}
}