/*
 * Created by JFormDesigner on Fri Apr 11 09:18:15 CEST 2014
 */

package com.t3.client.ui.forms;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * @author Manuel Hegner
 */
public abstract class AddResourceBaseDialog extends JDialog {
	public AddResourceBaseDialog(Frame owner) {
		super(owner);
		initComponents();
	}

	public AddResourceBaseDialog(Dialog owner) {
		super(owner);
		initComponents();
	}

	protected abstract void installButtonClicked(ActionEvent e);

	protected abstract void cancelButtonClicked(ActionEvent e);

	protected abstract void tabPaneChanged(ChangeEvent e);

	protected abstract void localDirectoryButtonClicked(ActionEvent e);

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - Manuel Hegner
		dialogPane = new JPanel();
		buttonBar = new JPanel();
		installButton = new JButton();
		cancelButton = new JButton();
		tabPane = new JTabbedPane();
		panel2 = new JPanel();
		label1 = new JLabel();
		panel6 = new JPanel();
		label2 = new JLabel();
		localDirectoryField = new JTextField();
		localDirectoryButton = new JButton();
		label3 = new JLabel();
		panel3 = new JPanel();
		label5 = new JLabel();
		panel5 = new JPanel();
		label7 = new JLabel();
		urlNameField = new JTextField();
		label8 = new JLabel();
		label6 = new JLabel();
		urlField = new JTextField();
		label4 = new JLabel();
		panel4 = new JPanel();
		panel7 = new JPanel();
		label9 = new JLabel();
		resourceListDownloadingLabel = new JLabel();
		scrollPane1 = new JScrollPane();
		tabletoptoolList = new JList<>();

		//======== this ========
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());

		//======== dialogPane ========
		{
			dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));

			dialogPane.setLayout(new BorderLayout());

			//======== buttonBar ========
			{
				buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
				buttonBar.setLayout(new GridBagLayout());
				((GridBagLayout)buttonBar.getLayout()).columnWidths = new int[] {0, 85, 80};
				((GridBagLayout)buttonBar.getLayout()).columnWeights = new double[] {1.0, 0.0, 0.0};

				//---- installButton ----
				installButton.setText("Install >>"); //$NON-NLS-1$
				installButton.setActionCommand("Install >>"); //$NON-NLS-1$
				installButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						installButtonClicked(e);
					}
				});
				buttonBar.add(installButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 5), 0, 0));

				//---- cancelButton ----
				cancelButton.setText("Cancel"); //$NON-NLS-1$
				cancelButton.setActionCommand("Cancel"); //$NON-NLS-1$
				cancelButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						cancelButtonClicked(e);
					}
				});
				buttonBar.add(cancelButton, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 0), 0, 0));
			}
			dialogPane.add(buttonBar, BorderLayout.SOUTH);

			//======== tabPane ========
			{
				tabPane.setTabPlacement(SwingConstants.LEFT);
				tabPane.addChangeListener(new ChangeListener() {
					@Override
					public void stateChanged(ChangeEvent e) {
						tabPaneChanged(e);
					}
				});

				//======== panel2 ========
				{
					panel2.setBorder(new EmptyBorder(10, 10, 10, 10));
					panel2.setLayout(new BorderLayout(0, 10));

					//---- label1 ----
					label1.setText("Local Directory"); //$NON-NLS-1$
					label1.setFont(new Font("Tahoma", Font.BOLD, 14)); //$NON-NLS-1$
					panel2.add(label1, BorderLayout.NORTH);

					//======== panel6 ========
					{

						//---- label2 ----
						label2.setText("Path:"); //$NON-NLS-1$
						label2.setFont(new Font("Tahoma", Font.BOLD, 11)); //$NON-NLS-1$

						//---- localDirectoryField ----
						localDirectoryField.setColumns(30);

						//---- localDirectoryButton ----
						localDirectoryButton.setText("..."); //$NON-NLS-1$
						localDirectoryButton.setActionCommand("..."); //$NON-NLS-1$
						localDirectoryButton.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								localDirectoryButtonClicked(e);
							}
						});

						//---- label3 ----
						label3.setText("(e.g. c:\\data\\cmpgn_images)"); //$NON-NLS-1$
						label3.setFont(new Font("Tahoma", Font.ITALIC, 11)); //$NON-NLS-1$

						GroupLayout panel6Layout = new GroupLayout(panel6);
						panel6.setLayout(panel6Layout);
						panel6Layout.setHorizontalGroup(
							panel6Layout.createParallelGroup()
								.addGroup(panel6Layout.createSequentialGroup()
									.addComponent(label2)
									.addGap(18, 18, 18)
									.addGroup(panel6Layout.createParallelGroup()
										.addGroup(panel6Layout.createSequentialGroup()
											.addComponent(localDirectoryField, GroupLayout.DEFAULT_SIZE, 339, Short.MAX_VALUE)
											.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
											.addComponent(localDirectoryButton)
											.addContainerGap())
										.addComponent(label3, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
						);
						panel6Layout.setVerticalGroup(
							panel6Layout.createParallelGroup()
								.addGroup(panel6Layout.createSequentialGroup()
									.addContainerGap()
									.addGroup(panel6Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
										.addComponent(label2)
										.addComponent(localDirectoryButton)
										.addComponent(localDirectoryField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
									.addGap(0, 0, 0)
									.addComponent(label3)
									.addContainerGap(170, Short.MAX_VALUE))
						);
					}
					panel2.add(panel6, BorderLayout.CENTER);
				}
				tabPane.addTab("", new ImageIcon(getClass().getResource("/com/t3/client/image/folder.png")), panel2); //$NON-NLS-1$ //$NON-NLS-2$

				//======== panel3 ========
				{
					panel3.setBorder(new EmptyBorder(10, 10, 10, 10));
					panel3.setLayout(new BorderLayout(0, 10));

					//---- label5 ----
					label5.setText("URL"); //$NON-NLS-1$
					label5.setFont(new Font("Tahoma", Font.BOLD, 14)); //$NON-NLS-1$
					panel3.add(label5, BorderLayout.NORTH);

					//======== panel5 ========
					{

						//---- label7 ----
						label7.setText("Name:"); //$NON-NLS-1$
						label7.setFont(new Font("Tahoma", Font.BOLD, 11)); //$NON-NLS-1$

						//---- label8 ----
						label8.setText("(e.g. My Campaign Images)"); //$NON-NLS-1$
						label8.setFont(new Font("Tahoma", Font.ITALIC, 11)); //$NON-NLS-1$

						//---- label6 ----
						label6.setText("URL:"); //$NON-NLS-1$
						label6.setFont(new Font("Tahoma", Font.BOLD, 11)); //$NON-NLS-1$

						//---- label4 ----
						label4.setText("(e.g. http://myhost.com/cmpgn_images.zip)"); //$NON-NLS-1$
						label4.setFont(new Font("Tahoma", Font.ITALIC, 11)); //$NON-NLS-1$

						GroupLayout panel5Layout = new GroupLayout(panel5);
						panel5.setLayout(panel5Layout);
						panel5Layout.setHorizontalGroup(
							panel5Layout.createParallelGroup()
								.addGroup(panel5Layout.createSequentialGroup()
									.addGroup(panel5Layout.createParallelGroup()
										.addComponent(label6)
										.addComponent(label7))
									.addGap(18, 18, 18)
									.addGroup(panel5Layout.createParallelGroup()
										.addComponent(label8, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(urlNameField)
										.addComponent(urlField)
										.addComponent(label4, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
									.addContainerGap())
						);
						panel5Layout.setVerticalGroup(
							panel5Layout.createParallelGroup()
								.addGroup(panel5Layout.createSequentialGroup()
									.addContainerGap()
									.addGroup(panel5Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
										.addComponent(urlNameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(label7))
									.addGap(0, 0, 0)
									.addComponent(label8)
									.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
									.addGroup(panel5Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
										.addComponent(urlField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(label6))
									.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
									.addComponent(label4)
									.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						);
					}
					panel3.add(panel5, BorderLayout.CENTER);
				}
				tabPane.addTab("", new ImageIcon(getClass().getResource("/com/t3/client/image/download.png")), panel3); //$NON-NLS-1$ //$NON-NLS-2$

				//======== panel4 ========
				{
					panel4.setBorder(new EmptyBorder(10, 10, 10, 10));
					panel4.setLayout(new BorderLayout(10, 10));

					//======== panel7 ========
					{
						panel7.setLayout(new BorderLayout());

						//---- label9 ----
						label9.setText("Tabletoptool Library"); //$NON-NLS-1$
						label9.setFont(new Font("Tahoma", Font.BOLD, 14)); //$NON-NLS-1$
						panel7.add(label9, BorderLayout.CENTER);

						//---- resourceListDownloadingLabel ----
						resourceListDownloadingLabel.setText("Loading..."); //$NON-NLS-1$
						panel7.add(resourceListDownloadingLabel, BorderLayout.EAST);
					}
					panel4.add(panel7, BorderLayout.NORTH);

					//======== scrollPane1 ========
					{
						scrollPane1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

						//---- tabletoptoolList ----
						tabletoptoolList.setBorder(new EmptyBorder(5, 5, 5, 5));
						scrollPane1.setViewportView(tabletoptoolList);
					}
					panel4.add(scrollPane1, BorderLayout.CENTER);
				}
				tabPane.addTab("", new ImageIcon(getClass().getResource("/com/t3/client/image/rptools_icon.png")), panel4); //$NON-NLS-1$ //$NON-NLS-2$
			}
			dialogPane.add(tabPane, BorderLayout.CENTER);
		}
		contentPane.add(dialogPane, BorderLayout.CENTER);
		pack();
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - Manuel Hegner
	protected JPanel dialogPane;
	protected JPanel buttonBar;
	protected JButton installButton;
	protected JButton cancelButton;
	protected JTabbedPane tabPane;
	protected JPanel panel2;
	protected JLabel label1;
	protected JPanel panel6;
	protected JLabel label2;
	protected JTextField localDirectoryField;
	protected JButton localDirectoryButton;
	protected JLabel label3;
	protected JPanel panel3;
	protected JLabel label5;
	protected JPanel panel5;
	protected JLabel label7;
	protected JTextField urlNameField;
	protected JLabel label8;
	protected JLabel label6;
	protected JTextField urlField;
	protected JLabel label4;
	protected JPanel panel4;
	protected JPanel panel7;
	protected JLabel label9;
	protected JLabel resourceListDownloadingLabel;
	protected JScrollPane scrollPane1;
	protected JList<com.t3.client.ui.AddResourceDialog.LibraryRow> tabletoptoolList;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
