Eclipse-E4-Orion-CSS-Editor
===========================

E4 Tools explores the capability to provide a CSS Editor with Orion. See bug https://bugs.eclipse.org/bugs/show_bug.cgi?id=410841

This Git projects tries to improve the Eclipse E4 Orion CSS Editor. It contains 3 projects : 

 * [https://github.com/angelozerr/Eclipse-E4-Orion-CSS-Editor/tree/master/org.eclipse.e4.tools.orion.editor org.eclipse.e4.tools.orion.editor] which provides
a SWT control [https://github.com/angelozerr/Eclipse-E4-Orion-CSS-Editor/blob/master/org.eclipse.e4.tools.orion.editor/src/org/eclipse/e4/tools/orion/editor/swt/OrionControl.java OrionControl]
which loads Javascript Orion editor in a SWT Browser. It provides setText, getText, setDirty, isDirty and setFocus methods.
 * [https://github.com/angelozerr/Eclipse-E4-Orion-CSS-Editor/tree/master/org.eclipse.e4.tools.orion.editor.css org.eclipse.e4.tools.orion.editor.css] is the original 
Orion CSS Editor (Tweaklet) but instead of using a SWT Browser which loads teh Orion CSS Editor, it uses OrionControl.
 * [https://github.com/angelozerr/Eclipse-E4-Orion-CSS-Editor/tree/master/org.eclipse.e4.tools.orion.editor.samples org.eclipse.e4.tools.orion.editor.samples] provides SWT Java main sample
which use OrionControl.

Here a list of improvment of the original E4 CSS editor with Orion: 

 * OrionControl (instead of SWT Browser). Using OrionControl (which uses SWT Browser) is more cleaner than using directly SWT Browser. More it can e used with another context than Tweaklet
(SWT Java main, 3.x ViewPart, E4 Editor, etc).
 * [https://github.com/angelozerr/Eclipse-E4-Orion-CSS-Editor/tree/master/org.eclipse.e4.tools.orion.editor/templates JET templates] are used instead of using String.format.
 * fix some bugs (support IE8, when prefertences page is opend/closed/reopened disposed exception is thrown in the original project).
 * the original project load the full HTML of the orion editor (with the selected theme content of the editor). Here the HTML of the orion editor
 doesn't contains the selected theme content, the theme is setted with OrionControl#setText which uses Javascript code. It's more cleaner and more performant (no need to reload each time that theme is selected the SWT Browser).
