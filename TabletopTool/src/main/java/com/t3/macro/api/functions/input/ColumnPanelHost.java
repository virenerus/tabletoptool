package com.t3.macro.api.functions.input;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.Scrollable;

/**
 * Wrapper panel provides alignment and scrolling to a ColumnPanel.
 * ColumnPanel uses GridBagLayout, which ignores size requests. Thus, the
 * ColumnPanel is always the size of its container, and is always centered.
 * We wrap the ColumnPanel in a ScrollingPanel to set the alignment and
 * desired maximum size. The ScrollingPanel is wrapped in a JScrollPane to
 * provide scrolling support.
 */
@SuppressWarnings("serial")
public final class ColumnPanelHost extends JScrollPane {
	ScrollingPanel panel;
	ColumnPanel columnPanel;

	ColumnPanelHost(ColumnPanel cp) {
		// get the width of the vertical scrollbar
		JScrollBar vscroll = getVerticalScrollBar();
		int scrollBarWidth = 20; // default value if we can't find the scrollbar
		if (vscroll != null) {
			Dimension d = vscroll.getMaximumSize();
			scrollBarWidth = (d == null) ? 20 : d.width;
		}

		// Cap the height of the contents at 3/4 of the screen height
		int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;

		panel = new ScrollingPanel(cp, scrollBarWidth, screenHeight * 5 / 8);
		setViewportView(panel);
		columnPanel = cp;

		panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		panel.add(columnPanel);

		// Restores focus to the appropriate input field in the nested ColumnPanel.
		ComponentAdapter onPanelShow = new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent ce) {
				columnPanel.restoreFocus();
			}
		};

		setBorder(null);
		addComponentListener(onPanelShow);
	}

	final class ScrollingPanel extends JPanel implements Scrollable {
		ColumnPanel cp;
		int scrollBarWidth;
		int maxHeight;

		ScrollingPanel(ColumnPanel cp, int scrollBarWidth, int maxHeight) {
			this.cp = cp;
			this.scrollBarWidth = scrollBarWidth;
			this.maxHeight = maxHeight;
		}

		// The Scrollable interface methods
		public Dimension getPreferredScrollableViewportSize() {
			Dimension d = cp.getPreferredSize();
			if (d.height > maxHeight || (d.height > cp.getParent().getHeight() && cp.getParent().getHeight() > 0)) {
				d.height = maxHeight + cp.maxHeightModifier;
				d.width += scrollBarWidth; // make room for the vertical scrollbar
			}
			return d;
		}

		public int getScrollableBlockIncrement(Rectangle visRect, int orientation, int direction) {
			int retval = visRect.height - 10;
			if (retval < 0)
				retval = 10;
			return retval;
		}

		public int getScrollableUnitIncrement(Rectangle visRect, int orientation, int direction) {
			return scrollBarWidth;
		}

		public boolean getScrollableTracksViewportHeight() {
			return false;
		}

		public boolean getScrollableTracksViewportWidth() {
			return false;
		}
	}
}
