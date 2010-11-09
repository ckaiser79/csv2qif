package de.servicezombie.core.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class BeanUtils {

	/**
	 * try to execute a method on target, if method does not exist, try its parent class
	 * if not found, do nothing. Dispatched methods may throw an exception.
	 * @throws RuntimeException, if invoked method throws an exception, the thrown exception is given up by this function as a runtime exception
	 * @throws IllegalAccessException, if dispatch failed because of reflection reasons
	 */
	public static Object silentDispatch(final Object target, final String methodName, Object parameter) throws RuntimeException {
		Object result = null;
		
		Class<?> targetClass = target.getClass();
		Class<?> parameterClass = parameter.getClass();
		
		while(targetClass != null) {
			try {
				Method method = target.getClass().getMethod(methodName, parameterClass);
				result = method.invoke(target, parameter);
				break;
			} 
			catch(NoSuchMethodException e) {
				targetClass = targetClass.getSuperclass(); 
			} 
			catch(InvocationTargetException e) {
				throw new RuntimeException(e.getTargetException());
			} catch(IllegalAccessException e) {
				throw new RuntimeException("Got IllegalAccessException during call " + methodName,e);
			}
		}
		return result;
		
	}
	
	/**
	 * instantiate a class without throwing a exception
	 * 
	 * @param <T>
	 * @param clazz
	 * @return
	 * @throws WrappedRuntimeException
	 */
	public static <T> T instantiateClass(final Class<T> clazz) throws WrappedRuntimeException {
		try {
			T result = clazz.newInstance();
			return result;
		}
		catch(Exception e) {
			throw new WrappedRuntimeException(e);
		}
		
	}
		
	@SuppressWarnings("unchecked")
	public static <T> T instantiateClass(final String clazzName) throws WrappedRuntimeException {
		
		Class<T> clazz;
		try {
			clazz = (Class<T>) Class.forName(clazzName);
		}
		catch (ClassNotFoundException e) {
			throw new WrappedRuntimeException(e);
		}
		return instantiateClass(clazz);
	}
	
}

