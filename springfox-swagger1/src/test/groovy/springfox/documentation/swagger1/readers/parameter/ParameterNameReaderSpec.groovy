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

package springfox.documentation.swagger1.readers.parameter

import com.fasterxml.classmate.TypeResolver
import io.swagger.annotations.ApiParam
import org.springframework.web.bind.annotation.RequestBody
import springfox.documentation.builders.ParameterBuilder
import springfox.documentation.schema.DefaultGenericTypeNamingStrategy
import springfox.documentation.service.ResolvedMethodParameter
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spi.service.contexts.OperationContext
import springfox.documentation.spi.service.contexts.ParameterContext
import springfox.documentation.spring.web.mixins.ModelProviderForServiceSupport
import springfox.documentation.spring.web.mixins.RequestMappingSupport
import springfox.documentation.spring.web.plugins.DocumentationContextSpec

@Mixin([RequestMappingSupport, ModelProviderForServiceSupport])
class ParameterNameReaderSpec extends DocumentationContextSpec {

  def "Should support only swagger 1 documentation types"() {
    given:
      def sut = new ParameterNameReader()
    expect:
      !sut.supports(DocumentationType.SPRING_WEB)
      sut.supports(DocumentationType.SWAGGER_12)
      !sut.supports(DocumentationType.SWAGGER_2)
  }

  def "param required"() {
    given:
      def operationContext = Mock(OperationContext)
      def resolvedMethodParameter =
          new ResolvedMethodParameter(0, defaultName, [apiParam, requestBody], new TypeResolver().resolve(Object.class))
      def genericNamingStrategy = new DefaultGenericTypeNamingStrategy()
    and: "mocks are setup"
      operationContext.consumes() >> []
    and: "documentationContext is setup"
      ParameterContext parameterContext = new ParameterContext(resolvedMethodParameter, new ParameterBuilder(),
          documentationContext(), genericNamingStrategy, operationContext)
    when:
      def sut = nameReader(apiParam)
      sut.apply(parameterContext)
    then:
      parameterContext.parameterBuilder().build().name == expectedName
    where:
      defaultName            | apiParam                                                            | requestBody         | paramType | expectedName
      null                   | [name: { -> "bodyParam" }, value: { -> "body Param"}] as ApiParam   | null                | "query"   | "bodyParam"
      "methodParameterName"  | [name: { -> "bodyParam" }, value: { -> "body Param"}] as ApiParam   | []  as RequestBody  | "body"    | "body"
      null                   | null                                                                | []  as RequestBody  | "body"    | "body"
      "methodParameterName"  | null                                                                | []  as RequestBody  | "body"    | "body"
      "methodParameterName"  | null                                                                | null                | "query"   | "methodParameterName"
  }

  def nameReader(annotation) {
    new ParameterNameReader() {
    }
  }
}
