# Defining the Java compiler and flags
JAVAC = javac
JFLAGS = -d bin -sourcepath src

# Source files
SOURCES = $(shell find src -name '*.java')
OS := $(shell uname)

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

view:
	@python3.11 plot/main.py

view_qf:
	@python3.11 plot/main.py qf

view_wqf:
	@python3.11 plot/main.py wqf

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
