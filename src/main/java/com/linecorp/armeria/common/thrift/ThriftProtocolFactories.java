/*
 * Copyright 2015 LINE Corporation
 *
 * LINE Corporation licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package com.linecorp.armeria.common.thrift;

import static java.util.Objects.requireNonNull;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TJSONProtocol;
import org.apache.thrift.protocol.TProtocolFactory;

import com.linecorp.armeria.common.SerializationFormat;

/**
 * Provides a set of the known {@link TProtocolFactory} instances.
 */
public final class ThriftProtocolFactories {

    /**
     * {@link TProtocolFactory} for Thrift TBinary protocol
     */
    public static final TProtocolFactory BINARY = new TBinaryProtocol.Factory() {
        private static final long serialVersionUID = -9020693963961565748L;

        @Override
        public String toString() {
            return "TProtocolFactory(binary)";
        }
    };

    /**
     * {@link TProtocolFactory} for Thrift TCompact protocol
     */
    public static final TProtocolFactory COMPACT = new TCompactProtocol.Factory() {
        private static final long serialVersionUID = 1629726795326210377L;

        @Override
        public String toString() {
            return "TProtocolFactory(compact)";
        }
    };

    /**
     * {@link TProtocolFactory} for the Thrift TJSON protocol
     */
    public static final TProtocolFactory JSON = new TJSONProtocol.Factory() {
        private static final long serialVersionUID = 7690636602996870153L;

        @Override
        public String toString() {
            return "TProtocolFactory(JSON)";
        }
    };

    /**
     * Returns the {@link TProtocolFactory} for the specified {@link SerializationFormat}.
     *
     * @throws IllegalArgumentException if the specified {@link SerializationFormat} is not for Thrift
     */
    public static TProtocolFactory get(SerializationFormat serializationFormat) {
        switch (requireNonNull(serializationFormat, "serializationFormat")) {
        case THRIFT_BINARY:
            return BINARY;
        case THRIFT_COMPACT:
            return COMPACT;
        case THRIFT_JSON:
            return JSON;
        default:
            throw new IllegalArgumentException("non-Thrift serializationFormat: " + serializationFormat);
        }
    }

    /**
     * Returns the {@link SerializationFormat} for the specified {@link TProtocolFactory}.
     *
     * @throws IllegalArgumentException if the specified {@link TProtocolFactory} is not known by this class
     */
    public static SerializationFormat toSerializationFormat(TProtocolFactory protoFactory) {
        requireNonNull(protoFactory, "protoFactory");

        if (protoFactory instanceof TBinaryProtocol.Factory) {
            return SerializationFormat.THRIFT_BINARY;
        } else if (protoFactory instanceof TCompactProtocol.Factory) {
            return SerializationFormat.THRIFT_COMPACT;
        } else if (protoFactory instanceof TJSONProtocol.Factory) {
            return SerializationFormat.THRIFT_JSON;
        } else {
            throw new IllegalArgumentException(
                    "unsupported TProtocolFactory: " + protoFactory.getClass().getName());
        }
    }

    private ThriftProtocolFactories() {}
}
