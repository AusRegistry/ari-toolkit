#include "SystemProperties.hpp"
#include "common/Logger.hpp"
#include <sys/time.h>
#include <pthread.h>
#include "common/AutoMutex.hpp"

#include <iostream>
#include <fstream>
#include <sstream>

using namespace std;

namespace {

// s_loggerMap and s_loggerProperties are protected by s_logPoolLock
class LoggerMap
{
public:

    // This is primarily a thin wrapper around std::map to release the heap
    // allocated Logger objects. (Bring on map<string, tr1::shared_ptr > ... ).
    ~LoggerMap()
    {
        for (Map::iterator i = loggerMap.begin(); i != loggerMap.end(); ++i)
        {
            delete i->second;
        }
    }
    typedef map<string, Logger *> Map;
    typedef Map::const_iterator const_iterator;

    Map::const_iterator find(const string& name) const
    {
        return  loggerMap.find(name);
    }

    Map::const_iterator end() const { return loggerMap.end(); }

    void addLog(const string& name, Logger::LoggerLevel lvl, const string& fname)
    {
        loggerMap.insert(make_pair(name, new Logger(name, lvl, fname)));
    }

private:
    Map loggerMap;
};
static LoggerMap s_loggerMap;
static auto_ptr<Properties> s_loggerProperties;

pthread_mutex_t s_logPoolLock = PTHREAD_MUTEX_INITIALIZER;

const char * LoggerLevelStringReps[Logger::__INVALID_LEVEL + 1] =
{
    "OFF",
    "FINEST",
    "FINER",
    "FINE",
    "CONFIG",
    "INFO",
    "WARNING",
    "SEVERE",
    "__INVALID_LEVEL"
};

string findKey(const string& parent, const string& key, const string& def)
{
    const string delim(".");
    string::size_type i;
    for (i = parent.size(); i != string::npos; i = parent.rfind(delim, i))
    {
        try
        {
            return s_loggerProperties->getProperty(parent.substr(0, i) + delim + key);
        }
        catch (PropertyNotFoundException& e)
        { }
        if (i == 0) break;
        i -= delim.size();
    }
    return def;
}

string getFileNameForLogger(const string& logName)
{
    try
    {
        return findKey(logName, "file", "");
    }
    catch (EPPException& e)
    {
        return "";
    }
}

Logger::LoggerLevel getLevelForLogger(
    const string& logName,
    const Logger::LoggerLevel defaultLevel)
{
    try
    {
        const string lvl(findKey(logName, "level", "WARNING"));
        for (int i = 0; i < Logger::__INVALID_LEVEL; i++)
        {
            if (lvl == LoggerLevelStringReps[i])
            {
                return (Logger::LoggerLevel)i;
            }
        }
        return defaultLevel;
    }
    catch (EPPException& e)
    {
        return defaultLevel;
    }
}

// Add a time string to str.
ostream& logTime(ostream& str)
{
    struct timeval tv;
    struct tm tm;
    const char BUFSZ=32;
    char tmpbuf[BUFSZ];
    
    gettimeofday(&tv, NULL);
    gmtime_r(&(tv.tv_sec), &tm);
    
    strftime(tmpbuf, BUFSZ, "%Y%m%d %H:%M:%S", &tm);
    str << tmpbuf;
    snprintf(tmpbuf, BUFSZ, ".%03ld", tv.tv_usec / 1000);
    str << tmpbuf;
    return str;
}

} // anonymous namespace


Logger::Logger(const string& name, const LoggerLevel ll, const string& fileName)
    : myName(name), level(ll), stream(NULL)
{
    pthread_mutex_init(&mtx, NULL);
    if (fileName.size() > 0)
    {
        stream = new ofstream(fileName.c_str(), ios_base::app);
        if (!stream->good())
        {
            cerr << "Logger: failed open file '" << fileName
                 << "' for logger '" << name << "'." << endl;

            // Note if the new worked, but the stream is 'invalid', the object
            // still exists but is of no use.  Let's delete it and treat it as
            // for the default logging case.
            delete stream;
            stream = NULL;
        }
    }
}

Logger::~Logger()
{
    pthread_mutex_destroy(&mtx);
    if (stream) delete stream;
}

void Logger::init() throw (PropertyConfigException)
{
    s_loggerMap = LoggerMap();
    s_loggerProperties = auto_ptr<Properties>(new Properties);
    try
    {
        s_loggerProperties->load(SystemProperties::getProperty("logging.config.file"));
    }
    catch (PropertyConfigException& e)
    {
        // We explicitly send this to cerr as the logging system itself is not working!
        cerr << "Logger::init failed to load config: "
                  << e.getMessage() << endl;
        throw;
    }
    catch (PropertyNotFoundException& e)
    {
        PropertyConfigException pce("Could not initialise the logging system.");
        pce.causedBy(e);

        // We explicitly send this to cerr as the logging system itself is not
        // working!
        cerr << e.getMessage() << endl;
        throw pce;
    }
}

Logger* Logger::getLogger(const string &name)
{
    AutoMutex lock(&s_logPoolLock);

    LoggerMap::const_iterator p = s_loggerMap.find(name);
    if (p != s_loggerMap.end()) return p->second;

    const string fname = getFileNameForLogger(name);
    const LoggerLevel ll = getLevelForLogger(name, WARNING);
    s_loggerMap.addLog(name, ll, fname);

    // Redundant find, but this only happens once per log name.
    return s_loggerMap.find(name)->second;
}


void Logger::log(LoggerLevel lvl, 
                 const string& msg,
                 const string& unit,
                 int line)
{
    if (lvl >= this->level)
    {
        ostringstream str;
        logTime(str) << " | " << myName << " | ";
        if (unit != "")
        {
            str << unit;
            if (line > 0) str << "[" << line << "]";
            str << " | ";
        }
        str << LoggerLevelStringReps[lvl] << ": " << msg << "\n";

        AutoMutex lock(&mtx);
        if (stream)
        {
            *stream << str.str();
            stream->flush();
        }
        else
        {
            clog << str.str();
            clog.flush();
        }
    }
}
