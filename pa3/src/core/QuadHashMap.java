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
    final int kx = ix(key);
    if (!absent(kx)) return;
    buckets[kx] = new Entry<>(key, val, key.hashCode() % buckets.length, kx);
    if (++size > buckets.length / 2) rehash();
  }

  public void del(K key) {
    final int kx = ix(key);
    if (absent(kx)) return;
    buckets[kx].deleted = true;
    size--;
  }

  public Entry<K, V>[] getBuckets() {
    return buckets;
  }

  private int ix(K key) {
    System.out.println(key.hashCode());

    int pos = key.hashCode() % buckets.length;
    int off = 1;
    System.out.println(buckets.length);
    System.out.println(pos);

    while(buckets[pos] != null && !buckets[pos].key.equals(key)) {
      pos += off;
      off += 2;
      if (pos >= buckets.length) pos -= buckets.length;
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
    buckets = new Entry[nextSize()];
    for (int ix : range(old.length)) {
      if (!absent(old, ix)) put(old[ix].key, old[ix].val);
    }
  }

  private int nextSize() {
    int sz = (int) (size / loadFactor);
    int cap = 1;
    while (cap < sz) cap <<= 1;
    while (!isPrime(cap++)) {}
    return cap;
  }

  private boolean isPrime(int num) {
    if (num <= 1) return false;
    if (num <= 3) return true;
    if (num % 2 == 0 || num % 3 == 0) return false;
    for (int i = 5; i * i <= num; i += 6) {
      if (num % i == 0 || num % (i + 2) == 0) return false;
    }
    return true;
  }

  public int getSize() {
    return size;
  }


}
