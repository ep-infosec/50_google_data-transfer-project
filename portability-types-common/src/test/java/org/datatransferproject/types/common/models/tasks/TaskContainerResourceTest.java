package org.datatransferproject.types.common.models.tasks;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.common.collect.ImmutableList;
import com.google.common.truth.Truth;

import java.util.List;
import org.datatransferproject.types.common.models.ContainerResource;
import org.junit.jupiter.api.Test;

public class TaskContainerResourceTest {

  @Test
  public void verifySerializeDeserialize() throws Exception {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
    objectMapper.registerSubtypes(TaskContainerResource.class);

    List<TaskListModel> taskLists = ImmutableList.of(new TaskListModel("id1", "List 1"));

    List<TaskModel> tasks =
        ImmutableList.of(
            new TaskModel("id1", "Write Better tests", "Do this soon", null, null),
            new TaskModel("id2", "Liberate all the data", "do this in stages", null, null));

    ContainerResource data = new TaskContainerResource(taskLists, tasks);

    String serialized = objectMapper.writeValueAsString(data);

    ContainerResource deserializedModel =
        objectMapper.readValue(serialized, ContainerResource.class);

    Truth.assertThat(deserializedModel).isNotNull();
    Truth.assertThat(deserializedModel).isInstanceOf(TaskContainerResource.class);
    TaskContainerResource deserialized = (TaskContainerResource) deserializedModel;
    Truth.assertThat(deserialized.getLists()).hasSize(1);
    Truth.assertThat(deserialized.getTasks()).hasSize(2);
    Truth.assertThat(deserialized).isEqualTo(data);
  }
}
