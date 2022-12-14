// ChatMsg.java ä�� �޽��� ObjectStream ��.
import java.io.Serializable;
import javax.swing.ImageIcon;

class ChatMsg implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
	private String code; // 100:�α���, 400:�α׾ƿ�, 200:ä�ø޽���, 300:Image
	private String data;
	public ImageIcon img;
	/*
	 * 자바에서는 제대로 처리를 못하게 됩니다. 따라서 각 서버가 쉽게 해당 객체가 같은지 다른지를 확인할 수 있도록 하기 위해서는 serialVersionUID로 관리를 해주어야만 합니다.

즉 클래스 이름이 같더라도 이 ID가 다르면 다른 클래스라고 인식합니다. 게다가, 같은 UID라고 할지라도, 변수의 개수나 타입 등이 다르면 이 경우도 다른 클래스로 인식합니다.
	 */

	public ChatMsg(String id, String code, String msg) {
		this.id = id;
		this.code = code;
		this.data = msg;
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

	public void setId(String id) {
		this.id = id;
	}

	public void setData(String data) {
		this.data = data;
	}

	public void setImg(ImageIcon img) {
		this.img = img;
	}
}