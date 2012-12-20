#ifndef __RESULT_HPP
#define __RESULT_HPP

#include "string"
//#include <xercesc/dom/DOMNode.hpp>
#include <xalanc/XalanDOM/XalanNode.hpp>

#ifndef SWIG
XALAN_USING_XALAN(XalanNode)
#endif

/**
 * This class models the result element of EPP responses.  From RFC 3730:
 * "One or more <result>}elements [...] document the success or failure
 * of command execution.  If the command was processed successfully, only one
 * <result> element MUST be returned.  If the command was not processed
 * successfully, multiple <result> elements MAY be returned to document
 * failure conditions.  Each <result> element contains the following
 * attribute and child elements.
 * </blockquote>
 * See getX method descriptions for a description of the elements of a Result.
 * Note that any DOM Node fields will not be serialized.
 * @note A Result instance uses nodes (cf. XalanNode*) from a parsed response object, but does
 * not assume ownership.  Therefore, these other objects life time must outlast
 * the Result object.
 */ 
class Result
{
public:

    /// @TODO SWIG/Perl workaround - figure out why SWIG wants an empty constructor.
    Result () {}

	/**
	 * @note A Result instance uses nodes (cf. XalanNode*) from a parsed response object, but does
	 * not assume ownership.  Therefore, these other objects life time must outlast
	 * the Result object.
	 */
    Result (int code,
            const std::string &msg,
            const XalanNode *value,
            const std::string &valueText,
            const std::string &valueReason,
            const std::string &msgLang = "");

	~Result();

    /** 
     * The code attribute of a result.  From RFC 3730:
     * A "code" attribute whose value is a four-digit, decimal number that
     * describes the success or failure of the command.
     */
    int getResultCode() const { return resultCode; };
    
    /**
     * The msg element of a result.  From RFC 3730:
     * <blockquote>
     * A <msg> element containing a human-readable description of the
     * response code.
     * </blockquote>
     */
    const std::string& getResultMessage() const { return resultMessage; };
    /**
     * The lang attribute of the msg element of a result.  From RFC 3730:
     * <blockquote>
     * The language of the response is identified via an OPTIONAL "lang"
     * attribute.  If not specified, the default attribute value MUST be "en"
     * (English).
     * </blockquote>
     */
    const std::string& getResultMessageLanguage() const { return resultMessageLang; };
    /**
     * The value child elements of the extValue element of a result.  From RFC
     * 3730:
     * "Zero or more OPTIONAL <extValue> elements that can be used to
     * provide additional error diagnostic information, including:<br/>
     * - A <value> element that identifies a client-provided element
     *   (including XML tag and value) that caused a server error condition."
     */
    const std::string& getResultExtValueValue() const { return resultExtvalueValue; };
    /**
     * The reason child elements of the extValue element of a result.  From RFC
     * 3730: "Zero or more OPTIONAL <extValue> elements that can be used to
     * provide additional error diagnostic information, including:<br/>
     * - A <reason> element containing a human-readable message that
     *   describes the reason for the error."
     * @todo provide interface to get language of each reason.
     */
    const std::string& getResultExtValueReason() const { return resultExtvalueReason; };
    
	/**
     * The value elements of a result.  From RFC 3730:
     * <blockquote>
     * Zero or more OPTIONAL <value> elements that identify a
     * client-provided element (including XML tag and value) that caused a
     * server error condition.
     * </blockquote>
     */
	const xalanc::XalanNode* getResultValue() { return resultValue; };
    
     /**
     * Whether the associated command succeeded or not.  This can be used to
     * reduce the amount of result checking, since if this returns true, then
     * no further results should be available for the associated response.
     */
	bool succeeded() const { return ((1000 <= resultCode) && (resultCode < 2000)); };
    
    std::string toString() const;
    
private:
    int resultCode;
    std::string resultMessage;
    std::string resultMessageLang;
    
	// non-owning pointer.
    const XalanNode* resultValue;
    
    std::string resultExtvalueValue;
    std::string resultExtvalueReason;
};

#endif // __RESULT_HPP
