SHELL=/bin/bash

BIN_DIR := bin
SOURCE_DIR := .
PACKAGE=a01a/e1
SOURCES := $(wildcard $(SOURCE_DIR)/*.java)
CLASSES := $(SOURCES:$(SOURCE_DIR)/%.java=$(BIN_DIR)/%.class)
DEPENDECIES=$(shell ls . | egrep -wv "Test.java|makefile|bin")

JFLAGS := -d $(BIN_DIR)

all:		${BIN_DIR}/SubsequenceFactoryImpl.class

${BIN_DIR}/SubsequenceFactoryImpl.class: 
	@mkdir -p $(BIN_DIR)
	javac $(JFLAGS) ${DEPENDECIES} 

clean:
	rm -rf ${BIN_DIR}
	
.PHONY: all clean