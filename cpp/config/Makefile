
CC = gcc

CUNIT_INCLUDE = ~/CUnit-2.1-0/include/

INCLUDE_DIR = -I ${CUNIT_INCLUDE}

LIBOBJS = boolean.o \
		  config.o \
		  log.o

OBJS = $(LIBOBJS) test_config.o


LIBS = ~/CUnit-2.1-0/lib/libcunit.a

DEPS = boolean.h config.h log.h

%.o: %.c $(DEPS)	
	$(CC) -c $< $(CFLAGS) $(INCLUDE_DIR)

libConfig.so: $(LIBOBJS)
	$(CC) -o $@ $^ -shared

test: $(OBJS)
	$(CC) -o $@ $^ $(CFLAGS) $(LIBS)


