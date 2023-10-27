package com.gridnine.testing;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FilterFlightImpl implements FilterFlight {
    @Override
    public List<Flight> getListFlights_afterCurrentTime(List<Flight> flights) {
        if (flights == null || flights.isEmpty())
            throw new IllegalArgumentException(
                    "list of flights is empty in the filter \"afterCurrentTime\"");
        return flights.stream()
                .filter(flight -> flight.getSegments().get(0).getDepartureDate().isAfter(LocalDateTime.now()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Flight> getListFlights_withDepartureDateBeforeArrivalDate(List<Flight> flights) {
        if (flights == null || flights.isEmpty())
            throw new IllegalArgumentException(
                    "list of flights is empty in the filter \"withDepartureDateBeforeArrivalDate\"");
        return flights.stream()
                .filter(flight -> flight.getSegments().stream()
                        .allMatch(segment -> segment.getArrivalDate().isAfter(segment.getDepartureDate())))
                .collect(Collectors.toList());
    }

    @Override
    public List<Flight> getListFlights_totalGroundTimeLessThanTwoHours(List<Flight> flights) {
        if (flights == null || flights.isEmpty())
            throw new IllegalArgumentException(
                    "list of flights is empty in the filter \"totalGroundTimeLessThanTwoHours\"");
        List<Flight> result = new ArrayList<>();
        for (Flight flight : flights) {
            long timeOnEarth = 0;
            List<Segment> segments = flight.getSegments();
            for (int i = 0; i < segments.size() - 1 && timeOnEarth < 7200; i++) {
                timeOnEarth += ChronoUnit.SECONDS.between(segments.get(i).getArrivalDate(),
                        segments.get(i + 1).getDepartureDate());
            }
            if (timeOnEarth < 7200)
                result.add(flight);
        }
        return result;
    }
}
