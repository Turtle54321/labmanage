package com.xhk.labmanage.common.aspect;

import com.xhk.labmanage.common.ProjectException;
import com.xhk.labmanage.common.constant.ErrorCodeMap;
import com.xhk.labmanage.utils.JsonUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Enumeration;
import java.util.Set;

/**
 * create by xhk on 18/3/4
 */
@Aspect
public class JsonParameterVerifyAspect implements Ordered {
	private static Logger logger = LoggerFactory.getLogger(JsonParameterVerifyAspect.class);

	@Around(value = "@annotation(pv)")
	public Object aroundMethod(ProceedingJoinPoint pjd, JsonParameterVerify pv) throws Throwable {
		Object result = null;
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		String jsonParams = request.getParameter("data");
		logger.info("url=" + request.getRequestURI() + ",请求参数:" + requestLog(request));
		try {
			if (!pv.isPage()) {
				if (org.springframework.util.StringUtils.isEmpty(jsonParams)) {
					throw new ProjectException(ErrorCodeMap.NOT_FOUND_PARAM);
				}

				Object sObj = pjd.getArgs()[0];
				Object oObj = JsonUtil.getObjectFromJson(jsonParams, sObj.getClass());
				// BeanUtils.copyProperties(sObj,oObj);
				pjd.getArgs()[0] = oObj;
			} else {
				if (!org.springframework.util.StringUtils.isEmpty(jsonParams)) {
					Object sObj = pjd.getArgs()[0];
					if (sObj.getClass().getName().startsWith("com.zgc.fds")) {
						Object oObj = JsonUtil.getObjectFromJson(jsonParams, sObj.getClass());
						pjd.getArgs()[0] = oObj;
					}
				}
			}
			Object[] params = pjd.getArgs();
			ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
			Validator validator = factory.getValidator();

			for (Object o : params) {
				if (o == null) {
					continue;
				}

				if (o.getClass().getName().startsWith("com.xhk.labmanage")) {
					Set<ConstraintViolation<Object>> constraintViolations = validator.validate(o);
					if (!CollectionUtils.isEmpty(constraintViolations)) {
						for (ConstraintViolation<Object> constraintViolation : constraintViolations) {
							throw new ProjectException(ErrorCodeMap.PARAMETER_ERROR,
									constraintViolation.getPropertyPath() + "格式不对");
						}
					}
				}
			}

		} catch (Exception e) {
			logger.error("JsonParameterVerifyAspect error", e);
			throw e;
		}

		try {
			result = pjd.proceed(pjd.getArgs());
		} catch (Throwable e) {
			throw e;
		}
		return result;
	}

	@Override
	public int getOrder() {
		return 5;
	}

	public String requestLog(HttpServletRequest request) {
		String params = "";
		Enumeration enu = request.getParameterNames();
		while (enu.hasMoreElements()) {
			String paraName = (String) enu.nextElement();
			params = params + (paraName + "=" + request.getParameter(paraName) + "&");
		}
		return params;
	}

}
