package com.inductiveautomation.opcua.stack.core.application;

import java.security.cert.X509Certificate;
import java.util.List;
import java.util.concurrent.ExecutorService;

import com.inductiveautomation.opcua.stack.core.application.services.AttributeServiceSet;
import com.inductiveautomation.opcua.stack.core.application.services.DiscoveryServiceSet;
import com.inductiveautomation.opcua.stack.core.application.services.MethodServiceSet;
import com.inductiveautomation.opcua.stack.core.application.services.MonitoredItemServiceSet;
import com.inductiveautomation.opcua.stack.core.application.services.NodeManagementServiceSet;
import com.inductiveautomation.opcua.stack.core.application.services.QueryServiceSet;
import com.inductiveautomation.opcua.stack.core.application.services.ServiceRequestHandler;
import com.inductiveautomation.opcua.stack.core.application.services.SessionServiceSet;
import com.inductiveautomation.opcua.stack.core.application.services.SubscriptionServiceSet;
import com.inductiveautomation.opcua.stack.core.application.services.TestServiceSet;
import com.inductiveautomation.opcua.stack.core.application.services.ViewServiceSet;
import com.inductiveautomation.opcua.stack.core.channel.ChannelConfig;
import com.inductiveautomation.opcua.stack.core.channel.ServerSecureChannel;
import com.inductiveautomation.opcua.stack.core.security.SecurityPolicy;
import com.inductiveautomation.opcua.stack.core.serialization.UaRequestMessage;
import com.inductiveautomation.opcua.stack.core.serialization.UaResponseMessage;
import com.inductiveautomation.opcua.stack.core.types.enumerated.MessageSecurityMode;
import com.inductiveautomation.opcua.stack.core.types.structured.ActivateSessionRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.AddNodesRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.AddReferencesRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.ApplicationDescription;
import com.inductiveautomation.opcua.stack.core.types.structured.BrowseNextRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.BrowseRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.CallRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.CancelRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.CloseSessionRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.CreateMonitoredItemsRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.CreateSessionRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.CreateSubscriptionRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.DeleteMonitoredItemsRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.DeleteNodesRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.DeleteReferencesRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.DeleteSubscriptionsRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.EndpointDescription;
import com.inductiveautomation.opcua.stack.core.types.structured.FindServersRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.GetEndpointsRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.HistoryReadRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.HistoryUpdateRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.ModifyMonitoredItemsRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.ModifySubscriptionRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.PublishRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.QueryFirstRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.QueryNextRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.ReadRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.RegisterNodesRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.RegisterServerRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.RepublishRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.SetMonitoringModeRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.SetPublishingModeRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.SetTriggeringRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.SignedSoftwareCertificate;
import com.inductiveautomation.opcua.stack.core.types.structured.TestStackExRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.TestStackRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.TransferSubscriptionsRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.TranslateBrowsePathsToNodeIdsRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.UnregisterNodesRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.UserTokenPolicy;
import com.inductiveautomation.opcua.stack.core.types.structured.WriteRequest;

public interface UaServer {

    void startup();

    void shutdown();

    CertificateManager getCertificateManager();

    ChannelConfig getChannelConfig();

    ExecutorService getExecutorService();

    EndpointDescription[] getEndpointDescriptions();

    List<UserTokenPolicy> getUserTokenPolicies();

    ApplicationDescription getApplicationDescription();

    SignedSoftwareCertificate[] getSoftwareCertificates();

    ServerSecureChannel openSecureChannel();

    void closeSecureChannel(ServerSecureChannel secureChannel);

    <T extends UaRequestMessage, U extends UaResponseMessage>
    void addRequestHandler(Class<T> requestClass, ServiceRequestHandler<T, U> requestHandler);

    /**
     * Add an endpoint with the given security configuration. A certificate must be provided for secure endpoints.
     *
     * @param endpointUri     the endpoint URL.
     * @param bindAddress     the address to bind to.
     * @param certificate     the {@link X509Certificate} for this endpoint.
     * @param securityPolicy  the {@link SecurityPolicy} for this endpoint.
     * @param messageSecurity the {@link MessageSecurityMode} for this endpoint.
     * @return
     */
    UaServer addEndpoint(String endpointUri,
                         String bindAddress,
                         X509Certificate certificate,
                         SecurityPolicy securityPolicy,
                         MessageSecurityMode messageSecurity);

    /**
     * Add an endpoint with no certificate or security.
     *
     * @param endpointUri the endpoint URL.
     * @param bindAddress the address to bind to.
     * @return
     */
    default UaServer addEndpoint(String endpointUri, String bindAddress) {
        return addEndpoint(
                endpointUri, bindAddress, null,
                SecurityPolicy.None, MessageSecurityMode.None);
    }

    default void addServiceSet(AttributeServiceSet serviceSet) {
        addRequestHandler(ReadRequest.class, serviceSet::onRead);
        addRequestHandler(WriteRequest.class, serviceSet::onWrite);
        addRequestHandler(HistoryReadRequest.class, serviceSet::onHistoryRead);
        addRequestHandler(HistoryUpdateRequest.class, serviceSet::onHistoryUpdate);
    }

    default void addServiceSet(DiscoveryServiceSet serviceSet) {
        addRequestHandler(GetEndpointsRequest.class, serviceSet::onGetEndpoints);
        addRequestHandler(FindServersRequest.class, serviceSet::onFindServers);
        addRequestHandler(RegisterServerRequest.class, serviceSet::onRegisterServer);
    }

    default void addServiceSet(QueryServiceSet serviceSet) {
        addRequestHandler(QueryFirstRequest.class, serviceSet::onQueryFirst);
        addRequestHandler(QueryNextRequest.class, serviceSet::onQueryNext);
    }

    default void addServiceSet(MethodServiceSet serviceSet) {
        addRequestHandler(CallRequest.class, serviceSet::onCall);
    }

    default void addServiceSet(MonitoredItemServiceSet serviceSet) {
        addRequestHandler(CreateMonitoredItemsRequest.class, serviceSet::onCreateMonitoredItems);
        addRequestHandler(ModifyMonitoredItemsRequest.class, serviceSet::onModifyMonitoredItems);
        addRequestHandler(DeleteMonitoredItemsRequest.class, serviceSet::onDeleteMonitoredItems);
        addRequestHandler(SetMonitoringModeRequest.class, serviceSet::onSetMonitoringMode);
        addRequestHandler(SetTriggeringRequest.class, serviceSet::onSetTriggering);
    }

    default void addServiceSet(NodeManagementServiceSet serviceSet) {
        addRequestHandler(AddNodesRequest.class, serviceSet::onAddNodes);
        addRequestHandler(DeleteNodesRequest.class, serviceSet::onDeleteNodes);
        addRequestHandler(AddReferencesRequest.class, serviceSet::onAddReferences);
        addRequestHandler(DeleteReferencesRequest.class, serviceSet::onDeleteReferences);
    }

    default void addServiceSet(SessionServiceSet serviceSet) {
        addRequestHandler(CreateSessionRequest.class, serviceSet::onCreateSession);
        addRequestHandler(ActivateSessionRequest.class, serviceSet::onActivateSession);
        addRequestHandler(CloseSessionRequest.class, serviceSet::onCloseSession);
        addRequestHandler(CancelRequest.class, serviceSet::onCancel);
    }

    default void addServiceSet(SubscriptionServiceSet serviceSet) {
        addRequestHandler(CreateSubscriptionRequest.class, serviceSet::onCreateSubscription);
        addRequestHandler(ModifySubscriptionRequest.class, serviceSet::onModifySubscription);
        addRequestHandler(DeleteSubscriptionsRequest.class, serviceSet::onDeleteSubscriptions);
        addRequestHandler(TransferSubscriptionsRequest.class, serviceSet::onTransferSubscriptions);
        addRequestHandler(SetPublishingModeRequest.class, serviceSet::onSetPublishingMode);
        addRequestHandler(PublishRequest.class, serviceSet::onPublish);
        addRequestHandler(RepublishRequest.class, serviceSet::onRepublish);
    }

    default void addServiceSet(TestServiceSet serviceSet) {
        addRequestHandler(TestStackRequest.class, serviceSet::onTestStack);
        addRequestHandler(TestStackExRequest.class, serviceSet::onTestStackEx);
    }

    default void addServiceSet(ViewServiceSet serviceSet) {
        addRequestHandler(BrowseRequest.class, serviceSet::onBrowse);
        addRequestHandler(BrowseNextRequest.class, serviceSet::onBrowseNext);
        addRequestHandler(TranslateBrowsePathsToNodeIdsRequest.class, serviceSet::onTranslateBrowsePaths);
        addRequestHandler(RegisterNodesRequest.class, serviceSet::onRegisterNodes);
        addRequestHandler(UnregisterNodesRequest.class, serviceSet::onUnregisterNodes);
    }

}
