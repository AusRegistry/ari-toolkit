#ifndef __CLTRID_HPP
#define __CLTRID_HPP

#include <string>

/**
 * Provides generated unique client transaction identifiers for use in EPP
 * commands as the value of the epp:clTRID element.  The class should first be
 * initialised by setting the client identifier.
 */
class CLTRID
{
public:
    CLTRID() { }
    
    /**
     * Generate a unique client transaction identifier and return the value.
     */
    static std::string nextVal();

    /**
     * Set the client identifier for generating client transaction IDs.  This
     * constitutes the first part of the clTRID and helps to ensure uniqueness
     * of clTRIDs within a Registry system.
     */
    static void setClID(const std::string &clID);
    
private:
    static const int BUFFER_SIZE = 24;
    static const int maxVal = 1000;
    static int& val();
    static std::string& clID();
    
    static void inc();
};

#endif // __CLTRID_HPP
