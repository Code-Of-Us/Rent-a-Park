package com.codeofus.rent_a_park.kafka.consumer;

import com.codeofus.rent_a_park.services.EmailService;
import com.codeofus.reservations.ReservationDto;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;


@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Slf4j
public class EmailSenderConsumer {
    final EmailService emailService;

    @Value("${email.sent-from}" )
    String sentFrom;

    @KafkaListener(topics = "${spring.kafka.reservation-topic}", groupId = "email-sender")
    public void sendEmails(ConsumerRecord<String, ReservationDto> record) {
        ReservationDto reservationMessage = record.value();
        log.info("Message received: {}", reservationMessage);

        String sentTo = reservationMessage.getPersonEmail();
        try {
            emailService.send(sentFrom, sentTo, "RENT-A-PARK", prepareEmailContent(reservationMessage));
            log.info("Email successfully sent to {}", sentTo);
        } catch (MessagingException ex) {
            log.error("Failed to send email to {}", sentTo);
        }
    }

    private String prepareEmailContent(ReservationDto message) {
        return String.format(
                "<p>Thank you for reserving parking!</p>You have reserved %s from %s until %s.",
                message.getSpotAddress(),
                message.getReservedFrom(),
                message.getReservedUntil()
        );
    }

}
