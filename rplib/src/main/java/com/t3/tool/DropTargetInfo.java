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
package com.t3.tool;

import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.t3.transferable.ImageTransferableHandler;

/**
 * This class will show a frame that accepts system drag-and-drop events
 * it is a discovery tool useful to determine which flavors a drop from a specific application 
 * supports (such as a browser)
 */
public class DropTargetInfo extends JFrame implements DropTargetListener{

	JLabel label = new JLabel("Drop here");
	
    public DropTargetInfo() {
        super ("Drag and drop into this window");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocation(300,200);
        setSize(200, 200);
        
        new DropTarget(this, this);
        
        add(label);
    }
    
    public static void main(String[] args) {
        
        DropTargetInfo dti = new DropTargetInfo();
        
        dti.setVisible(true);
    }
    
    ////
    // DROP TARGET LISTENER
    @Override
	public void dragEnter(DropTargetDragEvent dtde) {
    }
    
    @Override
	public void dragExit(DropTargetEvent dte) {
    }

    @Override
	public void dragOver(DropTargetDragEvent dtde) {
    }
    
    @Override
	public void drop(DropTargetDropEvent dtde) {
    	
    	dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
    	
    	Object handlerObj = null;
    	try {
    		handlerObj = new ImageTransferableHandler().getTransferObject(dtde.getTransferable());

    		System.out.println ("DropAction:" + dtde.getDropAction());
            System.out.println ("Source:" + dtde.getSource());
            System.out.println ("DropTargetContext:" + dtde.getDropTargetContext());
            System.out.println ("Data Flavors:");
            for (DataFlavor flavor : dtde.getCurrentDataFlavorsAsList()) {
            	try {
            		System.out.println ("\t" + flavor.getMimeType());
            	} catch (Exception e) {
            		System.out.println ("\t\tfailed");
            	}
            }
            
            System.out.println ("--------------------");
            label.setIcon(new ImageIcon(new ImageTransferableHandler().getTransferObject(dtde.getTransferable())));
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	
    }
    
    @Override
	public void dropActionChanged(DropTargetDragEvent dtde) {
    }
}
