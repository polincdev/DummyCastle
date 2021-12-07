package pl.polinc.dummycastle.obfuscate;

import pl.polinc.dummycastle.random.RandomClient;

public class ObfuscateClient {

	public static byte[] obfuscate(byte[] ibuf) {
		byte[] key = RandomClient
				.generateNextDeterministicStr((ibuf.length + "").getBytes(), 0, 8, RandomClient.Mode.ALPHANUMERIC, true)
				.getBytes();
		System.out.println("AA=" + new String(key) + " " + new String(ibuf));
		int iLen = ibuf.length;
		byte[] result = new byte[iLen];
		for (int i = 0; i < iLen; i++) {
			result[i] = (byte) (ibuf[i] + key[(i % key.length)]);
		}

		return result;
	}

	public static byte[] unobfuscate(byte[] ibuf) {
		byte[] key = RandomClient
				.generateNextDeterministicStr((ibuf.length + "").getBytes(), 0, 8, RandomClient.Mode.ALPHANUMERIC, true)
				.getBytes();
		System.out.println("BB=" + new String(key) + " " + new String(ibuf));
		int iLen = ibuf.length;
		byte[] result = new byte[iLen];
		for (int i = 0; i < iLen; i++) {
			result[i] = (byte) (ibuf[i] - key[(i % key.length)]);
		}

		return result;
	}

}
