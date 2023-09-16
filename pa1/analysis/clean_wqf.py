
import data

def main():
    dat = data.load_csv("wqf_bench.csv")
    tpls = [(int(d[0]), d[1], d[2], int(d[3])) for d in dat]
    with open("bench_results/wqf_bench_cleanup.csv", "w") as f:
        f.write("size,duration,mean,reps\n")
        for tpl in tpls:
            if tpl[2] < 0.000035:
                f.write(f"{tpl[0]};{tpl[1]};{tpl[2]};{tpl[3]}\n")

if __name__ == '__main__':
    main()