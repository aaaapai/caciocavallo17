/*
 * Copyright 2009 Sun Microsystems, Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Sun designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Sun in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Sun Microsystems, Inc., 4150 Network Circle, Santa Clara,
 * CA 95054 USA or visit www.sun.com if you need additional information or
 * have any questions.
 */

package com.github.caciocavallosilano.cacio.peer;

import java.awt.AWTEvent;
import java.awt.AWTException;
import java.awt.BufferCapabilities;
import java.awt.BufferCapabilities.FlipContents;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.FocusEvent;
import java.awt.event.PaintEvent;
import java.awt.event.WindowEvent;
import java.awt.image.ColorModel;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.awt.image.VolatileImage;
import java.awt.peer.ComponentPeer;
import java.awt.peer.ContainerPeer;
import java.awt.peer.WindowPeer;
import sun.java2d.pipe.Region;

class ProxyWindowPeer implements WindowPeer {

    private ProxyWindow proxyWindow;

    ProxyWindowPeer(ProxyWindow pw) {
        proxyWindow = pw;
    }

    CacioComponentPeer getTarget() {
        return proxyWindow.getTargetPeer();
    }

    @Override
    public void toFront() {
        // TODO: Maybe call target.toFront() here?
    }

    @Override
    public void toBack() {
        // TODO: Maybe call target.toBack() here?
    }

    public void setAlwaysOnTop(boolean alwaysOnTop) {
        // TODO: Maybe call target.setAlwaysOnTop here?
    }

    @Override
    public void updateAlwaysOnTopState() {
    }

    @Override
    public void updateFocusableWindowState() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setModalBlocked(Dialog blocker, boolean blocked) {
        // Nothing to do here yet.
    }

    @Override
    public void updateMinimumSize() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void updateIconImages() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Insets getInsets() {
        return new Insets(0, 0, 0, 0);
    }

    @Override
    public void beginValidate() {
        // Nothing to do here yet.
    }

    @Override
    public void endValidate() {
        // Nothing to do here yet.
    }

    @Override
    public void beginLayout() {
        // Nothing to do here yet.
    }

    @Override
    public void endLayout() {
        // Nothing to do here yet.
    }


    @Override
    public boolean isObscured() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean canDetermineObscurity() {
        return false;
    }

    @Override
    public void setVisible(boolean v) {
        // Nothing to do here yet.
    }

    @Override
    public void setEnabled(boolean e) {
        // Nothing to do here yet.
    }

    @Override
    public void paint(Graphics g) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void print(Graphics g) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setBounds(int x, int y, int width, int height, int op) {
        // Nothing to do here yet.
    }

    @Override
    public void handleEvent(AWTEvent e) {

        /* correctly dispatch events from windows borders menues and buttons */
        if (e instanceof WindowEvent) {
            getTarget().getAWTComponent().dispatchEvent(e);
        }
    }

    @Override
    public void coalescePaintEvent(PaintEvent e) {
        // Nothing to do here.
    }

    @Override
    public Point getLocationOnScreen() {
        return getTarget().getLocationOnScreen();
    }

    @Override
    public Dimension getPreferredSize() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Dimension getMinimumSize() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ColorModel getColorModel() {
        return getTarget().getColorModel();
    }

    public Toolkit getToolkit() {
        return getTarget().getToolkit();
    }

    @Override
    public Graphics getGraphics() {
        return getTarget().getGraphics();
    }

    @Override
    public FontMetrics getFontMetrics(Font font) {
        return getTarget().getFontMetrics(font);
    }

    @Override
    public void dispose() {
        CacioToolkit.disposePeer(proxyWindow, this);
    }

    @Override
    public void setForeground(Color c) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setBackground(Color c) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setFont(Font f) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void updateCursorImmediately() {
        getTarget().updateCursorImmediately();
    }

    @Override
    public boolean requestFocus(Component lightweightChild, boolean temporary,
                                boolean focusedWindowChangeAllowed, long time,
                                FocusEvent.Cause cause) {

        return getTarget().requestFocus(lightweightChild, temporary,
                focusedWindowChangeAllowed, time, cause);
    }

    @Override
    public boolean isFocusable() {
        return getTarget().isFocusable();
    }

    @Override
    public Image createImage(int width, int height) {
        return getTarget().createImage(width, height);
    }

    @Override
    public VolatileImage createVolatileImage(int width, int height) {
        return getTarget().createVolatileImage(width, height);
    }

    @Override
    public GraphicsConfiguration getAppropriateGraphicsConfiguration(GraphicsConfiguration gc) {
           throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public GraphicsConfiguration getGraphicsConfiguration() {
        return getTarget().getGraphicsConfiguration();
    }

    @Override
    public boolean handlesWheelScrolling() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void createBuffers(int numBuffers, BufferCapabilities caps) throws AWTException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Image getBackBuffer() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void flip(int x1, int y1, int x2, int y2, FlipContents flipAction) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void destroyBuffers() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void reparent(ContainerPeer newContainer) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isReparentSupported() {
        return false;
    }

    @Override
    public void layout() {
        // Nothing to do here yet.
    }

    @Override
    public void applyShape(Region shape) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setOpacity(float opacity) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setOpaque(boolean isOpaque) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setZOrder(ComponentPeer above) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void updateWindow() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean updateGraphicsData(GraphicsConfiguration gc) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
