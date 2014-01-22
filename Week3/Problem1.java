import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;

public class Problem1 {
	public static void main(String[] args) throws Exception {
		
		//The file we are going to hash
		String fileName = "C:\\Users\\Costis\\Downloads\\6 - 1 - Introduction (11 min).mp4";
		
		//The variable totalBytes stores the Bytes of the file
		File file = new File(fileName);
		long totalBytes = file.length();
		
		//opening the file
		FileInputStream fis = new FileInputStream(fileName);
		
		//Block size
		int bSize = 1024;
		
		//the last block is totalBytes mod bSize long
		int lastBlock = (int) (totalBytes % bSize);
		
		//We create the 2 dim matrix of (totalBytes/bSize x bSizeKB buffers)
		//and the extra last one < bSizeKB
		byte[][] dataBytes = new byte[(int) (totalBytes / bSize)][bSize];
		byte[] dB = new byte[lastBlock];
		
		//pass buffering blocks
		for (int k = 0; k < (int) (totalBytes / bSize); k++) {
			fis.read(dataBytes[k]);
		}
		//and the last block
		fis.read(dB);
		
		//We are going to use SHA256
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		
		//The SHA-256 is 32 byte long
		byte[] sha = new byte[32];
		
		//we digest the lastBlock and create the first hash
		md.update(dB, 0, lastBlock);
		sha = md.digest();	
		//and until the beginning, we consume each block concatenated with the previous digest
		for (int k = 0; k < (int) (totalBytes / bSize); k++) {
			md.update(dataBytes[(int) (totalBytes / bSize) - k - 1], 0, bSize);
			md.update(sha, 0 ,32);
			sha = md.digest();
		}
		
		//closing the file
		fis.close();

		//We convert each byte of the last hash key to hex string
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < sha.length; i++) {
			sb.append(((sha[i] & 0xff) < 16 ? "0" : "") + Integer.toHexString(sha[i] & 0xff));
		}
		
		//The output
		System.out.println("SHA-256: " + sb.toString());
	}
}
