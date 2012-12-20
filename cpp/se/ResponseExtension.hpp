#ifndef RESPONSEEXTENSION_H_
#define RESPONSEEXTENSION_H_

#include <string>

#include <se/ReceiveSE.hpp>

class XMLDocument;

/**
 * Extension of the response mapping of the EPP response. Instances of this
 * class provide an interface to access all of the information available through
 * EPP response extension. This relies on the instance first being initialised
 * by a suitable EPP response using the method fromXML. For flexibility, this
 * implementation extracts the data from the response using XPath queries, the
 * expressions for which are defined statically.
 */
class ResponseExtension : public ReceiveSE
{
   public:
      /**
       * Indicates whether fromXML() completed successfully and the extension was
       * successfully initialised from the EPP response.
       *
       * @return true if the extension has been initialised, else false.
       */
      virtual bool isInitialised() const = 0;

   protected:
      /**
       * XPath expression to locate the extension element from the EPP response.
       */
      static const std::string EXTENSION_EXPR();
};

#endif /* RESPONSEEXTENSION_H_ */
