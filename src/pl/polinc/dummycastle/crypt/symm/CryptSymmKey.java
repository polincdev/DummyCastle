package pl.polinc.dummycastle.crypt.symm;

import pl.polinc.dummycastle.random.RandomClient;

public class CryptSymmKey {

	public static enum KEY_TYPE {
		TYPE_ROT, TYPE_SEED
	}

	private KEY_TYPE type = KEY_TYPE.TYPE_SEED;
	// Key
	private String keyStr;
	private byte[] keyBytes;
	private int keySize = 0;
	private byte seed;

	/**
	 * Creates a key for symmetric encryption based on provided data.
	 * 
	 * @param keyStr key data that will be used to generated the key.
	 * @param type   of the key. KEY_TYPE.TYPE_SEED is the default type.
	 */
	public CryptSymmKey(String keyStr, KEY_TYPE type) {

		this.type = type;
		this.keyStr = keyStr;
		keyBytes = keyStr.getBytes();
		keySize = keyStr.length();

		if (type == KEY_TYPE.TYPE_SEED) {
			for (int i = 0; i < keySize; i++)
				seed = (byte) (seed ^ keyBytes[i]);
		}
	}

	/**
	 * Creates a key for symmetric encryption based on provided data. Type key type
	 * is TYPE_SEED
	 * 
	 * @param keyStr key data that will be used to generated the key.
	 */
	public CryptSymmKey(String keyStr) {
		this(keyStr, KEY_TYPE.TYPE_SEED);
	}

	/**
	 * Creates a key for symmetric encryption based on provided data. Type key type
	 * is TYPE_SEED. The key data is of 32 characters.
	 * 
	 */
	public CryptSymmKey() {
		this(RandomClient.generateRandomString(32, RandomClient.Mode.ALPHANUMERIC, true), KEY_TYPE.TYPE_SEED);
	}

	/**
	 * Retrieves one character of the key.
	 * 
	 * @param pos character to retrieve at a specific position. May be any positive
	 *            number as the key is endless and generated on the fly.
	 * @return a character of the key.
	 */
	public char getKeyAt(int pos) {
		if (type == KEY_TYPE.TYPE_ROT)
			return keyStr.charAt((pos) % keySize);
		else if (type == KEY_TYPE.TYPE_SEED) {
			return (char) (keyStr.charAt((pos) % keySize) ^ (seed + pos));
		} else
			return keyStr.charAt((pos) % keySize);
	}

	/**
	 * Returns the key of the original length.
	 *
	 */
	public String toString() {

		return toString(keySize);
	}

	/**
	 * Returns the key of the specified length.
	 * 
	 * @param len the length of resulting string.
	 * @return
	 */
	public String toString(int len) {
		if (len < 0)
			return "";
		StringBuffer sb = new StringBuffer();
		if (type == KEY_TYPE.TYPE_ROT) {
			for (int i = 0; i < len; i++)
				sb.append((char) (keyStr.charAt((i) % keySize)));
			return sb.toString();
		} else if (type == KEY_TYPE.TYPE_SEED) {
			for (int i = 0; i < len; i++)
				sb.append((char) (keyStr.charAt((i) % keySize) ^ seed ^ i));

			return sb.toString();
		}

		return keyStr;
	}

	/**
	 * @return
	 */
	public int getKeySize() {
		return keySize;
	}
}
