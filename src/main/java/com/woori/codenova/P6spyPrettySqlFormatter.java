package com.woori.codenova;

import org.hibernate.engine.jdbc.internal.FormatStyle;
import org.springframework.util.StringUtils;

import com.p6spy.engine.spy.appender.MessageFormattingStrategy;

public class P6spyPrettySqlFormatter implements MessageFormattingStrategy {
//	private String getServiceNameFromStackTrace() {
//		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
//
//		for (StackTraceElement element : stackTrace) {
//			// 예: "com.example.service" 패키지의 클래스를 서비스로 가정
//			if (element.getClassName().contains("service")) {
//				return element.getClassName() + "." + element.getMethodName();
//			}
//		}
//		return "UnknownService";
//	}

	@Override
	public String formatMessage(int connectionId, String now, long elapsed, String category, String prepared,
			String sql, String url) {

		StringBuilder sb = new StringBuilder();
		sb.append(category).append(" ").append(elapsed).append("ms");
		if (StringUtils.hasText(sql)) {
			sb.append(highlight(format(sql)));
		}
		return sb.toString();
	}

	private String format(String sql) {
		if (isDDL(sql)) {
			return FormatStyle.DDL.getFormatter().format(sql);
		} else if (isBasic(sql)) {
			return FormatStyle.BASIC.getFormatter().format(sql);
		}
		return sql;
	}

	private String highlight(String sql) {
		return FormatStyle.HIGHLIGHT.getFormatter().format(sql);
	}

	private boolean isDDL(String sql) {
		return sql.startsWith("create") || sql.startsWith("alter") || sql.startsWith("comment");
	}

	private boolean isBasic(String sql) {
		return sql.startsWith("select") || sql.startsWith("insert") || sql.startsWith("update")
				|| sql.startsWith("delete");
	}

}