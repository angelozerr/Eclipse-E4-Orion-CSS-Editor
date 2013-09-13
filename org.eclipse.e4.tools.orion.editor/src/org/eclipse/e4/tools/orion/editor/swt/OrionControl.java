/*******************************************************************************
 * Copyright (c) 2013 Angelo Zerr and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Angelo Zerr <angelo.zerr@gmail.com> - initial API and implementation
 *******************************************************************************/
package org.eclipse.e4.tools.orion.editor.swt;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.e4.tools.orion.editor.builder.IHTMLBuilder;
import org.eclipse.e4.tools.orion.editor.internal.org.apache.commons.lang3.StringEscapeUtils;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.BrowserFunction;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Layout;

/**
 * SWT Orion Editor control.
 * 
 */
public class OrionControl extends Composite {

	private final Browser browser;
	private String textToBeSet;
	private boolean loaded;
	private boolean focusToBeSet;
	private List<IDirtyListener> listeners = new ArrayList<IDirtyListener>();

	/**
	 * 
	 * @param parent
	 * @param style
	 * @param builder
	 */
	public OrionControl(Composite parent, int style, IHTMLBuilder builder) {
		super(parent, style);
		super.setLayout(new FillLayout());
		this.browser = BrowserFactory.create(this, getBrowserStyle());
		// System.err.println(builder.getHTML());
		browser.setText(builder.getHTML());
		createBrowserFunctions();
	}

	protected void createBrowserFunctions() {
		new BrowserFunction(browser, "orion_onLoad") {
			public Object function(Object[] arguments) {
				onLoad();
				return null;
			}
		};
		new BrowserFunction(browser, "orion_dirty") {
			public Object function(Object[] arguments) {
				notifyDirtyListeners();
				return null;
			}
		};
	}

	protected Integer getBrowserStyle() {
		return null;
	}

	@Override
	public void setLayout(Layout layout) {
		throw new UnsupportedOperationException(
				"Cannot change internal layout of Editor");
	}

	// ------------------------- Load methods -----------------------

	protected void onLoad() {
		loaded = true;
		if (textToBeSet != null) {
			setText(textToBeSet);
			textToBeSet = null;
		}
		if (focusToBeSet) {
			browser.evaluate("editor.focus();");
			focusToBeSet = false;
		}
		//
		browser.evaluate("window.editor.addEventListener('DirtyChanged', orion_dirty, true)");
	}

	public boolean isLoaded() {
		return loaded;
	}

	// ------------------------- Text methods -----------------------

	/**
	 * 
	 * @param text
	 */
	public void setText(String text) {
		if (text == null || text.length() == 0) {
			text = "";
		}

		if (isLoaded()) {
			doSetText(text);
		} else {
			textToBeSet = text;
		}
	}

	protected void doSetText(String text) {
		String js = new StringBuilder("window.editor.setInput(null, null, \"")
				.append(StringEscapeUtils.escapeEcmaScript(text))
				.append("\", false );").toString();
		browser.evaluate(js);
	}

	public String getText() {
		if (!isLoaded()) {
			if (textToBeSet != null) {
				return textToBeSet;
			} else {
				throw new IllegalStateException("Editor not loaded");
			}
		}

		return doGetText();
	}

	private String doGetText() {
		return (String) browser.evaluate("return editor.getText();");
	}

	// ------------------------- Focus methods -----------------------

	@Override
	public boolean setFocus() {
		boolean result = browser.setFocus();
		if (result) {
			if (isLoaded()) {
				browser.evaluate("window.editor.focus();");
			} else {
				focusToBeSet = true;
			}
		}
		return result;
	}

	// ------------------------- Dirty methods -----------------------

	/**
	 * 
	 * @param l
	 */
	public void addDirtyListener(IDirtyListener l) {
		listeners.add(l);
	}

	private void notifyDirtyListeners() {
		Display.getCurrent().asyncExec(new Runnable() {
			public void run() {
				boolean dirty = isDirty();
				for (IDirtyListener l : listeners) {
					l.dirtyChanged(dirty);
				}
			}
		});
	}

	public void setDirty(boolean dirty) {
		String js = new StringBuilder("window.editor.setDirty(").append(dirty)
				.append(");").toString();
		browser.evaluate(js);
	}

	public boolean isDirty() {
		final Object rc = browser.evaluate("return window.editor.isDirty();");
		if (rc instanceof Boolean) {
			return (Boolean) rc;
		}
		return false;
	}
}
