//	黃士耘_109403050_資管二B
//目前的bug : 橡皮擦功能筆刷只能用筆刷橡皮擦猜掉，以此類推，橢圓圖形只能用橢圓橡皮擦擦掉

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

class MyJframe extends JFrame implements ItemListener, ActionListener{
	private JPanel contentPane;
	private JLabel tool;
	private JRadioButton[] rdb = new JRadioButton[3];
	private JCheckBox full;
	private JButton color, fullcolor, clear, eraser;
	private static String[] tools  ={"筆刷", "直線", "橢圓形", "矩形"};
	private JComboBox<String> pen = new JComboBox<>(tools);
	private middlePane paintpanel;
	
	public static int paintsize = 4;
	public static String toolselect = tools[0];
	public static Color forecolor = Color.BLACK;
	public static Color backcolor = Color.black;
	public static boolean fullcheck = false;
	public static boolean clean =false;
	public JPanel address = new JPanel();
	public static JLabel location = new JLabel();
	public static String currenttool = tools[0];
	
	MyJframe(){
		
		setTitle("小畫家");  
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        
		setBounds(100, 100, 1000, 600);    
		setLayout(new BorderLayout(0,0));
		
		contentPane = new JPanel();
		add(contentPane, BorderLayout.NORTH);
		contentPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		address.add(location);
		address.setBackground(Color.GRAY);
		add(address, BorderLayout.SOUTH);
		address.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		
		JPanel toolpan = new JPanel();
		toolpan.setLayout(new GridLayout(2, 1, 0, 5));
		tool = new JLabel("繪圖工具");
		pen.addItemListener(this);
		toolpan.add(tool);
		toolpan.add(pen);
		contentPane.add(toolpan);
		
		JPanel txtpansize = new JPanel();
		txtpansize.setLayout(new GridLayout(2, 1, 0, 0));
		JPanel pansize = new JPanel();
		JPanel txtpan = new JPanel();
		JLabel sizetxt = new JLabel("筆刷大小");
		pansize.setLayout(new FlowLayout(FlowLayout.LEFT));
		txtpan.setLayout(new FlowLayout(FlowLayout.LEFT));
		ButtonGroup group = new ButtonGroup();
		rdb[0] = new JRadioButton("小", true);
		rdb[0].addActionListener(this);    
		rdb[1] = new JRadioButton("中");
		rdb[1].addActionListener(this);
		rdb[2] = new JRadioButton("大");
		rdb[2].addActionListener(this);
		group.add(rdb[0]);
		group.add(rdb[1]);
		group.add(rdb[2]);
		txtpan.add(sizetxt);
		pansize.add(rdb[0]);
		pansize.add(rdb[1]);
		pansize.add(rdb[2]);
		txtpansize.add(txtpan);
		txtpansize.add(pansize);
		contentPane.add(txtpansize);
		
		JPanel fullpan = new JPanel();
		fullpan.setLayout(new GridLayout(2, 1, 5, 5));
		JLabel fulltxt = new JLabel("填滿");
		full = new JCheckBox("");
		full.addActionListener(this);
		fullpan.add(fulltxt);
		fullpan.add(full);
		contentPane.add(fullpan);
		
		color = new JButton("筆刷顏色");
		color.addActionListener(this);
		contentPane.add(color);
		
		fullcolor = new JButton("填滿顏色");
		fullcolor.addActionListener(this);
		contentPane.add(fullcolor);
		
		clear = new JButton("清除畫面");
		clear.addActionListener(this);
		contentPane.add(clear);
		
		eraser = new JButton("橡皮擦");
		eraser.addActionListener(this);
		contentPane.add(eraser);
		
		paintpanel = new middlePane();
		add(paintpanel, BorderLayout.CENTER);
		
		setVisible(true);       		
	}
	
	public void itemStateChanged(ItemEvent e) {
//	    int index = pen.getSelectedIndex();
//	    System.out.println("選擇" + tools[index]);
		
		if(e.getStateChange() == e.SELECTED) {
			 System.out.println("選擇 " + e.getItem());
			 toolselect = tools[pen.getSelectedIndex()];
			 currenttool = tools[pen.getSelectedIndex()];
		}
	}
	

	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == rdb[0] | e.getSource() == rdb[1] | e.getSource() == rdb[2]) {
			if(rdb[0].isSelected()){ 
				System.out.println("選擇 小 筆刷");
				paintsize = 4; }
			else if(rdb[1].isSelected()) {
				System.out.println("選擇 中 筆刷");
				paintsize = 25; }
			else if(rdb[2].isSelected()) { 
				System.out.println("選擇 大 筆刷"); 
				paintsize = 50; }
		}
		
		else if(e.getSource() == full) {
			if(full.isSelected()) { 
				System.out.println("選擇 填滿");
				fullcheck = true; }
			else {
				System.out.println("取消 填滿");
				fullcheck = false; }
		}
		else if(e.getSource() == color) {
			System.out.println("點選 筆刷顏色");
			forecolor = JColorChooser.showDialog(MyJframe.this, "Choose a color", forecolor);
		    color.setBackground(forecolor);
		}
		else if(e.getSource() == fullcolor){
			System.out.println("點選 填滿顏色");
			backcolor = JColorChooser.showDialog(MyJframe.this, "Choose a color", backcolor);
		    fullcolor.setBackground(backcolor);
		}
		else if(e.getSource() == clear) {
			System.out.println("點選 清除畫面");
			clean = true;
			paintpanel.clearPanel();
		}
		else if (e.getSource() == eraser) {
			System.out.println("點選 橡皮擦");
			forecolor = Color.WHITE;
			backcolor = Color.WHITE;
			currenttool = "橡皮擦";
		}
		
//		boolean b1 = rdb[0].isSelected();
//		if(b1 == true) { System.out.println("選擇 小 筆刷"); }   
			
//		boolean b2 = rdb[1].isSelected();
//		if(b2 == true) { System.out.println("選擇 中 筆刷"); }       	
		
//		boolean b3 = rdb[2].isSelected();
//		if(b3 == true) { System.out.println("選擇 大 筆刷"); }
		
//		boolean b = full.isSelected();
//		if(b == true) { System.out.println("選擇 填滿"); }
//		else { System.out.println("取消 填滿"); }	
		
//		if (e.getSource() == color) { System.out.println("點選 筆刷顏色"); }
//		if (e.getSource() == clear) { System.out.println("點選 清除畫面"); }
		
	}
}

public class Main {

	public static void main(String[] args) {
		
		MyJframe f = new MyJframe();
		JOptionPane.showMessageDialog(f, "Welcome");
	}

}