package com.t3.client.ui.forms;

import javax.swing.AbstractListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import com.jgoodies.forms.factories.CC;
import com.jgoodies.forms.layout.FormLayout;
import com.jidesoft.swing.CheckBoxListWithSelectable;

/**
 * @author Virenerus
 */
public abstract class UpdateRepoBaseDialog extends JPanel {

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - Manuel Hegner
	protected JLabel label1;
	protected JLabel label2;
	protected JLabel label3;
	protected JLabel label4;
	protected JLabel label5;
	protected JButton _okButton;
	protected JButton _cancelButton;
	protected JTextField _hostname;
	protected JTextField _username;
	protected JPasswordField _password;
	protected JTextField _saveTo;
	protected JLabel label6;
	protected JScrollPane scrollPane1;
	protected CheckBoxListWithSelectable checkBoxList;
	protected JLabel label7;
	protected JTextField _directory;
	protected JLabel label8;
	protected JLabel label9;
	protected JCheckBox _subdir;
	// JFormDesigner - End of variables declaration  //GEN-END:variables

	public UpdateRepoBaseDialog() {
		initComponents();
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - Manuel Hegner
		label1 = new JLabel();
		label2 = new JLabel();
		label3 = new JLabel();
		label4 = new JLabel();
		label5 = new JLabel();
		_okButton = new JButton();
		_cancelButton = new JButton();
		_hostname = new JTextField();
		_username = new JTextField();
		_password = new JPasswordField();
		_saveTo = new JTextField();
		label6 = new JLabel();
		scrollPane1 = new JScrollPane();
		checkBoxList = new CheckBoxListWithSelectable();
		label7 = new JLabel();
		_directory = new JTextField();
		label8 = new JLabel();
		label9 = new JLabel();
		_subdir = new JCheckBox();


		// JFormDesigner evaluation mark
		setBorder(new javax.swing.border.CompoundBorder(
			new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
				"JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER, //$NON-NLS-1$
				javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12), //$NON-NLS-1$
				java.awt.Color.red), getBorder())); addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}}); //$NON-NLS-1$

		setLayout(new FormLayout(
			"$ugap, default, 4dlu, default:grow, 3*(4dlu, default), $ugap", //$NON-NLS-1$
			"2*($rgap), default, $nlgap, default, $rgap, default:grow, [10px,default], default, $nlgap, 2*(default, [10px,default]), 3*(default, $nlgap), default, [10px,default], $rgap")); //$NON-NLS-1$
		((FormLayout)getLayout()).setRowGroups(new int[][] {{15, 17, 19, 21}});

		label1.setText("Save To:"); //$NON-NLS-1$
		add(label1, CC.xy(2, 13, CC.RIGHT, CC.DEFAULT));

		label2.setText("Repositories are listed in the same order as they appear in the Campaign Properties."); //$NON-NLS-1$
		add(label2, CC.xywh(2, 3, 9, 1));

		label3.setText("FTP Server:"); //$NON-NLS-1$
		add(label3, CC.xy(2, 15, CC.RIGHT, CC.DEFAULT));

		label4.setText("Username:"); //$NON-NLS-1$
		add(label4, CC.xy(2, 19, CC.RIGHT, CC.DEFAULT));

		label5.setText("Password:"); //$NON-NLS-1$
		add(label5, CC.xy(2, 21, CC.RIGHT, CC.DEFAULT));

		_okButton.setText("OK"); //$NON-NLS-1$
		_okButton.setActionCommand("OK"); //$NON-NLS-1$
		_okButton.setToolTipText("Begin the update process."); //$NON-NLS-1$
		add(_okButton, CC.xy(8, 21));

		_cancelButton.setText("Cancel"); //$NON-NLS-1$
		_cancelButton.setActionCommand("Cancel"); //$NON-NLS-1$
		_cancelButton.setToolTipText("Cancel the update."); //$NON-NLS-1$
		add(_cancelButton, CC.xy(10, 21));

		_hostname.setToolTipText("The FTP server name may be a hostname or an IP address.  When using an IPv6 address surround it with \"[\" and \"]\"."); //$NON-NLS-1$
		add(_hostname, CC.xywh(4, 15, 7, 1));

		_username.setToolTipText("The username to login as on the FTP server."); //$NON-NLS-1$
		add(_username, CC.xy(4, 19));

		_password.setToolTipText("The password associated with the above username.  It will not display here nor will it be saved for future use."); //$NON-NLS-1$
		add(_password, CC.xy(4, 21));

		_saveTo.setToolTipText("Set this to one of the repositories in the list above.  You may double-click an entry from the list, use keyboard cut/paste, or type in the text."); //$NON-NLS-1$
		add(_saveTo, CC.xywh(4, 13, 7, 1));

		label6.setText("<html>Double-click a repository to copy the repository location into the <b>Save To:</b> field."); //$NON-NLS-1$
		add(label6, CC.xywh(2, 9, 9, 1));

		{

			checkBoxList.setModel(new AbstractListModel<String>() {
				String[] values = {
					"http://rptools.net/images-indexes/gallery.rpax.gz" //$NON-NLS-1$
				};
				@Override
				public int getSize() { return values.length; }
				@Override
				public String getElementAt(int i) { return values[i]; }
			});
			checkBoxList.setToolTipText("<html>These repositories are from the <b>Campaign Properties</b> on the <b>Edit</b> menu."); //$NON-NLS-1$
			scrollPane1.setViewportView(checkBoxList);
		}
		add(scrollPane1, CC.xywh(2, 7, 9, 1, CC.DEFAULT, CC.FILL));

		label7.setText("Directory on Server:"); //$NON-NLS-1$
		add(label7, CC.xy(2, 17));

		_directory.setSelectionEnd(1);
		_directory.setText("/"); //$NON-NLS-1$
		_directory.setSelectionStart(1);
		_directory.setToolTipText("<html>If your FTP login requires that you change directory to navigate to the location of the repository's <b>index.gz</b> directory, enter the directory name here."); //$NON-NLS-1$
		add(_directory, CC.xywh(4, 17, 4, 1));

		label8.setText("Place a checkmark in each repository that you want to search for existing assets."); //$NON-NLS-1$
		add(label8, CC.xywh(2, 5, 9, 1));

		label9.setText("<html>This is the <b>index.gz</b> that will be updated with the new image information."); //$NON-NLS-1$
		add(label9, CC.xywh(2, 11, 9, 1));

		_subdir.setText("Create subdir on server?"); //$NON-NLS-1$
		_subdir.setSelected(true);
		_subdir.setActionCommand("Create subdir on server?"); //$NON-NLS-1$
		_subdir.setToolTipText("Some FTP servers may not allow TabletopTool to create a directory.  If this is the case for your server yet you want new assets in their own directory, uncheck this field and create the directory yourself, then specify that directory as the location on the server."); //$NON-NLS-1$
		add(_subdir, CC.xywh(6, 19, 5, 1, CC.LEFT, CC.DEFAULT));
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}
}
