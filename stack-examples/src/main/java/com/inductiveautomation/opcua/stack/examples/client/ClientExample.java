package com.inductiveautomation.opcua.stack.examples.client;

import java.security.KeyPair;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicLong;

import com.inductiveautomation.opcua.stack.client.UaTcpClient;
import com.inductiveautomation.opcua.stack.client.UaTcpClientBuilder;
import com.inductiveautomation.opcua.stack.core.application.UaClient;
import com.inductiveautomation.opcua.stack.core.types.builtin.DateTime;
import com.inductiveautomation.opcua.stack.core.types.builtin.LocalizedText;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;
import com.inductiveautomation.opcua.stack.core.types.builtin.Variant;
import com.inductiveautomation.opcua.stack.core.types.structured.EndpointDescription;
import com.inductiveautomation.opcua.stack.core.types.structured.RequestHeader;
import com.inductiveautomation.opcua.stack.core.types.structured.TestStackRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.TestStackResponse;

import static com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.Unsigned.uint;

public class ClientExample {

    private final AtomicLong requestHandle = new AtomicLong(1L);

    private final UaTcpClient client;

    public ClientExample(X509Certificate certificate, KeyPair keyPair) throws Exception {
        // Query endpoints and select highest security level.
        EndpointDescription[] endpoints = UaTcpClient.getEndpoints("opc.tcp://localhost:12685/example").get();

        EndpointDescription endpoint = Arrays.stream(endpoints)
                .sorted((e1, e2) -> e2.getSecurityLevel().intValue() - e1.getSecurityLevel().intValue())
                .findFirst()
                .orElseThrow(() -> new Exception("no endpoints returned"));

        client = new UaTcpClientBuilder()
                .setApplicationName(LocalizedText.english("Stack Example Client"))
                .setApplicationUri(String.format("urn:example-client:%s", UUID.randomUUID()))
                .setCertificate(certificate)
                .setKeyPair(keyPair)
                .build(endpoint);
    }

    public CompletableFuture<TestStackResponse> testStack(int input) {
        RequestHeader header = new RequestHeader(
                NodeId.NULL_VALUE,
                DateTime.now(),
                uint(requestHandle.getAndIncrement()),
                uint(0), null, uint(60), null
        );

        TestStackRequest request = new TestStackRequest(header, uint(0), 1, new Variant(input));

        return client.sendRequest(request);
    }

    public CompletableFuture<UaClient> disconnect() {
        return client.disconnect();
    }

}
