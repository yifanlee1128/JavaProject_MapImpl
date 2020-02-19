package Assignment8;

public class MyIntObjMapImp<V> implements IntObjectMap<V>{

    /*
    * field "_keys" is used to store the keys
    * field "_values" is used to store the values
    * field "_counts" is used to store the number of the keys with same hash code
    * field "_capacity" is used to store the capacity of the map
    * field "_size" is used to store the number of key-value pairs in the map
    * */
    private int[] _keys;
    private Object[] _values;
    private int[] _counts;
    private int _capacity;
    private int _size;

    //constructor
    public MyIntObjMapImp(){
        _capacity=32;
        _size=0;
        _keys=new int[_capacity];
        _counts=new int[_capacity];
        _values=new Object[_capacity];
        for(int i=0;i<_capacity;i++){
            _keys[i]=Integer.MIN_VALUE;// use the minimum value of integer as null
            _counts[i]=0;
            _values[i]=null;// filled with null
        }
    }

    @Override
    public void clear(){
        _size=0;
        for(int i=0;i<_capacity;i++){
            _keys[i]=Integer.MIN_VALUE;// refill the array of key with the minimum value of integer
            _counts[i]=0;
            _values[i]=null;
        }
    }

    @Override
    public int size(){
        return _size;
    }

    @Override
    public V get(int key){
        int hash=key%_capacity;
        int numElements=_counts[hash];
        int idx=hash;// idx is used in the loop
        if(numElements==0) return null;// no such key in the map
        else if(numElements==1) {// only one key with same hash code in the map
            if (_keys[hash]==key) return (V)_values[hash];// exactly the same key
            else return null;// not the same key
        }

        idx=(++idx)%_capacity; //increment by 1
        while(idx!=hash){
            if(_keys[idx]==Integer.MIN_VALUE){ //empty
                idx=(++idx)%_capacity;
                continue;
            }
            else if((_keys[idx]%_capacity)==hash && _keys[idx]==key) return (V)_values[idx];//exactly the same key
            else if((_keys[idx]%_capacity)==hash){// not the same key, but has the same hash code
                --numElements;
                if(numElements==0) break; // has traversed all elements with the same hash code, then quit
                idx=(++idx)%_capacity;
                continue;
            }
            else {idx=(++idx)%_capacity;}
        }
        return null;//nothing found, return null
    }

    /*
    * This method is use to found the corresponding index of a key in the map,
    * if it exists in the map, the method returns the index;
    * if it does not exist in the map, the method returns -1.
    * */
    public int locateKey(int key){
        int hash=key%_capacity;
        int numElements=_counts[hash];
        int idx=hash;// idx is used in the loop
        if(numElements==0) return -1;// no such key in the map
        else if(numElements==1) {// only one key with same hash code in the map
            if(_keys[idx]==key)return idx;// exactly the same key
            else return -1;// not the same key
        }
        if(_keys[idx]==key) return idx;
        idx=(++idx)%_capacity; //increment by 1
        while(idx!=hash){
            if(_keys[idx]==Integer.MIN_VALUE){//empty
                idx=(++idx)%_capacity;
                continue;
            }
            else if((_keys[idx]%_capacity)==hash && _keys[idx]==key) return idx; //exactly the same key
            else if((_keys[idx]%_capacity)==hash){// not the same key, but has the same hash code
                --numElements;
                if(numElements==0) break;// has traversed all elements with the same hash code, then quit
                idx=(++idx)%_capacity;
                continue;
            }
            else {idx=(++idx)%_capacity;}
        }
        return -1;//nothing found, return null
    }


    /*
    * This method implements the rehashing of the map.
    * If the number of elements is more than 1/2 of backing array length, it create new backing array
    * and for any elements in the old backing array insert it in the right place in the new backing arrays.
    * */
    public void rehashingMap(){
        int newCapacity=_capacity*2; //double the capacity
        _size=0;
        int[] oldKeys=_keys; //used to store the keys
        Object[] oldValues=_values; // used to store the values

        //create new arrays
        _keys=new int[newCapacity];
        _values=new Object[newCapacity];
        _counts=new int[newCapacity];
        for(int i=0;i<newCapacity;i++){
            _keys[i]=Integer.MIN_VALUE;
            _values[i]=null;
            _counts[i]=0;
        }
        _capacity=newCapacity;
        for(int j=0;j<oldKeys.length;j++){
            if(oldKeys[j]!=Integer.MIN_VALUE) put(oldKeys[j],(V)oldValues[j]);//put the pairs into the new arrays.
        }

    }
    @Override
    public V put(int key,V value) throws NullPointerException{
        if(key==Integer.MIN_VALUE) throw new NullPointerException("key cannot be NULL!");// throw exception if key is illegal
        if(value==null) throw new NullPointerException("value cannot be NULL!");// throw exception if value is illegal
        if((_size+1)>(_capacity/2)){ //rehashing the map when meeting the requirement
            rehashingMap();
        }
        int idx=key%_capacity;
        if(_keys[idx]==Integer.MIN_VALUE){ //no key with the same hash code in the map
            _keys[idx] = key;
            _values[idx] = value;
            _counts[idx]++;
            _size++;
            return null;
        }
        else if(locateKey(key)==-1){// some keys with same hash code are in the map, but not the same key
            _counts[idx]++;
            _size++;
            while(_keys[idx]!=Integer.MIN_VALUE) idx=(++idx)%_capacity; // find an empty place
            _keys[idx]=key;
            _values[idx]=value;
            return null;
        }
        else{ // same key in the map
            int newIdx=locateKey(key);
            V oldValue = (V)_values[newIdx];
            _values[newIdx] = value; //replace the old value with the new value
            return oldValue;
        }

    }

    @Override
    public V remove(int key){
        if(locateKey(key)==-1) return null;//the key is not in the map
        int location=locateKey(key);
        int hashCode=key%_capacity;
        V removeValue=(V)_values[location];

        //remove the pair
        _values[location]=null;
        _keys[location]=Integer.MIN_VALUE;
        _counts[hashCode]--;
        _size--;

        if(_counts[hashCode]==0) return removeValue;//no other key-value pair with same hash code in the map
        else{
            // move the remaining key-value pair with the same hash code to the previous location
            int maxIteration=_counts[hashCode];// the number of remaining key-value pairs with the same hash code we need to move
            int lastLocation=location;
            for(int i=location+1;i<_capacity;i++){

                //find the next key-value pair with the same hash code, then exchange the values
                if((_keys[i]%_capacity)!=hashCode) continue;
                else{
                    int tempKey=_keys[i];
                    Object tempValue=_values[i];
                    _keys[i]=_keys[lastLocation];
                    _values[i]=_values[lastLocation];
                    _keys[lastLocation]=tempKey;
                    _values[lastLocation]=tempValue;
                    lastLocation=i;
                    maxIteration--;
                    if(maxIteration<=0) break;//has traversed all elements with the same hash code, then quit
                }
            }
            return removeValue;
        }

    }



}
