package com.finalexample.demo.model.response;

import lombok.Data;

import java.util.List;

@Data
public class MyReservationsResponse {

    private List<ReservationResponse> myReservations;

    private int currentPage;

    private int totalPage;
}
