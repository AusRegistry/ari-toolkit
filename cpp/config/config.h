#ifndef _CONFIG_H
#define _CONFIG_H

#ifdef __cplusplus
extern "C" {
#endif

typedef struct config_t
{
    int content_bufsz;
	char	*content;
	char	**keys;
	char	**values;
	int	num_keys;
} config_t;

config_t *config_open(const char *file_name);
config_t *config_open_inmem(const char *data);
void config_destroy(config_t *);

int config_save(config_t *cfg, const char *file_name);
config_t *config_clone(const config_t * const other_cfg);

/* return value associated with 'key', or NULL if key not found. caller
   must free returned string */
char *config_get_str(config_t *,const char *key);

/* write a new value to the key and return the value string. */
char *config_put_str(config_t *cfg, const char *key, const char *value);

/* return through 'val' the integer value associated with 'key'. if found and
 * int-like, return TRUE, else FALSE */
int config_get_int(config_t *cfg,const char *key,int* val);
int config_get_long(config_t *cfg,const char *key,long* val);

/* return through 'val' the offset within the list of possible values given by
 * 'allowed' for the config key 'key'. 'allowed' is NULL terminated. Return TRUE
 * if key found and value within range, else FALSE. */
int config_get_enum(config_t *cfg,const char *key,const char* const allowed[],int *val);

/* same as config_get_enum, with 'allowed' == {"FALSE", "TRUE", NULL} */
int config_get_bool(config_t *cfg,const char *key,int *val);

typedef struct config_iter_t
{
	char	*key;
	char	*value;
	char	*prefix;
	config_t	*cfg;
	int	idx;
} config_iter_t;

config_iter_t *config_iter_create(config_t *,const char* pattern);
void config_iter_destroy(config_iter_t *);
int config_iter_next(config_iter_t *);

#ifdef __cplusplus
}
#endif

#endif /* _CONFIG_H */
