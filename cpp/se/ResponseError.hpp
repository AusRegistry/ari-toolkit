#ifndef __RESPONSEERROR_HPP
#define __RESPONSEERROR_HPP

#include "se/Error.hpp"

class ResponseError : public Error
{
public:
    ResponseError (const std::string & msg) : Error(msg) {};
};
#endif // __RESPONSEERROR_HPP
