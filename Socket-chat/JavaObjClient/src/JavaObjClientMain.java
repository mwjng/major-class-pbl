// JavaObjClient.java
// ObjecStream ����ϴ� ä�� Client

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import java.awt.Color;

public class JavaObjClientMain extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtEmailAddress;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JavaObjClientMain frame = new JavaObjClientMain();
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
	public JavaObjClientMain() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 254, 190);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel emailAddress = new JLabel("이름");
		emailAddress.setHorizontalAlignment(SwingConstants.CENTER);
		emailAddress.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		emailAddress.setBounds(12, 30, 82, 33);
		contentPane.add(emailAddress);
		
		txtEmailAddress = new JTextField();
		txtEmailAddress.setHorizontalAlignment(SwingConstants.CENTER);
		txtEmailAddress.setColumns(10);
		txtEmailAddress.setBounds(101, 30, 116, 33);
		contentPane.add(txtEmailAddress);
        
        ImageIcon icon = new ImageIcon(
            JavaObjClientMain.class.getResource("/kakao_login_medium_narrow.png")
        );
        
        Image img = icon.getImage();
    	Image updateImg = img.getScaledInstance(183, 45, Image.SCALE_SMOOTH);
        ImageIcon updateIcon = new ImageIcon(updateImg);
        
        //imgLabel.setIcon(updateIcon);
        
        //imgLabel.setBounds(210, 30, 165, 150);
       // imgLabel.setHorizontalAlignment(JLabel.CENTER);
		
    	//getContentPane().add(imgLabel);
		
		JButton btnConnect = new JButton("");
		btnConnect.setIcon(updateIcon);
		btnConnect.setBounds(31, 84, 176, 38);
		btnConnect.setBorder(null);
		btnConnect.setContentAreaFilled(false);
		contentPane.add(btnConnect);
		Myaction action = new Myaction();
		btnConnect.addActionListener(action);
		txtEmailAddress.addActionListener(action);
	}
	class Myaction implements ActionListener // ����Ŭ������ �׼� �̺�Ʈ ó�� Ŭ����
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			String id = txtEmailAddress.getText().trim();
			JavaObjClientView view = new JavaObjClientView(id);
			setVisible(false);
		}
	}
}


