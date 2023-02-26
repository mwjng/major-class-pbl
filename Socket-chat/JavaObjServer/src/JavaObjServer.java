//JavaObjServer.java ObjectStream 기반 채팅 Server

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

public class JavaObjServer extends JFrame {
	private List<String> names = Arrays.asList("Arsenal", "Aston Villa", "Bournemouth",
	        "Chelsea", "Crystal Palace", "Everton", "Leicester City", "Liverpool",
	        "Manchester United", "Manchester City", "Newcastle United",
	        "Norwich City", "Southampton", "Stoke City", "Sunderland",
	        "Swansea City", "Tottenham Hotspur", "Watford", "West Brom", "West Ham");
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	JTextArea textArea;
	private JTextField txtPortNumber;

	private ServerSocket socket; // 서버소켓
	private Socket client_socket; // accept() 에서 생성된 client 소켓
	private Vector UserVec = new Vector(); // 연결된 사용자를 저장할 벡터
	private static final int BUF_LEN = 128; // Windows 처럼 BUF_LEN 을 정의
	
	private int roomID = 1;
	private List<String> roomUsers;// = new ArrayList();
	private List<String> multiRoomUsers = new ArrayList();
	private String[] multiRoomStringVer;
	private HashMap<String, List<String>> chatRoom = new HashMap();
	
	// 단톡방 관리
	private List<String> checkedUserList = new ArrayList<String>();
	private HashMap<Integer, List<String>> multiRoom = new HashMap();
	private int multiRoomID = 0;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JavaObjServer frame = new JavaObjServer();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public JavaObjServer() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 338, 440);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 10, 300, 298);
		contentPane.add(scrollPane);

		textArea = new JTextArea();
		textArea.setEditable(false);
		scrollPane.setViewportView(textArea);

		JLabel lblNewLabel = new JLabel("Port Number");
		lblNewLabel.setBounds(13, 318, 87, 26);
		contentPane.add(lblNewLabel);

		txtPortNumber = new JTextField();
		txtPortNumber.setHorizontalAlignment(SwingConstants.CENTER);
		txtPortNumber.setText("30000");
		txtPortNumber.setBounds(112, 318, 199, 26);
		contentPane.add(txtPortNumber);
		txtPortNumber.setColumns(10);

		JButton btnServerStart = new JButton("Server Start");
		btnServerStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					socket = new ServerSocket(Integer.parseInt(txtPortNumber.getText()));
				} catch (NumberFormatException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				AppendText("Chat Server Running..");
				btnServerStart.setText("Chat Server Running..");
				btnServerStart.setEnabled(false); // 서버를 더이상 실행시키지 못 하게 막는다
				txtPortNumber.setEnabled(false); // 더이상 포트번호 수정못 하게 막는다
				AcceptServer accept_server = new AcceptServer();
				accept_server.start();
			}
		});
		btnServerStart.setBounds(12, 356, 300, 35);
		contentPane.add(btnServerStart);
	}

	// 새로운 참가자 accept() 하고 user thread를 새로 생성한다.
	class AcceptServer extends Thread {
		@SuppressWarnings("unchecked")
		public void run() {
			while (true) { // 사용자 접속을 계속해서 받기 위해 while문
				try {
					AppendText("Waiting new clients ...");
					client_socket = socket.accept(); // accept가 일어나기 전까지는 무한 대기중
					AppendText("새로운 참가자 from " + client_socket);
					// User 당 하나씩 Thread 생성
					UserService new_user = new UserService(client_socket);
					UserVec.add(new_user); // 새로운 참가자 배열에 추가
					new_user.start(); // 만든 객체의 스레드 실행
					AppendText("현재 참가자 수 " + UserVec.size());
				} catch (IOException e) {
					AppendText("accept() error");
					// System.exit(0);
				}
			}
		}
	}

	public void AppendText(String str) {
		// textArea.append("사용자로부터 들어온 메세지 : " + str+"\n");
		textArea.append(str + "\n");
		textArea.setCaretPosition(textArea.getText().length());
	}

	public void AppendObject(ChatMsg msg) {
		// textArea.append("사용자로부터 들어온 object : " + str+"\n");
		textArea.append("code = " + msg.getCode() + "\n");
		textArea.append("id = " + msg.getId() + "\n");
		textArea.append("data = " + msg.getData() + "\n");
		textArea.setCaretPosition(textArea.getText().length());
	}

	// User 당 생성되는 Thread
	// Read One 에서 대기 -> Write All
	class UserService extends Thread {
		private InputStream is;
		private OutputStream os;
		private DataInputStream dis;
		private DataOutputStream dos;

		private ObjectInputStream ois;
		private ObjectOutputStream oos;

		private Socket client_socket;
		private Vector user_vc;
		public String UserName = "";
		//public String UserStatus;

		public UserService(Socket client_socket) {
			// 매개변수로 넘어온 자료 저장
			this.client_socket = client_socket;
			this.user_vc = UserVec;
			try {
				
				oos = new ObjectOutputStream(client_socket.getOutputStream());
				oos.flush();
				ois = new ObjectInputStream(client_socket.getInputStream());

			} catch (Exception e) {
				AppendText("userService error");
			}
		}

		//public void Login() {
			//AppendText("새로운 참가자 " + UserName + " 입장.");
			//WriteOne("Welcome to Java chat server\n");
			//WriteOne(UserName + "님 환영합니다.\n"); // 연결된 사용자에게 정상접속을 알림
			//String msg = "[" + UserName + "]님이 입장 하였습니다.\n";
			//WriteOthers(msg); // 아직 user_vc에 새로 입장한 user는 포함되지 않았다.
		//}

		public void Logout() {
			String msg = "[" + UserName + "]님이 퇴장 하였습니다.\n";
			UserVec.removeElement(this); // Logout한 현재 객체를 벡터에서 지운다
			WriteAll(msg); // 나를 제외한 다른 User들에게 전송
			AppendText("사용자 " + "[" + UserName + "] 퇴장. 현재 참가자 수 " + UserVec.size());
		}

		// 모든 User들에게 방송. 각각의 UserService Thread의 WriteONe() 을 호출한다.
		public void WriteAll(String str) {
			for (int i = 0; i < user_vc.size(); i++) {
				UserService user = (UserService) user_vc.elementAt(i);
				//if (user.UserStatus == "O")
					user.WriteOne(str);
			}
		}
		// 모든 User들에게 Object를 방송. 채팅 message와 image object를 보낼 수 있다
		public void WriteAllObject(Object ob) {
			for (int i = 0; i < user_vc.size(); i++) {
				UserService user = (UserService) user_vc.elementAt(i);
				//if (user.UserStatus == "O")
					user.WriteOneObject(ob);
			}
		}

		// 나를 제외한 User들에게 방송. 각각의 UserService Thread의 WriteONe() 을 호출한다.
		public void WriteOthers(String str) {
			for (int i = 0; i < user_vc.size(); i++) {
				UserService user = (UserService) user_vc.elementAt(i);
				if (user != this)
					user.WriteOne(str);
			}
		}

		// Windows 처럼 message 제외한 나머지 부분은 NULL 로 만들기 위한 함수
		public byte[] MakePacket(String msg) {
			byte[] packet = new byte[BUF_LEN];
			byte[] bb = null;
			int i;
			for (i = 0; i < BUF_LEN; i++)
				packet[i] = 0;
			try {
				bb = msg.getBytes("euc-kr");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for (i = 0; i < bb.length; i++)
				packet[i] = bb[i];
			return packet;
		}

		// UserService Thread가 담당하는 Client 에게 1:1 전송
		public void WriteOne(String msg) {
			try {
				// dos.writeUTF(msg);
//				byte[] bb;
//				bb = MakePacket(msg);
//				dos.write(bb, 0, bb.length);
				ChatMsg obcm = new ChatMsg("SERVER", "200", msg);
				oos.writeObject(obcm);
			} catch (IOException e) {
				AppendText("dos.writeObject() error");
				try {
//					dos.close();
//					dis.close();
					ois.close();
					oos.close();
					client_socket.close();
					client_socket = null;
					ois = null;
					oos = null;
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				Logout(); // 에러가난 현재 객체를 벡터에서 지운다
			}
		}

		// 귓속말 전송
		public void WritePrivate(String msg) {
			try {
				ChatMsg obcm = new ChatMsg("귓속말", "200", msg);
				oos.writeObject(obcm);
			} catch (IOException e) {
				AppendText("dos.writeObject() error");
				try {
					oos.close();
					client_socket.close();
					client_socket = null;
					ois = null;
					oos = null;
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				Logout(); // 에러가난 현재 객체를 벡터에서 지운다
			}
		}
		public void WriteOneObject(Object ob) {
			try {
			    oos.writeObject(ob);
			} 
			catch (IOException e) {
				AppendText("oos.writeObject(ob) error");		
				try {
					ois.close();
					oos.close();
					client_socket.close();
					client_socket = null;
					ois = null;
					oos = null;				
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				Logout();
			}
		}
		int cnt = 0;
		public void run() {
			while (true) { // 사용자 접속을 계속해서 받기 위해 while문
				try {
					// String msg = dis.readUTF();
//					byte[] b = new byte[BUF_LEN];
//					int ret;
//					ret = dis.read(b);
//					if (ret < 0) {
//						AppendText("dis.read() < 0 error");
//						try {
//							dos.close();
//							dis.close();
//							client_socket.close();
//							Logout();
//							break;
//						} catch (Exception ee) {
//							break;
//						} // catch문 끝
//					}
//					String msg = new String(b, "euc-kr");
//					msg = msg.trim(); // 앞뒤 blank NULL, \n 모두 제거
					Object obcm = null;
					String msg = null;
					ChatMsg cm = null;
					SingleChatMsg scm = null;
					if (socket == null)
						break;
					try {
						obcm = ois.readObject();
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return;
					}
					
					if (obcm == null)
						break;
					roomUsers = new ArrayList();
					
					if (obcm instanceof SingleChatMsg) {
						scm = (SingleChatMsg) obcm;
						//AppendText("SingleChatMsg arrived\n");
						if(scm.getCode().matches("199")) {
							
							for(int i=0;i<UserVec.size();i++) {
								//AppendText("198\n");
								UserService user = (UserService)UserVec.get(i);
								
								//scm.setCode("199");
								scm.setData(roomID+"");
								
								//roomUsers = new ArrayList();
								
								if(user.UserName.equals(scm.getId())) {
									user.WriteOneObject(scm);
									roomUsers.add(scm.getId());
								}
									
								if(user.UserName.equals(scm.getTo())) {
									user.WriteOneObject(scm);
									roomUsers.add(scm.getTo());
									
									//roomUsers.clear();
								}
								
								
								
								//roomUsers.clear();
								//roomID++;
							}
							chatRoom.put(roomID+"", roomUsers);
							//roomUsers.clear();
							roomID++;
						}
						
						if(scm.getCode().matches("200")) {
							
							List<String> chatingUsers = new ArrayList(); 
							chatingUsers.clear();
							chatingUsers = chatRoom.get(scm.getTo());
							
							for(String user: chatingUsers) {
								AppendText(user);
							}
							
							for(int i=0;i<UserVec.size();i++) {
								UserService user = (UserService)UserVec.get(i);
								for (String name: chatingUsers) {
									if(name.equals(user.UserName)) {
										user.WriteOneObject(scm);
									}										
								}
							}
						}
						if (scm.getCode().matches("300")) {
							multiRoomStringVer = scm.getTo().split(" ");
							for (String user : multiRoomStringVer) {
								multiRoomUsers.add(user);
								AppendText("#####user" + user);
							}
							chatRoom.put(roomID+"", multiRoomUsers);
							//multiRoomUsers.addAll(multiRoomStringVer);
							for(int i=0;i<UserVec.size();i++) {
								UserService user = (UserService)UserVec.get(i);
								for (String name: multiRoomUsers) {
									if(name.equals(user.UserName)) {
										scm.setCode("301");
										scm.setData(roomID+"");
										scm.setTo(name);
										//AppendText("#####WriteOneObject" + user.UserName);
										int forTitle = multiRoomUsers.size() - 2;
										String multiRoomTitle = multiRoomUsers.get(0) + ", " + multiRoomUsers.get(1) + "외 " + forTitle + "명";
										//scm.setMultiRoomTitle(multiRoomTitle);	
										scm.setChatRoomTitle(multiRoomTitle);
										user.WriteOneObject(scm);
									}										
								}
							}
							multiRoomUsers = new ArrayList<>();					
							roomID++;
						}
//						if (scm.getCode().matches("300")) {
//							
//							int size = Integer.parseInt(scm.getData()) - 1;
//							if (cnt < size) {
//								multiRoomUsers.add(scm.getTo());
//								AppendText("#####cnt"+cnt);
//								cnt++;
//							}								
//							else {				
//								// 아스날이 id임
//								multiRoomUsers.add(scm.getTo());
//								AppendText("#####else");
//								for (String user: multiRoomUsers) {
//									AppendText("#####multiRoomUsers" + user);
//								}
//								chatRoom.put(roomID+"", multiRoomUsers);
//								
//								scm.setData(roomID+"");
//								for(int i=0;i<UserVec.size();i++) {
//									UserService user = (UserService)UserVec.get(i);
//									for (String name: multiRoomUsers) {
//										if(name.equals(user.UserName)) {
//											scm.setTo(name);
//											AppendText("#####WriteOneObject" + user.UserName);
//											int forTitle = multiRoomUsers.size() - 2;
//											String multiRoomTitle = multiRoomUsers.get(0) + ", " + multiRoomUsers.get(1) + "외 " + forTitle + "명";
//											//scm.setMultiRoomTitle(multiRoomTitle);	
//											scm.setChatRoomTitle(multiRoomTitle);
//											user.WriteOneObject(scm);
//										}										
//									}
//								}
//								multiRoomUsers = new ArrayList<>();
//								cnt = 0;
//								roomID++;
//								//multiRoomUsers.clear();
//							}							
////							for(int i=0;i<UserVec.size();i++) {
////								UserService user = (UserService)UserVec.get(i);
////								
////								//checkedUserList = scm.getCheckedNames();
////								
////								multiRoom.put(multiRoomID++, checkedUserList);
////								
////								for (String name : checkedUserList) {
////									if(user.UserName.equals(name)) {
////										scm.setTo(name);
////										user.WriteOneObject(scm);
////									}										
////								}								
////								
////								// 다음 리스트를 받을 수 있도록 리스트를 비운다.
////								// checkedUserList.clear();								
////							}
//						}
						// 500 
						if (scm.getCode().matches("500")) {
							AppendText("scm.getCode().matches(\"500\")");
							List<String> chatingUsers = new ArrayList(); 
							chatingUsers.clear();
							// 
							chatingUsers = chatRoom.get(scm.getTo());
							
							for(String user: chatingUsers) {
								AppendText("scm.getCode().matches(\"600\")");
							}
							
							for(int i=0;i<UserVec.size();i++) {
								UserService user = (UserService)UserVec.get(i);
								for (String name: chatingUsers) {
									if(name.equals(user.UserName)) {
										user.WriteOneObject(scm);
									}										
								}
							}
						}
						
						// 800: 파일
						if (scm.getCode().matches("800")) {
							AppendText("scm.getCode().matches(\"800\")");
							List<String> chatingUsers = new ArrayList(); 
							chatingUsers.clear();
							// 
							chatingUsers = chatRoom.get(scm.getTo());
							
							for(String user: chatingUsers) {
								AppendText("scm.getCode().matches(\"800\")");
							}
							
							for(int i=0;i<UserVec.size();i++) {
								UserService user = (UserService)UserVec.get(i);
								for (String name: chatingUsers) {
									if(name.equals(user.UserName)) {
										user.WriteOneObject(scm);
									}										
								}
							}
						}
						
						// 700: 이미지
						if (scm.getCode().matches("700")) {
							AppendText("scm.getCode().matches(\"700\")");
							List<String> chatingUsers = new ArrayList(); 
							chatingUsers.clear();
							chatingUsers = chatRoom.get(scm.getTo());
							
							for(String user: chatingUsers) {
								AppendText("scm.getCode().matches(\"700\")");
							}
							
							for(int i=0;i<UserVec.size();i++) {
								UserService user = (UserService)UserVec.get(i);
								for (String name: chatingUsers) {
									if(name.equals(user.UserName)) {
										user.WriteOneObject(scm);
									}										
								}
							}
						}
						// 600: 이모티콘
						if (scm.getCode().matches("600")) {
							AppendText("scm.getCode().matches(\"600\")");
							List<String> chatingUsers = new ArrayList(); 
							chatingUsers.clear();
							chatingUsers = chatRoom.get(scm.getTo());
							
							for(String user: chatingUsers) {
								AppendText("scm.getCode().matches(\"600\")");
							}
							
							for(int i=0;i<UserVec.size();i++) {
								UserService user = (UserService)UserVec.get(i);
								for (String name: chatingUsers) {
									if(name.equals(user.UserName)) {
										user.WriteOneObject(scm);
									}										
								}
							}
						}
						
						//900: 프로필사진, 상태메시지
						if (scm.getCode().matches("900")) {
							AppendText("scm.getCode().matches(\"900\")");
							WriteAllObject(scm);	
						}
//						if (scm.getCode().matches("301")) {
//							// 여기서 checkedNames를 비워줘도 된다?
//							checkedUserList.clear();	
//							for(int i=0;i<UserVec.size();i++) {
//								UserService user = (UserService)UserVec.get(i);
//								if(user.UserName.equals(scm.getTo()))
//									user.WriteOneObject(scm);
//							}
//						}
						
					}
					
					if (obcm instanceof ChatMsg) {
						cm = (ChatMsg) obcm;
						AppendObject(cm);
					} else
						continue;
					if (cm.getCode().matches("100")) {
						UserName = cm.getId();
					} 
					if (cm.getCode().matches("200")) {
						msg = String.format("[%s] %s", cm.getId(), cm.getData());
						AppendText(msg); // server 화면에 출력
						String[] args = msg.split(" "); // 단어들을 분리한다.
						for(int i=0;i<UserVec.size();i++) {
							UserService user = (UserService)UserVec.get(i);
							if(args[1].equals(user.UserName))
								WriteOne(args[2]);
						}
						if (args.length == 1) { // Enter key 만 들어온 경우 Wakeup 처리만 한다.
							//UserStatus = "O";
						} else if (args[1].matches("/exit")) {
							Logout();
							break;
						} else if (args[1].matches("/list")) {
							WriteOne("User list\n");
							WriteOne("Name\tStatus\n");
							WriteOne("-----------------------------\n");
							for (int i = 0; i < user_vc.size(); i++) {
								UserService user = (UserService) user_vc.elementAt(i);
								WriteOne(user.UserName + "\t" + "\n");
							}
							WriteOne("-----------------------------\n");
						} else if (args[1].matches("/sleep")) {
							//UserStatus = "S";
						} else if (args[1].matches("/wakeup")) {
							//UserStatus = "O";
						} else if (args[1].matches("/to")) { // 귓속말
							for (int i = 0; i < user_vc.size(); i++) {
								UserService user = (UserService) user_vc.elementAt(i);
								if (user.UserName.matches(args[2])) {
									String msg2 = "";
									for (int j = 3; j < args.length; j++) {// 실제 message 부분
										msg2 += args[j];
										if (j < args.length - 1)
											msg2 += " ";
									}
									// /to 빼고.. [귓속말] [user1] Hello user2..
									user.WritePrivate(args[0] + " " + msg2 + "\n");
									//user.WriteOne("[귓속말] " + args[0] + " " + msg2 + "\n");
									break;
								}
							}
						} else { // 일반 채팅 메시지
							//UserStatus = "O";
							//WriteAll(msg + "\n"); // Write All
							WriteAllObject(cm);
						}
					} else if (cm.getCode().matches("400")) { // logout message 처리
						Logout();
						break;
					} else if (cm.getCode().matches("300")) {
						WriteAllObject(cm);
					}
				} catch (IOException e) {
					AppendText("ois.readObject() error");
					try {
//						dos.close();
//						dis.close();
						ois.close();
						oos.close();
						client_socket.close();
						Logout(); // 에러가난 현재 객체를 벡터에서 지운다
						break;
					} catch (Exception ee) {
						break;
					} // catch문 끝
				} // 바깥 catch문끝
			} // while
		} // run
	}
}
