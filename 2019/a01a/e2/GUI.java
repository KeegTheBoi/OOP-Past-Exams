package a01a.e2;

import javax.swing.*;


import java.util.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    
    private final Map<JButton, Coord> cells = new HashMap<>();
    private final Logic log;
    
    public GUI(int size, int boat) {
        log = new LogicsImpl(size, boat);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(500, 500);
        int cols = size; // {1,2,3,4,5}
        JPanel panel = new JPanel(new GridLayout(cols,cols));
        this.getContentPane().add(BorderLayout.CENTER,panel);
        ActionListener al = (e)->{
            final JButton bt = (JButton)e.getSource();
            bt.setText(log.hit(cells.get((JButton)e.getSource())));
            if(log.isOVer()) {
                System.exit(9);
            }
        };
        for (int i=0;i<size;i++){
            for (int j = 0; j < size; j++) {
                final JButton jb = new JButton("");
                cells.put(jb, new Coord(j, i));
                jb.addActionListener(al);
                panel.add(jb);
            }
            
        }         
    	this.setVisible(true);
    }
    
}
