package pl.polinc.dummycastle.random;

public class RandomClient {

	public static enum Mode {
		ALPHA, ALPHANUMERIC, NUMERIC
	}

	static public String generateRandomString(int length, Mode mode, boolean nonleadzero) {

		StringBuffer buffer = new StringBuffer();
		String characters = "";

		switch (mode) {

		case ALPHA:
			characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
			break;

		case ALPHANUMERIC:
			characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
			break;

		case NUMERIC:
			characters = "1234567890";
			break;

		}

		int charactersLength = characters.length();

		for (int i = 0; i < length; i++) {
			int index = generateNextRandomInt(charactersLength);
			char ch = characters.charAt((int) index);
			if (nonleadzero && ch == '0') {
				index = generateNextRandomInt(charactersLength - 1);
				ch = characters.charAt((int) index);
			}
			buffer.append(characters.charAt((int) index));
		}
		return buffer.toString();
	}

	static int generateRandomInt(long seed, int nbits) {
		long x = seed;
		x ^= (x << 21);
		x ^= (x >>> 35);
		x ^= (x << 4);
		seed = x;
		x &= ((1L << nbits) - 1);
		return (int) x;
	}

	static int generateRandomInt(int nbits) {
		//
		long seed = System.nanoTime() + System.currentTimeMillis();
		//
		return generateRandomInt(seed, nbits);

	}

	static int generateRandomInt() {
		//
		long seed = System.nanoTime() + System.currentTimeMillis();
		//
		return generateRandomInt(seed, 32);
	}

	static int generateNextRandomInt(int cap) {
		//
		int num = generateRandomInt() % cap;
		return num > 0 ? num : -num;

	}

	public static String generateNextDeterministicStr(String salt, int posStart, int length, Mode mode,
			boolean nonleadzero) {
		byte[] ibuf = salt.getBytes();
		int iLen = ibuf.length;
		//
		byte seed = 0;
		for (int i = 0; i < iLen; i++)
			seed = (byte) (seed ^ ibuf[i]);

		/*
		 * // Modulus parameter int mod = 7; // Multiplier term int multiplier = 3; //
		 * Increment term int inc = 3; // Number of Random numbers // to be generated
		 * int noOfRandomNum = length; // To store random numbers int[] randomNums = new
		 * int[noOfRandomNum]; // Function Call lcm(seed, mod, multiplier, inc,
		 * randomNums, noOfRandomNum);
		 */

		StringBuffer buffer = new StringBuffer();
		String characters = "";

		switch (mode) {

		case ALPHA:
			characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
			break;

		case ALPHANUMERIC:
			characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
			break;

		case NUMERIC:
			characters = "1234567890";
			break;

		}

		int charactersLength = characters.length();
		for (int i = 0; i < length; i++) {
			int index = (salt.charAt((posStart + i) % salt.length()) ^ (seed + (posStart + i))) % charactersLength;

		 
			char ch = characters.charAt((int) index);
			if (nonleadzero && ch == '0') {
				index = (salt.charAt((posStart + i) % salt.length()) ^ (seed * (posStart + i)))
						% (charactersLength - 1);
				ch = characters.charAt((int) index);
			}
			buffer.append(characters.charAt((int) index));
		}
	 
		return buffer.toString();
	}

//Function to generate random numbers
	/*
	 * static void lcm(int seed, int mod, int multiplier, int inc, int[] randomNums,
	 * int noOfRandomNum ) {
	 * 
	 * // Initialize the seed state randomNums[0] = seed;
	 * 
	 * // Traverse to generate required // numbers of random numbers for (int i = 1;
	 * i < noOfRandomNum; i++) {
	 * 
	 * // Follow the linear congruential method randomNums[i] = ((randomNums[i - 1]
	 * * multiplier) + inc) % mod; } }
	 */

	static float generateUniqueFloatInRange(float min, float max) {
		// musi byc roznica
		if (min >= max)
			max = (float) (min + 0.1f);

		float result = (float) (Math.random() < 0.5 ? ((1 - Math.random()) * (max - min) + min)
				: (Math.random() * (max - min) + min));
		return result;
	}

}
