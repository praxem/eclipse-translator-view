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
package at.bestsolution.translate.services.google;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import at.bestsolution.translate.services.ITranslator;

import com.google.api.translate.Language;
import com.google.api.translate.Translate;

public class GoogleTranslator implements ITranslator {
	private static TranslationLanguage[] LANGUAGES;
	
	static {
		ArrayList<TranslationLanguage> l = new ArrayList<TranslationLanguage>();
		for(Language fromLang : Language.values()) {
			if( fromLang == Language.AUTO_DETECT ) {
				continue;
			}
			
			List<String> targets = new ArrayList<String>();
			for( Language toLanguage : Language.values() ) {
				if( toLanguage == Language.AUTO_DETECT ) {
					continue;
				}
				
				if( fromLang != toLanguage ) {
					targets.add(toLanguage.name());
				}
			}
			
			l.add(new TranslationLanguage(fromLang.name(), targets));
		}
		LANGUAGES =  l.toArray(new TranslationLanguage[0]);
	}
	
	public String getName() {
		return "Google Translate";
	}

	public TranslationLanguage[] getLanguages() {
		return LANGUAGES;
	}

	public String translate(String from, String to, String term) throws InvocationTargetException {
		try {
			Translate.setHttpReferrer("http://code.google.com/a/eclipselabs.org/p/eclipse-translator-view/");
			return Translate.execute(term, Language.valueOf(from), Language.valueOf(to));
		} catch (Exception e) {
			throw new InvocationTargetException(e);
		}
	}
}