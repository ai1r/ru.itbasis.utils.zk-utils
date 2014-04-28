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
	private transient static final Logger LOG = LoggerFactory.getLogger(LoggerAbstractField.class.getName());

	@Pointcut("execution(* *.setValue(..)) && this(ru.itbasis.utils.zk.ui.form.fields.AbstractField)")
	protected void setValue() {
	}

	@Around("setValue()")
	public Object logSetValue(ProceedingJoinPoint joinPoint) throws Throwable {
		if (LOG.isTraceEnabled()) {
			final Signature sig = joinPoint.getSignature();
			Field field = sig.getDeclaringType().getDeclaredField("value");
			LOG.trace("value: {}", field);
		}
		return joinPoint.proceed();
	}
}