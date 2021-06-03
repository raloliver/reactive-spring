package com.raloliver.reactivespring.controller;

import com.raloliver.reactivespring.model.Reservation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(ReservationResource.ROOM_RESERVATION)
@CrossOrigin
public class ReservationResource {

    public static final String ROOM_RESERVATION = "room/reservation";
    public static final String APPLICATION_JSON_VALUE = MediaType.APPLICATION_JSON_VALUE;

    /**
     * @PathVariable. Because the variable name and the path parameter match,
     * string will know to extract that path parameter to this variable
     * @param roomId
     * @return
     */
    @GetMapping(path = "{roomId}", produces = APPLICATION_JSON_VALUE)
    public Mono<String> getReservationById(@PathVariable String roomId) {

        //reservationService.getReservation(roomId);
        return Mono.just("{}");
    }

    /**
     * @RequestBody This annotation will deserialize a JSON RequestBody into a reservation object.
     * @param reservation
     * @return
     */
    @PostMapping(path = "", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public Mono<String> createReservation(@RequestBody Mono<Reservation> reservation) {

        return Mono.just("{}");
    }

    @PutMapping(path = "{roomId}", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public Mono<String> updatePrice(@PathVariable String roomId, @RequestBody Mono<Reservation> reservation) {

        return Mono.just("{}");
    }

    @DeleteMapping(path = "{roomId}")
    public Mono<Boolean> deleteReservation(@PathVariable String roomId) {

        return Mono.just(true);
    }
}


