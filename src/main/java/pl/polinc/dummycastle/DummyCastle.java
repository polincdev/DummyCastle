package pl.polinc.dummycastle;

import java.math.BigInteger;

import pl.polinc.dummycastle.coding.Coder;
import pl.polinc.dummycastle.crypt.asymm.CryptAsymm;
import pl.polinc.dummycastle.crypt.asymm.CryptAsymmKey;
import pl.polinc.dummycastle.crypt.asymm.CryptAsymmKeys;
import pl.polinc.dummycastle.crypt.asymm.CryptAsymmKeysPair;
import pl.polinc.dummycastle.crypt.asymm.CryptAsymmPrivateKey;
import pl.polinc.dummycastle.crypt.asymm.CryptAsymmPublicKey;
import pl.polinc.dummycastle.crypt.asymm.CryptAsymmKeys.KEY_SIZE_TYPE;
import pl.polinc.dummycastle.crypt.symm.CryptSymm;
import pl.polinc.dummycastle.crypt.symm.CryptSymmKey;
import pl.polinc.dummycastle.hash.HashClient;
import pl.polinc.dummycastle.hash.HasherClient;
import pl.polinc.dummycastle.hash.PermutClient;
import pl.polinc.dummycastle.obfuscate.ObfuscateClient;
import pl.polinc.dummycastle.random.RandomClient;

/**
 * @author polinc
 *
 */
public class DummyCastle {

	byte[] resultBytes = new byte[0];
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
	public DummyCastle encryptSymmWith(String plainText) {

		if (plainText == null || plainText.isEmpty() || cryptSymmKey == null) {
			exception = new IllegalArgumentException("Empty or null argument");
			error = true;
			return this;
		}

		try {
			resultBytes = CryptSymm.encryptStream(plainText.getBytes(), cryptSymmKey);
		} catch (Exception e) {
			setUpError(e);
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

		if (resultBytes == null || resultBytes.length == 0 || cryptSymmKey == null) {
			exception = new IllegalArgumentException("Empty or null argument");
			error = true;
			return this;
		}

		try {
			resultBytes = CryptSymm.encryptStream(resultBytes, cryptSymmKey);
		} catch (Exception e) {
			setUpError(e);
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
	public DummyCastle decryptSymmWith(String secretText) {

		if (secretText == null || secretText.isEmpty() || cryptSymmKey == null) {
			exception = new IllegalArgumentException("Empty or null argument");
			error = true;
			return this;
		}

		try {
			resultBytes = CryptSymm.decryptStream(Coder.decodeString2Bytes(secretText), cryptSymmKey);
		} catch (Exception e) {
			setUpError(e);
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

		if (resultBytes == null || resultBytes.length == 0 || cryptSymmKey == null) {
			exception = new IllegalArgumentException("Empty or null argument");
			error = true;
			return this;
		}
		try {
			resultBytes = CryptSymm.decryptStream(resultBytes, cryptSymmKey);
		} catch (Exception e) {
			setUpError(e);
		}

		return this;
	}

	/**
	 * Encrypts provided plain text using symmetric encryption. The result may be
	 * obtained using getResult() method.
	 * 
	 * @param plainText to encrypt and encode.
	 * @param keyPos    starting position of the key used to encrypt previous chunks
	 *                  of data.
	 * @return this object for chaining.
	 */
	public DummyCastle encryptSymmFromWith(String plainText, int keyPos) {

		if (plainText == null || plainText.isEmpty() || cryptSymmKey == null || keyPos < 0) {
			exception = new IllegalArgumentException("Empty or null or <0 argument");
			error = true;
			return this;
		}

		try {
			resultBytes = CryptSymm.encryptStreamFrom(plainText.getBytes(), cryptSymmKey, keyPos);
		} catch (Exception e) {
			setUpError(e);
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
	public DummyCastle decryptSymmFromWith(String secretText, int keyPos) {

		if (secretText == null || secretText.isEmpty() || cryptSymmKey == null || keyPos < 0) {
			exception = new IllegalArgumentException("Empty or null or <0 argument");
			error = true;
			return this;
		}

		try {
			resultBytes = CryptSymm.decryptStreamFrom(Coder.decodeString2Bytes(secretText), cryptSymmKey, keyPos);
		} catch (Exception e) {
			setUpError(e);
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
	public DummyCastle genSymmKeyWith() {
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
	public DummyCastle genSymmKeyWith(String keySeed) {

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
			cryptSymmKey = new CryptSymmKey();
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
	public DummyCastle encryptAsymmWith(String plainText, CryptAsymmKey key) {

		if (plainText == null || plainText.isEmpty() || key == null) {
			exception = new IllegalArgumentException("Empty or null argument");
			error = true;
			return this;
		}

		try {
			resultBytes = CryptAsymm.encryptStream(plainText.getBytes(), key);
		} catch (Exception e) {
			setUpError(e);
		}

		return this;
	}

	/**
	 * Encrypts data internally using asymmetric encryption. The result may be
	 * obtained using getResult() method and it is not encoded. Use decryptSymm() to
	 * decrypt the result. Use decryptAsymm() to decrypt.
	 * 
	 * @param key contains data used to encrypt plain text. If it is an instance of
	 *            a CryptAsymmPublicKey class, a CryptAsymmPrivateKey object should
	 *            be used to decrypt the text.
	 * @return this object for chaining.
	 */
	public DummyCastle encryptAsymmWithKey(CryptAsymmKey key) {

		if (resultBytes == null || resultBytes.length == 0 || key == null) {
			exception = new IllegalArgumentException("Empty or null argument");
			error = true;
			return this;
		}

		try {
			resultBytes = CryptAsymm.encryptStream(resultBytes, key);
		} catch (Exception e) {
			setUpError(e);
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
	public DummyCastle decryptAsymmWith(String secretText, CryptAsymmKey key) {

		if (secretText == null || secretText.isEmpty() || key == null) {
			exception = new IllegalArgumentException("Empty or null argument");
			error = true;
			return this;
		}

		try {
			resultBytes = CryptAsymm.decryptStream(Coder.decodeHex(secretText.getBytes()), key);
		} catch (Exception e) {
			setUpError(e);
		}

		return this;
	}

	/**
	 * Decrypts dta internally using asymmetric encryption. The result may be
	 * obtained using getResult() method and it is not encoded.
	 * 
	 * @param key contains data used to crypt plain text. It should be an instance
	 *            of a CryptAsymmPublicKey class if CryptAsymmPrivateKey was used to
	 *            encrypt the text.
	 * @return this object for chaining.
	 */
	public DummyCastle decryptAsymmWithKey(CryptAsymmKey key) {

		if (resultBytes == null || resultBytes.length == 0 || key == null) {
			exception = new IllegalArgumentException("Empty or null argument");
			error = true;
			return this;
		}

		try {
			resultBytes = CryptAsymm.decryptStream(resultBytes, key);
		} catch (Exception e) {
			setUpError(e);
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
	public CryptAsymmKeysPair genAsymmKeysWithKeySizeType(KEY_SIZE_TYPE keySizeType) {

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
	public CryptAsymmPublicKey genAsymmPublicKeyWith(String keyData) {

		try {
			return CryptAsymmPublicKey.createFromString(keyData);
		} catch (Exception e) {
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
	public CryptAsymmPrivateKey genAsymmPrivateKeyWith(String keyData) {

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
	public DummyCastle encodeWith(String textToDecode) {

		if (textToDecode == null || textToDecode.isEmpty()) {
			exception = new IllegalArgumentException("Empty or null argument");
			error = true;
			return this;
		}

		try {
			resultBytes = Coder.encode(textToDecode.getBytes());
		} catch (Exception e) {
			setUpError(e);
		}

		return this;
	}

	/**
	 * Decodes a text using HEX encoding. The result may be obtained using
	 * getResult().
	 * 
	 * @param encodedText text to be decoded. Must be non-null nor empty.
	 * @return this object for chaining.
	 */
	public DummyCastle decodeWith(String encodedText) {

		if (encodedText == null || encodedText.isEmpty()) {
			exception = new IllegalArgumentException("Empty or null argument");
			error = true;
			return this;
		}

		try {
			resultBytes = Coder.decode(encodedText.getBytes());
		} catch (Exception e) {
			setUpError(e);
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
	public DummyCastle hashToNumWith(String textToHash) {

		if (textToHash == null || textToHash.isEmpty()) {
			exception = new IllegalArgumentException("Empty or null argument");
			error = true;
			return this;
		}

		try {
			resultBytes = String.valueOf(HashClient.defaultHash(textToHash)).getBytes();
		} catch (Exception e) {
			setUpError(e);
		}

		return this;
	}

	/**
	 * Hashes(reduces) a text internally into a long number represented as a string.
	 * The result may be obtained using getResult() method.
	 * 
	 * @return this object for chaining.
	 */
	public DummyCastle hashToNum() {

		if (resultBytes.length == 0) {
			exception = new IllegalArgumentException("Empty or null argument");
			error = true;
			return this;
		}

		try {
			resultBytes = String.valueOf(HashClient.defaultHash(resultBytes)).getBytes();
		} catch (Exception e) {
			setUpError(e);
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
	public DummyCastle hashToStrWith(String textToHash) {

		if (textToHash == null || textToHash.isEmpty()) {
			exception = new IllegalArgumentException("Empty or null argument");
			error = true;
			return this;
		}

		try {
			resultBytes = HasherClient.hash(textToHash.getBytes(), 8);
		} catch (Exception e) {
			setUpError(e);
		}

		return this;
	}

	/**
	 * Hashes(reduces) a text internally into a string. The result may be obtained
	 * using getResult() method.
	 * 
	 * @return this object for chaining.
	 */
	public DummyCastle hashToStr() {

		if (resultBytes.length == 0) {
			exception = new IllegalArgumentException("Empty or null argument");
			error = true;
			return this;
		}

		try {
			resultBytes = HasherClient.hash(resultBytes, 8);
		} catch (Exception e) {
			setUpError(e);
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
	public DummyCastle obfuscateWith(String textToObfuscate) {

		if (textToObfuscate == null || textToObfuscate.isEmpty()) {
			exception = new IllegalArgumentException("Empty or null argument");
			error = true;
			return this;
		}

		try {
			resultBytes = ObfuscateClient.obfuscate(textToObfuscate.getBytes());
		} catch (Exception e) {
			setUpError(e);
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

		if (resultBytes == null || resultBytes.length == 0) {
			exception = new IllegalArgumentException("Empty or null argument");
			error = true;
			return this;
		}

		try {
			resultBytes = ObfuscateClient.obfuscate(resultBytes);
		} catch (Exception e) {
			setUpError(e);
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
	public DummyCastle unobfuscateWith(String obfuscatedText) {

		if (obfuscatedText == null || obfuscatedText.isEmpty()) {
			exception = new IllegalArgumentException("Empty or null argument");
			error = true;
			return this;
		}

		try {
			resultBytes = ObfuscateClient.unobfuscate(Coder.decodeString2Bytes(obfuscatedText));
		} catch (Exception e) {
			setUpError(e);
		}

		return this;
	}

	/**
	 * Unobfuscates(reveals) a text provided by a previous operation. The result may
	 * be obtained using getResult() method.
	 * 
	 * @return this object for chaining.
	 */
	public DummyCastle unobfuscate() {

		if (resultBytes == null || resultBytes.length == 0) {
			exception = new IllegalArgumentException("Empty or null argument");
			error = true;
			return this;
		}

		try {
			resultBytes = ObfuscateClient.unobfuscate(resultBytes);
		} catch (Exception e) {
			setUpError(e);
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
	public DummyCastle randomNumWith(int lengthInDigits) {

		if (lengthInDigits < 0) {
			exception = new IllegalArgumentException("<0 argument");
			error = true;
			return this;
		}

		try {
			resultBytes = RandomClient.generateRandomString(lengthInDigits, RandomClient.Mode.NUMERIC, true).getBytes();
		} catch (Exception e) {
			setUpError(e);
		}

		return this;
	}

	/**
	 * Generates a random string. The result may be obtained using getResult()
	 * method.
	 * 
	 * @param lengthInChars the size of the result.
	 * @return this object for chaining.
	 */
	public DummyCastle randomStrWith(int lengthInChars) {
		if (lengthInChars < 0) {
			exception = new IllegalArgumentException("<0 argument");
			error = true;
			return this;
		}

		try {
			resultBytes = RandomClient.generateRandomString(lengthInChars, RandomClient.Mode.ALPHANUMERIC, true)
					.getBytes();
		} catch (Exception e) {
			setUpError(e);
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
	public DummyCastle randomDeterministicNumWith(String seed, int lengthInDigits) {
		if (seed == null || seed.isEmpty() || lengthInDigits < 0) {
			exception = new IllegalArgumentException("<0 argument");
			error = true;
			return this;
		}

		try {
			resultBytes = RandomClient
					.generateNextDeterministicStr(seed.getBytes(), 0, lengthInDigits, RandomClient.Mode.NUMERIC, true)
					.getBytes();
		} catch (Exception e) {
			setUpError(e);
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
	public DummyCastle randomDeterministicStrWith(String seed, int lengthInChars) {

		if (seed == null || seed.isEmpty() || lengthInChars < 0) {
			exception = new IllegalArgumentException("Empty or null or <0 argument");
			error = true;
			return this;
		}

		try {
			resultBytes = RandomClient.generateNextDeterministicStr(seed.getBytes(), 0, lengthInChars,
					RandomClient.Mode.ALPHANUMERIC, true).getBytes();
		} catch (Exception e) {
			setUpError(e);
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
	public DummyCastle randomDeterministicNumFromWith(String seed, int lengthInDigits, int resPos) {
		if (seed == null || seed.isEmpty() || lengthInDigits < 0 || resPos < 0) {
			exception = new IllegalArgumentException("Empty or null or <0 argument");
			error = true;
			return this;
		}

		try {
			resultBytes = RandomClient.generateNextDeterministicStr(seed.getBytes(), resPos, lengthInDigits,
					RandomClient.Mode.NUMERIC, true).getBytes();
		} catch (Exception e) {
			setUpError(e);
		}

		return this;
	}

	/**
	 * Generates a string based on a provided seed which guarantees the result will
	 * be always the same if you provide the method with the same seed(idepotency).
	 * The result is generated from certain point. The result may be obtained using
	 * getResult() method.
	 * 
	 * @param seed          any kind of string. May not be null nor empty.
	 * @param lengthInChars the size of the result.
	 * @param keyPos        starting point of a deterministic result.
	 * @return this object for chaining.
	 */
	public DummyCastle randomDeterministicStrFromWith(String seed, int lengthInChars, int keyPos) {

		if (seed == null || seed.isEmpty() || lengthInChars < 0 || keyPos < 0) {
			exception = new IllegalArgumentException("Empty or null or <0 argument");
			error = true;
			return this;
		}

		try {
			resultBytes = RandomClient.generateNextDeterministicStr(seed.getBytes(), keyPos, lengthInChars,
					RandomClient.Mode.ALPHANUMERIC, true).getBytes();
		} catch (Exception e) {
			e.printStackTrace();
			setUpError(e);
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
	public DummyCastle shuffleWith(String textToShuffle) {

		if (textToShuffle == null || textToShuffle.isEmpty()) {
			exception = new IllegalArgumentException("Empty or null");
			error = true;
			return this;
		}
		try {
			resultBytes = PermutClient.shuffle(textToShuffle.getBytes());

		} catch (Exception e) {
			setUpError(e);
		}

		return this;
	}

	/**
	 * Shuffles randomly characters internally.
	 * 
     * @return this object
	 */
	public DummyCastle shuffle() {
		if ((resultBytes == null || resultBytes.length == 0)) {
			exception = new Exception("Empty or null");
			error = true;
			return this;
		}
		try {
			resultBytes = PermutClient.shuffle(resultBytes);
		} catch (Exception e) {
			setUpError(e);
		}
		return this;
	}

	/**
	 * Shuffles characters internally based on a provided seed. It always provides
	 * the same result give the same input data.
	 * 
	 * @param seed - key data used to shuffle
	 * @return this object
	 */
	public DummyCastle shuffleDeterministic(String seed) {
		if ((resultBytes == null || resultBytes.length == 0) || (seed == null || seed.isEmpty())) {
			exception = new Exception("Empty or null");
			error = true;
			return this;
		}
		try {
			resultBytes = PermutClient.shuffleDeterministic(resultBytes, seed.getBytes());
		} catch (Exception e) {
			setUpError(e);
		}
		return this;
	}

	/**
	 * Shuffles characters in a text based on a provided seed. It always provides
	 * the same result give the same input data.
	 * 
	 * @param textToShuffle - text to shuffle
	 * @param seed          - key data used to shuffle
	 * @return this object
	 */
	public DummyCastle shuffleDeterministicWith(String textToShuffle, String seed) {
		if (textToShuffle == null || textToShuffle.isEmpty() || (seed == null || seed.isEmpty())) {
			exception = new Exception("Empty or null");
			error = true;
			return this;
		}
		try {
			resultBytes = PermutClient.shuffleDeterministic(textToShuffle.getBytes(), seed.getBytes());
		} catch (Exception e) {
			setUpError(e);
		}
		return this;
	}

	/**
	 * @return results of all operations encoded using the default encoding - HEX.
	 *         The same as toString(). If no operation is executed the result will
	 *         be an empty string.
	 */
	public String getResult() {
		try {
			return new String(Coder.encode(resultBytes));
		} catch (Exception e) {
			exception = e;
			error = true;
		}
		return "";
	}

	/**
	 * Returns unencoded raw string data which is a result of library's operations
	 * 
	 * @return unencoded string
	 */
	public String getResultDecoded() {
		try {
			return new String(resultBytes);
		} catch (Exception e) {
			exception = e;
			error = true;
		}
		return "";
	}

	/**
	 * Returns unencoded raw byte data which is a result of library's operations
	 * 
	 * @return unencoded bytes
	 */
	byte[] getResultDecodedRaw() {
		try {
			return resultBytes;
		} catch (Exception e) {
			exception = e;
			error = true;
		}
		return new byte[0];
	}

	/**
	 * Inserts unencoded raw string data to work on it.
	 * 
	 * @param plainText - unencoded plain data as string.
	 * 
	 * @return this object
	 */
	public DummyCastle fromStringDecoded(String plainText) {
		if ((plainText == null) || plainText.isEmpty()) {
			exception = new Exception("Empty or null argument");
			error = true;
			return this;
		}
		try {
			resultBytes = plainText.getBytes();
		} catch (Exception e) {
			setUpError(e);
		}
		return this;
	}

	/**
	 * Inserts unencoded raw byte data to work on it.
	 * 
	 * @param plainText - unencoded plain byte data.
	 * 
	 * @return this object
	 */
	DummyCastle fromBytesDecoded(byte[] plainText) {
		if ((plainText == null) || plainText.length == 0) {
			exception = new Exception("Empty or null argument");
			error = true;
			return this;
		}
		try {
			resultBytes = plainText;
		} catch (Exception e) {
			setUpError(e);
		}
		return this;
	}

	/**
	 * Inserts encoded with the library's default encoding string data to work on
	 * it.
	 * 
	 * @param encodedText - unencoded plain data as string.
	 * 
	 * @return this object
	 */
	DummyCastle fromStringEncoded(String encodedText) {
		if ((encodedText == null) || encodedText.isEmpty()) {
			exception = new Exception("Empty or null argument");
			error = true;
			return this;
		}
		try {
			resultBytes = Coder.decode(encodedText.getBytes());
		} catch (Exception e) {
			setUpError(e);
		}
		return this;
	}

	/**
	 * Inserts encoded with the library's default encoding byte data to work on it.
	 * 
	 * @param encodedText - encoded data.
	 * 
	 * @return this object
	 */
	DummyCastle fromBytesEncoded(byte[] encodedText) {
		if ((encodedText == null) || encodedText.length == 0) {
			exception = new Exception("Empty or null argument");
			error = true;
			return this;
		}
		try {
			resultBytes = encodedText;
		} catch (Exception e) {
			setUpError(e);
		}
		return this;
	}

	/**
	 *
	 * @return results of all operations. Encoded with default encoding method -
	 *         HEX. The same as toString(). If not operation is executed the result
	 *         will be an empty string. The same as getResult()
	 */
	public String toString() {
		return getResult();
	}

	/**
	 * Returns unencoded data being as a string. It is a result of all operations.
	 * 
	 * @return raw unencoded data.
	 */
	public String toStringDecoded() {
		return getResultDecoded();
	}

	/**
	 * Resets the whole object and cleans its memory. Resets also the error flag.
	 * 
	 */
	public void reset() {
		resultBytes = new byte[0];
		error = false;
		exception = new Exception();
		cryptSymmKey = new CryptSymmKey();
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

	/**
	 * Prepares exception
	 * @param e exception to setup
	 */
	private void setUpError(Exception e) {
		exception = e;
		error = true;
		resultBytes = new byte[0];
	}

}
