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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

public class ProfileShowFrame extends JFrame {

	private JPanel contentPane;
	private JButton img;
	private JLabel label;
	private JLabel label_name;
	private String prfile_msg;
	private JScrollPane scrollPane;

	public ProfileShowFrame(JButton button, JLabel _label, String name) {
		super(name+"님의 프로필");
		setBounds(100, 100, 300, 250);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		this.setVisible(true);
		img = new JButton("");
		img.setBackground(SystemColor.window);
		img.setBorder(null);
		img.setBounds(100,10,80,80);
		img.setIcon(button.getIcon());
		contentPane.add(img);
		label_name = new JLabel();
		label_name.setText(name);
		label_name.setHorizontalAlignment(label.CENTER);
		label_name.setBorder(null);
		label_name.setBounds(88,100,100,35);
		label = new JLabel();
		label.setText(_label.getText());
		label.setHorizontalAlignment(label.CENTER);
		scrollPane = new JScrollPane();
		scrollPane.setBounds(45,160,200,35);
		scrollPane.setViewportView(label);
		contentPane.add(label_name);
		contentPane.add(scrollPane);
	}
	
	class SafeClose extends WindowAdapter {
		
		public void windowClosing(WindowEvent e) {
			JFrame frame = (JFrame)e.getWindow();	
			frame.dispose();
		}
	}

}
