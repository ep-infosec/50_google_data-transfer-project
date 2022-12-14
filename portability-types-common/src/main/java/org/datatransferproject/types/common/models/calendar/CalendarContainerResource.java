/*
 * Copyright 2018 The Data Transfer Project Authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.datatransferproject.types.common.models.calendar;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.google.common.collect.ImmutableList;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

import com.google.common.collect.ImmutableMap;
import org.datatransferproject.types.common.models.ContainerResource;

/** A Wrapper for all the possible objects that can be returned by a calendar exporter. */
@JsonTypeName("CalendarContainerResource")
public class CalendarContainerResource extends ContainerResource {
  public static final String CALENDARS_COUNT_DATA_NAME = "calendarsCount";
  public static final String EVENTS_COUNT_DATA_NAME = "eventsCount";

  private final Collection<CalendarModel> calendars;
  private final Collection<CalendarEventModel> events;

  @JsonCreator
  public CalendarContainerResource(
      @JsonProperty("calendars") Collection<CalendarModel> calendars,
      @JsonProperty("events") Collection<CalendarEventModel> events) {
    this.calendars = calendars == null ? ImmutableList.of() : calendars;
    this.events = events == null ? ImmutableList.of() : events;
  }

  public Collection<CalendarModel> getCalendars() {
    return calendars;
  }

  public Collection<CalendarEventModel> getEvents() {
    return events;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    CalendarContainerResource that = (CalendarContainerResource) o;
    return Objects.equals(getCalendars(), that.getCalendars()) &&
            Objects.equals(getEvents(), that.getEvents());
  }

  @Override
  public Map<String, Integer> getCounts() {
    return new ImmutableMap.Builder<String, Integer>()
            .put(CALENDARS_COUNT_DATA_NAME, calendars.size())
            .put(EVENTS_COUNT_DATA_NAME, events.size())
            .build();
  }

  @Override
  public int hashCode() {
    return Objects.hash(getCalendars(), getEvents());
  }
}
