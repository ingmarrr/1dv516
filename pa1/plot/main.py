import matplotlib.pyplot as plt
import numpy as np
import sys

import data as d
import view as v


def main():
    # Get the first
    path = sys.argv[1]

    match path:
        case "qf":
            qf()
        case "wqf":
            wqf()
        case _:
            print("Invalid argument")


def qf() -> None:
    qfdata = d.load_csv("qfres.csv")
    v.plot(qfdata, "QUnionFind", "Size", "Time in Ms", "qfres")


def wqf() -> None:
    qwfdata = d.load_csv("qwfres.csv")
    v.plot(qwfdata, "QWUnionFind", "Size", "Time in Ms", "qwfres")
    v.plot_scale_y(qwfdata, "QWUnionFind", "Size", "Time in Ms", "qwfres", 100)


if __name__ == "__main__":
    main()
