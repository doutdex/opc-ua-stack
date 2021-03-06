package com.inductiveautomation.opcua.stack.core.types.structured;

import com.inductiveautomation.opcua.stack.core.Identifiers;
import com.inductiveautomation.opcua.stack.core.serialization.DelegateRegistry;
import com.inductiveautomation.opcua.stack.core.serialization.UaDecoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaEncoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaStructure;
import com.inductiveautomation.opcua.stack.core.types.UaDataType;
import com.inductiveautomation.opcua.stack.core.types.builtin.DataValue;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;

@UaDataType("HistoryData")
public class HistoryData implements UaStructure {

    public static final NodeId TypeId = Identifiers.HistoryData;
    public static final NodeId BinaryEncodingId = Identifiers.HistoryData_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.HistoryData_Encoding_DefaultXml;

    protected final DataValue[] _dataValues;

    public HistoryData() {
        this._dataValues = null;
    }

    public HistoryData(DataValue[] _dataValues) {
        this._dataValues = _dataValues;
    }

    public DataValue[] getDataValues() {
        return _dataValues;
    }

    @Override
    public NodeId getTypeId() {
        return TypeId;
    }

    @Override
    public NodeId getBinaryEncodingId() {
        return BinaryEncodingId;
    }

    @Override
    public NodeId getXmlEncodingId() {
        return XmlEncodingId;
    }


    public static void encode(HistoryData historyData, UaEncoder encoder) {
        encoder.encodeArray("DataValues", historyData._dataValues, encoder::encodeDataValue);
    }

    public static HistoryData decode(UaDecoder decoder) {
        DataValue[] _dataValues = decoder.decodeArray("DataValues", decoder::decodeDataValue, DataValue.class);

        return new HistoryData(_dataValues);
    }

    static {
        DelegateRegistry.registerEncoder(HistoryData::encode, HistoryData.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(HistoryData::decode, HistoryData.class, BinaryEncodingId, XmlEncodingId);
    }

}
