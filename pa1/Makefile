# Defining the Java compiler and flags
JAVAC = javac
JFLAGS = -d bin -sourcepath src

# Source files
SOURCES = $(shell find . -name '*.java')
TESTS = $(shell find ./tests -name '*.java')
OS := $(shell uname)

# Default target
all: build

# ==========================
# JAVA COMMANDS
# ==========================

build: clean
	@mkdir -p bin
	@$(JAVAC) $(JFLAGS) $(SOURCES)

bench: build
	@java -cp bin src.benchmark.Bench

clean:
	@if [ -d bin ]; then rm -rf bin; fi

# ==========================
# JAVA TESTING COMMANDS
# ==========================

test: test_uf test_ts

test_uf: build
	@java -cp bin tests.UfTest

test_ts: build
	@java -cp bin tests.TsTest

# ==========================
# PYTHON COMMANDS
# ==========================

fit:
	python3.11 analysis/fit.py 

view: view_qf view_wqf

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
	@. .venv/bin/activate
else
	@.\venv_name\Scripts\activate
endif
