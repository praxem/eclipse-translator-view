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

import at.bestsolution.translate.services.ITranslator;

import com.google.api.translate.Language;
import com.google.api.translate.Translate;

public class GoogleTranslator implements ITranslator {
	private static FromTo[] FROM_TOS;
	
	{
		ArrayList<FromTo> l = new ArrayList<FromTo>();
		for(Language fromLang : Language.values()) {
			if( fromLang == Language.AUTO_DETECT ) {
				continue;
			}
			
			for( Language toLanguage : Language.values() ) {
				if( toLanguage == Language.AUTO_DETECT ) {
					continue;
				}
				
				if( fromLang != toLanguage ) {
					l.add(new FromTo(fromLang.name(), toLanguage.name()));
				}
			}
		}
		FROM_TOS =  l.toArray(new FromTo[0]);
	}
	
	public String getName() {
		return "Google Translate";
	}

	public FromTo[] getFromTo() {
		return FROM_TOS;
	}

	public String translate(FromTo fromTo, String term) throws InvocationTargetException {
		try {
			Translate.setHttpReferrer("http://code.google.com/a/eclipselabs.org/p/eclipse-translator-view/");
			return Translate.execute(term, Language.valueOf(fromTo.from), Language.valueOf(fromTo.to));
		} catch (Exception e) {
			throw new InvocationTargetException(e);
		}
	}
}