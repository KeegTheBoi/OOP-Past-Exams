package a06.sol2;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.stream.*;
import javax.swing.*;
import java.util.List;


public class GUI extends JFrame{
	
	public GUI(int size){
		final Logics cs = new LogicsImpl(size);
		this.setSize(500, 100);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.getContentPane().setLayout(new FlowLayout());
		
		final java.util.List<JButton> jbs = cs.currentValues()
				  .stream()
				  .map(String::valueOf)
				  .map(JButton::new)
				  .collect(Collectors.toList());
		ActionListener ac = e -> {
			final JButton jb = (JButton)e.getSource();
			jb.setEnabled(false);
			final int pos = jbs.indexOf(jb);
			jb.setText(String.valueOf(cs.hit(pos)));
		};
		jbs.forEach(x->{
			x.addActionListener(ac);
			this.getContentPane().add(x);
		});
		final JButton reset = new JButton("Reset");
		this.getContentPane().add(reset);
		reset.addActionListener(x->{
			cs.reset();
			final List<Integer> l = cs.currentValues();
			for (int i=0;i<size;i++) {
				jbs.get(i).setText(String.valueOf(l.get(i)));
				jbs.get(i).setEnabled(true);
			}
		});
		
		this.setVisible(true);
	}

}
