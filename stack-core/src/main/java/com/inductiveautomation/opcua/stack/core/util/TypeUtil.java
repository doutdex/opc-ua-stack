package com.inductiveautomation.opcua.stack.core.util;

import java.util.UUID;

import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import com.inductiveautomation.opcua.stack.core.types.builtin.ByteString;
import com.inductiveautomation.opcua.stack.core.types.builtin.DataValue;
import com.inductiveautomation.opcua.stack.core.types.builtin.DateTime;
import com.inductiveautomation.opcua.stack.core.types.builtin.DiagnosticInfo;
import com.inductiveautomation.opcua.stack.core.types.builtin.ExpandedNodeId;
import com.inductiveautomation.opcua.stack.core.types.builtin.ExtensionObject;
import com.inductiveautomation.opcua.stack.core.types.builtin.LocalizedText;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;
import com.inductiveautomation.opcua.stack.core.types.builtin.QualifiedName;
import com.inductiveautomation.opcua.stack.core.types.builtin.StatusCode;
import com.inductiveautomation.opcua.stack.core.types.builtin.Variant;
import com.inductiveautomation.opcua.stack.core.types.builtin.XmlElement;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UByte;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.ULong;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UShort;

public class TypeUtil {

    private static final BiMap<Class<?>, Integer> PrimitiveBuiltinTypeIds =
            ImmutableBiMap.<Class<?>, Integer>builder()
                    .put(boolean.class, 1)
                    .put(byte.class, 2)
                    .put(short.class, 4)
                    .put(int.class, 6)
                    .put(long.class, 8)
                    .put(float.class, 10)
                    .put(double.class, 11)
                    .build();

    private static final BiMap<Integer, Class<?>> BackingClasses =
            ImmutableBiMap.<Integer, Class<?>>builder()
                    .put(1, Boolean.class)      // Boolean
                    .put(2, Byte.class)         // SByte
                    .put(3, UByte.class)        // UByte
                    .put(4, Short.class)        // Int16
                    .put(5, UShort.class)       // UInt16
                    .put(6, Integer.class)      // Int32
                    .put(7, UInteger.class)     // UInt32
                    .put(8, Long.class)         // Int64
                    .put(9, ULong.class)        // UInt64
                    .put(10, Float.class)
                    .put(11, Double.class)
                    .put(12, String.class)
                    .put(13, DateTime.class)
                    .put(14, UUID.class)
                    .put(15, ByteString.class)
                    .put(16, XmlElement.class)
                    .put(17, NodeId.class)
                    .put(18, ExpandedNodeId.class)
                    .put(19, StatusCode.class)
                    .put(20, QualifiedName.class)
                    .put(21, LocalizedText.class)
                    .put(22, ExtensionObject.class)
                    .put(23, DataValue.class)
                    .put(24, Variant.class)
                    .put(25, DiagnosticInfo.class)
                    .build();

    /**
     * @param backingType the backing {@link Class} of the builtin type.
     * @return the id of the builtin type backed by {@code backingType}, or -1 if backingType is not builtin.
     */
    public static int getBuiltinTypeId(Class<?> backingType) {
        if (backingType.isPrimitive()) {
            return PrimitiveBuiltinTypeIds.getOrDefault(backingType, -1);
        } else {
            return BackingClasses.inverse().getOrDefault(backingType, -1);
        }
    }

    /**
     * @param typeId the id of the builtin type.
     * @return the {@link Class} backing the builtin type.
     */
    public static Class<?> getBackingClass(int typeId) {
        return BackingClasses.get(typeId);
    }

}
