/*******************************************************************************
 * Copyright (c) 2013 Angelo Zerr and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Angelo Zerr <angelo.zerr@gmail.com> - initial API and implementation
 *******************************************************************************/
package org.eclipse.e4.tools.orion.editor.builder.css;

import java.io.File;
import java.io.IOException;

import org.eclipse.e4.tools.orion.editor.builder.AbstractHTMLBuilder;

public class CSSBuilder extends AbstractHTMLBuilder {

	public CSSBuilder(CSSModel model) {
		super(model);
	}

	public CSSBuilder(File file, String keywords) {
		this(new CSSModel(file, keywords));
	}

	public CSSBuilder(String keywords) throws IOException {
		this(new CSSModel(keywords));
	}
}
