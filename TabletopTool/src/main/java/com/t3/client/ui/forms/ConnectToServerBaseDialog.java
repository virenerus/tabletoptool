package com.t3.client.ui.forms;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import com.jgoodies.forms.factories.CC;
import com.jgoodies.forms.layout.FormLayout;

/**
 * @author Virenerus
 */
public abstract class ConnectToServerBaseDialog extends JPanel {

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - Manuel Hegner
	protected JLabel label1;
	protected JLabel label2;
	protected JLabel label3;
	protected JTextField _username;
	protected JTextField _password;
	protected JComboBox _role;
	protected JTabbedPane tabPane;
	protected JPanel tabletoptoolPanel;
	protected JLabel label4;
	protected JButton refreshButton;
	protected JTextField _serverName;
	protected JScrollPane scrollPane1;
	protected JTable aliasTable;
	protected JPanel lanPanel;
	protected JLabel label5;
	protected JScrollPane scrollPane2;
	protected JList localServerList;
	protected JButton rescanButton;
	protected JPanel directPanel;
	protected JLabel label6;
	protected JLabel label7;
	protected JTextField _host;
	protected JTextField _port;
	protected JPanel panel1;
	protected JButton okButton;
	protected JButton cancelButton;
	// JFormDesigner - End of variables declaration  //GEN-END:variables

	public ConnectToServerBaseDialog() {
		initComponents();
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - Manuel Hegner
		label1 = new JLabel();
		label2 = new JLabel();
		label3 = new JLabel();
		_username = new JTextField();
		_password = new JTextField();
		_role = new JComboBox();
		tabPane = new JTabbedPane();
		tabletoptoolPanel = new JPanel();
		label4 = new JLabel();
		refreshButton = new JButton();
		_serverName = new JTextField();
		scrollPane1 = new JScrollPane();
		aliasTable = new JTable();
		lanPanel = new JPanel();
		label5 = new JLabel();
		scrollPane2 = new JScrollPane();
		localServerList = new JList();
		rescanButton = new JButton();
		directPanel = new JPanel();
		label6 = new JLabel();
		label7 = new JLabel();
		_host = new JTextField();
		_port = new JTextField();
		panel1 = new JPanel();
		okButton = new JButton();
		cancelButton = new JButton();


		// JFormDesigner evaluation mark
		setBorder(new javax.swing.border.CompoundBorder(
			new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
				"JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER, //$NON-NLS-1$
				javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12), //$NON-NLS-1$
				java.awt.Color.red), getBorder())); addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}}); //$NON-NLS-1$

		setLayout(new FormLayout(
			"[10px,default], right:default, 2*(default), default:grow, [10px,default]", //$NON-NLS-1$
			"3*([10px,default], default), [10px,default], default:grow, [10px,default], default, [10px,default]")); //$NON-NLS-1$

		label1.setText("Username:"); //$NON-NLS-1$
		add(label1, CC.xy(2, 2));

		label2.setText("Password"); //$NON-NLS-1$
		add(label2, CC.xy(2, 4));

		label3.setText("Role:"); //$NON-NLS-1$
		add(label3, CC.xy(2, 6));
		add(_username, CC.xywh(4, 2, 2, 1));
		add(_password, CC.xywh(4, 4, 2, 1));
		add(_role, CC.xy(4, 6));

		{

			{
				tabletoptoolPanel.setLayout(new FormLayout(
					"[10px,default], 2*(default), default:grow, 2*(default), [10px,default]", //$NON-NLS-1$
					"[10px,default], default, [10px,default], default:grow, [10px,default]")); //$NON-NLS-1$

				label4.setText("Server Name:"); //$NON-NLS-1$
				tabletoptoolPanel.add(label4, CC.xy(2, 2));

				refreshButton.setText("Refresh"); //$NON-NLS-1$
				refreshButton.setActionCommand("Refresh"); //$NON-NLS-1$
				tabletoptoolPanel.add(refreshButton, CC.xy(6, 2));
				tabletoptoolPanel.add(_serverName, CC.xy(4, 2));

				{
					scrollPane1.setViewportView(aliasTable);
				}
				tabletoptoolPanel.add(scrollPane1, CC.xywh(2, 4, 5, 1, CC.FILL, CC.FILL));
			}
			tabPane.addTab("RPTools.net", tabletoptoolPanel); //$NON-NLS-1$

			{
				lanPanel.setLayout(new FormLayout(
					"[10px,default], default:grow, default, [10px,default]", //$NON-NLS-1$
					"[10px,default], default, [10px,default], default:grow, [10px,default]")); //$NON-NLS-1$

				label5.setText("Local Servers:"); //$NON-NLS-1$
				lanPanel.add(label5, CC.xy(2, 2));

				{
					scrollPane2.setViewportView(localServerList);
				}
				lanPanel.add(scrollPane2, CC.xywh(2, 4, 2, 1, CC.FILL, CC.FILL));

				rescanButton.setText("Rescan"); //$NON-NLS-1$
				rescanButton.setActionCommand("Rescan"); //$NON-NLS-1$
				lanPanel.add(rescanButton, CC.xy(3, 2));
			}
			tabPane.addTab("LAN", lanPanel); //$NON-NLS-1$

			{
				directPanel.setLayout(new FormLayout(
					"[10px,default], default, [10px,default], [48dlu,default], default:grow, [10px,default]", //$NON-NLS-1$
					"2*([10px,default], default), [10px,default]")); //$NON-NLS-1$

				label6.setText("Address:"); //$NON-NLS-1$
				directPanel.add(label6, CC.xy(2, 2));

				label7.setText("Port:"); //$NON-NLS-1$
				directPanel.add(label7, CC.xy(2, 4));
				directPanel.add(_host, CC.xywh(4, 2, 2, 1));

				_port.setColumns(5);
				directPanel.add(_port, CC.xy(4, 4));
			}
			tabPane.addTab("Direct", directPanel); //$NON-NLS-1$
		}
		add(tabPane, CC.xywh(2, 8, 4, 1, CC.FILL, CC.FILL));

		{
			panel1.setLayout(new FormLayout(
				"default, [10px,default], default", //$NON-NLS-1$
				"default")); //$NON-NLS-1$

			okButton.setText("OK"); //$NON-NLS-1$
			okButton.setActionCommand("OK"); //$NON-NLS-1$
			panel1.add(okButton, CC.xy(1, 1));

			cancelButton.setText("Cancel"); //$NON-NLS-1$
			cancelButton.setActionCommand("JButton"); //$NON-NLS-1$
			panel1.add(cancelButton, CC.xy(3, 1));
		}
		add(panel1, CC.xywh(2, 10, 4, 1, CC.RIGHT, CC.DEFAULT));
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}
}
