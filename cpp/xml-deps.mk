define env_required_msg 
The environment variable $(1) must be defined to compile the toolkit.
endef

ifndef XERCES_INC_DIR
$(error $(call env_required_msg,XERCES_INC_DIR))
endif
ifndef XERCES_LIB_DIR
$(error $(call env_required_msg,XERCES_LIB_DIR))
endif
ifndef XERCES_LIB
$(error $(call env_required_msg,XERCES_LIB))
endif

ifndef XALAN_INC_DIR
$(error $(call env_required_msg,XALAN_INC_DIR))
endif
ifndef XALAN_LIB_DIR
$(error $(call env_required_msg,XALAN_LIB_DIR))
endif
XALAN_LIB       = xalan-c

