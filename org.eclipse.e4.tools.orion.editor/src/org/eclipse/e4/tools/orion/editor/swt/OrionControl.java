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

	/**
	 * The SWT Browser which loads the HTML Orion editor.
	 */
	private final Browser browser;

	/**
	 * True when Orion editor is loaded and flase otherwise.
	 */
	private boolean loaded;

	/**
	 * Dirty listener.
	 */
	private List<IDirtyListener> listeners = null;

	/**
	 * Text to set for Orion the editor although the editor is not loaded.
	 */
	private String textToBeSet;

	/**
	 * True if focus should be set for Orion the editor although the editor is
	 * not loaded.
	 */
	private Boolean focusToBeSet;

	/**
	 * Not null if dirty should be set for Orion the editor although the editor
	 * is not loaded.
	 */
	private Boolean dirtyToBeSet;

	/**
	 * Orion control constructor.
	 * 
	 * @param parent
	 *            widget which will be the parent of the new instance (cannot be
	 *            null)
	 * @param style
	 *            the style of the composite which wraps the SWT Browser.
	 * @param builder
	 *            the HTML builder to use to load the Orion editor with SWT
	 *            Browser.
	 */
	public OrionControl(Composite parent, int style, IHTMLBuilder builder) {
		super(parent, style);
		super.setLayout(new FillLayout());
		this.browser = BrowserFactory.create(this, getBrowserStyle());
		// System.err.println(builder.getHTML());
		browser.setText(builder.getHTML());
		createBrowserFunctions();
	}

	/**
	 * Create Broser functions.
	 */
	protected void createBrowserFunctions() {
		// onload function
		new BrowserFunction(browser, "orion_onLoad") {
			public Object function(Object[] arguments) {
				onLoad();
				return null;
			}
		};
		// dirty function
		new BrowserFunction(browser, "orion_dirty") {
			public Object function(Object[] arguments) {
				notifyDirtyListeners();
				return null;
			}
		};
	}

	/**
	 * Returns the Browser style to use.
	 * 
	 * @return the Browser style to use.
	 */
	protected Integer getBrowserStyle() {
		return null;
	}

	@Override
	public void setLayout(Layout layout) {
		throw new UnsupportedOperationException(
				"Cannot change internal layout of Editor");
	}

	// ------------------------- Load methods -----------------------

	/**
	 * Callback called when Orion editor is loaded.
	 */
	protected void onLoad() {
		loaded = true;
		// Set text if need
		if (textToBeSet != null) {
			setText(textToBeSet);
			textToBeSet = null;
		}
		// Set focus if need
		if (focusToBeSet != null) {
			if (focusToBeSet) {
				browser.evaluate("editor.focus();");
			}
			focusToBeSet = null;
		}
		// add dirty event listener.
		browser.evaluate("window.editor.addEventListener('DirtyChanged', orion_dirty, true)");
		// Set dirty if need
		if (dirtyToBeSet != null) {
			setDirty(dirtyToBeSet);
			dirtyToBeSet = null;
		}
	}

	/**
	 * Returns true if Orion editor is loaded and false otherwise.
	 * 
	 * @return
	 */
	public boolean isLoaded() {
		return loaded;
	}

	// ------------------------- Text methods -----------------------

	/**
	 * Set the text in the Orion editor.
	 * 
	 * @param text
	 *            the text to set.
	 */
	public void setText(String text) {
		if (text == null || text.length() == 0) {
			text = "";
		}

		if (isLoaded()) {
			// orion editor is loaded, set the text in the editor
			doSetText(text);
		} else {
			// Orion editor is not loaded, set the text in the textToBeSet
			textToBeSet = text;
		}
	}

	/**
	 * Set text in the Orion editor by using Javascript function editor.setInput
	 * 
	 * @param text
	 *            the text to set.
	 */
	protected void doSetText(String text) {
		String js = new StringBuilder("window.editor.setInput(null, null, \"")
				.append(StringEscapeUtils.escapeEcmaScript(text))
				.append("\", false );").toString();
		browser.evaluate(js);
	}

	/**
	 * Returns the text of the Orion editor.
	 * 
	 * @return
	 */
	public String getText() {
		if (!isLoaded()) {
			if (textToBeSet != null) {
				return textToBeSet;
			} else {
				return "";
			}
		}
		return doGetText();
	}

	/**
	 * Returns the text of the Orion editor by using Javascript function
	 * editor.getText
	 * 
	 * @return the text of the Orion editor by using Javascript function
	 *         editor.getText
	 */
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
	 * Add dirty listener.
	 * 
	 * @param listener
	 *            dirty listener to add.
	 */
	public void addDirtyListener(IDirtyListener listener) {
		if (listeners == null) {
			listeners = new ArrayList<IDirtyListener>();
		}
		listeners.add(listener);
	}

	/**
	 * Remove dirty listener.
	 * 
	 * @param listener
	 *            dirty listener to remove.
	 */
	public void removeDirtyListener(IDirtyListener listener) {
		if (listeners != null) {
			listeners.remove(listener);
		}
	}

	/**
	 * Notify dirty listeners if need.
	 */
	private void notifyDirtyListeners() {
		if (listeners == null) {
			return;
		}
		Display.getCurrent().asyncExec(new Runnable() {
			public void run() {
				boolean dirty = isDirty();
				for (IDirtyListener listener : listeners) {
					listener.dirtyChanged(dirty);
				}
			}
		});
	}

	/**
	 * Set the dirty flag.
	 * 
	 * @param dirty
	 *            the dirty flag.
	 */
	public void setDirty(boolean dirty) {
		if (isLoaded()) {
			// orion editor is loaded, set the dirty in the editor
			doSetDirty(dirty);
		} else {
			// Orion editor is not loaded, set the dirty in the dirtyToBeSet
			dirtyToBeSet = dirty;
		}
	}

	/**
	 * Set the dirty flag in the orion editor with Javascript function
	 * editor.setDirty.
	 * 
	 * @param dirty
	 *            the dirty flag.
	 */
	public void doSetDirty(boolean dirty) {
		String js = new StringBuilder("window.editor.setDirty(").append(dirty)
				.append(");").toString();
		browser.evaluate(js);
	}

	/**
	 * Returns true if Orion control is dirty and false otherwise.
	 * 
	 * @return true if Orion control is dirty and false otherwise.
	 */
	public boolean isDirty() {
		if (!isLoaded()) {
			if (dirtyToBeSet != null) {
				return dirtyToBeSet;
			}
			return false;
		}
		final Object rc = browser.evaluate("return window.editor.isDirty();");
		if (rc instanceof Boolean) {
			return (Boolean) rc;
		}
		return false;
	}
}
