package org.eclipse.e4.tools.orion.editor.builder.css;

import java.io.File;

import org.eclipse.e4.tools.orion.editor.builder.AbstractOrionBuilder;

public class CSSBuilder extends AbstractOrionBuilder {

	public CSSBuilder(CSSModel model) {
		super(model);
		model.setKeywords("");
	}

	public CSSBuilder(File file) {
		this(new CSSModel(file));
	}

}
