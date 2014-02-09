package net.rptools.maptool.script.mt2api.functions.input;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/** JLabel variant that listens for new image data, and redraws its icon. */
public class UpdatingLabel extends JLabel {
	private String macroLink;

	@Override
	public boolean imageUpdate(Image img, int infoflags, int x, int y, int w, int h) {
		Icon curIcon = getIcon();
		int curWidth = curIcon.getIconWidth();
		int curHeight = curIcon.getIconHeight();

		// Are we receiving the final image data?
		int flags = ImageObserver.ALLBITS | ImageObserver.FRAMEBITS;
		if ((infoflags & flags) == 0)
			return true;

		// Resize
		Dimension dim = new Dimension(curWidth, curHeight);
		BufferedImage sizedImage = new BufferedImage(dim.width, dim.height, Transparency.BITMASK);
		Graphics2D g = sizedImage.createGraphics();
		g.drawImage(img, 0, 0, dim.width, dim.height, null);

		// Update our Icon
		setIcon(new ImageIcon(sizedImage));
		return false;
	}

	public void setMacroLink(String link) {
		macroLink = link;
		/*this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
					System.out.println(macroLink);
			}
		});*/
	}
}
