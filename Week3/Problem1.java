import java.io.FileInputStream;
import java.security.MessageDigest;

public class Problem1 {
	public static void main(String[] args) throws Exception {
		
		//The file we are going to hash
		String fileName = "C:\\filepath\\6 - 1 - Introduction (11 min).mp4";
		
		//We read the file 2 times
		//1st for byte count
		//2nd for hashing blocks
		FileInputStream fis = new FileInputStream(fileName);
		
		//The variable totalBytes stores the Bytes of the file
		long totalBytes = 0;
		
		//nRead is the number of blocks the read command returns
		int nread = 0;
		
		//we set the buffer to 1024 bytes = 1 KB
		byte[] temp = new byte[1024];
		
		//1st pass
		while ((nread = fis.read(temp)) != -1) {
			totalBytes = totalBytes + nread;
		}
		
		//and close the file
		fis.close();
		
		//reopening the file
		fis = new FileInputStream(fileName);
		
		//the last block is totalBytes mod 1024 bytes long
		int lastBlock = (int) (totalBytes % 1024);
		
		//We create the 2 dim matrix of totalBytes/1024 - 1KB buffers		
		byte[][] dataBytes = new byte[(int) (totalBytes / 1024)][1024];
		
		//and the extra last one <=1KB
		byte[] dB = new byte[lastBlock];
		
		//2nd pass buffering blocks
		for (int k = 0; k < (int) (totalBytes / 1024); k++) {
			nread = fis.read(dataBytes[k]);
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
		for (int k = 0; k < (int) (totalBytes / 1024); k++) {
			md.update(dataBytes[(int) (totalBytes / 1024) - k - 1], 0, 1024);
			md.update(sha, 0 ,32);
			sha = md.digest();
		}
		
		//closing the file
		fis.close();

		//We convert each byte of the last hash key to hex string
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < sha.length; i++) {
			sb.append(Integer.toString((sha[i] & 0xff) + 0x100, 16)
					.substring(1));
		}
		
		//The output
		System.out.println("SHA-256: " + sb.toString());
	}
}
