package com.raloliver.reactivespring.controller;

import com.raloliver.reactivespring.model.Reservation;
import com.raloliver.reactivespring.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(ReservationResource.ROOM_RESERVATION)
@CrossOrigin
public class ReservationResource {

    public static final String ROOM_RESERVATION = "room/reservation";
    public static final String APPLICATION_JSON_VALUE = MediaType.APPLICATION_JSON_VALUE;

    private final ReservationService reservationService;

    /**
     * @param reservationService
     * @Autowired Spring will now inject a instance of
     * the ReservationService implementation into our controller.
     */
    @Autowired
    public ReservationResource(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    /**
     * @param roomId
     * @return
     * @PathVariable. Because the variable name and the path parameter match,
     * string will know to extract that path parameter to this variable
     */
    @GetMapping(path = "{id}", produces = APPLICATION_JSON_VALUE)
    public Mono<Reservation> getReservationById(@PathVariable String id) {

        return reservationService.getReservation(id);
    }

    @GetMapping(path = "", produces = APPLICATION_JSON_VALUE)
    public Flux<Reservation> getAllReservations() {

        return reservationService.listAllReservations();
    }

    /**
     * @param reservation
     * @return
     * @RequestBody This annotation will deserialize a JSON RequestBody into a reservation object.
     */
    @PostMapping(path = "", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public Mono<Reservation> createReservation(@RequestBody Mono<Reservation> reservation) {

        return reservationService.createReservation(reservation);
    }

    @PutMapping(path = "{id}", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public Mono<Reservation> updatePrice(@PathVariable String id, @RequestBody Mono<Reservation> reservation) {

        return reservationService.updateReservation(id, reservation);
    }

    @DeleteMapping(path = "{id}")
    public Mono<Boolean> deleteReservation(@PathVariable String id) {

        return reservationService.deleteReservation(id);
    }
}


