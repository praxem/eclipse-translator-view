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

public interface ITranslator {
	public class FromTo {
		public final String from;
		public final String to;

		public FromTo(String from, String to) {
			this.from = from;
			this.to = to;
		}
	}

	public String getName();
	public FromTo[] getFromTo();

	public String translate(FromTo fromTo, String term) throws InvocationTargetException;
}
