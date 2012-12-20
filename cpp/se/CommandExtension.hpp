#ifndef __COMMAND_EXTENSION_HPP_
#define __COMMAND_EXTENSION_HPP_

#include <string>

class Command;

/**
 * Any classes that implement an EPP command extension should implement this
 * interface. The responsibility of implementor is to construct the subtree
 * under the command extension element.
 */


class CommandExtension
{
   public:
        CommandExtension() { }
		virtual ~CommandExtension() { }
		virtual void addToCommand(const Command &command) const = 0;
};

#endif /* __COMMAND_EXTENSION_HPP_ */
