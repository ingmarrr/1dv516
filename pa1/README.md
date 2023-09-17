
# 1DV516 Programming Assignment 1

To be able to run everything, I provided a `Makefile` that exposes commands to run all the tests, benchmarks, visualizations, etc.

## Building The Project

All the java commands rely on build so it should not be necessary to run this command, but if you want to, you can run `make build` to build the project. To clean any build artifacts, you can run `make clean`.

## Running The Tests

I dont recommend running all the tests together since there is a lot of output and that might be a bit overwhelming. So I recommend to run them individually. I will probably try and improve that for the next submission but for this one it is still a lot, also because there a quite a few tests.

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

Running all benchmarks is going to take a couple of minutes, so I recommend to, if you
as the person correcting this want to run all benchmarks, to run start them before you start correcting in order to save some time. And then once they are finished you can have a look at them. Otherwise you can run them individually.

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
