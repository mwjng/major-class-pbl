import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.sound.sampled.*;
import java.io.*;

public class PacMan extends JFrame {
	private sPanel panel = new sPanel();
	private ImageIcon title = new ImageIcon("images/title.png");
	private Image image = title.getImage();
	private Clip clip;
	Game nf;	
	public PacMan() {
		setTitle("PAC-MAN");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600,400);
		setLocation(120,60);

		nf = new Game(this);
        setContentPane(panel); 
        panel.setLayout(null);
        panel.setBackground(Color.black);
        JButton btn1 = new JButton("S T A R T"); 
        JButton btn2 = new JButton("E X I T");
        btn1.setSize(120, 50);
        btn1.setLocation(150, 290);
        btn1.setBackground(Color.red);
        btn1.setForeground(Color.yellow);
        btn2.setSize(120, 50);
        btn2.setLocation(300, 290);
        btn2.setBackground(Color.red);
        btn2.setForeground(Color.yellow);
        btn1.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
            	nf.setVisible(true);
            	dispose(); 
            	clip.stop();
            }
        }); 
        btn2.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		System.exit(0);
            }
        });     
        panel.add(btn1); 
        panel.add(btn2);     
        setVisible(true);
        loadAudio("audio/pacman2.wav");
        clip.start();
    }
	class sPanel extends JPanel {
    	public void paintComponent(Graphics g) {
    		super.paintComponent(g);
    		g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);
    	}
    }
	private void loadAudio(String pathName) {
        try {
            clip = AudioSystem.getClip();
            File audioFile = new File(pathName);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            clip.open(audioStream);
        }
        catch (LineUnavailableException e) { e.printStackTrace(); }
        catch (UnsupportedAudioFileException e) { e.printStackTrace(); }
        catch (IOException e) { e.printStackTrace(); }
    }
	public static void main(String[] args) {
		new PacMan();
    }	
}

class Game extends JFrame {		
	private ImageIcon player1 = new ImageIcon("images/pacman.png");
	private ImageIcon player2 = new ImageIcon("images/pacman2.png");
	private ImageIcon player3 = new ImageIcon("images/pacman3.png");
	private ImageIcon player4 = new ImageIcon("images/pacman4.png");
	private ImageIcon enemyr1 = new ImageIcon("images/enemyr1.png");
	private ImageIcon enemyr2 = new ImageIcon("images/enemyr2.png");
	private ImageIcon enemyr3 = new ImageIcon("images/enemyr3.png");
	private ImageIcon enemyr4 = new ImageIcon("images/enemyr4.png");
	private ImageIcon enemyg1 = new ImageIcon("images/enemyg1.png");
	private ImageIcon enemyg2 = new ImageIcon("images/enemyg2.png");
	private ImageIcon enemyg3 = new ImageIcon("images/enemyg3.png");
	private ImageIcon enemyg4 = new ImageIcon("images/enemyg4.png");
	private ImageIcon coin = new ImageIcon("images/coin.png");
	private ImageIcon bigcoin = new ImageIcon("images/bigcoin.png");
	private ImageIcon wall = new ImageIcon("images/blue.png");
	private ImageIcon space = new ImageIcon("images/black.png");
	private ImageIcon ending = new ImageIcon("images/ending.png");
	
	private int playerX = 17, playerY = 11, enemyX = 7, enemyY = 14;
	private int enemyX2 = 11, enemyY2 = 6;
	private int score = 0, item = 170;
	private Icon a, b;
	private Icon save = space;
	private Icon save2 = space;
	
	JLabel [][] m = new JLabel[21][21];
	PacMan sf;
	public Game(PacMan sf) {
		this.sf = sf;
		setTitle("PAC-MAN");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container c = getContentPane();
		c.setLayout(new BorderLayout());
		for(int i=0; i<21; i++) {
			for(int j=0; j<21; j++) {
				m[i][j] = new JLabel();
			}
		}
		c.add(new NorthPanel(), BorderLayout.NORTH);
		c.add(new CenterPanel(), BorderLayout.CENTER);
		
		setSize(1000, 1030);
		setResizable(false);
	}
	
	public void change() {
		getContentPane().removeAll();
		getContentPane().add(new endGame());
		revalidate();
		repaint();
	}
	
	class ScoreThread extends Thread {
		private JLabel Score;
		public ScoreThread(JLabel Score) {
			this.Score = Score;
		}
		@Override
		public void run() {
			while(true) {
				Score.setText(Integer.toString(score));
				if(item == 0)
					change();
				try {
					Thread.sleep(100);
				}
				catch(InterruptedException e) {
					return;
				}
			}
		}
	}
	
	class MyKeyAdapter extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			int KeyCode = e.getKeyCode();
			switch(KeyCode) {
			case KeyEvent.VK_RIGHT:
				if((m[playerX][playerY+1].getIcon()).equals(wall))
					m[playerX][playerY].setIcon(player1);
				else if(((m[playerX][playerY+1].getIcon()).equals(enemyr1))||((m[playerX][playerY+1].getIcon()).equals(enemyr2))||
						((m[playerX][playerY+1].getIcon()).equals(enemyr3))||((m[playerX][playerY+1].getIcon()).equals(enemyr4))||
						((m[playerX][playerY+1].getIcon()).equals(enemyg1))||((m[playerX][playerY+1].getIcon()).equals(enemyg2))||
						((m[playerX][playerY+1].getIcon()).equals(enemyg3))||((m[playerX][playerY+1].getIcon()).equals(enemyg4))) {
					m[playerX][playerY].setIcon(space);
					change();
				}
				else {
					if(m[playerX][playerY+1].getIcon().equals(coin)) {
						score+=10;
						item--;
					}
					if(m[playerX][playerY+1].getIcon().equals(bigcoin)) {
						score+=50;
						item--;
					}
					m[playerX][playerY].setIcon(space);
					m[playerX][playerY+1].setIcon(player1);
					playerY++;
				}
				break;
			case KeyEvent.VK_LEFT:
				if((m[playerX][playerY-1].getIcon()).equals(wall))
					m[playerX][playerY].setIcon(player2);
				else if(((m[playerX][playerY-1].getIcon()).equals(enemyr1))||((m[playerX][playerY-1].getIcon()).equals(enemyr2))||
						((m[playerX][playerY-1].getIcon()).equals(enemyr3))||((m[playerX][playerY-1].getIcon()).equals(enemyr4))||
						((m[playerX][playerY-1].getIcon()).equals(enemyg1))||((m[playerX][playerY-1].getIcon()).equals(enemyg2))||
						((m[playerX][playerY-1].getIcon()).equals(enemyg3))||((m[playerX][playerY-1].getIcon()).equals(enemyg4))) {
					m[playerX][playerY].setIcon(space);
					change();
				}
				else {
					if(m[playerX][playerY-1].getIcon().equals(coin)) {
						score+=10;
						item--;
					}
					if(m[playerX][playerY-1].getIcon().equals(bigcoin)) {
						score+=50;
						item--;
					}
					m[playerX][playerY].setIcon(space);
					m[playerX][playerY-1].setIcon(player2);
					playerY--;
				}
				break;
			case KeyEvent.VK_UP:
				if((m[playerX-1][playerY].getIcon()).equals(wall))
					m[playerX][playerY].setIcon(player3);
				else if(((m[playerX-1][playerY].getIcon()).equals(enemyr1))||((m[playerX-1][playerY].getIcon()).equals(enemyr2))||
						((m[playerX-1][playerY].getIcon()).equals(enemyr3))||((m[playerX-1][playerY].getIcon()).equals(enemyr4))||
						((m[playerX-1][playerY].getIcon()).equals(enemyg1))||((m[playerX-1][playerY].getIcon()).equals(enemyg2))||
						((m[playerX-1][playerY].getIcon()).equals(enemyg3))||((m[playerX-1][playerY].getIcon()).equals(enemyg4))) {
					m[playerX][playerY].setIcon(space);
					change();
				}
				else {
					if(m[playerX-1][playerY].getIcon().equals(coin)) {
						score+=10;
						item--;
					}
					if(m[playerX-1][playerY].getIcon().equals(bigcoin)) {
						score+=50;
						item--;
					}
					m[playerX][playerY].setIcon(space);
					m[playerX-1][playerY].setIcon(player3);
					playerX--;
				}
				break;
			case KeyEvent.VK_DOWN:
				if((m[playerX+1][playerY].getIcon()).equals(wall))
					m[playerX][playerY].setIcon(player4);
				else if(((m[playerX+1][playerY].getIcon()).equals(enemyr1))||((m[playerX+1][playerY].getIcon()).equals(enemyr2))||
						((m[playerX+1][playerY].getIcon()).equals(enemyr3))||((m[playerX+1][playerY].getIcon()).equals(enemyr4))||
						((m[playerX+1][playerY].getIcon()).equals(enemyg1))||((m[playerX+1][playerY].getIcon()).equals(enemyg2))||
						((m[playerX+1][playerY].getIcon()).equals(enemyg3))||((m[playerX+1][playerY].getIcon()).equals(enemyg4))) {
					m[playerX][playerY].setIcon(space);
					change();
				}
				else {
					if(m[playerX+1][playerY].getIcon().equals(coin)) {
						score+=10;
						item--;
					}
					if(m[playerX+1][playerY].getIcon().equals(bigcoin)) {
						score+=50;
						item--;
					}
					m[playerX][playerY].setIcon(space);
					m[playerX+1][playerY].setIcon(player4);
					playerX++;
				}
				break;
			}
		}
	}
	
	class enemyThread extends Thread {
		private int enemyX, enemyY;
		public enemyThread(int enemyX, int enemyY) {
			this.enemyX = enemyX;
			this.enemyY = enemyY;
		}
		@Override
		public void run() {
			while(true) {
				enemyMove();
				try {
					Thread.sleep(100);
				}
				catch(InterruptedException e) {
					return;
				}
			}
		}
		public void enemyMove() {
			int way = (int)(Math.random()*4);
			switch(way) {
			case 0:
				if ((!(m[enemyX+1][enemyY].getIcon()).equals(wall)) && ((!(m[enemyX+1][enemyY].getIcon()).equals(enemyg1))||
						(!(m[enemyX+1][enemyY].getIcon()).equals(enemyg2))||(!(m[enemyX+1][enemyY].getIcon()).equals(enemyg3))||
						(!(m[enemyX+1][enemyY].getIcon()).equals(enemyg4)))) {
					a = m[enemyX+1][enemyY].getIcon();
					m[enemyX+1][enemyY].setIcon(enemyr4);
					m[enemyX][enemyY].setIcon(save);
					save = a;
					enemyX++;
				}
				break;
			case 1:
				if ((!(m[enemyX-1][enemyY].getIcon()).equals(wall)) && ((!(m[enemyX-1][enemyY].getIcon()).equals(enemyg1))||
						(!(m[enemyX-1][enemyY].getIcon()).equals(enemyg2))||(!(m[enemyX-1][enemyY].getIcon()).equals(enemyg3))||
						(!(m[enemyX-1][enemyY].getIcon()).equals(enemyg4)))) {
					a = m[enemyX-1][enemyY].getIcon();
					m[enemyX-1][enemyY].setIcon(enemyr3);
					m[enemyX][enemyY].setIcon(save);
					save = a;
					enemyX--;
				}
				break;
			case 2:
				if ((!(m[enemyX][enemyY+1].getIcon()).equals(wall)) && ((!(m[enemyX][enemyY+1].getIcon()).equals(enemyg1))||
						(!(m[enemyX][enemyY+1].getIcon()).equals(enemyg2))||(!(m[enemyX][enemyY+1].getIcon()).equals(enemyg3))||
						(!(m[enemyX][enemyY+1].getIcon()).equals(enemyg4)))){
					a = m[enemyX][enemyY+1].getIcon();
					m[enemyX][enemyY+1].setIcon(enemyr1);
					m[enemyX][enemyY].setIcon(save);
					save = a;
					enemyY++;
				}
				break;
			case 3:
				if ((!(m[enemyX][enemyY-1].getIcon()).equals(wall)) && ((!(m[enemyX][enemyY-1].getIcon()).equals(enemyg1))||
						(!(m[enemyX][enemyY-1].getIcon()).equals(enemyg2))||(!(m[enemyX][enemyY-1].getIcon()).equals(enemyg3))||
						(!(m[enemyX][enemyY-1].getIcon()).equals(enemyg4)))){
					a = m[enemyX][enemyY-1].getIcon();
					m[enemyX][enemyY-1].setIcon(enemyr2);
					m[enemyX][enemyY].setIcon(save);
					save = a;
					enemyY--;
				}
				break;
			}
			if(playerX == enemyX && playerY == enemyY) {
				m[enemyX][enemyY].setIcon(enemyr4);
				change();
			}
		}
	}
	class enemyThread2 extends Thread {
		private int enemyX2, enemyY2;
		public enemyThread2(int enemyX2, int enemyY2) {
			this.enemyX2 = enemyX2;
			this.enemyY2 = enemyY2;
		}
		@Override
		public void run() {
			while(true) {
				enemyMove2();
				try {
					Thread.sleep(500);
				}
				catch(InterruptedException e) {
					return;
				}
			}
		}
		public void enemyMove2() {
			int way2 = (int)(Math.random()*4);
			switch(way2) {
			case 0:
				if ((!(m[enemyX2+1][enemyY2].getIcon()).equals(wall)) && ((!(m[enemyX2+1][enemyY2].getIcon()).equals(enemyr1))||
						(!(m[enemyX2+1][enemyY2].getIcon()).equals(enemyr2))||(!(m[enemyX2+1][enemyY2].getIcon()).equals(enemyr3))||
						(!(m[enemyX2+1][enemyY2].getIcon()).equals(enemyr4)))) {
					b = m[enemyX2+1][enemyY2].getIcon();
					m[enemyX2+1][enemyY2].setIcon(enemyg4);
					m[enemyX2][enemyY2].setIcon(save2);
					save2 = b;
					enemyX2++;
				}
				break;
			case 1:
				if ((!(m[enemyX2-1][enemyY2].getIcon()).equals(wall)) && ((!(m[enemyX2-1][enemyY2].getIcon()).equals(enemyr1))||
						(!(m[enemyX2-1][enemyY2].getIcon()).equals(enemyr2))||(!(m[enemyX2-1][enemyY2].getIcon()).equals(enemyr3))||
						(!(m[enemyX2-1][enemyY2].getIcon()).equals(enemyr4)))) {
					b = m[enemyX2-1][enemyY2].getIcon();
					m[enemyX2-1][enemyY2].setIcon(enemyg3);
					m[enemyX2][enemyY2].setIcon(save2);
					save2 = b;
					enemyX2--;
				}
				break;
			case 2:
				if ((!(m[enemyX2][enemyY2+1].getIcon()).equals(wall)) && ((!(m[enemyX2][enemyY2+1].getIcon()).equals(enemyr1))||
						(!(m[enemyX2][enemyY2+1].getIcon()).equals(enemyr2))||(!(m[enemyX2][enemyY2+1].getIcon()).equals(enemyr3))||
						(!(m[enemyX2][enemyY2+1].getIcon()).equals(enemyr4)))) {
					b = m[enemyX2][enemyY2+1].getIcon();
					m[enemyX2][enemyY2+1].setIcon(enemyg1);
					m[enemyX2][enemyY2].setIcon(save2);
					save2 = b;
					enemyY2++;
				}
				break;
			case 3:
				if ((!(m[enemyX2][enemyY2-1].getIcon()).equals(wall)) && ((!(m[enemyX2][enemyY2-1].getIcon()).equals(enemyr1))||
						(!(m[enemyX2][enemyY2-1].getIcon()).equals(enemyr2))||(!(m[enemyX2][enemyY2-1].getIcon()).equals(enemyr3))||
						(!(m[enemyX2][enemyY2-1].getIcon()).equals(enemyr4)))) {
					b = m[enemyX2][enemyY2-1].getIcon();
					m[enemyX2][enemyY2-1].setIcon(enemyg2);
					m[enemyX2][enemyY2].setIcon(save2);
					save2 = b;
					enemyY2--;
				}
				break;
			}
			if(playerX == enemyX2 && playerY == enemyY2) {
				m[enemyX2][enemyY2].setIcon(enemyg4);
				change();
			}
		}
	}
	
	class NorthPanel extends JPanel {
		NorthPanel() {
			setBackground(Color.black);
			setLayout(new FlowLayout());
			JLabel Menu = new JLabel("SCORE : ");
			JLabel Score = new JLabel();
			Menu.setForeground(Color.red);
			Score.setForeground(Color.white);
			Score.setText(Integer.toString(score));
			Menu.setFont(new Font("Gothic", Font.CENTER_BASELINE, 30));
			Score.setFont(new Font("Gothic", Font.CENTER_BASELINE, 30));
			add(Menu); add(Score);
			ScoreThread th = new ScoreThread(Score);
			th.start();
		}
	}
	
	class CenterPanel extends JPanel {
		CenterPanel() {
			setBackground(Color.BLACK);
			setLayout(new GridLayout(21,21));
			for(int i=0; i<21; i++) {
				for(int j=0; j<21; j++) {
					m[i][j].setIcon(space);
					add(m[i][j]);
				}
			}
			drawMap();
			addKeyListener(new MyKeyAdapter());
			setFocusable(true);
			requestFocus();
			enemyThread t1 = new enemyThread(enemyX, enemyY);
			enemyThread2 t2 = new enemyThread2(enemyX2, enemyY2);
			t1.start(); t2.start();
		}
		
		public void drawMap() {
			m[1][8].setIcon(wall); m[1][12].setIcon(wall); m[2][2].setIcon(wall);
			m[2][3].setIcon(wall); m[2][4].setIcon(wall); m[2][5].setIcon(wall);
			m[2][6].setIcon(wall); m[2][14].setIcon(wall); m[2][15].setIcon(wall);
			m[2][16].setIcon(wall); m[2][17].setIcon(wall); m[2][18].setIcon(wall);
			m[2][8].setIcon(wall); m[2][12].setIcon(wall); m[3][2].setIcon(wall);
			m[3][10].setIcon(wall); m[3][18].setIcon(wall); m[2][10].setIcon(wall);
			m[4][4].setIcon(wall); m[4][6].setIcon(wall); m[4][7].setIcon(wall);
			m[4][8].setIcon(wall); m[4][10].setIcon(wall); m[4][12].setIcon(wall); 
			m[4][13].setIcon(wall); m[4][14].setIcon(wall); m[4][16].setIcon(wall);
			m[5][1].setIcon(wall); m[5][2].setIcon(wall); m[5][4].setIcon(wall);
			m[5][16].setIcon(wall); m[5][18].setIcon(wall); m[5][19].setIcon(wall);
			m[6][4].setIcon(wall); m[6][5].setIcon(wall); m[6][7].setIcon(wall); 
			m[6][8].setIcon(wall); m[6][9].setIcon(wall); m[6][10].setIcon(wall);
			m[6][11].setIcon(wall); m[6][12].setIcon(wall); m[6][13].setIcon(wall);
			m[6][15].setIcon(wall); m[6][16].setIcon(wall); m[7][2].setIcon(wall);
			m[7][18].setIcon(wall); m[8][2].setIcon(wall); m[8][3].setIcon(wall);
			m[8][5].setIcon(wall); m[8][7].setIcon(wall); m[8][8].setIcon(wall);
			m[8][9].setIcon(wall); m[8][11].setIcon(wall); m[8][12].setIcon(wall);
			m[8][13].setIcon(wall); m[8][15].setIcon(wall); m[8][17].setIcon(wall);
			m[8][18].setIcon(wall); m[9][5].setIcon(wall); m[9][7].setIcon(wall);
			m[9][13].setIcon(wall); m[9][15].setIcon(wall); m[10][2].setIcon(wall);
			m[10][4].setIcon(wall); m[10][5].setIcon(wall); m[10][7].setIcon(wall);
			m[10][8].setIcon(wall); m[10][9].setIcon(wall); m[10][10].setIcon(wall);
			m[10][11].setIcon(wall); m[10][12].setIcon(wall); m[10][13].setIcon(wall);
			m[10][15].setIcon(wall); m[10][16].setIcon(wall); m[10][18].setIcon(wall);
			m[11][2].setIcon(wall); m[11][18].setIcon(wall); m[12][2].setIcon(wall);
			m[12][3].setIcon(wall); m[12][5].setIcon(wall); m[12][6].setIcon(wall);
			m[12][7].setIcon(wall); m[12][8].setIcon(wall); m[12][10].setIcon(wall);
			m[12][12].setIcon(wall); m[12][13].setIcon(wall); m[12][14].setIcon(wall);
			m[12][15].setIcon(wall); m[12][17].setIcon(wall); m[12][18].setIcon(wall);
			m[13][5].setIcon(wall); m[13][10].setIcon(wall); m[13][15].setIcon(wall);
			m[14][1].setIcon(wall); m[14][3].setIcon(wall); m[14][5].setIcon(wall);
			m[14][7].setIcon(wall); m[14][8].setIcon(wall); m[14][9].setIcon(wall);
			m[14][10].setIcon(wall); m[14][11].setIcon(wall); m[14][12].setIcon(wall);
			m[14][13].setIcon(wall); m[14][15].setIcon(wall); m[14][17].setIcon(wall);
			m[14][19].setIcon(wall); m[15][3].setIcon(wall); m[15][17].setIcon(wall);
			m[16][2].setIcon(wall); m[16][3].setIcon(wall); m[16][5].setIcon(wall);
			m[16][6].setIcon(wall); m[16][7].setIcon(wall); m[16][8].setIcon(wall);
			m[16][10].setIcon(wall); m[16][12].setIcon(wall); m[16][13].setIcon(wall);
			m[16][14].setIcon(wall); m[16][15].setIcon(wall); m[16][17].setIcon(wall);
			m[16][18].setIcon(wall); m[17][5].setIcon(wall); m[17][10].setIcon(wall);
			m[17][15].setIcon(wall); m[18][2].setIcon(wall); m[18][3].setIcon(wall);
			m[18][5].setIcon(wall); m[18][7].setIcon(wall); m[18][8].setIcon(wall);
			m[18][9].setIcon(wall); m[18][10].setIcon(wall); m[18][11].setIcon(wall);
			m[18][12].setIcon(wall); m[18][13].setIcon(wall); m[18][15].setIcon(wall);
			m[18][17].setIcon(wall); m[18][18].setIcon(wall); m[19][5].setIcon(wall);
			m[19][15].setIcon(wall); m[1][1].setIcon(coin); m[1][2].setIcon(coin);
			m[1][3].setIcon(coin); m[1][4].setIcon(coin); m[1][5].setIcon(coin);
			m[1][6].setIcon(coin); m[1][7].setIcon(coin); m[1][9].setIcon(coin);
			m[1][10].setIcon(coin); m[1][11].setIcon(coin); m[1][13].setIcon(coin);
			m[1][14].setIcon(coin); m[1][15].setIcon(coin); m[1][16].setIcon(coin);
			m[1][17].setIcon(coin); m[1][18].setIcon(coin); m[1][19].setIcon(coin);
			m[2][1].setIcon(bigcoin); m[2][7].setIcon(coin); m[2][9].setIcon(coin);
			m[2][11].setIcon(coin); m[2][13].setIcon(coin); m[2][19].setIcon(bigcoin);
			m[3][1].setIcon(coin); m[3][3].setIcon(coin); m[3][4].setIcon(coin);
			m[3][5].setIcon(coin); m[3][6].setIcon(coin); m[3][7].setIcon(coin);
			m[3][8].setIcon(coin); m[3][9].setIcon(coin); m[3][11].setIcon(coin);
			m[3][12].setIcon(coin); m[3][13].setIcon(coin); m[3][14].setIcon(coin);
			m[3][15].setIcon(coin); m[3][16].setIcon(coin); m[3][17].setIcon(coin);
			m[3][19].setIcon(coin); m[4][1].setIcon(coin); m[4][2].setIcon(coin);
			m[4][3].setIcon(coin); m[4][5].setIcon(coin); m[4][9].setIcon(coin);
			m[4][11].setIcon(coin); m[4][15].setIcon(coin); m[4][17].setIcon(coin);
			m[4][18].setIcon(coin); m[4][19].setIcon(coin); m[5][3].setIcon(coin);
			m[5][5].setIcon(coin); m[5][6].setIcon(coin); m[5][7].setIcon(coin);
			m[5][8].setIcon(coin); m[5][9].setIcon(coin); m[5][10].setIcon(coin);
			m[5][11].setIcon(coin); m[5][12].setIcon(coin); m[5][13].setIcon(coin);
			m[5][14].setIcon(coin);m[5][15].setIcon(coin); m[5][17].setIcon(coin);
			m[6][1].setIcon(coin); m[6][2].setIcon(coin); m[6][3].setIcon(coin);
			m[6][17].setIcon(coin); m[6][18].setIcon(coin); m[6][19].setIcon(coin);
			m[7][1].setIcon(coin); m[7][3].setIcon(coin); m[7][4].setIcon(coin);
			m[7][16].setIcon(coin); m[7][17].setIcon(coin); m[7][19].setIcon(coin);
			m[8][1].setIcon(coin); m[8][16].setIcon(coin); m[8][4].setIcon(coin);
			m[8][19].setIcon(coin); m[9][1].setIcon(coin); m[9][2].setIcon(coin);
			m[9][3].setIcon(coin); m[9][4].setIcon(coin); m[9][16].setIcon(coin);
			m[9][17].setIcon(coin); m[9][18].setIcon(coin); m[9][19].setIcon(coin);
			m[10][1].setIcon(coin); m[10][3].setIcon(coin); m[10][17].setIcon(coin);
			m[10][19].setIcon(coin); m[11][1].setIcon(coin); m[11][3].setIcon(coin);
			m[11][4].setIcon(coin); m[11][16].setIcon(coin); m[11][17].setIcon(coin);
			m[11][19].setIcon(coin); m[12][1].setIcon(coin); m[12][4].setIcon(coin);
			m[12][16].setIcon(coin); m[12][19].setIcon(coin); m[13][1].setIcon(coin);
			m[13][2].setIcon(coin); m[13][3].setIcon(coin); m[13][4].setIcon(coin);
			m[13][16].setIcon(coin); m[13][17].setIcon(coin); m[13][18].setIcon(coin);
			m[13][19].setIcon(coin); m[14][2].setIcon(coin); m[14][4].setIcon(coin);
			m[14][16].setIcon(coin); m[14][18].setIcon(coin); m[15][1].setIcon(bigcoin);
			m[15][2].setIcon(coin); m[15][4].setIcon(coin); m[15][5].setIcon(coin);
			m[15][6].setIcon(coin); m[15][7].setIcon(coin); m[15][8].setIcon(coin);
			m[15][9].setIcon(coin); m[15][12].setIcon(coin); m[15][13].setIcon(coin);
			m[15][14].setIcon(coin); m[15][15].setIcon(coin); m[15][16].setIcon(coin);
			m[15][18].setIcon(coin); m[15][19].setIcon(bigcoin); m[16][1].setIcon(coin);
			m[16][4].setIcon(coin); m[16][9].setIcon(coin); m[16][16].setIcon(coin);
			m[16][19].setIcon(coin); m[17][1].setIcon(coin); m[17][2].setIcon(coin);
			m[17][3].setIcon(coin); m[17][4].setIcon(coin); m[17][6].setIcon(coin);
			m[17][7].setIcon(coin); m[17][8].setIcon(coin); m[17][9].setIcon(coin);
			m[17][11].setIcon(player1); m[17][12].setIcon(coin); m[17][13].setIcon(coin);
			m[17][14].setIcon(coin); m[17][16].setIcon(coin); m[17][17].setIcon(coin);
			m[17][18].setIcon(coin);m[17][19].setIcon(coin); m[18][1].setIcon(coin);
			m[18][4].setIcon(coin); m[18][6].setIcon(coin); m[18][14].setIcon(coin);
			m[18][16].setIcon(coin); m[18][19].setIcon(coin); m[19][1].setIcon(coin);
			m[19][2].setIcon(coin); m[19][3].setIcon(coin); m[19][4].setIcon(coin);
			m[19][6].setIcon(coin); m[19][7].setIcon(coin); m[19][8].setIcon(coin);
			m[19][9].setIcon(coin); m[19][10].setIcon(coin); m[19][11].setIcon(coin);
			m[19][12].setIcon(coin); m[19][13].setIcon(coin); m[19][14].setIcon(coin);
			m[19][16].setIcon(coin); m[19][17].setIcon(coin); m[19][18].setIcon(coin); 
			m[19][19].setIcon(coin); m[7][14].setIcon(enemyr4); m[11][6].setIcon(enemyg4);
			for(int i=0; i<21; i++) {				
				m[0][i].setIcon(wall);
				m[i][0].setIcon(wall);
				m[20][i].setIcon(wall);
				m[i][20].setIcon(wall);
			}
		}
	}
	
	class endGame extends JPanel {
		private Image img = ending.getImage();
		endGame() {
			setLayout(null);
			JLabel label1 = new JLabel("SCORE");
			JLabel label2 = new JLabel(Integer.toString(score));
			JButton btn1 = new JButton("돌아가기");
			JButton btn2 = new JButton("그만하기");
			label1.setSize(370, 100); label2.setSize(180, 100);
			label1.setForeground(Color.yellow); label2.setForeground(Color.green);
			label1.setBackground(Color.black); label2.setBackground(Color.black);
			label1.setOpaque(true); label2.setOpaque(true);
	        btn1.setSize(200, 70); btn2.setSize(200, 70);
	        label1.setFont(new Font("Gothic", Font.CENTER_BASELINE, 100));
	        label2.setFont(new Font("Gothic", Font.CENTER_BASELINE, 80));
	        btn1.setFont(new Font("Gothic", Font.CENTER_BASELINE, 20));
	        btn2.setFont(new Font("Gothic", Font.CENTER_BASELINE, 20));
	        btn1.setBackground(Color.lightGray); btn1.setForeground(Color.black);
	        btn2.setBackground(Color.lightGray); btn2.setForeground(Color.black);
	        label1.setLocation(300, 200); label2.setLocation(400, 320);
	        btn1.setLocation(375, 500); btn2.setLocation(375, 600);
	        add(label1); add(label2); add(btn1); add(btn2);
	        btn1.addActionListener(new MyActionListener1());
	        btn2.addActionListener(new MyActionListener2());
		}
		
		public void paintComponent(Graphics g) {
    		super.paintComponent(g);
    		g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
    	}

		class MyActionListener1 implements ActionListener {
	        public void actionPerformed(ActionEvent e) {
	        	dispose();
	            new PacMan();
	        }
	    }
		class MyActionListener2 implements ActionListener {
	        public void actionPerformed(ActionEvent e) {
	        	System.exit(0);
	        }
	    }
	}
}
