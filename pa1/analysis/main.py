import matplotlib.pyplot as plt
import numpy as np
import sys

import data as d
import view as v
import consts


def main():
    path = sys.argv[1]

    match path:
        case "all":
            qf()
            wqf()
            cache()
            cacheI()
            twoP()

        case "uf":
            qf()
            wqf()
        case "ts":
            cache()
            cacheI()
            twoP()
        
        case "qf":
            qf()
        case "wqf":
            wqf()
        case "cache":
            cache()
        case "cacheI":
            cacheI()
        case "twoP":
            twoP()
        case _:
            print("Invalid argument")


def qf() -> None:
    qfdata = d.load_csv(consts.QFRES_CSV)
    v.plot(qfdata, "QUnionFind", "Size", "Time in Ms", "qfres")


def wqf() -> None:
    wqfdata = d.load_csv(consts.WQFRES_CSV)
    # size; duration; mean; repeat
    dat: list[d.Row] = [ d.Row( row[0], row[1], row[2], row[3]) for row in wqfdata ]
    v.plot(wqfdata, "QWUnionFind", "Size", "Time in Ms", "wqf/duration_per_size")
    v.plot_scale_y(wqfdata, "QWUnionFind", "Size", "Time in Ms", "wqf/scaled_duration_per_size", 100)
    v.plot(wqfdata, "QWUnionFind", "Size", "Mean", "wqf/mean_per_size")

def cache() -> None:
    cache = d.load_csv(consts.CACHE_CSV)
    v.plot(cache, "Cache", "Size", "Time in Ms", "cache")

def cacheI() -> None:
    cacheI = d.load_csv(consts.CACHEI_CSV)
    v.plot(cacheI, "CacheI", "Size", "Time in Ms", "cacheI")

def twoP() -> None:
    twoP = d.load_csv(consts.TWOP_CSV)
    v.plot(twoP, "TwoP", "Size", "Time in Ms", "twoP")

if __name__ == "__main__":
    main()
