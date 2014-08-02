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
public class LoggerEventListener {

	@Pointcut("execution(* *.onEvent(..)) && this(org.zkoss.zk.ui.event.EventListener)")
	public void onEvent() {
	}

	@Around("onEvent()")
	public Object logEvent(ProceedingJoinPoint joinPoint) throws Throwable {
		final Signature sig = joinPoint.getSignature();
		final Class aClass = sig.getDeclaringType();
		final Logger logger = LoggerFactory.getLogger(aClass.getName());
		final Field field = aClass.getDeclaredFields()[0];
		logger.trace("method: '{}', event: {}", sig.getName(), field);
		return joinPoint.proceed();
	}
}
