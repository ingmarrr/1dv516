# Defining the Java compiler and flags
JAVAC = javac
JFLAGS = -d bin -sourcepath src

# Source files
SOURCES = $(shell find src -name '*.java')

# Default target
all: build

# Compile all sources
build: 
	@mkdir -p bin
	@$(JAVAC) $(JFLAGS) $(SOURCES)

p1:
	@java -cp bin src.p1.P1

bench:
	@java -cp bin src.benchmark.Bench

clean:
	@if [ -d bin ]; then rm -rf bin; fi
