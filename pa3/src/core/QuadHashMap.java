package core;

import java.util.Optional;

import static java.util.Optional.of;
import static java.util.Optional.empty;

import static range.Range.range;

public class QuadHashMap<K extends Comparable<K>, V> {
  private static final int initCap = 16;
  private Entry<K, V>[] buckets;
  private int size = 0;

  public QuadHashMap() {
    buckets = new Entry[initCap];
  }

  public QuadHashMap(int initCap) {
    buckets = new Entry[initCap];
  }

  static class Entry<K, V> {
    public K key;
    public V val;
    public boolean deleted;

    public Entry(K k, V v) {
      key = k;
      val = v;
      deleted = false;
    }
  }

  public Optional<V> get(K key) {
    final int kx = ix(key);
    return absent(kx) ? empty() : of(buckets[kx].val);
  }

  public void put(K key, V val) {
    final int kx = ix(key);
    if (!absent(kx)) return;
    buckets[kx] = new Entry<>(key, val);
    if (++size > buckets.length / 2) rehash();
  }

  public void del(K key) {
    final int kx = ix(key);
    if (absent(kx)) return;
    buckets[kx].deleted = true;
    size--;
  }

  private int ix(K key) {
    int pos = key.hashCode() % buckets.length;
    int off = 1;

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
    buckets = new Entry[nextPrime(2 * buckets.length)];
    for (int ix : range(old.length)) {
      if (!absent(old, ix)) put(old[ix].key, old[ix].val);
    }
  }

  private int nextPrime(int n) {
    while (!isPrime(n++)) {}
    return n;
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
