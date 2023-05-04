import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class middlePane extends JPanel{
	private Point firstpointbuffer;
	private Point lastpointbuffer;
	private ArrayList<PaintPoint> points = new ArrayList<>();
	private ArrayList<LinePoints> lines = new ArrayList<>();
	private ArrayList<OvalShape> ovals = new ArrayList<>();
	private ArrayList<RectShape> rects = new ArrayList<>();
	
	public middlePane() {
		  firstpointbuffer = new Point();
		  lastpointbuffer = new Point();
		  this.setBackground(Color.WHITE);
		  
		  addMouseMotionListener(
				  new MouseMotionAdapter() {
					  public void mouseDragged(MouseEvent e) {
						  switch(MyJframe.currenttool) {
						  	case "筆刷":
						  		points.add(new PaintPoint(e.getPoint(),MyJframe.paintsize, MyJframe.forecolor));
						        break;
						  	case"直線":
						        lastpointbuffer = e.getPoint();
						        break;
						  	case"橢圓形":
						        lastpointbuffer = e.getPoint();
						        break;
						    case"矩形":
						        lastpointbuffer = e.getPoint();
						        break;
						    case"橡皮擦":
						    	lastpointbuffer = e.getPoint();
						    	break;
						  }
						  repaint();
					  }
					  
					  public void mouseMoved(MouseEvent e) {
						  MyJframe.location.setText((String.format("游標位置 [%d,%d]", e.getX(),e.getY())));
					  }
				  }
		  );
		  
		  addMouseListener(
				  new MouseAdapter() {
					  public void mousePressed(MouseEvent e) {
					      firstpointbuffer = e.getPoint();
					  }
					  public void mouseReleased(MouseEvent e) {
						  switch(MyJframe.currenttool) {
						  	case "筆刷":
						  		break;
						  	case"直線":
						        if(MyJframe.fullcheck) {
						        	lines.add(new LinePoints(firstpointbuffer, e.getPoint(), new BasicStroke(MyJframe.paintsize), MyJframe.forecolor));
						        	repaint(); }
						        else {
						        	lines.add(new LinePoints(firstpointbuffer, e.getPoint(), new BasicStroke(
						        	         MyJframe.paintsize, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0), MyJframe.forecolor));
						        	       repaint();
						        }
						        break;
						  	case"橢圓形":
						        ovals.add(new OvalShape(
						        		firstpointbuffer,e.getPoint(), MyJframe.forecolor, MyJframe.backcolor, MyJframe.fullcheck,new BasicStroke(MyJframe.paintsize)));
						        repaint();
						        break;
						  	case"矩形":
						        rects.add(new RectShape(
						        		firstpointbuffer,e.getPoint(), MyJframe.forecolor, MyJframe.backcolor, MyJframe.fullcheck,new BasicStroke(MyJframe.paintsize)));
						        repaint();
						        break;
						  }
					  }
				  }
		  );
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		//筆刷
		for(PaintPoint point:points) {
			g.setColor(point.forecolor);
		    g.fillOval(point.x, point.y, point.size , point.size);
		}
		//直線
		for(LinePoints line:lines) {
		    g2.setColor(line.forecolor);
		    g2.setStroke(line.thickness);
		    g2.drawLine(line.firstpoint.x, line.firstpoint.y, line.lastpoint.x, line.lastpoint.y);
		}
		if(MyJframe.currenttool=="直線" && MyJframe.fullcheck) {
		    g2.setColor(MyJframe.forecolor);
		    g2.setStroke(new BasicStroke(MyJframe.paintsize));
		    g2.drawLine(firstpointbuffer.x, firstpointbuffer.y, lastpointbuffer.x, lastpointbuffer.y);
		}
		else if(MyJframe.currenttool=="直線") {
		    g2.setColor(MyJframe.forecolor);
		    g2.setStroke(new BasicStroke(MyJframe.paintsize, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0));
		    g2.drawLine(firstpointbuffer.x, firstpointbuffer.y, lastpointbuffer.x, lastpointbuffer.y);
		}
		//橢圓
		for(OvalShape oval:ovals) {
		    g2.setColor(oval.forecolor);
		    g2.setStroke(oval.thickness);

		    if(oval.fullcheck) {
		        g2.setColor(oval.backcolor);
		        g2.fillOval(oval.startpoint.x, oval.startpoint.y, oval.width, oval.height);
		        g2.setColor(oval.forecolor);
		        g2.drawOval(oval.startpoint.x, oval.startpoint.y, oval.width, oval.height);
		    }
		    else { g2.drawOval(oval.startpoint.x, oval.startpoint.y, oval.width, oval.height); }
		}
		if(MyJframe.currenttool == "橢圓形") {
		    g2.setStroke(new BasicStroke(MyJframe.paintsize));
		    g2.setColor(MyJframe.forecolor);
		    if(MyJframe.fullcheck) {
		    	g2.setColor(MyJframe.backcolor);
		        g2.fillOval(Math.min(firstpointbuffer.x, lastpointbuffer.x), Math.min(firstpointbuffer.y, lastpointbuffer.y),
		        		Math.abs(firstpointbuffer.x-lastpointbuffer.x), Math.abs(firstpointbuffer.y-lastpointbuffer.y));
		        g2.setColor(MyJframe.forecolor);
		        g2.drawOval(Math.min(firstpointbuffer.x, lastpointbuffer.x), Math.min(firstpointbuffer.y, lastpointbuffer.y),
		        		Math.abs(firstpointbuffer.x-lastpointbuffer.x), Math.abs(firstpointbuffer.y-lastpointbuffer.y));
		    }
		    else {
		    	g2.drawOval(Math.min(firstpointbuffer.x, lastpointbuffer.x), Math.min(firstpointbuffer.y, lastpointbuffer.y),
		        Math.abs(firstpointbuffer.x-lastpointbuffer.x), Math.abs(firstpointbuffer.y-lastpointbuffer.y));
		    }
		}
		//矩形
		for(RectShape rect:rects) {
		    g2.setColor(rect.forecolor);
		    g2.setStroke(rect.thickness);
		    
		    if(rect.fullcheck) {
		    	g2.setColor(rect.backcolor);
		        g2.fillRect(rect.startpoint.x, rect.startpoint.y, rect.width, rect.height);
		        g2.setColor(rect.forecolor);
		        g2.drawRect(rect.startpoint.x, rect.startpoint.y, rect.width, rect.height);
		    }
		    else {
		    	g2.drawRect(rect.startpoint.x, rect.startpoint.y, rect.width, rect.height);
		    }
		}
		if(MyJframe.currenttool =="矩形") {
		    g2.setStroke(new BasicStroke(MyJframe.paintsize));
		    g2.setColor(MyJframe.forecolor);
		    if(MyJframe.fullcheck) {
		    	g2.setColor(MyJframe.backcolor);
		        g2.fillRect(Math.min(firstpointbuffer.x, lastpointbuffer.x), Math.min(firstpointbuffer.y, lastpointbuffer.y),
		        		Math.abs(firstpointbuffer.x-lastpointbuffer.x), Math.abs(firstpointbuffer.y-lastpointbuffer.y));
		        g2.setColor(MyJframe.forecolor);
		        g2.drawRect(Math.min(firstpointbuffer.x, lastpointbuffer.x), Math.min(firstpointbuffer.y, lastpointbuffer.y),
		        		Math.abs(firstpointbuffer.x-lastpointbuffer.x), Math.abs(firstpointbuffer.y-lastpointbuffer.y));
		    }
		    else {
		        g2.drawRect(Math.min(firstpointbuffer.x, lastpointbuffer.x), Math.min(firstpointbuffer.y, lastpointbuffer.y),
		        		Math.abs(firstpointbuffer.x-lastpointbuffer.x), Math.abs(firstpointbuffer.y-lastpointbuffer.y));
		    }
		}
	}
	public void clearPanel() {
		  points.clear();
		  lines.clear();
		  ovals.clear();
		  rects.clear();
		  firstpointbuffer = new Point();
		  lastpointbuffer = new Point();
		  repaint();
		  MyJframe.clean = false;
	}
	
	class PaintPoint extends Point{
		public int size;
		public Color forecolor;
		public PaintPoint(Point p,int size,Color forecolor ) {
			super(p);
		    this.size = size;
		    this.forecolor = forecolor;
		}
	}

	class LinePoints{
		public Color forecolor;
		public Point firstpoint;
		public Point lastpoint;
	    public BasicStroke thickness;
	    public LinePoints(Point one , Point two, BasicStroke thickness, Color forecolor) {
	    	firstpoint = one;
	    	lastpoint = two;
	    	this.thickness = thickness;
	    	this.forecolor = forecolor;
	    }
	}

	class OvalShape{
		public Point startpoint;
		public Point endpoint;
		public int width, height;
		public Color forecolor;
		public Color backcolor;
		public Boolean fullcheck;
		public BasicStroke thickness;
		public OvalShape(Point p,Point p2,Color forecolor,Color backcolor,Boolean fullcheck,BasicStroke thickness) {
			this.startpoint = p;
		    this.endpoint = p2;
		    this.forecolor = forecolor;
		    this.backcolor = backcolor;
		    this.fullcheck = fullcheck;
		    this.thickness = thickness;
		    this.width = Math.abs(p2.x-p.x);
		    this.height = Math.abs(p2.y-p.y);
		   
		    if(p2.x<p.x) {
		    	this.startpoint.x = p2.x;
		    }
		    if(p2.y<p.y){
		    	this.startpoint.y = p2.y;
		    }
		   
		}
	}

	class RectShape{
		public Point startpoint;
		public Point endpoint;
		public Color forecolor;
		public Color backcolor;
		public int width,height;
		public boolean fullcheck;
		public BasicStroke thickness;
		public RectShape(Point p,Point p2,Color forecolor,Color backcolor,Boolean fullcheck,BasicStroke thickness) {
			this.startpoint = p;
		    this.endpoint = p2;
		    this.forecolor = forecolor; 
		    this.backcolor = backcolor;
		    this.thickness = thickness;
		    this.fullcheck = fullcheck; 
		    this.width = Math.abs(p2.x-p.x);
		    this.height = Math.abs(p.y-p2.y);
		    if(p2.x<p.x) {
		    	this.startpoint.x = p2.x;
		    }
		    if(p2.y<p.y){
		    	this.startpoint.y = p2.y;
		    }
		}
	}
}

