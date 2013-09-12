package org.eclipse.e4.tools.orion.editor.builder.css;

import java.io.File;

import org.eclipse.e4.tools.orion.editor.builder.OrionEditorModel;

public class CSSModel extends OrionEditorModel {

	public CSSModel(File baseDir) {
		super(baseDir, "css");
	}

	public CSSModel(String baseURL) {
		super(baseURL, "css");
	}

	public void setKeywords(String keywords) {
		String swtContentAssist = new CSSEdit().generate(keywords);
		super.addScript(swtContentAssist);
	}

}
