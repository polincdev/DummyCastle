package pl.polinc.dummycastle.crypt.asymm;

import java.math.BigInteger;

public interface CryptAsymmKey {

	public String toString();

	public BigInteger getExponent();

	public BigInteger getProduct();

	public int getKeySize();

}