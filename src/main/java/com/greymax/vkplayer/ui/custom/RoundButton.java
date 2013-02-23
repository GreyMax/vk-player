package com.greymax.vkplayer.ui.custom;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;

public class RoundButton extends JButton {

    public RoundButton(Icon icon, String action, ActionListener listener) {
        this(null, icon, action, listener);
    }

    public RoundButton(String text) {
        this(text, null, null, null);
    }

    public RoundButton(String text, Icon icon, String action, ActionListener listener) {

        setModel(new DefaultButtonModel());
        if(null != action) setActionCommand(action);
        addActionListener(listener);
        init(text, icon);
        if(icon==null) {
            return;
        }
        setBorder(BorderFactory.createEmptyBorder(1,1,1,1));
        setBackground(Color.BLACK);
        setContentAreaFilled(false);
        setFocusPainted(false);
        //setVerticalAlignment(SwingConstants.TOP);
        setAlignmentY(Component.TOP_ALIGNMENT);
        initShape();

    }
    @Override public Dimension getPreferredSize() {
        Icon icon = getIcon();
        Insets i = getInsets();
        int iw = Math.max(icon.getIconWidth(), icon.getIconHeight());
        return new Dimension(iw+i.right+i.left, iw+i.top+i.bottom);
    }
    protected Shape shape, base;
    protected void initShape() {
        if(!getBounds().equals(base)) {
            Dimension s = getPreferredSize();
            base = getBounds();
            shape = new Ellipse2D.Float(0, 0, s.width-1, s.height-1);
        }
    }
    @Override protected void paintBorder(Graphics g) {
        initShape();
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        //g2.setStroke(new BasicStroke(1.0f));
        g2.draw(shape);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
    }
    @Override public boolean contains(int x, int y) {
        initShape();
        return shape.contains(x, y);
    }
}
