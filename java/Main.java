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
		// 예시 데이터셋 설정
		loadPrices("python/data/uk_prices_8000_key_price.csv", 8000);
		
		int[] tests = {5, 133, 5, 261, 5, 133, 389, 5, 133};
		
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
			return prices[address];
		} else if (l2Cache.lookUp(address)) {
			l1Cache.insert(address);
			return prices[address];
		} else if (l3Cache.lookUp(address)) {
			l2Cache.insert(address);
			l1Cache.insert(address);
			return prices[address];
		} else if (l4Cache.lookUp(address)) {
			l3Cache.insert(address);
			l2Cache.insert(address);
			l1Cache.insert(address);
			return prices[address];
		} else {
			l4Cache.insert(address);
			l3Cache.insert(address);
			l2Cache.insert(address);
			l1Cache.insert(address);
			return prices[address];
		}
	}
	
	private static void printHitMiss() {
		System.out.println("L1 hits: " + l1Cache.getHit() + ", " + "L1 misses: " + l1Cache.getMiss());
		System.out.println("L2 hits: " + l2Cache.getHit() + ", " + "L2 misses: " + l2Cache.getMiss());
		System.out.println("L3 hits: " + l3Cache.getHit() + ", " + "L3 misses: " + l3Cache.getMiss());
		System.out.println("L3 hits: " + l4Cache.getHit() + ", " + "L3 misses: " + l4Cache.getMiss());
		
	}
}