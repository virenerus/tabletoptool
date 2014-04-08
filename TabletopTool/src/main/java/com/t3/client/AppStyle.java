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
package com.t3.client;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.t3.image.ImageUtil;
import com.t3.swing.ImageBorder;

/**
 * @author trevor
 */
public class AppStyle {

	public static ImageBorder border = ImageBorder.GRAY;
	public static ImageBorder selectedBorder = ImageBorder.RED;
	public static ImageBorder selectedStampBorder = ImageBorder.BLUE;
	public static ImageBorder selectedUnownedBorder = AppConstants.GREEN_BORDER;
	public static ImageBorder miniMapBorder = AppConstants.GRAY_BORDER;
	public static ImageBorder shadowBorder = AppConstants.SHADOW_BORDER;
	public static ImageBorder commonMacroBorder = AppConstants.HIGHLIGHT_BORDER;

	public static Font labelFont = Font.decode("serif-NORMAL-12");

	public static BufferedImage tokenInvisible;

	public static BufferedImage cellWaypointImage;

	public static BufferedImage stackImage;

	public static BufferedImage markerImage;

	public static Color selectionBoxOutline = Color.black;
	public static Color selectionBoxFill = Color.blue;

	public static BufferedImage chatImage;
	public static BufferedImage chatScrollImage;
	public static BufferedImage chatScrollLockImage;
	public static BufferedImage chatNotifyImage;

	public static BufferedImage showTypingNotification;
	public static BufferedImage hideTypingNotification;

	public static Color topologyColor = new Color(0, 0, 255, 128);
	public static Color topologyAddColor = new Color(255, 0, 0, 128);
	public static Color topologyRemoveColor = new Color(255, 255, 255, 128);

	public static BufferedImage boundedBackgroundTile;

	public static BufferedImage cancelButton;
	public static BufferedImage addButton;

	public static BufferedImage panelTexture;

	public static BufferedImage lookupTableDefaultImage;

	public static BufferedImage resourceLibraryImage;
	public static BufferedImage mapExplorerImage;
	public static BufferedImage connectionsImage;
	public static BufferedImage chatPanelImage;
	public static BufferedImage globalPanelImage;
	public static BufferedImage campaignPanelImage;
	public static BufferedImage selectionPanelImage;
	public static BufferedImage impersonatePanelImage;
	public static BufferedImage tablesPanelImage;
	public static BufferedImage initiativePanelImage;
	public static BufferedImage arrowOut;
	public static BufferedImage arrowRotateClockwise;
	public static BufferedImage arrowIn;
	public static BufferedImage arrowRight;
	public static BufferedImage arrowLeft;

	public static BufferedImage lightSourceIcon;

	static {

		try {
			// Set defaults
			tokenInvisible = ImageUtil.getCompatibleImage("com/t3/client/image/icon_invisible.png");
			cellWaypointImage = ImageUtil.getCompatibleImage("com/t3/client/image/redDot.png");
			stackImage = ImageUtil.getCompatibleImage("com/t3/client/image/stack.png");
			markerImage = ImageUtil.getCompatibleImage("com/t3/client/image/marker.png");
			chatImage = ImageUtil.getCompatibleImage("com/t3/client/image/chat-blue.png");
			chatScrollImage = ImageUtil.getCompatibleImage("com/t3/client/image/comments.png");
			chatScrollLockImage = ImageUtil.getCompatibleImage("com/t3/client/image/comments_delete.png");
			chatNotifyImage = ImageUtil.getCompatibleImage("com/t3/client/image/chat-red.png");

			// Typing notification icons added by Rumble
			showTypingNotification = ImageUtil.getCompatibleImage("com/t3/client/image/chatNotifyOn.png");
			hideTypingNotification = ImageUtil.getCompatibleImage("com/t3/client/image/chatNotifyOff.png");

			boundedBackgroundTile = ImageUtil.getCompatibleImage("com/t3/client/image/Black.png");
			panelTexture = ImageUtil.getCompatibleImage("com/t3/client/image/panelTexture.jpg");

			cancelButton = ImageUtil.getCompatibleImage("com/t3/client/image/cancel_sm.png");
			addButton = ImageUtil.getCompatibleImage("com/t3/client/image/add_sm.png");

			lookupTableDefaultImage = ImageUtil.getCompatibleImage("com/t3/client/image/document.jpg");

			resourceLibraryImage = ImageUtil.getCompatibleImage("com/t3/client/image/book_open.png");
			mapExplorerImage = ImageUtil.getCompatibleImage("com/t3/client/image/eye.png");
			connectionsImage = ImageUtil.getCompatibleImage("com/t3/client/image/computer.png");
			chatPanelImage = ImageUtil.getCompatibleImage("com/t3/client/image/application.png");
			globalPanelImage = ImageUtil.getCompatibleImage("com/t3/client/image/global_panel.png");
			campaignPanelImage = ImageUtil.getCompatibleImage("com/t3/client/image/campaign_panel.png");
			selectionPanelImage = ImageUtil.getCompatibleImage("com/t3/client/image/cursor.png");
			impersonatePanelImage = ImageUtil.getCompatibleImage("com/t3/client/image/impersonate.png");
			tablesPanelImage = ImageUtil.getCompatibleImage("com/t3/client/image/layers.png");
			initiativePanelImage = ImageUtil.getCompatibleImage("com/t3/client/image/initiativePanel.png");
			arrowOut = ImageUtil.getCompatibleImage("com/t3/client/image/arrow_out.png");
			arrowRotateClockwise = ImageUtil.getCompatibleImage("com/t3/client/image/arrow_rotate_clockwise.png");
			arrowIn = ImageUtil.getCompatibleImage("com/t3/client/image/arrow_in_red.png");
			arrowRight = ImageUtil.getCompatibleImage("com/t3/client/image/arrow_right.png");
			arrowLeft = ImageUtil.getCompatibleImage("com/t3/client/image/arrow_left.png");

			lightSourceIcon = ImageUtil.getCompatibleImage("com/t3/client/image/lightbulb.png");
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
}
