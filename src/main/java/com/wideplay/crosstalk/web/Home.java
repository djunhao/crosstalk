package com.wideplay.crosstalk.web;

import com.google.inject.Inject;
import com.google.sitebricks.At;
import com.google.sitebricks.http.Get;
import com.wideplay.crosstalk.data.Room;
import com.wideplay.crosstalk.data.store.RoomStore;

import java.util.List;

/**
 * @author dhanji@gmail.com (Dhanji R. Prasanna)
 */
@At("/")
public class Home {
  @Inject
  private RoomStore roomStore;

  private List<Room> rooms;

  @Get
  void displayHome() {
    rooms = roomStore.list();
  }

  public List<Room> getRooms() {
    return rooms;
  }
}
