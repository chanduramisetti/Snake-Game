import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import java.util.Random;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements ActionListener{
	static final int screen_w=600;
	static final int screen_h=600;
	static final int u_size =25;
	static final int g_units=(screen_w*screen_h)/u_size;
	static final int delay=75;
	final int[] x=new int[g_units];
	final int[] y=new int[g_units];
	int co=0;
	int body_p=6;
	int apples_e=0,apple_y,apple_x,apple_x1,apple_y1;
	char direc ='D',m='M';
	boolean run=false,t=false;
	boolean b=false;
	Timer timer;
	Random random;
	GamePanel(){
		random=new Random();
		this.setPreferredSize(new Dimension(screen_w,screen_h));
		this.setBackground(Color.gray);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		startGame();
	}
	public void startGame()
	{
		newApple();
		run=true;
		t=false;
		timer=new Timer(delay,this);
		timer.start();
		
	}
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		draw(g);
	}
	public void draw(Graphics g)
	{
		if(run) {
			/*
		for(int i=0;i<screen_w/u_size;i++) {
			g.drawLine(i*u_size,0,i*u_size, screen_h);
			g.drawLine(0,i*u_size, screen_w,i*u_size);
		}*/
		g.setColor(Color.green);
		g.fillOval(apple_x, apple_y, u_size, u_size);
		if(co%6==0&&co!=0)
		{
			g.setColor(Color.orange);
			g.fillOval(apple_x, apple_y, u_size, u_size);
			
		}
		
		
		for(int i=0;i<body_p;i++)
		{
			if(i==0)
			{
				g.setColor(Color.black);
				g.fillOval(x[i], y[i], u_size-2, u_size-2);
				
			}
			else
			{
				if(b) {
				g.setColor(Color.gray);
				g.fillRect(x[i], y[i], u_size, u_size);
				g.setColor(new Color(120,10,10));
				g.fillOval(x[i]+12, y[i]+12, u_size/3,u_size/3);
				g.setColor(new Color(120,10,10));
				g.fillOval(x[i]+7, y[i], u_size/3,u_size/3);
				g.setColor(new Color(120,10,10));
				g.fillOval(x[i], y[i]+12, u_size/3,u_size/3);
				}
				else 
				{
					g.setColor(Color.gray);
					g.fillRect(x[i], y[i], u_size, u_size);
					g.setColor(new Color(120,10,10));
					g.fillOval(x[i]+7, y[i]+12, u_size/3,u_size/3);
					g.setColor(new Color(120,10,10));
					g.fillOval(x[i], y[i], u_size/3,u_size/3);
					g.setColor(new Color(120,10,10));
					g.fillOval(x[i]+12, y[i], u_size/3,u_size/3);
				}
				
			}
		}
		
	}
		else
			gameOver(g);
		g.setColor(Color.black);
		g.setFont(new Font("Ink Free",Font.ITALIC,20));
		FontMetrics met=getFontMetrics(g.getFont());
		g.drawString("Score: "+apples_e, (screen_w-met.stringWidth("Score: "+apples_e))/2, g.getFont().getSize());
		g.setColor(Color.black);
		g.setFont(new Font("Ink Free",Font.BOLD,10));
		FontMetrics met1=getFontMetrics(g.getFont());
		g.drawString("x-co :"+x[0]+" y-co :"+y[0]+" x_app : "+apple_x1+" y_app : "+apple_y1, 25,25);
	}
	public void newApple() {
		apple_x=random.nextInt((int)(screen_w/u_size))*u_size;
		apple_y=random.nextInt((int)(screen_h/u_size))*u_size;
	}
	public void newBigAplle() {
		apple_x1=random.nextInt((int)(screen_w/u_size))*u_size;
		apple_y1=random.nextInt((int)(screen_h/u_size))*u_size;
	}
	public void move() {
		for(int i=body_p;i>0;i--) {
			x[i]=x[i-1];
			y[i]=y[i-1];
			b=!b;
		}
		switch(direc) {
		case 'U':
			y[0]=y[0]-u_size;
			break;
		case 'D':
			y[0]=y[0]+u_size;
			break;
		case 'R':
			x[0]=x[0]+u_size;
			break;
		case 'L':

			x[0]=x[0]-u_size;
			break;
		}
	}
	public void checkBig() {
		if(x[0]==apple_x1 && y[0]==apple_y1)
		{
			//timer.stop();
			body_p++;
			apples_e++;
			co++;
			
		}
	}
	public void checkApple() {
		if(x[0]==apple_x && y[0]==apple_y) {
			body_p++;
			apples_e++;
			co++;
			newApple();
			b=!b;
			if(apples_e%6==0 && apples_e!=0)
				newBigAplle();
		}
	}
	public void checkCollisions() {
		for(int i=body_p;i>0;i--)
		{
			if((x[0]==x[i])&&(y[0]==y[i]))
				run=false;
		}
		if(x[0]<0)
			x[0]=screen_w;
		if(x[0]>screen_w)
			x[0]=0;
		if(y[0]<0)
			y[0]=screen_h;
		if(y[0]>screen_h)
			y[0]=0;
		if(!run)
			timer.stop();
	}
	public void gameOver(Graphics g)
	{
		g.setColor(Color.black);
		g.setFont(new Font("Ink Free",Font.ITALIC,75));
		FontMetrics met=getFontMetrics(g.getFont());
		g.drawString("GAME OVER", (screen_w-met.stringWidth("GAME OVER"))/2, screen_h/2);
		
		g.setColor(Color.black);
		g.setFont(new Font("Ink Free",Font.BOLD,20));
		FontMetrics met1=getFontMetrics(g.getFont());
		g.drawString("Score: "+apples_e, (screen_w-met1.stringWidth("Score: "+apples_e))/2, g.getFont().getSize());
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(run) {
			move();
			checkApple();
			checkBig();
			checkCollisions();
			
		}
		repaint();
	}
	public class MyKeyAdapter extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				if(direc!='R'&& y[0]!=600)
					direc='L';
				//System.out.println("key");
				break;
			case KeyEvent.VK_RIGHT:
				if(direc!='L'&& y[0]!=600)
					direc='R';
				break;
			case KeyEvent.VK_UP:
				if(direc!='D'&& x[0]!=600)
					direc='U';
				break;
			case KeyEvent.VK_DOWN:
				if(direc!='U' && x[0]!=600)
					direc='D';
				break;
			case KeyEvent.VK_SPACE:
				t=!t;
				if(t)
				timer.stop();
				else
					timer.start();
				break;
			}
		}
	}
}
