/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.github.caciocavallosilano.cacio.ctc;

import com.github.caciocavallosilano.cacio.peer.managed.ExternalMouseReader;


/**
 *
 * @author maks
 */
public class InfdevGrabHandler{
    private static boolean eventSelfSubmitted;
    private static ExternalMouseReader externalMouseReader;
    private static boolean isGrabbing;
    
    private static int lx, ly;
    public static boolean robotMouseEvent(CTCRobotPeer sourcePeer, int x, int y) {
        if(!isGrabbing) return false;
        if(eventSelfSubmitted) {
            eventSelfSubmitted = false;
            return false;
        }
        if(externalMouseReader != null) {
            eventSelfSubmitted = true;
            int cx = externalMouseReader.getX(), cy = externalMouseReader.g<etY();
            sourcePeer.mouseMove(x + cx-lx, y + ly-cy);
            lx = cx;
            ly = cy;
        }
        return true;
    }
    public static void setMouseReader(ExternalMouseReader reader) {
        externalMouseReader = reader;
    }
    public static void setGrabbed(boolean grabbed) {
        isGrabbing = grabbed;
        if(isGrabbing) {
            lx = externalMouseReader.getX();
            ly = externalMouseReader.getY();
        }
    }
}
