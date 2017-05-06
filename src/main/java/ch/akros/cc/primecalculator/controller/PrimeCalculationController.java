/*
 * Copyright: (c) 2017 AKROS AG
 */
package ch.akros.cc.primecalculator.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ch.akros.cc.primecalculator.model.Calculation;
import ch.akros.cc.primecalculator.repo.CalculationRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = { "Calculation", })
@RestController
@RequestMapping("calc")
public class PrimeCalculationController {

   private static final Logger   LOG = LoggerFactory.getLogger(PrimeCalculationController.class);

   @Autowired
   private CalculationRepository calcRepository;

   @ApiOperation(value = "Alle errechneten Primzahlen im Repository", response = Calculation.class, responseContainer = "Iterable")
   @RequestMapping(method = RequestMethod.GET)
   public List<Calculation> getCalculations() {

      return (List<Calculation>) calcRepository.findAll();
   }

   @ApiOperation(value = "Neue Primzahl hinzufügen", response = Boolean.class)
   @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
   public ResponseEntity<Boolean> add(@RequestBody final Calculation calc) {

      LOG.debug("Neue Berechnung erhalten. uuid={} ip={} prime={} calctime={}", calc.getTraceNumber(), calc.getIp(),
            calc.getPrimeNumber(), calc.getCalcTime());

      final HttpHeaders httpHeaders = new HttpHeaders();
      if (calcRepository.findByPrimeNumber(calc.getPrimeNumber()) == null) {
         LOG.info("Nicht vorhandene Primzahl {} wird dem Repository hinzugefügt. uuid={} ip={} prime={} calctime={}",
               calc.getPrimeNumber(), calc.getTraceNumber(), calc.getIp(), calc.getPrimeNumber(), calc.getCalcTime());
         calcRepository.save(calc);
         return new ResponseEntity<>(Boolean.TRUE, httpHeaders, HttpStatus.OK);
      }

      LOG.warn("Primzahl {} bereits im Repository vorhanden. Wird verworfen! uuid={} ip={} prime={} calctime={}",
            calc.getTraceNumber(), calc.getIp(), calc.getPrimeNumber(), calc.getCalcTime());
      return new ResponseEntity<>(Boolean.FALSE, httpHeaders, HttpStatus.OK);
   }
}
