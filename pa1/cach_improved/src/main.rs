use std::{collections::HashSet, fs::File, io::Write, sync::Mutex};

use rand::Rng;
use rayon::prelude::{IntoParallelIterator, ParallelIterator};

fn main() {
    run()
}

fn run() {
    let step = 20;
    let upper = 10_000;
    let reps = 50;

    let mut file = std::fs::OpenOptions::new()
        .append(true)
        .open("data.csv")
        .unwrap();

    file.write_all("size;duration;mean;reps\n".as_bytes())
        .expect("Unable to write data");
    st(&mut file, upper, step, reps)
}

#[allow(dead_code)]
fn st(f: &mut File, upper: isize, step: usize, reps: usize) {
    for i in (0..upper).step_by(step) {
        let inp = (0..i)
            .map(|_| rand::thread_rng().gen_range(-10..10))
            .collect::<Vec<_>>();
        let mut durs = Vec::new();
        for _ in 0..reps {
            let start = std::time::Instant::now();
            _cache_i(&inp);
            let dur = start.elapsed();
            durs.push(dur.as_nanos());
        }
        let duration = durs.iter().sum::<u128>() / 1e6 as u128;
        let mean = duration as f64 / reps as f64;
        println!(
            "SIZE :: {}, DURATION :: {}, MEAN :: {}, REPS :: {}",
            i, duration, mean, reps
        );
        f.write_all(format!("{};{};{};{}\n", i, duration, mean, reps).as_bytes())
            .expect("Unable to write data");
    }
}

#[allow(dead_code)]
fn mt(f: &mut File, upper: usize, step: usize, reps: usize) {
    for size in (0..upper).step_by(step) {
        let inp = (0..size)
            .map(|_| rand::thread_rng().gen_range(-10..10))
            .collect::<Vec<_>>();
        let durations = std::sync::Arc::new(Mutex::new(Vec::<u128>::new()));
        let num_threads = if reps > 10 { 10 } else { reps };
        let reps_per_thread = if reps > 10 { reps / 10 } else { 1 };
        (0..num_threads).into_par_iter().for_each(|_| {
            for _ in 0..reps_per_thread {
                let start = std::time::Instant::now();
                _cache_i(&inp);
                let duration = start.elapsed();
                durations.lock().unwrap().push(duration.as_nanos());
            }
        });
        let durs = durations.lock().unwrap().to_vec();
        let duration = durs.iter().sum::<u128>() / 1e6 as u128;
        let mean = duration as f64 / reps as f64;
        println!(
            "SIZE :: {}, DURATION :: {}, MEAN :: {}, REPS :: {}",
            size, duration, mean, reps
        );
        f.write_all(format!("{};{};{};{}\n", size, duration, mean, reps).as_bytes())
            .expect("Unable to write data");
    }
}

fn _cache_i(input: &Vec<isize>) {
    let mut cache = HashSet::new();
    let mut out = Vec::new();
    for a in input {
        for b in &cache {
            let c: isize = -(a + b);
            if cache.contains(&c) {
                out.push(vec![*b, c, *a]);
            }
        }
        cache.insert(*a);
    }
}

struct Triple {
    _a: isize,
    _b: isize,
    _c: isize,
}

fn threesum(input: &[isize]) -> Vec<Triple> {
    let mut out = Vec::new();
    let len = input.len();
    for i in 0..len {
        for j in 0..len {
            for k in 0..len {
                if i == j || i == k || j == k {
                    continue;
                }
                if input[i] + input[j] + input[k] == 0 {
                    out.push(Triple {
                        _a: input[i],
                        _b: input[j],
                        _c: input[k],
                    });
                }
            }
        }
    }
    out
}
