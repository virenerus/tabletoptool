package com.t3.client.ui.forms;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import com.jgoodies.forms.factories.CC;
import com.jgoodies.forms.layout.FormLayout;

/**
 * @author Virenerus
 */
public abstract class ServerInfoBaseDialog extends JPanel {

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - Manuel Hegner
	protected JLabel label1;
	protected JLabel label2;
	protected JLabel label3;
	protected JLabel label4;
	protected JLabel label5;
	protected JButton okButton;
	protected JTextField name;
	protected JTextField localAddress;
	protected JTextField externalAddress;
	protected JTextField port;
	// JFormDesigner - End of variables declaration  //GEN-END:variables

	public ServerInfoBaseDialog() {
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
		okButton = new JButton();
		name = new JTextField();
		localAddress = new JTextField();
		externalAddress = new JTextField();
		port = new JTextField();


		// JFormDesigner evaluation mark
		setBorder(new javax.swing.border.CompoundBorder(
			new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
				"JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER, //$NON-NLS-1$
				javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12), //$NON-NLS-1$
				java.awt.Color.red), getBorder())); addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}}); //$NON-NLS-1$

		setLayout(new FormLayout(
			"2*(default), left:default:grow, default", //$NON-NLS-1$
			"4*(default, [10px,default]), default, 18px, default")); //$NON-NLS-1$

		label1.setText("Name:"); //$NON-NLS-1$
		label1.setFont(new Font("Tahoma", Font.BOLD, 11)); //$NON-NLS-1$
		add(label1, CC.xy(1, 3));

		label2.setText("Local Address:"); //$NON-NLS-1$
		label2.setFont(new Font("Tahoma", Font.BOLD, 11)); //$NON-NLS-1$
		add(label2, CC.xy(1, 5));

		label3.setText("External Address:"); //$NON-NLS-1$
		label3.setFont(new Font("Tahoma", Font.BOLD, 11)); //$NON-NLS-1$
		add(label3, CC.xy(1, 7));

		label4.setText("Port:"); //$NON-NLS-1$
		label4.setFont(new Font("Tahoma", Font.BOLD, 11)); //$NON-NLS-1$
		add(label4, CC.xy(1, 9));

		label5.setHorizontalAlignment(SwingConstants.CENTER);
		label5.setText("Server Info"); //$NON-NLS-1$
		label5.setFont(new Font("Tahoma", Font.BOLD, 14)); //$NON-NLS-1$
		label5.setForeground(new Color(0, 0, 204));
		add(label5, CC.xywh(1, 1, 3, 1));

		okButton.setText("OK"); //$NON-NLS-1$
		okButton.setActionCommand("OK"); //$NON-NLS-1$
		add(okButton, CC.xywh(1, 11, 3, 1, CC.RIGHT, CC.DEFAULT));

		name.setSelectionEnd(4);
		name.setText("name"); //$NON-NLS-1$
		name.setOpaque(false);
		name.setBackground(new Color(236, 233, 216));
		name.setSelectionStart(4);
		name.setEditable(false);
		add(name, CC.xywh(3, 3, 2, 1));

		localAddress.setSelectionEnd(12);
		localAddress.setText("localAddress"); //$NON-NLS-1$
		localAddress.setOpaque(false);
		localAddress.setBackground(new Color(236, 233, 216));
		localAddress.setSelectionStart(12);
		localAddress.setEditable(false);
		add(localAddress, CC.xywh(3, 5, 2, 1));

		externalAddress.setSelectionEnd(15);
		externalAddress.setText("externalAddress"); //$NON-NLS-1$
		externalAddress.setOpaque(false);
		externalAddress.setBackground(new Color(236, 233, 216));
		externalAddress.setSelectionStart(15);
		externalAddress.setEditable(false);
		add(externalAddress, CC.xywh(3, 7, 2, 1));

		port.setSelectionEnd(4);
		port.setText("port"); //$NON-NLS-1$
		port.setOpaque(false);
		port.setBackground(new Color(236, 233, 216));
		port.setSelectionStart(4);
		port.setEditable(false);
		add(port, CC.xywh(3, 9, 2, 1));
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}
}
