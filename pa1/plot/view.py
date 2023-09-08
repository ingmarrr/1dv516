import matplotlib.pyplot as plt
import numpy as np
import os


def plot(data: np.ndarray, title: str, xlabel: str, ylabel: str, path: str) -> None:
    plt.figure(figsize=(10, 5))
    plt.plot(data[:, 0], data[:, 1], "o-", label="data")
    plt.title(title)
    plt.xlabel(xlabel)
    plt.ylabel(ylabel)
    plt.legend()
    if not os.path.exists("./plots/"):
        os.makedirs("./plots/")
    plt.savefig("./plots/" + path + ".png")
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
    if not os.path.exists("./plots/"):
        os.makedirs("./plots/")
    plt.savefig("./plots/" + path + ".png")
    plt.show()
    plt.close()


if __name__ == "__main__":
    print("This is the plot module. Dont run it directly.")
    pass
