package com.atmosg.windai.output.parser.shared;

public interface ReportParser<T> {
  
  T parse(String rawText);
  
}
