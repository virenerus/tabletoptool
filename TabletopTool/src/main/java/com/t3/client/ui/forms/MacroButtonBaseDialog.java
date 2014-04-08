package com.t3.client.ui.forms;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rtextarea.RTextScrollPane;

/**
 * @author Virenerus
 */
public abstract class MacroButtonBaseDialog extends JDialog {

	protected JTabbedPane macroTabs;
	protected JPanel detailsPanel;
	protected RTextScrollPane commandScrollPane;
	protected RSyntaxTextArea commandTextArea;
	protected JLabel macroCommandLabel;
	protected JPanel panel4;
	protected JLabel macroLabelLabel;
	protected JTextField labelTextField;
	protected JLabel macroGroupLabel;
	protected JTextField groupTextField;
	protected JLabel macroSortPrefixLabel;
	protected JTextField sortByTextField;
	protected JLabel macroHotKeyLabel;
	protected JComboBox<String> hotKeyCombo;
	protected JPanel panel5;
	protected JLabel macroButtonColorLabel;
	protected JComboBox<String> colorComboBox;
	protected JLabel macroFontSizeLabel;
	protected JTextField tooltipTextField;
	protected JLabel macroMinWidthLabel;
	protected JTextField minWidthTextField;
	protected JLabel macroFontColorLabel;
	protected JComboBox<String> fontColorComboBox;
	protected JLabel macroToolTipLabel;
	protected JComboBox<String> fontSizeComboBox;
	protected JLabel macroMaxWidthLabel;
	protected JTextField maxWidthTextField;
	protected JPanel panel1;
	protected JPanel macroComparisonGridView;
	protected JCheckBox compareGroupCheckBox;
	protected JCheckBox compareSortPrefixCheckBox;
	protected JCheckBox compareCommandCheckBox;
	protected JCheckBox allowPlayerEditsCheckBox;
	protected JButton okButton;
	protected JButton cancelButton;

	public MacroButtonBaseDialog() {
		initComponents();
	}

	protected abstract void okButtonClicked(ActionEvent e);

	protected abstract void cancelButtonClicked(ActionEvent e);

	private void initComponents() {
		macroTabs = new JTabbedPane();
		macroTabs.setBorder(null);
		detailsPanel = new JPanel();
		commandScrollPane = new RTextScrollPane();
		commandTextArea = new RSyntaxTextArea();
		macroCommandLabel = new JLabel();
		panel4 = new JPanel();
		macroLabelLabel = new JLabel();
		labelTextField = new JTextField();
		macroGroupLabel = new JLabel();
		groupTextField = new JTextField();
		macroSortPrefixLabel = new JLabel();
		sortByTextField = new JTextField();
		macroHotKeyLabel = new JLabel();
		hotKeyCombo = new JComboBox<>();
		panel5 = new JPanel();
		panel5.setBorder(null);
		macroButtonColorLabel = new JLabel();
		colorComboBox = new JComboBox<>();
		macroFontSizeLabel = new JLabel();
		tooltipTextField = new JTextField();
		macroMinWidthLabel = new JLabel();
		minWidthTextField = new JTextField();
		macroFontColorLabel = new JLabel();
		fontColorComboBox = new JComboBox<>();
		macroToolTipLabel = new JLabel();
		fontSizeComboBox = new JComboBox<>();
		macroMaxWidthLabel = new JLabel();
		maxWidthTextField = new JTextField();
		panel1 = new JPanel();
		macroComparisonGridView = new JPanel();
		compareGroupCheckBox = new JCheckBox();
		compareSortPrefixCheckBox = new JCheckBox();
		compareCommandCheckBox = new JCheckBox();
		allowPlayerEditsCheckBox = new JCheckBox();
		okButton = new JButton();
		cancelButton = new JButton();

		//======== this ========
		setMinimumSize(new Dimension(790, 415));
		Container contentPane = getContentPane();

		//======== macroTabs ========
		{

			//======== detailsPanel ========
			{

				detailsPanel.setLayout(new BorderLayout(5, 5));

				//======== commandScrollPane ========
				{

					//---- commandTextArea ----
					commandTextArea.setHighlightCurrentLine(false);
					commandTextArea.setCodeFoldingEnabled(true);
					commandTextArea.setSyntaxEditingStyle("text/groovy"); //$NON-NLS-1$
					commandScrollPane.setFoldIndicatorEnabled(true);
					commandScrollPane.setLineNumbersEnabled(true);
					commandScrollPane.setViewportView(commandTextArea);
				}
				detailsPanel.add(commandScrollPane, BorderLayout.CENTER);

				//---- macroCommandLabel ----
				macroCommandLabel.setText("Command:"); //$NON-NLS-1$
				macroCommandLabel.setBorder(new EmptyBorder(0, 10, 0, 0));
				detailsPanel.add(macroCommandLabel, BorderLayout.WEST);

				//======== panel4 ========
				{
					panel4.setLayout(new GridLayout(1, 0, 10, 10));

					//---- macroLabelLabel ----
					macroLabelLabel.setHorizontalAlignment(SwingConstants.RIGHT);
					macroLabelLabel.setText("Label:"); //$NON-NLS-1$
					panel4.add(macroLabelLabel);

					//---- labelTextField ----
					labelTextField.setColumns(15);
					panel4.add(labelTextField);

					//---- macroGroupLabel ----
					macroGroupLabel.setHorizontalAlignment(SwingConstants.RIGHT);
					macroGroupLabel.setText("Group:"); //$NON-NLS-1$
					panel4.add(macroGroupLabel);

					//---- groupTextField ----
					groupTextField.setColumns(15);
					panel4.add(groupTextField);

					//---- macroSortPrefixLabel ----
					macroSortPrefixLabel.setHorizontalAlignment(SwingConstants.RIGHT);
					macroSortPrefixLabel.setText("Sort Prefix:"); //$NON-NLS-1$
					panel4.add(macroSortPrefixLabel);

					//---- sortByTextField ----
					sortByTextField.setColumns(15);
					panel4.add(sortByTextField);

					//---- macroHotKeyLabel ----
					macroHotKeyLabel.setHorizontalAlignment(SwingConstants.RIGHT);
					macroHotKeyLabel.setText("Hot Key:"); //$NON-NLS-1$
					panel4.add(macroHotKeyLabel);

					//---- hotKeyCombo ----
					hotKeyCombo.setMaximumRowCount(12);
					hotKeyCombo.setModel(new DefaultComboBoxModel<>(new String[] {

					}));
					panel4.add(hotKeyCombo);
				}
				detailsPanel.add(panel4, BorderLayout.NORTH);

				//======== panel5 ========
				{
					panel5.setLayout(new GridLayout(2, 0, 10, 10));

					//---- macroButtonColorLabel ----
					macroButtonColorLabel.setHorizontalAlignment(SwingConstants.RIGHT);
					macroButtonColorLabel.setText("Button Color:"); //$NON-NLS-1$
					panel5.add(macroButtonColorLabel);

					//---- colorComboBox ----
					colorComboBox.setRequestFocusEnabled(false);
					colorComboBox.setEditable(true);
					panel5.add(colorComboBox);

					//---- macroFontSizeLabel ----
					macroFontSizeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
					macroFontSizeLabel.setText("Font Size:"); //$NON-NLS-1$
					panel5.add(macroFontSizeLabel);

					//---- tooltipTextField ----
					tooltipTextField.setColumns(15);
					panel5.add(tooltipTextField);

					//---- macroMinWidthLabel ----
					macroMinWidthLabel.setHorizontalAlignment(SwingConstants.RIGHT);
					macroMinWidthLabel.setText("Min Width:"); //$NON-NLS-1$
					panel5.add(macroMinWidthLabel);

					//---- minWidthTextField ----
					minWidthTextField.setColumns(15);
					panel5.add(minWidthTextField);

					//---- macroFontColorLabel ----
					macroFontColorLabel.setHorizontalAlignment(SwingConstants.RIGHT);
					macroFontColorLabel.setText("Font Color:"); //$NON-NLS-1$
					panel5.add(macroFontColorLabel);

					//---- fontColorComboBox ----
					fontColorComboBox.setRequestFocusEnabled(false);
					fontColorComboBox.setEditable(true);
					panel5.add(fontColorComboBox);

					//---- macroToolTipLabel ----
					macroToolTipLabel.setHorizontalAlignment(SwingConstants.RIGHT);
					macroToolTipLabel.setText("Tool Tip:"); //$NON-NLS-1$
					panel5.add(macroToolTipLabel);

					//---- fontSizeComboBox ----
					fontSizeComboBox.setRequestFocusEnabled(false);
					fontSizeComboBox.setEditable(true);
					panel5.add(fontSizeComboBox);

					//---- macroMaxWidthLabel ----
					macroMaxWidthLabel.setHorizontalAlignment(SwingConstants.RIGHT);
					macroMaxWidthLabel.setText("Max Width:"); //$NON-NLS-1$
					panel5.add(macroMaxWidthLabel);

					//---- maxWidthTextField ----
					maxWidthTextField.setColumns(15);
					panel5.add(maxWidthTextField);
				}
				detailsPanel.add(panel5, BorderLayout.SOUTH);
			}
			macroTabs.addTab("Details", detailsPanel); //$NON-NLS-1$

			//======== panel1 ========
			{
				panel1.setLayout(new BorderLayout(30, 20));

				//======== macroComparisonGridView ========
				{
					macroComparisonGridView.setBorder(new TitledBorder(LineBorder.createBlackLineBorder(), "Macro Commonality", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, Color.black)); //$NON-NLS-1$
					macroComparisonGridView.setLayout(new BoxLayout(macroComparisonGridView, BoxLayout.Y_AXIS));

					//---- compareGroupCheckBox ----
					compareGroupCheckBox.setText("Use Group"); //$NON-NLS-1$
					compareGroupCheckBox.setActionCommand("Use Label"); //$NON-NLS-1$
					macroComparisonGridView.add(compareGroupCheckBox);

					//---- compareSortPrefixCheckBox ----
					compareSortPrefixCheckBox.setText("Use Sort Prefix"); //$NON-NLS-1$
					compareSortPrefixCheckBox.setActionCommand("Use Label"); //$NON-NLS-1$
					macroComparisonGridView.add(compareSortPrefixCheckBox);

					//---- compareCommandCheckBox ----
					compareCommandCheckBox.setText("Use Command"); //$NON-NLS-1$
					compareCommandCheckBox.setActionCommand("Use Label"); //$NON-NLS-1$
					macroComparisonGridView.add(compareCommandCheckBox);
				}
				panel1.add(macroComparisonGridView, BorderLayout.CENTER);

				//---- allowPlayerEditsCheckBox ----
				allowPlayerEditsCheckBox.setText("Allow Players to Edit Macro"); //$NON-NLS-1$
				allowPlayerEditsCheckBox.setActionCommand("Allow Players to Edit Macro"); //$NON-NLS-1$
				allowPlayerEditsCheckBox.setBorder(new EmptyBorder(30, 20, 0, 0));
				panel1.add(allowPlayerEditsCheckBox, BorderLayout.NORTH);
			}
			macroTabs.addTab("Options", panel1); //$NON-NLS-1$
		}

		//---- okButton ----
		okButton.setText("OK"); //$NON-NLS-1$
		okButton.setActionCommand("OK"); //$NON-NLS-1$
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				okButtonClicked(e);
			}
		});

		//---- cancelButton ----
		cancelButton.setText("Cancel"); //$NON-NLS-1$
		cancelButton.setActionCommand("Cancel"); //$NON-NLS-1$
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cancelButtonClicked(e);
			}
		});

		GroupLayout contentPaneLayout = new GroupLayout(contentPane);
		contentPane.setLayout(contentPaneLayout);
		contentPaneLayout.setHorizontalGroup(
			contentPaneLayout.createParallelGroup()
				.addGroup(contentPaneLayout.createSequentialGroup()
					.addContainerGap(544, Short.MAX_VALUE)
					.addComponent(okButton)
					.addGap(18, 18, 18)
					.addComponent(cancelButton)
					.addContainerGap())
				.addComponent(macroTabs, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 684, Short.MAX_VALUE)
		);
		contentPaneLayout.setVerticalGroup(
			contentPaneLayout.createParallelGroup()
				.addGroup(GroupLayout.Alignment.TRAILING, contentPaneLayout.createSequentialGroup()
					.addComponent(macroTabs, GroupLayout.DEFAULT_SIZE, 339, Short.MAX_VALUE)
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(cancelButton)
						.addComponent(okButton))
					.addContainerGap())
		);
		pack();
		setLocationRelativeTo(getOwner());
	}
}
