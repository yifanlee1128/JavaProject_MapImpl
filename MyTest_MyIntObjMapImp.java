package Assignment8;

public class MyTest_MyIntObjMapImp extends junit.framework.TestCase {

	/*
	* This is the test for the constructor,
	* when we instantiate a new map, the size should be 0,
	* anything we get or remove from the map should be null,
	* and we cannot locate any key in this map.
	* */
	public void test_constructor() {

		MyIntObjMapImp<Integer> map = new MyIntObjMapImp<>();
		assertTrue(map.size()==0);
		assertTrue(map.get(1)==null);
		assertTrue(map.locateKey(1)==-1);
		assertTrue(map.remove(1)==null);
		
	}

	/*
	* This is the test for the "clear" method,
	* after we use the clear method, the map should be like a new one
	* */
	public void test_clear(){
		MyIntObjMapImp<Integer> map = new MyIntObjMapImp<>();
		map.put(1,1);
		map.put(2,2);
		map.put(3,3);
		map.clear();
		assertTrue(map.size()==0);
		assertTrue(map.get(1)==null);
		assertTrue(map.locateKey(1)==-1);
		assertTrue(map.remove(1)==null);
	}

	/*
	* This is the test for "size" method,
	* in the for loop, the increment of size should be 1.
	* */
	public void test_size(){
		MyIntObjMapImp<Integer> map = new MyIntObjMapImp<>();
		for (int i=0;i<10;i++) {
			map.put(i,i);
			assertTrue(map.size()==i+1);
		}
	}

	/*
	* The is the test for the "get" method
	* */
	public void test_get() {

		MyIntObjMapImp<Integer> map = new MyIntObjMapImp<>();
		map.put(1,1);
		map.put(2,2);
		map.put(3,3);
		map.put(1,4);// the value 4 will cover the former value 1
		map.put(3,5);// the value 5 will cover the former value 3
		map.put(4,6);// new key-value pair

		assertTrue(map.get(1)==4);
		assertTrue(map.get(2)==2);
		assertTrue(map.get(3)==5);
		assertTrue(map.get(4)==6);
		assertTrue(map.get(5)==null);// no key 5 in the map, the return value should be null


	}

	/*
	* This is the test for the "locateKey" method,
	* */
	public void test_locateKey() {

		MyIntObjMapImp<Integer> map = new MyIntObjMapImp<>();
		map.put(1,1);
		map.put(2,2);
		map.put(3,3);
		map.put(3,3);// key 3 is already in the map, this will not increase the size of the map
		
		assertTrue(map.size()==3);
		
		// The keys 1,2,3 are in the map.
		assertTrue(map.locateKey(1)==1);
		assertTrue(map.locateKey(2)==2);
		assertTrue(map.locateKey(3)==3);
		
		// The key 4 and 5 are not in the map.
		assertTrue(map.locateKey(4)==-1);
		assertTrue(map.locateKey(5)==-1);
		
	}
	/*
	* This is the test for the "rehashingMap" method,
	* */
	public void test_rehashingMap() {

		MyIntObjMapImp<Integer> map = new MyIntObjMapImp<>();
		for (int i=0;i<20;i++) {
			map.put(i,i+1);//when we insert the 17th into the map, the rehashingMap method will be called
		}
		
		assertTrue(map.size()==20);
		for (int j=0;j<20;j++) {
			assertTrue(map.locateKey(j)==j);
		}
		
	}

	/*
	* This is the test for the "put" method,
	* */
	public void test_put() {

		MyIntObjMapImp<Integer> map = new MyIntObjMapImp<>();

		map.put(1,1);
		map.put(2,2);
		map.put(33,33);// 33 has same hashCode(1) with 1
		map.put(34,34);// 34 has same hashCode(2) with 2
		assertTrue(map.size()==4);// check the size

		assertTrue(map.locateKey(1)==1);
		assertTrue(map.locateKey(2)==2);
		assertTrue(map.locateKey(33)==3);
		assertTrue(map.locateKey(34)==4);

		assertTrue(map.put(1,3)==1);// return the previous value 1
		assertTrue(map.put(2,4)==2);// return the previous value 2
		assertTrue(map.put(33,35)==33);// return the previous value 33
		assertTrue(map.put(3,36)==null); // key 3 is not in the map, return value should be null
		
	}

	/*
	* This is the test for the "remove" method,
	* */
	public void test_remove() {

		MyIntObjMapImp<Integer> map = new MyIntObjMapImp<>();
		map.put(1,1);
		map.put(2,2);
		map.put(3,3);
		
		map.put(33,33);// 33 has same hashCode(1) with 1
		map.put(34,34);// 34 has same hashCode(2) with 2
		map.put(35,35);// 35 has same hashCode(3) with 3
		
		map.put(65,65);// 65 has same hashCode(1) with 1
		map.put(66,66);// 66 has same hashCode(2) with 2
		map.put(67,67);// 67 has same hashCode(3) with 3
		
		assertTrue(map.size()==9);// check the size
		


		assertTrue(map.remove(1)==1);// return the value
		assertTrue(map.remove(2)==2);// return the value
		assertTrue(map.remove(3)==3);// return the value
		assertTrue(map.remove(4)==null);// key 4 is not in the map, the return value should be null
		assertTrue(map.remove(5)==null);// key 5 is not in the map, the return value should be null


		assertTrue(map.size() == 6);// check the size
		assertTrue(map.locateKey(33) == 1); // since we remove the pair (1,1), the pair(33,33) will be moved to previous location
		assertTrue(map.locateKey(34) == 2); // since we remove the pair (2,2), the pair(34,34) will be moved to previous location
		assertTrue(map.locateKey(35) == 3); // since we remove the pair (3,3), the pair(35,35) will be moved to previous location
		assertTrue(map.locateKey(65) == 4); // since pair(33,33) is moved, the pair(65,65) will replace its location with idx 4
		assertTrue(map.locateKey(66) == 5); // since pair(34,34) is moved, the pair(66,66) will replace its location with idx 5
		assertTrue(map.locateKey(67) == 6); // since pair(35,35) is moved, the pair(67,67) will replace its location with idx 6

		assertTrue(map.remove(33)==33); // return the value
		assertTrue(map.remove(34)==34); // return the value
		assertTrue(map.remove(35)==35); // return the value
		assertTrue(map.remove(1)==null); // key 1 is not in the map, the return value should be null
		assertTrue(map.remove(2)==null); // key 2 is not in the map, the return value should be null
		assertTrue(map.remove(3)==null); // key 3 is not in the map, the return value should be null

		assertTrue(map.size() == 3); // check the size
		assertTrue(map.locateKey(65) == 1); // since we remove the pair (33,33), the pair(65,65) will be moved to previous location with idx 1
		assertTrue(map.locateKey(66) == 2); // since we remove the pair (34,34), the pair(66,66) will be moved to previous location with idx 2
		assertTrue(map.locateKey(67) == 3); // since we remove the pair (35,35), the pair(67,67) will be moved to previous location with idx 3
	}	
		
}
