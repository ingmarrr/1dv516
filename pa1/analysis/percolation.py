
import data

def main() -> None:
    dat = [(row[0], row[1], row[2]) for row in data.load_csv("percolation.csv")]
    mean = sum([row[1] for row in dat]) / len(dat)
    mean_stddev = sum([row[2] for row in dat]) / len(dat)
    print(f"mean: {mean:.5f}")
    print(f"mean stddev: {mean_stddev:.5f}")


if __name__ == '__main__':
    main()