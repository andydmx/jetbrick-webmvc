/**
 * Copyright 2013-2014 Guoqiang Chen, Shanghai, China. All rights reserved.
 *
 *   Author: Guoqiang Chen
 *    Email: subchen@gmail.com
 *   WebURL: https://github.com/subchen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package jetbrick.web.mvc.action.annotation;

import javax.xml.bind.*;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import jetbrick.bean.ParameterInfo;
import jetbrick.web.mvc.RequestContext;

public final class JAXBElementRequestBodyGetter implements RequestBodyGetter<JAXBElement<?>> {

    @Override
    public JAXBElement<?> get(RequestContext ctx, ParameterInfo parameter) throws Exception {
        Class<?> declaringClass = parameter.getDeclaringExecutable().getDeclaringKlass().getType();
        Class<?> type = parameter.getRawComponentType(declaringClass, 0);
        if (type == null) {
            throw new IllegalStateException("Unable to unmarshal JAXB element, type is null");
        }

        JAXBContext jc = JAXBContext.newInstance(type);
        Unmarshaller unmarshaler = jc.createUnmarshaller();
        Source source = new StreamSource(ctx.getRequest().getInputStream());
        return unmarshaler.unmarshal(source, type);
    }
}
