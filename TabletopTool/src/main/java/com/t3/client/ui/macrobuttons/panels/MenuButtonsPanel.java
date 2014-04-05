/*
 *  This software copyright by various authors including the RPTools.net
 *  development team, and licensed under the LGPL Version 3 or, at your
 *  option, any later version.
 *
 *  Portions of this software were originally covered under the Apache
 *  Software License, Version 1.1 or Version 2.0.
 *
 *  See the file LICENSE elsewhere in this distribution for license details.
 */

package com.t3.client.ui.macrobuttons.panels;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import com.t3.client.AppStyle;
import com.t3.client.TabletopTool;
import com.t3.client.ui.zone.ZoneRenderer;
import com.t3.language.I18N;

public class MenuButtonsPanel extends JPanel {

	public MenuButtonsPanel() {
		//TODO: refactoring reminder
		setLayout(new FlowLayout(FlowLayout.LEFT));
		
		addSelectAllButton();
		addDeselectAllButton();
		addSpacer();
		addSelectPreviousButton();
		addSelectNextButton();
		addSpacer();
		addRevertToPreviousButton();
	}

	private void addSelectAllButton() {
		ImageIcon i = new ImageIcon(AppStyle.arrowOut);
		JButton label = new JButton(i) {
			@Override
			public Insets getInsets() {
				return new Insets(2, 2, 2, 2);
			}
		};
		label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent event) {
				ZoneRenderer renderer = TabletopTool.getFrame().getCurrentZoneRenderer();
				renderer.selectTokens(new Rectangle(renderer.getX(), renderer.getY(), renderer.getWidth(), renderer.getHeight()));
			}
		});
		label.setToolTipText(I18N.getText("panel.Selected.tooltip.selectAll"));
		label.setBackground(null);
		add(label);
	}
	
	private void addDeselectAllButton() {
		ImageIcon i3 = new ImageIcon(AppStyle.arrowIn);
		JButton label3 = new JButton(i3) {
			@Override
			public Insets getInsets() {
				return new Insets(2, 2, 2, 2);
			}
		};
		label3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent event) {
				ZoneRenderer renderer = TabletopTool.getFrame().getCurrentZoneRenderer();
				renderer.clearSelectedTokens();
			}
		});
		label3.setToolTipText(I18N.getText("panel.Selected.tooltip.deslectAll"));
		label3.setBackground(null);
		add(label3);
	}
	
	private void addRevertToPreviousButton() {
		ImageIcon i1 = new ImageIcon(AppStyle.arrowRotateClockwise);
		JButton label1 = new JButton(i1) {
			@Override
			public Insets getInsets() {
				return new Insets(2, 2, 2, 2);
			}
		};
		label1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent event) {
				TabletopTool.getFrame().getCurrentZoneRenderer().undoSelectToken();
			}
		});
		label1.setToolTipText(I18N.getText("panel.Selected.tooltip.revertToPrevious"));
		label1.setBackground(null);
		add(label1);
	}
	
	private void addSpacer() {
		JPanel panel = new JPanel() {
			@Override
			public Dimension getPreferredSize() {
				return new Dimension(10, 10);
			}
			
			@Override
			public Insets getInsets() {
				return new Insets(0, 0, 0, 0);
			}
		};
		add(panel);
	}
	
	private void addSelectNextButton() {
		ImageIcon i1 = new ImageIcon(AppStyle.arrowRight);
		JButton label1 = new JButton(i1) {
			@Override
			public Insets getInsets() {
				return new Insets(2, 2, 2, 2);
			}
		};
		label1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent event) {
				TabletopTool.getFrame().getCurrentZoneRenderer().cycleSelectedToken(1);
			}
		});
		label1.setToolTipText(I18N.getText("panel.Selected.tooltip.next"));
		label1.setBackground(null);
		add(label1);
	}

	private void addSelectPreviousButton() {
		ImageIcon i1 = new ImageIcon(AppStyle.arrowLeft);
		JButton label1 = new JButton(i1) {
			@Override
			public Insets getInsets() {
				return new Insets(2, 2, 2, 2);
			}
		};
		label1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent event) {
				TabletopTool.getFrame().getCurrentZoneRenderer().cycleSelectedToken(-1);
			}
		});
		label1.setToolTipText(I18N.getText("panel.Selected.tooltip.previous"));
		label1.setBackground(null);
		add(label1);
	}
	
	@Override
	public Dimension getPreferredSize() {

		Dimension size = getParent().getSize();

		FlowLayout layout = (FlowLayout) getLayout();
		Insets insets = getInsets();

		// This isn't exact, but hopefully it's close enough
		int x = layout.getHgap() + insets.left;
		int y = layout.getVgap();
		int rowHeight = 0;
		for (Component c : getComponents()) {

			Dimension cSize = c.getPreferredSize();
			if (x + cSize.width + layout.getHgap() > size.width - insets.right && x > 0) {
				x = 0;
				y += rowHeight + layout.getVgap();
				rowHeight = 0;
			}

			x += cSize.width + layout.getHgap();
			rowHeight = Math.max(cSize.height, rowHeight);
		}

		y += rowHeight + layout.getVgap();

		y += getInsets().top;
		y += getInsets().bottom;

		Dimension prefSize = new Dimension(size.width, y);
		return prefSize;
	}

}
