
// JavaObjClientView.java ObjecStram 기반 Client
//실질적인 채팅 창
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;
import javax.swing.JToggleButton;
import javax.swing.JList;
import javax.swing.JRadioButton;
import javax.swing.JComboBox;
import javax.swing.SpringLayout;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import java.awt.CardLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import net.miginfocom.swing.MigLayout;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ScrollPaneConstants;
import java.awt.SystemColor;
import javax.swing.JEditorPane;

public class JavaObjClientView extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private String UserName;
	private static final int BUF_LEN = 128; // Windows 처럼 BUF_LEN 을 정의
	private Socket socket; // 연결소켓
	private InputStream is;
	private OutputStream os;
	private DataInputStream dis;
	private DataOutputStream dos;
	
	// 11-10
	private JTextPane chatListPane = new JTextPane();
	private List<String> chats = new ArrayList<>();
	private HashMap<String, List<String>> chatsVer2 = new HashMap<>();
	private HashMap<String, String> chatsVer3 = new HashMap<>();
	private List<String> chatsVer4 = new ArrayList<>();
	//private List<JButton> katalk = new ArrayList<JButton>();

	//444
	private HashMap<String, JButton> katalk = new HashMap<>();
	
	public ObjectInputStream ois;
	public ObjectOutputStream oos;
	private ButtonGroup group = new ButtonGroup();

	private Frame frame;
	private FileDialog fd;
	
	private Boolean me = false;
	// 친구 목록을 담는 벡터
	private Vector friends = new Vector();
	
	private JLabel titleLabel = new JLabel("");
	
	private JScrollPane scrollPane = new JScrollPane();
	
	//private ChatRoomList chatRoomList = ChatRoomList.getInstance();
	
	// 단체 채팅 추가 버튼
	private JButton addChatButton;
	
	private JButton myProfile;
	
	private String myName;
	private JPanel panel = new JPanel();
	private JPanel panel2;
	
	private JavaObjClientView view = this;
	
	private int chatRoomID = 0;
	
	// 채팅방을 하나만 생성하도록 설정하는 변수
	private int once = 1;
	
	private List<String> forMultiChatBoxTitle = new ArrayList<String>();
	
	private List<ChatRoomFrame> activatedChatRoom = new ArrayList<ChatRoomFrame>();
	
	private HashMap<String, List<String>> unreadedChatMap = new HashMap<>();
	//private List<String> activatedChatBox = new ArrayList<String>();
	
	// 처음에 이거를 String에는 names, ImageIcon에는 
	private HashMap<String, ImageIcon> profileImg = new HashMap<>();
	private HashMap<String, String> profileMsg = new HashMap<>();
	
	private String tempID;
	
	private List<String> multiChatUsers = new ArrayList<String>();
	private List<String> friend = new ArrayList<String>();
	
	private List<String> names = Arrays.asList("Arsenal", "Aston Villa", "Bournemouth",
	        "Chelsea", "Crystal Palace", "Everton", "Leicester City", "Liverpool",
	        "Manchester United", "Manchester City", "Newcastle United",
	        "Norwich City", "Southampton", "Stoke City", "Sunderland",
	        "Swansea City", "Tottenham Hotspur", "Watford", "West Brom", "West Ham");
//insert component
	private JButton[] btnNewButton = new JButton[names.size()];
	private JButton[] profile_img = new JButton[names.size()];
	private JLabel[] profile_msg = new JLabel[names.size()];
	private int i = 0;
	private static int j = 0;

	
	/**
	 * Create the frame.
	 */
	public JavaObjClientView(String email) {		
		this.myName = email;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 415, 630);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.textHighlightText);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setVisible(true);

		//AppendText("0", "User " + username + " connecting " + ip_addr + " " + port_no);
		//UserName = username;
		//lblUserName.setText(username);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(0, 0, 86, 593);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JToggleButton chatToggleBtn = new JToggleButton("");
		ImageIcon chatSelectedImage = new ImageIcon("src/for_ui/chat_button.png");
		chatSelectedImage = imageSetSize(chatSelectedImage, 30, 30);
		chatToggleBtn.setSelectedIcon(chatSelectedImage);
		//chatToggleBtn.setIcon(new ImageIcon("src/for_ui/chat_button.png"));
		ImageIcon chatImage = new ImageIcon("src/for_ui/chat_not_button.png");
		chatImage = imageSetSize(chatImage, 30, 30);
		chatToggleBtn.setIcon(chatImage);
				
		chatToggleBtn.setContentAreaFilled(false);
		chatToggleBtn.setBorder(null);
		chatToggleBtn.setBounds(20, 132, 50, 50);
		chatToggleBtn.addActionListener(new showChatList());
		panel_1.add(chatToggleBtn);
		group.add(chatToggleBtn);
		
		JToggleButton friendsToogleBtn = new JToggleButton("");
		friendsToogleBtn.setContentAreaFilled(false);
		friendsToogleBtn.setBorder(null);
		//friendsToogleBtn.setSelectedIcon(new ImageIcon("src/friendsBtn.JPG"));
		
		ImageIcon friends_not_selected = new ImageIcon("src/for_ui/friends_not_button.png");
		friends_not_selected = imageSetSize(friends_not_selected, 30, 30);
		friendsToogleBtn.setIcon(friends_not_selected);
		
		ImageIcon friends_selected = new ImageIcon("src/for_ui/friends_button.png");
		friends_selected = imageSetSize(friends_selected, 30, 30);
		friendsToogleBtn.setSelectedIcon(friends_selected);
		
		friendsToogleBtn.setBounds(20, 72, 50, 50);
		panel_1.add(friendsToogleBtn);
		friendsToogleBtn.addActionListener(new showFriendsList());
		
		group.add(friendsToogleBtn);
		
		
		addChatButton = new JButton("");
		
		ImageIcon chatAddImage = new ImageIcon("src/for_ui/chat_add_button.png");
		chatAddImage = imageSetSize(chatAddImage, 30, 30);
		addChatButton.setIcon(chatAddImage);
		
		//addChatButton.setIcon(new ImageIcon("src/addChatButtonImg.JPG"));
		addChatButton.setBounds(328, 20, 50, 50);
		addChatButton.setVisible(false);			
		addChatButton.setContentAreaFilled(false);
		addChatButton.setBorder(null);
		addChatButton.addActionListener(new showNewChatRoom());
		contentPane.add(addChatButton);		
		
		titleLabel.setFont(new Font("맑은 고딕", Font.BOLD, 17));
		titleLabel.setBounds(107, 20, 50, 15);
		contentPane.add(titleLabel);
		
		friendsToogleBtn.doClick();
		
		scrollPane.setBounds(98, 61, 291, 518);
		scrollPane.setBorder(null);
		contentPane.add(scrollPane);
		
		try {
			socket = new Socket("127.0.0.1", Integer.parseInt("30000"));

			oos = new ObjectOutputStream(socket.getOutputStream());
			oos.flush();
			ois = new ObjectInputStream(socket.getInputStream());
			
			ChatMsg obcm = new ChatMsg(myName, "100", "Hello");
			SendObject(obcm);
			
			ListenNetwork net = new ListenNetwork();
			net.start();
			
		} catch (NumberFormatException | IOException e) {
			e.printStackTrace();
		}

	}
	
	ImageIcon imageSetSize(ImageIcon icon, int i, int j) { // image Size Setting
		Image ximg = icon.getImage();  //ImageIcon을 Image로 변환.
		Image yimg = ximg.getScaledInstance(i, j, java.awt.Image.SCALE_SMOOTH);
		ImageIcon xyimg = new ImageIcon(yimg); 
		return xyimg;
	}

class ListenNetwork extends Thread {
	public void run() {
		while (true) {
			try {
				System.out.println("####");
				Object obcm = null;
				String msg = null;
				ChatMsg cm;
				SingleChatMsg scm = null;
				try {
					//recv
					obcm = ois.readObject();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
					break;
				}
				if (obcm == null)
					break;
				// 11-2 gold
				if (obcm instanceof SingleChatMsg) {
					scm = (SingleChatMsg) obcm;
					switch(scm.getCode()) {
					case "100":
						friend.add(msg);
						break;
					case "199":
						for(ChatRoomFrame chatRoom: activatedChatRoom) {
							if(chatRoom.getMyName().equals(scm.getId()) && chatRoom.getChatRoomID() == null) {
								chatRoom.setChatRoomID(scm.getData());
								//System.out.println("refresh");
								addChatBox(scm.getTo(), scm.getData(),"");
							}
						}
						// 받는 사람 입장
						if(scm.getTo().equals(myName)) {
							// addChatBox(채팅 보낸 사람 이름, 대화 내용)
							//activatedChatBox.add(scm.getId());
							// getData()에는 room id가 들어있다.
							addChatBox(scm.getId(), scm.getData(),"");
						}
						break;
					case "200":
						// 보내는 사람 입장
						//System.out.println("case 200");
						//System.out.println("scm.getId(): " + scm.getId());
						//System.out.println("$#$#$#$#$myName: "+myName);
						for(ChatRoomFrame room : activatedChatRoom) {
							String roomID = room.getChatRoomID();
							System.out.println("activated room ids: " + roomID);
							if(roomID.equals(scm.getTo())) {
								if(myName.equals(scm.getId()))
									room.AppendTextR(scm.getData());
								else {
									//room.AppendTextX(scm.getChatRoomTitle(), "");
									//room.AppendProfileImage(profileImg.get(scm.getTo())); <- 원래 의도
									// 11-30 수정 원래 여기에 위와 같이 있었음, 의도는 profileImg 해쉬맵에 사용자 id를 주면
									// 해당하는 profile_img를 get 하는 거였는데 원인은 아마 profileImg 해쉬맵에 profile 이미지가 저장이 안되서?
									room.AppendProfileImage(profileImg.get(scm.getId())); // 이렇게 하면 작동은 하는데 항상 기본 프로필 이미지로 전송됩니다.
									room.AppendText(scm.getChatRoomTitle(), scm.getData());		
								}									
							}
						}
						JButton temp = katalk.get(scm.getTo());
						JLabel set = (JLabel)temp.getComponent(1);
						JLabel doubleSet = (JLabel)set.getComponent(1);
						chats.add(scm.getData());
						//chatsVer4.add(scm.getTo() + ":" + scm.getData() + ",");
						//chats.add(scm.getTo() + " " + scm.getData());
						//chatsVer3.put(scm.getTo(), scm.getData());
						doubleSet.setText(scm.getData());
						
						break;
					case "301":
						//multiChatUsers = scm.getCheckedNames();
						System.out.println("###########300");
						System.out.println("###########300-"+scm.getTo());
						System.out.println("###########300-"+myName);
						forMultiChatBoxTitle.add(scm.getTo());
						if (scm.getTo().equals(myName)) {
							System.out.println("###########300+"+scm.getTo());
							// 생성된 roomId를 가져옴
							for(ChatRoomFrame chatRoom: activatedChatRoom) {
								if(chatRoom.getMyName().equals(scm.getId()) && chatRoom.getChatRoomID() == null) {
									chatRoom.setChatRoomID(scm.getData());
									//System.out.println("refresh");
									//addChatBox(scm.getTo(), scm.getData(),"");
								}
							}
							System.out.println("###########300+getData"+scm.getData());
							addMultiChatBox(scm.getChatRoomTitle(), scm.getData());
						}						
						break;
						
					case "302":
						// 다음 리스트를 받을 수 있도록 리스트를 비운다.
						// multiChatUsers.clear();
						break;
						
						
					case "600":
						for(ChatRoomFrame room : activatedChatRoom) {
							String roomID = room.getChatRoomID();
							System.out.println("activated room ids: " + roomID);
							if(roomID.equals(scm.getTo())) {		
								// 내가 보낸게 다시 나한테 올 때
								if(myName.equals(scm.getId())) {
									// room.AppendEmoji(scm.emoji);
									// 
									room.AppendTextREmojiVer(scm.emoji);
								}			
								// 내가 보낸게 아닌 상대방이 보낸 이모티콘
								else {
									room.AppendProfileImage(profileImg.get(scm.getId()));
									room.AppendRecivedEmoji(scm.getChatRoomTitle(), scm.emoji);
									
									//room.AppendEmoji(scm.emoji);
								}
							}
						}
						break;
						
					// 이미지 파일 전송
					case "700":
						for(ChatRoomFrame room : activatedChatRoom) {
							String roomID = room.getChatRoomID();
							System.out.println("activated room ids: " + roomID);
							if(roomID.equals(scm.getTo())) {							
								if(myName.equals(scm.getId())) {
									//room.AppendEmoji(scm.emoji);
									room.AppendTextRX("");
									room.AppendImage(scm.img);
									//room.AppendTextREmojiVer(scm.emoji);
								}									
								else {
									//room.AppendRecivedEmoji(scm.getChatRoomTitle(), scm.emoji);
									room.AppendProfileImage(profileImg.get(scm.getId()));
									room.AppendTextX(scm.getChatRoomTitle(), "");
									room.AppendImage(scm.img);
									//room.AppendEmoji(scm.emoji);
								}
							}
						}
						break;
					
					// 파일 전송
					case "800":
						for(ChatRoomFrame room : activatedChatRoom) {
							String roomID = room.getChatRoomID();
							System.out.println("activated room ids: " + roomID);
							if(roomID.equals(scm.getTo())) {							
								if(myName.equals(scm.getId())) {
									//room.AppendEmoji(scm.emoji);
									room.AppendTextRX("");
									room.AppendFile(scm);
									//room.AppendTextREmojiVer(scm.emoji);
								}									
								else {
									//room.AppendRecivedEmoji(scm.getChatRoomTitle(), scm.emoji);
									room.AppendProfileImage(profileImg.get(scm.getId()));
									room.AppendTextX(scm.getChatRoomTitle(), "");
									room.AppendFile(scm);
									//room.AppendEmoji(scm.emoji);
								}
							}
						}
						break;
						
					// 프로필 사진, 상태메시지
					case "900":
						for(int i = 0; i<names.size(); i++) {
							if(scm.getId().equals(btnNewButton[i].getText())) {
								profile_img[i].setIcon(scm.img);
								profile_msg[i].setText(scm.getProfileMsg());
								profileImg.put(scm.getId(), scm.img);
								profileMsg.put(scm.getId(), scm.getProfileMsg());
								break;
							}
						}
					}
				}
				if (obcm instanceof ChatMsg) {
					cm = (ChatMsg) obcm;
					//msg = String.format("[%s] %s", cm.getId(), cm.getData());
					//System.out.println("##########");
					
				} else
					continue;
				switch (cm.getCode()) {
				case "200": // chat message
					System.out.println();
					System.out.println("cm.getId: " + cm.getId());
					System.out.println("cm.getData: " + cm.getData());
					if(cm.getId().equals(myName)) {
						//chatRoomList.addChatBox(new JButton(cm.getId()), cm.getData());
						System.out.println("activated");
					}
					
					break;
				case "300": // Image 첨부
					break;
				}
			} catch (IOException e) {
				try {
					ois.close();
					oos.close();
					socket.close();

					break;
				} catch (Exception ee) {
					break;
				} // catch문 끝
			} // 바깥 catch문끝

		}
	}
}
public void SendObject(Object ob) { // 서버로 메세지를 보내는 메소드
	try {
		oos.writeObject(ob);
	} catch (IOException e) {
		// textArea.append("메세지 송신 에러!!\n");
		//AppendText("SendObject Error");
	}
}
	class showProfileSetting implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			ProfileSettingFrame proFrame = new ProfileSettingFrame(view, myName, myProfile);
		}		
	}
	
	// newFriendList에서 가져온 class
	class AtFriendListStartChat implements ActionListener {
		private String sendingTo;
		public AtFriendListStartChat(String sendingTo) {
			this.sendingTo = sendingTo;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			// SingleChatMsg(String id, String code, String to, String msg)
			activatedChatRoom.add(new ChatRoomFrame(view, myName, sendingTo, profileImg.get(sendingTo)));
			//addChatBox(String jbuttonName, String roomID, String msg)
			SingleChatMsg scm = new SingleChatMsg(myName, "199", sendingTo, "");
			try {
				oos.writeObject(scm);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}		
	}
	
	// 친구 목록 보여주기
	class showFriendsList implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			titleLabel.setText("친구");
			addChatButton.setVisible(false);
			
			panel2 = new JPanel();
			panel2.setBorder(null);
			scrollPane.setViewportView(panel2);
			panel2.setLayout(new GridLayout(0, 1, 0, 0));
			
			myProfile = new JButton(myName);
			myProfile.setBackground(SystemColor.window);
			myProfile.setHorizontalAlignment(SwingConstants.LEFT);
			ImageIcon icon = new ImageIcon("src/icon1.jpg");
			Image img = icon.getImage();
			Image newImg = img.getScaledInstance(75, 75, java.awt.Image.SCALE_SMOOTH);
			icon = new ImageIcon(newImg);
			myProfile.setIcon(icon);
			myProfile.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
			myProfile.addActionListener(new showProfileSetting());
			myProfile.setBounds(98, 10, 91, 23);
			myProfile.setBorder(null);

			panel2.add(myProfile);
			
			for (String key : profileImg.keySet()) {
				if (myProfile.getText().equals(key)) {
					myProfile.setIcon(profileImg.get(key));
				}
			}
			
			JButton label = new JButton("친구 " + names.size());
			label.setBackground(SystemColor.window);
			label.setHorizontalAlignment(SwingConstants.LEFT);
			label.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
			label.setBorder(null);
			panel2.add(label);
			
			if (j==0) {
				for (String sendingTo: names) {
					profileImg.put(sendingTo, new ImageIcon("src/icon1.jpg"));
				}
				j++;
			}
			
			i = 0;
			//if (i == 0) {
				for (String sendingTo: names) {
					btnNewButton[i] = new JButton(sendingTo);
					profile_img[i] = new JButton("");
					profile_msg[i] = new JLabel();
					profile_msg[i].setHorizontalAlignment(SwingConstants.CENTER);
					profile_img[i].setBorder(null);
					profile_img[i].setIcon(new ImageIcon("src/icon1.jpg"));
					profile_msg[i].setBorder(null);
					btnNewButton[i].setLayout(new BorderLayout());
					btnNewButton[i].setBackground(SystemColor.window);
					profile_img[i].setBackground(SystemColor.window);
					profile_img[i].addActionListener(new showProfile(profile_img[i], profile_msg[i], sendingTo));
					profile_msg[i].setBackground(SystemColor.window);
					btnNewButton[i].add(BorderLayout.WEST, profile_img[i]);
					btnNewButton[i].add(BorderLayout.SOUTH, profile_msg[i]);
					btnNewButton[i].setFont(new Font("맑은 곧딕", Font.PLAIN, 12));
					btnNewButton[i].setBorder(null);
					//btnNewButton.addActionListener(new startChat(chatRoomList, myName, name));
					btnNewButton[i].addActionListener(new AtFriendListStartChat(sendingTo));
					panel2.add(btnNewButton[i]);
					for (String key : profileImg.keySet()) {
						if (btnNewButton[i].getText().equals(key)) {
							profile_img[i].setIcon(profileImg.get(key));
							profile_msg[i].setText(profileMsg.get(key));
							//btnNewButton[i].setText(profileMsg.get(key));
						}
					}
					i++;
				}
			//}
			
		}			
	}
	
	class showProfile implements ActionListener {
		private JButton buttonImg;
		private JLabel labelMsg;
		private String name;
		public showProfile(JButton buttonImg, JLabel labelMsg, String name) {
			this.buttonImg = buttonImg;
			this.labelMsg = labelMsg;
			this.name = name;
		}
		public void actionPerformed(ActionEvent e) {
			new ProfileShowFrame(buttonImg, labelMsg, name);
		}
	}
	
	class showChatList implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			titleLabel.setText("채팅");			
			//chatRoomList = ChatRoomList.getInstance();
			addChatButton.setVisible(true);
			//chatRoomList.init(socket, scrollPane, myName);
			scrollPane.setViewportView(chatListPane);
			chatListPane.setLayout(null);
		}		
	}
	
	class showNewChatRoom implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			new ChatStartFriendList();
		}		
	}
	
	// chatListFrame 결합
	public void addMultiChatBox(String forMultiChatBoxTitle, String roomID) {
		
			int cnt = 0;
		
			//chats = new ArrayList<String>();
			
			JButton jbutton = new JButton();
			jbutton.setLayout(new BorderLayout());

			JLabel roomTitle = new JLabel(forMultiChatBoxTitle);		
			roomTitle.setFont(new Font("맑은 고딕", Font.BOLD, 13));
			
			JLabel roomText = new JLabel("");
			roomText.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
			
			JLabel roomSetting = new JLabel();
			roomSetting.setLayout(new BorderLayout());
			roomSetting.add(BorderLayout.NORTH, roomTitle);
			roomSetting.add(BorderLayout.CENTER, roomText);			
			
			JLabel roomImg = new JLabel();
			
			String result = forMultiChatBoxTitle.split(",")[0];
			// 채팅방 이미지
			//roomImg.setIcon(profileImg.get(result));
			ImageIcon ori_icon = profileImg.get(result);
			Image ori_img = ori_icon.getImage();
			Image small_img = ori_img.getScaledInstance(60, 60, Image.SCALE_SMOOTH);
			ImageIcon new_icon = new ImageIcon(small_img);
			roomImg.setIcon(new_icon);
			
			jbutton.add(BorderLayout.WEST, roomImg);
			jbutton.add(BorderLayout.CENTER, roomSetting);
			jbutton.setBackground(SystemColor.window);
			jbutton.setBounds(10,10,250,100);
			jbutton.setBorder(null);
			// 채팅 버튼 제목에 a, c.. 이렇게 표시되도록 설정
			jbutton.addActionListener(new newRoomFrameAction(forMultiChatBoxTitle, roomID));
			//multi_katalk.add(jbutton);
			chatListPane.replaceSelection("\n");
			chatListPane.insertComponent(jbutton);
			katalk.put(roomID, jbutton);
		//}
	}
	
	public void addChatBox(String jbuttonName, String roomID, String msg) {		
			chats = new ArrayList<String>();
			//chats.add(msg);
			
			newRoomFrameAction testing = new newRoomFrameAction(jbuttonName, roomID);
			
			JButton jbutton = new JButton();
			jbutton.setLayout(new BorderLayout());

			JLabel roomTitle = new JLabel(jbuttonName);		
			roomTitle.setFont(new Font("맑은 고딕", Font.BOLD, 13));
			
			JLabel roomText = new JLabel(msg);
			roomText.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
			
			JLabel roomSetting = new JLabel();
			roomSetting.setLayout(new BorderLayout());
			roomSetting.add(BorderLayout.NORTH, roomTitle);
			roomSetting.add(BorderLayout.CENTER, roomText);			
			
			JLabel roomImg = new JLabel();
			
			// 채팅방 이미지
			ImageIcon ori_icon = profileImg.get(jbuttonName);
			Image ori_img = ori_icon.getImage();
			Image small_img = ori_img.getScaledInstance(60, 60, Image.SCALE_SMOOTH);
			ImageIcon new_icon = new ImageIcon(small_img);
			roomImg.setIcon(new_icon);
			
			jbutton.add(BorderLayout.WEST, roomImg);
			jbutton.add(BorderLayout.CENTER, roomSetting);
			jbutton.setBackground(SystemColor.window);
			jbutton.setBounds(10,10,250,100);
			jbutton.setBorder(null);
			jbutton.addActionListener(new newRoomFrameAction(jbuttonName, roomID));	
			katalk.put(roomID, jbutton);
			chatListPane.replaceSelection("\n");
			chatListPane.insertComponent(jbutton);
	}
	
	// 갠톡 받는 사람이 채팅리스트에 채팅방을 눌렀을 때
	class newRoomFrameAction implements ActionListener {
		//int cnt = 0; // 채팅방이 중복되서 생성되지 않게 해주는 변수 -> 취소(11-24)
		private String sendingTo;
		private String roomID;
		//private List<String> chats;
		
		public newRoomFrameAction(String sendingTo, String roomID) {
			this.sendingTo = sendingTo;
			this.roomID = roomID;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			//  ChatRoomFrame(ObjectOutputStream oos, String myName, String sendingTo)
			//String temp = chatsVer3.get(roomID);
			//if(cnt == 0) {
				//ChatRoomFrame(JavaObjClientView view, String myName, String sendingTo, List<String> chats, String roomID)
				activatedChatRoom.add(new ChatRoomFrame(view, myName, sendingTo, chats, roomID, profileImg.get(sendingTo)));
				//new ChatRoomFrame(myName, this.sendingTo, chatMap.get(this.sendingTo));
				//System.out.println("cnt == " + cnt);
				//cnt++;
			//}
				
		}		
	}
	
	//chatstart 합침
	public class ChatStartFriendList extends JFrame {
		private List<String> names = Arrays.asList("Arsenal", "Aston Villa", "Bournemouth",
		        "Chelsea", "Crystal Palace", "Everton", "Leicester City", "Liverpool",
		        "Manchester United", "Manchester City", "Newcastle United",
		        "Norwich City", "Southampton", "Stoke City", "Sunderland",
		        "Swansea City", "Tottenham Hotspur", "Watford", "West Brom", "West Ham");
		
		// 체크된 이름을 담는 리스트
		private List<String> checkedNames = new ArrayList<String>();
		
		// 라디오 버튼을 담는 리스트 -> 이 중 체크된 라디오 버튼만 뽑는다
		private List<JRadioButton> radios = new ArrayList<JRadioButton>();
		/**
		 * Launch the application.
		 */
		
		/**
		 * Create the frame.
		 */
		public ChatStartFriendList() {
			
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(100, 100, 385, 419);
			setVisible(true);
			
			contentPane = new JPanel();
			contentPane.setBackground(new Color(255, 255, 255));
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

			setContentPane(contentPane);
			contentPane.setLayout(null);
			
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setBounds(12, 10, 347, 302);
			contentPane.add(scrollPane);
			
			JPanel panel = new JPanel();
			panel.setBackground(new Color(255, 255, 255));
			scrollPane.setViewportView(panel);
			panel.setLayout(new GridLayout(0, 1, 0, 0));
			// titleLabel.setFont(new Font("맑은 고딕", Font.BOLD, 17));
			JButton confirmBtn = new JButton("확인");
			confirmBtn.setBorder(null);
			confirmBtn.setFont(new Font("맑은 고딕", Font.PLAIN, 17));
			confirmBtn.setBackground(new Color(255, 255, 0));
			confirmBtn.setBounds(188, 337, 78, 35);
			confirmBtn.addActionListener(new startMultiChat());
			contentPane.add(confirmBtn);
			
			JButton btnNewButton_1 = new JButton("취소");
			btnNewButton_1.setBorder(null);
			btnNewButton_1.setBackground(new Color(240, 240, 240));
			btnNewButton_1.setFont(new Font("맑은 고딕", Font.PLAIN, 17));
			btnNewButton_1.setBounds(278, 337, 81, 32);
			contentPane.add(btnNewButton_1);
			
			for(String name: names) {
				JRadioButton rdbtnNewRadioButton = new JRadioButton();
				rdbtnNewRadioButton.setLayout(new BorderLayout());
				JLabel friendName = new JLabel(name);
				rdbtnNewRadioButton.add(friendName, BorderLayout.WEST);
				rdbtnNewRadioButton.setHorizontalAlignment(SwingConstants.RIGHT);
				radios.add(rdbtnNewRadioButton);
				panel.add(rdbtnNewRadioButton);
			}		
		}
		
		class startMultiChat implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent e) {
				checkedNames.clear();
				
				for(JRadioButton checked: radios) {
					if(checked.isSelected()) {
						System.out.println("###$$$### "+((JLabel)checked.getComponent(0)).getText());
						checkedNames.add(((JLabel)checked.getComponent(0)).getText());
					}
				}				
				
				// 내 이름도 포함해서, 없으면 막상 방 만든 사람이랑은 통신 안됨
				checkedNames.add(myName);
				System.out.println("############startMultiChat"+myName);
				activatedChatRoom.add(new ChatRoomFrame(view, myName, checkedNames, chats));
				String str = "";
				for(String name : checkedNames) {
					str += name + " ";
//					System.out.println("checkedNames test: " + name);
//					SingleChatMsg obcmWithUserInfo = new SingleChatMsg(myName, "300", name, checkedNames.size()+"");
					//obcmWithUserInfo.setChatRoomTitle(myName);
//					try {
//						oos.writeObject(obcmWithUserInfo);
//					} catch (IOException e1) {
//						e1.printStackTrace();
//					}
				}	
				System.out.println("$$$$$$$$$$$$" + str);
				SingleChatMsg obcmWithUserInfo = new SingleChatMsg(myName, "300", str, checkedNames.size()+""); 
				SendObject(obcmWithUserInfo);
				dispose();
			}
		}
	}
}
