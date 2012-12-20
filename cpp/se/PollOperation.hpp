#ifndef __POLL_OPERATION_HPP
#define __POLL_OPERATION_HPP

#include "se/EnumType.hpp"

/**
 * Enumeration of poll operations supported by EPP.
 */
class PollOperation : public EnumType
{
public:
    PollOperation(const std::string& op);
    
    static const PollOperation* REQ();
    static const PollOperation* ACK();

    static const PollOperation* value(const std::string &name);

	static void init();

private:
    static std::vector<const EnumType *> values;
};

#endif // __POLL_OPERATION_HPP
