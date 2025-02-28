/*
 * Copyright (c) 2017, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.carbon.identity.application.authenticator.facebook;

import mockit.Expectations;
import org.apache.commons.logging.Log;
import org.wso2.carbon.identity.central.log.mgt.utils.LoggerUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class TestUtils {

    public static void enableDebugLogs(final Log mockedLog, Class className) throws NoSuchFieldException,
            IllegalAccessException {

        new Expectations() {{
            mockedLog.isDebugEnabled();
            result = true;
        }};
        Field field = className.getDeclaredField("log");
        field.setAccessible(true);
        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
        field.set(null, mockedLog);
    }

    public static void mockLoggerUtils(LoggerUtils mockLoggerUtils) {

        new Expectations(LoggerUtils.class) {{
            mockLoggerUtils.isDiagnosticLogsEnabled();
            result = true;
        }};
        new Expectations(LoggerUtils.class) {{
            mockLoggerUtils.triggerDiagnosticLogEvent(withNotNull());
            minTimes = 0;
        }};
    }
}
