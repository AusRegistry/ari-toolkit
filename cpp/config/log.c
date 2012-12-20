#include <stdio.h>
#include <stdlib.h>
#include <stdarg.h>
#include <sys/time.h>
#include <time.h>
#include <string.h>

#include "log.h"
#include "boolean.h"

#define LOG_BUFFER_SIZE	6000
static void real_log_again(
	const char *format,
	va_list ap
	);

/* need a debug flag for this ... */
static int g_mylog_enabled = FALSE;

#ifdef __cplusplus
extern "C" {
#endif

const char *LOG_ERR_STR(int errnum)
{
	/* uses the GNU specific strerror_r which requires a buffer which it
         * 'might' use.  provide one, but make is zero length */
	char dummy;
	return strerror_r(errnum, &dummy, 0);
}

//TODO this is a workaround for proper logging for now
// NB: Adds a '\n' to message.
void mylog(
	const char	*const format,
	...
	)
{
	if (!g_mylog_enabled) return;
	va_list		ap;

	va_start(ap,format);
	vfprintf(stderr,format,ap);
	fprintf(stderr,"\n");
	va_end(ap);
	
	return;
}

void real_log(
	const char	*const format,
	...
	)
{
	if (!g_mylog_enabled) return;
	va_list		ap;

	va_start(ap,format);
	real_log_again(format,ap);
	va_end(ap);

	return;
}

#ifdef __cplusplus
}
#endif

static void real_log_again(
	const char *format,
	va_list ap
	)
{
	char		buffer[LOG_BUFFER_SIZE+1];
	struct timeval	tv;
	struct tm	tm;
	
	gettimeofday(&tv,NULL);
	gmtime_r(&(tv.tv_sec),&tm);
	snprintf(buffer,LOG_BUFFER_SIZE,"%d%02d%02d %02d:%02d:%02d.%03lu %s",1900+tm.tm_year,1+tm.tm_mon,tm.tm_mday,tm.tm_hour,tm.tm_min,tm.tm_sec,tv.tv_usec/1000,format);
	vfprintf(stderr,buffer,ap);

	return;
}	
