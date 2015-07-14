/*
 * Copyright 2008-2015 Aerospike, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.aerospike.cache;

import java.io.IOException;
import java.io.InputStream;

import javax.inject.Singleton;

import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;

import com.aerospike.client.Host;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

/**
 * Read configuration from config file
 *
 * @author akshay
 *
 */
@Slf4j
@Singleton
public class ConfigReader {

    /**
     * Read configuration from given file
     *
     * @param configresourcename
     * @return
     * @throws IOException
     */
    public AerospikeCacheConfig getConfiguration(String configresourcename)
            throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        log.debug("Reading cache config frm {}.", configresourcename);
        @Cleanup
        final InputStream istream = getClass().getClassLoader()
                .getResourceAsStream(configresourcename);
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Host.class, new HostDeserializer());
        mapper.registerModule(module);
        return mapper.readValue(istream, AerospikeCacheConfig.class);
    }

    /**
     * Read configuration from default file
     *
     * @return
     * @throws IOException
     */
    public AerospikeCacheConfig getConfiguration() throws IOException {
        return getConfiguration("aerospike-cache.cfg");
    }
}
