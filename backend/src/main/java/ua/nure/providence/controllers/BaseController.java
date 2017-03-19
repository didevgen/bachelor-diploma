package ua.nure.providence.controllers;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Transactional
@Controller
public class BaseController {

    protected static final transient Logger logger = Logger.getLogger(BaseController.class.getName());

    @PersistenceContext
    protected EntityManager entityManager;

    public BaseController() {
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }
}
