# Defining the Java compiler and flags
JAVAC = javac
JFLAGS = -d bin -sourcepath src

# Source files
SOURCES = $(shell find . -name '*.java')
# SOURCES = $(wildcard *.java)

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

bench: 
	echo "Unimplemented"

# ==========================
# JAVA TESTING COMMANDS
# ==========================

test: 
	echo "Unimplemented"

# ==========================
# PYTHON COMMANDS
# ==========================

fit:
	python3.11 analysis/fit.py 

view:
	@python3.11 analysis/view.py all

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
