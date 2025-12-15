import java.util.*;

class Memory{
    private CacheLine[] sets;

    public Memory(int setNumber){
        this.sets = new CacheLine[setNumber];
    }

    /**
     * @return CacheLine[] sets
     *
     * 현재 메모리의 전체 sets를 리턴합니다.
     */
    public CacheLine[] getSets(){
        return this.sets;
    }


    /**
     * @param line : 현재 넣으려는 데이터 값
     * @param tag : 현재 sets 배열에 넣을 위치
     */
    public void setSets(int tag, CacheLine line) throws Exception{

        if(this.sets.length < tag){
            System.err.println("Error: set number out of range");
            return;
        }

        this.sets[tag] = line;
    }

    /**
     * @param tag : 현재 sets 배열에서 확인하려는 위치
     * @return 존재하면 ? CachLine : null ;
     * 현재 메모리에 저장된 sets에서 특정 tag값을 저장합니다.
     * 2-way set association을 고려하지 않았습니다.
     */
    public CacheLine read(int tag){

        if(this.sets.length < tag){
            System.err.println("Error: set number out of range");
        }

        if(this.sets[tag] == null){
            return null;
        } else {
            return this.sets[tag];
        }
    }

    /**
     * @param line : 삽입하려는 데이터
     * 저장하려는 데이터의 유효성을 검사한 뒤 현재 메모리에 저장된 sets에서 값을 저장합니다.
     *             이때 값이 이미 해당 tag에 존재한다면 quadratic probing을 활용합니다.
     * 2-way set association을 고려하지 않았습니다.
     */
    public void write(CacheLine line) throws Exception {
        if(!line.isValid()){
            System.err.println("Invalid CacheLine");
            return;
        }

        setSets(line.getTag(), line);
    }
}