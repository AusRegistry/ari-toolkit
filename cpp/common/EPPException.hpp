#ifndef __EPP_EXCEPTION_H
#define __EPP_EXCEPTION_H

#include <stdarg.h>
#include <string>
#include <vector>

#include <iostream>

#define EPP_EXCEPTION(className) virtual EPPException* clone() const { return new className (*this); }

/**
 * Root exception class for exception reporting within the EPP toolkit.
 */
class EPPException
{
public:

	// constructor.
	EPPException(const std::string& message)
		: msg(message), cause(NULL)
	{ }

	// copy constructor.
	EPPException(const EPPException& other)
		: msg(other.msg)
	{
		if (other.cause) cause = other.cause->clone();
		else cause = NULL;
	}

	virtual ~EPPException() { if (cause) delete cause; }

	const std::string getMessage() const
	{
		return msg + (cause != NULL ? "\nCaused by\n" + cause->getMessage() : "");
	}

	/// Indicate that this exception was caused by another exception.
	virtual void causedBy(const EPPException& ex)
	{
		cause = ex.clone();
	}

	EPPException* getCause() const { return cause; }
	EPP_EXCEPTION(EPPException);

private:

	std::string msg;
	EPPException* cause;
};

#endif //__EPP_EXCEPTION_H
