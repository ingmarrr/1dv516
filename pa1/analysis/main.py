import matplotlib.pyplot as plt
import os
import numpy as np
import sys

import data as d
import view as v
import consts


def main():
    path = sys.argv[1]

    match path:
        case "all":
            all()
        case "uf":
            qf()
            wqf()
        case "ts":
            rs_cache_i()
            brute()
            # cache()
            cache_i()
            ts_brute_vs_cache_vs_two_p()
            # twoP()
        
        case "qf":
            qf()
        case "wqf":
            wqf()
        case "cache":
            cache()
        case "cacheI":
            cache_i()
        case "twoP":
            twoP()
        case _:
            print("Invalid argument")


def qf() -> None:
    qfdata = d.load_csv(consts.QF_BENCH_CSV)
    v.plot(qfdata, "QUnionFind", "Size", "Time in Ms", consts.QF_BENCH)

def wqf() -> None:
    wqfdata = d.load_csv(consts.WQF_BENCH_CSV)
    v.plot(wqfdata, "QWUnionFind", "Size", "Time in Ms", consts.WQF_BENCH)
    wqfdata = d.load_csv("wqf_bench.csv")
    v.plot(wqfdata, "QWUnionFind", "Size", "Time in Ms", consts.WQF_BENCH + "_replaced")
    # v.plot_scale_y(wqfdata, "QWUnionFind", "Size", "Time in Ms", "wqf/scaled_duration_per_size", 100)
    # v.plot(wqfdata, "QWUnionFind", "Size", "Mean", "wqf/mean_per_size")

def brute() -> None:
    brute = d.load_csv(consts.BRUTE_BENCH_CSV)
    v.plot(brute, "Brute", "Size", "Mean in Ms", "brute_ms")

def cache() -> None:
    cache = d.load_csv(consts.CACHE_BENCH_CSV)
    v.plot_mean(cache, "Cache", "Size", "Mean in Ms", "cache_ms")

def cache_i() -> None:
    cache_i_15k = d.load_csv(consts.CACHEI_15K_BENCH_CSV)
    v.plot_mean(cache_i_15k, "CacheI", "Size", "Mean in Ms", "cache_i_ms")
    cache_i50k = d.load_csv("cache_i_50k_bench.csv")
    v.plot_mean(cache_i50k, "CacheI", "Size", "Mean in Ms", "cache_i_ms_50k")


def ts_brute_vs_cache_vs_two_p() -> None:
    cache = np.array([row for row in d.load_csv("cache_15k_bench.csv") if row[0] < 4000])
    cache_i = np.array([row for row in d.load_csv(consts.CACHEI_15K_BENCH_CSV) if row[0] < 4000])
    two_p = np.array([row for row in d.load_csv(consts.TWOP_BENCH_CSV) if row[0] < 3700])
    v.plot_multiple({"cache": cache, "cache_i": cache_i, "two_p": two_p}, "Brute vs TwoP vs Cache", "Size", "Mean in Ms", "comparison", True)


def rs_cache_i() -> None:
    rs_brute = d.load_data("threesum_rs/brute.csv")
    v.plot_mean(rs_brute, "Rust Brute", "Size", "Mean in Ms", "cache_brute_rs")
    rs_cache = d.load_data("threesum_rs/data.csv")
    v.plot_mean(rs_cache, "Rust CacheI", "Size", "Mean in Ms", "cache_i_rs")
    rs_cache = d.load_data("threesum_rs/data_200k.csv")
    v.plot_mean(rs_cache, "Rust CacheI 200k", "Size", "Mean in Ms", "cache_i_rs_200k")
    rs_cache = d.load_data("threesum_rs/data_50k_seed100.csv")
    v.plot_mean(rs_cache, "Rust CacheI Seed 1000", "Size", "Mean in Ms", "cache_i_rs_s1000")

def twoP() -> None:
    two_p = d.load_csv(consts.TWOP_BENCH_CSV)
    two_p_secs = d.load_csv(consts.TWOP_BENCH_SECS_CSV)
    v.plot_mean(two_p, "TwoP", "Size", "Mean in Ms", "two_p_ms")
    v.plot_mean(two_p_secs, "TwoP", "Size", "Mean in Secs", "two_p_secs")


def all() -> None:
    files = os.listdir(consts.DATA_DIR)
    for file in sorted(files):
        data = d.load_csv(file)
        name: str
        if file.__contains__("secs"):
            name = "Secs"
        else:
            name = "Ms"
        v.plot(data, file, "Size", f"Duration in {name}", file[:-4])
    
if __name__ == "__main__":
    main()
