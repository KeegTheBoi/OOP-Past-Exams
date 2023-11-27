package a04.sol2;

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;

public class GUI extends JFrame {
    
    private final Map<JButton, Pair<Integer,Integer>> cells = new HashMap<>();
    private final Logic logic;
    
    public GUI(int size) {
        this.logic = new LogicImpl(size);
        this.logic.reset();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(100*size, 100*size);
        
        JPanel panel = new JPanel(new GridLayout(size,size));
        this.getContentPane().add(panel);
        
        ActionListener al = new ActionListener(){
            public void actionPerformed(ActionEvent e){
        	    var button = (JButton)e.getSource();
        	    var position = cells.get(button);
                if (logic.rookMove(position.getX(), position.getY())){
                    if (logic.isOver()){
                        System.out.println("Vittoria");
                        logic.reset();
                    } else {
                        logic.kingMove();
                        if (logic.isOver()){
                            System.out.println("Sconfitta");
                            System.exit(size);
                        }
                    }
                    redraw();
                }
            }
        };
                
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
                final JButton jb = new JButton(" ");
                this.cells.put(jb, new Pair<>(j,i));
                jb.addActionListener(al);
                panel.add(jb);
            }
        }
        redraw();
        this.setVisible(true);
    }

    private void redraw() {
        for (var entry: this.cells.entrySet()){
            var text = logic.hasKing(entry.getValue().getX(), entry.getValue().getY()) ? "K" 
                    : logic.hasRook(entry.getValue().getX(), entry.getValue().getY()) ? "R" 
                    : " ";
            entry.getKey().setText(text);
        } 
    }    
}
