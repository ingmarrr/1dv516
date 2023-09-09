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

