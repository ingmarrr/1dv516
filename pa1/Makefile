# Defining the Java compiler and flags
JAVAC = javac
JFLAGS = -d bin -sourcepath src

# Source files
SOURCES = $(shell find . -name '*.java')
TESTS = $(shell find ./tests -name '*.java')
BENCHS = $(shell find ./benchmarks -name '*.java')
OS := $(shell uname)

# Default target
all: build

# ==========================
# JAVA COMMANDS
# ==========================

build: clean
	@mkdir -p bin
	@$(JAVAC) $(JFLAGS) $(SOURCES)


clean:
	@if [ -d bin ]; then rm -rf bin; fi

# ==========================
# JAVA BENCHMARKING COMMANDS
# ==========================

bench: bench_uf bench_ts

bench_uf: build
	@java -cp bin benchmarks.UfBench

bench_clean_wqf:
	@python3.11 analysis/clean_wqf.py

bench_ts: build
	@java -cp bin benchmarks.TsBench

bench_pc: build
	@java -cp bin benchmarks.PercolationBench

# ==========================
# JAVA TESTING COMMANDS
# ==========================

test: test_uf test_ts

test_uf: build
	@java -cp bin tests.UfTest

test_ts: build
	@java -cp bin tests.TsTest

test_pc: build
	@java -cp bin tests.PercolationTest

# ==========================
# PYTHON COMMANDS
# ==========================

fit:
	python3.11 analysis/fit.py 

view:
	@python3.11 analysis/main.py all

view_ts:
	@python3.11 analysis/main.py ts

view_uf:
	@python3.11 analysis/main.py uf

view_qf:
	@python3.11 analysis/main.py qf

view_wqf:
	@python3.11 analysis/main.py wqf

# ==========================
# PYTHON ENV SETUP COMMANDS
# ==========================

pysetup: pyenv pyinstall pyactivate

pyenv: 
	@python3.11 -m venv .venv 

pyinstall:
	@pip install -r requirements.txt

pyactivate:
ifeq ($(OS),Darwin)
	. .venv/bin/activate
else ifeq ($(OS),Linux)
	. .venv/bin/activate
else
	.\venv_name\Scripts\activate
endif
