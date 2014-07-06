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
public class LoggerAbstractDialogForm {
	private transient static final Logger LOG = LoggerFactory.getLogger(LoggerAbstractDialogForm.class.getName());

	@Pointcut("execution(* *.loadFieldData()) && this(ru.itbasis.utils.zk.ui.dialog.form.AbstractDialogForm)")
	protected void loadFieldData() {
	}

	@Around("loadFieldData()")
	public Object logLoadFieldData(ProceedingJoinPoint joinPoint) throws Throwable {
		if (LOG.isTraceEnabled()) {
			final Signature sig = joinPoint.getSignature();
			Field field = sig.getDeclaringType().getDeclaredField("_item");
			LOG.trace("item: {}", field);
		}
		return joinPoint.proceed();
	}
}
