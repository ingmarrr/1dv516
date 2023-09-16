
import sys
import random as rand
import numpy as np

import data

def all(dat: np.ndarray) -> float:
    out = []
    for row in dat:
        out.append((int(row[0]), row[2]))
    avg_slope = 0
    for i in range(1, len(out)):
        avg_slope += (out[i][1] - out[i - 1][1]) / (out[i][0] - out[i - 1][0])
    avg_slope /= len(out)
    return avg_slope

def main():
    path = "qf_union_bench.csv"
    dat = data.load_csv(path)
    # first_rand = rand.randint(0, len(dat) - 1)
    # if first_rand > len(dat) - 100:
    #     print("first_rand too large")
    #     return
    # second_rand = first_rand + 100
    # print("Sizes: ", first_rand, second_rand)
    # first = dat[first_rand]
    # second = dat[second_rand]
    # print("Results: ", (int(first[0]), first[2]), (int(second[0]), second[2]))
    # slope = (second[2] - first[2]) / (second[0] - first[0])
    # print(slope)
    avg_slope = all(dat)
    print(avg_slope)

if __name__ == '__main__':
    main()