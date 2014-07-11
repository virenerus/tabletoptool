package com.t3.xstreamversioned;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;

/**
 * Use this class to represent a change in the serialized part of a class. You then have to create a converter in
 * com.t3.persitence.migrators that converts old versions of the type into newer ones. 
 * @author Virenerus
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface SerializationVersion {
	public int value();
}
