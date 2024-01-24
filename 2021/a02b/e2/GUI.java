package a02b.e2;

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    
    private static final long serialVersionUID = -6218820567019985015L;
    private final Map<Coord, JButton> cells = new HashMap<>();
    private final Logic log;
    
    public GUI(int size) {
        log = new LogicImpl(size);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(50*size, 50*size);
        
        JPanel panel = new JPanel(new GridLayout(size,size));
        this.getContentPane().add(panel);
        
        ActionListener al = e -> {
        	log.hit();
            if(log.isOver()) {
                System.out.println("finished");
                System.exit(99);
            }
            draw();
        };
                
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
                final JButton jb = new JButton(" ");
                this.cells.put(new Coord(j, i), jb);
                jb.addActionListener(al);
                panel.add(jb);
            }
        }
        this.setVisible(true);
        draw();
    }

    private void draw() {
        cells.forEach((k, v) -> {
            if(log.getMap().keySet().contains(k)) {
                v.setText(log.getMap().get(k).getSymb());
            }
            else {
                v.setText("");
            }
        });
    }
    
}
