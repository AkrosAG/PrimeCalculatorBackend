package ch.akros.cc.primecalculator.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import ch.akros.cc.primecalculator.model.Calculation;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class PrimeCalculationControllerTest {

   @Autowired
   private TestRestTemplate restTemplate;

   @Test
   public void postCalculation_shouldSaveNewPrimeNumber() {

      final Calculation calc = new Calculation(UUID.randomUUID(), 7, 300, "127.0.0.1");

      final ResponseEntity<Boolean> resp = restTemplate.postForEntity("/calc", calc, Boolean.class);

      assertEquals(HttpStatus.OK, resp.getStatusCode());
      assertTrue(resp.getBody());
   }

   @Test
   public void postSameCalculation_shouldSaveOneAndRejectOne() {

      final Calculation calc = new Calculation(UUID.randomUUID(), 3, 300, "127.0.0.1");

      ResponseEntity<Boolean> resp = restTemplate.postForEntity("/calc", calc, Boolean.class);

      assertEquals(HttpStatus.OK, resp.getStatusCode());

      resp = restTemplate.postForEntity("/calc", calc, Boolean.class);

      assertEquals(HttpStatus.OK, resp.getStatusCode());
      assertFalse(resp.getBody());
   }

   @Test
   public void getCalculations_shouldReturnListOfCalculations() {

      final ResponseEntity<Object[]> resp = restTemplate.getForEntity("/calc", Object[].class);

      assertEquals(HttpStatus.OK, resp.getStatusCode());
      assertNotNull(resp.getBody());
   }
}
