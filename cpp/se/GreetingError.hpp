#ifndef __GREETINGERROR_HPP
#define __GREETINGERROR_HPP

class GreetingError : public Error
{
public:
    GreetingError (const std::string & msg) : Error(msg) {};
};

#endif // __GREETINGERROR_HPP
