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

import java.net.URI;
import java.net.URISyntaxException;

public class MediaObject extends CreativeWork {

  public MediaObject(String identifier) {
    super(identifier);
  }

  private URI contentUrl;
  private String encodingFormat;

  public URI getContentUrl() {
    return contentUrl;
  }

  public void setContentUrl(URI uri) {
    this.contentUrl = uri;
  }

  public void setContentUrl(String uri) {
    try {
      this.contentUrl = new URI(uri);
    } catch (URISyntaxException e) {
      e.printStackTrace();
    }
  }

  /** schema.org/encodingFormat value - a mimetype string like "video/ogg". */
  public String getEncodingFormat() {
    return encodingFormat;
  }

  /** Set the schema.org/encodingFormat value - a mimetype string like "video/ogg". */
  public void setEncodingFormat(String encodingFormat) {
    this.encodingFormat = encodingFormat;
  }
}
