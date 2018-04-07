package com.xhk.labmanage.service;

import com.xhk.labmanage.common.ProjectException;
import com.xhk.labmanage.common.constant.ErrorCodeMap;
import com.xhk.labmanage.dao.ManagerDao;
import com.xhk.labmanage.model.Manager;
import com.xhk.labmanage.rmodel.CheckLoginRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * create by xhk on 2018/3/28
 */
@Service
public class BackUserService {
    private static Logger logger = LoggerFactory.getLogger(BackUserService.class);

    @Autowired
    private ManagerDao managerDao;

    public String checkLogin(CheckLoginRequest request){
        Manager manager = managerDao.checkLogin(request.getUsername(),request.getPassword());
        if (manager == null){
            throw new ProjectException(ErrorCodeMap.Login_ERROR);
        }
        return manager.getName();
    }
}
