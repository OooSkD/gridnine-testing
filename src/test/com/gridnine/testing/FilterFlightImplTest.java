package com.gridnine.testing;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FilterFlightImplTest {

    FilterFlight filter = new FilterFlightImpl();
    //a normal flights for all test
    static List<Flight> normal_flights_for_all_test = new ArrayList<>();
    //A flight departing in the past
    static List<Flight> flights_for_test1 = new ArrayList<>();
    //A flight that departs before it arrives;
    static List<Flight> flights_for_test2 = new ArrayList<>();
    //flight with more than two hours ground time
    static List<Flight> flights_for_test3 = new ArrayList<>();

    //filling an array of flights with test values
    @BeforeAll
    static void setUpBeforeAll() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime threeDaysFromNow = now.plusDays(3);
        //A normal flight with two hour duration
        normal_flights_for_all_test.add(new Flight(Arrays.asList(new Segment(threeDaysFromNow, threeDaysFromNow.plusHours(2)))));
        //A normal multi segment flight
        normal_flights_for_all_test.add(new Flight(Arrays.asList(
                new Segment(now.plusHours(12), now.plusHours(15)),
                new Segment(now.plusHours(16), now.plusHours(18)),
                new Segment(now.plusHours(18).plusMinutes(30), now.plusDays(1)))));
        normal_flights_for_all_test.add(new Flight(Arrays.asList(
                new Segment(now.plusHours(12), now.plusHours(15)),
                new Segment(now.plusHours(16).plusMinutes(30), now.plusDays(1)))));

        //A flights departing in the past(days,hours and minutes)
        flights_for_test1.add(new Flight(Arrays.asList(new Segment(now.minusDays(3), threeDaysFromNow))));
        flights_for_test1.add(new Flight(Arrays.asList(new Segment(now.minusHours(3), threeDaysFromNow))));
        flights_for_test1.add(new Flight(Arrays.asList(new Segment(now.minusMinutes(3), threeDaysFromNow))));

        //one segment flight that departs before it arrives;
        flights_for_test2.add(new Flight(Arrays.asList(new Segment(threeDaysFromNow, threeDaysFromNow.minusHours(1)))));
        //A multi segment flight that departs before it arrives;
        flights_for_test2.add(new Flight(Arrays.asList(
                new Segment(threeDaysFromNow.minusDays(1), threeDaysFromNow),
                new Segment(threeDaysFromNow.plusHours(1), threeDaysFromNow.minusDays(1)))));
        flights_for_test2.add(new Flight(Arrays.asList(
                new Segment(threeDaysFromNow.minusDays(1), threeDaysFromNow),
                new Segment(threeDaysFromNow.plusHours(1), threeDaysFromNow),
                new Segment(threeDaysFromNow.plusHours(1), threeDaysFromNow.plusHours(8)))));

        //flight with more than two hours ground time in one segment
        flights_for_test3.add(new Flight(Arrays.asList(
                new Segment(threeDaysFromNow.minusDays(1), threeDaysFromNow),
                new Segment(threeDaysFromNow.plusHours(3), threeDaysFromNow.minusDays(1)))));
        //flight with more than two hours ground time in total segments
        flights_for_test3.add(new Flight(Arrays.asList(
                new Segment(threeDaysFromNow.minusDays(1), threeDaysFromNow),
                new Segment(threeDaysFromNow.plusHours(1), threeDaysFromNow.plusHours(7)),
                new Segment(threeDaysFromNow.plusHours(8).plusMinutes(30), threeDaysFromNow.plusHours(17)))));

    }

    @Test
    void getListFlights_afterCurrentTime() {
        flights_for_test1.addAll(normal_flights_for_all_test);
        List<Flight> result = filter.getListFlights_afterCurrentTime(flights_for_test1);
        assertArrayEquals(result.toArray(), normal_flights_for_all_test.toArray());
    }

    @Test
    void getListFlights_withDepartureDateBeforeArrivalDate() {
        flights_for_test2.addAll(normal_flights_for_all_test);
        List<Flight> result = filter.getListFlights_withDepartureDateBeforeArrivalDate(flights_for_test2);
        assertArrayEquals(result.toArray(), normal_flights_for_all_test.toArray());
    }

    @Test
    void getListFlights_ofTotalTimeSpentOnEarthLessThanTwoHours() {
        flights_for_test3.addAll(normal_flights_for_all_test);
        List<Flight> result = filter.getListFlights_totalGroundTimeLessThanTwoHours(flights_for_test3);
        assertArrayEquals(result.toArray(), normal_flights_for_all_test.toArray());
    }


}