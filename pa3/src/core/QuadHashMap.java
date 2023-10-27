package core;

import java.util.Optional;

import static java.util.Optional.of;
import static java.util.Optional.empty;

import static range.Range.range;

public class QuadHashMap<K extends Comparable<K>, V> {
  private static final int initCap = 16;
  private Entry<K, V>[] buckets;
  private int size = 0;
  private double loadFactor = 0.7;

  public QuadHashMap() {
    buckets = new Entry[initCap];
  }

  public QuadHashMap(int initCap) {
    buckets = new Entry[initCap];
  }

  public static class Entry<K, V> {
    public K key;
    public V val;
    public int destIx;
    public int actualIx;
    public int collisions;
    private boolean deleted;

    public Entry(K k, V v, int dix, int aix) {
      key = k;
      val = v;
      destIx = dix;
      actualIx = aix;
      deleted = false;
    }

    @Override
    public String toString() {
      return "Key: " + key.toString()
          + " Val: " + val.toString()
          + " Destination Index: " + destIx
          + " Actual Index: " + actualIx
          + " Offset: " + Math.abs(actualIx - destIx);
    }
  }

  public Optional<V> get(K key) {
    final int kx = ix(key);
    return absent(kx) ? empty() : of(buckets[kx].val);
  }

  public void put(K key, V val) {
    int initialIndex = Math.abs(key.hashCode()) % buckets.length;
    if (buckets[initialIndex] != null) buckets[initialIndex].collisions++;
    final int kx = ix(key);
    if (!absent(kx)) return;
    buckets[kx] = new Entry<>(key, val, key.hashCode() % buckets.length, kx);
    if (++size > (buckets.length * loadFactor)) rehash();
  }

  public void del(K key) {
    final int kx = ix(key);
    if (absent(kx))
      return;
    buckets[kx].deleted = true;
    size--;
  }

  public Entry<K, V>[] getBuckets() {
    return buckets;
  }

  private int ix(K key) {

    int pos = Math.abs(key.hashCode()) % buckets.length;
    int off = 1;

    while (buckets[pos] != null && !buckets[pos].key.equals(key)) {
      pos = (pos + off * off) % buckets.length;
      off++;
    }

    return pos;
  }

  private boolean absent(int kx) {
    return buckets[kx] == null || buckets[kx].deleted;
  }

  private boolean absent(Entry<K, V>[] buckets, int kx) {
    return buckets[kx] == null || buckets[kx].deleted;
  }

  private void rehash() {
    final Entry<K, V>[] old = buckets;
    int newSize = nextSize(); // Calculate the new size based on the load factor
    buckets = new Entry[newSize];

    size = 0; // Reset size because 'put' method will increase it
    for (Entry<K, V> entry : old) {
      if (entry != null && !entry.deleted) {
        put(entry.key, entry.val);
      }
    }
  }

  private int nextSize() {
    int newSize = (int) (buckets.length / loadFactor);
    return nextPrime(newSize);
  }

  private int nextPrime(int num) {
    while (!isPrime(num)) {
      num++;
    }
    return num;
  }

  private boolean isPrime(int num) {
    if (num <= 1) return false;
    if (num == 2 || num == 3) return true;
    if (num % 2 == 0 || num % 3 == 0) return false;
    int sqrtNum = (int) Math.sqrt(num) + 1;
    for (int i = 5; i < sqrtNum; i += 6) {
      if (num % i == 0 || num % (i + 2) == 0) return false;
    }
    return true;
  }

  public int getSize() {
    return size;
  }

}
