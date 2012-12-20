#!/bin/sh

CP=

for jar in lib/*
do
	if [ "$CP" = "" ]
	then
		CP=$jar
	else
		CP=${CP}:$jar
	fi
done

if [ "$CLASSPATH" = "" ]
then
	export CLASSPATH=${CP}
else
	export CLASSPATH=${CLASSPATH}:${CP}
fi

