#include "common/SystemProperties.hpp"
#include "common/Logger.hpp"
#include "common/ErrorPkg.hpp"
#include "se/SendSE.hpp"
#include "se/ReceiveSE.hpp"
#include "se/IPVersion.hpp"
#include "se/PostalInfoType.hpp"
#include "se/StandardCommandType.hpp"
#include "se/AddRemType.hpp"
#include "se/StandardObjectType.hpp"
#include "se/PeriodUnit.hpp"
#include "se/PollOperation.hpp"
#include "se/TransferOp.hpp"
#include "xml/XMLParser.hpp"
#include "xml/XMLInit.hpp"
#include <xercesc/util/PlatformUtils.hpp>
#include <xalanc/XalanTransformer/XalanTransformer.hpp>

#include <iostream>
#include <signal.h>

namespace {

void initEnumTypes()
{
	// These classes are decendants of EnumType and so have static storage
	// listing valid values.  The init() method must be called to establish
	// these values.
	IPVersion::init();
	PostalInfoType::init();
	StandardCommandType::init();
	AddRemType::init();
	StandardObjectType::init();
	PeriodUnit::init();
	PollOperation::init();
	TransferOp::init();
}

class Init
{
public:
    Init(const std::string& system_props_file)
    {
		try
		{
			// SystemProperties, Logger and ErrorPkg must be initialised in
			// this order.
			sigset_t new_set, old_set;
		        sigemptyset(&new_set);
		        sigaddset(&new_set,SIGPIPE);
        		sigprocmask(SIG_BLOCK,&new_set,&old_set);
			
			SystemProperties::init(system_props_file);
			Logger::init();
			ErrorPkg::init();


			XMLInit::init();
			XMLParser::init();
			XMLWriter::init();
			ReceiveSE::init();
			SendSE::init();

			initEnumTypes();

		}
		catch (EPPException& e)
		{
			std::cerr << "Toolkit initialisation exception: " << e.getMessage() << std::endl;
			throw;
		}
		catch (std::exception& e)
		{
			std::cerr << "Toolkit initialisation exception: " << e.what() << std::endl;
			throw;
		}
    }
};

} // anonymous namepsace


void init(const std::string& system_props_file)
{
	static const Init doInit(system_props_file);
}
