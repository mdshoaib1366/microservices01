package com.example.analytics_service.kafka;

import com.google.protobuf.InvalidProtocolBufferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import patient.events.PatientEvent;

@Service
public class KafkaConsumer {

    private static final Logger log = LoggerFactory.getLogger(KafkaConsumer.class);

    @KafkaListener(topics = "patient", groupId = "analytics-service")
    public void consumerEvent(byte[] event){
            try {
                PatientEvent patientEvent = PatientEvent.parseFrom(event);

                log.info("Received Patient Event: [PatientId={}, PatientEmail={}, PatientName={}," +
                                " PatientPhone={}, PatientEventType={}]",
                        patientEvent.getPatientId(),
                        patientEvent.getEmail(),
                        patientEvent.getName(),
                        patientEvent.getPhone(),
                        patientEvent.getEventType());

            } catch (InvalidProtocolBufferException e) {
                log.error("Error deserializing event: {}",e.getMessage());
            }
    }
}
