package a03a.e2;

import javax.management.modelmbean.ModelMBeanInfo;
import javax.naming.event.ObjectChangeListener;
import javax.swing.*;


import java.util.*;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.awt.*;
import java.awt.event.*;

public class GUI extends JFrame {
    
    private final Map<JButton, Coord> cells = new LinkedHashMap<>();
    private final Logic modello;
    
    public GUI(int size) {
        modello = new LogicImpl(size);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(100*size, 100*size);
        
        JPanel panel = new JPanel(new GridLayout(size,size));
        this.getContentPane().add(panel);
        
        ActionListener al = new ActionListener(){
            public void actionPerformed(ActionEvent e){
        	    var button = (JButton)e.getSource();
        	    var position = cells.get(button);
                
                    
              
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
        this.setVisible(true);
    }    

   
}
