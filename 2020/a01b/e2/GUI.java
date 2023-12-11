package a01b.e2;

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    
    private static final long serialVersionUID = -6218820567019985015L;
    private final Map<JButton, Coord> cells = new HashMap<>();
    private final Logic logic;

    public GUI(int size) {
        logic = new LogicImpl((size));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(100*size, 100*size);
        
        JPanel panel = new JPanel(new GridLayout(size,size));
        this.getContentPane().add(panel);
        
        ActionListener al = e -> {
        	var button = (JButton)e.getSource();
        	var position = cells.get(button);
        	logic.hit(position);
            draw();
            if(logic.isOver()) {
                System.out.println("finito");
                System.exit(0);
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
        draw();
    }


    private void draw() {
        cells.forEach((b, c) -> {
            if(logic.getMap().containsKey(c)) {
                b.setText(logic.getMap().get(c).getContent());
            }
            else {
                b.setText("");
            }
        });
    }
    
    
}
