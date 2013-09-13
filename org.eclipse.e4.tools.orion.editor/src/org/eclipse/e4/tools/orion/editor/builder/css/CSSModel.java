package org.eclipse.e4.tools.orion.editor.builder.css;

import java.io.File;
import java.io.IOException;

import org.eclipse.e4.tools.orion.editor.builder.EditorModel;

public class CSSModel extends EditorModel {

	public CSSModel(File baseDir, String keywords) {
		super(baseDir, "css");
		setKeywords(keywords);
	}

	public CSSModel(String baseURL, String keywords) {
		super(baseURL, "css");
		setKeywords(keywords);
	}

	public CSSModel(String editorJsUrl, String editorCssUrl, String keywords) {
		super(editorJsUrl, editorCssUrl, "css");
		setKeywords(keywords);
	}

	public CSSModel(String keywords) throws IOException {
		super("css");
		setKeywords(keywords);
	}

	public void setKeywords(String keywords) {
		String swtContentAssist = new CSSEdit().generate(keywords);
		super.addScript(swtContentAssist);
	}

}
