/*
 *
 *  Copyright 2015-2016 the original author or authors.
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
package springfox.documentation.spring.web;

import java.util.Objects;

import springfox.documentation.spi.service.contexts.RequestMappingContext;

public class OperationCachingEquivalence {

  private RequestMappingContext requestMappingContext;

  public OperationCachingEquivalence(RequestMappingContext requestMappingContext) {
    this.requestMappingContext = requestMappingContext;
  }

  public RequestMappingContext get() {
    return requestMappingContext;
  }

  @Override
  public int hashCode() {
    if (requestMappingContext == null) {
      return 0;
    }
    return doHash(requestMappingContext);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof OperationCachingEquivalence) {
      obj = ((OperationCachingEquivalence) obj).get();
    }
    if (requestMappingContext == obj) {
      return true;
    }
    if (requestMappingContext == null || obj == null) {
      return false;
    }
    if (requestMappingContext.getClass() != obj.getClass()) {
      return false;
    }
    return doEquivalent(requestMappingContext, (RequestMappingContext) obj);
  }

  protected boolean doEquivalent(RequestMappingContext first, RequestMappingContext second) {
    if (bothAreNull(first, second)) {
      return true;
    }
    if (eitherOfThemIsNull(first, second)) {
      return false;
    }
    return Objects.equals(first.key(), second.key())
        && Objects.equals(first.getGenericsNamingStrategy(), second.getGenericsNamingStrategy());
  }

  private boolean eitherOfThemIsNull(RequestMappingContext first, RequestMappingContext second) {
    return first.key() == null || second.key() == null;
  }

  private boolean bothAreNull(RequestMappingContext first, RequestMappingContext second) {
    return first.key() == null && second.key() == null;
  }

  protected int doHash(RequestMappingContext requestMappingContext) {
    return Objects.hash(requestMappingContext.key(), requestMappingContext.getRequestMappingPattern(),
        requestMappingContext.getGenericsNamingStrategy());
  }
}
