/*
 * Copyright 2019 The Data Transfer Project Authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.datatransferproject.types.common.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.annotation.Nonnull;
import org.datatransferproject.types.common.ImportableItem;

public class Thing implements ImportableItem {

  private final String identifier;
  private String name;
  private String description;

  public Thing(String identifier) {
    if (identifier == null || identifier.isEmpty()) {
      throw new IllegalArgumentException("identifier must be set");
    }
    this.identifier = identifier;
  }

  public String getIdentifier() {
    return identifier;
  }

  @Nonnull
  @Override
  public String getIdempotentId() {
    return getIdentifier();
  }

  @JsonIgnore(false) // we do want name in the json representation, so override the annotation
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
