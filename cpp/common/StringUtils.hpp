#ifndef __STRING_UTILS_HPP
#define __STRING_UTILS_HPP

#include <string>

class StringUtils
{
public:
	typedef long long HashType;

	static std::string replaceAll (const std::string &theString,
                        const std::string &orig,
                        const std::string &repl);

	static std::string replaceFirst (const std::string &theString,
                          const std::string &orig,
                          const std::string &repl);

	static HashType hashCode (const std::string &str);

	static std::string makeString (int num);
	static std::string makeString (bool tf);
};

#endif // __STRING_UTILS_HPP
