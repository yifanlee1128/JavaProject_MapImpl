package Assignment8;

import java.util.Iterator;

/**
 * A map that holds primitive integers as keys and object as values.
 *
 * <p>Implementations of this interface are based on open-addressing and do not use box-unboxing
 * or traditiona {@code Node}.
 */
public interface IntObjectMap<V> {

    /**
     * Adding the specified key and the corresponding value to the map.
     *
     * @param key the key to add to the map
     * @param value the value corresponding to the key
     * @return the previous value assoczaited with the key or null
     * @throws NullPointerException if {@code value} is null or the key is the integer
     * representing the null element
     */
    V put(int key, V value);

    /**
     * Returns the value corresponding to the specified key.
     *
     * @param key the key
     * @return the value corresponding to the specified key or null otherwise
     */
    V get(int key);

    /**
     * Removes and returns the value corresponding to the specified key.
     *
     * @param key the key
     * @return the value corresponding to the key or null
     */
    V remove(int key);

    /**
     * Returns the number of elements in the map.
     *
     * @return the number of elements in the map
     */
    int size();

    /**
     * Removes all the elements from this map.
     */
    void clear();
}

