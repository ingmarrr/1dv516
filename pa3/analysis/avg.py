
import os
import sys


def data(path: str) -> tuple[list[tuple[str, str, float]], list[tuple[str, str, float]]]:
    
    files = os.listdir(path)
    heap = []
    insert = []

    for file in files:
        with open(path + "/" + file, "r") as f:

            lines = f.readlines()
            depth = 0
            mean = 1000
            for ix in range(1, len(lines)):
                tmp = float(lines[ix].split(";")[2].strip())
                if tmp < mean:
                    mean = tmp
                    depth = int(lines[ix].split(";")[0].strip())
            
            if file.__contains__("heap"):
                heap.append((file, depth, mean))
            else:
                insert.append((file, depth, mean))

    return sorted(insert, key=lambda t: t[0]), sorted(heap, key=lambda t: t[0])



def main() -> None:

    insert, heap = data("bench_results")
    
    for (i, h) in zip(insert, heap):
        print(str(i) + "\t ::\t" + str(h))


if __name__ == "__main__":
    main()
