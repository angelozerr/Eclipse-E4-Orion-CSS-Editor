package org.eclipse.e4.tools.orion.editor.builder.js;

import java.io.File;

import org.eclipse.e4.tools.orion.editor.builder.OrionEditorModel;

public class JSModel extends OrionEditorModel {

	public JSModel(File baseDir) {
		super(baseDir, "js");
		this.addScript(new JSEdit().generate(null));
	}

	public JSModel(String baseURL) {
		super(baseURL, "js");
		this.addScript(new JSEdit().generate(null));
	}

}
