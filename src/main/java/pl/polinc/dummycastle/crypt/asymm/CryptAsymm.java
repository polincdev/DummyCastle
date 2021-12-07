package pl.polinc.dummycastle.crypt.asymm;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

import pl.polinc.dummycastle.coding.Coder;
import pl.polinc.dummycastle.coding.Hex;
import pl.polinc.dummycastle.crypt.symm.CryptSymmKey;
import pl.polinc.dummycastle.random.RandomClient;

public class CryptAsymm {

	// Encrypt
	public static byte[] encryptStream(byte[] plainData, CryptAsymmKey key) {

		int keySize = key.getKeySize() / 8;
		if (keySize > plainData.length)
			keySize = plainData.length;
		// prepare
		String symmKey = RandomClient.generateRandomString(keySize, RandomClient.Mode.ALPHANUMERIC, false);
		BigInteger gemStr = new BigInteger(symmKey.getBytes());
		BigInteger enc = gemStr.modPow(key.getExponent(), key.getProduct());
		byte[] gem = enc.toString().getBytes();
		//
		CryptAsymmClient cryptClient = new CryptAsymmClient(new CryptSymmKey(symmKey));
		//
		byte[] encryptedData = cryptClient.encrypt(plainData);
		int gemLen = gem.length;
		byte[] encryptedDataWithGem = new byte[gemLen + 4 + encryptedData.length];
		byte[] gemLenBytes = intToByteArray(gemLen);
		for (int a = 0; a < 4; a++)
			encryptedDataWithGem[a] = gemLenBytes[a];

		for (int a = 0; a < gemLen; a++)
			encryptedDataWithGem[a + 4] = gem[a];

		for (int a = gemLen + 4, b = 0; a < gemLen + 4 + encryptedData.length; a++, b++)
			encryptedDataWithGem[a] = encryptedData[b];

		//
		return encryptedDataWithGem;
	}

	// Decrypt
	public static byte[] decryptStream(byte[] encryptedDataWithGem, CryptAsymmKey key) {

		byte[] gemLenBytes = new byte[4];
		for (int a = 0; a < 4; a++)
			gemLenBytes[a] = encryptedDataWithGem[a];

		int gemLen = fromByteArray(gemLenBytes);
		byte[] gemEnc = new byte[gemLen];
		for (int a = 0; a < gemLen; a++)
			gemEnc[a] = encryptedDataWithGem[a + 4];
		BigInteger gemStr = new BigInteger(new String(gemEnc));

		// DOnt use (byte[]) constructor nor toBytes method. It is wrong
		BigInteger enc = gemStr.modPow(key.getExponent(), key.getProduct());
		byte[] gem = enc.toByteArray();
		String symmKey = new String(gem);

		byte[] encryptedData = new byte[encryptedDataWithGem.length - (gemLen + 4)];
		for (int a = 0; a < encryptedData.length; a++)
			encryptedData[a] = encryptedDataWithGem[gemLen + 4 + a];
		//
		CryptAsymmClient cryptClient = new CryptAsymmClient(new CryptSymmKey(symmKey));
		byte[] plainData = cryptClient.decrypt(encryptedData);

		//
		return plainData;
	}

	static public final byte[] intToByteArray(int value) {
		return new byte[] { (byte) (value >>> 24), (byte) (value >>> 16), (byte) (value >>> 8), (byte) value };
	}

	static int fromByteArray(byte[] bytes) {
		return ((bytes[0] & 0xFF) << 24) | ((bytes[1] & 0xFF) << 16) | ((bytes[2] & 0xFF) << 8)
				| ((bytes[3] & 0xFF) << 0);
	}
}