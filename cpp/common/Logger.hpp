#ifndef __LOGGER_HPP
#define __LOGGER_HPP

#include "common/Properties.hpp"
#include "common/ConfigurationError.hpp"

#include <string>
#include <map>
#include <memory>
#include <ostream>

#define LOG_FINEST(msg)  log(Logger::FINEST,  msg, __FILE__, __LINE__)
#define LOG_FINER(msg)   log(Logger::FINER,   msg, __FILE__, __LINE__)
#define LOG_FINE(msg)    log(Logger::FINE,    msg, __FILE__, __LINE__)
#define LOG_CONFIG(msg)  log(Logger::CONFIG,  msg, __FILE__, __LINE__)
#define LOG_INFO(msg)    log(Logger::INFO,    msg, __FILE__, __LINE__)
#define LOG_WARNING(msg) log(Logger::WARNING, msg, __FILE__, __LINE__)
#define LOG_SEVERE(msg)  log(Logger::SEVERE,  msg, __FILE__, __LINE__)

/**
 *	An interface that support logging at various severity levels and
 *	to various logging sinks.  The toolkit uses a number of well known
 *	logging sinks as defined in the shipped 'logging.conf' property
 *	file.  The Logging interface needs to be initialised before any
 *	toolkit application objects are constructed to ensure the settings
 *	present in the configuration have an effect.
 *	Note: SystemProperties::init() must have been run before using this
 *	interface.  Logger::init() must be called first.
 *	The SystemProperty 'logging.config.file' must refer to a file contain
 *	the logging configuration.
 */
class Logger
{
public:
	enum LoggerLevel
	{
		OFF,
		FINEST,
		FINER,
		FINE,
		CONFIG,
		INFO,
		WARNING,
		SEVERE,
		__INVALID_LEVEL
	};

	/// @note Use the getLogger function exclusively to create loggers.
	~Logger();
	Logger(const std::string& name, const LoggerLevel ll, const std::string& fileName);


	static void init() throw (PropertyConfigException);

	// Return the logger object from the configuration.
	// @param name The logger identity.
	static Logger* getLogger(const std::string &name);

	void severe(const std::string &str) { log(SEVERE,str); }
	void warning(const std::string &str){ log(WARNING, str); }
	void finest(const std::string &str) { log(FINEST, str); }
	void finer(const std::string &str) { log(FINER, str); }
	void fine(const std::string &str) { log(FINE, str); }
	void info(const std::string &str) { log(INFO, str); }
	void config(const std::string &str) { log(CONFIG, str); }

	/// Return true if a log message of the given level will be recorded
	// by this logger.
	// This may be useful in cases where it is considered comparatively
	// expensive to construct a specific diagnostic--constuction can be
	// predicated on whether or not the message will in fact be logged.
	// @param lvl The log level importance level to test.
	bool enabled(const LoggerLevel& lvl) { return lvl >= level; }

	void log(LoggerLevel lvl, 
			 const std::string &str,
			 const std::string &unit = "",
			 int line = 0);

private:
	Logger(const Logger&);
	Logger& operator=(Logger&);
	
	pthread_mutex_t mtx;

	std::string myName;
	LoggerLevel level;
	std::ostream* stream;

	void logMessage(const std::string& msg, LoggerLevel lvl);
};
#endif // __LOGGER_HPP
