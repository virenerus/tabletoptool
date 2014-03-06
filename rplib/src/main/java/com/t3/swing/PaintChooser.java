/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 */
package com.t3.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Paint;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class PaintChooser extends JPanel {
	
	private JColorChooser swatchColorChooser;
	private JColorChooser hueColorChooser;
	private JColorChooser rgbColorChooser;
	
	private Paint paint;
	
	private PaintedPanel previewPanel;
	
	private JTabbedPane tabbedPane;
	
	private JDialog dialog;
	
	public PaintChooser() {
		setLayout(new BorderLayout());

		tabbedPane = new JTabbedPane();
		
		previewPanel = new PaintedPanel();
		previewPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		previewPanel.setPreferredSize(new Dimension(150, 100));
		
		swatchColorChooser = createSwatchColorChooser();
		swatchColorChooser.setColor(Color.white);

		hueColorChooser = createHueColorChooser();
		hueColorChooser.setColor(Color.white);

		rgbColorChooser = createRGBColorChooser();
		rgbColorChooser.setColor(Color.white);

		tabbedPane.addTab("Swatch", swatchColorChooser);
		tabbedPane.addTab("Hue", hueColorChooser);
		tabbedPane.addTab("RGB", rgbColorChooser);
		
		add(BorderLayout.CENTER, tabbedPane);
		add(BorderLayout.SOUTH, createSouthPanel());
	}
	
	public void addPaintChooser(AbstractPaintChooserPanel panel) {
		tabbedPane.addTab(panel.getDisplayName(), panel);
	}

	public Paint getPaint() {
		return paint;
	}
	
	public void setPaint(Paint paint) {
		this.paint = paint;
		previewPanel.setPaint(paint);
	}
	
	private JPanel createSouthPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5), BorderFactory.createLoweredBevelBorder()));
		panel.add(previewPanel);
		
		return panel;
	}

	private JPanel createButtonPanel(JDialog dialog) {
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		
		panel.add(createOKButton(dialog));
		panel.add(createCancelButton(dialog));
		
		return panel;
	}
	
	private JButton createOKButton(final JDialog dialog) {
		JButton button = new JButton("OK");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				dialog.setVisible(false);
			}
		});
		
		return button;
	}
	
	private JButton createCancelButton(final JDialog dialog) {
		JButton button = new JButton("Cancel");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				paint = null;
				dialog.setVisible(false);
			}
		});
		
		return button;
	}
	
	private JColorChooser createSwatchColorChooser() {
		final JColorChooser chooser = new JColorChooser();
		
		for (AbstractColorChooserPanel panel : chooser.getChooserPanels()) {
			if (!panel.getDisplayName().startsWith("S")) { // Swatch panel
				chooser.removeChooserPanel(panel);
			}
		}

		chooser.setPreviewPanel(new JLabel());
		chooser.getSelectionModel().addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e) {
				setPaint(chooser.getColor());
				
				hueColorChooser.setColor(chooser.getColor());
				rgbColorChooser.setColor(chooser.getColor());
			}
		});
		return chooser;
	}
	
	private JColorChooser createHueColorChooser() {
		final JColorChooser chooser = new JColorChooser();
		
		for (AbstractColorChooserPanel panel : chooser.getChooserPanels()) {
			if (!panel.getDisplayName().startsWith("H")) { // Swatch panel
				chooser.removeChooserPanel(panel);
			}
		}

		chooser.setPreviewPanel(new JLabel());
		chooser.getSelectionModel().addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e) {
				setPaint(chooser.getColor());
				
				swatchColorChooser.setColor(chooser.getColor());
				rgbColorChooser.setColor(chooser.getColor());
			}
		});
		return chooser;
	}
	
	private JColorChooser createRGBColorChooser() {
		final JColorChooser chooser = new JColorChooser();
		
		for (AbstractColorChooserPanel panel : chooser.getChooserPanels()) {
			if (!panel.getDisplayName().startsWith("R")) { // Swatch panel
				chooser.removeChooserPanel(panel);
			}
		}

		chooser.setPreviewPanel(new JLabel());
		chooser.getSelectionModel().addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e) {
				setPaint(chooser.getColor());
				
				swatchColorChooser.setColor(chooser.getColor());
				hueColorChooser.setColor(chooser.getColor());
			}
		});
		return chooser;
	}
	
	public Paint choosePaint(Frame owner, Paint paint) {
		return choosePaint(owner, paint, "Choose Paint");
	}
	
	public Paint choosePaint(Frame owner, Paint paint, String title) {
		
		if (dialog == null) {
			dialog = new JDialog(owner, true);
			dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
			dialog.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e) {
					PaintChooser.this.paint = null;
					dialog.setVisible(false);
				}
			});
	
			JPanel panel = new JPanel(new BorderLayout());
			panel.add(BorderLayout.CENTER, this);
			panel.add(BorderLayout.SOUTH, createButtonPanel(dialog));
			
			dialog.setContentPane(panel);
			dialog.pack();
			SwingUtil.centerOver(dialog, owner);
		}
		
		dialog.setTitle(title);
		
		setPaint(paint);
		
		dialog.setVisible(true);
		
		return getPaint(); 
	}
	
}
