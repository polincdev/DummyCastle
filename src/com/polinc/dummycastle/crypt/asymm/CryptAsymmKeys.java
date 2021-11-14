package com.polinc.dummycastle.crypt.asymm;

import java.math.BigInteger;
import java.util.Random;

public class CryptAsymmKeys {

	public static enum KEY_SIZE_TYPE {
		KEY_SIZE_128, KEY_SIZE_256, KEY_SIZE_512, KEY_SIZE_1024, KEY_SIZE_2048, KEY_SIZE_4096
	}

	int keySize = 512;

	/**
	 * Creates a CryptAsymmKeys object with key type described by a keySizeType
	 * param.
	 * 
	 * @param keySizeType one of KEY_SIZE_TYPE types.
	 */
	public CryptAsymmKeys(KEY_SIZE_TYPE keySizeType) {

		if (keySizeType == KEY_SIZE_TYPE.KEY_SIZE_128)
			keySize = 128;
		else if (keySizeType == KEY_SIZE_TYPE.KEY_SIZE_256)
			keySize = 256;
		else if (keySizeType == KEY_SIZE_TYPE.KEY_SIZE_512)
			keySize = 512;
		else if (keySizeType == KEY_SIZE_TYPE.KEY_SIZE_1024)
			keySize = 1024;
		else if (keySizeType == KEY_SIZE_TYPE.KEY_SIZE_2048)
			keySize = 2048;
		else if (keySizeType == KEY_SIZE_TYPE.KEY_SIZE_4096)
			keySize = 4096;

	}

	/**
	 * Creates a CryptAsymmKeys object with 512 bit key
	 * 
	 */
	public CryptAsymmKeys() {
		this(KEY_SIZE_TYPE.KEY_SIZE_512);
	}

	/**
	 * Generates keys - both public and private.
	 * 
	 * @return CryptAsymmKeysPair object containing public and private.
	 */
	public CryptAsymmKeysPair generateKeys() {
		Random rand = new Random();
		BigInteger p = BigInteger.probablePrime(keySize / 2, rand);
		BigInteger q = BigInteger.probablePrime(keySize / 2, rand);
		// Calculate products
		BigInteger n = p.multiply(q);
		BigInteger phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));

		// Generate public and private exponents
		BigInteger e;
		do
			e = new BigInteger(phi.bitLength(), rand);
		while (e.compareTo(BigInteger.ONE) <= 0 || e.compareTo(phi) >= 0 || !e.gcd(phi).equals(BigInteger.ONE));
		BigInteger d = e.modInverse(phi);

		CryptAsymmPublicKey pub = CryptAsymmPublicKey.createFromNums(e, n);
		CryptAsymmPrivateKey priv = CryptAsymmPrivateKey.createFromNums(d, n);
		return new CryptAsymmKeysPair(pub, priv);
	}

}
