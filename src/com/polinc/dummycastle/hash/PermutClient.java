package com.polinc.dummycastle.hash;

import java.util.Random;

public class PermutClient {

	// Fisher–Yates shuffle
	public static byte[] shuffle(byte[] ar) {
		Random rnd = new Random();
		for (int i = ar.length - 1; i > 0; i--) {
			int index = rnd.nextInt(i + 1);
			// Simple swap
			byte a = ar[index];
			ar[index] = ar[i];
			ar[i] = a;
		}
		return ar;
	}
}
