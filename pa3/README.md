
# Running the code

## Prerequisites

### ! Do this before running anything

First, you need to build the `jtuil` library. To do so, go into the `jutil` project, run the following commands:

```bash
gradle build
```

The main submission relies on the project being installed in `mavenLocal`, so to publish it to your local maven repository, run:

```bash
./gradlew publishToMavenLocal
```

## Running the tests

There are tests for most of the problems, which can be run with `gradle test`. The tests are located in the `src/test` directory.

## Running The Benchmarks

To run the benchmarks for `Merge`-, `Quick`-, and `ShellSort`, as well as the `Vehicle` problem, run the following command:

- `gradle merge`
- `gradle quick`
- `gradle shell`
- `gradle vehicle`
