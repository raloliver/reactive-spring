package com.raloliver.reactivespring.service;

import com.raloliver.reactivespring.model.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

// The service annotation is a specialization of the component annotation.
// This service class can now be injected where it's needed.

@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReactiveMongoOperations reactiveMongoOperations;

    @Autowired
    public ReservationServiceImpl(ReactiveMongoOperations reactiveMongoOperations) {
        this.reactiveMongoOperations = reactiveMongoOperations;
    }

    @Override
    public Mono<Reservation> getReservation(String id) {
        return reactiveMongoOperations.findById(id, Reservation.class);
    }

    @Override
    public Mono<Reservation> createReservation(Mono<Reservation> reservationMono) {
        return reactiveMongoOperations.save(reservationMono);
    }

    /**
     * .save is actually an upsert.
     * If it doesn't already exist it will do an insert, otherwise it will do an update.
     *
     * @param id
     * @param reservationMono
     * @return
     */
    @Override
    public Mono<Reservation> updateReservation(String id, Mono<Reservation> reservationMono) {
        //Upsert functionality
        //return reactiveMongoOperations.save(reservationMono);

        //Update just price
        return reservationMono.flatMap(reservation -> reactiveMongoOperations.findAndModify(
                Query.query(Criteria.where("id").is(id)),
                Update.update("price", reservation.getPrice()),
                Reservation.class).flatMap(result -> {
                    result.setPrice(reservation.getPrice());
                    return Mono.just(result);
                })
        );
    }

    /**
     * Since MongoDB is a distributed database, a delete result won't
     * necessarily propagate to every single node before you get a
     * successful acknowledgement. However, we will return true,
     * if a delete operation was acknowledged.
     *
     * @param id
     * @return
     */
    @Override
    public Mono<Boolean> deleteReservation(String id) {
        return reactiveMongoOperations.remove(
                Query.query(Criteria.where("id").is(id)), Reservation.class)
                .flatMap(deleteResult -> Mono.just(deleteResult.wasAcknowledged()));
    }

    @Override
    public Flux<Reservation> listAllReservations() {
        return reactiveMongoOperations.findAll(Reservation.class);
    }
}
