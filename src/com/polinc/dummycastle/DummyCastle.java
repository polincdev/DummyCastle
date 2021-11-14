package com.polinc.dummycastle;

import java.math.BigInteger;

import com.polinc.dummycastle.coding.Coder;
import com.polinc.dummycastle.crypt.asymm.CryptAsymm;
import com.polinc.dummycastle.crypt.asymm.CryptAsymmKey;
import com.polinc.dummycastle.crypt.asymm.CryptAsymmKeys;
import com.polinc.dummycastle.crypt.asymm.CryptAsymmKeys.KEY_SIZE_TYPE;
import com.polinc.dummycastle.crypt.asymm.CryptAsymmKeysPair;
import com.polinc.dummycastle.crypt.asymm.CryptAsymmPrivateKey;
import com.polinc.dummycastle.crypt.asymm.CryptAsymmPublicKey;
import com.polinc.dummycastle.crypt.symm.CryptSymm;
import com.polinc.dummycastle.crypt.symm.CryptSymmKey;
import com.polinc.dummycastle.hash.HashClient;
import com.polinc.dummycastle.hash.HasherClient;
import com.polinc.dummycastle.hash.PermutClient;
import com.polinc.dummycastle.obfuscate.ObfuscateClient;
import com.polinc.dummycastle.random.RandomClient;

public class DummyCastle {

	private String resultStr = "";
	private boolean error = false; 
	private Exception exception = new Exception();
	private CryptSymmKey cryptSymmKey;

	/* ENCRYPT */
	/**
	 * Encrypts provided plain text using symmetric encryption. The result may be
	 * obtained using getResult() method and it is not encoded. Use decryptSymm() do
	 * decrypt the result.
	 * 
	 * @param plainText to encrypt.
	 * @return this object for chaining.
	 */
	public DummyCastle encryptSymm(String plainText) {

		if (plainText == null || plainText.isEmpty() || cryptSymmKey == null) {
			exception = new IllegalArgumentException("Empty or null argument");
			error = true;
			return this;
		}
		resultStr = "";
		try {
			byte[] encryptedBytes = CryptSymm.encryptStream(plainText.getBytes(), cryptSymmKey);
			String secretText = new String(encryptedBytes);
			resultStr = secretText;
		} catch (Exception e) {
			exception = e;
			error = true;
			resultStr = "";
		}
		return this;
	}

	/**
	 * Encrypts internally a text provided by a previous operation using symmetric
	 * encryption. The result may be obtained using getResult() method and it is not
	 * encoded. Use decryptSymm() do decrypt the result.
	 * 
	 * @return this object for chaining.
	 */
	public DummyCastle encryptSymm() {

		if (resultStr == null || resultStr.isEmpty() || cryptSymmKey == null) {
			exception = new IllegalArgumentException("Empty or null argument");
			error = true;
			return this;
		}
		resultStr = "";
		try {
			byte[] encryptedBytes = CryptSymm.encryptStream(resultStr.getBytes(), cryptSymmKey);
			String secretText = new String(encryptedBytes);
			resultStr = secretText;
		} catch (Exception e) {
			exception = e;
			error = true;
			resultStr = "";
		}
		return this;
	}

	/**
	 * Decrypts provided plain text using symmetric encryption. The result may be
	 * obtained using getResult() method and it is not encoded.
	 * 
	 * @param secretText to decrypt.
	 * @return this object for chaining.
	 */
	public DummyCastle decryptSymm(String secretText) {

		if (secretText == null || secretText.isEmpty() || cryptSymmKey == null) {
			exception = new IllegalArgumentException("Empty or null argument");
			error = true;
			return this;
		}
		resultStr = "";
		try {
			byte[] encryptedBytes = CryptSymm.decryptStream(secretText.getBytes(), cryptSymmKey);
			String plainText = new String(encryptedBytes);
			resultStr = plainText;
		} catch (Exception e) {
			exception = e;
			error = true;
			resultStr = "";
		}

		return this;
	}

	/**
	 * Decrypts internally a text provided by previous operations using symmetric
	 * encryption. The result may be obtained using getResult() method and it is not
	 * encoded.
	 * 
	 * @return this object for chaining.
	 */
	public DummyCastle decryptSymm() {

		if (resultStr == null || resultStr.isEmpty() || cryptSymmKey == null) {
			exception = new IllegalArgumentException("Empty or null argument");
			error = true;
			return this;
		}
		resultStr = "";
		try {
			byte[] encryptedBytes = CryptSymm.decryptStream(resultStr.getBytes(), cryptSymmKey);
			String plainText = new String(encryptedBytes);
			resultStr = plainText;
		} catch (Exception e) {
			exception = e;
			error = true;
			resultStr = "";
		}

		return this;
	}

	/**
	 * Encrypts provided text using symmetric encryption and encodes the result
	 * using HEX encoding. The result may be obtained using getResult() method.
	 * 
	 * @param plainText to encrypt and encode.
	 * @return this object for chaining.
	 */
	public DummyCastle encryptSymmWithCoding(String plainText) {

		if (plainText == null || plainText.isEmpty() || cryptSymmKey == null) {
			exception = new IllegalArgumentException("Empty or null argument");
			error = true;
			return this;
		}
		resultStr = "";
		try {
			byte[] encryptedBytes = CryptSymm.encryptStream(plainText.getBytes(), cryptSymmKey);
			resultStr = new String(Coder.encode(encryptedBytes));
		} catch (Exception e) {
			exception = e;
			error = true;
			resultStr = "";
		}

		return this;
	}

	/**
	 * Decodes and decrypts provided text using symmetric encryption. The result may
	 * be obtained using getResult() method.
	 * 
	 * @param secretText to decode and decrypt.
	 * @return this object for chaining.
	 */
	public DummyCastle decryptSymmWithCoding(String secretText) {

		if (secretText == null || secretText.isEmpty() || cryptSymmKey == null) {
			exception = new IllegalArgumentException("Empty or null argument");
			error = true;
			return this;
		}
		resultStr = "";
		try {
			byte[] decodedBytes = Coder.decode(secretText.getBytes());
			byte[] decryptedBytes = CryptSymm.decryptStream(decodedBytes, cryptSymmKey);
			resultStr = new String(decryptedBytes);
		} catch (Exception e) {
			exception = e;
			error = true;
			resultStr = "";
		}

		return this;
	}

	/**
	 * Encrypts provided plain text using symmetric encryption and encodes the
	 * result using HEX encoding. The result may be obtained using getResult()
	 * method.
	 * 
	 * @param plainText to encrypt and encode.
	 * @param keyPos    starting position of the key used to encrypt previous chunks
	 *                  of data.
	 * @return this object for chaining.
	 */
	public DummyCastle encryptSymmFromWithCoding(String plainText, int keyPos) {

		if (plainText == null || plainText.isEmpty() || cryptSymmKey == null || keyPos < 0) {
			exception = new IllegalArgumentException("Empty or null or <0 argument");
			error = true;
			return this;
		}
		resultStr = "";
		try {
			byte[] encryptedBytes = CryptSymm.encryptStreamFrom(plainText.getBytes(), cryptSymmKey, keyPos);
			resultStr = new String(Coder.encode(encryptedBytes));
		} catch (Exception e) {
			exception = e;
			error = true;
			resultStr = "";
		}

		return this;
	}

	/**
	 * Decodes and decrypts provided text using symmetric encryption. The result may
	 * be obtained using getResult() method.
	 * 
	 * @param secretText to decode and decrypt.
	 * @param keyPos     starting position of the key used to decrypt previous
	 *                   chunks of data.
	 * @return this object for chaining.
	 */
	public DummyCastle decryptSymmFromWithCoding(String secretText, int keyPos) {

		if (secretText == null || secretText.isEmpty() || cryptSymmKey == null || keyPos < 0) {
			exception = new IllegalArgumentException("Empty or null or <0 argument");
			error = true;
			return this;
		}
		resultStr = "";
		try {
			byte[] decodedBytes = Coder.decode(secretText.getBytes());
			byte[] decryptedBytes = CryptSymm.decryptStreamFrom(decodedBytes, cryptSymmKey, keyPos);
			resultStr = new String(decryptedBytes);
		} catch (Exception e) {
			exception = e;
			error = true;
			resultStr = "";
		}

		return this;
	}

	/* SYM KEY */
	/**
	 * Retrieves a key that was previously generated using genSymmKey() method for
	 * the purpose of symmetric encryption. If the genSymmKey() has not been called
	 * before this method, it generates a default key.
	 * 
	 * @return CryptSymmKey object containing symmetric key data
	 */
	public CryptSymmKey getSymmKey() {

		try {
			if (cryptSymmKey != null)
				return cryptSymmKey;
			else
				return new CryptSymmKey();

		} catch (Exception e) {
			exception = e;
			error = true;
			cryptSymmKey = null;
		}

		return new CryptSymmKey();
	}

	/**
	 * Generates a default key for the purpose of symmetric encryption. The key may
	 * be obtained using getSymmKey() method.
	 * 
	 * @return this object for chaining.
	 */
	public DummyCastle genSymmKey() {
		try {
			cryptSymmKey = new CryptSymmKey();
		} catch (Exception e) {
			exception = e;
			error = true;
			cryptSymmKey = null;
		}
		return this;
	}

	/**
	 * Generates a key based on provided seed data for the purpose of symmetric
	 * encryption.
	 * 
	 * @param keySeed any kind of string data that will be used to generated the
	 *                key. The same seed would provide the same key.
	 * @return this object for chaining.
	 */
	public DummyCastle genSymmKey(String keySeed) {

		if (keySeed == null || keySeed.isEmpty()) {
			exception = new IllegalArgumentException("Empty or null argument");
			error = true;
			return this;
		}

		try {
			cryptSymmKey = new CryptSymmKey(keySeed);
		} catch (Exception e) {
			exception = e;
			error = true;
			cryptSymmKey = null;
		}

		return this;
	}

	/* ENCRYPT ASYMM */
	/**
	 * Encrypts provided text using asymmetric encryption. The result may be
	 * obtained using getResult() method and it is not encoded. Use decryptSymm() to
	 * decrypt the result. Use decryptAsymm() to decrypt.
	 * 
	 * @param plainText to encrypt.
	 * @param key       contains data used to encrypt plain text. If it is an
	 *                  instance of a CryptAsymmPublicKey class, a
	 *                  CryptAsymmPrivateKey object should be used to decrypt the
	 *                  text.
	 * @return this object for chaining.
	 */
	public DummyCastle encryptAsymm(String plainText, CryptAsymmKey key) {

		if (plainText == null || plainText.isEmpty() || key == null) {
			exception = new IllegalArgumentException("Empty or null argument");
			error = true;
			return this;
		}
		resultStr = "";
		try {
			byte[] encryptedBytes = CryptAsymm.encryptStream(plainText.getBytes(), key);
			String secretText = new String(encryptedBytes);
			resultStr = secretText;
		} catch (Exception e) {
			exception = e;
			error = true;
			resultStr = "";
		}

		return this;
	}

	/**
	 * Decrypts provided text using asymmetric encryption. The result may be
	 * obtained using getResult() method and it is not encoded.
	 * 
	 * @param secretText text to decrypt.
	 * @param key        contains data used to crypt plain text. It should be an
	 *                   instance of a CryptAsymmPublicKey class if
	 *                   CryptAsymmPrivateKey was used to encrypt the text.
	 * @return this object for chaining.
	 */
	public DummyCastle decryptAsymm(String secretText, CryptAsymmKey key) {

		if (secretText == null || secretText.isEmpty() || key == null) {
			exception = new IllegalArgumentException("Empty or null argument");
			error = true;
			return this;
		}
		resultStr = "";
		try {
			byte[] encryptedBytes = CryptAsymm.decryptStream(secretText.getBytes(), key);
			String plainText = new String(encryptedBytes);
			resultStr = plainText;
		} catch (Exception e) {
			exception = e;
			error = true;
			resultStr = "";
		}

		return this;
	}

	/**
	 * Encrypts provided plain text using asymmetric encryption and encodes the
	 * result using HEX encoding. The result may be obtained using getResult()
	 * method. Use decryptAsymmWithCoding() to decrypt.
	 * 
	 * @param plainText to encrypt and encode.
	 * @param key       contains data used to encrypt plain text. If it is an
	 *                  instance of a CryptAsymmPublicKey class, a
	 *                  CryptAsymmPrivateKey object should be used to decrypt the
	 *                  text.
	 * @return this object for chaining.
	 */
	public DummyCastle encryptAsymmWithCoding(String plainText, CryptAsymmKey key) {

		if (plainText == null || plainText.isEmpty() || key == null) {
			exception = new IllegalArgumentException("Empty or null argument");
			error = true;
			return this;
		}
		resultStr = "";
		try {
			byte[] encryptedBytes = CryptAsymm.encryptStream(plainText.getBytes(), key);
			String secretText = new String(Coder.encode(encryptedBytes));
			resultStr = secretText;
		} catch (Exception e) {
			exception = e;
			error = true;
			resultStr = "";
		}

		return this;
	}

	/**
	 * Decrypts provided text using asymmetric encryption. The result may be
	 * obtained using getResult() method and it is not encoded.
	 * 
	 * @param secretText text to decrypt.
	 * @param key        contains data used to crypt plain text. It should be an
	 *                   instance of a CryptAsymmPublicKey class if
	 *                   CryptAsymmPrivateKey was used to encrypt the text.
	 * @return this object for chaining.
	 */
	public DummyCastle decryptAsymmWithCoding(String secretText, CryptAsymmKey key) {

		if (secretText == null || secretText.isEmpty() || key == null) {
			exception = new IllegalArgumentException("Empty or null argument");
			error = true;
			return this;
		}
		resultStr = "";
		try {
			byte[] decodedBytes = Coder.decode(secretText.getBytes());
			byte[] encryptedBytes = CryptAsymm.decryptStream(decodedBytes, key);
			resultStr = new String(encryptedBytes);
		} catch (Exception e) {
			exception = e;
			error = true;
			resultStr = "";
		}
		return this;
	}

	/* ASYM KEY */
	/**
	 * Generates a pair of keys for the purpose of asymmetric encryption.
	 * 
	 * @return CryptAsymmKeysPair object containing asymmetric keys data, both
	 *         public and private.
	 */
	public CryptAsymmKeysPair genAsymmKeys() {

		try {

			CryptAsymmKeys cryptAsymmKeys = new CryptAsymmKeys();
			return cryptAsymmKeys.generateKeys();

		} catch (Exception e) {
			exception = e;
			error = true;
		}

		CryptAsymmKeys cryptAsymmKeys = new CryptAsymmKeys();
		return cryptAsymmKeys.generateKeys();

	}

	/**
	 * Generates a pair of keys for the purpose of asymmetric encryption of the
	 * specified size.
	 * 
	 * @param keySizeType size of the keys. Must be one of
	 *                    CryptAsymmKeys.KEY_SIZE_TYPE constants.
	 * @return CryptAsymmKeysPair object containing asymmetric keys data, both
	 *         public and private.
	 */
	public CryptAsymmKeysPair genAsymmKeys(KEY_SIZE_TYPE keySizeType) {

		try {
			return new CryptAsymmKeys().generateKeys();
		} catch (Exception e) {
			exception = e;
			error = true;
		}

		CryptAsymmKeys cryptAsymmKeys = new CryptAsymmKeys();
		return cryptAsymmKeys.generateKeys();

	}

	/**
	 * Generates a public key for the purpose of asymmetric encryption.
	 * 
	 * @param keyData specific data containing a private key.
	 * 
	 * @return CryptAsymmKeysPair object containing asymmetric keys data, both
	 *         public and private.
	 */
	public CryptAsymmPublicKey genAsymmPublicKey(String keyData) {

		try {
			return CryptAsymmPublicKey.createFromString(keyData);
		} catch (Exception e) {
			e.printStackTrace();
			exception = e;
			error = true;
		}

		return CryptAsymmPublicKey.createFromNums(BigInteger.ZERO, BigInteger.ZERO);
	}

	/**
	 * Generates a private key for the purpose of asymmetric encryption.
	 * 
	 * @param keyData specific data containing a private key.
	 * @return CryptAsymmKeysPair object containing asymmetric keys data, both
	 *         public and private.
	 */
	public CryptAsymmPrivateKey genAsymmPrivateKey(String keyData) {

		try {
			return CryptAsymmPrivateKey.createFromString(keyData);
		} catch (Exception e) {
			exception = e;
			error = true;
		}

		return CryptAsymmPrivateKey.createFromNums(BigInteger.ZERO, BigInteger.ZERO);
	}

	/**
	 * Encodes a text using HEX encoding. The result may be obtained using
	 * getResult() method.
	 * 
	 * @param textToDecode text to be encoded. Must be non-null and not empty.
	 * @return this object for chaining.
	 */
	public DummyCastle encode(String textToDecode) {

		if (textToDecode == null || textToDecode.isEmpty()) {
			exception = new IllegalArgumentException("Empty or null argument");
			error = true;
			return this;
		}
		resultStr = "";
		try {
			resultStr = new String(Coder.encode(textToDecode));
		} catch (Exception e) {
			exception = e;
			error = true;
			resultStr = "";
		}

		return this;
	}

	/**
	 * Decodes a text using HEX encoding. The result may be obtained using
	 * getResult().
	 * 
	 * @param textToDecode text to be decoded. Must be non-null nor empty.
	 * @return this object for chaining.
	 */
	public DummyCastle decode(String encodedText) {

		if (encodedText == null || encodedText.isEmpty()) {
			exception = new IllegalArgumentException("Empty or null argument");
			error = true;
			return this;
		}
		resultStr = "";
		try {
			resultStr = new String(Coder.decode(encodedText));
		} catch (Exception e) {
			exception = e;
			error = true;
			resultStr = "";
		}

		return this;
	}

	/* ENCODE */
	/**
	 * Encodes a text internally using HEX encoding. The result may be obtained
	 * using getResult(). Use decode() method to decode.
	 * 
	 * @return this object for chaining.
	 */
	public DummyCastle encode() {

		if (resultStr.isEmpty()) {
			exception = new IllegalArgumentException("Empty or null argument");
			error = true;
			return this;
		}

		try {
			resultStr = new String(Coder.encode(resultStr));
		} catch (Exception e) {
			exception = e;
			error = true;
			resultStr = "";
		}

		return this;
	}

	/**
	 * Decodes a text internally using HEX encoding. The result may be obtained
	 * using getResult().
	 * 
	 * @return this object for chaining.
	 */
	public DummyCastle decode() {
		if (resultStr.isEmpty()) {
			exception = new IllegalArgumentException("Empty or null argument");
			error = true;
			return this;
		}
		try {
			resultStr = new String(Coder.decode(resultStr));
		} catch (Exception e) {
			exception = e;
			error = true;
			resultStr = "";
		}

		return this;
	}

	/* HASH */
	/**
	 * Hashes(reduces) a provided text into a long number represented as a string.
	 * 
	 * @param textToHash to hash. Must not be null nor empty.
	 * @return this object for chaining.
	 */
	public DummyCastle hashToNum(String textToHash) {

		if (textToHash == null || textToHash.isEmpty()) {
			exception = new IllegalArgumentException("Empty or null argument");
			error = true;
			return this;
		}
		resultStr = "";
		try {
			resultStr = String.valueOf(HashClient.defaultHash(textToHash));
		} catch (Exception e) {
			exception = e;
			error = true;
			resultStr = "";
		}

		return this;
	}

	/**
	 * Hashes(reduces) a text internally into a long number represented as a string.
	 * The result may be obtained using getResult() method.
	 * 
	 * @param textToHash to hash. Must not be null nor empty.
	 * @return this object for chaining.
	 */
	public DummyCastle hashToNum() {

		if (resultStr.isEmpty()) {
			exception = new IllegalArgumentException("Empty or null argument");
			error = true;
			return this;
		}

		try {
			resultStr = String.valueOf(HashClient.defaultHash(resultStr));
		} catch (Exception e) {
			exception = e;
			error = true;
			resultStr = "";
		}

		return this;
	}

	/**
	 * Hashes(reduces) a provided text into a 8 characters string. The result may be
	 * obtained using getResult() method.
	 * 
	 * @param textToHash to hash. Must not be null nor empty.
	 * @return this object for chaining.
	 */
	public DummyCastle hashToStr(String plainText) {

		if (plainText == null || plainText.isEmpty()) {
			exception = new IllegalArgumentException("Empty or null argument");
			error = true;
			return this;
		}

		try {
			resultStr = HasherClient.hash(plainText, 8);
		} catch (Exception e) {
			exception = e;
			error = true;
			resultStr = "";
		}

		return this;
	}

	/**
	 * Hashes(reduces) a text internally into a string. The result may be obtained
	 * using getResult() method.
	 * 
	 * @param textToHash to hash. Must not be null nor empty.
	 * @return this object for chaining.
	 */
	public DummyCastle hashToStr() {

		if (resultStr.isEmpty()) {
			exception = new IllegalArgumentException("Empty or null argument");
			error = true;
			return this;
		}

		try {
			resultStr = HasherClient.hash(resultStr, 8);
		} catch (Exception e) {
			exception = e;
			error = true;
			resultStr = "";
		}

		return this;
	}

	/**
	 * Hashes(reduces) a provided text into a 8 characters string and applies an
	 * encoding. The result may be obtained using getResult() method.
	 * 
	 * @param textToHash to hash. Must not be null nor empty.
	 * @return this object for chaining.
	 */
	public DummyCastle hashToStrWithCoding(String textToHash) {

		if (textToHash == null || textToHash.isEmpty()) {
			exception = new IllegalArgumentException("Empty or null argument");
			error = true;
			return this;
		}

		try {
			resultStr = new String(Coder.encode(HasherClient.hash(textToHash, 8)));
		} catch (Exception e) {
			exception = e;
			error = true;
			resultStr = "";
		}

		return this;
	}

	/**
	 * Hashes(reduces) a text internally into a 8 characters string and applies an
	 * encoding. The result may be obtained using getResult() method.
	 * 
	 * @param textToHash to hash. Must not be null nor empty.
	 * @return this object for chaining.
	 */
	public DummyCastle hashToStrWithCoding() {

		if (resultStr.isEmpty()) {
			exception = new IllegalArgumentException("Empty or null argument");
			error = true;
			return this;
		}

		try {
			resultStr = new String(Coder.encode(HasherClient.hash(resultStr, 8)));
		} catch (Exception e) {
			exception = e;
			error = true;
			resultStr = "";
		}

		return this;
	}

	/**
	 * Hashes(reduces) a provided text into a string of desired length and applies
	 * an encoding. The result may be obtained using getResult() method.
	 * 
	 * @param textToHash     text to hash. Must not be null nor empty.
	 * @param hashLenInChars the length in characters of the resulting hash value.
	 * @return this object for chaining.
	 */
	public DummyCastle hashToStrWithCoding(String plainText, int hashLenInChars) {

		if (plainText == null || plainText.isEmpty() || hashLenInChars < 0) {
			exception = new IllegalArgumentException("Empty or null or <0 argument");
			error = true;
			return this;
		}

		try {
			resultStr = new String(Coder.encode(HasherClient.hash(plainText, hashLenInChars)));
		} catch (Exception e) {
			exception = e;
			error = true;
			resultStr = "";
		}

		return this;
	}

	/**
	 * Hashes(reduces) a text internally into a string of desired length and applies
	 * an encoding. The result may be obtained using getResult() method.
	 * 
	 * @param textToHash     to hash. Must not be null nor empty.
	 * @param hashLenInChars the length in characters of the resulting hash value.
	 * @return this object for chaining.
	 */
	public DummyCastle hashToStrWithCoding(int hashLenInChars) {

		if (resultStr.isEmpty() || hashLenInChars < 0) {
			exception = new IllegalArgumentException("Empty or null or <0 argument");
			error = true;
			return this;
		}

		try {
			resultStr = new String(Coder.encode(HasherClient.hash(resultStr, hashLenInChars)));
		} catch (Exception e) {
			exception = e;
			error = true;
			resultStr = "";
		}
		return this;
	}

	/* OBFUSCATE */
	/**
	 * Obfuscates(hide) a provided text. The result may be obtained using
	 * getResult() method. Use unobfuscate() method to reveal the text.
	 * 
	 * @param textToObfuscate to obfuscate. Must not be null nor empty.
	 * @return this object for chaining.
	 */
	public DummyCastle obfuscate(String textToObfuscate) {

		if (textToObfuscate == null || textToObfuscate.isEmpty()) {
			exception = new IllegalArgumentException("Empty or null argument");
			error = true;
			return this;
		}

		try {
			resultStr = new String(ObfuscateClient.obfuscate(textToObfuscate.getBytes()));
		} catch (Exception e) {
			exception = e;
			error = true;
			resultStr = "";
		}

		return this;
	}

	/**
	 * Obfuscates(hides) a text internally. The result may be obtained using
	 * getResult() method.
	 * 
	 * @return this object for chaining.
	 */
	public DummyCastle obfuscate() {

		if (resultStr == null || resultStr.isEmpty()) {
			exception = new IllegalArgumentException("Empty or null argument");
			error = true;
			return this;
		}

		try {
			resultStr = new String(ObfuscateClient.obfuscate(resultStr.getBytes()));
		} catch (Exception e) {
			exception = e;
			error = true;
			resultStr = "";
		}

		return this;
	}

	/**
	 * Unobfuscates(reveals) a provided text. The result may be obtained using
	 * getResult() method.
	 * 
	 * @param obfuscatedText to obfuscate. Must not be null nor empty.
	 * @return this object for chaining.
	 */
	public DummyCastle unobfuscate(String obfuscatedText) {

		if (obfuscatedText == null || obfuscatedText.isEmpty()) {
			exception = new IllegalArgumentException("Empty or null argument");
			error = true;
			return this;
		}

		try {
			resultStr = new String(ObfuscateClient.unobfuscate(obfuscatedText.getBytes()));
		} catch (Exception e) {
			exception = e;
			error = true;
			resultStr = "";
		}

		return this;
	}

	/**
	 * Unobfuscates(reveals) a text provided by a previous operation. The result may
	 * be obtained using getResult() method.
	 * 
	 * @param obfuscatedText to obfuscate. Must not be null nor empty.
	 * @return this object for chaining.
	 */
	public DummyCastle unobfuscate() {

		if (resultStr == null || resultStr.isEmpty()) {
			exception = new IllegalArgumentException("Empty or null argument");
			error = true;
			return this;
		}

		try {
			resultStr = new String(ObfuscateClient.unobfuscate(resultStr.getBytes()));
		} catch (Exception e) {
			exception = e;
			error = true;
			resultStr = "";
		}

		return this;
	}

	/**
	 * Obfuscates(hides) a provided text and encoded the result. The result may be
	 * obtained using getResult() method.
	 * 
	 * @param textToObfuscate to obfuscate. Must not be null nor empty.
	 * @return this object for chaining.
	 */
	public DummyCastle obfuscateWithCoding(String plainText) {

		if (plainText == null || plainText.isEmpty()) {
			exception = new IllegalArgumentException("Empty or null argument");
			error = true;
			return this;
		}

		try {
			resultStr = new String(Coder.encode(ObfuscateClient.obfuscate(plainText.getBytes())));
		} catch (Exception e) {
			exception = e;
			error = true;
			resultStr = "";
		}

		return this;
	}

	/**
	 * Obfuscates(hides) a text provided internally by a previous operation and
	 * encodes the result. The result may be obtained using getResult() method.
	 * 
	 * @return this object for chaining.
	 */
	public DummyCastle obfuscateWithCoding() {

		if (resultStr == null || resultStr.isEmpty()) {
			exception = new IllegalArgumentException("Empty or null argument");
			error = true;
			return this;
		}

		try {
			resultStr = new String(Coder.encode(ObfuscateClient.obfuscate(resultStr.getBytes())));
		} catch (Exception e) {
			exception = e;
			error = true;
			resultStr = "";
		}

		return this;
	}

	/**
	 * Decodes and unobfuscates a provided text. The result may be obtained using
	 * getResult() method.
	 * 
	 * @param obfuscatedText to decode and unobfuscate. Must not be null nor empty.
	 * @return this object for chaining.
	 */
	public DummyCastle unobfuscateWithCoding(String obfuscatedText) {

		if (obfuscatedText == null || obfuscatedText.isEmpty()) {
			exception = new IllegalArgumentException("Empty or null argument");
			error = true;
			return this;
		}

		try {
			resultStr = new String(ObfuscateClient.unobfuscate(Coder.decode(obfuscatedText.getBytes())));
		} catch (Exception e) {
			exception = e;
			error = true;
			resultStr = "";
		}

		return this;
	}

	/**
	 * Decodes and unobfuscates a text provided internally by previous operation.
	 * The result may be obtained using getResult() method.
	 * 
	 * @return this object for chaining.
	 */
	public DummyCastle unobfuscateWithCoding() {

		if (resultStr == null || resultStr.isEmpty()) {
			exception = new IllegalArgumentException("Empty or null argument");
			error = true;
			return this;
		}

		try {
			resultStr = new String(ObfuscateClient.unobfuscate(Coder.decode(resultStr.getBytes())));
		} catch (Exception e) {
			exception = e;
			error = true;
			resultStr = "";
		}

		return this;
	}

	/* RANDOM */
	/**
	 * Generates a random numeric value as a string. No negative nor leading zero.
	 * The result may be obtained using getResult() method.
	 * 
	 * @param lengthInDigits the size of the result.
	 * @return this object for chaining.
	 */
	public DummyCastle randomNum(int lengthInDigits) {

		if (lengthInDigits < 0) {
			exception = new IllegalArgumentException("<0 argument");
			error = true;
			return this;
		}

		try {
			resultStr = RandomClient.generateRandomString(lengthInDigits, RandomClient.Mode.NUMERIC, true);
		} catch (Exception e) {
			exception = e;
			error = true;
			resultStr = "";
		}

		return this;
	}

	/**
	 * Generates a random string. The result may be obtained using getResult()
	 * method.
	 * 
	 * @param lengthInDigits the size of the result.
	 * @return this object for chaining.
	 */
	public DummyCastle randomStr(int lengthInChars) {
		if (lengthInChars < 0) {
			exception = new IllegalArgumentException("<0 argument");
			error = true;
			return this;
		}

		try {
			resultStr = RandomClient.generateRandomString(lengthInChars, RandomClient.Mode.ALPHANUMERIC, true);
		} catch (Exception e) {
			exception = e;
			error = true;
			resultStr = "";
		}

		return this;
	}

	/**
	 * Generates a random numeric value as a string based on a provided seed which
	 * guarantees the result will be always the same if you provide the method with
	 * the same seed(idepotency). The result may be obtained using getResult()
	 * method.
	 * 
	 * @param seed           any kind of string. May not be null nor empty.
	 * @param lengthInDigits the size of the result.
	 * @return this object for chaining.
	 */
	public DummyCastle randomDeterministicNum(String seed, int lengthInDigits) {
		if (seed == null || seed.isEmpty() || lengthInDigits < 0) {
			exception = new IllegalArgumentException("<0 argument");
			error = true;
			return this;
		}

		try {
			resultStr = RandomClient.generateNextDeterministicStr(seed, 0, lengthInDigits, RandomClient.Mode.NUMERIC,
					true);
		} catch (Exception e) {
			exception = e;
			error = true;
			resultStr = "";
		}

		return this;
	}

	/**
	 * Generates a random numeric value as a string based on a provided seed which
	 * guarantees the result will be always the same if you provide the method with
	 * the same seed(idepotency). The result may be obtained using getResult()
	 * method.
	 * 
	 * @param seed          any kind of string May not be null nor empty.
	 * @param lengthInChars the size of the result.
	 * @return this object for chaining.
	 */
	public DummyCastle randomDeterministicStr(String seed, int lengthInChars) {

		if (seed == null || seed.isEmpty() || lengthInChars < 0) {
			exception = new IllegalArgumentException("Empty or null or <0 argument");
			error = true;
			return this;
		}

		try {
			resultStr = RandomClient.generateNextDeterministicStr(seed, 0, lengthInChars,
					RandomClient.Mode.ALPHANUMERIC, true);
		} catch (Exception e) {
			exception = e;
			error = true;
			resultStr = "";
		}

		return this;
	}

	/**
	 * Generates a random numeric value as a string based on a provided seed which
	 * guarantees the result will be always the same if you provide the method with
	 * the same seed(idepotency). The result is generated from a certain point. The
	 * result may be obtained using getResult() method.
	 * 
	 * @param seed           any kind of string. May not be null nor empty.
	 * @param lengthInDigits the size of the result.
	 * @param resPos         starting point of a deterministic result.
	 * @return this object for chaining.
	 */
	public DummyCastle randomDeterministicNumFrom(String seed, int lengthInDigits, int resPos) {
		if (seed == null || seed.isEmpty() || lengthInDigits < 0 || resPos < 0) {
			exception = new IllegalArgumentException("Empty or null or <0 argument");
			error = true;
			return this;
		}

		try {
			resultStr = RandomClient.generateNextDeterministicStr(seed, resPos, lengthInDigits,
					RandomClient.Mode.NUMERIC, true);
		} catch (Exception e) {
			exception = e;
			error = true;
			resultStr = "";
		}

		return this;
	}

	/**
	 * Generates a string based on a provided seed which guarantees the result will
	 * be always the same if you provide the method with the same seed(idepotency).
	 * The result is generated from certain point. The result may be obtained using
	 * getResult() method.
	 * 
	 * @param seed           any kind of string. May not be null nor empty.
	 * @param lengthInDigits the size of the result.
	 * @param resPos         starting point of a deterministic result.
	 * @return this object for chaining.
	 */
	public DummyCastle randomDeterministicStrFrom(String seed, int lengthInChars, int keyPos) {

		if (seed == null || seed.isEmpty() || lengthInChars < 0 || keyPos < 0) {
			exception = new IllegalArgumentException("Empty or null or <0 argument");
			error = true;
			return this;
		}

		try {
			resultStr = RandomClient.generateNextDeterministicStr(seed, keyPos, lengthInChars,
					RandomClient.Mode.ALPHANUMERIC, true);
		} catch (Exception e) {
			exception = e;
			error = true;
			resultStr = "";
		}

		return this;
	}

	/* SHUFFLE */
	/**
	 * Shuffles randomly characters in a text.
	 * 
	 * @param textToShuffle. May not be null nor empty.
	 * @return this object for chaining.
	 */
	public DummyCastle shuffle(String textToShuffle) {

		if (textToShuffle == null || textToShuffle.isEmpty()) {
			exception = new IllegalArgumentException("Empty or null");
			error = true;
			return this;
		}
		try {
			String permText = new String(PermutClient.shuffle(textToShuffle.getBytes()));
			resultStr = permText;
		} catch (Exception e) {
			exception = e;
			error = true;
			resultStr = "";
		}

		return this;
	}

	/**
	 * @return results of all operations. The same as toString(). If not operation
	 *         is executed the result will be an empty string.
	 */
	public String getResult() {
		return resultStr;
	}

	/**
	 *
	 * @return results of all operations. The same as toString(). If not operation
	 *         is executed the result will be an empty string. The same as
	 *         getResult()
	 */
	public String toString() {
		return getResult();
	}

	/**
	 * Resets the whole object and cleans its memory. Resets also the error flag.
	 * 
	 */
	public void reset() {
		resultStr = "";
		error = false;
		exception = new Exception();
	}

	/**
	 * @return true if an exception was thrown during any of previous operation.
	 *         Check this flag and use getException() to get the exception.
	 */
	public boolean isError() {
		return error;
	}

	/**
	 * @return exception that was thrown during any of previous operation.
	 */
	public Exception getException() {
		return exception;
	}

}
