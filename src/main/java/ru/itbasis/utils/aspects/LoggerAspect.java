package ru.itbasis.utils.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.itbasis.utils.zk.LogMsg;

@Aspect
public class LoggerAspect {
	private transient static final Logger LOG = LoggerFactory.getLogger(LoggerAspect.class.getName());

	@Pointcut("execution(* *.onEvent(..)) && this(org.zkoss.zk.ui.event.EventListener)")
	public void onEvent() {
	}

	@Around("onEvent()")
	public Object logEvent(ProceedingJoinPoint joinPoint) throws Throwable {
		Object[] methodArgs = joinPoint.getArgs();
		if (LOG.isTraceEnabled()) {
			LOG.trace(LogMsg.EVENT, methodArgs);
		}
		return joinPoint.proceed();
	}
}
