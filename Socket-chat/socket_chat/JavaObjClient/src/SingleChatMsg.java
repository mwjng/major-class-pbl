
// ChatMsg.java ä�� �޽��� ObjectStream ��.
import java.io.File;
import java.io.Serializable;
import java.util.List;

import javax.swing.ImageIcon;

class SingleChatMsg implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
	private String code; // 100:�α���, 400:�α׾ƿ�, 200:ä�ø޽���, 300:Image
	private String data;
	private String to;
	private String profile_msg;
	private int roomID;
	public ImageIcon emoji;
	public ImageIcon img;
	public ImageIcon profile_img;
	public File file;
	private String chatRoomTitle;
	// 단톡방 리스트
	//private List<String> checkedNames;

	public SingleChatMsg(String id, String code, String to, String msg) {
		this.id = id;
		this.code = code;
		this.to = to;
		this.data = msg;
	}

	public String getChatRoomTitle() {
		return chatRoomTitle;
	}

	public void setChatRoomTitle(String chatRoomTitle) {
		this.chatRoomTitle = chatRoomTitle;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

//	public List getCheckedNames() {
//		return checkedNames;
//	}
//	
//	public void setCheckedNames(List<String> checkedNames) {
//		this.checkedNames = checkedNames;
//	}
	
	public int getRoomID() {
		return roomID;
	}

	public void setRoomID(int roomID) {
		this.roomID = roomID;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getData() {
		return data;
	}

	public String getId() {
		return id;
	}
	
	public String getProfileMsg() {
		return profile_msg;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setData(String data) {
		this.data = data;
	}

	public void setEmoji(ImageIcon emoji) {
		this.emoji = emoji;
	}

	public void setImg(ImageIcon img) {
		this.img = img;
	}
	
	public void setProfileImg(ImageIcon profile_img) {
		this.profile_img = profile_img;
	}
	
	public void setProfileMsg(String profile_msg) {
		this.profile_msg = profile_msg;
	}
	
	public void setFile(File file) {
		this.file = file;
	}
}