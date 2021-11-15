package pl.polinc.dummycastle.crypt.asymm;

import pl.polinc.dummycastle.crypt.symm.CryptSymmKey;

public class CryptAsymmClient {

	CryptSymmKey cryptSymmKey;

	// Domyslae - prosty algorytw XOR
	public CryptAsymmClient(CryptSymmKey cryptSymmKey) {
		this.cryptSymmKey = cryptSymmKey;

	}

//Dla recznego ustawienia 
	public CryptAsymmClient() {

	}

	// Bez hexa
	public byte[] encrypt(byte[] inputStr) {

		return crypt(inputStr);
	}

	// Bez hexa
	public byte[] encryptFrom(byte[] inputStr, int keyPos) {

		return cryptFrom(inputStr, keyPos);
	}

	public byte[] decrypt(byte[] inputStr) {

		return crypt(inputStr);
	}

	public byte[] decryptFrom(byte[] inputStr, int keyPos) {

		return cryptFrom(inputStr, keyPos);
	}

	public byte[] crypt(byte[] toEnc) {
		int t = 0;
		byte[] tog = new byte[toEnc.length];
		// if(keyBytes[t%keySize]>0) {
		while (t < toEnc.length) {
			int a = toEnc[t];
			int c = a ^ cryptSymmKey.getKeyAt(t);
			byte d = (byte) c;
			tog[t] = d;
			// if(t<10)
			// log.debug(t+":"+a+"_"+new String(Hex.encode(new
			// byte[]{d}))+"_"+keyStr.charAt(t%keySize)+"_"+c+"_"+keyStr+"_"+keySize);
			t++;

			// }

		}
		//
		return tog;
	}

	public byte[] cryptFrom(byte[] toEnc, int keyPos) {
		int t = 0;
		byte[] tog = new byte[toEnc.length];
		// if(keyBytes[t%keySize]>0) {
		while (t < toEnc.length) {
			int a = toEnc[t];
			int c = a ^ cryptSymmKey.getKeyAt(t + keyPos);
			byte d = (byte) c;
			tog[t] = d;
			// if(t<10)
			// log.debug(t+":"+a+"_"+new String(Hex.encode(new
			// byte[]{d}))+"_"+keyStr.charAt(t%keySize)+"_"+c+"_"+keyStr+"_"+keySize);
			t++;

			// }

		}
		//
		return tog;
	}

//////////////XOR W TEN SAMEJ TABLICY 
	public void encryptInside(byte[] inputStr) {
		cryptInside(inputStr);
	}

	public void decryptInside(byte[] inputStr) {
		cryptInside(inputStr);
	}

	// Koduje do tej same tablicy nadpisujeac ja
	public void cryptInside(byte[] toEnc) {
		int t = 0;

		// if(keyBytes[t%keySize]>0)
		{
			while (t < toEnc.length) {
				int a = toEnc[t];
				int c = a ^ cryptSymmKey.getKeyAt(t);
				byte d = (byte) c;
				toEnc[t] = d;
				t++;
			}

		}
	}

}
