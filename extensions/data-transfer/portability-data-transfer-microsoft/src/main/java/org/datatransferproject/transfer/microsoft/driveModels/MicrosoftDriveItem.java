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

package org.datatransferproject.transfer.microsoft.driveModels;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Drive Item object in Microsoft Graph API. Ref:
 * https://docs.microsoft.com/en-us/graph/api/resources/driveitem?view=graph-rest-1.0
 */
public class MicrosoftDriveItem {

  @JsonProperty("id")
  public String id;

  @JsonProperty("name")
  public String name;

  @JsonProperty("folder")
  public MicrosoftDriveFolder folder;

  @JsonProperty("photo")
  public MicrosoftPhotoMetadata photo;

  @JsonProperty("video")
  public MicrosoftVideoMetadata video;

  @JsonProperty("file")
  public MicrosoftFileMetadata file;

  @JsonProperty("description")
  public String description;

  @JsonProperty("@microsoft.graph.downloadUrl")
  public String downloadUrl;


  public boolean isFolder() {
    return folder != null;
  }

  public boolean isFile() {
    return file != null;
  }

  /** Encodes mimetype tree for simple string matching. */
  private enum MimeTypePrefix {
    IMAGE("image"),
    VIDEO("video")
      ;

    private final String prefix;
    MimeTypePrefix(String prefix) {
      this.prefix = prefix;
    }

    /** Whether mimeType is contained by the current prefix. */
    public boolean containsType(String mimeType) {
      return mimeType != null && mimeType.startsWith(this.prefix + "/");
    }
  }

  private boolean isMimeType(MimeTypePrefix prefix) {
    return prefix.containsType(file.mimeType);
  }

  public boolean isImage() {
    return isFile() && isMimeType(MimeTypePrefix.IMAGE);
  }

  public boolean isVideo() {
    return isFile() && isMimeType(MimeTypePrefix.VIDEO);
  }
}
