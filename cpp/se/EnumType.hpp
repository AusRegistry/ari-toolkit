#ifndef __ENUMTYPE_HPP
#define __ENUMTYPE_HPP

#include "se/IllegalArgException.hpp"
#include <string>
#include <vector>

/// An enumeration of unique name to values mappings.  Decendants are expected
// to vector (per the first argument of EnumType's ctr) that prepresents the
// pool of possible values.  Decendents are then expected to create well-known
// static instances of themseleves that pass their address to this base class.
//
// Note: Decendant classes will need to ensure their well-known static instances
// are initialised before any other class attempts to use them.
//
class EnumType
{
public:
    /// @TODO SWIG/Perl workaround - figure out why SWIG wants an empty constructor.
    EnumType() {}

    EnumType(std::vector<const EnumType *>& values,
             const std::string& first,
             const std::string& second = "",
             const std::string& third  = "",
             const std::string& fourth = "");

    virtual ~EnumType() { }
    
    virtual std::string toString() const { return first; };
    
    static const EnumType* value(
        const std::string& name,
        std::vector<const EnumType *>& values)
    throw (IllegalArgException);

protected:
    const std::string& getFirst()  const { return first; };
    const std::string& getSecond() const { return second; };
    const std::string& getThird()  const { return third; };
    const std::string& getFourth() const { return fourth; };

private:
    std::string first, second, third, fourth;
};

#endif // __ENUMTYPE_HPP
