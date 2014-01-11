
public class Message {
	
	private String msg;
	private String hexed;
	private byte[] byted;

	public Message() {
		
	}
	
	public Message(String a) {
		msg = a;
		hexed = stringToHex(a);
		byted = hexToBytes(hexed);
	}
	
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
		hexed = stringToHex(msg);
		byted = hexToBytes(hexed);
	}

	public String getHexed() {
		return hexed;
	}

	public void setHexed(String hexed) {
		this.hexed = hexed;
		msg = hexToString(hexed);
		byted = hexToBytes(hexed);
	}

	public byte[] getByted() {
		return byted;
	}

	public void setByted(byte[] byted) {
		this.byted = byted;
	}
	

	private byte[] hexToBytes(String a) {
		
		byte[] b = new byte[a.length() / 2];
		
		for (int i = 0; i < b.length; i++) {
			b[i] = (byte) Integer.parseInt(a.substring(2 * i, 2 * i + 2), 16);
		}
		
		return b;
	}
	
	private String bytesToHex(byte[] b) {
		
		String res = "";
		
		for (int i = 0; i < b.length; i++) {
			res = res + ((b[i] & 0xff) < 16 ? "0" : "") + Integer.toHexString(b[i] & 0xff);
		}
		
		return res;
	}
	
	public Message encryptWith(Message b) {
		
		expand(b);
		
		byte[] b1 = byted;
		byte[] b2 = b.byted;
		
		int min = (b1.length > b2.length ? b2.length : b1.length);
		
		byte[] c = new byte[min];
		
		for (int i = 0; i < min; i++) {
			c[i] = (byte) (b1[i] ^ b2[i]);
		}
		Message res = new Message();
		res.setHexed(bytesToHex(c));
		return res;
	}
	
	private String hexToString(String a) {
		return new String(hexToBytes(a));
	}
	
	private String stringToHex(String a) {
		return bytesToHex(a.getBytes());
	}
	
	public void expand(Message a) {
		
		String s = "";
		
		String temp = a.getMsg();
		
		for(int i = 0; i <= (msg.length() / temp.length()) + 1; i++) {
			s = s + temp;
		}
		
		a.setMsg(s);
	}
	
}
