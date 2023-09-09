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
        case "qf":
            qf()
        case "wqf":
            wqf()
        case _:
            print("Invalid argument")


def qf() -> None:
    qfdata = d.load_csv(consts.QFRES_CSV)
    v.plot(qfdata, "QUnionFind", "Size", "Time in Ms", "qfres")


def wqf() -> None:
    qwfdata = d.load_csv(consts.WQFRES_CSV)
    v.plot(qwfdata, "QWUnionFind", "Size", "Time in Ms", "qwfres")
    v.plot_scale_y(qwfdata, "QWUnionFind", "Size", "Time in Ms", "qwfres", 100)


if __name__ == "__main__":
    main()
