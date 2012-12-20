#ifndef __LOG_H
#define __LOG_H

#ifdef __cplusplus
extern "C" {
#endif

const char *LOG_ERR_STR(int errnum);

void mylog(
	const char *const format,
	...
	);

void real_log(
	const char	*const format,
	...
	);

//critical loggin needs to be handled by the thread itself otherwsie we risk exiting before the log is written out
//XXX think about making an on exit to flush and close log files otherwise messages might get lost

#ifdef __cplusplus
}
#endif

#endif //__LOG_H
