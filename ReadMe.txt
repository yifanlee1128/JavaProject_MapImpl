In the file "MyIntObjMapImp.java",I implement all the methods in the interface and 5 fields and create other two methods:

Fields:

int[] _keys      is used to store the keys
Object[] _values is used to store the values
int[] _counts    is used to store the number of the keys with same hash code
int _capacity    is used to store the capacity of the map
int _size        is used to store the number of key-value pairs in the map

Methods:

1.Constructor

instantiate the fields:

_capacity=32;
_size=0;
_keys is filled with the minimum value of integer as null
_counts is filled with 0
_values is filled with null

2.clear

reset _size to 0
_keys is filled with the minimum value of integer as null
_counts is filled with 0
_values is filled with null

3.size

return the value of _size

4.get

return the value corresponding to the key：
If the key is not in the map, return null.
If the key is in the map, return the corresponding value.

5.locateKey

This method is use to found the corresponding index of a key in the map:
if it exists in the map, the method returns the index;
if it does not exist in the map, the method returns -1.

6.rehashingMap

This method implements the rehashing of the map.

If the number of elements is more than 1/2 of backing array length, it create new backing array and for any elements in the old backing array insert it in the right place in the new backing arrays.

7.put

put the key-value pair into the map

Throw exception if key value or the key is illegal

We have three circumstances:
i:no key with the same hash code in the map, then we put the pair in the right place and return null
ii:some keys with same hash code are in the map, but not the same key, then we find an empty place for this pair and return null
iii:same key in the map, then we repalce the old value with new value, then return the old value 

8.remove

remove a key-value pair in the map and return the removed value

We have three circumstances:
i: no such key in the map, then return null
ii:no other key-value pair with same hash code in the map, then just return the value
iii:some key-value pairs with same hash code in the map, then move the remaining key-value pair with the same hash code to the previous location, finally return the value