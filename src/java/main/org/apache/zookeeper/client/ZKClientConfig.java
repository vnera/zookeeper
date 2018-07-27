/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.zookeeper.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class ZKClientConfig {

    private static final Logger LOG = LoggerFactory.getLogger(ZKClientConfig.class);

    private final Map<String, String> properties = new HashMap<String, String>();
    public static final String ZOOKEEPER_REQUEST_TIMEOUT = "zookeeper.request.timeout";
    /**
     * Feature is disabled by default. 
     */
    public static final long ZOOKEEPER_REQUEST_TIMEOUT_DEFAULT = 0;

    public ZKClientConfig() {
        initFromJavaSystemProperties();
    }

    /**
     * Initialize all the ZooKeeper client properties which are configurable as
     * java system property
     */
    private void initFromJavaSystemProperties() {
        setProperty(ZOOKEEPER_REQUEST_TIMEOUT,
                System.getProperty(ZOOKEEPER_REQUEST_TIMEOUT));
    }

    /**
     * FIXME: this should be in ZKConfig, land ZOOKEEPER-2139
     * Get the property value
     *
     * @param key
     * @return property value
     */
    public String getProperty(String key) {
        return properties.get(key);
    }

    /**
     * FIXME: this should be in ZKConfig, land ZOOKEEPER-2139
     * Get the property value, if it is null return default value
     *
     * @param key
     *            property key
     * @param defaultValue
     * @return property value or default value
     */
    public String getProperty(String key, String defaultValue) {
        String value = properties.get(key);
        return (value == null) ? defaultValue : value;
    }

    /**
     * FIXME: this should be in ZKConfig, land ZOOKEEPER-2139
     * Maps the specified <code>key</code> to the specified <code>value</code>.
     * key can not be <code>null</code>. If key is already mapped then the old
     * value of the <code>key</code> is replaced by the specified
     * <code>value</code>.
     *
     * @param key
     * @param value
     */
    public void setProperty(String key, String value) {
        if (null == key) {
            throw new IllegalArgumentException("property key is null.");
        }
        String oldValue = properties.put(key, value);
        if (LOG.isDebugEnabled()) {
            if (null != oldValue && !oldValue.equals(value)) {
                LOG.debug("key {}'s value {} is replaced with new value {}", key, oldValue, value);
            }
        }
    }

    /**
     * Get the value of the <code>key</code> property as an <code>long</code>.
     * If property is not set, the provided <code>defaultValue</code> is
     * returned
     *
     * @param key
     *            property key.
     * @param defaultValue
     *            default value.
     * @throws NumberFormatException
     *             when the value is invalid
     * @return return property value as an <code>long</code>, or
     *         <code>defaultValue</code>
     */
    public long getLong(String key, long defaultValue) {
        String value = getProperty(key);
        if (value != null) {
            return Long.parseLong(value.trim());
        }
        return defaultValue;
    }
}
