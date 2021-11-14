package com.polinc.dummycastle.crypt.asymm;

import java.math.BigInteger;

public class CryptAsymmPublicKey implements CryptAsymmKey {
	BigInteger e;
	BigInteger n;
	int keySize;

	private static String KEY_SEPARATOR = "=@=";

	/**
	 * @param e
	 * @param n
	 */
	private CryptAsymmPublicKey(BigInteger e, BigInteger n) {
		this.e = e;
		this.n = n;
		this.keySize = e.bitLength();

	}

	/**
	 * Creates a public key object from two elements - an exponent and a product.
	 * 
	 * @param e an exponent of the key.
	 * @param n a product of the key.
	 * @return
	 */
	static public CryptAsymmPublicKey createFromNums(BigInteger e, BigInteger n) {

		return new CryptAsymmPublicKey(e, n);
	}

	/**
	 * Creates a private key object from two elements - an exponent and a product.
	 * 
	 * @param publicKeyNumberData an exponent and a product of the key represented
	 *                            as one string.
	 * @return
	 */
	static public CryptAsymmPublicKey createFromString(String publicKeyNumberData) {
		String[] data = publicKeyNumberData.split(KEY_SEPARATOR);
		BigInteger e = new BigInteger(data[0]);
		BigInteger n = new BigInteger(data[1]);
		return new CryptAsymmPublicKey(e, n);
	}

	/**
	 * String representation of the key. Includes both the exponent and the product.
	 * 
	 * @return key as string.
	 */
	public String toString() {
		return e + KEY_SEPARATOR + n;
	};

	/**
	 * Returns key's exponent.
	 * 
	 * @return key's exponent as BigInteger
	 */
	public BigInteger getExponent() {
		return e;
	};

	/**
	 * Returns key's product.
	 * 
	 * @return key's product as BigInteger
	 */
	public BigInteger getProduct() {
		return n;
	}

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