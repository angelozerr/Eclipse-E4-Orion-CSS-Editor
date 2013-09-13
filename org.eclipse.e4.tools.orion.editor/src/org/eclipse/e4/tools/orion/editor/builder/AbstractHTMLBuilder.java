package org.eclipse.e4.tools.orion.editor.builder;

public class AbstractHTMLBuilder implements IHTMLBuilder {

	private final String html;

	public AbstractHTMLBuilder(EditorModel model) {
		this.html = generateHTML(model);
	}

	public String getHTML() {
		return html;
	}

	private String generateHTML(EditorModel model) {
		HTMLEditor template = new HTMLEditor();
		return template.generate(model);
	}
}
