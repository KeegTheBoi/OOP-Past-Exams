package a05.e2;

import javax.swing.*;

import a05.e2.Logic.Operator;

import java.util.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    
    private static final long serialVersionUID = -6218820567019985015L;
    private final Map<JButton, Coord> cells = new HashMap<>();
    private Logic log;
    private Operator op;
    
    public GUI(int size) {
        log = new LogicImpl(size);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(100*size, 100*size);
        
        JPanel grid = new JPanel(new GridLayout(size,size));
        this.getContentPane().add(BorderLayout.CENTER,grid);
        
        ActionListener al = e -> {
        	var jb = (JButton)e.getSource();
        	var position = cells.get(jb);
        	if (log.hit(position)) {
                jb.setEnabled(false);
            }

            if(log.isOver()) {
                System.out.println("finished");
                System.exit(2);
            }
        };
                
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
                final JButton jb = new JButton(""+(i+j));
                this.cells.put(jb, new Coord(j, i));
                grid.add(jb);
                jb.addActionListener(al);
            }
        }
        this.setVisible(true);
        draw();
    }

    void draw() {
       
        cells.forEach((k, v) -> { 
            if(log.getMap().containsKey(v)) {
                if(log.getMap().get(v) instanceof Operator) {
                   op = (Operator)(log.getMap().get(v));
                }
                k.setText(log.getMap().get(v) instanceof Boolean ? String.valueOf(log.getMap().get(v)) : String.valueOf(op.getSymbol()));
            }
        });
    }
}