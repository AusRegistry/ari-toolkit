#include "se/HostCreateCommand.hpp"
#include "se/StandardObjectType.hpp"


HostCreateCommand::HostCreateCommand (const std::string &name,
                                      const std::vector<InetAddress> *addresses)
    : CreateCommand(StandardObjectType::HOST(), name)
{
    if (addresses)
    {
        std::vector<InetAddress>::const_iterator inaddr;
        
        for (inaddr = addresses->begin(); inaddr != addresses->end(); inaddr++)
            inaddr->appendToElement (xmlWriter, objElement);
    }
}
