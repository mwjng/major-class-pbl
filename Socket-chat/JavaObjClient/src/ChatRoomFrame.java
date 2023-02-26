
// JavaObjClientView.java ObjecStram 기반 Client
//실질적인 채팅 창
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.SystemColor;
import java.awt.Color;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.JToggleButton;
import javax.swing.JList;

public class ChatRoomFrame extends JFrame {
	private String roomName;
	private JLabel lblNewLabel;
	private JButton imgBtn;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtInput;
	private String sendingTo;
	private JButton btnSend;
	private static final int BUF_LEN = 128; // Windows 처럼 BUF_LEN 을 정의
	// private JTextArea textArea;
	private JTextPane textArea;

	//private ObjectOutputStream oos;
	//private ObjectInputStream ois;
	
	private Frame frame;
	private FileDialog fd;
	private JButton emojiSendBtn;
	
	// 단톡방 유저 리스트
	private List<String> multiChatUsers = new ArrayList<String>();
	
	// 채팅방의 대화 기록 임시 저장 기능
	//private List chats;
	
	// private ChatRoomList chatRoomList;
	private String myName;
	private JButton fileSendBtn;
	// oos, ois
	// view.oos 사용 
	private JavaObjClientView view;
	private String chatRoomID;
	private JButton fbutton;
	private JButton button_icon;
	private List<String> chatsVer5 = new ArrayList<>();
	private JLabel date;
	
	LocalDate now = LocalDate.now();
	int year = now.getYear();
	int month = now.getMonthValue();
	int day = now.getDayOfMonth();
	
	private ImageIcon basic_profile = new ImageIcon("src/icon1.jpg");
	
	public String getTime() {
		LocalTime now = LocalTime.now();
		 
        // 현재시간 출력
        System.out.println(now);  // 06:20:57.008731300
 
        // 포맷 정의하기
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH시 mm분");
 
        // 포맷 적용하기
        String formatedNow = now.format(formatter);
        
        return formatedNow;
	}
	
	// 단체 채팅방을 위한 생성
	public ChatRoomFrame(JavaObjClientView view, String myName, List<String> checkedNames, List<String> chats) {
		this.myName = myName;
		this.view = view;

		this.addWindowListener(new SafeClose());
		setBounds(100, 100, 387, 620);
		
		int forTitle = checkedNames.size() - 2;
		String multiRoomTitle = checkedNames.get(0) + ", " + checkedNames.get(1) + "외 " + forTitle + "명";
		
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 67, 349, 345);
		scrollPane.setBorder(null);
		contentPane.add(scrollPane);

		textArea = new JTextPane();
		textArea.setEditable(true);
		textArea.setBackground(new Color(186, 206, 224));
		textArea.setBorder(null);
		textArea.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		scrollPane.setViewportView(textArea);

		txtInput = new JTextField();
		txtInput.setBounds(0, 409, 380, 121);
		txtInput.setBorder(null);
		txtInput.addActionListener(new TextSendAction());
		contentPane.add(txtInput);
		txtInput.setColumns(10);
		
		date = new JLabel("New label");		
		setDateLabel(date);
		scrollPane.setColumnHeaderView(date);
		
		btnSend = new JButton("전송");
		btnSend.setBackground(new Color(255, 235, 51));
		btnSend.addActionListener(new TextSendAction());
		btnSend.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
		btnSend.setBounds(299, 554, 69, 29);
		contentPane.add(btnSend);
		setVisible(true);

		emojiSendBtn = new JButton("");
		emojiSendBtn.setIcon(new ImageIcon("src/이모티콘.JPG"));
		emojiSendBtn.setBounds(0, 554, 50, 40);
		emojiSendBtn.setContentAreaFilled(false);
		emojiSendBtn.setBorder(null);
		emojiSendBtn.addActionListener(new EmojiSendAction());
		contentPane.add(emojiSendBtn);		
		
		imgBtn = new JButton("");
		imgBtn.setIcon(new ImageIcon("src/이미지첨부.JPG"));
		imgBtn.addActionListener(new ImageSendAction());
		imgBtn.setBounds(77, 550, 50, 40);
		imgBtn.setContentAreaFilled(false);
		imgBtn.setBorder(null);
		contentPane.add(imgBtn);
		
		fileSendBtn = new JButton("");
		fileSendBtn.setIcon(new ImageIcon("src/파일첨부.JPG"));
		fileSendBtn.addActionListener(new FileSendAction());
		fileSendBtn.setBounds(37, 554, 50, 40);
		fileSendBtn.setContentAreaFilled(false);
		fileSendBtn.setBorder(null);
		contentPane.add(fileSendBtn);
		
		JLabel lblNewLabel_2 = new JLabel("New label");
		ImageIcon img = new ImageIcon("src/multiIcon.png");
		Image ori_img = img.getImage();
		Image small_img = ori_img.getScaledInstance(60, 60, Image.SCALE_SMOOTH);
		ImageIcon new_icon = new ImageIcon(small_img);
		
		lblNewLabel_2.setIcon(new_icon);
		lblNewLabel_2.setBounds(12, 10, 56, 47);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel = new JLabel(multiRoomTitle);
		lblNewLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		lblNewLabel.setBounds(77, 10, 284, 29);
		contentPane.add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBorder(null);
		panel.setBackground(new Color(186, 206, 224));
		panel.setBounds(0, 0, 380, 412);
		contentPane.add(panel);
		
		JLabel lblNewLabel_1 = new JLabel("New label");
		lblNewLabel_1.setBounds(0, 10, 65, 58);
		contentPane.add(lblNewLabel_1);
		txtInput.requestFocus();
	}
	
	public void setChatRoomID(String id) {
		this.chatRoomID = id;
	}
	
	public String getChatRoomID() {
		return chatRoomID;
	}
		
	public String getMyName() {
		return myName;
	}
	
	private void setDateLabel(JLabel label) {
		label.setForeground(new Color(128, 128, 128));
		label.setOpaque(true);
		label.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBackground(new Color(186, 206, 224));
		label.setText(year + "년" + month + "월" + day + "일");
	}
	
	// 보내는 사람 입장, newFreindList와 연결됨
	
	public ChatRoomFrame(JavaObjClientView view, String myName, String sendingTo, ImageIcon img) {
		//oos = view.oos;
		//ois = view.ois;
		this.myName = myName;
		this.sendingTo = sendingTo;
		this.view = view;
		this.addWindowListener(new SafeClose());
		setBounds(100, 100, 387, 620);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 67, 349, 345);
		scrollPane.setBorder(null);
		contentPane.add(scrollPane);

		textArea = new JTextPane();
		textArea.setEditable(true);
		textArea.setBackground(new Color(186, 206, 224));
		textArea.setBorder(null);
		textArea.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		scrollPane.setViewportView(textArea);
		
		date = new JLabel("New label");		
		setDateLabel(date);
		scrollPane.setColumnHeaderView(date);

		txtInput = new JTextField();
		txtInput.setBounds(0, 409, 380, 121);
		txtInput.setBorder(null);
		txtInput.addActionListener(new TextSendAction());
		contentPane.add(txtInput);
		txtInput.setColumns(10);
		
		btnSend = new JButton("전송");
		btnSend.setBackground(new Color(255, 235, 51));
		btnSend.addActionListener(new TextSendAction());
		btnSend.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
		btnSend.setBounds(299, 554, 69, 29);
		contentPane.add(btnSend);
		setVisible(true);
		
		// 이미지
		JLabel lblNewLabel_2 = new JLabel("New label");
		if (img == null) {
			img = new ImageIcon("src/multiIcon.png");
		}
		Image ori_img = img.getImage();
		Image small_img = ori_img.getScaledInstance(60, 60, Image.SCALE_SMOOTH);
		ImageIcon new_icon = new ImageIcon(small_img);
		
		lblNewLabel_2.setIcon(new_icon);
		lblNewLabel_2.setBounds(12, 10, 56, 47);
		contentPane.add(lblNewLabel_2);
		

		emojiSendBtn = new JButton("");
		emojiSendBtn.setIcon(new ImageIcon("src/이모티콘.JPG"));
		//emojiSendBtn.setFont(new Font("굴림", Font.PLAIN, 16));
		emojiSendBtn.setBounds(0, 554, 50, 40);
		emojiSendBtn.setContentAreaFilled(false);
		emojiSendBtn.setBorder(null);
		emojiSendBtn.addActionListener(new EmojiSendAction());
		contentPane.add(emojiSendBtn);		
		
		imgBtn = new JButton("");
		imgBtn.setIcon(new ImageIcon("src/이미지첨부.JPG"));
		//imgBtn.setFont(new Font("굴림", Font.PLAIN, 16));
		imgBtn.addActionListener(new ImageSendAction());
		imgBtn.setBounds(77, 550, 50, 40);
		imgBtn.setContentAreaFilled(false);
		imgBtn.setBorder(null);
		contentPane.add(imgBtn);
		
		fileSendBtn = new JButton("");
		fileSendBtn.setIcon(new ImageIcon("src/파일첨부.JPG"));
		//fileSendBtn.setFont(new Font("굴림", Font.PLAIN, 16));
		fileSendBtn.addActionListener(new FileSendAction());
		fileSendBtn.setBounds(37, 554, 50, 40);
		fileSendBtn.setContentAreaFilled(false);
		fileSendBtn.setBorder(null);
		contentPane.add(fileSendBtn);
		
		JLabel lblNewLabel = new JLabel(sendingTo);
		lblNewLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		lblNewLabel.setBounds(77, 10, 284, 29);
		contentPane.add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBorder(null);
		panel.setBackground(new Color(186, 206, 224));
		panel.setBounds(0, 0, 380, 412);
		contentPane.add(panel);
		
		txtInput.requestFocus();
	}
	
	class SafeClose extends WindowAdapter {
		
		public void windowClosing(WindowEvent e) {
			JFrame frame = (JFrame)e.getWindow();
			frame.dispose();
		}
	}
	
	
	// 받는 사람 입장	
	public void addChat(String msg) {
		chatsVer5.add(msg);
	}
	/**
	 * @wbp.parser.constructor
	 */
	public ChatRoomFrame(JavaObjClientView view, String myName, String sendingTo, List<String> chats, String roomID, ImageIcon img) {
		this.myName = myName;
		this.sendingTo = sendingTo;
		this.chatRoomID = roomID;
		this.view = view;
		
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.addWindowListener(new SafeClose());
		setBounds(100, 100, 387, 620);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 67, 349, 345);
		scrollPane.setBorder(null);
		contentPane.add(scrollPane);

		date = new JLabel("New label");		
		setDateLabel(date);
		scrollPane.setColumnHeaderView(date);
		
		textArea = new JTextPane();
		textArea.setEditable(true);
		textArea.setBackground(new Color(186, 206, 224));
		textArea.setBorder(null);
		textArea.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		scrollPane.setViewportView(textArea);

		txtInput = new JTextField();
		txtInput.setBounds(0, 409, 380, 121);
		txtInput.setBorder(null);
		txtInput.addActionListener(new TextSendAction());
		contentPane.add(txtInput);
		txtInput.setColumns(10);
		
		btnSend = new JButton("전송");
		btnSend.setBackground(new Color(255, 235, 51));
		btnSend.addActionListener(new TextSendAction());
		btnSend.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
		btnSend.setBounds(299, 554, 69, 29);
		contentPane.add(btnSend);
		setVisible(true);
		
		JLabel lblNewLabel_2 = new JLabel("New label");
		
		if (img == null) {
			img = new ImageIcon("src/multiIcon.png");
		}
		Image ori_img = img.getImage();
		Image small_img = ori_img.getScaledInstance(60, 60, Image.SCALE_SMOOTH);
		ImageIcon new_icon = new ImageIcon(small_img);
		
		lblNewLabel_2.setIcon(new_icon);
		lblNewLabel_2.setBounds(12, 10, 56, 47);
		contentPane.add(lblNewLabel_2);

		emojiSendBtn = new JButton("");
		emojiSendBtn.setIcon(new ImageIcon("src/이모티콘.JPG"));
		//emojiSendBtn.setFont(new Font("굴림", Font.PLAIN, 16));
		emojiSendBtn.setBounds(0, 554, 50, 40);
		emojiSendBtn.setContentAreaFilled(false);
		emojiSendBtn.setBorder(null);
		emojiSendBtn.addActionListener(new EmojiSendAction());
		contentPane.add(emojiSendBtn);		
		
		imgBtn = new JButton("");
		imgBtn.setIcon(new ImageIcon("src/이미지첨부.JPG"));
		//imgBtn.setFont(new Font("굴림", Font.PLAIN, 16));
		imgBtn.addActionListener(new ImageSendAction());
		imgBtn.setBounds(77, 550, 50, 40);
		imgBtn.setContentAreaFilled(false);
		imgBtn.setBorder(null);
		contentPane.add(imgBtn);
		
		fileSendBtn = new JButton("");
		fileSendBtn.setIcon(new ImageIcon("src/파일첨부.JPG"));
		//fileSendBtn.setFont(new Font("굴림", Font.PLAIN, 16));
		fileSendBtn.addActionListener(new FileSendAction());
		fileSendBtn.setBounds(37, 554, 50, 40);
		fileSendBtn.setContentAreaFilled(false);
		fileSendBtn.setBorder(null);
		contentPane.add(fileSendBtn);
		
		JLabel lblNewLabel = new JLabel(sendingTo);
		lblNewLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		lblNewLabel.setBounds(77, 10, 284, 29);
		contentPane.add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBorder(null);
		panel.setBackground(new Color(186, 206, 224));
		panel.setBounds(0, 0, 380, 412);
		contentPane.add(panel);
		
		JLabel lblNewLabel_1 = new JLabel("New label");
		lblNewLabel_1.setBounds(0, 10, 65, 58);
		contentPane.add(lblNewLabel_1);
				
		for(String chat: chats) {
			if (chat == "") continue;
			AppendText(sendingTo, chat);
		}
		
//		for(String chat: chatsVer5) {
//			if (chat == "") continue;
//			AppendText(sendingTo, chat);
//		}
		chats.clear();
		txtInput.requestFocus();
	}

	public String getSendingTo() {
		return sendingTo;
	}
	
	public void AppendRecivedEmoji(String from, ImageIcon ori_icon) {
		//msg = msg.trim(); // 앞뒤 blank와 \n을 제거한다.
		StyledDocument doc = textArea.getStyledDocument();
		SimpleAttributeSet left = new SimpleAttributeSet();
		StyleConstants.setAlignment(left, StyleConstants.ALIGN_LEFT);
		//StyleConstants.setBackground(left, Color.WHITE);
	    doc.setParagraphAttributes(doc.getLength(), 1, left, false);
		try {
			doc.insertString(doc.getLength(), "\n"+from+"\n", left );
			//doc.insertString(doc.getLength(), msg+"\n", left );
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int len = textArea.getDocument().getLength();
		textArea.setCaretPosition(len);
		textArea.insertIcon(ori_icon);
		
		len = textArea.getDocument().getLength();
		textArea.setCaretPosition(len);
		textArea.replaceSelection("\n");
	}
	
	public void AppendTextREmojiVer(ImageIcon ori_icon) {
		//msg = msg.trim();
		StyledDocument doc = textArea.getStyledDocument();
		SimpleAttributeSet right = new SimpleAttributeSet();
		StyleConstants.setAlignment(right, StyleConstants.ALIGN_RIGHT);
		StyleConstants.setBackground(right, Color.YELLOW);
		//StyleConstants.setBackground(right, Color.YELLOW);	
	    doc.setParagraphAttributes(doc.getLength(), 1, right, false);
	    //AppendTextR("[" + myName + "]");
		//AppendTextR(msg);
//		try {
//			doc.insertString(doc.getLength(),msg+"\n\n", right );
//		} catch (BadLocationException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		int len = textArea.getDocument().getLength();
		textArea.setCaretPosition(len);
		textArea.insertIcon(ori_icon);
		len = textArea.getDocument().getLength();
		textArea.setCaretPosition(len);
		textArea.replaceSelection("\n");
	}
	
	public void AppendText(String sendingTo, String msg) {
		//msg = msg.trim(); // 앞뒤 blank와 \n을 제거한다.
		StyledDocument doc = textArea.getStyledDocument();
		SimpleAttributeSet left = new SimpleAttributeSet();
		StyleConstants.setAlignment(left, StyleConstants.ALIGN_LEFT);
		StyleConstants.setBackground(left, new Color(186, 206, 224));
		
	    doc.setParagraphAttributes(doc.getLength(), 1, left, false);
		try {
			doc.insertString(doc.getLength(), "\n"+sendingTo+"\n", left );
			//doc.insertString(doc.getLength(), msg+"\n", left );
			StyleConstants.setBackground(left, Color.WHITE);
			doc.insertString(doc.getLength(), msg, left );
			StyleConstants.setBackground(left, new Color(186, 206, 224));
			StyleConstants.setFontSize(left, 10);
			doc.insertString(doc.getLength(), " "+getTime()+"\n", left );
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int len = textArea.getDocument().getLength();
		textArea.setCaretPosition(len);
		textArea.replaceSelection("\n");
	}	
	
	public void AppendTextR(String msg) {
		//msg = msg.trim();
		StyledDocument doc = textArea.getStyledDocument();
		SimpleAttributeSet right = new SimpleAttributeSet();
		StyleConstants.setAlignment(right, StyleConstants.ALIGN_RIGHT);
		int size = StyleConstants.getFontSize(right);
		StyleConstants.setBackground(right, new Color(186, 206, 224));
		StyleConstants.setFontSize(right, 10);
		try {
			doc.insertString(doc.getLength(), getTime()+" ", right);
		} catch (BadLocationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}		
		StyleConstants.setFontSize(right, 15);
		StyleConstants.setBackground(right, Color.YELLOW);	
	    doc.setParagraphAttributes(doc.getLength(), 1, right, false);
	    //AppendTextR("[" + myName + "]");
		//AppendTextR(msg);
		try {
			doc.insertString(doc.getLength(),msg+"\n\n", right );
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int len = textArea.getDocument().getLength();
		textArea.setCaretPosition(len);
	}
	
	public void AppendTextRX(String msg) {
		//msg = msg.trim();
		StyledDocument doc = textArea.getStyledDocument();
		SimpleAttributeSet right = new SimpleAttributeSet();
		StyleConstants.setAlignment(right, StyleConstants.ALIGN_RIGHT);
		StyleConstants.setBackground(right, Color.YELLOW);	
	    doc.setParagraphAttributes(doc.getLength(), 1, right, false);
	    //AppendTextR("[" + myName + "]");
		//AppendTextR(msg);
		try {
			doc.insertString(doc.getLength(),msg, right );
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int len = textArea.getDocument().getLength();
		textArea.setCaretPosition(len);
	}
	
	public void AppendTextX(String sendingTo, String msg) {
		//msg = msg.trim(); // 앞뒤 blank와 \n을 제거한다.
		StyledDocument doc = textArea.getStyledDocument();
		SimpleAttributeSet left = new SimpleAttributeSet();
		StyleConstants.setAlignment(left, StyleConstants.ALIGN_LEFT);
		StyleConstants.setBackground(left, Color.WHITE);
	    doc.setParagraphAttributes(doc.getLength(), 1, left, false);
		try {
			doc.insertString(doc.getLength(), sendingTo, left );
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int len = textArea.getDocument().getLength();
		textArea.setCaretPosition(len);
		textArea.replaceSelection("\n");
	}
	
	public void AppendFile(SingleChatMsg scm) {
		//fileName = fileName.trim();
		int len = textArea.getDocument().getLength();
		textArea.setCaretPosition(len);
		fbutton = new JButton(scm.getData());
		fbutton.setBorder(null);
		fbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 파일 저장 경로
				 File rfile = new File("./download/" + scm.getData());
				 try {
					 FileReader fr = new FileReader(scm.file);
					 BufferedReader br = new BufferedReader(fr);
					 FileWriter fw = new FileWriter(rfile);
					 BufferedWriter bw = new BufferedWriter(fw);
					 String line = "";
					 while( (line = br.readLine()) != null ){
						 //System.out.println(line);
						 bw.write(line);
						 bw.newLine();
						 bw.flush();
					 }
					 fr.close();
					 br.close();
					 fw.close();
					 bw.close();

				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		textArea.replaceSelection("\n");
		textArea.insertComponent(fbutton);		
	}
	
	public void AppendProfileImage(ImageIcon ori_icon) {
		int len = textArea.getDocument().getLength();
		textArea.setCaretPosition(len); // place caret at the end (with no selection)
		Image ori_img = ori_icon.getImage();
		int width, height;
		double ratio;
		width = ori_icon.getIconWidth();
		height = ori_icon.getIconHeight();
		// Image가 너무 크면 최대 가로 또는 세로 200 기준으로 축소시킨다.
//		if (width > 200 || height > 200) {
//			if (width > height) { // 가로 사진
//				ratio = (double) height / width;
//				width = 200;
//				height = (int) (width * ratio);
//			} else { // 세로 사진
//				ratio = (double) width / height;
//				height = 200;
//				width = (int) (height * ratio);
//			}
//			Image new_img = ori_img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
//			ImageIcon new_icon = new ImageIcon(new_img);
//			textArea.insertIcon(new_icon);
//		} else
		Image small_img = ori_img.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		ImageIcon new_icon = new ImageIcon(small_img);
		textArea.insertIcon(new_icon);
		//len = textArea.getDocument().getLength();
		//textArea.setCaretPosition(len);
		//textArea.replaceSelection("\n");
	}
	
	public void AppendImage(ImageIcon ori_icon) {
		int len = textArea.getDocument().getLength();
		textArea.setCaretPosition(len); // place caret at the end (with no selection)
		Image ori_img = ori_icon.getImage();
		int width, height;
		double ratio;
		width = ori_icon.getIconWidth();
		height = ori_icon.getIconHeight();
		button_icon = new JButton("");
		button_icon.setBorder(null);
		button_icon.setBackground(SystemColor.window);
		// Image가 너무 크면 최대 가로 또는 세로 200 기준으로 축소시킨다.
		if (width > 200 || height > 200) {
			if (width > height) { // 가로 사진
				ratio = (double) height / width;
				width = 200;
				height = (int) (width * ratio);
			} else { // 세로 사진
				ratio = (double) width / height;
				height = 200;
				width = (int) (height * ratio);
			}
			Image new_img = ori_img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
			ImageIcon new_icon = new ImageIcon(new_img);
			button_icon.setIcon(new_icon);
		} else
			button_icon.setIcon(ori_icon);
		button_icon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ImageViewer(ori_icon);
			}
		});
		textArea.insertComponent(button_icon);
		len = textArea.getDocument().getLength();
		textArea.setCaretPosition(len);
		textArea.replaceSelection("\n");
	}
	
	class ImageViewer extends JFrame {
		private JPanel contentPane;
		public ImageViewer(ImageIcon img) {
			setBounds(100, 100, 300, 300);
			contentPane = new JPanel() {
				Image image = img.getImage();
				public void paint(Graphics g) {
					g.drawImage(image, 0, 0, this);	
				}
			};
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			contentPane.setLayout(null);
			setContentPane(contentPane);
			this.setVisible(true);
		}
		
		class SafeClose extends WindowAdapter {
			
			public void windowClosing(WindowEvent e) {
				JFrame frame = (JFrame)e.getWindow();	
				frame.dispose();
			}
		}
	}
	
	
	class MultiChatFrameSendAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {	
			if (e.getSource() == btnSend || e.getSource() == txtInput) {
				System.out.println(txtInput.getText());
				String msg = null;
				msg = txtInput.getText();
				//SendMultiUserInfo(myName, msg);
				//AppendText(msg);
				SendMultiMessage(msg);
				txtInput.setText(""); // 메세지를 보내고 나면 메세지 쓰는창을 비운다.
				txtInput.requestFocus(); // 메세지를 보내고 커서를 다시 텍스트 필드로 위치시킨다
				if (msg.contains("/exit")) // 종료 처리
					System.exit(0);
			}	
		}		
	}
	
	// keyboard enter key 치면 서버로 전송
	class TextSendAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// Send button을 누르거나 메시지 입력하고 Enter key 치면
			if (e.getSource() == btnSend || e.getSource() == txtInput) {
				System.out.println(txtInput.getText());
				String msg = null;
				// msg = String.format("[%s] %s\n", UserName, txtInput.getText());
				msg = txtInput.getText();
				//AppendTextR("[" + myName + "]");
				//AppendTextR(msg);
				SendMessage(chatRoomID, myName, msg);
				txtInput.setText(""); // 메세지를 보내고 나면 메세지 쓰는창을 비운다.
				txtInput.requestFocus(); // 메세지를 보내고 커서를 다시 텍스트 필드로 위치시킨다
				if (msg.contains("/exit")) // 종료 처리
					System.exit(0);
			}
		}
	}
	// Server에게 network으로 전송
			public void SendMultiMessage(String msg) {	
					// 11-10
					//for(String user: multiChatUsers) {					
					//	if (user.equals(myName))
					//		continue;
					//SingleChatMsg(String id, String code, String to, String roomID, String msg)
						SingleChatMsg obcm = new SingleChatMsg(myName, "200", chatRoomID, msg);
						view.SendObject(obcm);
						//oos.writeObject(obcm);
					//}				
			}
			
			public void SendMultiObject(Object ob) {	
				for(String user: multiChatUsers) {					
					if (user.equals(myName))
						continue;
					view.SendObject(ob);
				}
			}
//			public void SendMultiMessage(String myName, String msg) {	
//				try {
//					// 11-10
//					for(String user: multiChatUsers) {					
//						if (user.equals(myName))
//							continue;
//						SingleChatMsg obcm = new SingleChatMsg(myName, "301", user, msg);
//						oos.writeObject(obcm);
//					}
//				} catch (IOException e) {
//					try {
//						ois.close();
//						oos.close();
//					} catch (IOException e1) {
//						e1.printStackTrace();
//						System.exit(0);
//					}
//				}
//			}
	
	// Server에게 network으로 전송
//		public void SendMultiMessage(String myName, String msg) {	
//			try {
//				// 11-10 11-14
//				SingleChatMsg obcm = new SingleChatMsg(myName, "200", chatRoomID, msg);
//				oos.writeObject(obcm);
//				//for(String user: multiChatUsers) {					
//				//	if (user.equals(myName))
//				//		continue;
//				//	SingleChatMsg obcm = new SingleChatMsg(myName, "200", user, msg);
//				//	oos.writeObject(obcm);
//				//}
//			} catch (IOException e) {
//				try {
//					ois.close();
//					oos.close();
//				} catch (IOException e1) {
//					e1.printStackTrace();
//					System.exit(0);
//				}
//			}
//		}
	
	// Server에게 network으로 전송, 방 번호도 보내야 된다.
	public void SendMessage(String roomID, String myName, String msg) {
		System.out.println("roomID: " + roomID);
		SingleChatMsg obcm = new SingleChatMsg(myName, "200", roomID, msg);
		//obcm.setProfileImg(basic_profile);
		System.out.println("obcm");
		System.out.printf("obcm.getID(): %s, obcm.getCode(): %s, obcm.getTo(): %s, obcm.getData(): %s", 
		obcm.getId(), obcm.getCode(), obcm.getTo(), obcm.getData());
		obcm.setChatRoomTitle(myName);
		//oos.writeObject(obcm);
		view.SendObject(obcm);
	}
		
	class FileSendAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == fileSendBtn) {
				frame = new Frame("파일첨부");
				fd = new FileDialog(frame, "파일 선택", FileDialog.LOAD);
				// frame.setVisible(true);
				// fd.setDirectory(".\\");
				fd.setVisible(true);
				//System.out.println(fd.getDirectory() + fd.getFile());
				File file = new File(fd.getDirectory() + fd.getFile());
				SingleChatMsg obcm = new SingleChatMsg(myName, "800", chatRoomID, fd.getFile());
				obcm.setFile(file);
				obcm.setChatRoomTitle(myName);
				view.SendObject(obcm);
			}
		}
	}

	class ImageSendAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == imgBtn) {
				frame = new Frame("이미지첨부");
				fd = new FileDialog(frame, "이미지 선택", FileDialog.LOAD);
				// frame.setVisible(true);
				// fd.setDirectory(".\\");
				fd.setVisible(true);
				//System.out.println(fd.getDirectory() + fd.getFile());
				SingleChatMsg obcm = new SingleChatMsg(myName, "700", chatRoomID, "IMG");
				ImageIcon img = new ImageIcon(fd.getDirectory() + fd.getFile());
				obcm.setImg(img);
				obcm.setChatRoomTitle(myName);
				view.SendObject(obcm);
			}
		}
	}
	
	class EmojiSendAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == emojiSendBtn) {
				new ChatEmojiList(ChatRoomFrame.this, view, myName, chatRoomID);
			}
		}
	}
	
	public void SendObject(Object ob) { // 서버로 메세지를 보내는 메소드
		view.SendObject(ob);
	}
}
