A. Software Requirements
------------------------

1. GNU Make 3.80 or later
2. GCC 3.4.6 (for gcc and g++)
3. Xerces C++ 2.7.0
4. Xalan C++ 1.0
5. OpenSSL 0.9.7a or later
6. Doxygen 1.3.9.1 (required to produce API documentation)

Notes
The software library may build with an earlier GCC release, but has only been
verified on GCC 3.4.6.  It was developed on 64-bit Redhat Enterprise Linux ES
4 using 64-bit supporting libraries (Xerces, Xalan, OpenSSL).


B. Build Instructions
---------------------

1. Set the values of the following environment variables:
XALAN_LIB_DIR
XALAN_LIB
XALAN_INC_DIR
XERCES_LIB_DIR
XERCES_LIB
XERCES_INC_DIR
Examples:
$ XALAN_LIB_DIR=/usr/local/xalan-c/lib
$ XALAN_LIB=xalan-c
$ XALAN_INC_DIR=/usr/local/xalan-c/include
$ XERCES_LIB_DIR=/usr/lib
$ XERCES_LIB=xerces-c
$ XERCES_INC_DIR=/usr/include/xercesc
$ export XALAN_LIB_DIR XALAN_LIB XALAN_INC_DIR XERCES_LIB_DIR XERCES_LIB XERCES_INC_DIR

2. Verify version of tools.
$ make --version$ make --version
GNU Make 3.80
Copyright (C) 2002  Free Software Foundation, Inc.
This is free software; see the source for copying conditions.
There is NO warranty; not even for MERCHANTABILITY or FITNESS FOR A
PARTICULAR PURPOSE.

$ gcc --version
gcc (GCC) 3.4.6 20060404 (Red Hat 3.4.6-8)
Copyright (C) 2006 Free Software Foundation, Inc.
This is free software; see the source for copying conditions.  There is NO
warranty; not even for MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

$ openssl version
OpenSSL 0.9.7a Feb 19 2003

$ doxygen --version
1.3.9.1

2. Unpack the toolkit source (one of the following):
> tar xf cpptk2_release.tar
> tar zxf cpptk2_release.tar.gz
> tar jxf cpptk2_release.tar.bz2

3. Build the C++ Toolkit shared library:
> make

4. The previous command will leave object files in the bin directory and, most
importantly, the shared object file (libAusreg_EPP_toolkit.so) in the lib
directory.


C. Usage Information and Advice
-------------------------------

1. When using the toolkit, the LD_LIBRARY_PATH should contain the lib
directory, or else the libAusreg_EPP_toolkit.so file shold be placed in a
system library directory, or the linker should otherwise be made aware of the
location of the libAusreg_EPP_toolkit.so file (such as by using ldconfig).
Also, the linker will need to find the Xalan and Xerces libraries (by similar
means).

2. A configuration file (see toolkit2.conf for a sample file) must be available
to the toolkit at startup.  It is recommended that this file be named
absolutely.  This file directs the library to the location of other resources
required for correct operation.  The user should ensure that each resource
named in the configuration file resolves to an available file on the system
(such as .xsd files).  The sample configuration file is in the 'etc' directory.

4. XML schema definition files are in the 'resources' directory.  They may be
copied to another location as appropriate to the client's environment, but such
a location must be defined as described in step C.2.


D. Running Bundled Tests
------------------------

There are a set of unit tests provided with the source release of the C++
toolkit.  Follow the steps below to run these tests.  In the examples, phrases
delimited by < and > should be replaced by suitable values, such as:
ORG=<organisation-name>
to be replaced with:
ORG="AusRegistry Pty Ltd"

1. Ensure the system requirements are met.

2. Obtain the latest C++ toolkit source release.

3. Obtain a valid username/password combination from the Registry server.

4. Generate a private key and corresponding public certificate signing request,
as documented in the Registrar Help Centre (www.ausregistry.net.au).  Define
the location of the private key and the certificate file returned by Registry
support staff in the toolkit configuration file.  You will also receive a
CAfile from the Registry operator.  The location of this must be specified by
the ssl.ca.location configuration parameter.  The other parameters are
ssl.cert.location, ssl.privatekey.location and ssl.privatekey.pass for the
location of the public certificate and private key files and the passphrase of
the private key, respectively.

5. Copy or modify the site.properties file from the 'etc' directory of the
Toolkit source release, and modify the parameters to suit your requirements.
The username and password are as obtained in (3) above.  Registry support
should provide the location (hostname and port) of a suitable EPP server for
testing.  
Note: the environment variable EPP_SITE_PROPERTIES defines the location of the
modified site.properties file and should be stated as an absolute filename.

Example:
export EPP_SITE_PROPERTIES=/home/eppclient/etc/site-test.properties

6. Run the supplied tests (will build the Toolkit library if not already built).
$ make tests

9. Verify the results of the test run.  If the test run is successful, then
there will be no output, otherwise each failed test will be reported to the
screen.  Failure details are available in the target/test_reports directory.
Log messages are written by default to a file name 'log' in the working
directory.  Only the messages from the last test class are available after the
test run completes.

If the tests passed, congratulations!  You have successfully configured the
dependencies of the toolkit.  The next step is to consult the user manual,
which is available from the Registry Portal.  Keep track of the resources
created during this procedure, especially the private key and public
certificate, as these will be required by the toolkit when integrated into your
application.


--- End of README.txt ---
