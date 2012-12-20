/*
 * Copyright 1999-2004 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
#if !defined(MEMORYMANAGERIMPL_HEADER_GUARD_1357924680)
#define MEMORYMANAGERIMPL_HEADER_GUARD_1357924680


// Base include file.  Must be first.
#include <new>

#include <xalanc/Include/PlatformDefinitions.hpp>

#include <xercesc/framework/MemoryManager.hpp>


class XalanMemoryManagerImpl : public XALAN_CPP_NAMESPACE_QUALIFIER MemoryManagerType
{
public:
	virtual 
	~XalanMemoryManagerImpl()
	{
	}
	
	virtual void*
	allocate( size_t 	size )
	{
	     void* memptr = ::operator new(size);

	    if (memptr != NULL) 
	    {
	        return memptr;
	    }
	    
		XALAN_USING_STD(bad_alloc)
		
		throw bad_alloc();
	}	
	virtual void
	deallocate(  void* 		pDataPointer  )
	{
		operator delete(pDataPointer);		
		
	}	
};

#endif  // MEMORYMANAGERIMPL_HEADER_GUARD_1357924680


