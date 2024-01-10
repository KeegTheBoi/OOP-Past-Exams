package a03a.e2;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class GUI extends JFrame {
    
    private Map<JButton, Coord> cells = new HashMap<>();
    private final Logic log;
    
    public GUI(final int size) {
		log = new LogicImpl(size);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(500, 500);
        int cols = size; // {1,2,3,4,5}
        JPanel panel = new JPanel(new GridLayout(cols,cols));
        this.getContentPane().add(BorderLayout.CENTER,panel);
        final JButton mv = new JButton(">");
        this.getContentPane().add(BorderLayout.SOUTH, mv);
        
        ActionListener al = (e)->{
            final JButton bt = (JButton)e.getSource();
            var pos = cells.get(bt);
            log.hit(pos);
			draw();
        };
        
        mv.addActionListener( e -> {
			log.move();
			if(log.isOver()) {
				System.exit(0);
			}
			draw();
		});
		
        for (int i=0;i<size;i++){
			for (int j=0;j<size;j++){
				final JButton jb = new JButton();
				jb.addActionListener(al);
				this.cells.put(jb, new Coord(j, i));
				panel.add(jb);
            }
        } 
        draw();
        this.setVisible(true);
    }

        
    private void draw() {
		this.cells.forEach((k, v) -> k.setText(log.getMap().containsKey(v) ? log.getMap().get(v).getSymbol() : ""));
	}
}
