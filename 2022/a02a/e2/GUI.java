package a02a.e2;

import javax.swing.*;


import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.event.*;

public class GUI extends JFrame {
    
    private final Map<JButton, Coord> cells = new HashMap<>();
    private Logic log;
    private boolean isOver;
    
    public GUI(int size) {
        log = new LogicImpl(size);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(100*size, 100*size);
        
        JPanel panel = new JPanel(new GridLayout(size,size));
        this.getContentPane().add(panel);
        
        ActionListener al = new ActionListener(){
            public void actionPerformed(ActionEvent e){
        	    var button = (JButton)e.getSource();
        	    var position = cells.get(button);
                if(isOver) {
                    reset(size);
                    return;
                }
                toDisable(log.hit(position));
                if(log.isOVer()) {
                    System.out.println("finished");
                    isOver = true;
                }
                draw();
            }
        };

       
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
                final JButton jb = new JButton(" ");
                this.cells.put(jb, new Coord(j, i));
                jb.addActionListener(al);
                panel.add(jb);
            }
        }
        this.setVisible(true);
    }

    protected void toDisable(Set<Coord> hit) {
        cells.forEach((k, v) -> {
            if(hit.contains(v)) {
                k.setEnabled(false);
            }
        });
    }

    protected void draw() {
        cells.forEach((k, v) -> {
            if(log.getSelected().contains(v)) {
                k.setText("B");
            } else {
                k.setText(" ");
            }
        });
    }   
    
    protected void reset(int size) {
        cells.forEach((k, v) -> {
                        k.setText(" ");
                        k.setEnabled(true);       
        });
        log = new LogicImpl(size);
        isOver = false;
    }
}
