Eclipse-E4-Orion-CSS-Editor
===========================

E4 Tools explores the capability to provide a CSS Editor with Orion. See bug https://bugs.eclipse.org/bugs/show_bug.cgi?id=410841

This Git projects tries to improve the Eclipse E4 Orion CSS Editor. It contains 3 projects : 

 * [org.eclipse.e4.tools.orion.editor](https://github.com/angelozerr/Eclipse-E4-Orion-CSS-Editor/tree/master/org.eclipse.e4.tools.orion.editor)} which provides
a SWT control [OrionControl](https://github.com/angelozerr/Eclipse-E4-Orion-CSS-Editor/blob/master/org.eclipse.e4.tools.orion.editor/src/org/eclipse/e4/tools/orion/editor/swt/OrionControl.java)
which loads Javascript Orion editor in a SWT Browser. It provides setText, getText, setDirty, isDirty and setFocus methods.
 * [https://github.com/angelozerr/Eclipse-E4-Orion-CSS-Editor/tree/master/org.eclipse.e4.tools.orion.css.editor org.eclipse.e4.tools.orion.css.editor] is the original 
Orion CSS Editor (See [CSSEditorPreferences](https://github.com/angelozerr/Eclipse-E4-Orion-CSS-Editor/blob/master/org.eclipse.e4.tools.orion.css.editor/src/org/eclipse/e4/tools/orion/css/editor/CSSEditorPreferences.java)) 
but instead of using a SWT Browser which loads the Orion CSS Editor, it uses OrionControl.
 * [org.eclipse.e4.tools.orion.editor.samples](https://github.com/angelozerr/Eclipse-E4-Orion-CSS-Editor/tree/master/org.eclipse.e4.tools.orion.editor.samples) provides SWT Java main samples
which use OrionControl (CSS editor and JS editor).

Here a list of improvement of the original E4 CSS editor with Orion: 

 * Provides OrionControl (instead of SWT Browser). Using OrionControl (which uses SWT Browser) is more cleaner than using directly SWT Browser. More it can be used with other context than CSSEditorPreferences
(SWT Java main, 3.x ViewPart, E4 Editor, etc).
 * [JET templates](https://github.com/angelozerr/Eclipse-E4-Orion-CSS-Editor/tree/master/org.eclipse.e4.tools.orion.editor/templates) are used instead of using String.format.
 * the original project loads the full HTML of the orion editor (with the selected theme content of the editor). Here the HTML of the orion editor
 doesn't contains the selected theme content, the theme is setted with OrionControl#setText which uses Javascript code. It's more cleaner and more performant (no need to reload each time that theme is selected the SWT Browser).
 * fix some bugs (support IE8, when preferences page is opened/closed/reopened disposed exception is thrown in the original project).