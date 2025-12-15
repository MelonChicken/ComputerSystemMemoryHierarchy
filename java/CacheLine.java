import java.util.*;

public class CacheLine {
	
	/*
	 * Line 의 구조 클래스 
	 */
	
	Boolean valid;
	int tag;
	int block;
	
	public CacheLine() {
		this.valid = false; // 비어있음 초기에 
		this.tag = 0;  
		this.block = 0; 
	}
	
	// Valid 한가?
	public boolean isValid() { 
		return valid; 
	}
	
	// Valid true 로 수정 
	public void setTrueValid() {
		this.valid = true;
	}
	
	// Valid false 로 수정 
	public void setFalseValid() {
		this.valid = false;
	}
	
	// tag 가져오기 
	public int getTag() { 
		return tag; 
	}
	
	// tag 수정하기 
	public void setTag(int numTag) {
		this.tag = numTag;
	}
	
	// block 가져오기 
	public int getBlock() {
		return block;
	}
	
	// block 설정하기 
	public void setBlock(int blockNum) {
		this.block = blockNum;
	}

	
	// 태그가 동일한가?   
	public boolean isSameTag(int tag) {
		if (this.tag == tag) {
			return true;
		}
		return false;
	}
}
