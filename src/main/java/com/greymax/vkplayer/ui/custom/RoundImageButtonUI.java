package com.greymax.vkplayer.ui.custom;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonListener;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;

public class RoundImageButtonUI extends BasicButtonUI {
    protected Shape shape, base;
    @Override protected void installDefaults(AbstractButton b) {
        super.installDefaults(b);
        clearTextShiftOffset();
        defaultTextShiftOffset = 0;
        Icon icon = b.getIcon();
        if(icon==null) return;
        b.setBorder(BorderFactory.createEmptyBorder(1,1,1,1));
        b.setContentAreaFilled(false);
        b.setFocusPainted(false);
        b.setOpaque(false);
        b.setBackground(Color.BLACK);
        //b.setVerticalAlignment(SwingConstants.TOP);
        b.setAlignmentY(Component.TOP_ALIGNMENT);
        initShape(b);
    }
    @Override protected void installListeners(AbstractButton b) {
        BasicButtonListener listener = new BasicButtonListener(b) {
            @Override public void mousePressed(MouseEvent e) {
                AbstractButton b = (AbstractButton) e.getSource();
                initShape(b);
                if(shape.contains(e.getX(), e.getY())) {
                    super.mousePressed(e);
                }
            }
            @Override public void mouseEntered(MouseEvent e) {
                if(shape.contains(e.getX(), e.getY())) {
                    super.mouseEntered(e);
                }
            }
            @Override public void mouseMoved(MouseEvent e) {
                if(shape.contains(e.getX(), e.getY())) {
                    super.mouseEntered(e);
                }else{
                    super.mouseExited(e);
                }
            }
        };
        if(listener != null) {
            b.addMouseListener(listener);
            b.addMouseMotionListener(listener);
            b.addFocusListener(listener);
            b.addPropertyChangeListener(listener);
            b.addChangeListener(listener);
        }
    }
    @Override public void paint(Graphics g, JComponent c) {
        super.paint(g, c);
        Graphics2D g2 = (Graphics2D)g;
        initShape(c);
        //Border
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(c.getBackground());
        //g2.setStroke(new BasicStroke(1.0f));
        g2.draw(shape);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
    }
    @Override public Dimension getPreferredSize(JComponent c) {
        JButton b = (JButton)c;
        Icon icon = b.getIcon();
        Insets i = b.getInsets();
        int iw = Math.max(icon.getIconWidth(), icon.getIconHeight());
        return new Dimension(iw+i.right+i.left, iw+i.top+i.bottom);
    }
    private void initShape(JComponent c) {
        if(!c.getBounds().equals(base)) {
            Dimension s = c.getPreferredSize();
            base = c.getBounds();
            shape = new Ellipse2D.Float(0, 0, s.width-1, s.height-1);
        }
    }
}
