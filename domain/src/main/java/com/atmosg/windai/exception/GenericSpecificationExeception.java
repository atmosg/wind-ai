package com.atmosg.windai.exception;

public class GenericSpecificationExeception extends RuntimeException {

  public GenericSpecificationExeception(String message) {
    super(message);
  }

  public GenericSpecificationExeception(String message, Throwable cause) {
    super(message, cause);
  }

}
