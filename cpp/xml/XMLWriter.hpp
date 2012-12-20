#ifndef __XMLWRITER_H
#define __XMLWRITER_H

#include <vector>
#include <string>

#include <xercesc/dom/DOMElement.hpp>

#include "common/Logger.hpp"

#ifndef SWIG
XERCES_CPP_NAMESPACE_USE
#endif /* SWIG */

class XMLParser;

/**
 * An XMLWriter provides a simple interface to build a DOM tree and serialize
 * it to XML format.  XMLWriters are namespace aware and may be configured to
 * validate the generated XML.
 */
class XMLWriter
{
public:

    /**
     * Get the root element of the DOM tree associated with this writer.
     *
     * @return The root Element of the DOM tree.
     */
	virtual DOMElement* getRoot(void) const = 0;
	virtual ~XMLWriter();

	typedef std::vector<std::string> string_list;

    /**
     * Append a new child element to the specified element.  The new element
     * will inherit the namespace of the parent.
     *
     * @param parent The element which will be the parent element of the new
     * element.
     *
     * @param name The name of the new element (tag).
     *
     * @return The new child element.
     */
	DOMElement* appendChild(DOMElement* parent, 
	                        const std::string& name);
    /**
     * Append a new child element to the specified element.  The new element
     * will be in the namespace identified by the given URI.
     *
     * @param parent The element which will be the parent element of the new
     * element.
     *
     * @param name The name of the new element (tag).
     *
     * @param uri Namespace URI for the new child element.
     */
	DOMElement* appendChild(DOMElement* parent,
	                        const std::string& name,
	                        const std::string& uri);
    /**
     * Append a DOM Element with the given name and attribute to the specified
     * parent Element.  The new {@link org.w3c.dom.Element} will be in the
     * namespace of its parent.
     *
     * @param parent The parent of the new Element.
     *
     * @param name The name of the new Element.
     *
     * @param attrName The name of the attribute of the new Element.
     *
     * @param attrValue The value of the attribute named {@code attrName}.
     *
     * @return The new Element.
     */
	DOMElement* appendChild(DOMElement* parent,
	                        const std::string& name,
	                        const std::string& attrName,
	                        const std::string& attrValue);
    /**
     * Append a DOM Element with the given name, text content value and
     * attribute to the specified parent Element.  The new {@link
     * org.w3c.dom.Element} will be in the namespace of its parent.
     *
     * @param parent The parent of the new Element.
     *
     * @param name The name of the new Element.
     *
     * @param value The text content to assign to the text node child of the
     * new Element.
     *
     * @param attrName The name of the attribute of the new Element.
     *
     * @param attrValue The value of the attribute named {@code attrName}.
     *
     * @return The new Element.
     */
	DOMElement* appendChild(DOMElement* parent,
	                        const std::string& name,
	                        const std::string& value,
	                        const std::string& attrName,
	                        const std::string& attrValue);
    /**
     * Append a DOM Element with the given name and attributes to the specified
     * parent Element.  The new element is in the namespace identified the the
     * given URI.  The two arrays {@code attrNames} and {@code attrValues} must
     * have the same length and be ordered such that the nth element of {@code
     * attrValues} corresponds to the attribute with the name given by the nth
     * element of {@code attrNames}.
     *
     * @param parent The parent of the new Element.
     *
     * @param name The name of the new Element.
     *
     * @param uri The URI of the new Element's namespace.
     *
     * @param attrNames The names of the attributes of the new Element.
     *
     * @param attrValues The values of each of the attributes {@code attrName}.
     *
     * @return The new Element.
     */
	DOMElement* appendChild(DOMElement* parent,
	                        const std::string& name,
	                        const std::string& uri,
	                        const std::string& value,
	                        const string_list& attrNames,
	                        const string_list& attrValues);
    /**
     * Append multiple DOM Elements to the given Element, each having a
     * specified text content.  The text content of each new element is defined
     * by the {@code values} array.  The new {@link DOMElement} will
     * be in the namespace of its parent.
     *
     * @param parent The parent of the new Elements.
     *
     * @param name The name of the new Elements.
     */
	void appendChildren(DOMElement* parent,
	                    const std::string& name,
	                    const string_list& values);

    /**
     * Serialize to XML format the DOM tree currently associated with this
     * XMLWriter.
	 */
	std::string toXML();

	/// Must be called prior to any instances are created.
	static void init();
    
protected:
	static Logger *debugLogger;
	static Logger *userLogger;

	virtual std::string version() = 0;
	virtual std::string encoding() = 0;
	//
	virtual DOMElement* createElement(const std::string &uri,
	                                  const std::string &name) = 0;
    /**
     * Set the root element of the DOM tree associated with this writer.
     *
     * @param newRoot The DOM Element to assign as the root.
     */
	virtual void setRoot(DOMElement *newRoot) = 0;

};
#endif // __XMLWRITER_H
