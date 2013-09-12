package org.eclipse.e4.tools.orion.editor.builder;

public class AbstractOrionBuilder implements IOrionBuilder {

	private final OrionEditorModel model;

	public AbstractOrionBuilder(OrionEditorModel model) {
		this.model = model;
	}

	public String getHTML() {
		OrionEditorTemplate template = new OrionEditorTemplate();
		return template.generate(model);
	}
}
