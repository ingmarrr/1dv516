import matplotlib.pyplot as plt
import numpy as np
import os

import consts


def plot(data: np.ndarray, title: str, xlabel: str, ylabel: str, path: str) -> None:
    plt.figure(figsize=(10, 5))
    plt.plot(data[:, 0], data[:, 1], "o-", label="data")
    plt.title(title)
    plt.xlabel(xlabel)
    plt.ylabel(ylabel)
    plt.legend()
    if not os.path.exists(consts.PLOT_DIR):
        os.makedirs(consts.PLOT_DIR)
    plt.savefig(consts.PLOT_DIR + path + ".png")
    plt.show()
    plt.close()


def plot_scale_y(
    data: np.ndarray, title: str, xlabel: str, ylabel: str, path: str, yupper: int
) -> None:
    plt.figure(figsize=(10, 5))
    plt.plot(data[:, 0], data[:, 1], "o-", label="data")
    plt.ylim([0, yupper])
    plt.title(title)
    plt.xlabel(xlabel)
    plt.ylabel(ylabel)
    plt.legend()
    if not os.path.exists(consts.PLOT_DIR):
        os.makedirs(consts.PLOT_DIR)
    plt.savefig(consts.PLOT_DIR + path + ".png")
    plt.show()
    plt.close()


def plot_fit(x: np.ndarray, y: np.ndarray, params: list[float], fn: callable) -> None:
    plt.scatter(x, y, label='Data', color='red')
    plt.plot(x, fn(x, *params), label='Fit', color='blue')
    plt.xlabel('Size')
    plt.ylabel('Duration')
    plt.legend()
    plt.show()


if __name__ == "__main__":
    print("This is the plot module. Dont run it directly.")
    pass
