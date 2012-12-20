##################################################
# Makefile for building lib and main test routine#
##################################################
# operating system
SYSTEM = $(shell uname -s)

CXX            = g++
CC             = gcc

#SHARED_CXXFLAGS += -O2
SHARED_CXXFLAGS	+= -g

CXXFLAGS = $(SHARED_CXXFLAGS) -Wall -Wpointer-arith -Wcast-qual -D_REENTRANT -fPIC
CPPFLAGS = -fPIC -D_GNU_SOURCE
CXXFLAGS += -D_GNU_SOURCE -O0

#XERCES_HOME 	= /usr/local/xerces-c-src_2_7_0
#XERCES_LIB_DIR		= ${XERCES_HOME}/lib
#XERCES_LIB 	= xerces-c2_7_0

#XALAN_HOME      = /usr/local/xalan-c_1_10_0
#XALAN_LIB_DIR   = $(XALAN_HOME)/lib

include xml-deps.mk

##### Set this to the location of the library to be produced

BUILD_LIB_DIR = lib
BUILD_OBJ_DIR = build

###### Include Paths

INCLUDE_DIR = -I . -I $(XERCES_INC_DIR) -I $(XALAN_INC_DIR)

build_obj_dir = $(BUILD_OBJ_DIR)

src_dirs_cpp = se se/secDNS common session xml
src_dirs_c = config
srcs_all = $(foreach dir,$(src_dirs_cpp),$(wildcard $(dir)/*.cpp))
srcs_all += $(foreach dir,$(src_dirs_c),$(wildcard $(dir)/*.c))

srcs = $(filter-out %Test.cpp,$(srcs_all))
objs = $(foreach file,$(srcs),$(build_obj_dir)/$(basename $(notdir $(file))).o)
test_srcs = $(filter %Test.cpp,$(srcs_all))
test_objs = $(foreach file,$(test_srcs),$(build_obj_dir)/$(basename $(notdir $(file))).o)
test_execs = $(subst .o,,$(test_objs))

calc_deps = \
	$(CC) -MT '$(build_obj_dir)/$(basename $(notdir $@)).o $@' -MF $@ -MM $(CPPFLAGS) $(INCLUDE_DIR) $<

all: dirs $(objs) $(BUILD_LIB_DIR)/libAusreg_EPP_toolkit.so

%.d: %.c
	$(calc_deps)
%.d: %.cpp
	$(calc_deps)
include $(foreach name,$(srcs_all),$(basename $(name)).d)

vpath %.cpp ./ \
			./se\
			./se/secDNS\
			./xml\
			./common\
			./session\

vpath %.c ./config

vpath %.o 	../build

####### Implicit rules

.SUFFIXES: .cpp .c

$(BUILD_OBJ_DIR)/%.o: %.cpp
	$(CXX) $(CXXFLAGS) -c -o $@ $(INCLUDE_DIR) $<

$(BUILD_OBJ_DIR)/%.o: %.c
	$(CC) $(CPPFLAGS) -c -o $@ $(INCLUDE_DIR) $<


LDFLAGS_TESTS = -L$(BUILD_LIB_DIR) -lAusreg_EPP_toolkit \
				-L$(XALAN_LIB_DIR) -lxalan-c -lxalanMsg\
				-L$(XERCES_LIB_DIR) -lxerces-c \
				-lssl \
				-lrt

$(BUILD_OBJ_DIR)/%: $(BUILD_OBJ_DIR)/%.o
	$(CXX) $(LDFLAGS_TESTS) -o $@ $<

.PHONY: doc clean dclean
####### Build rules

#libAusreg_EPP_toolkit.a: $(OBJECTS)
#	$(LIB_ARCHIVER) $(LIB_FLAGS) $(BUILD_LIB_DIR)/$@ $^


.PHONY: tests
tests: all $(test_execs)


.PHONY: dirs
dirs:
	-mkdir -p $(BUILD_LIB_DIR)
	-mkdir -p $(BUILD_OBJ_DIR)
	
$(BUILD_LIB_DIR)/libAusreg_EPP_toolkit.so: $(objs)
	$(CXX) $(LDFLAGS) $(SHARED_CXXFLAGS) -shared $^ -o $@

doc:
	doxygen etc/Doxyfile

clean:
	$(RM) $(objs) $(BUILD_LIB_DIR)/libAusreg_EPP_toolkit.so *~
	$(RM) -r $(BUILD_OBJ_DIR)
	$(RM) -r $(BUILD_LIB_DIR)

dclean:
	$(MAKE) clean
	$(RM) */*.d

