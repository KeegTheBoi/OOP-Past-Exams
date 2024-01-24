package a01a.e2;

import javax.swing.*;
import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.event.*;

public class GUI extends JFrame {
    
    private final Map<JButton, Coord> cells = new HashMap<>();
    private final Logic log;
   
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
                log.hit(position);
                draw();
                if(log.isOver()) {
                    System.out.println("finished");
                    System.exit(2);
                }
                
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

    private void draw() {
        this.cells.forEach((k, v) -> {
            if(log.getSelected().contains(v)) {
                k.setText("*");
            }
            else {
                k.setText(" ");
            }
        });
    }    
}
