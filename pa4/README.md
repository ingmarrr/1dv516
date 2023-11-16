
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

## Running the File Tree

For all the problems and my implementations, there are a lot of tests in the test folder that can be run with a simple `gradle test` command.

For the File Tree problem, there is a main class that can be run with the following command:

- Build the project with `gradle build`
- Install it with gradle `./gradlew installDist`
- Run the main class with `build/install/pa2/bin/pa2 <path-to-file>`

To shortcut all of this, I created a simple `bash` script that can be run with `./run.sh <path-to-file>`. You might have to first run `chmod +x run.sh` to make it executable.

## Run the Dijkstra vs Bellman-Ford benchmark

To run the Benchmark run:

```bash
gradle dvb
```

## Running the tests

To test any implementations, simply run `gradle test`