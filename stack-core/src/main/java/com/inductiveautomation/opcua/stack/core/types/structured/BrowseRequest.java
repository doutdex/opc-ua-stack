package com.inductiveautomation.opcua.stack.core.types.structured;

import com.inductiveautomation.opcua.stack.core.Identifiers;
import com.inductiveautomation.opcua.stack.core.serialization.DelegateRegistry;
import com.inductiveautomation.opcua.stack.core.serialization.UaDecoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaEncoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaRequestMessage;
import com.inductiveautomation.opcua.stack.core.types.UaDataType;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UInteger;

@UaDataType("BrowseRequest")
public class BrowseRequest implements UaRequestMessage {

    public static final NodeId TypeId = Identifiers.BrowseRequest;
    public static final NodeId BinaryEncodingId = Identifiers.BrowseRequest_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.BrowseRequest_Encoding_DefaultXml;

    protected final RequestHeader _requestHeader;
    protected final ViewDescription _view;
    protected final UInteger _requestedMaxReferencesPerNode;
    protected final BrowseDescription[] _nodesToBrowse;

    public BrowseRequest() {
        this._requestHeader = null;
        this._view = null;
        this._requestedMaxReferencesPerNode = null;
        this._nodesToBrowse = null;
    }

    public BrowseRequest(RequestHeader _requestHeader, ViewDescription _view, UInteger _requestedMaxReferencesPerNode, BrowseDescription[] _nodesToBrowse) {
        this._requestHeader = _requestHeader;
        this._view = _view;
        this._requestedMaxReferencesPerNode = _requestedMaxReferencesPerNode;
        this._nodesToBrowse = _nodesToBrowse;
    }

    public RequestHeader getRequestHeader() {
        return _requestHeader;
    }

    public ViewDescription getView() {
        return _view;
    }

    public UInteger getRequestedMaxReferencesPerNode() {
        return _requestedMaxReferencesPerNode;
    }

    public BrowseDescription[] getNodesToBrowse() {
        return _nodesToBrowse;
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


    public static void encode(BrowseRequest browseRequest, UaEncoder encoder) {
        encoder.encodeSerializable("RequestHeader", browseRequest._requestHeader != null ? browseRequest._requestHeader : new RequestHeader());
        encoder.encodeSerializable("View", browseRequest._view != null ? browseRequest._view : new ViewDescription());
        encoder.encodeUInt32("RequestedMaxReferencesPerNode", browseRequest._requestedMaxReferencesPerNode);
        encoder.encodeArray("NodesToBrowse", browseRequest._nodesToBrowse, encoder::encodeSerializable);
    }

    public static BrowseRequest decode(UaDecoder decoder) {
        RequestHeader _requestHeader = decoder.decodeSerializable("RequestHeader", RequestHeader.class);
        ViewDescription _view = decoder.decodeSerializable("View", ViewDescription.class);
        UInteger _requestedMaxReferencesPerNode = decoder.decodeUInt32("RequestedMaxReferencesPerNode");
        BrowseDescription[] _nodesToBrowse = decoder.decodeArray("NodesToBrowse", decoder::decodeSerializable, BrowseDescription.class);

        return new BrowseRequest(_requestHeader, _view, _requestedMaxReferencesPerNode, _nodesToBrowse);
    }

    static {
        DelegateRegistry.registerEncoder(BrowseRequest::encode, BrowseRequest.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(BrowseRequest::decode, BrowseRequest.class, BinaryEncodingId, XmlEncodingId);
    }

}
