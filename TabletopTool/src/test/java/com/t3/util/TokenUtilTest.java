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
package com.t3.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Transparency;
import java.awt.image.BufferedImage;

import junit.framework.TestCase;

import com.t3.image.ImageUtil;
import com.t3.model.Token;

public class TokenUtilTest extends TestCase {

	public void testGuessTokenType() throws Exception {

		// SQUARE
		BufferedImage img = new BufferedImage(100, 100, Transparency.BITMASK);
		Graphics2D g = img.createGraphics();
		g.setColor(Color.blue);
		g.fillRect(0, 0, 100, 100);
		g.dispose();
		
		assertEquals(Token.TokenShape.SQUARE, TokenUtil.guessTokenType(img));

		img = ImageUtil.getCompatibleImage("com/t3/client/image/squareToken.gif");
		
		assertEquals(Token.TokenShape.SQUARE, TokenUtil.guessTokenType(img));
		
		// CIRCLE
		img = new BufferedImage(100, 100, Transparency.BITMASK);
		g = img.createGraphics();
		g.setColor(Color.red);
		g.fillOval(0, 0, 100, 100);
		g.dispose();
		
		assertEquals(Token.TokenShape.CIRCLE, TokenUtil.guessTokenType(img));

		img = ImageUtil.getCompatibleImage("com/t3/client/image/circleToken.png");
		
		assertEquals(Token.TokenShape.CIRCLE, TokenUtil.guessTokenType(img));

		// TOP DOWN
		img = new BufferedImage(100, 100, Transparency.BITMASK);
		g = img.createGraphics();
		g.setColor(Color.red);
		g.fillOval(0, 0, 10, 10);
		g.fillOval(90, 90, 10, 10);
		g.fillRect(0, 50, 100, 10);
		g.dispose();
		
		assertEquals(Token.TokenShape.TOP_DOWN, TokenUtil.guessTokenType(img));
	}
}
