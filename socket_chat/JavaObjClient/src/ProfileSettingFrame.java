import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Image;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

public class ProfileSettingFrame extends JFrame {

	private JPanel contentPane;
	private JavaObjClientView view;
	private JButton img;
	private JButton confirmBtn;
	private JTextPane textArea;
	private JScrollPane scrollPane;
	private Frame frame;
	private FileDialog fd;
	private String myName;
	private SingleChatMsg obcm;
	private JButton profile;

	public ProfileSettingFrame(JavaObjClientView view, String myName, JButton profile) {
		this.view = view;
		this.myName = myName;
		this.profile = profile;
		
		setBounds(100, 100, 300, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		this.setVisible(true);
		img = new JButton("");
		img.setBackground(SystemColor.window);
		img.setBorder(null);
		img.setBounds(100,10,80,80);
		ImageIcon icon = new ImageIcon("src/icon1.jpg");
		Image image = icon.getImage();
		Image newImg = image.getScaledInstance(75, 75, java.awt.Image.SCALE_SMOOTH);
		icon = new ImageIcon(newImg);
		img.setIcon(icon);
		img.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame = new Frame("이미지첨부");
				fd = new FileDialog(frame, "이미지 선택", FileDialog.LOAD);
				fd.setVisible(true);
				obcm = new SingleChatMsg(myName, "900", "", "proIMG");
				ImageIcon image = new ImageIcon(fd.getDirectory() + fd.getFile());
				Image ori_img = image.getImage();
				int width, height;
				double ratio;
				width = image.getIconWidth();
				height = image.getIconHeight();
				// Image가 너무 크면 최대 가로 또는 세로 200 기준으로 축소시킨다.
				if (width > 70 || height > 70) {
					if (width > height) { // 가로 사진
						ratio = (double) height / width;
						width = 70;
						height = (int) (width * ratio);
					} else { // 세로 사진
						ratio = (double) width / height;
						height = 70;
						width = (int) (height * ratio);
					}
					Image new_img = ori_img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
					ImageIcon new_image = new ImageIcon(new_img);
					obcm.setImg(new_image);
					img.setIcon(new_image);
				}
				else {
					obcm.setImg(image);
					img.setIcon(image);
				}
			}
		});		
		contentPane.add(img);
		textArea = new JTextPane();
		scrollPane = new JScrollPane();
		scrollPane.setBounds(45,120,200,35);
		scrollPane.setViewportView(textArea);
		contentPane.add(scrollPane);
		confirmBtn = new JButton("확인");
		confirmBtn.setBorder(null);
		confirmBtn.setFont(new Font("맑은 고딕", Font.PLAIN, 17));
		confirmBtn.setBackground(new Color(255, 255, 0));
		confirmBtn.setBounds(200, 220, 78, 35);
		contentPane.add(confirmBtn);
		confirmBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (obcm == null) {
					obcm = new SingleChatMsg(myName, "900", "", "proIMG");
					ImageIcon icon = new ImageIcon("src/icon1.jpg");
					Image ori_img = icon.getImage();
					Image new_img = ori_img.getScaledInstance(70, 70, Image.SCALE_SMOOTH);
					ImageIcon new_image = new ImageIcon(new_img);
					obcm.setImg(new_image);
				}
				
				obcm.setProfileMsg(textArea.getText());
				view.SendObject(obcm);
				profile.setIcon(obcm.img);
				dispose();
			}
		});
	}
	
	class SafeClose extends WindowAdapter {
		
		public void windowClosing(WindowEvent e) {
			JFrame frame = (JFrame)e.getWindow();	
			frame.dispose();
		}
	}

}
