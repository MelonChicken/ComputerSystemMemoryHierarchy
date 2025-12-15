public class L2Cache extends Memory {

    private int hit = 0;
    private int miss = 0;
    private final int capacity = 16; // L2 Cache 용량은 16개

    /**
     * L2Cache 생성자: 상위 클래스 Memory에 용량 16을 전달합니다.
     */
    public L2Cache() {
        super(16);
        // Direct-Mapped이므로, 모든 CacheLine을 미리 초기화합니다.
        // (Memory 클래스의 read/write가 null을 처리하지 않는다면 필요)
        
    }

    // --- 통계 메서드 ---

    public int getHit() {
        return this.hit;
    }

    public int getMiss() {
        return this.miss;
    }

    public int getTotalAttempt() {
        return this.hit + this.miss;
    }

    // --- L2 동작 정의 ---

    /**
     * L2 캐시에서 주어진 address의 데이터를 탐색합니다. (lookup)
     * 주소 매핑: Index = address % 16, Tag = address / 16
     * @param address 탐색할 주소
     * @return Hit 시 true, Miss 시 false
     */
    public boolean lookUp(int address) throws Exception {
        int tag = address / this.capacity;   // tag = address / 16

        // 상위 클래스 Memory의 read(index)를 사용하여 CacheLine을 가져옵니다.
        CacheLine existingLine = this.read(address);

        // 1. 해당 index에 라인이 없거나 (read가 null 반환) 유효하지 않으면 (비어있음)
        if (existingLine == null || !existingLine.isValid()) {
            this.miss++;
            // System.out.println("[L2 MISS] Address: " + address + ", Index: " + index + ", Tag: " + tag);
            return false;
        }

        // 2. 유효하며 Tag가 일치하면 (Hit)
        if (existingLine.getTag() == tag) {
            this.hit++;
            // System.out.println("[L2 HIT] Address: " + address + ", Index: " + index + ", Tag: " + tag);
            return true;
        }

        // 3. 유효하지만 Tag가 일치하지 않으면 (Conflict Miss)
        else {
            this.miss++;
            // System.out.println("[L2 MISS - Conflict] Address: " + address + ", Index: " + index + ", Tag: " + tag);
            return false;
        }
    }

    /**
     * L2 캐시에 데이터를 삽입/갱신합니다. (insert, Overwrite 방식)
     * L3 hit 후 promotion/fill 목적으로 사용됩니다.
     * @param address 삽입할 데이터의 주소
     */
    public void insert(int address) throws Exception {
        int index = address % this.capacity;
        int tag = address / this.capacity;

        // 상위 클래스 Memory의 read(index)를 사용하여 CacheLine을 가져옵니다.
        CacheLine line = this.read(address);

        // read가 null을 반환할 경우 새로운 CacheLine 객체 생성 (팀원의 MainMemory 로직과 동일)
        if (line == null) line = new CacheLine();

        // 데이터 갱신 (Overwrite)
        line.setTrueValid();
        line.setTag(tag);
        line.setBlock(address); // 과제 요구사항에 따라 address 자체가 데이터 블록 값을 의미한다고 가정

        // 상위 클래스 Memory의 write(index, line)를 사용하여 저장합니다.
        this.write(index, line);

        // System.out.println("[L2 INSERT] Address: " + address + ", Index: " + index + ", Tag: " + tag);
    }
}