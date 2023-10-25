import matplotlib.pyplot as plt
import numpy as np
import os

import consts

DATA_DIR = consts.MERGE_DIR


def load_csv(path: str) -> np.ndarray:
    print("Loading data from: " + DATA_DIR + path)
    return np.genfromtxt(DATA_DIR + "/" + path, delimiter=";", skip_header=1)


def read_file(path: str) -> list[str]:
    with open(path, "r") as f:
        return f.readlines()


def read_dir(path: str) -> list[tuple[str, np.ndarray]]:
    out: list[tuple[str, np.ndarray]] = []
    for file in os.listdir(path):
        out.append((file, load_csv(file)))

    return out


def plot(
    data: np.ndarray,
    title: str,
    xlabel: str,
    ylabel: str,
    path: str,
    show: bool = False,
) -> None:
    plt.figure(figsize=(10, 5))
    plt.plot(data[:, 0], data[:, 1], "-", label="data")
    plt.title(title)
    plt.xlabel(xlabel)
    plt.ylabel(ylabel)
    plt.legend()
    if not os.path.exists(consts.VIEW_DIR):
        os.makedirs(consts.VIEW_DIR)
    plt.savefig(consts.VIEW_DIR + "/" + path + ".png")
    if consts.SHOW or show:
        plt.show()
    plt.close()


def plot_multiple(
    data: dict[str, np.ndarray],
    title: str,
    xlabel: str,
    ylabel: str,
    path: str,
    show: bool = False,
) -> None:
    plt.figure(figsize=(10, 5))
    for d in data:
        plt.plot(data[d][:, 0], data[d][:, 1], "-", label=d)
    plt.title(title)
    plt.xlabel(xlabel)
    plt.ylabel(ylabel)
    plt.legend()
    if consts.SHOW or show:
        if not os.path.exists(consts.VIEW_DIR):
            os.makedirs(consts.VIEW_DIR)
        plt.savefig(consts.VIEW_DIR + "/" + path + ".png")
        plt.show()


def main():
    # base = "merge_bench_results"
    # base = "bench_results"
    data = read_dir(DATA_DIR)
    ms(data)


def qs(data):
    so = sorted(data, key=lambda x: x[0])
    heap = []
    insert = []
    for name, ls in so:
        if "heap" in name:
            heap.append((name, ls))
        else:
            insert.append((name, ls))

    for (hname, hdata), (iname, idata) in zip(heap, insert):
        plot_multiple(
            {hname: hdata, iname: idata},
            "heap vs insert",
            "size",
            "duration in ms",
            hname + "_vs_" + iname,
        )


def ms(data):
    dic = dict(data)
    plot_multiple(dic, "iterative vs recursive", "size", "duration in ms", "itr_vs_rec")
    for name, file in data:
        plot(file, name, "size", "duration in ms", name)


if __name__ == "__main__":
    main()
