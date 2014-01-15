//The class Message represents every message in every format
/*
Every message is represented either in
- ascii characters
- hexed message
- byte[] array
so you can construct it either with message(ascii) or message(byte[]) or even with any of the 3 seters each for the proper format

the 2 public methods are for encryption
The encryptWith, xors the class's instance with another one
The expand, expands the "key" to have the same length with the current instance
*/


public class Message {
	
	//either pt, hexed pt, or byted pt
	private String msg;
	private String hexed;
	private byte[] byted;

	public Message() {
		
	}

	//you can construct either with pt
	public Message(String a) {
		setMsg(a);
	}
	
	//or with byte[] format
	public Message(byte[] b) {
		byted = b;
		bytesToHex();
		msg = new String(byted);
	}
	
	public String toText() {
		return msg;
	}

	//it could have been a constructor but there would be messed with hexed format
	public void setMsg(String msg) {
		this.msg = msg;
		byted = msg.getBytes();
		bytesToHex();
	}

	public String toHex() {
		return hexed;
	}

	//construct hexed
	public void setHexed(String hexed) {
		this.hexed = hexed;
		hexToBytes();
		msg = new String(byted);
	}

	//convert hex to bytes
	private void hexToBytes() {
		
		byted = new byte[hexed.length() / 2];
		
		for (int i = 0; i < byted.length; i++) {
			byted[i] = (byte) Integer.parseInt(hexed.substring(2 * i, 2 * i + 2), 16);
		}

	}
	
	//convert bytes to hex
	private void bytesToHex() {
		
		hexed = "";
		
		for (int i = 0; i < byted.length; i++) {
			hexed = hexed + ((byted[i] & 0xff) < 16 ? "0" : "") + Integer.toHexString(byted[i] & 0xff);
		}

	}
	
	//create a cipher Message with key b
	public Message encryptWith(Message b) {
		
		expand(b);
		
		byte[] b1 = byted;
		byte[] b2 = b.byted;
		
		int min = (b1.length > b2.length ? b2.length : b1.length);
		
		byte[] c = new byte[min];
		
		for (int i = 0; i < min; i++) {
			c[i] = (byte) (b1[i] ^ b2[i]);
		}
		
		Message res = new Message(c);
		
		return res;
	}

	//if the key is "small" compared to message expand it
	public void expand(Message a) {
		
		String s = "";
		
		String temp = a.toText();
		
		for(int i = 0; i <= (msg.length() / temp.length()) + 1; i++) {
			s = s + temp;
		}
		
		a.setMsg(s);
	}
	
}
