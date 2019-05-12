package cs466_project;

public class compare {

	public static void main(String[] args) {
		generate_data gd = new generate_data(20, 6);
		String[] keysDB = gd.getKeys("DB");
		String[] keysOG = gd.getKeys("OG");
		String data = gd.getOriginal();
		
		Overlap_Naive m1 = new Overlap_Naive(keysOG);
		Overlap_DP m2 = new Overlap_DP(keysOG);
		De_Bruijn_1 m3 = new De_Bruijn_1(keysDB);
		De_Bruijn_2 m4 = new De_Bruijn_2(keysDB);
		
		
		System.out.println("String length is set to be " + data.length());
		System.out.println("Orignal string is:\n" + data);
		System.out.println("-------------------");
		
		System.out.println("For Overlapped method input size is " + keysOG.length);
		
		long start1 = System.currentTimeMillis();
		String res1 = m1.getString();
		long finish1 = System.currentTimeMillis();
		long timeElapsed1 = finish1 - start1;
		System.out.println("Naive method: ");
		System.out.println("Recovered string is:\n" + res1);
		System.out.println("Running takes: " + (timeElapsed1 * 0.001) + " secondes.");
		if (res1.equals(data))
			System.out.println("Recovered string matches the orginial!");
		else
			System.out.println("Recovered string mismatches the original!");
		System.out.println("-------------------");
		
		long start2 = System.currentTimeMillis();
		String res2 = m2.getString();
		long finish2 = System.currentTimeMillis();
		long timeElapsed2 = finish2 - start2;
		System.out.println("DP method: ");
		System.out.println("Recovered string is:\n" + res2);
		System.out.println("Running takes: " + (timeElapsed2 * 0.001) + " secondes.");
		if (res2.equals(data))
			System.out.println("Recovered string matches the orginial!");
		else
			System.out.println("Recovered string mismatches the original!");
		
		System.out.println("-------------------");
		
		System.out.println("For De Bruijn method input size is " + keysDB.length);
		
		long start3 = System.currentTimeMillis();
		String res3 = m3.getString();
		long finish3 = System.currentTimeMillis();
		long timeElapsed3 = finish3 - start3;
		System.out.println("Fleury method: ");
		System.out.println("Recovered string is:\n" + res3);
		System.out.println("Running takes: " + (timeElapsed3 * 0.001) + " secondes.");
		if (res3.equals(data))
			System.out.println("Recovered string matches the orginial!");
		else
			System.out.println("Recovered string mismatches the original!");
		System.out.println("-------------------");
		
		long start4 = System.currentTimeMillis();
		String res4 = m4.getString();
		long finish4 = System.currentTimeMillis();
		long timeElapsed4 = finish4 - start4;
		System.out.println("Hierholzer method: ");
		System.out.println("Recovered string is:\n" + res4);
		System.out.println("Running takes: " + (timeElapsed4 * 0.001) + " secondes.");
		if (res4.equals(data))
			System.out.println("Recovered string matches the orginial!");
		else
			System.out.println("Recovered string mismatches the original!");

	}

}
