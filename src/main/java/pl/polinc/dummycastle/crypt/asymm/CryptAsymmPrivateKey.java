package pl.polinc.dummycastle.crypt.asymm;

import java.math.BigInteger;

public class CryptAsymmPrivateKey implements CryptAsymmKey {
	BigInteger d;
	BigInteger n;
	private static String KEY_SEPARATOR = "=@=";

	int keySize;

	/**
	 * Creates a CryptAsymmPrivateKey object from two elements - an exponent and a
	 * product.
	 * 
	 * @param d exponent part of
	 * @param n
	 */
	private CryptAsymmPrivateKey(BigInteger d, BigInteger n) {
		this.d = d;
		this.n = n;
		this.keySize = d.bitLength();

	}

	/**
	 * Creates a private key object from two elements - an exponent and a product.
	 * 
	 * @param d an exponent of the key.
	 * @param n a product of the key.
	 * @return the key
	 */
	static public CryptAsymmPrivateKey createFromNums(BigInteger d, BigInteger n) {
		return new CryptAsymmPrivateKey(d, n);
	}

	/**
	 * Creates a private key object from two elements - an exponent and a product.
	 * 
	 * @param privateKeyNumberData an exponent and a product of the key represented
	 *                             as one string.
	 * @return the key
	 */
	static public CryptAsymmPrivateKey createFromString(String privateKeyNumberData) {
		String[] data = privateKeyNumberData.split(KEY_SEPARATOR);
		BigInteger d = new BigInteger(data[0]);
		BigInteger n = new BigInteger(data[1]);
		return new CryptAsymmPrivateKey(d, n);
	}

	/**
	 * String representation of the key. Includes both the exponent and the product.
	 * 
	 * @return key as string.
	 */
	public String toString() {
		return d + KEY_SEPARATOR + n;
	};

	/**
	 * Returns key's exponent.
	 * 
	 * @return key's exponent as BigInteger
	 */
	public BigInteger getExponent() {
		return d;
	};

	/**
	 * Returns key's product.
	 * 
	 * @return key's product as BigInteger
	 */
	public BigInteger getProduct() {
		return n;
	};

	/**
	 * Key size of the key.
	 * 
	 * @return key's bit size
	 */
	@Override
	public int getKeySize() {
		return keySize;
	};

}