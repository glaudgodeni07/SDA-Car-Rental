package com.finalexample.demo.services;

import com.finalexample.demo.model.entity.CarListing;
import com.finalexample.demo.model.response.AllCarsResponse;
import com.finalexample.demo.model.response.CarListingResponse;
import com.finalexample.demo.repository.CarListingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CarListingService {

    private final CarListingRepository carListingRepository;

    private final ImageService imageService;

    public AllCarsResponse retrieveAllListings(Pageable pageable){

        Page<CarListing>carListingsPage=carListingRepository.findAll(pageable);

        List<CarListingResponse>carListingResponses= carListingsPage
                .stream()
                .map(this::buildCarListingResponse)
                .collect(Collectors.toList());

        int currentPage=pageable.getPageNumber();
        int totalPages=carListingsPage.getTotalPages();

        AllCarsResponse allCarsResponse=new AllCarsResponse();
        allCarsResponse.setListingResponses(carListingResponses);
        allCarsResponse.setCurrentPage(currentPage);
        allCarsResponse.setTotalPage(totalPages);

        return allCarsResponse;
    }

    public List<CarListingResponse> retrieveAllListings(){

        List<CarListing> carListings=carListingRepository.findAll();

        List<CarListingResponse>carListingResponses=carListings
                .stream()
                .map(this::buildCarListingResponse)
                .collect(Collectors.toList());

        return carListingResponses;

    }

    public CarListingResponse retrieveCarListingById(Long id){
        Optional<CarListing> optionalCarListing =carListingRepository.findById(id);

        if (optionalCarListing.isEmpty()){
            throw new RuntimeException("This car does not exist");
        }

        CarListing carListing=optionalCarListing.get();
        CarListingResponse carListingResponse=buildCarListingResponse(carListing);

        return carListingResponse;

    }


    private CarListingResponse buildCarListingResponse(CarListing carListing){
        CarListingResponse carListingResponse=new CarListingResponse();

        carListingResponse.setId(carListing.getId());
        carListingResponse.setTitle(carListing.getTitle());
        carListingResponse.setDescription(carListing.getDescription());
        carListingResponse.setPrice(carListing.getPrice());

        //Create /upload-image/<ID>/<IMAGE_NAME>
        String imagePath=imageService.getImagePath(carListing.getId(),carListing.getImageName());
        carListingResponse.setImagePath(imagePath);

        carListingResponse.setPower(carListing.getCar().getPower());
        carListingResponse.setFuelType(carListing.getCar().getFuelType());
        carListingResponse.setBrand(carListing.getCar().getBrand());
        carListingResponse.setModel(carListing.getCar().getModel());
        carListingResponse.setYear(carListing.getCar().getYear());

        return carListingResponse;

    }
    public CarListingResponse retrieveById(Long id) {
        CarListing carListing = carListingRepository
                .findById(id).orElseThrow(() -> new NoSuchElementException("Car Listing not found!"));

        return buildCarListingResponse(carListing);
    }
}
