package com.atmosg.windai.input.mesonet;

import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import com.atmosg.windai.parser.metar.generic.factory.MetarParser;
import com.atmosg.windai.parser.metar.mesonet.IssuedTimeRegexParser;
import com.atmosg.windai.parser.shared.ReportParser;
import com.atmosg.windai.ports.input.MetarManagementInputPort;
import com.atmosg.windai.ports.output.MetarManagementOutputPort;
import com.atmosg.windai.usecases.MetarManagementUseCase;
import com.atmosg.windai.vo.metar.Metar;

public class MetarManagementMesonetInputAdapter {
  
  private MetarManagementUseCase metarManagementUseCase;
  private ReportParser<ZonedDateTime> issuedTimeParser;
  private MetarParser parser;
  
  public MetarManagementMesonetInputAdapter() {
    issuedTimeParser = new IssuedTimeRegexParser();
    parser = new MetarParser(YearMonth.now(ZoneId.of("UTC")));
  }

  public void persist(String rawText) {
    ZonedDateTime issuedTime = issuedTimeParser.parse(rawText);
    String metarRawText = extractMetarRawText(rawText);
    
    parser.setYearMonth(YearMonth.from(issuedTime));
    Metar metar = parser.parse(metarRawText);

    metarManagementUseCase.persistMetar(metar, issuedTime);
  }


  private String extractMetarRawText(String mesonetRawText) {
    return mesonetRawText.split(",")[2];
  }

  // private void setPorts() {
  //   this.metarManagementUseCase = new MetarManagementInputPort(
  //     new MetarManagementOutputPort() {
        
  //     };
  //   );
  // }

}
