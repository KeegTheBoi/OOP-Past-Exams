package a03b.e2;

import javax.swing.*;
import java.util.*;
import java.util.stream.Collectors;
import java.awt.*;
import java.awt.event.*;

public class GUI extends JFrame {
    
    private final Map<JButton, Pair<Integer, Integer>> cells = new HashMap<>();
    private final Modello model;
    
    public GUI(int size) {
        model = new ModelloImpl(size);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(100*size, 100*size);
        
        JPanel panel = new JPanel(new GridLayout(size,size));
        this.getContentPane().add(panel);
        
        ActionListener al = new ActionListener(){
            public void actionPerformed(ActionEvent e){
        	    var button = (JButton)e.getSource();
        	    var position = cells.get(button);
                if(model.hit(position.getX(),position.getY())){
                    drawTable();
                }
            }
        };
                
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
                final JButton jb = new JButton(" ");
                this.cells.put(jb, new Pair<>(j, i));
                jb.addActionListener(al);
                panel.add(jb);
            }
        }
        model.initMap();
        drawTable();
        this.setVisible(true);
    }    

    private JButton reverseButtonMap(Pair<Integer, Integer> pos){
        return this.cells.entrySet().stream().filter(e -> e.getValue().equals(pos)).findFirst().get().getKey(); 
    }

    private Set<Pair<Integer, Integer>> getPlayerSet(Modello.Player player) {
        return this.model.getMapper().entrySet().stream().filter(c -> c.getValue().equals(player)).map(z -> z.getKey()).collect(Collectors.toSet());       
    }

    private void drawTable(){
        for (Pair<Integer,Integer> pair : getPlayerSet(Modello.Player.COMPUTER)) {
            reverseButtonMap(pair).setText("O");
        }
        for (Pair<Integer,Integer> pair : getPlayerSet(Modello.Player.HUMAN)) {
            reverseButtonMap(pair).setText("*");
        }
        for (Pair<Integer,Integer> pair : getPlayerSet(Modello.Player.EMPTY)) {
            reverseButtonMap(pair).setText(" ");
        }
    }
}
