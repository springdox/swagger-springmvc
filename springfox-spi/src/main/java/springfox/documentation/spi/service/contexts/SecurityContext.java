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

package springfox.documentation.spi.service.contexts;

import springfox.documentation.service.SecurityReference;

import java.util.List;
import java.util.function.Predicate;

/**
 * A class to represent a default set of authorizations to apply to each api operation
 * To customize which request mappings the list of authorizations are applied to Specify the custom includePatterns
 * or requestMethods
 */
public class SecurityContext {

  private final List<SecurityReference> securityReferences;
  private final Predicate<String> selector;

  public SecurityContext(List<SecurityReference> securityReferences, Predicate<String> selector) {

    this.securityReferences = securityReferences;
    this.selector = selector;
  }

  public List<SecurityReference> securityForPath(String path) {
    if (selector.test(path)) {
      return securityReferences;
    }
    return null;
  }

  public List<SecurityReference> getSecurityReferences() {
    return securityReferences;
  }

  public static SecurityContextBuilder builder() {
    return new SecurityContextBuilder();
  }

}
