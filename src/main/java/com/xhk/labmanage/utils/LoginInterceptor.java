package com.xhk.labmanage.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {
	private static Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);
	
//	@Autowired
//	BackUserDao backUserDao;

	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub

	}

	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		// TODO Auto-generated method stub

	}

	/**
	 * 用于验证登录状况的拦截器
	 *
	 */
	public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object arg2) throws Exception {
		
		//从redis中获取用户登录信息
		String sessionId = HttpUtil.getHttpRequest().getSession().getId();
		Object usernaemObj = RedisUtil.getValue(sessionId);
		logger.info("username=" + usernaemObj);
		logger.info(req.getContextPath() + "/login.htm");
		
		//没有登录或者登录时间到期
		if (usernaemObj == null) {
			res.sendRedirect(req.getContextPath() + "/login.htm");
			return false;
		}
		String username = usernaemObj.toString();
		//判断是否是被踢下线
//		String oldSessionId = RedisUtil.getValue(FdsConstant.REDIS_SINGLE_LOGIN + username);
//		String loginFlag = RedisUtil.getValue(FdsConstant.REDIS_ONLINE_INFO + oldSessionId);
//		if (!StringUtils.isBlank(loginFlag)) {
//			if (loginFlag.equals("1")) {
//				res.sendRedirect(req.getContextPath() + "/back/user/login.do" + "?loginFlag="+loginFlag);
//				return false;
//			}
//		}
		
//		BackUser conds = new BackUser();
//		conds.setUsername(username);
//		List<BackUser> list = backUserDao.findEntityListByCond(conds);
//		if(CollectionUtils.isEmpty(list)){
//			res.sendRedirect(req.getContextPath() + "/back/user/login.do");
//			return false;
//		}
		return true; 
	}
}