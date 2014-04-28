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
	private transient static final Logger LOG = LoggerFactory.getLogger(LoggerEventListener.class.getName());

	@Pointcut("execution(* *.onEvent(..)) && this(org.zkoss.zk.ui.event.EventListener)")
	public void onEvent() {
	}

	@Around("onEvent()")
	public Object logEvent(ProceedingJoinPoint joinPoint) throws Throwable {
		if (LOG.isTraceEnabled()) {
			final Signature sig = joinPoint.getSignature();
			Field field = sig.getDeclaringType().getDeclaredField("event");
			LOG.trace("method: '{}', event: {}", sig.getName(), field);
		}
		return joinPoint.proceed();
	}
}
