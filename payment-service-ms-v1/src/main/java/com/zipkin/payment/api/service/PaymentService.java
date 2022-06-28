package com.zipkin.payment.api.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zipkin.payment.api.entity.Payment;
import com.zipkin.payment.api.repository.PaymentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Random;
import java.util.UUID;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository repository;

    Logger logger= LoggerFactory.getLogger(PaymentService.class);

    public Payment doPayment(Payment payment) throws JsonProcessingException {
        payment.setPaymentStatus(paymentProcessing());
        payment.setTransactionId(UUID.randomUUID().toString());
        logger.info("Payment-Service Request : {}",new ObjectMapper().writeValueAsString(payment));

        return repository.save(payment);
    }


    public String paymentProcessing(){
        return new Random().nextBoolean()?"success":"false";
    }

    public Payment findPaymentHistoryByOrderId(int orderId) {
        try{
            Payment payment=repository.findByOrderId(orderId);
            logger.info("paymentService findPaymentHistoryByOrderId : {}",new ObjectMapper().writeValueAsString(payment));
            return payment ;
        } catch (JsonProcessingException e) {
            logger.error("Unable to parse json, exception: {}",e.getMessage());
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,"Unable to parse json");
        }
        catch (Exception ex) {
            logger.error("Exception occurred {}",ex.getMessage());
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,"Unable to parse json");
        }
    }
}