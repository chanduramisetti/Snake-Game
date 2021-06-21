import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.math.*;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.util.Random;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements ActionListener{
	static final int screen_w=600;
	static final int screen_h=600;
	static final int u_size =25;
	static final int g_units=(screen_w*screen_h)/u_size;
 int delay=50;
	int[] x=new int[g_units];
	int[] y=new int[g_units];
	int co=0;
	int body_p=6,d=0;
	int apples_e=0,apple_y,apple_x,apple_x1,apple_y1;
	char direc ='D',m='M';
	boolean run=false,t=false;
	boolean b=false,b1=false;
	Timer timer;
	Random random;
	int c=0;
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
		//run=true;
		t=false;
		delay=50;
		timer=new Timer(delay,this);
		timer.start();
		x=new int[g_units];
		y=new int[g_units];
		b1=false;
		apples_e=0;
		body_p=6;
	}
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		draw(g);
	}
	public void draw(Graphics g)
	{
		if(!b&&!b1)
		{
			sound2();
			b1=true;
		}
		if(!b)
		{
			g.setColor(Color.red);
			g.setFont(new Font("Ink Free",Font.ITALIC,30));
			FontMetrics met=getFontMetrics(g.getFont());
			g.drawString("Click 'S' to start", (screen_w-met.stringWidth("Click 'S' to start"))/2, 300);
			g.setFont(new Font("Ink Free",Font.ROMAN_BASELINE,40));
			FontMetrics met1=getFontMetrics(g.getFont());
			g.drawString("Snakeyy snakee..", (screen_w-met1.stringWidth("Snakeyy snakee..."))/2, 100);
			g.setColor(Color.black);
			g.setFont(new Font("Ink Free",Font.BOLD,15));
			 met1=getFontMetrics(g.getFont());
			g.drawString("Designed and Developed by :", (screen_w-met1.stringWidth("Desigend and Developed by :"))/2, 400);
			g.drawString("Chandu Ramisetti", (screen_w-met1.stringWidth("Chandu Ramisetti"))/2, 420);
			g.drawString("email : chandu978.ramisetti@gmail.com", (screen_w-met1.stringWidth("email : chandu978.ramisetti@gmail.com"))/2, 440);
			g.drawString("contact : +91 6303859756", (screen_w-met1.stringWidth("contact : +91303859756"))/2, 460);
			//System.out.println("controlgot_!b");
				
		}
		if(b) {
		if(run) {
			/*
		for(int i=0;i<screen_w/u_size;i++) {
			g.drawLine(i*u_size,0,i*u_size, screen_h);
			g.drawLine(0,i*u_size, screen_w,i*u_size);
		}*/
			if(apples_e>40)
				timer.setDelay(delay-40);
			else if(apples_e>30)
				timer.setDelay(delay-30);
			else if(apples_e>20)
				timer.setDelay(delay-20);
			else if(apples_e>10)
				timer.setDelay(delay-10);
			else
				timer.setDelay(delay);
			
			
		g.setColor(Color.green);
		g.fillOval(apple_x, apple_y, u_size, u_size);
		/*if(apples_e%6==0&&apples_e!=0)
		{
			g.setColor(Color.orange);
			g.fillOval(apple_x1, apple_y1, u_size, u_size);
			
		}*/
		
		
		for(int i=0;i<body_p;i++)
		{
			if(i==0)
			{
				g.setColor(Color.black);
				g.fillOval(x[i], y[i], u_size, u_size);
				
			}
			else
			{
				g.setColor(Color.black);
				g.fillOval(x[i], y[i], u_size, u_size);
				
				
			}
		}
		
g.setColor(Color.black);
		
		g.setFont(new Font("Ink Free",Font.ITALIC,20));
		FontMetrics met=getFontMetrics(g.getFont());
		g.drawString("Score: "+apples_e, (screen_w-met.stringWidth("Score: "+apples_e))/2, g.getFont().getSize());
		g.setColor(Color.black);
		g.setFont(new Font("Ink Free",Font.BOLD,10));
		FontMetrics met1=getFontMetrics(g.getFont());
		g.drawString("x-co :"+x[0]+" y-co :"+y[0]+" x_app : "+apple_x1+" y_app : "+apple_y1, 25,25);
		
	}
		else {
			gameOver(g);
		}
		
	}
	}
	public void newApple() {
		apple_x=random.nextInt((int)(screen_w/10))*10;
		apple_y=random.nextInt((int)(screen_h/10))*10;
	}
	public void newBigAplle() {
		apple_x1=random.nextInt((int)(screen_w/10))*10;
		apple_y1=random.nextInt((int)(screen_h/10))*10;
	}
	public void move() {
		for(int i=body_p;i>0;i--) {
			x[i]=x[i-1];
			y[i]=y[i-1];
			d++;
			d=d%6;
		}
		switch(direc) {
		case 'U':
			y[0]=y[0]-10;
			break;
		case 'D':
			y[0]=y[0]+10;
			break;
		case 'R':
			x[0]=x[0]+10;
			break;
		case 'L':

			x[0]=x[0]-10;
			break;
		}
	}
	/*public void checkBig() {
		if(Math.abs(x[0]-apple_x1)<=10 && Math.abs(y[0]-apple_y1)<=10)
		{
			//timer.stop();
			body_p+=1;
			apples_e+=1;
			co++;
			}
			
		}*/
	
	public void checkApple() {
		if(Math.abs(x[0]-apple_x)<=10 && Math.abs(y[0]-apple_y)<=10) {
			if(apples_e>40)
			body_p+=5;
			else if(apples_e>30)
				body_p+=4;
			else if(apples_e>20)
				body_p+=3;
			else if(apples_e>10)
				body_p+=2;
			else
				body_p++;
			apples_e++;
			co++;
			//timer.stop();
			sound1();
				newApple();

		}
	}
	public void sound1() {
		try {
			File file=new File("C:\\Users\\Lenovo\\Downloads\\smb_coin.wav");
			AudioInputStream audio=AudioSystem.getAudioInputStream(file);
			Clip clip=AudioSystem.getClip();
			clip.open(audio);
			clip.start();
		}catch(Exception e)
		{
			System.out.println("exception"+e);
		}
	}
	public void sound2() {
		try {
			File file=new File("C:\\Users\\Lenovo\\Downloads\\smb_stage_clear.wav");
			AudioInputStream audio=AudioSystem.getAudioInputStream(file);
			Clip clip=AudioSystem.getClip();
			clip.open(audio);
			clip.start();
		}catch(Exception e)
		{
			System.out.println("exception"+e);
		}
	}
	public void sound3() {
		try {
			File file=new File("C:\\Users\\Lenovo\\Downloads\\smb_mariodie.wav");
			AudioInputStream audio=AudioSystem.getAudioInputStream(file);
			Clip clip=AudioSystem.getClip();
			clip.open(audio);
			clip.start();
		}catch(Exception e)
		{
			System.out.println("exception"+e);
		}
	}
	public void sound4() {
		try {
			File file=new File("C:\\Users\\Lenovo\\Downloads\\smb_jump-small.wav");
			AudioInputStream audio=AudioSystem.getAudioInputStream(file);
			Clip clip=AudioSystem.getClip();
			clip.open(audio);
			clip.start();
		}catch(Exception e)
		{
			System.out.println("exception"+e);
		}
	}
	public void checkCollisions() {
		for(int i=body_p;i>0;i--)
		{
			if((x[0]==x[i])&&(y[0]==y[i]))
			{
				sound3();
				run=false;
			}
		}
		if(x[0]<0)
		{
			x[0]=screen_w;
			sound4();
		}
		if(x[0]>screen_w)
		{
			x[0]=0;
			sound4();
		}
		if(y[0]<0)
		{
			y[0]=screen_h;
			sound4();
		}
		if(y[0]>screen_h)
		{
			y[0]=0;
			sound4();
		}
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
		g.drawString("Click Enter to Home", (screen_w-met1.stringWidth("Click Enter to Home"))/2, 400);
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(run) {
			move();
			checkApple();
			//checkBig();
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
			case KeyEvent.VK_S:
				System.out.println("start");
				b=true;
				run=true;
				break;
			case KeyEvent.VK_ENTER:
				b=false;
				run=false;
				startGame();
				System.out.println("e");
				break;
			}
		}
	}
}
