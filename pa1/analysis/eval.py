
import data

import math
import sys

def main():
    step = 100_000
    path = "qf_union_bench.csv"
    if len(sys.argv) >= 3:
        try:
            step = int(sys.argv[2])
        except:
            if len(sys.argv) == 4:
                step = int(sys.argv[3])
            path = sys.argv[2]

    dat = data.load_csv(path)
    out = []
    table: list[tuple[int, int, float, float, float, float ,float, float]] = []
    for row in dat:
        if row[0] % step == 0:
            size = row[0]
            time = row[1]
            # mean = row[2]
            out.append((size,time))
    for ix, row in enumerate(out):
        if ix == 0:
            table.append((row[0], row[1], math.log2(row[1]), 0, 0, 0, 0, 0))
            continue
            
        size = row[0]
        time = row[1]
        prev_size = out[ix - 1][0]
        prev_time = out[ix - 1][1]
        ratio = time / prev_time
        log_ratio = math.log2(ratio)
        slope = (time - prev_time) / (size - prev_size)
        log_slope = (math.log2(time) - math.log2(prev_time)) / (math.log2(size) - math.log2(prev_size))
        c = math.log2(time) - log_slope * math.log2(size)
        table.append((
            int(size),
            time,
            math.log2(time),
            ratio,
            log_ratio,
            slope,
            log_slope,
            c
        ))

    with open(sys.argv[1], "w") as f:
        f.write("s &\tt &\tl2t &\tr &\tl2r &\ts &\tl2s &\tc\n")
        for row in table:
            f.write(f"{int(row[0])} &\t{row[1]:.2f} &\t{row[2]:.2f} &\t{row[3]:.2f} &\t{row[4]:.2f} &\t{row[5]:.4e} &\t{row[6]:.3f} &\t{row[7]:.2f}\n")
        # averages
        withoutfirst = table[1:]
        log2_time = sum([row[2] for row in withoutfirst]) / len(withoutfirst)
        ratio = sum([row[3] for row in withoutfirst]) / len(withoutfirst)
        log2_ratio = sum([row[4] for row in withoutfirst]) / len(withoutfirst)
        slope = sum([row[5] for row in withoutfirst]) / len(withoutfirst)
        log2_slope = sum([row[6] for row in withoutfirst]) / len(withoutfirst)
        c = sum([row[7] for row in withoutfirst]) / len(withoutfirst)
        f.write(f"average &\t& {log2_time:.2f} &\t{ratio:.2f} &\t{log2_ratio:.2f} &\t{slope:.2e} &\t{log2_slope:.3f} &\t{c:.2f}\n")
        def power_law(a: float, k: float, x: float) -> str:
            return f"{a}x^{k}"
        f.write(f"a (coefficient) : {2**c:.10f}\n")
        f.write(f"b (constant) : {log2_slope:.10f}\n")
        f.write(f"\npower law: {power_law(2**c, log2_slope, 2**log2_time)}\n")

        original_times = [row[1] for row in out]
        model_predictions = [2**c * row[0]**log2_slope for row in out]
        f.write("size & original & prediction & accuracy\n")
        for ix, row in enumerate(out):
            f.write(f"{int(row[0])} & {row[1]:.4f} & {model_predictions[ix]:.4f} & {original_times[ix] / model_predictions[ix]:.4f}\n")


        original_log2_times = [row[2] for row in table]
        model_log2_predictions = [log2_slope * math.log2(row[0]) + c for row in table]
        f.write("size & original & prediction & accuracy\n")
        for ix, row in enumerate(table):
            f.write(f"{int(row[0])} & {row[2]:.4f} & {model_log2_predictions[ix]:.4f} & {original_log2_times[ix] / model_log2_predictions[ix]:.4f}\n")

if __name__ == '__main__':
    main()