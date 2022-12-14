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

package org.datatransferproject.datatransfer.google;

import static org.datatransferproject.types.common.models.DataVertical.SOCIAL_POSTS;

import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import java.io.IOException;
import org.datatransferproject.api.launcher.ExtensionContext;
import org.datatransferproject.api.launcher.Monitor;
import org.datatransferproject.datatransfer.google.blogger.GoogleBloggerImporter;
import org.datatransferproject.datatransfer.google.common.GoogleCredentialFactory;
import org.datatransferproject.spi.cloud.storage.AppCredentialStore;
import org.datatransferproject.types.common.models.DataVertical;
import org.datatransferproject.spi.transfer.extension.TransferExtension;
import org.datatransferproject.spi.transfer.provider.Exporter;
import org.datatransferproject.spi.transfer.provider.Importer;
import org.datatransferproject.types.transfer.auth.AppCredentials;

/*
 * BloggerTransferExtension allows for importers and exporters (not yet implemented) of
 * Google Blogger data.
 */
// This needs to be separated out from Google, as there are multiple services that want to
// handle SOCIAL-POSTS.
public class BloggerTransferExtension implements TransferExtension {

  private static final String SERVICE_ID = "GoogleBlogger";

  // TODO: centralized place, or enum type for these
  private static final ImmutableList<DataVertical> SUPPORTED_SERVICES =
      ImmutableList.of(SOCIAL_POSTS);
  private ImmutableMap<DataVertical, Importer> importerMap;
  private ImmutableMap<DataVertical, Exporter> exporterMap;
  private boolean initialized = false;

  @Override
  public String getServiceId() {
    return SERVICE_ID;
  }

  @Override
  public Exporter<?, ?> getExporter(DataVertical transferDataType) {
    Preconditions.checkArgument(initialized);
    Preconditions.checkArgument(SUPPORTED_SERVICES.contains(transferDataType));
    return exporterMap.get(transferDataType);
  }

  @Override
  public Importer<?, ?> getImporter(DataVertical transferDataType) {
    Preconditions.checkArgument(initialized);
    Preconditions.checkArgument(SUPPORTED_SERVICES.contains(transferDataType));
    return importerMap.get(transferDataType);
  }

  @Override
  public void initialize(ExtensionContext context) {
    // Note: initialize could be called twice in an account migration scenario where we import and
    // export to the same service provider. So just return rather than throwing if called multiple
    // times.
    if (initialized) {
      return;
    }
    Monitor monitor = context.getMonitor();

    AppCredentials appCredentials;
    try {
      appCredentials =
          context
              .getService(AppCredentialStore.class)
              .getAppCredentials("GOOGLEBLOGGER_KEY", "GOOGLEBLOGGER_SECRET");
    } catch (IOException e) {
      monitor.info(
          () -> "Unable to retrieve Google AppCredentials. "
              + "Did you set GOOGLEBLOGGER_KEY and GOOGLEBLOGGER_SECRET?");
      return;
    }

    HttpTransport httpTransport = context.getService(HttpTransport.class);
    JsonFactory jsonFactory = context.getService(JsonFactory.class);

    // Create the GoogleCredentialFactory with the given {@link AppCredentials}.
    GoogleCredentialFactory credentialFactory =
        new GoogleCredentialFactory(httpTransport, jsonFactory, appCredentials, monitor);

    ImmutableMap.Builder<DataVertical, Importer> importerBuilder = ImmutableMap.builder();

    importerBuilder.put(SOCIAL_POSTS, new GoogleBloggerImporter(credentialFactory));

    importerMap = importerBuilder.build();

    ImmutableMap.Builder<DataVertical, Exporter> exporterBuilder = ImmutableMap.builder();

    exporterMap = exporterBuilder.build();

    initialized = true;
  }
}
