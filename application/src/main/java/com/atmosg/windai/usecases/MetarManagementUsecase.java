package com.atmosg.windai.usecases;

import com.atmosg.windai.vo.metar.Metar;

public interface MetarManagementUsecase {
  
  Metar createMetar(String rawText);

}
