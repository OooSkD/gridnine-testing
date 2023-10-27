package com.gridnine.testing;

import java.util.List;

public interface FilterFlight {
    List<Flight> getListFlights_afterCurrentTime(List<Flight> flights);
    List<Flight> getListFlights_withDepartureDateBeforeArrivalDate(List<Flight> flights);
    List<Flight> getListFlights_totalGroundTimeLessThanTwoHours(List<Flight> flights);
}
