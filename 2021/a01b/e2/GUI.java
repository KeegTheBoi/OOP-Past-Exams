package a01b.e2;

import javax.swing.*;

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
        	log.hit(cells.get(button));
            draw();
            if(log.isOver()) {
                this.diableCells();
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

    private void diableCells() {
        cells.keySet().forEach(k -> k.setEnabled(false));
    }

    private void draw() {
        this.cells.forEach((k, v) -> {
            if(log.getMap().containsKey(v)) {
                k.setText(log.getMap().get(v).getSymbol());       
            }
        });
    }

    
    
}
