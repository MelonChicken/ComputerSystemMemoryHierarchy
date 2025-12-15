import java.util.*;

public class Main {
	static L1Cache l1Cache = new L1Cache();
	static L2Cache l2Cache = new L2Cache();
	static L3Cache l3Cache = new L3Cache();
	static MainMemory l4Cache = new MainMemory();
	
	static double[] prices;
	
	static void loadPrices(String path, int nRows) throws Exception{
		prices = new double[nRows];
		try (java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(path))) {
			br.readLine(); //header
			String line;
			while ((line = br.readLine()) != null) {
				String[] c = line.split(",", -1);
				int key = Integer.parseInt(c[0]);
				prices[key] = Double.parseDouble(c[1]);
			}
		}		
	}
	
	
	public static void main(String [] args) throws Exception {
		
		loadPrices("python/data/uk_prices_8000_key_price.csv", 8000);
		
		int[] tests = randomSamples();
		
		for (int addr : tests) {
			double value = access(addr);
			System.out.println("addr = " + addr + " value = " + value);
		}
		
		printHitMiss();
	}

	private static double access(int address) throws Exception {
		/*
		 * L1 -> L2 -> L3 -> L4 -> 원본 데이터 탐색 
		 */
		
		if(l1Cache.lookUp(address)) {
			return prices[l1Cache.read(address).block];
		} else if (l2Cache.lookUp(address)) {
			l1Cache.insert(address);
			return prices[l1Cache.read(address).block];
		} else if (l3Cache.lookUp(address)) {
			l2Cache.insert(address);
			l1Cache.insert(address);
			return prices[l1Cache.read(address).block];
		} else if (l4Cache.lookUp(address)) {
			l3Cache.insert(address);
			l2Cache.insert(address);
			l1Cache.insert(address);
			return prices[l1Cache.read(address).block];
		} else {
			l4Cache.insert(address);
			l3Cache.insert(address);
			l2Cache.insert(address);
			l1Cache.insert(address);
			return prices[l1Cache.read(address).block];
		}
	}
	
	private static void printHitMiss() {
        int totalHits = l1Cache.getHit() + l2Cache.getHit() + l3Cache.getHit() + l4Cache.getHit();
        int totalMisses = l1Cache.getMiss() + l2Cache.getMiss() + l3Cache.getMiss() + l4Cache.getMiss();
        int totalCount = totalMisses + totalHits;

		System.out.println("L1 hits: " + l1Cache.getHit() + ", " + "L1 misses: " + l1Cache.getMiss() + " L1 hit ratio: "+(1.0*l1Cache.getHit() / l1Cache.getTotalAttempt()));
		System.out.println("L2 hits: " + l2Cache.getHit() + ", " + "L2 misses: " + l2Cache.getMiss()+ " L2 hit ratio: "+(1.0*l2Cache.getHit() / l2Cache.getTotalAttempt()));
		System.out.println("L3 hits: " + l3Cache.getHit() + ", " + "L3 misses: " + l3Cache.getMiss()+ " L3 hit ratio: "+(1.0*l3Cache.getHit() / l3Cache.getTotalAttempt()));
		System.out.println("L4 hits: " + l4Cache.getHit() + ", " + "L4 misses: " + l4Cache.getMiss()+ " L4 hit ratio: "+(1.0*l4Cache.getHit() / l4Cache.getTotalAttempt()));
        System.out.println("Total hits: " + totalHits+" Total misses: " + totalMisses+" Total Hit Ratio: "+(1.0*totalHits/totalCount));
	}

    private static int[] randomSamples() {
        Random random = new Random();
        int[] result = new int[100];

        for (int i = 0; i < 100; i++) {
            result[i] = random.nextInt(8000);
        }
        return result;
    }
}