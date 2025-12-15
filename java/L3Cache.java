
public class L3Cache {
	private int hit = 0; 
	private int miss = 0;
	private int capacity = 128;
	private int ways = 2;
	
	//2way 라서 CacheLine 2차원 배열 선언 
	CacheLine[][] lines = new CacheLine[capacity][ways];
	
	//lines 의 특정한 인덱스 내에서 두 way 중 어느 way 가 죽을지 기록해둔 명부 
	int[] toBeKilled = new int[capacity];
	
	// CacheLine 총 128 * 2 개 선언 
	public L3Cache() {
		for (int i = 0; i < capacity; i++) {
			for (int j = 0; j < ways ; j++) {
				lines[i][j] = new CacheLine();
			}
		}
	}
	
	// Credits to @MelonChicken 
	public int getHit() {
		return this.hit;
	}
	
	public int getMiss() {
		return this.miss;
	}
	
	public int getTotalAttempt() {
		return this.hit + this.miss;		
	}
	
	// lines 내에서 0 방향 way 부터 탐색하고, 이후 1 방향 way 탐색 
	public boolean lookUp(int address) throws Exception{
		int index = address % this.capacity;
		int tag = address / this.capacity;
		
		for (int i = 0; i < ways; i++) {
			CacheLine line = lines[index][i];
			if (line.isValid() && line.getTag() == tag) {
				hit++;
				return true;
			}
		}
		
		miss++;
		return false;
	}
	
	/* lines 내에서 0 방향 way 부터 탐색하고, 이후 1 방향 way 탐색. 
	 * 그런데 way 가 둘 다 비어있는 경우, 하나만 비어있는 경우, 아무것도 안 비어있는 경우로 나뉨.
	 * 2. 비어있는 way 가 있을 경우: 한 방향이라도 line 이 존재하게 되었으니 setTrueValid() 하고 말그대로 그냥 넣음. (Credits to @MelonChicken)  
	 * 3. 두 쪽 다 만석인 경우 -> toBeKilled 죽이고 새로 들어오는 line 으로 대체. 
	 */
	public void insert(int address) throws Exception{
		int index = address % this.capacity;
		int tag = address / this.capacity;
		
		// 1. 이미 set 내에 동일한 line 존재하는 경우 -> 안 쓰게 됨
		/*
		for (int i = 0; i < ways; i++) {
			CacheLine line = lines[capacity][i];
			if (line.isValid() && line.getTag() == tag) {
				line.setBlock(address);
				toBeKilled[index] = 1 - i;
				return;
			}
		}
		*/
		
		// 2. 비어있는 way가 하나라도 존재하는 경우 (way 다 비어있으면 0방향 부터 채움) 
		for (int i = 0; i < ways; i++) {
			CacheLine line = lines[index][i];
			if (!line.isValid()) {
				line.setTrueValid();
				line.setTag(tag);
				line.setBlock(address);
				toBeKilled[index] = 1 - i;
				return;
			}
		}
		
		// 3. 두 쪽 다 만석인 경우 -> toBeKilled 를 처분한다. 
		int killTarget = toBeKilled[index];
		CacheLine line = lines[index][killTarget];
		line.setTrueValid();
		line.setTag(tag);
		line.setBlock(address);
		toBeKilled[index] = 1 - killTarget;	
	}
	
	
}
