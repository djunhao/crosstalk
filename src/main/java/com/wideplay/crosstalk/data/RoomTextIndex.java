package com.wideplay.crosstalk.data;

import com.google.common.collect.Lists;
import com.google.gson.annotations.SerializedName;
import com.googlecode.objectify.annotation.Cached;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.List;

/**
 * @author dhanji@gmail.com (Dhanji R. Prasanna)
 */
@Cached @Entity
public class RoomTextIndex {
  @Id
  private Long id;

  @Embedded
  private List<WordTuple> words = Lists.newArrayList();

  public void setId(long id) {
    this.id = id;
  }

  public static class WordTuple implements Comparable<WordTuple>, Serializable {
    private static final long serialVersionUID = 0L;

    @SerializedName("title")
    private String word;
    @SerializedName("rank")
    private int count;
    @SerializedName("room")
    private String roomName;

    public String getWord() {
      return word;
    }

    public int getCount() {
      return count;
    }

    public int compareTo(WordTuple that) {
      return that.count - this.count;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (!(o instanceof WordTuple)) return false;

      WordTuple that = (WordTuple) o;
      return that.word.equals(this.word);
    }

    @Override
    public int hashCode() {
      return word != null ? word.hashCode() : 0;
    }

    public void set(String key, Integer value) {
      this.word = key;
      this.count = value;
    }

    @Override
    public String toString() {
      return word + '(' + count + ')';
    }

    public void setCount(int count) {
      this.count = count;
    }

    public void setRoomName(String roomName) {
      this.roomName = roomName;
    }
  }

  public void setRoom(Room room) {
    this.id = room.getId();
  }

  public List<WordTuple> getWords() {
    return words;
  }

  public void setWords(List<WordTuple> words) {
    this.words = words;
  }
}
