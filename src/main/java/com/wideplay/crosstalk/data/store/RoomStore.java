package com.wideplay.crosstalk.data.store;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.googlecode.objectify.Objectify;
import com.wideplay.crosstalk.data.Occupancy;
import com.wideplay.crosstalk.data.Room;

import java.util.List;
import java.util.UUID;

/**
 * @author dhanji@gmail.com (Dhanji R. Prasanna)
 */
@Singleton
public class RoomStore {

  @Inject
  private Objectify objectify;

  public RoomStore() {
  }

  public Room byId(Long id) {
    Room room = objectify.find(Room.class, id);
    if (room == null) {
      return null;
    }

    loadOccupancy(room);
    return room;
  }

  private void loadOccupancy(Room room) {
    // Grab the occupancy too (they share the same id).
    Occupancy occupancy = objectify.get(Occupancy.class, room.getId());
    room.setOccupancy(occupancy);
  }

  public Room named(String name) {
    Room room = objectify.query(Room.class).filter("name", name).get();
    if (null == room) {
      return null;
    }

    // Load occupancy.
    loadOccupancy(room);
    return room;
  }

  public void remove(Long roomId) {
    // delete! (orphans both occupancy & posts).
    objectify.delete(Room.class, roomId);
  }

  public List<Room> list() {
    return objectify.query(Room.class).list();
  }

  public void create(String displayName) {
    // Derive a name for this room.
    String name = displayName.toLowerCase();

    Room room = new Room();
    room.setId(UUID.randomUUID().getMostSignificantBits());
    room.setName(name);
    room.setDisplayName(displayName);

    objectify.put(room);
    // Also create an occupancy with the same id.
    Occupancy occupancy = new Occupancy();
    occupancy.setId(room.getId());
    objectify.put(occupancy);
  }
}
