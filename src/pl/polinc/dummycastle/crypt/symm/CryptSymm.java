package pl.polinc.dummycastle.crypt.symm;

public class CryptSymm {

	// Encrypt
	public static byte[] encryptStream(byte[] plainData, CryptSymmKey key) {
		//
		CryptClient cryptClient = new CryptClient(key);
		//
		byte[] encryptedData = cryptClient.encrypt(plainData);
		//
		return encryptedData;
	}
	// Encrypt

	public static byte[] encryptStreamFrom(byte[] plainData, CryptSymmKey key, int keyPos) { //
		CryptClient cryptClient = new CryptClient(key); // byte[]
		byte[] encryptedData = cryptClient.encryptFrom(plainData, keyPos);
		return encryptedData;
	}

	// Decrypt
	public static byte[] decryptStream(byte[] encryptedData, CryptSymmKey key) {
		//
		CryptClient cryptClient = new CryptClient(key);
		//
		byte[] plainData = cryptClient.decrypt(encryptedData);
		//
		return plainData;
	}

	// Decrypt
	public static byte[] decryptStreamFrom(byte[] encryptedData, CryptSymmKey key, int keyPos) {
		//
		CryptClient cryptClient = new CryptClient(key);
		//
		byte[] plainData = cryptClient.decryptFrom(encryptedData, keyPos);
		//
		return plainData;
	}
}