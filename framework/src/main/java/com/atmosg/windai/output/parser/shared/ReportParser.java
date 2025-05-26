package org.windai.domain.policy.parser.shared;

public interface ReportParser<T> {
  
  T parse(String rawText);
  
}
