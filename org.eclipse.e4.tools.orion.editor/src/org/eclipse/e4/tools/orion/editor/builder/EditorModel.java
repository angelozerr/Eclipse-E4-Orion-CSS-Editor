package org.eclipse.e4.tools.orion.editor.builder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.e4.tools.orion.editor.Activator;

public class EditorModel {

	public static final String BUILT_EDITOR_JS = "/web/built-editor.js";
	public static final String BUILT_EDITOR_CSS = "/web/built-editor.css";

	private final String editorCssUrl;
	private final String editorJsUrl;
	private final String lang;
	private final Collection<String> scripts;

	public EditorModel(String editorJsUrl, String editorCssUrl, String lang) {
		this.editorCssUrl = editorCssUrl;
		this.editorJsUrl = editorJsUrl;
		this.scripts = new ArrayList<String>();
		this.lang = lang;
	}

	public EditorModel(String baseURL, String lang) {
		this(baseURL + BUILT_EDITOR_JS, baseURL + BUILT_EDITOR_CSS, lang);
	}

	public EditorModel(File baseDir, String lang) {
		this(toURL(baseDir), lang);
	}

	public EditorModel(String lang) throws IOException {
		this(FileLocator.toFileURL(
				Activator.getContext().getBundle().getEntry(BUILT_EDITOR_JS))
				.toExternalForm(), FileLocator.toFileURL(
				Activator.getContext().getBundle().getEntry(BUILT_EDITOR_CSS))
				.toExternalForm(), lang);
	}

	private static String toURL(File file) {
		try {
			return new StringBuilder("file://").append(file.getCanonicalPath())
					.toString();
		} catch (IOException e) {
			return new StringBuilder("file://").append(file.getPath())
					.toString();
		}
	}

	public String getEditorCssUrl() {
		return editorCssUrl;
	}

	public String getEditorJsUrl() {
		return editorJsUrl;
	}

	public void addScript(String script) {
		this.scripts.add(script);
	}

	public Collection<String> getScripts() {
		return scripts;
	}

	public String getLang() {
		return lang;
	}
}
