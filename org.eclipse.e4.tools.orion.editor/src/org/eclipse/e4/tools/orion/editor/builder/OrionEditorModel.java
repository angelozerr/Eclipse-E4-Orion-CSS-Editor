package org.eclipse.e4.tools.orion.editor.builder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class OrionEditorModel {

	private final String baseURL;
	private final String lang;
	private final Collection<String> scripts;

	public OrionEditorModel(String baseURL, String lang) {
		this.baseURL = baseURL;
		this.scripts = new ArrayList<String>();
		this.lang = lang;
	}

	public OrionEditorModel(File baseDir, String lang) {
		this(toURL(baseDir), lang);
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

	public String getBaseURL() {
		return baseURL;
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
