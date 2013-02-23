package com.greymax.vkplayer.ui.control.panel;

import com.greymax.vkplayer.listeners.control.panel.ControlActionListener;
import com.greymax.vkplayer.ui.custom.RoundButton;
import com.greymax.vkplayer.ui.custom.RoundImageButtonUI;

import javax.swing.*;
import java.awt.*;

public class ControlPanelButtons extends JPanel{
    private final Box box = Box.createHorizontalBox();

    public ControlPanelButtons() {
        super(null);
        final java.util.List<JButton> l = makeButtonArray1(getClass()); //Override JButton

        box.setOpaque(true);
//        box.setBackground(new Color(120,120,160));
        box.setBorder(BorderFactory.createLoweredBevelBorder());
        box.add(Box.createHorizontalGlue());
        for(JButton b: l) {
            b.setBackground(Color.WHITE);
            b.setAlignmentY(Component.CENTER_ALIGNMENT);
            box.add(b);
            box.add(Box.createHorizontalStrut(5));
        }
        box.add(Box.createHorizontalGlue());
        box.setBounds(0,0, 275, 90);
        add(box);
        setPreferredSize(new Dimension(50, 30));
        setBounds(0,0, 275, 90);
    }
    private static java.util.List<JButton> makeButtonArray1(final Class clazz) {
        ControlActionListener listener = new ControlActionListener();
        return java.util.Arrays.<JButton>asList(
                new RoundButton(new ImageIcon(clazz.getResource("/resources/img/play.png")), "Play", listener) {{
                    setPressedIcon(new ImageIcon(clazz.getResource("/resources/img/play_d.png")));
                    setRolloverIcon(new ImageIcon(clazz.getResource("/resources/img/play_h.png")));
                }},
                new RoundButton(new ImageIcon(clazz.getResource("/resources/img/pause.png")), "Pause", listener) {{
                    setPressedIcon(new ImageIcon(clazz.getResource("/resources/img/pause_d.png")));
                    setRolloverIcon(new ImageIcon(clazz.getResource("/resources/img/pause_h.png")));
                }},
                new RoundButton(new ImageIcon(clazz.getResource("/resources/img/stop.png")), "Stop", listener) {{
                    setPressedIcon(new ImageIcon(clazz.getResource("/resources/img/stop_d.png")));
                    setRolloverIcon(new ImageIcon(clazz.getResource("/resources/img/stop_h.png")));
                }},
                new RoundButton(new ImageIcon(clazz.getResource("/resources/img/prev.png")), "Prev", listener) {{
                    setPressedIcon(new ImageIcon(clazz.getResource("/resources/img/prev_d.png")));
                    setRolloverIcon(new ImageIcon(clazz.getResource("/resources/img/prev_h.png")));
                }},
                new RoundButton(new ImageIcon(clazz.getResource("/resources/img/next.png")), "Next", listener) {{
                    setPressedIcon(new ImageIcon(clazz.getResource("/resources/img/next_d.png")));
                    setRolloverIcon(new ImageIcon(clazz.getResource("/resources/img/next_h.png")));
                }}
        );
    }
    private static java.util.List<JButton> makeButtonArray2(final Class clazz) {
        return java.util.Arrays.asList(
            new JButton(new ImageIcon(clazz.getResource("/resources/img/005.png"))) {{
                setPressedIcon(new ImageIcon(clazz.getResource("/resources/img/005d.png")));
                setRolloverIcon(new ImageIcon(clazz.getResource("/resources/img/005g.png")));
                setUI(new RoundImageButtonUI());
            }},
            new JButton(new ImageIcon(clazz.getResource("/resources/img/003.png"))) {{
                setPressedIcon(new ImageIcon(clazz.getResource("/resources/img/003d.png")));
                setRolloverIcon(new ImageIcon(clazz.getResource("/resources/img/003g.png")));
                setUI(new RoundImageButtonUI());
            }},
            new JButton(new ImageIcon(clazz.getResource("/resources/img/001.png"))) {{
                setPressedIcon(new ImageIcon(clazz.getResource("/resources/img/001d.png")));
                setRolloverIcon(new ImageIcon(clazz.getResource("/resources/img/001g.png")));
                setUI(new RoundImageButtonUI());
            }},
            new JButton(new ImageIcon(clazz.getResource("/resources/img/002.png"))) {{
                setPressedIcon(new ImageIcon(clazz.getResource("/resources/img/002d.png")));
                setRolloverIcon(new ImageIcon(clazz.getResource("/resources/img/002g.png")));
                setUI(new RoundImageButtonUI());
            }},
            new JButton(new ImageIcon(clazz.getResource("/resources/img/004.png"))) {{
                setPressedIcon(new ImageIcon(clazz.getResource("/resources/img/004d.png")));
                setRolloverIcon(new ImageIcon(clazz.getResource("/resources/img/004g.png")));
                setUI(new RoundImageButtonUI());
            }});
    }

}