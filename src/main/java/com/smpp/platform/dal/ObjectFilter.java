package com.smpp.platform.dal;

public interface ObjectFilter<T> {
	boolean accept(T object);
}
