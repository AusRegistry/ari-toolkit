#include "se/Result.hpp"
#include "common/StringUtils.hpp"

Result::Result (int code,
                const std::string& msg,
                const XalanNode* value,
                const std::string& valueText,
                const std::string& valueReason,
                const std::string& msgLang)
    : resultCode(code),
      resultMessage(msg),
      resultMessageLang(msgLang),
      resultValue(value),
      resultExtvalueValue(valueText),
      resultExtvalueReason(valueReason)
{ }

Result::~Result()
{ }


std::string Result::toString() const
{
	std::string retval ("(result = (code = ");

	retval += StringUtils::makeString(resultCode) +
              ")(msg = " + 
              resultMessage + 
              "))";

	return retval;
}
