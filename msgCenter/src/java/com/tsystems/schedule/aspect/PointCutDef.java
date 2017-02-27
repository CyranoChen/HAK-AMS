/**
 * 
 */
package com.tsystems.schedule.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * @ClassName: PointCutDef
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author zhoushun
 * @date 2012-12-20 上午10:41:50
 * 
 */

@Aspect
public class PointCutDef {
	// 定义切入点
	@Pointcut("execution(* com.tsystems.schedule.execute.IExecutable.*(..))")
	public void beforeTaskExec() {

	}

	/*
	 * @Before(value="execution(* test(*)) && args(param)", argNames="param")
	 * public void before1(JointPoint jp, String param) {
	 * System.out.println("===param:" + param); }
	 * 
	 * 切入点表达式execution(* test(*)) && args(param) ： 1）首先execution(*
	 * test(*))匹配任何方法名为test，且有一个任何类型的参数；
	 * 2）args(param)将首先查找通知方法上同名的参数，并在方法执行时（运行时）匹配传入的参数是使用该同名参数类型，
	 * 即java.lang.String；如果匹配将把该被通知参数传递给通知方法上同名参数。 argNames="param" 指定参数名
	 * 
	 * 如果第一个参数类型是JoinPoint、ProceedingJoinPoint或JoinPoint.StaticPart类型，
	 * 应该从“argNames”属性省略掉该参数名（可选，写上也对），这些类型对象会自动传入的，但必须作为第一个参数；
	 */
}
