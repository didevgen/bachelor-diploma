package ua.malibu.ostpc.controllers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import ua.malibu.ostpc.services.UserService;

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
