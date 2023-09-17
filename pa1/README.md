
# 1DV516 Programming Assignment 1

To be able to run everything, I provided a `Makefile` that exposes commands to run all the tests, benchmarks, visualizations, etc.

## Running The Tests

```bash
make test

# Union Find Tests
make test_uf

# Threesum Tests
make test_ts

# Percolation Tests
make test_pc
```

## Running The Benchmarks

```bash
make bench

# Union Find Benchmarks
make bench_uf

# Threesum Benchmarks
make bench_ts

# Percolation Benchmarks, not really benchmarks, but the calculation of pStar
make bench_pc
```

## Python Environment

Before running the visualisations and fits, make sure that the python environment is set up correctly. You can use `make pyactivate` to activate the environment.

If it does not exist, you can create it with `make pyenv`, followed by `make pyinstall` to install the dependencies and follow that up with `make pyactivate`.

Otherwise you can also run all of those commands together with `make pysetup`.

## Running The Visualizations

```bash
make view

# Union Find Visualizations
make view_uf

# Threesum Visualizations
make view_ts
```

## Running The Fitting with Scipy

```bash
make fit
```
