#ifndef __XSTR_HPP
#define __XSTR_HPP

#include <xercesc/util/XMLString.hpp>
#include <string>

// A simple wrapper that converts an string into a Xerces unicode
// string and manages the transcoded string with a destructor.
class XStr
{
public:
	XStr(const char* const toTranscode)
		: fUnicodeForm(xercesc::XMLString::transcode(toTranscode))
	{ }

	XStr(const std::string& toTranscode)
		: fUnicodeForm(xercesc::XMLString::transcode(toTranscode.c_str()))
	{ }

	~XStr()
	{
		xercesc::XMLString::release(&fUnicodeForm);
	}

	const XMLCh* str() const
	{
		return fUnicodeForm;
	}

private:
	XMLCh*   fUnicodeForm;
};

#endif // __XSTR_HPP
