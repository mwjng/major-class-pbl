import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JRadioButton;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class ChatEmojiList extends JFrame {
	public ImageIcon[] emojis = {
			new ImageIcon("src/emoji/angry.png"), new ImageIcon("src/emoji/happy.png"), new ImageIcon("src/emoji/love.png"), 
			new ImageIcon("src/emoji/sad.png"), new ImageIcon("src/emoji/shocked.png"), new ImageIcon("src/emoji/smile.png"), 
			new ImageIcon("src/emoji/smile2.png"), new ImageIcon("src/emoji/smile3.png"), new ImageIcon("src/emoji/smiling.png"),
			new ImageIcon("src/emoji/thinking.png")
	};
	private JPanel contentPane;
	private String sendingTo;
	private String myName;
	private ChatRoomFrame room;
	
	public JButton emojiBtns[] = new JButton[10];
	private int index = 0;
	public boolean check = false;
	
	private JavaObjClientView view;
	
	public ChatEmojiList(ChatRoomFrame room, JavaObjClientView view, String myName, String sendingTo) {
		super("이모티콘");
		this.myName = myName;
		this.sendingTo = sendingTo;
		this.room = room;
		this.view = view;
	
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setLayout(new GridLayout(3,4));
		
		for (int i = 0; i < emojis.length; i++) {
			Image image = emojis[i].getImage();
			image = image.getScaledInstance(58, 58, java.awt.Image.SCALE_SMOOTH);
			emojis[i] = new ImageIcon(image);
		}
		
		for (int i = 0; i < emojiBtns.length; i++) {
			emojiBtns[i] = new JButton("");
			emojiBtns[i].setIcon(emojis[i]);
			emojiBtns[i].setContentAreaFilled(false);
			emojiBtns[i].setBorder(null);
			emojiBtns[i].addActionListener(new EmojiAction());
			add(emojiBtns[i]);
			contentPane.add(emojiBtns[i]);
		}
		this.addWindowListener(new SafeClose());		
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 300, 300, 250);
		setVisible(true);	
	}
		
	class EmojiAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			for (int i = 0; i < emojiBtns.length; i++) {
				if (e.getSource() == emojiBtns[i]) {
					index = i;
					ImageIcon emo = emojis[index];
					SingleChatMsg obcm = new SingleChatMsg(myName, "600", sendingTo, "EMOJI");
					obcm.setEmoji(emo);
					obcm.setChatRoomTitle(myName);
					//room.AppendTextR("[" + myName + "]");
					//room.AppendEmoji(emo);
					view.SendObject(obcm);
				}
			}
			dispose();
		}
	}
	
	class SafeClose extends WindowAdapter {
		
		public void windowClosing(WindowEvent e) {
			JFrame frame = (JFrame)e.getWindow();	
			frame.dispose();
		}
	}
}