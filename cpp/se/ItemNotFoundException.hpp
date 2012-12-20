#ifndef __ITEM_NOT_FOUND_EXCEPTION_HPP
#define __ITEM_NOT_FOUND_EXCEPTION_HPP

#include "common/EPPException.hpp"

class ItemNotFoundException : public EPPException
{
public:
	ItemNotFoundException()
		: EPPException("Item not found.")
	{ }
	EPP_EXCEPTION(ItemNotFoundException);
};

#endif // __ITEM_NOT_FOUND_EXCEPTION_HPP
