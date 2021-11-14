package com.polinc.dummycastle.coding;

public class Coder {

	static public String encode(String plainText) {
		return encodeHex(plainText);
	}

	static public String decode(String plainText) {
		return decodeHex(plainText);
	}

	static public byte[] encode(byte[] plainText) {
		return encodeHex(plainText);
	}

	static public byte[] decode(byte[] plainText) {
		return decodeHex(plainText);
	}

	static public String encodeBase64(String plainText) {
		return new String(Base64.encode(plainText.getBytes()));
	}

	static public String encodeHex(String plainText) {
		return new String(Hex.encode(plainText.getBytes()));
	}

	static public byte[] encodeBase64(byte[] plainText) {
		return Base64.encode(plainText);
	}

	static public byte[] encodeHex(byte[] plainText) {
		return Hex.encode(plainText);
	}

	static public String decodeBase64(String plainText) {
		return new String(Base64.decode(plainText.getBytes()));
	}

	static public String decodeHex(String plainText) {
		return new String(Hex.decode(plainText.getBytes()));
	}

	static public byte[] decodeBase64(byte[] plainText) {
		return Base64.decode(plainText);
	}

	static public byte[] decodeHex(byte[] plainText) {
		return Hex.decode(plainText);
	}
}