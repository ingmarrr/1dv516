use std::collections::HashSet;

use criterion::{black_box, criterion_group, criterion_main, Criterion};
use rand::Rng;

fn bench_three_sum(c: &mut Criterion) {
    let mut rng = rand::thread_rng();

    let sizes = (500..=50000).collect::<Vec<_>>();

    for &size in &sizes {
        c.bench_function(&format!("three_sum_{}", size), |b| {
            b.iter(|| {
                cache_i(black_box(
                    (0..size).map(|_| rng.gen_range(-10000..10000)).collect(),
                ))
            })
        });
    }
}

criterion_group!(benches, bench_three_sum);
criterion_main!(benches);

fn cache_i(input: Vec<isize>) {
    let mut cache = HashSet::new();
    let mut out = Vec::new();
    for a in input {
        for b in &cache {
            let c: isize = -(a + b);
            if cache.contains(&c) {
                out.push(vec![*b, c, a]);
            }
        }
        cache.insert(a);
    }
}
