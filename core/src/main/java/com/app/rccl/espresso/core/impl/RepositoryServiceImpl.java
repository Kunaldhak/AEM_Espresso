package com.app.rccl.espresso.core.impl;

import com.app.rccl.espresso.core.RepositoryService;

import org.osgi.service.component.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.jcr.Repository;

@Component(service = RepositoryService.class, immediate=true)

//@Service(value = RepositoryService.class)
public class RepositoryServiceImpl implements RepositoryService {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(RepositoryServiceImpl.class);
        
    @Reference
    private Repository repository;
    
    public String getRepositoryName() {
        return repository.getDescriptor(Repository.REP_NAME_DESC);    
    }
        
    @Activate
    protected void activate() {
        LOGGER.info("service activated" );
    }
    
    @Deactivate
    protected void deactivate() {
        LOGGER.info ("service deactivated");
    }
}