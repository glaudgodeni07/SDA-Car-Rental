package com.finalexample.demo.controllers;

import com.finalexample.demo.model.request.AddReservationRequest;
import com.finalexample.demo.model.response.MyReservationsResponse;
import com.finalexample.demo.services.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class MyUserController extends BaseController{

    private final ReservationService reservationService;

    @GetMapping("/reserve")
    public String getMyReservationView(@PageableDefault(value = 0,size=5)Pageable pageable,Model model){
        String username=extractUsername();
        MyReservationsResponse myReservationResponse =
                reservationService.getReservationsByUsername(username, pageable);

        model.addAttribute("myReservationResponse",myReservationResponse);
        return ok("my-reservations",model);
    }

    @PostMapping("/reserve")
    public String reserveCarForUser(@ModelAttribute("addReservationRequest")
                                    AddReservationRequest addReservationRequest,Model model){

        String username=extractUsername();
        addReservationRequest.setUsername(username);
        //Add reservation
        Boolean added = reservationService.addReservation(addReservationRequest);

        if(added){

            return success("Success",model);
        }
        return error("This car is reserved on these dates!!. Please provide different ranges",model);
    }

}
