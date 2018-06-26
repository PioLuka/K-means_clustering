import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

public class PanelStart extends JPanel{
	
	int wysokosc = 860,
		szerokosc = 1180;
	
	final private Color[] tabKolor = {
		Color.BLUE,
		Color.GREEN,
		Color.RED,
		Color.CYAN,
		Color.MAGENTA,
		Color.YELLOW
	};
	
	int iterator;
	
	List<General> listaGeneralow = new ArrayList<>();
	
	private String nazwaMetody;
	
	JCheckBox euklides = new JCheckBox("Metoda Euklidesowa"),
			  miejska = new JCheckBox("Metoda Miejska"),
			  maksimum = new JCheckBox("Metoda Maksimum"),
			  rzeczna = new JCheckBox("Metoda Rzeczna");
	
	JButton wyslij = new JButton("Start");
	JButton zatwierdz = new JButton("Zatwierdz");
	JButton wyczysc = new JButton("Wyczyœæ");
	
	JFrame frame;
	
	JPanel glownyPanel = new JPanel(new BorderLayout());
	JPanel menuPanel = new JPanel(new FlowLayout());
	
	JPanel metodaPanel = new JPanel(new FlowLayout());
	JPanel comboBoxPanel = new JPanel(new FlowLayout());
	JPanel labelPanel = new JPanel(new FlowLayout());
	
	JComboBox<Integer> liczbaWoja = new JComboBox<>();
	JComboBox<Integer> liczbaGen = new JComboBox<>();
	
	Border raisedetched = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
	
	JPanel animPanel = null;
	
	JLabel label = new JLabel("Genera³owie do rozmieszczenia: ");
	
	public PanelStart(JFrame okno, JPanel animPanel){
		setPreferredSize(new Dimension(1200, 950));
		this.frame = okno;
		this.animPanel = animPanel;
		init();

		add(glownyPanel);
	}
	
	public void init(){
		
		euklides.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(euklides.isSelected()){
					miejska.setSelected(false);
					maksimum.setSelected(false);
					rzeczna.setSelected(false);
					
					nazwaMetody = "Euklidesowa";
				}
			}
			
		});
		
		miejska.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(miejska.isSelected()){
					euklides.setSelected(false);
					maksimum.setSelected(false);
					rzeczna.setSelected(false);
					
					nazwaMetody = "Miejska";
				}
			}
			
		});
		
		maksimum.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if(maksimum.isSelected()){
					miejska.setSelected(false);
					euklides.setSelected(false);
					rzeczna.setSelected(false);
					
					nazwaMetody = "Maksimum";
				}
			}
			
		});
		
		rzeczna.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if(rzeczna.isSelected()){
					miejska.setSelected(false);
					euklides.setSelected(false);
					maksimum.setSelected(false);
					
					nazwaMetody = "Rzeczna";
				}
			}
			
		});
		
		wyslij.addMouseListener((MousePressListener) e -> {
			if(!zatwierdz.isEnabled()){
				if(iterator == 0){
					animPanel = new MyPanel(nazwaMetody, (int) liczbaWoja.getSelectedItem(), (int) liczbaGen.getSelectedItem(), listaGeneralow);
					frame.remove(this);
					frame.add(new PanelStart(frame, animPanel));
					frame.revalidate();
					frame.repaint();
				}else{
					JOptionPane.showMessageDialog(frame, "Nie rozmieszczono wszystkich genera³ów!", "Warning!", JOptionPane.WARNING_MESSAGE);
				}
			}else{
				JOptionPane.showMessageDialog(frame, "Nie zatwierdzono!", "Warning!", JOptionPane.WARNING_MESSAGE);
			}
		});
		
		zatwierdz.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(euklides.isSelected() || miejska.isSelected() || maksimum.isSelected() || rzeczna.isSelected()){
					zatwierdz.setEnabled(false);
					euklides.setEnabled(false);
					miejska.setEnabled(false);
					maksimum.setEnabled(false);
					rzeczna.setEnabled(false);
					liczbaWoja.setEnabled(false);
					liczbaGen.setEnabled(false);
					
					iterator = (int) liczbaGen.getSelectedItem();
					label.setText("Genera³owie do rozmieszczenia: " + Integer.toString(iterator));
					
					animPanel.addMouseListener(new MouseAdapter() {
			            @Override
			            public void mousePressed(MouseEvent e) {
			            	iterator--;
			            	label.setText("Genera³owie do rozmieszczenia: " + Integer.toString(iterator));
			                listaGeneralow.add(new General(e.getX(), e.getY(), tabKolor[iterator%6]));
			                repaint();
			                if(iterator == 0){
			                	animPanel.removeMouseListener(this);
			                }
			            }
			        });
					
				}else{
					JOptionPane.showMessageDialog(frame, "Nie wybrano metody odleg³oœci!", "Warning!", JOptionPane.WARNING_MESSAGE);
				}
				
			}
			
		});
		
		wyczysc.addMouseListener((MousePressListener) e -> {
			frame.remove(this);
			frame.add(new PanelStart(frame, null));
			frame.revalidate();
			frame.repaint();
		});
		
		for(int i = 1000; i <= 100000; i += 1000){
			liczbaWoja.addItem(i);
		}
		
		for(int i = 2; i <= 40; i++){
			liczbaGen.addItem(i);
		}
		
		metodaPanel.add(euklides);
		metodaPanel.add(miejska);
		metodaPanel.add(maksimum);
		metodaPanel.add(rzeczna);
		
		metodaPanel.setBorder(raisedetched);
		
		menuPanel.add(metodaPanel);
		
		comboBoxPanel.add(new JLabel("Liczba wojska:"));
		comboBoxPanel.add(liczbaWoja);
		comboBoxPanel.add(new JLabel("Liczba generalow:"));
		comboBoxPanel.add(liczbaGen);
		
		comboBoxPanel.setBorder(raisedetched);
		
		labelPanel.add(label);
		
		menuPanel.add(comboBoxPanel);
		
		menuPanel.add(zatwierdz);
		menuPanel.add(wyslij);
		menuPanel.add(wyczysc);
		
		glownyPanel.add(menuPanel, "North");
		
		if(animPanel != null){
			animPanel.setBorder(raisedetched);
			glownyPanel.add(animPanel, "Center");
			
			zatwierdz.setEnabled(false);
			wyslij.setEnabled(false);
			euklides.setEnabled(false);
			miejska.setEnabled(false);
			maksimum.setEnabled(false);
			rzeczna.setEnabled(false);
			liczbaWoja.setEnabled(false);
			liczbaGen.setEnabled(false);
		}else{
			animPanel = new PomocniczyPanel(listaGeneralow);
			animPanel.setBorder(raisedetched);
			animPanel.setPreferredSize(new Dimension(szerokosc, wysokosc));
			glownyPanel.add(animPanel);
		}
		
		glownyPanel.add(labelPanel, "South");
	}
}
