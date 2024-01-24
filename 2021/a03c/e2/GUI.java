package a03c.e2;

import javax.swing.*;

import a03c.e2.Logic.Player;

import java.util.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    
    private static final long serialVersionUID = -6218820567019985015L;
    private final Map<JButton, Coord> cells = new HashMap<>();
    private Logic log;
    
    public GUI(int size) {
        log = new LogicImpl(size);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(50*size, 50*size);
        
        JPanel panel = new JPanel(new GridLayout(size,size));
        this.getContentPane().add(panel);
        
        ActionListener al = e -> {
            if(!log.hit()) {
                System.out.println("finished");
                System.exit(1);
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
        draw();
    }

    private void draw() {
        cells.forEach((k, v) -> {
            if(log.getMap().containsKey(v)) {
                if(log.getMap().get(v).getX().equals(Player.BOUNCER)) {
                    k.setText(log.getMap().get(v).getX().getSymbol());
                }
                else if(log.getMap().get(v).getX().equals(Player.PEDINA)) {           
                    k.setText(log.getMap().get(v).getX().getSymbol());
                }
            }
            else {
                k.setText(""); 
            }
        });
    }
    
}
