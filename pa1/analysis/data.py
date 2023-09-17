import numpy as np
from dataclasses import dataclass

import consts


def load_csv(path: str) -> np.ndarray:
    print("Loading data from: " + consts.DATA_DIR + path)
    return np.genfromtxt(consts.DATA_DIR + path, delimiter=";", skip_header=1)

def load_data(path: str) -> np.ndarray:
    return np.genfromtxt(path, delimiter=";", skip_header=1)

if __name__ == "__main__":
    print("This is the data module. Dont run it directly.")
    pass

