/*
 * Copyright: (c) 2017 AKROS AG
 */
package ch.akros.cc.primecalculator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PrimeCalculatorBackend {

   public static void main(final String[] args) {

      SpringApplication.run(PrimeCalculatorBackend.class, args);
   }

}
