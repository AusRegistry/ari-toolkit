#ifndef __TEST_HPP
#define __TEST_HPP

#include "common/EPPException.hpp"

#include <iostream>
#include <string>

static int g_numErrors = 0;
int TEST_errorCount() { return g_numErrors; }

#define ASSERT_STR_EQ(got, expect) ASSERT_EQ(std::string(got), std::string(expect))

#define ASSERT(expr) \
  if (!(expr)) { \
	  g_numErrors++; \
	  std::cerr << __FILE__ << ":" << __LINE__ << ":" << #expr << std::endl;}

#define ASSERT_EQ(got, expect) \
  if ((got) != (expect)) \
  { g_numErrors++; \
	  std::cerr << __FILE__ << ":" << __LINE__ << ": (" \
		  		<< got << " != " << expect \
		        << ") for (" << #got" == "#expect")" << std::endl; }

#define ASSERT_NULL(got) \
  if ((got) != NULL) \
  { g_numErrors++; \
	  std::cerr << __FILE__ << ":" << __LINE__ << ": (" \
		  		<< got << " != null" \
		        << ") for (" << #got" == null)" << std::endl; }

#define ASSERT_NEQ(got, expect) \
  if ((got) == (expect)) \
  { g_numErrors++; \
	  std::cerr << __FILE__ << ":" << __LINE__ << ": (" \
		  		<< got << " == " << expect \
		        << ") for (" << #got" != "#expect")" << std::endl; }

#define FAIL(msg) std::cerr << __FILE__ << ":" << __LINE__ << ": " << msg << std::endl

int TEST_run(void (*test)())
{
	try
	{
		(*test)();
	}
	catch (EPPException& e)
	{
		std::cerr << "Unexpected EPPException: " << e.getMessage() << std::endl;
		g_numErrors++;
	}
	catch (std::exception& e)
	{
		std::cerr << "Unexpected std::exception: " << e.what() << std::endl;
		g_numErrors++;
	}
	catch (...)
	{
		std::cerr << "Unexpected unknown." << std::endl;
		g_numErrors++;
	}
	return g_numErrors;
}
#endif // __TEST_HPP
