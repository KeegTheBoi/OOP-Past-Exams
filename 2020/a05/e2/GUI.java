package a05.e2;

import javax.swing.*;
import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    
    private static final long serialVersionUID = -6218820567019985015L;
    private final Map<JButton, Coord > cells = new HashMap<>();
    private final Logic log;
    
    public GUI(int size) {
        log = new LogicImpl(size);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(100*size, 100*size);
        
        JPanel panel = new JPanel(new GridLayout(size,size));
        this.getContentPane().add(panel);
        
        ActionListener al = e -> {
        	var button = (JButton)e.getSource();
        	var position = cells.get(button);
        	log.hit(position);
            if(log.isOver()) {
                System.exit(size);
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
         draw();
        this.setVisible(true);
    }

    private void draw() {
        this.cells.forEach((k, v) -> {
            if(log.getMap().containsKey(v)) {
                if(log.getMap().get(v) instanceof Integer) {
                    int number = (Integer)log.getMap().get(v);
                    k.setText(String.valueOf(number));
                }
                if(log.getMap().get(v) instanceof String) {
                    k.setText("X");
                }
            }
        });
    }
    
}
