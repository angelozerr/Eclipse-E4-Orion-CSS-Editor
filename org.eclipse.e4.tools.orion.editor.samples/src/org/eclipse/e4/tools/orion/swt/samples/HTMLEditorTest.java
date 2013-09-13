package org.eclipse.e4.tools.orion.swt.samples;

import java.io.File;
import java.io.IOException;

import org.eclipse.e4.tools.orion.editor.builder.HTMLEditor;
import org.eclipse.e4.tools.orion.editor.builder.EditorModel;
import org.eclipse.e4.tools.orion.editor.builder.css.CSSEdit;

public class HTMLEditorTest {

	public static void main(String[] args) throws IOException {
		HTMLEditor builder = new HTMLEditor();

		File f = new File("");

		EditorModel context = new EditorModel(f, "css");

		String keywords = "";
		String swtContentAssist = new CSSEdit().generate(keywords);
		context.addScript(swtContentAssist);

		context.getScripts();

		String s = builder.generate(context);
		System.err.println(s);

	}
}
