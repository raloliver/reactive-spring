package com.raloliver.reactivespring.controller;

import com.raloliver.reactivespring.model.Reservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import javax.sound.midi.Receiver;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static com.raloliver.reactivespring.controller.ReservationResource.APPLICATION_JSON_VALUE;
import static com.raloliver.reactivespring.controller.ReservationResource.ROOM_RESERVATION;

//@WebFluxTest(controllers = ReservationResourceTest.class)
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ReservationResourceTest {

    @Autowired
    private ApplicationContext context;
    private WebTestClient webTestClient;
    private Reservation reservation;

    @BeforeEach
    void setUp() {
        webTestClient = WebTestClient
                .bindToApplicationContext(this.context)
                .build();

        reservation = new Reservation(1L,
                LocalDate.now(),
                LocalDate.now().plus(10, ChronoUnit.DAYS),
                100);
    }

    @Test
    void getAllReservations() {

        webTestClient.get()
                .uri(ROOM_RESERVATION)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Reservation.class);
    }

    @Test
    void createReservation() {

        webTestClient.post()
                .uri(ROOM_RESERVATION)
                .body(Mono.just(reservation), Reservation.class)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(APPLICATION_JSON_VALUE)
                .expectBody(Reservation.class);
    }
}