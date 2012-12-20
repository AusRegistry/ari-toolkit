#include <ctype.h>
#include <stdio.h>
#include <errno.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <unistd.h>
#include <stdlib.h>

#include "config.h"
#include "log.h"
#include "boolean.h"
#include "string.h"
#include "mem_debug.h"


static const int KEYS_PER_BLOCK=64;


static char **config_find_entry(config_t *cfg, const char *key);
static int config_rebuild_index (config_t *cfg);
static int config_iter_seek(config_iter_t *iter);
static void config_reset_content(config_t *cfg);
        
/* TODO add parsing state info/offsets for better error messages? */
/* TODO iff this is an ACTUAL performance problem, could qsort cfg->keys at
 *      completion then bsearch on lookup */
static int config_build_index(config_t *cfg)
{
	/* we modify cfg->content, NULL-terminating keys and values */
	char *c = cfg->content;
	int pair_num=0;

	/* scan through looking for comments and "key=value\n" strings. */
	/* blank lines may contain whitespace, which is ignored (and line may be indented) */
	while (*c!='\0')
	{
		if (*c=='\n')
		{
			c++;
			continue;
		}
		if (*c=='#')
		{
			if((c=strchr(c, '\n'))==NULL)
			{
				real_log("config_build_index: un-terminated comment.\n");
				return FALSE;
			}
			c++;	/* move past the '\n' */
			continue;
		}
		if (isalpha(*c))
		{
			/* if we have crossed a multiple of KEY_BLOCK_SIZE keys, grow by another 64. */
			if (pair_num && pair_num%KEYS_PER_BLOCK==0)
			{
				size_t new_size = (pair_num+KEYS_PER_BLOCK)*sizeof(char *);
				cfg->keys=(char **)realloc_d(cfg->keys,new_size,"config_build_index: cfg->keys");  /* XXX errors */
				cfg->values=(char **)realloc_d(cfg->values,new_size,"config_build_index: cfg_values");
			}
			cfg->keys[pair_num] = c;
			if((c=strchr(c,'='))==NULL)
			{
				real_log("config_build_index: value without '=' found.\n");
				return FALSE;
			}
			*c='\0';
			c++;
			cfg->values[pair_num] = c;
			if((c=strchr(c, '\n'))==NULL)
			{
				real_log("config_build_index: a value was found that was not terminated by new-line.\n");
				return FALSE;
			}
			*c='\0';
			c++;
			pair_num++;
			continue;
		}
		if(isspace(*c))
		{
			while(isspace(*c)) c++;
			continue;
		}
		real_log("config_build_index: unexpected char '%c' at offset %d\n", *c, c-cfg->content);
		return FALSE;
	}
	cfg->num_keys=pair_num;
	return TRUE;
}

config_t *config_open_inmem(
	const char *data
	)
{
	int 		success=FALSE;
	config_t	*cfg=NULL;

	for(;;)
	{
		if ((cfg=(config_t *)malloc_d(sizeof(config_t),"config_open_inmem: config_t"))==NULL
			|| !memset(cfg,0,sizeof(config_t))
			|| (cfg->content=strdup_d(data, "config_open_inmem: cfg->content")) == NULL
			|| (cfg->keys=malloc_d(KEYS_PER_BLOCK*sizeof(char *), "config_open_inmem: cfg->keys"))==NULL
			|| (cfg->values=malloc_d(KEYS_PER_BLOCK*sizeof(char *), "config_open_inmem: cfg->values"))==NULL)
		{
			real_log("config_open_inmem: memory allocation failed for config_t\n");
			break;
		}
		if (!config_build_index(cfg))
		{
			real_log("config_open_inmem: failed to build config index\n");
			break;
		}
		cfg->content_bufsz = strlen(data) + 1;
		success=TRUE;
		break;
	}
	if(!success)
	{
		if (cfg) config_destroy(cfg);
		return NULL;
	}
	return cfg;
}



config_t *config_open(
	const char *file_name
)
{
	int 		sz, success=FALSE;
	config_t 	*cfg=NULL;
	struct stat 	stats;
	FILE 		*file=NULL;

	for(;;)
	{
		if (stat(file_name,&stats)==-1)
		{
			real_log("config_open: could not get information for '%s': %s\n", file_name, LOG_ERR_STR(errno));
			break;
		}
		if ((file=fopen(file_name,"r"))==NULL)
		{
			real_log("config_open: could not open '%s': %s\n", file_name, LOG_ERR_STR(errno));
			break;
		}
		if ((cfg=(config_t *)malloc_d(sizeof(config_t),"config_open: config_t"))==NULL
			|| !memset(cfg,0,sizeof(config_t))
			|| (cfg->content=malloc_d(stats.st_size+1,"config_open: cfg->content"))==NULL
			|| (cfg->keys=malloc_d(KEYS_PER_BLOCK*sizeof(char *), "config_open: cfg->keys"))==NULL
			|| (cfg->values=malloc_d(KEYS_PER_BLOCK*sizeof(char *), "config_open: cfg->values"))==NULL)
		{
			real_log("config_open: memory allocation failed for config_t\n");
			break;
		}
		if ((sz=fread(cfg->content,sizeof(char),stats.st_size,file))!=stats.st_size)
		{
			real_log("config_open: fread return unexpected length (got %d, expected %d)\n", sz, stats.st_size);
			break;
		}
		(cfg->content)[stats.st_size]='\0';  /* terminate file with NULL */
        cfg->content_bufsz = stats.st_size + 1;
		if (!config_build_index(cfg))
		{
			real_log("config_open: failed to build config index\n");
			break;
		}
		success=TRUE;
		break;
	}
	if(file) fclose(file);
	if(!success)
	{
		if (cfg) config_destroy(cfg);
		return NULL;
	}
	return cfg;
}

void config_destroy(
	config_t *cfg
)
{
	free_d(cfg->keys,"config_destroy: cfg->keys");
	free_d(cfg->values,"config_destroy: cfg->values");
	free_d(cfg->content,"config_destroy: cfg->content");
	free(cfg);
}

config_iter_t *config_iter_create(
	config_t *cfg,
	const char* prefix
)
{
	config_iter_t	*iter;
	iter=(config_iter_t *)malloc_d(sizeof(config_iter_t),"config_iter_create: iter");
	memset(iter,0,sizeof(config_iter_t));
	iter->cfg=cfg;
	if(prefix) iter->prefix=strdup_d(prefix,"config_iter_create: pattern");
	config_iter_seek(iter);
	return iter;
}


int config_iter_next(
	config_iter_t *iter
)
{
	/* if iterator currently valid, increment */
	if(iter->key!=NULL)
	{
		iter->idx++;
		if (config_iter_seek(iter))
		{
			return TRUE;
		}
		/* indicate iter now at end */
		(iter->key)=(iter->value)=NULL;
	}
	return FALSE;
}

void config_iter_destroy(
    config_iter_t *cfg_iter
)       
{       
    free_d(cfg_iter->prefix,"config_iter_destroy: pattern");
    free_d(cfg_iter,"config_iter_destroy: cfg_iter");
}       

/* returns NULL if string not found, or if key is NULL */
char *config_get_str(
	config_t *cfg,
	const char *key)
{
    char **entry = config_find_entry(cfg, key);
    
    if (entry)
        return strdup_d (*entry, "config_get_str: cfg->values[i]");
    else
        return NULL;
}

/* reallocates space for the config's content buffer, adjusting the
 * existing size by delta.
 */
static void config_realloc_content (config_t *cfg, int delta)
{
    cfg->content = realloc_d (cfg->content,
                              cfg->content_bufsz + delta,
                              "config_realloc_content: cfg->content");
}

/* Slides the block of memory from 'block' to the end of the content buffer
 * by dist bytes (may be negative).
 */
static void config_slide_content (config_t *cfg, char* block, int dist)
{
	size_t block_size = cfg->content_bufsz - (block - cfg->content);

    memmove (block + dist,
             block,
             block_size);
}

/* Replaces the entry string with the new_entry string within the
 * config's content buffer, adjusting the buffer size accordingly.
 */
void config_replace_entry(config_t *cfg, char *entry, const char *new_entry)
{
    size_t old_length = strlen(entry), new_length = strlen(new_entry);
	int delta_size = new_length - old_length;

	/* point into the data directly after to the current entry value. */
    char *next_block = entry + old_length + 1;

    /* We need integer offsets into the buffer, so we can re-find
     * the right position after realloc() is called.
     */
	size_t next_block_offset = next_block - cfg->content;
	size_t entry_offset = entry - cfg->content;

    /* The order we do this in depends on whether we're growing
     * or shrinking the existing key's value. */
    if (delta_size > 0)
    {
        config_realloc_content (cfg, delta_size);

        /* Buffer will have (possibly) moved - point at the (possibly different)
		 * new location */
		next_block = cfg->content + next_block_offset;

        config_slide_content (cfg, next_block, delta_size);
    }
    else if (delta_size < 0)
    {
        config_slide_content (cfg, next_block, delta_size);
        config_realloc_content (cfg, delta_size);
    }

    cfg->content_bufsz += delta_size;

	entry = cfg->content + entry_offset;
    memcpy(entry, new_entry, new_length+1);
}
     
/* changes the value of an existing key, returning the new value or NULL
 * if the key doesn't exist.
 */                         
char *config_put_str (config_t *cfg,
                      const char *key,
                      const char *new_value)
{
    char **entry = config_find_entry(cfg, key);
    char *res = NULL;
    
    if (entry)
    {
        config_replace_entry (cfg, *entry, new_value);
        if (config_rebuild_index (cfg))
            res = strdup_d (*entry, "config_put_str: value");
    }
    
    return res;
}

    

int config_get_enum(
	config_t *cfg,
	const char *key,
	const char* const allowed[],
	int *val
	)
{
	int i, success=FALSE;
	char *str_val;
	if ((str_val=config_get_str(cfg,key))==NULL) return FALSE;
	for(i=0;allowed[i]!=NULL;i++)
	{
		if(strcmp(str_val,allowed[i])==0)
		{
			*val=i;
			success=TRUE;
			break;
		}
	}
	if (!success) real_log("config_get_enum: key '%s' has out-of-range enum value '%s'\n", key, str_val);
	free_d(str_val,"config_get_enum: val");
	return success;
}

/* same as config_get_enum, with 'allowed' == {"FALSE", "TRUE", NULL} */
int config_get_bool(
	config_t *cfg,
	const char *key,
	int *val)
{
	return config_get_enum(cfg, key, bool_str_g, val);
}

/* return value associated with 'key' through 'val'. returns TRUE if
   value found and int-like, or FALSE otherwise. */
int	config_get_int(
	config_t *cfg,
	const char *key,
	int *val)
{
	int res;
	int success=TRUE;
	char *str_val;
	if ((str_val=config_get_str(cfg, key))==NULL)
	{
		return FALSE;
	}
	/* we expect a single integer term to match */
	if (sscanf(str_val,"%d",&res)!=1)
	{
		real_log("config_get_int: key: '%s' not an integer, instead '%s'\n",key,str_val);
		success=FALSE;
	}
	else *val=res;
	free_d(str_val, "config_get_int: str_val");
	return success;
}

int config_get_long (config_t *cfg, const char *key, long *val)
{
	long res;
	int success=TRUE;
	char *str_val;
	if ((str_val=config_get_str(cfg, key))==NULL)
	{
		return FALSE;
	}
	/* we expect a single integer term to match */
	if (sscanf(str_val,"%ld",&res)!=1)
	{
		real_log("config_get_long: key: '%s' not a long, instead '%s'\n",key,str_val);
		success=FALSE;
	}
	else *val=res;
	free_d(str_val, "config_get_long: str_val");
	return success;
}

/* JR */

/* Clones a config_t object */
config_t *config_clone (const config_t * const other_cfg)
{
    config_t *cfg;
    
	if ((cfg=(config_t *)malloc_d(sizeof(config_t),"config_clone: config_t"))==NULL
		|| !memset(cfg,0,sizeof(config_t))
		|| (cfg->content=malloc_d(other_cfg->content_bufsz,"config_clone: cfg->content"))==NULL
		|| (cfg->keys=malloc_d(KEYS_PER_BLOCK*sizeof(char *), "config_clone: cfg->keys"))==NULL
		|| (cfg->values=malloc_d(KEYS_PER_BLOCK*sizeof(char *), "config_clone: cfg->values"))==NULL)
	{
		real_log("config_open: memory allocation failed for config_t\n");
		return NULL;
	}

    memcpy (cfg->content, other_cfg->content, other_cfg->content_bufsz);
    if (config_rebuild_index (cfg))
    {
        return cfg;
    }
    else
    {
        config_destroy (cfg);
        return NULL;
    }
}

int config_save (config_t   *cfg,
                 const char *file_name)
{
    int success = FALSE;
    
    FILE *file = fopen (file_name, "w");
    
    if (file)
    {
        config_reset_content(cfg);
        
        fputs (cfg->content, file);
        
        fclose (file);
        
        config_build_index (cfg);
        
        success = TRUE;
    }
    
    return success;
}

/* replaces all the '=' and '\n' characters in the content buffer */
static void config_reset_content(config_t *cfg)
{
    int i, keyOrValue = 0;
    char *c = cfg->content;
    
	for (i = 0; i < cfg->content_bufsz - 1; i++)
		if (cfg->content[i] == '\0')
			cfg->content[i] = (keyOrValue = !keyOrValue) ? '=' : '\n';
}

static int config_rebuild_index (config_t *cfg)
{
    config_reset_content (cfg);
    return config_build_index (cfg);
}

static char **config_find_entry(config_t *cfg, const char *key)
{
    int i;
    
    if (key == NULL)
        return NULL;
    
    for (i = 0; i < cfg->num_keys; i++)
        if (strcmp (cfg->keys[i], key) == 0)
            return &(cfg->values[i]);
    
    real_log ("config_find_entry: key not found '%s'\n", key);
    return NULL;
}

static int config_iter_seek(config_iter_t *iter)
{
	config_t	*cfg=iter->cfg;
	int prefix_len=(iter->prefix?strlen(iter->prefix):0);

	while(iter->idx<cfg->num_keys)
	{
		if(iter->prefix==NULL
		   ||strncmp(cfg->keys[iter->idx],iter->prefix,prefix_len)==0)
		{
			iter->key=cfg->keys[iter->idx];
			iter->value=cfg->values[iter->idx];
			return TRUE;
		}
		iter->idx++;
	}
	return FALSE;
}

