/*
 *
 *  Copyright 2015 the original author or authors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 *
 */
package springfox.documentation.swagger1.web;

import static springfox.documentation.builders.BuilderDefaults.nullToEmptyList;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import springfox.documentation.swagger1.dto.ApiListing;

public class ApiListingMerger {
  public static Optional<ApiListing> mergedApiListing(List<ApiListing> apiListings) {
    if (nullToEmptyList(apiListings).size() > 1) {
      ApiListing merged = new ApiListing();
      merged.setSwaggerVersion("1.2");
      merged.setPosition(0);
      for (ApiListing each : apiListings) {
        merged.setApiVersion(each.getApiVersion());
        merged.setBasePath(each.getBasePath());
        merged.setResourcePath(each.getResourcePath());
        merged.setDescription(each.getDescription());
        merged.appendAuthorizations(each.getAuthorizations());
        merged.appendApis(each.getApis());
        merged.appendProtocols(new HashSet<>(each.getProtocols()));
        merged.appendConsumes(new HashSet<>(each.getConsumes()));
        merged.appendModels(each.getModels());
        merged.appendProduces(new HashSet<>(each.getProduces()));
      }
      return Optional.of(merged);
    }
    return apiListings != null && !apiListings.isEmpty() ? Optional.of(apiListings.get(0)) : Optional.empty();
  }
}
