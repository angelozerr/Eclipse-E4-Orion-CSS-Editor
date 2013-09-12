package org.eclipse.e4.tools.orion.swt.samples;

import java.io.File;
import java.io.IOException;

import org.eclipse.e4.tools.orion.editor.builder.OrionEditorModel;
import org.eclipse.e4.tools.orion.editor.builder.OrionEditorTemplate;
import org.eclipse.e4.tools.orion.editor.builder.css.CSSEdit;

public class OrionTemplateTest {

	public static void main(String[] args) throws IOException {
		OrionEditorTemplate builder = new OrionEditorTemplate();

		File f = new File("");

		OrionEditorModel context = new OrionEditorModel(f, "css");

		String keywords = "";
		String swtContentAssist = new CSSEdit().generate(keywords);
		context.addScript(swtContentAssist);

		context.getScripts();

		String s = builder.generate(context);
		System.err.println(s);

	}
}
