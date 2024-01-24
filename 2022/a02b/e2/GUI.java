package a02b.e2;

import javax.swing.*;


import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.event.*;

public class GUI extends JFrame {
    
    private final Map<JButton, Pair<Integer, Integer>> cells = new HashMap<>();
    private final ModelImpl model;

    public GUI(int size) {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(100*size, 100*size);
        model = new ModelImpl(size);
        JPanel main = new JPanel(new BorderLayout());
        JPanel panel = new JPanel(new GridLayout(size,size));
        this.getContentPane().add(main);
        main.add(BorderLayout.CENTER, panel);
        JButton check = new JButton("Check > Restart");
        main.add(BorderLayout.SOUTH, check);
        
        ActionListener al = new ActionListener(){
            public void actionPerformed(ActionEvent e){
        	    var button = (JButton)e.getSource();
        	    var position = cells.get(button);
                if(model.hit(position.getX(), position.getY())){
                    button.setText("*");
                }
                else{
                    button.setText(" ");
                }
            }
        };

        check.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                Set<Pair<Integer, Integer>> todisable = model.getSelectedPos();
                if(!todisable.isEmpty()){
                    toDisable(todisable);
                }               
            }           
        });
        
                
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
                final JButton jb = new JButton(" ");
                this.cells.put(jb, new Pair<Integer,Integer>(j, i));
                jb.addActionListener(al);
                panel.add(jb);
            }
        }
        this.setVisible(true);
    }    

    private JButton reverseButtonMap(Pair<Integer, Integer> pos){
        return this.cells.entrySet().stream().filter(e -> e.getValue().equals(pos)).findFirst().get().getKey(); 
    }

    private void toDisable(Set<Pair<Integer, Integer>> todisable){
        for (Pair<Integer,Integer> pair : todisable) {
            this.reverseButtonMap(pair).setEnabled(false);
        }
    }
}
