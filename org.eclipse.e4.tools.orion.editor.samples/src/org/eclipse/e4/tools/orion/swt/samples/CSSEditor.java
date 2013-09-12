package org.eclipse.e4.tools.orion.swt.samples;

import java.io.File;

import org.eclipse.e4.tools.orion.editor.builder.css.CSSBuilder;
import org.eclipse.e4.tools.orion.editor.swt.IDirtyListener;
import org.eclipse.e4.tools.orion.editor.swt.OrionControl;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;

public class CSSEditor {

	public static void main(String[] args) {

		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setSize(500, 500);
		shell.setText("Orion CSS Editor");
		shell.setLayout(new GridLayout());

		CSSBuilder builder = new CSSBuilder(new File(
				"../org.eclipse.e4.tools.orion.editor"));
		OrionControl editor = new OrionControl(shell, SWT.BORDER, builder);
		editor.setLayoutData(new GridData(GridData.FILL_BOTH));

		// Menu
		Menu menuBar = createMenu(shell, editor);
		shell.setMenuBar(menuBar);

		editor.setText("body {\n\tcolor:red;\n}");

		shell.open();
		editor.setFocus();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();

	}

	private static Menu createMenu(final Shell shell, final OrionControl editor) {
		Menu menuBar = new Menu(shell, SWT.BAR);
		// File menu
		createFileMenu(shell, menuBar, editor);
		return menuBar;
	}

	private static void createFileMenu(final Shell shell, Menu menuBar,
			final OrionControl editor) {
		MenuItem fileMenuHeader = new MenuItem(menuBar, SWT.CASCADE);
		fileMenuHeader.setText("&File");

		Menu fileMenu = new Menu(shell, SWT.DROP_DOWN);
		fileMenuHeader.setMenu(fileMenu);

		// Save
		final MenuItem fileSaveItem = new MenuItem(fileMenu, SWT.PUSH);
		fileSaveItem.setText("&Save");
		fileSaveItem.setEnabled(false);
		fileSaveItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				editor.setDirty(false);
			}
		});

		editor.addDirtyListener(new IDirtyListener() {
			public void dirtyChanged(boolean dirty) {
				fileSaveItem.setEnabled(dirty);
			}
		});

		// Exit
		MenuItem fileExitItem = new MenuItem(fileMenu, SWT.PUSH);
		fileExitItem.setText("E&xit");
		fileExitItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				shell.close();
				// shell.getDisplay().dispose();
			}
		});
	}
}
