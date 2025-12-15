# Introduction
Guidline
<img width="1662" height="988" alt="image" src="https://github.com/user-attachments/assets/ac3d3b69-b3de-4366-b596-fc9ace8e9d0a" />

- You can use any programming languages (e.g., C/C++, java, or python) only if they support arrays

- Design your own memory hierachy along with 4 layers where each one can be represented by an array. That is, you can sugguest four kinds of arrays having different number of elements: 

1) L1(1 element)

2) L2(16 elements)

3) L3(256 elements)
4) L4(4096 elements)
 Here, the number of elements in each layer can be changed considering the used data. 

- Choose the real-world data to store them to the 4-layer memory hierachy and collect them. The data needs to be larger than the biggest array. (i.e., larger than 4096).

- Validate the memory hierachy by storing the data to the memory hierachy. 

- Apply E-way set associative cache (E=2) to L3 layer. 

- Show the cache hit ratio for a given data set based on your own real-world scenario. 

 

CheckList (source codes must include these items; they and their results are needed to be explained in the report; the score portion for each item will be varied according to the relative difficulty)

- [ ] The designed memory hierachy consists of 4 layers to show the effectiveness of memory hierachy considering the used real-world data. 

- [ ] The real-world data is large enough so that only some of them can be maintained in the memory hierachy. 
 
- [ ] E-way set associate cache works correctly. This should be shown using an actual example of the real-world data in your report. 

- [ ] The cache hit ratio is correctly calculated. This should be shown by the report based on the real-world scenario on the data.

# Project Structure

```
ComputerSystemData/
├─ .idea/
├─ java/
│  └─ Main.java
├─ python/
│  ├─ .idea/
│  ├─ .venv/
│  ├─ data/
│  │  └─ data.csv
│  ├─ data_loader.py
│  └─ requirements.txt
├─ .gitignore
```

* `python/` 디렉터리에서 데이터 다운로드 및 전처리
* `java/` 디렉터리에서 메모리 계층 시뮬레이션 구현
