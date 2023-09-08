import numpy as np
from dataclasses import dataclass

DATA_DIR = "./logs/"


@dataclass
class Row:
    size: int
    time: float


def load_csv(path: str) -> np.ndarray:
    print("Loading data from: " + DATA_DIR + path)
    return np.genfromtxt(DATA_DIR + path, delimiter=";", skip_header=1)


if __name__ == "__main__":
    print("This is the data module. Dont run it directly.")
    pass

    match path:
        case "qf":
            qfdata = d.load_csv("qfres.csv")
            v.plot(qfdata, "QUnionFind", "Size", "Time in Ms", "qfres")
            v.plot_scale_y(qfdata, "QUnionFind", "Size", "Time in Ms", "qfres", 100)
        case "wqf":
            wqfdata = d.load_csv("wqfres.csv")
            v.plot(wqfdata, "WQUnionFind", "Size", "Time in Ms", "wqfres")
        case _:
            print("Invalid argument passed to script")
