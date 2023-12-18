package a04.e2;

import javax.swing.*;

import java.util.HashMap;
import java.util.Map;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    
    private static final long serialVersionUID = -6218820567019985015L;
    private final Map<JButton, Coord> cells = new HashMap<>();
    private final JButton quit = new JButton("QUIT");
    private final Logic log;
        
    public GUI(int size) {
        log = new LogicImpl(size);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(100*size, 100*size);
        
        JPanel grid = new JPanel(new GridLayout(size,size));
        this.getContentPane().add(BorderLayout.CENTER,grid);
        this.getContentPane().add(BorderLayout.SOUTH,quit);
        
        quit.addActionListener(e -> {
            System.out.println(log.getResult());
        	System.exit(0);
       	});
        
        ActionListener al = e -> {
        	var jb = (JButton)e.getSource();
            log.hit(cells.get(jb));
        };
                
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
                final JButton jb = new JButton("  ");
                this.cells.put(jb, new Coord(j, i));
                grid.add(jb);
                jb.addActionListener(al);
            }
        }
        this.setVisible(true);
        draw();
    }

    public void draw() {
        cells.forEach((k, v) -> {
            if(log.getMap().get(v).isValue()) {
                k.setText(String.valueOf(log.getMap().get(v).getVal()));
            }
            else {         
                switch (log.getMap().get(v).getOperand()){
                    case ADD:
                        k.setText("+");
                        return;
                    case MINUS:
                        k.setText("-");
                        return;
                    case MULTIPLY:
                        k.setText("*");
                        return;
                }               
            }
        });
    }
}
