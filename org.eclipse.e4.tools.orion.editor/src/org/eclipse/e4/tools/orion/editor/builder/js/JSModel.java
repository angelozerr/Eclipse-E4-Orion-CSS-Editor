package org.eclipse.e4.tools.orion.editor.builder.js;

import java.io.File;

import org.eclipse.e4.tools.orion.editor.builder.EditorModel;

public class JSModel extends EditorModel {

	public JSModel(File baseDir) {
		super(baseDir, "js");
		this.addScript(new JSEdit().generate(null));
	}

	public JSModel(String baseURL) {
		super(baseURL, "js");
		this.addScript(new JSEdit().generate(null));
	}

}
