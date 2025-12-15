public class MainMemory extends Memory{
    private int hit = 0;
    private int miss = 0;
    private int capacity = 4096;

    public MainMemory() {
        super(4096);
    }

    public int getHit() {

        return this.hit;
    }

    public int getMiss() {
        return this.miss;
    }

    public int getTotalAttempt(){
        return this.hit+this.miss;
    }

    public boolean lookUp(int address) throws Exception {
        int index = address % this.capacity;
        int tag = address / this.capacity;

        CacheLine existingLine = this.read(index);

        if (existingLine == null || !existingLine.isValid()) {  // valid가 false면 비어있음
            this.miss++;
            return false;
        }

        if (existingLine.getTag() == tag) {
            this.hit++;
            return true;
        } else {
            this.miss++;
            return false;
        }
    }


    public void insert(int address) throws Exception {
        int index = address % this.capacity;
        int tag = address / this.capacity;

        CacheLine line = this.read(index);
        if (line == null) line = new CacheLine(); // read가 null일 수 있다면

        line.setTrueValid();
        line.setTag(tag);
        line.setBlock(address);

        this.write(index, line);
    }


}
