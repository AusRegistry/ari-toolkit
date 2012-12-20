AusRegistry's EPP Client Library for the Java Programming Language.

Features
* Tunable, efficient session keepalive mechanism (optional).
* Statistics management.
* Natural mapping from API to EPP, fostering API extensibility.
* Configurable client-side optimisation of session utilisation when used with
  rate-limiting EPP servers.
* Command pipelining, as described in RFCs 4930 and 3734.
* Support for generic ccTLD extensions
* Support for the following custom extensions:
** AusRegistry EPP Extensions - undelete, unrenew, policyDelete,
   policyUndelete, domainSync
** AusRegistry IDNA 2008 Extensions 
** AusRegistry DNSSEC Extensions 
* Outbound and inbound XML validation against local XSDs.
* Simplified response indications for ease of application development.
* Exposure of error element content via low-level DOM types to support complex
  error handling logic.
* Customisable library error messages.
* Multi-audience configurable logging.
* Dynamic, responsive connection pooling.

A. Software Requirements
------------------------

1. Java SE 6 (jdk1.6) or later - jdk is needed if using source release.
2. Apache Ant 1.7.0 or later
3. JUnit 4.8.2 (included with Toolkit release as junit-4.8.2.jar)

Notes
1. The keytool examples provided in section D are based on the keytool
   command-line tool distributed with Java 6 SE.


B. Build Instructions
---------------------

1. Ensure that the Java home is set to a Java SE 6 (or later) home.
Examples:
$ export JAVA_HOME=/usr/lib/jvm/java-1.6.0-sun.x86_64
$ java -version
java version "1.6.0_31"
Java(TM) SE Runtime Environment (build 1.6.0_31-b04)
Java HotSpot(TM) 64-Bit Server VM (build 20.6-b01, mixed mode)

2. Unpack the toolkit source:
> jar xvf jtk2_release.jar

3. The toolkit jar file can be built from the source or obtained from the lib
directory as a Java archive(arjtk.jar)

If a fresh jar file is required use these additional steps:

4. Build the Java Toolkit class library and all required buildable resources:
> ant [deploy]

5. The previous command will leave build artifacts in the 'target' directory.
In particular, the target/lib directory will contain the toolkit Java archive
(arjtk.jar).

Notes
Use 'ant -p' to list all available project targets.
The 'tests' target can be used to verify successful installation (more below).


C. Usage Information and Advice
-------------------------------

1. When using the toolkit, the classpath (java.class.path) should contain:
- a directory containing the toolkit properties file (usually named
  toolkit.properties);
- a directory containing the keystore file named in the toolkit properties file
  by the key ssl.keystore.file
- any external Java archives (.jar files), such as sjsxp.jar and jsr173_api.jar
- the toolkit .jar file (arjtk.jar)

2. The system property java.util.logging.config.file must be set to the
location of the logging.properties file which controls logging behaviour.  A
sample logging.properties file is in the target directory (after running
'ant').

3. Toolkit behaviour is controlled by a properties file, which by default is
named toolkit.properties.  A sample properties file is provided in
target/toolkit.properties (after running 'ant').

4. XML schema definition files are bundled in the arjtk.jar archive.  The
source for these can be found in src/resources/

5. Authentication files are used by the TLS implementation and are required by
the toolkit in order to function correctly.  Please follow through section D
"Running Bundled Tests" for details of such files (keystores).

6. The site.properties file is used only for running the bundled tests.
Further (real scenario) use of the toolkit requires the toolkit.properties
file; any values modified in site.properties should be copied to the
toolkit.properties configuration file.


D. Running Bundled Tests
------------------------

There are a set of unit tests provided with the source release of the Java
toolkit.  Follow the steps below to run these tests.  In the examples, phrases
delimited by < and > should be replaced by suitable values, such as:
ORG=<organisation-name>
to be replaced with:
ORG="AusRegistry Pty Ltd"

1. Ensure the system requirements are met.

2. Obtain the latest Java toolkit source release.

3. Obtain a JKS truststore (CA certificate) from the Registry operator. The
toolkit property ssl.truststore.location must be the same as the filename and
the file must be placed in the classpath of any application which uses the
toolkit.  The password, provided by the Registry service operator, must be set
appropriately in the parameter ssl.truststore.pass.

Example (sh/bash/ksh):
# '$' represents the command line prompt, '#' indicates start of line comment,
# and '>' indicates continuation of command from previous line.

# grep truststore site.properties
ssl.truststore.location=AR-ca.jks
ssl.truststore.pass=CA-PASSWORD

$ export JAVA_HOME=/usr/java # substitute actual location of Java installation
$ export PATH=$JAVA_HOME/jre/bin:$JAVA_HOME/bin:$PATH

# Verify the truststore.  The Registry operator will inform you of the
# password on the keystore.  If your operator provides the CA certificate in a
# different form, please consult with them to determine the appropriate means
# to obtain a CA truststore (keystore).
$ keytool -list -v -storetype jks -keystore AR-ca.jks

4. Obtain a valid username/password combination from the Registry server.

5. Create a Java Key Store for storing a private key/public certificate pair,
generate a certificate signing request, receive a signed certificate and import
the signed certificate into the Key Store, then relocate the Key Store to the
directory identified by the environment variable SITE_CLASSPATH.

Example:
$ export USERNAME=<EPP-client-identifier>
$ export ORG=<organisation-name>
$ export CITY=<city-name>
$ export STATE=<state/province>
$ export COUNTRY=<country-code>
$ keytool -genkeypair -alias areppclient -keyalg RSA -keysize 1024 -keystore ${USERNAME}-ks.jks -dname "CN=${USERNAME},OU=EPP Client,O=${ORG},L=${CITY},ST=${STATE},C=${COUNTRY}"
$ keytool -certreq -alias areppclient -keystore ${USERNAME}-ks.jks -file ${USERNAME}-csr.pem
# Send file ${USERNAME}-csr.pem to Registry for signing, and receive file
# ${USERNAME}-cert.pem.
$ keytool -importcert -file ${USERNAME}-cert.pem -keystore ${USERNAME}-ks.jks -trustcacerts -alias areppclient
$ export SITE_CLASSPATH=~/etc/toolkit
$ mv ${USERNAME}-ks.jks $SITE_CLASSPATH

6. Copy or modify the site.properties file from the base directory of the
Toolkit source release, and modify the parameters to suit your requirements.
The username and password are as obtained in (4) above.  Registry support
should provide the location (hostname and port) of a suitable EPP server for
testing.  The keystore location is just the basename of the keystore file
created in (5) above (the Toolkit will search the classpath for the file). The
keystore type is normally jks (if created as described in the example in step
5). The parameter ssl.keystore.pass is the passphrase assigned to the Key Store
in step 5 for the purpose of restricting access to the Key Store.
Note: ensure that the value of SITE_PROPS_FILE is relative to the value of
SITE_CLASSPATH.  That is, if the properties are in
/etc/epp/client/site.properties, then suitable values would be:
SITE_CLASSPATH=/etc/epp/client
SITE_PROPS_FILE=site.properties

Example:
export SITE_PROPS_FILE=site-ote.properties
$ cp site.properties $SITE_CLASSPATH/$SITE_PROPS_FILE
# Edit $SITE_CLASSPATH/$SITE_PROPS_FILE as described

7. Verify that the environment variable SITE_CLASSPATH and SITE_PROPS_FILE are
set appropriately, as illustrated in the above examples.

8. Run the supplied tests (will build the Toolkit library if not already built).
$ ant tests

9. Verify the results of the test run.  If the test run is successful, then the
message 'Build completed successfully' will be displayed, otherwise each failed
test will be reported to the screen.  Failure details are available in the
target/test_reports directory.  Log messages are written to the system
temporary directory, usually /tmp on unix-based systems.  Only the messages
from the last test class are available after the test run completes.  To
restrict the logging output, modify the logging.properties file in the
target/config directory - a recommended change is to set .level=WARNING

If the tests passed, congratulations!  You have successfully configured the
dependencies of the toolkit.  The next step is to consult the user manual,
which is available from the Registry Portal.  Keep track of the resources
created during this procedure, especially the Key Store, as these will be
required by the toolkit when integrated into your application.


--- End of README.txt ---
