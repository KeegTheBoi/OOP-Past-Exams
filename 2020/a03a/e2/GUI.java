package a03a.e2;

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    
    private static final long serialVersionUID = -6218820567019985015L;
    private final Map<JButton, Coord> cells = new HashMap<>();
    private final Logic log;
    private Coord randomPosition;

    public GUI(int size) {
        log = new LogicImpl(size);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(100*size, 100*size);
        
        JPanel grid = new JPanel(new GridLayout(size,size));
        this.getContentPane().add(BorderLayout.CENTER,grid);
        
        ActionListener el = e -> {
        	var button = (JButton)e.getSource();
        	var position = this.cells.get(button);
        	cells.entrySet().stream().filter(y -> y.getValue().equals(this.log.rook())).map(Map.Entry::getKey).findFirst().get().setText("");
        	disable(log.hit(position));
            if(log.rook().equals(randomPosition)) {
                System.exit(0);
            }
            button.setText("R");
            
        	
        };
                
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
                final JButton jb = new JButton(" ");
                this.cells.put(jb, new Coord(j,i));
                grid.add(jb);
                jb.addActionListener(el);
            }
        }
        this.disable(log.hit(log.rook()));
        cells.entrySet().stream().filter(y -> y.getValue().equals(this.log.rook())).map(Map.Entry::getKey).findFirst().get().setText("R");

        
    	randomPosition = Optional.of(randomPos(size)).filter(p -> p.equals(this.log.rook())).orElse(randomPos(size));
    	cells.forEach( (button,position) -> { if (position.equals(randomPosition)) {button.setText("*");}});
        this.setVisible(true);
    }

    private Coord randomPos(final int size) {
        var random = new Random();
        return new Coord(random.nextInt(size),random.nextInt(size));
    }

    private void disable(Set<Coord> coords) {
        cells.forEach((k, v) -> k.setEnabled(coords.contains(v) ? true : false));
    }
    
}
