package com.adp.aspect;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;




@Component
@Aspect
public class LoggingAspect {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
		
	private String log = "Before invoking method {} in class {}";


	
	@Before("execution(* com.adp.controller.*.*(..))")
	public void logServiceBeforeConrolller(JoinPoint joinPoint) throws Exception {
		
		String methodName = joinPoint.getSignature().getName();
		String className = joinPoint.getTarget()
				.getClass().getName();
		logger.info(log+methodName,className);
		
	}
	
	@Before("execution(* com.adp.repository.*.*(..))")
	public void logServiceBeforeRepostiory(JoinPoint joinPoint) throws Exception {
		
		String methodName = joinPoint.getSignature().getName();
		String className = joinPoint.getTarget().getClass().getName();
		
		logger.info(log+methodName,className);
	}
	
	@Before("execution(* com.adp.Service.*.*(..))")
	public void logServiceBeforeService(JoinPoint joinPoint) throws Exception {
		
		String methodName = joinPoint.getSignature().getName();
		String className = joinPoint.getTarget().getClass().getName();
		
		logger.info(log+methodName,className);
	}
	
	@Before("execution(* com.adp.poi.*.*(..))")
	public void logServiceBeforePOI(JoinPoint joinPoint) throws Exception {
		
		String methodName = joinPoint.getSignature().getName();
		String className = joinPoint.getTarget().getClass().getName();
		
		logger.info(log+methodName,className);
	}
	
	


}