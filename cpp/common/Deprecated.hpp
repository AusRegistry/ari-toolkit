#ifndef DEPRECATED_HPP_
#define DEPRECATED_HPP_

/*
 * Platform independent way of deprecating methods, as referenced from
 * http://stackoverflow.com/questions/295120/c-mark-as-deprecated.
 */

#ifdef __GNUC__
/* Deprecating classes and constructors by using this attribute may have some
 * issues in versions older than 4.5.0, as reported in this gcc bug
 * http://gcc.gnu.org/bugzilla/show_bug.cgi?id=43797
 */
#define DEPRECATED(func) func __attribute__ ((deprecated))
#elif defined(_MSC_VER)
#define DEPRECATED(func) __declspec(deprecated) func
#else
#pragma message("WARNING: You need to implement DEPRECATED for this compiler")
#define DEPRECATED(func) func
#endif

#endif /* DEPRECATED_HPP_ */
