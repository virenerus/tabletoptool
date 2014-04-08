/*
 * Copyright (c) 2014 tabletoptool.com team.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 * 
 * Contributors:
 *     rptools.com team - initial implementation
 *     tabletoptool.com team - further development
 */
package com.t3.client.swing;

import java.awt.AWTEvent;
import java.awt.EventQueue;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import com.jidesoft.dialog.JideOptionPane;
import com.t3.client.TabletopTool;
import com.t3.language.I18N;

public class T3EventQueue extends EventQueue {
	private static final Logger log = Logger.getLogger(T3EventQueue.class);
	private static final JideOptionPane optionPane = new JideOptionPane(I18N.getString("T3EventQueue.details"), JOptionPane.ERROR_MESSAGE, JideOptionPane.CLOSE_OPTION); //$NON-NLS-1$
	
	@Override
	protected void dispatchEvent(AWTEvent event) {
		try {
			super.dispatchEvent(event);
		} catch (StackOverflowError soe) {
			log.error(soe, soe);
			optionPane.setTitle(I18N.getString("T3EventQueue.stackOverflow.title")); //$NON-NLS-1$
			optionPane.setDetails(I18N.getString("T3EventQueue.stackOverflow"));
			displayPopup();
		} catch (Throwable t) {
			log.error(t, t);
			optionPane.setTitle(I18N.getString("T3EventQueue.unexpectedError")); //$NON-NLS-1$
			optionPane.setDetails(toString(t));
			try {
				displayPopup();
			}
			catch (Throwable thrown) {
				// Displaying the error message using the JideOptionPane has just failed.
				// Fallback to standard swing dialog.
				log.error(thrown, thrown);
				JOptionPane.showMessageDialog(null, toString(thrown), I18N.getString("T3EventQueue.unexpectedError"), JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private static void displayPopup() {
		optionPane.setDetailsVisible(true);
		JDialog dialog = optionPane.createDialog(TabletopTool.getFrame(), I18N.getString("T3EventQueue.warning.title")); //$NON-NLS-1$
		dialog.setResizable(true);
		dialog.pack();
		dialog.setVisible(true);
	}

	private static String toString(Throwable t) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try(PrintStream ps = new PrintStream(out)) {
			t.printStackTrace(ps);
		}
		return out.toString();
	}
}
