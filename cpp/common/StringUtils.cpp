#include "common/StringUtils.hpp"

#include <cmath>
#include <sstream>

using namespace std;

string StringUtils::replaceAll (const string &theString,
                        const string &orig,
                        const string &repl)
{
    string retval (theString);
    
    int replLen = repl.length(), origLen = orig.length();
    
    string::size_type idx = 0;
    while ((idx = retval.find (orig, idx)) != string::npos)
        retval.replace (idx, origLen, repl, 0, replLen);
    
    return retval;
}
    
string StringUtils::replaceFirst (const string &theString,
                          const string &orig,
                          const string &repl)
{
    string retval (theString);
    
    string::size_type idx = retval.find (orig, 0);
    if (idx != string::npos)
        retval.replace (idx, orig.length(), repl, 0, repl.length());
    
    return retval;
}
	



/*
 * Create a hash code for the string.
 * Uses the java string.hashCode() algorithm:
 *
 *   s[0]*31^(n-1) + s[1]*31^(n-2) + ... + s[n-1]
 */
StringUtils::HashType StringUtils::hashCode (const string &str)
{
	unsigned int n = str.length();
	HashType hash = 0L;

	long double term;
	
	for (unsigned int i = 0; i < n; i++)
	{
		term = pow ((long double)31, (int)(n-(i+1)));
		hash += str[i] * (HashType)term;
	}
	
	return hash;
}

		

string StringUtils::makeString (int num)
{
	ostringstream intstr;
	intstr << num;
	return intstr.str();
}

string StringUtils::makeString (bool tf)
{
	return tf ? "true" : "false";
}
