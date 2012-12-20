#ifndef __PARSING_EXCEPTION_HPP
#define __PARSING_EXCEPTION_HPP

#include "common/EPPException.hpp"

class ParsingException : public EPPException
{
public:
	ParsingException (const std::string &msg = "Parsing exception.")
		: EPPException (msg) {};
	EPP_EXCEPTION(ParsingException); 
};

#endif // __PARSING_EXCEPTION_HPP
