package a04.e2;

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Map;

public class GUI extends JFrame {
    
    private final Map<JButton, Coord> cells = new HashMap<>();
    private final Logic log;
    
    public GUI(int size) {
        log = new LogicImpl(size);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(100*size, 100*size);
        
        JPanel panel = new JPanel(new GridLayout(size,size));
        this.getContentPane().add(panel);
        
        ActionListener al = new ActionListener(){
            public void actionPerformed(ActionEvent e){
        	    var button = (JButton)e.getSource();
        	    var position = cells.get(button);
                
                log.hit(position).ifPresent(p -> {
					clear();
					button.setText("R");
					reverseButton(p).setText("K");
				});
                if(log.isOver()) {
					System.out.println("Someone has won");
                    System.exit(0);
                }
                
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
        draw();
        this.setVisible(true);
    }

	private void clear() {
		this.cells.keySet().forEach(k -> k.setText(""));
	}
	
	private void draw() {
		reverseButton(log.getPlayers().getX()).setText("R");
		reverseButton(log.getPlayers().getY()).setText("K");
	}
	
	private JButton reverseButton(Coord c) {
		return cells.entrySet().stream().filter(e -> e.getValue().equals(c)).map(Map.Entry::getKey).findFirst().get();
	}
}
