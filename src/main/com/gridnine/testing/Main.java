package com.gridnine.testing;


import java.util.List;

public class Main {
    public static void main(String[] args) {
        FilterFlight filter = new FilterFlightImpl();
        List<Flight> flights = FlightBuilder.createFlights();

        System.out.println("All flights:");
        showFlights(flights);
        System.out.println();

        System.out.println("Flights departing after the current time:");
        showFlights(filter.getListFlights_afterCurrentTime(flights));
        System.out.println();

        System.out.println("Flights with departure date before arrival date:");
        showFlights(filter.getListFlights_withDepartureDateBeforeArrivalDate(flights));
        System.out.println();

        System.out.println("Flights with a total ground time of less than two hours:");
        showFlights(filter.getListFlights_totalGroundTimeLessThanTwoHours(flights));

    }

    public static void showFlights(List<Flight> flights) {
        if (flights == null) {
            throw new IllegalArgumentException(
                    "list of flights is null");
        }
        for (Flight flight : flights) {
            System.out.println(flight.getSegments());
        }
    }
}
