package com.t3.macro.api.functions.input;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import net.sf.tinylaf.TinyComboBoxButton;

import org.apache.commons.lang3.StringUtils;

import com.t3.client.ui.htmlframe.HTMLPane;
import com.t3.macro.api.functions.input.InputType.OptionException;

/**
 * Contains input controls, which are arranged in a two-column label +
 * control layout.
 */
@SuppressWarnings("serial")
public final class ColumnPanel extends JPanel {
	
	private static final Pattern ASSET_PATTERN = Pattern.compile("^(.*)asset://(\\w+)");
	
	public VarSpec tabVarSpec; // VarSpec for this subpanel's tab, if any
	public List<VarSpec> varSpecs;
	public List<JComponent> labels; // the labels in the left column
	public List<JComponent> inputFields; // the input controls (some may be panels for composite inputs)
	public JComponent lastFocus; // last input field with the focus
	public JComponent onShowFocus; // field to gain focus when shown
	private Insets textInsets = new Insets(0, 2, 0, 2); // used by all text controls
	private GridBagConstraints gbc = new GridBagConstraints();
	private int componentCount;

	public int maxHeightModifier = 0;

	public ColumnPanel() {
		tabVarSpec = null;
		varSpecs = new ArrayList<VarSpec>();
		labels = new ArrayList<JComponent>();
		inputFields = new ArrayList<JComponent>();
		lastFocus = null;
		onShowFocus = null;
		textInsets = new Insets(0, 2, 0, 2); // used by all TEXT controls

		setLayout(new GridBagLayout());
		gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.insets = new Insets(2, 2, 2, 2);

		componentCount = 0;
	}

	public ColumnPanel(List<VarSpec> lvs) {
		this(); // Initialize various member variables
		varSpecs = lvs;

		for (VarSpec vs : varSpecs) {
			addVariable(vs, false);
		}

	}

	/** Adds a row to the ColumnPanel with a label and input field. */
	public void addVariable(VarSpec vs) {
		addVariable(vs, true);
	}

	/**
	 * Adds a row to the ColumnPanel with a label and input field.
	 * <code>addToVarList</code> controls whether the VarSpec is added to
	 * the local listing.
	 */
	protected void addVariable(VarSpec vs, boolean addToVarList) {
		if (addToVarList)
			varSpecs.add(vs);

		gbc.gridy = componentCount;
		gbc.gridwidth = 1;

		// add the label
		gbc.gridx = 0;

		JComponent l;
		Matcher m = Pattern.compile("^\\s*<html>(.*)<\\/html>\\s*$").matcher(vs.prompt);

		if (m.find()) {
			// For HTML values we use a HTMLPane.
			HTMLPane htmlp = new HTMLPane();
			htmlp.setText("<html>" + m.group(1) + ":</html>");
			htmlp.setBackground(Color.decode("0xECE9D8"));
			l = htmlp;
		} else {
			l = new JLabel(vs.prompt + ":");
		}
		labels.add(l);

		if (!vs.optionValues.optionEquals("SPAN", "TRUE")) {
			// if the control is not set to span, we include the prompt label
			add(l, gbc);
		}

		// add the input component
		JComponent inputField = createInputControl(vs);

		if (vs.optionValues.optionEquals("SPAN", "TRUE")) {
			gbc.gridwidth = 2; // the control spans both columns
			inputField.setToolTipText(vs.prompt);
		} else {
			gbc.gridx = 1; // the control lives in the second column
		}

		inputFields.add(inputField);
		add(inputField, gbc);
		componentCount++;
	}

	/** Finds the first focusable control. */
	public JComponent findFirstFocusable() {
		for (JComponent c : inputFields) {
			if (c instanceof ColumnPanel) {
				ColumnPanel cp = (ColumnPanel) c;
				JComponent firstInPanel = cp.findFirstFocusable();
				if (firstInPanel != null) {
					return firstInPanel;
				}
			} else if (!(c instanceof JLabel)) {
				return c;
			}
		}
		return null;
	}

	/** Sets the focus to the control that last had it. */
	public void restoreFocus() {
//		// debugging
//		String s = (onShowFocus instanceof JTextField) ?
//			" (" + ((JTextField)onShowFocus).getText() + ")" : "";
//		String c = (onShowFocus == null) ? "null" : onShowFocus.getClass().getName();
//		System.out.println("  Shown: onShowFocus is " + c + s);

		if (onShowFocus != null) {
			onShowFocus.requestFocusInWindow();
		} else {
			JComponent first = findFirstFocusable();
			if (first != null)
				first.requestFocusInWindow();
		}
	}

	/** Adjusts the runtime behavior of controls. Called when displayed. */
	public void runtimeFixup() {
		// When first displayed, the focus will go to the first field.
		lastFocus = findFirstFocusable();

		// When a field gains the focus, save it in lastFocus.
		FocusListener listener = new FocusListener() {
			public void focusGained(FocusEvent fe) {
				JComponent src = (JComponent) fe.getSource();
				lastFocus = src;
				if (src instanceof JTextField)
					((JTextField) src).selectAll();
//					// debugging
//					String s = (src instanceof JTextField) ?
//						" (" + ((JTextField)src).getText() + ")" : "";
//					System.out.println("  Got focus " + src.getClass().getName() + s);
			}

			public void focusLost(FocusEvent arg0) {
			}
		};

		for (JComponent c : inputFields) {
			// Each control saves itself to lastFocus when it gains focus.
			c.addFocusListener(listener);

			// Implement control-specific adjustments
			if (c instanceof JComboBox) {
				// HACK: to fix a Swing issue.
				// The stupid JComboBox has two subcomponents, BOTH of which accept the focus.
				// Thus it takes two Tab presses to move to the next control, and if you
				// tab once and then hit the down arrow, you can then tab away while the dropdown
				// list remains displayed.  (Other comboboxes in TabletopTool have similar problems.)
				// Since the user is likely to tab between values when inputting, this is a
				// confusing nuisance.
				//
				// The hack used here is to make one of the two components (TinyComboBoxButton)
				// not focusable.  We have to do it in a callback like this because the subcomponents
				// don't exist until the dialog is created (I think?).  The code has a hardcoded index of 0,
				// which is where the TinyComboBoxButton lives on my Windows box (discovered using the debugger).
				// The code may fail on other OSs, or if a future version of Swing is used.
				// You're not supposed to mess with the internals like this.
				// But the resulting behavior is so much nicer with this fix in place, that I'm keeping it in.
				Component list[] = c.getComponents();
				for (int i = 0; i < list.length; i++)
					if (list[i] instanceof TinyComboBoxButton)
						list[i].setFocusable(false); // HACK!
//			} else if (c instanceof JTextField) {
//				// Select all text when the text field gains focus
//				final JTextField textFieldFinal = (JTextField) c;
//				textFieldFinal.addFocusListener(new FocusListener() {
//					public void focusGained(FocusEvent fe) {
//						textFieldFinal.selectAll();
//					}
//
//					public void focusLost(FocusEvent fe) {
//					}
//				});
			} else if (c instanceof ColumnPanel) {
				ColumnPanel cp = (ColumnPanel) c;
				cp.runtimeFixup();
			}
		}
		if (lastFocus != null)
			scrollRectToVisible(lastFocus.getBounds());
	}

	/** Creates the appropriate type of input control. */
	public JComponent createInputControl(VarSpec vs) {
		switch (vs.inputType) {
		case TEXT:
			return createTextControl(vs);
		case LIST:
			return createListControl(vs);
		case CHECK:
			return createCheckControl(vs);
		case RADIO:
			return createRadioControl(vs);
		case LABEL:
			return createLabelControl(vs);
		case PROPS:
			return createPropsControl(vs);
		case TAB:
			return null; // should never happen
		default:
			return null;
		}
	}

	/** Creates a text input control. */
	public JComponent createTextControl(VarSpec vs) {
		int width = vs.optionValues.getNumeric("Width");
		JTextField txt = new JTextField(vs.value, width);
		txt.setMargin(textInsets);
		return txt;
	}

	/** Creates a dropdown list control. */
	public JComponent createListControl(VarSpec vs) {
		JComboBox combo;
		boolean showText = vs.optionValues.optionEquals("TEXT", "TRUE");
		boolean showIcons = vs.optionValues.optionEquals("ICON", "TRUE");
		int iconSize = vs.optionValues.getNumeric("ICONSIZE", 0);
		if (iconSize <= 0)
			showIcons = false;

		// Build the combo box
		for (int j = 0; j < vs.valueList.size(); j++) {
			if (StringUtils.isEmpty(vs.valueList.get(j))) {
				// Using a non-empty string prevents the list entry from having zero height.
				vs.valueList.set(j, " ");
			}
		}
		if (!showIcons) {
			// Swing has an UNBELIEVABLY STUPID BUG when multiple items in a JComboBox compare as equal.
			// The combo box then stops supporting navigation with arrow keys, and
			// no matter which of the identical items is chosen, it returns the index
			// of the first one.  Sun closed this bug as "by design" in 1998.
			// A workaround found on the web is to use this alternate string class (defined below)
			// which never reports two items as being equal.
			NoEqualString[] nesValues = new NoEqualString[vs.valueList.size()];
			for (int i = 0; i < nesValues.length; i++)
				nesValues[i] = new NoEqualString(vs.valueList.get(i));
			combo = new JComboBox(nesValues);
		} else {
			combo = new JComboBox();
			combo.setRenderer(new ComboBoxRenderer());
			Pattern pattern = ASSET_PATTERN;

			for (String value : vs.valueList) {
				Matcher matcher = pattern.matcher(value);
				String valueText, assetID;
				Icon icon = null;

				// See if the value string for this item has an image URL inside it
				if (matcher.find()) {
					valueText = matcher.group(1);
					assetID = matcher.group(2);
				} else {
					valueText = value;
					assetID = null;
				}

				// Assemble a JLabel and put it in the list
				UpdatingLabel label = new UpdatingLabel();
				icon = InputFunctions.getIcon(assetID, iconSize, label);
				label.setOpaque(true); // needed to make selection highlighting show up
				if (showText)
					label.setText(valueText);
				if (icon != null)
					label.setIcon(icon);
				combo.addItem(label);
			}
		}
		int listIndex = vs.optionValues.getNumeric("SELECT");
		if (listIndex < 0 || listIndex >= vs.valueList.size())
			listIndex = 0;
		combo.setSelectedIndex(listIndex);
		combo.setMaximumRowCount(20);
		return combo;
	}

	/** Creates a single checkbox control. */
	public JComponent createCheckControl(VarSpec vs) {
		JCheckBox check = new JCheckBox();
		check.setText("    "); // so a focus indicator will appear
		if (vs.value.compareTo("0") != 0)
			check.setSelected(true);
		return check;
	}

	/** Creates a group of radio buttons. */
	public JComponent createRadioControl(VarSpec vs) {
		int listIndex = vs.optionValues.getNumeric("SELECT");
		if (listIndex < 0 || listIndex >= vs.valueList.size())
			listIndex = 0;
		ButtonGroup bg = new ButtonGroup();
		Box box = (vs.optionValues.optionEquals("ORIENT", "H")) ? Box.createHorizontalBox() : Box.createVerticalBox();

		// If the prompt is suppressed by SPAN=TRUE, use it as the border title
		String title = "";
		if (vs.optionValues.optionEquals("SPAN", "TRUE"))
			title = vs.prompt;
		box.setBorder(new TitledBorder(new EtchedBorder(), title));

		int radioCount = 0;
		for (String value : vs.valueList) {
			JRadioButton radio = new JRadioButton(value, false);
			bg.add(radio);
			box.add(radio);
			if (listIndex == radioCount)
				radio.setSelected(true);
			radioCount++;
		}
		return box;
	}

	/** Creates a label control, with optional icon. */
	public JComponent createLabelControl(VarSpec vs) {
		boolean hasText = vs.optionValues.optionEquals("TEXT", "TRUE");
		boolean hasIcon = vs.optionValues.optionEquals("ICON", "TRUE");

		// If the string starts with "<html>" then Swing will consider it HTML...
		if (hasText && vs.value.matches("^\\s*<html>")) {
			// For HTML values we use a HTMLPane.
			HTMLPane htmlp = new HTMLPane();
			htmlp.setText(vs.value);
			htmlp.setBackground(Color.decode("0xECE9D8"));
			return htmlp;
		}

		UpdatingLabel label = new UpdatingLabel();

		int iconSize = vs.optionValues.getNumeric("ICONSIZE", 0);
		if (iconSize <= 0)
			hasIcon = false;
		String valueText = "", assetID = "";
		Icon icon = null;

		// See if the string has an image URL inside it
		Matcher matcher = ASSET_PATTERN.matcher(vs.value);
		if (matcher.find()) {
			valueText = matcher.group(1);
			assetID = matcher.group(2);
		} else {
			hasIcon = false;
			valueText = vs.value;
		}

		// Try to get the icon
		if (hasIcon) {
			icon = InputFunctions.getIcon(assetID, iconSize, label);
			if (icon == null)
				hasIcon = false;
		}

		// Assemble the label
		if (hasText)
			label.setText(valueText);
		if (hasIcon)
			label.setIcon(icon);

		return label;
	}

	/** Creates a subpanel with controls for each property. */
	public JComponent createPropsControl(VarSpec vs) {
		// Get the key/value pairs from the property string
		Map<String, String> map = new HashMap<String, String>();
		List<String> oldKeys = new ArrayList<String>();
		
		for(String entry:org.apache.commons.lang3.StringUtils.split(vs.value,";")) {
			String[] e=org.apache.commons.lang3.StringUtils.split(entry.trim(),"=");
			map.put(e[0], e[1]);
			oldKeys.add(e[0]);
		}

		// Create list of VarSpecs for the subpanel
		List<VarSpec> varSpecs = new ArrayList<VarSpec>();
		for (String key : oldKeys) {
			String name = key;
			String value = map.get(key.toUpperCase());
			String prompt = key;
			InputType it = InputType.TEXT;
			String options = "WIDTH=14;";
			VarSpec subvs;
			try {
				subvs = new VarSpec(name, value, prompt, it, options);
			} catch (OptionException e) {
				// Should never happen
				e.printStackTrace();
				subvs = null;
			}
			varSpecs.add(subvs);
		}

		// Create the subpanel
		ColumnPanel cp = new ColumnPanel(varSpecs);

		// If the prompt is suppressed by SPAN=TRUE, use it as the border title
		String title = "";
		if (vs.optionValues.optionEquals("SPAN", "TRUE"))
			title = vs.prompt;
		cp.setBorder(new TitledBorder(new EtchedBorder(), title));

		return cp;
	}
}