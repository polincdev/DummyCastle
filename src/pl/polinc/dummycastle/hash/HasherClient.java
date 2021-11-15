package pl.polinc.dummycastle.hash;

public class HasherClient {
	// Salt
	String saltStr;
	byte[] saltBytes;
	int saltSize = 0;

	// Key
	String keyStr;
	byte[] keyBytes;
	int keySize = 0;

	static public String hash(String textInput, int outputLen) {
		int iLen = textInput.length();
		byte[] ibuf = textInput.getBytes();
		byte[] obuf = new byte[outputLen];
		byte[] salt = new byte[outputLen];
		byte[] pass = new byte[outputLen];
		//
		byte seed = 0;
		for (int i = 0; i < iLen; i++)
			seed = (byte) (seed ^ ibuf[i]);
		for (int i = 0; i < salt.length; i++)
			salt[i] = (byte) (seed ^ ibuf[i % iLen]);
		long hash = 1;
		for (int i = 0; i < pass.length; i++) {
			hash = ((hash << 5) + hash) + ibuf[i % iLen];
			pass[i] = (byte) (seed ^ ibuf[i % iLen] ^ hash);
		}

		HasherClient.hash(pass, ibuf, obuf, iLen, outputLen, salt);
		//
		return new String(obuf);
	}

	public static int hash(byte[] pass, byte[] ibuf, byte[] obuf, int ilen, int olen, byte[] salt) {

		int plen = pass.length;
		int i, n = 0, p = -1;
		byte seed = 0, rval;
		if (ilen < 0)
			ilen = ibuf.length;

		for (i = 1; i < salt.length; i++)
			seed = (byte) (seed ^ salt[i]);
		p = (seed % plen);
		for (i = 0; i < ilen; i++) {
			p++;
			if (p >= plen)
				p = 0;
			rval = pass[p];
			if (p == (seed % plen))
				seed = (byte) (pass[p] ^ seed);

			rval = (byte) (pass[p] ^ seed);
			obuf[n % olen] = (byte) (ibuf[i] ^ rval);
			n++;
		}
		return ilen;

	}

	String setSalt(String saltStr) {
		//
		if (saltStr == null) {
			long hash = System.nanoTime();
			for (int i = 0; i < keyStr.length(); i++)
				hash = ((hash << 5) + hash) + keyStr.charAt(i);

			saltStr = String.valueOf(hash);
		}
		//
		this.saltStr = saltStr;
		saltBytes = saltStr.getBytes();
		saltSize = saltStr.length();

		return saltStr;

	}
}
