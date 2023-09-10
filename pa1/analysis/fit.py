from typing import Callable
import scipy.optimize as opt
import matplotlib.pyplot as plt
import numpy as np

import data
import view
import consts

def quadratic(x: int, a: int, b: int, c: int) -> int:
    return a*x**2 + b*x + c

def linear(x: int, a: int, b: int) -> int:
    return a*x + b

def fit(x: np.ndarray, y: np.ndarray, func: Callable) -> tuple[np.ndarray, np.ndarray]:
    popt, pcov = opt.curve_fit(func, x, y)
    return popt, pcov

def main():
    dat = data.load_csv(consts.QF_BENCH_CSV)
    x, y = dat[:,0], dat[:,1]
    popt, _ = fit(x, y, quadratic)
    view.plot_fit(x, y, popt, quadratic)
    dat = data.load_csv(consts.WQF_BENCH_CSV)
    x, y = dat[:,0], dat[:,1]
    popt, _ = fit(x, y, linear)
    view.plot_fit(x, y, popt, linear)

if __name__ == '__main__':
    main()