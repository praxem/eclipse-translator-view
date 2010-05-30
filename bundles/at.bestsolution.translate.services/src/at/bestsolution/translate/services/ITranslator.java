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
package at.bestsolution.translate.services;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface ITranslator {
	public class TranslationLanguage {
		public final String name;
		public final List<String> targets;
		
		public TranslationLanguage(String name, List<String> targets) {
			this.name = name;
			this.targets = targets;
		}
		
		public List<String> getTargets() {
			return targets;
		}
	}

	public String getName();
	public TranslationLanguage[] getLanguages();

	public String translate(String from, String to, String term) throws InvocationTargetException;
}
