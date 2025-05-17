package com.atmosg.windai.exception;

public class GenericPolicyException extends RuntimeException {

  public GenericPolicyException(String message) {
    super(message);
  }

  public GenericPolicyException(String message, Throwable cause) {
    super(message, cause);
  }

}