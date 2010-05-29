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