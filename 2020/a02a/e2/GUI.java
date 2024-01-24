package a02a.e2;

import javax.swing.*;

import a02a.e2.Logic.Role;

import java.util.*;
import java.awt.*;

public class GUI extends JFrame {
    
    private static final long serialVersionUID = -6218820567019985015L;
    private final Map<Coord, JButton> cells = new HashMap<>();
    private final JButton next = new JButton(">");
    private final Logic log;
    
    public GUI(int size) {
        this.log = new LogicImpl(size);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(100*size, 100*size);
        
        JPanel grid = new JPanel(new GridLayout(size,size));
        this.getContentPane().add(BorderLayout.CENTER,grid);
        this.getContentPane().add(BorderLayout.SOUTH,next);
        
        next.addActionListener(e -> {
        	System.out.println("NEXT");
        	if(this.log.hit().isPresent()) {
                draw();
            } else {
                this.cells.get(this.log.getPawn()).setEnabled(false);
            }
    
        });
                
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
                final JButton jb = new JButton(" ");
                this.cells.put(new Coord(j, i), jb);
                grid.add(jb);
            }
        }
        draw();
        this.setVisible(true);
    }    

    private void draw() {
        var map = log.getMap();
        this.cells.forEach((k, v) -> v.setText(Optional.of(k).filter(map::containsKey).map(map::get).map(Role::getSymbol).orElse("")));
    }
}
