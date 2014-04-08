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
package com.t3.client.ui.zone;

import java.awt.Graphics2D;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.SwingUtilities;

import com.t3.client.TabletopTool;
import com.t3.client.swing.Animatable;
import com.t3.client.swing.AnimationManager;
import com.t3.util.GraphicsUtil;

public class NotificationOverlay implements ZoneOverlay, Animatable {

    private List<EventDetail> eventList = new CopyOnWriteArrayList<EventDetail>();

    // TODO: make this configurable
    private static final int MESSAGE_DELAY = 2500; 

    public NotificationOverlay () {
        AnimationManager.addAnimatable(this);
    }
    
    @Override
	public void paintOverlay(ZoneRenderer renderer, Graphics2D g) {

        int y = 15;
        for (EventDetail detail : eventList) {
            
            GraphicsUtil.drawBoxedString(g, detail.message, 10, y, SwingUtilities.LEFT);
            
            y += 20;
        }
    }

    public void addEvent(String message) {
        if (message == null) {
            return;
        }
        
        eventList.add(new EventDetail(message));
    }
    
    private static class EventDetail {
        
        public long timestamp;
        public String message;
        
        public EventDetail(String message) {
            this.message = message;
            timestamp = System.currentTimeMillis();
        }
    }
    
    ////
    // ANIMATABLE
    @Override
	public void animate() {

        boolean requiresRepaint = false;
        while (eventList.size() > 0) {
            
            EventDetail detail = eventList.get(0);
            if (System.currentTimeMillis() - detail.timestamp > MESSAGE_DELAY) {

                eventList.remove(0);
                requiresRepaint = true;
            } else {
                break;
            }
        }
        if (requiresRepaint) {
            ZoneRenderer renderer = TabletopTool.getFrame().getCurrentZoneRenderer();
            if (renderer != null) {
                renderer.repaint();
            }
        }
    }

}
