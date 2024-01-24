package a03a.e2;

import javax.swing.*;


import java.util.*;
import java.awt.*;
import java.awt.event.*;

public class GUI extends JFrame {
    
    private final Map<JButton, Coord> cells = new LinkedHashMap<>();
    private final Logic log;
    
    public GUI(int size) {
        this.log = new LogicImpl(size);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(100*size, 100*size);
        
        JPanel panel = new JPanel(new GridLayout(size,size));
        this.getContentPane().add(panel);
        
        ActionListener al = new ActionListener(){
            public void actionPerformed(ActionEvent e){
        	    var button = (JButton)e.getSource();
        	    var position = cells.get(button);
                log.hit(position);
                if(log.isOVer()) {
                    System.out.println(log.getMap().values().stream().findAny().get() + " wins");
                    System.exit(size);
                }
                draw();      
            }
        };
                
        for (int i=0; i< size; i++){
            for (int k = 0; k < size; k++) {
                final JButton jb = new JButton(" ");
                this.cells.put(jb, new Coord(k, i));
                jb.addActionListener(al);
                panel.add(jb);
            }
            
        }
        draw(); 
        this.setVisible(true);
    }

    protected void draw() {

        cells.forEach((k, v) -> {
            if(log.getMap().containsKey(v)) {
                k.setText(log.getMap().get(v).getSymbol());
            }
            else {
                k.setText("");
            }
        });
    }    

   
}
