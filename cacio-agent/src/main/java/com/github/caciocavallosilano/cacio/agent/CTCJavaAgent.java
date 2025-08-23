package com.github.caciocavallosilano.cacio.agent;

import javax.swing.plaf.metal.MetalLookAndFeel;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.lang.instrument.Instrumentation;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import sun.misc.Unsafe;

public class CTCJavaAgent {
    
    public static void premain(String args, Instrumentation inst) {
        try {
            // 执行原始静态初始化块中的代码
            initializeCTCEnvironment();
            
            // 设置系统属性以使用Metal外观
            System.setProperty("swing.defaultlaf", MetalLookAndFeel.class.getName());
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static void initializeCTCEnvironment() throws Exception {
        Field toolkit = Toolkit.class.getDeclaredField("toolkit");
        toolkit.setAccessible(true);
        toolkit.set(null, new CTCToolkit());

        Field defaultHeadlessField = java.awt.GraphicsEnvironment.class.getDeclaredField("defaultHeadless");
        defaultHeadlessField.setAccessible(true);
        defaultHeadlessField.set(null, Boolean.TRUE);
        Field headlessField = java.awt.GraphicsEnvironment.class.getDeclaredField("headless");
        headlessField.setAccessible(true);
        headlessField.set(null, Boolean.TRUE);

        Class<?> geCls = Class.forName("java.awt.GraphicsEnvironment$LocalGE");
        Field ge = geCls.getDeclaredField("INSTANCE");
        ge.setAccessible(true);
        defaultHeadlessField.set(null, Boolean.FALSE);
        headlessField.set(null, Boolean.FALSE);

        Class<?> smfCls = Class.forName("sun.java2d.SurfaceManagerFactory");
        Field smf = smfCls.getDeclaredField("instance");
        smf.setAccessible(true);
        smf.set(null, null);

        setFinalStatic(ge, new CTCGraphicsEnvironment());

        String propertyFontManager = System.getProperty("cacio.font.fontmanager");
        if (propertyFontManager != null) {
            FontManagerUtil.setFontManager(propertyFontManager);
        }
    }
    
    // 复制自原始类的setFinalStatic方法
    public static void setFinalStatic(Field field, Object value) throws Exception {
        Field unsafeField = Unsafe.class.getDeclaredField("theUnsafe");
        unsafeField.setAccessible(true);
        Unsafe unsafe = (Unsafe) unsafeField.get(null);
        Object fieldBase = unsafe.staticFieldBase(field);
        long fieldOffset = unsafe.staticFieldOffset(field);
        unsafe.putObject(fieldBase, fieldOffset, value);
    }
    
    // 复制自原始类的getFileURL方法
    static URL getFileURL(File file) {
        try {
            file = file.getCanonicalFile();
        } catch (IOException e) {}

        try {
            return file.toURL();
        } catch (MalformedURLException e) {
            // Should never happen since we specify the protocol...
            throw new InternalError(e);
        }
    }
}
