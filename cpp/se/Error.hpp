#ifndef __ERROR_HPP
#define __ERROR_HPP

#include <string>

class Error
{
public:
    Error (std::string const& msg) : myText(msg) {};
    
    virtual const std::string & getMessage() const { return myText; };

private:
    std::string myText;
};

#endif // __ERROR_HPP
