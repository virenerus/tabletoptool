package com.t3.script.api.functions.input;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.t3.language.I18N;
import com.t3.script.MT2ScriptException;

@SuppressWarnings("serial")
final class InputPanel extends JPanel {
	public List<ColumnPanel> columnPanels;
	public JTabbedPane tabPane = null;
	public int initialTab = 0; // Which one is first visible

	InputPanel(List<VarSpec> varSpecs) throws MT2ScriptException {
		ColumnPanel curcp;
		columnPanels = new ArrayList<ColumnPanel>();

		// Only allow tabs if the first item is a TAB specifier
		boolean useTabs = (varSpecs.get(0).inputType == InputType.TAB);
		int nextTabIndex = 0;

		if (useTabs) {
			// The top-level control in the InputPanel is a JTabbedPane
			tabPane = new JTabbedPane();
			add(tabPane);
			curcp = null; // Will get initialized on first step of loop below
		} else {
			// The top-level control is just a single ColumnPanelHost
			curcp = new ColumnPanel();
			columnPanels.add(curcp);
			ColumnPanelHost cph = new ColumnPanelHost(curcp);
			add(cph);
		}

		for (VarSpec vs : varSpecs) {
			if (vs.inputType == InputType.TAB) {
				if (useTabs) {
					curcp = new ColumnPanel();
					curcp.tabVarSpec = vs;
					curcp.setBorder(new EmptyBorder(5, 5, 5, 5));
					columnPanels.add(curcp);
					ColumnPanelHost cph = new ColumnPanelHost(curcp);

					tabPane.addTab(vs.value, null, cph, vs.prompt);
					if (vs.optionValues.optionEquals("SELECT", "TRUE")) {
						initialTab = nextTabIndex;
					}
					nextTabIndex++;
				} else {
					throw new MT2ScriptException(I18N.getText("macro.function.input.invalidTAB"));
				}
			} else {
				// Not a TAB variable, so just add to the current ColumnPanel
				curcp.addVariable(vs);
			}
		}
	}

	/**
	 * Returns the first focusable control on the tab which is shown
	 * initially.
	 */
	public JComponent findFirstFocusable() {
		ColumnPanel cp = columnPanels.get(initialTab);
		JComponent first = cp.findFirstFocusable();
		return first;
	}

	/**
	 * Adjusts the runtime behavior of components, and sets the initial
	 * focus.
	 */
	public void runtimeFixup() {
		for (ColumnPanel cp : columnPanels) {
			cp.runtimeFixup();
		}

		// Select the initial tab, if any
		if (tabPane != null) {
			tabPane.setSelectedIndex(initialTab);
		}

		// Start the focus in the first input field, so the user can type immediately
		JComponent compFirst = findFirstFocusable();
		if (compFirst != null)
			compFirst.requestFocusInWindow();

		// When tab changes, save the last field that had the focus.
		// (The first field in the panel will gain focus before the panel is shown,
		//  so we have to save cp.lastFocus before it's overwritten.)
		if (tabPane != null) {
			tabPane.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					int newTabIndex = tabPane.getSelectedIndex();
					ColumnPanel cp = columnPanels.get(newTabIndex);
					cp.onShowFocus = cp.lastFocus;

//					// debugging
//					JComponent foc = cp.onShowFocus;
//					String s = (foc instanceof JTextField) ?
//						" (" + ((JTextField)foc).getText() + ")" : "";
//					String c = (foc!=null) ? foc.getClass().getName() : "";
//					System.out.println("tabpane foc = " + c + s);
				}
			});
		}
	}

	public void modifyMaxHeightBy(int mod) {
		for (ColumnPanel cpanel : columnPanels) {
			cpanel.maxHeightModifier = mod;
		}
	}

}