package com.atmosg.windai.parser.shared;

public interface ReportParser<T> {
  
  T parse(String rawText);
  
}
