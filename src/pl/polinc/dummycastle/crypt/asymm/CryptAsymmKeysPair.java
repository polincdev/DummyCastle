package pl.polinc.dummycastle.crypt.asymm;

public class CryptAsymmKeysPair {
	CryptAsymmPublicKey cryptAsymmPublicKey;
	CryptAsymmPrivateKey cryptAsymmPrivateKey;

	/**
	 * Retrieves a public key
	 * 
	 * @return CryptAsymmPublicKey key object.
	 */
	public CryptAsymmPublicKey getCryptAsymmPublicKey() {
		return cryptAsymmPublicKey;
	}

	/**
	 * Retrieves a private key
	 * 
	 * @return CryptAsymmPrivateKey key object.
	 */
	public CryptAsymmPrivateKey getCryptAsymmPrivateKey() {
		return cryptAsymmPrivateKey;
	}

	/**
	 * Creates an object containing two keys - public and private. This constructor
	 * is internally used by CryptAsymmKeys.generateKeys() method.
	 * 
	 * @param cryptAsymmPublicKey  public key object
	 * @param cryptAsymmPrivateKey private key object
	 */
	public CryptAsymmKeysPair(CryptAsymmPublicKey cryptAsymmPublicKey, CryptAsymmPrivateKey cryptAsymmPrivateKey) {
		this.cryptAsymmPublicKey = cryptAsymmPublicKey;
		this.cryptAsymmPrivateKey = cryptAsymmPrivateKey;
	}
}
