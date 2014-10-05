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

	@Pointcut("execution(* *.loadFieldData()) && this(ru.itbasis.utils.zk.ui.dialog.form.AbstractDialogForm)")
	protected void loadFieldData() {
	}

	@Around("loadFieldData()")
	public Object logLoadFieldData(ProceedingJoinPoint joinPoint) throws Throwable {
		final Signature sig = joinPoint.getSignature();
		final Class aClass = sig.getDeclaringType();
		final Logger logger = LoggerFactory.getLogger(aClass.getName());
		final Field field = aClass.getDeclaredField("_item");
		logger.trace("item: {}", field);
		return joinPoint.proceed();
	}
}
