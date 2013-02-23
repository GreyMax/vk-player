package com.javafx.main;

import javax.swing.*;

/**
 * Created by IntelliJ IDEA.
 * User: Alexander
 * Date: 30.10.12
 * Time: 13:14
 * To change this template use File | Settings | File Templates.
 */
public class NoJavaFXFallback extends JApplet{
    public NoJavaFXFallback(boolean valjreError, boolean b, String s) {
        System.out.println("================= no javafx ===========");
        System.out.println(s);
    }
}
