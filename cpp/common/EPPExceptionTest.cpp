#include "common/EPPException.hpp"
#include "common/Test.hpp"

#include <iostream>

using namespace std;

int main(int argc, char* argv[])
{
	EPPException a("whoops");
	EPPException b("there was a problem");
	a.causedBy(b);
	ASSERT_EQ(a.getMessage(), "whoops\nCaused by\nthere was a problem");
}
