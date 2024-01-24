package a01a.e2;

import javax.swing.*;

import a01a.e2.Logic.Component;

import java.util.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    
    private static final long serialVersionUID = -6218820567019985015L;
    private final Map<JButton, Coord> cells = new HashMap<>();
    private final Logic log;
    
    public GUI(int size) {
        log = new LogicImpl(size);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(50*size, 50*size);
        
        JPanel panel = new JPanel(new GridLayout(size,size));
        this.getContentPane().add(panel);
        
        ActionListener al = e -> {
        	var button = (JButton)e.getSource();
        	var pos = cells.get(button);
        	log.hit(pos); 
            if(log.isOver()){
                disableButtons();  
            }
            draw();
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

    private void disableButtons() {
        cells.keySet().forEach(k -> k.setEnabled(false));
    }

    void draw() {
        cells.forEach((k, v) -> {
            if(log.getMap().containsKey(v)) {
                if(log.getMap().get(v).equals(Component.FIRST)) {
                    k.setText("1");
                }
                if(log.getMap().get(v).equals(Component.ANY)) {
                    k.setText("*");
                }
            }
        });
    }
    
}
