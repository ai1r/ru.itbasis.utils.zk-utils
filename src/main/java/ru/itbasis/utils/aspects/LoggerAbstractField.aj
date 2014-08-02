package ru.itbasis.utils.aspects;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

@Aspect
public class LoggerAbstractField {

	@Pointcut("execution(* *.set*(..)) && this(ru.itbasis.utils.zkoss.ui.dialog.form.fields.AbstractField)")
	protected void setValue() {
	}

	@Around("setValue()")
	public Object logSetValue(ProceedingJoinPoint joinPoint) throws Throwable {
		final Signature sig = joinPoint.getSignature();
		final Class aClass = sig.getDeclaringType();
		final Logger logger = LoggerFactory.getLogger(aClass.getName());
		if (logger.isTraceEnabled()) {
			final Field[] fields = aClass.getDeclaredFields();
			for (Field field : fields) {
				logger.trace("{}: {}", field.getName(), field);
			}
		}
		return joinPoint.proceed();
	}
}