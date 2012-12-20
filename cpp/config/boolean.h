#ifndef __BOOLEAN_H
#define __BOOLEAN_H

#ifndef TRUE
#define TRUE	1
#endif /* defined(TRUE) */
#ifndef FALSE
#define FALSE	0
#endif /* defined(FALSE) */
extern const char *const bool_str_g[];
#define BOOL_STR(x)	bool_str_g[x]

#endif //__BOOLEAN_H
