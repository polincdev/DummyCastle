package pl.polinc.dummycastle.hash;

public class HashClient {

	static public long defaultHash(String str) {
		long hash = 1;

		for (int i = 0; i < str.length(); i++) {
			hash = ((hash << 5) + hash) * str.charAt(i);
		}

		return hash;
	}

	static public long defaultHash(byte[] str) {
		long hash = 1;

		for (int i = 0; i < str.length; i++) {
			hash = ((hash << 5) + hash) * str[i];
		}

		return hash;
	}

}
