import os
import sys
from typing import Callable
import scipy.optimize as opt
import matplotlib.pyplot as plt
import numpy as np
import markdown as md

import data
import view
import consts


def log(x: int, a: int, b: int) -> int:
    return a * np.log(x) + b


def power(x: int, a: int, b: int) -> int:
    return a * x ** b


def linear(x: int, a: int, b: int) -> int:
    return a * x + b


def quadratic(x: int, a: int, b: int, c: int) -> int:
    return a * x ** 2 + b * x + c


def cubic(x: int, a: int, b: int, c: int, d: int) -> int:
    return a * x ** 3 + b * x ** 2 + c * x + d

def writef(path: str, content: str) -> None:
    with open(path, "w") as f:
        f.write(content + "\n")


def fit(path: str, name: str, x: np.ndarray, y: np.ndarray, func: Callable) -> tuple[np.ndarray, np.ndarray]:
    out = f"\n# {name} Fit\n\n"
    if name == "Linear":
        popt_linear, _ = opt.curve_fit(linear, x, y)
        a, k = popt_linear
        out += f"a (x coefficient) = {a}\n"
        out += f"b (constant term) = {k}\n"
        out += f"y = {a:.4f}x + {k:.4f}"
        print(out)
        writef(consts.CALCS_DIR + path + ".md", out)
    if name == "Quadratic":
        popt_quad, _ = opt.curve_fit(quadratic, x, y)
        a, k, c = popt_quad
        out += f"a (x^2 coefficient) = {a}\n"
        out += f"b (x coefficient) = {k}\n"
        out += f"c (constant term) = {c}\n"
        out += f"y = {a:.4f}x^2 + {k:.4f}x + {c:.4f}"
        print(out)
        writef(consts.CALCS_DIR + path + ".md", out)
    if name == "Cubic":
        popt_cubic, _ = opt.curve_fit(cubic, x, y)
        a, k, c, d = popt_cubic
        out += f"a (x^3 coefficient) = {a}\n"
        out += f"b (x^2 coefficient) = {k}\n"
        out += f"c (x coefficient) = {c}\n"
        out += f"d (constant term) = {d}\n"
        out += f"y = {a:.4f}x^3 + {k:.4f}x^2 + {c:.4f}x + {d:.4f}"
        print(out)
        writef(consts.CALCS_DIR + path + ".md", out)
    if name == "Power":
        popt_power, _ = opt.curve_fit(power, x, y)
        a, k = popt_power
        out += f"a (coefficient) = {a}\n"
        out += f"b (exponent) = {k}\n"
        out += f"y = {a:.4f}x^{k:.4f}"
        print(out)
        writef(consts.CALCS_DIR + path + ".md", out)
    if name == "Log":
        popt_log, _ = opt.curve_fit(log, x, y)
        a, k = popt_log
        out += f"a (coefficient) = {a}\n"
        out += f"b (constant) = {k}\n"
        out += f"y = {a:.4f}log(x) + {k:.4f}"
        print(out)
        writef(consts.CALCS_DIR + path + ".md", out)

    popt, pcov = opt.curve_fit(func, x, y)

    return popt, pcov

def all() -> None:
    if not os.path.exists(consts.FIT_DIR):
        os.makedirs(consts.FIT_DIR)
    files = os.listdir(consts.DATA_DIR)
    fit_files(files)

def fit_files(files: list[str]) -> None:
    for file in sorted(files):
        if file.__contains__("secs"):
            continue
        print("\033[H\033[J")
        dat = data.load_csv(file)
        file = file.split(".")[0]
        x, y = dat[:, 0], dat[:, 1]
        file_linear = "_linear"
        file_quadratic = "_quadratic"
        file_cubic = "_cubic"
        file_power = "_power"
        file_log = "_log"
        if not os.path.exists(consts.FIT_DIR + file):
            os.makedirs(consts.FIT_DIR + file)
        dir = file + "/"
        if not os.path.exists(consts.CALCS_DIR + dir):
            os.makedirs(consts.CALCS_DIR + dir)
        popt_power, _ = fit(dir + file_power, "Power", x, y, power)
        popt_linear, _ = fit(dir + file_linear, "Linear", x, y, linear)
        popt_quadratic, _ = fit(dir + file_quadratic, "Quadratic", x, y, quadratic)
        popt_cubic, _ = fit(dir + file_cubic, "Cubic", x, y, cubic)
        popt_log, _ = fit(dir + file_log, "Log", x, y, log)
        view.plot_fit(dir + file_power, x, y, popt_power, power)
        view.plot_fit(dir + file_linear, x, y, popt_linear, linear)
        view.plot_fit(dir + file_quadratic, x, y, popt_quadratic, quadratic)
        view.plot_fit(dir + file_cubic, x, y, popt_cubic, cubic)
        view.plot_fit(dir + file_log, x, y, popt_log, log)


def main():
    if len(sys.argv) == 2:
        path = sys.argv[1]
        fit_files([path])
        return

    all()


if __name__ == "__main__":
    main()
