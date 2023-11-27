package a03a.e2;

import javax.management.modelmbean.ModelMBeanInfo;
import javax.naming.event.ObjectChangeListener;
import javax.swing.*;

import a03a.e2.Model.Movement;

import java.util.*;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.awt.*;
import java.awt.event.*;

public class GUI extends JFrame {
    
    private final Map<JButton, Pair<Integer, Integer>> cells = new LinkedHashMap<>();
    private final Model modello;
    
    public GUI(int size) {
        modello = new ModelImpl(size);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(100*size, 100*size);
        
        JPanel panel = new JPanel(new GridLayout(size,size));
        this.getContentPane().add(panel);
        
        ActionListener al = new ActionListener(){
            public void actionPerformed(ActionEvent e){
        	    var button = (JButton)e.getSource();
        	    var position = cells.get(button);
                if(modello.humanmove(position.getX(), position.getY())){
                    if(modello.isOver()){
                        modello.initTable();
                        System.out.println("Human Wins");
                    }
                    else{
                        modello.computermove();
                        if(modello.isOver()){
                            modello.initTable();
                            System.out.println("Computer Wins");
                        }
                    }
                    
                }
                clear();             
                drawTable(modello.getMapperTable());
            }
        };
                
        for (int i=0; i< size; i++){
            for (int k = 0; k < size; k++) {
                final JButton jb = new JButton(" ");
                this.cells.put(jb, new Pair<Integer,Integer>(k, i));
                jb.addActionListener(al);
                panel.add(jb);
            }
            
        }
        modello.initTable();
        drawTable(modello.getMapperTable());
        this.setVisible(true);
    }    

    private void clear(){
        for (JButton button : cells.keySet()) {
            button.setText(" ");
        }
    }

    private JButton reverseButtonMap(Pair<Integer, Integer> pos){
        return this.cells.entrySet().stream().filter(e -> e.getValue().equals(pos)).findFirst().get().getKey(); 
    }

    private void drawTable(Map<Movement, Pair<Integer, Integer>> mapper){
        for (var positionSpecial : mapper.entrySet()) {
            var but = reverseButtonMap(positionSpecial.getValue());            
            if(positionSpecial.getKey().equals(Movement.HUMAN)){
                but.setText("*");
            }
            else{
                but.setText("O");
            }
            
        }
    }
}
