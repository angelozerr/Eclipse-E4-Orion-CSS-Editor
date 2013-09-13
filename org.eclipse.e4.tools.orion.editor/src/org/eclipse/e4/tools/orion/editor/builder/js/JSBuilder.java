package org.eclipse.e4.tools.orion.editor.builder.js;

import java.io.File;

import org.eclipse.e4.tools.orion.editor.builder.AbstractHTMLBuilder;

public class JSBuilder extends AbstractHTMLBuilder {

	public JSBuilder(JSModel model) {
		super(model);
	}

	public JSBuilder(File file) {
		this(new JSModel(file));
	}

}
