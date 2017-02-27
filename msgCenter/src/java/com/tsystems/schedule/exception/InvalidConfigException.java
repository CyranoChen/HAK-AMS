package com.tsystems.schedule.exception;

/**
 * @ClassName: InvalidConfigException
 * @Description: TODO(异常类)
 * @author zhoushun
 * @date 2012-11-28 上午11:30:06
 * 
 */
@SuppressWarnings("serial")
public class InvalidConfigException extends RuntimeException {
	private static final String msg = "配置无效，请检查t_schedule_config配置表！";

	public InvalidConfigException() {
		super();
	}

	public InvalidConfigException(String message, Throwable cause) {
		super(msg + message, cause);
	}

	public InvalidConfigException(String message) {
		super(msg + message);
	}

	public InvalidConfigException(Throwable cause) {
		super(cause);
	}

}
