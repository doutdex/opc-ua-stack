package com.inductiveautomation.opcua.stack.server;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.net.URI;
import java.security.cert.X509Certificate;
import java.util.Optional;

import com.inductiveautomation.opcua.stack.core.security.SecurityPolicy;
import com.inductiveautomation.opcua.stack.core.types.enumerated.MessageSecurityMode;

import static com.google.common.base.Preconditions.checkNotNull;

public class Endpoint {

    private final URI endpointUri;
    private final SecurityPolicy securityPolicy;
    private final MessageSecurityMode messageSecurity;
    private final X509Certificate certificate;
    private final String bindAddress;

    public Endpoint(@Nonnull URI endpointUri,
                    @Nullable String bindAddress,
                    @Nullable X509Certificate certificate,
                    @Nonnull SecurityPolicy securityPolicy,
                    @Nonnull MessageSecurityMode messageSecurity) {

        checkNotNull(endpointUri);
        checkNotNull(securityPolicy);
        checkNotNull(messageSecurity);

        this.endpointUri = endpointUri;
        this.securityPolicy = securityPolicy;
        this.messageSecurity = messageSecurity;
        this.certificate = certificate;
        this.bindAddress = bindAddress;
    }

    public URI getEndpointUri() {
        return endpointUri;
    }

    public SecurityPolicy getSecurityPolicy() {
        return securityPolicy;
    }

    public MessageSecurityMode getMessageSecurity() {
        return messageSecurity;
    }

    public Optional<X509Certificate> getCertificate() {
        return Optional.ofNullable(certificate);
    }

    public Optional<String> getBindAddress() {
        return Optional.ofNullable(bindAddress);
    }

    public short getSecurityLevel() {
        short securityLevel = 0;

        switch (messageSecurity) {
            case SignAndEncrypt:
                securityLevel |= 0x80;
                break;
            case Sign:
                securityLevel |= 0x40;
                break;
            default:
                securityLevel |= 0x20;
        }

        switch(securityPolicy) {
            case Basic256Sha256:
                securityLevel |= 0x08;
                break;
            case Basic256:
                securityLevel |= 0x04;
                break;
            case Basic128Rsa15:
                securityLevel |= 0x02;
                break;
            case None:
                securityLevel |= 0x01;
                break;
        }

        return securityLevel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Endpoint endpoint = (Endpoint) o;

        return !(bindAddress != null ? !bindAddress.equals(endpoint.bindAddress) : endpoint.bindAddress != null) &&
                !(certificate != null ? !certificate.equals(endpoint.certificate) : endpoint.certificate != null) &&
                endpointUri.equals(endpoint.endpointUri) &&
                messageSecurity == endpoint.messageSecurity &&
                securityPolicy == endpoint.securityPolicy;
    }

    @Override
    public int hashCode() {
        int result = endpointUri.hashCode();
        result = 31 * result + securityPolicy.hashCode();
        result = 31 * result + messageSecurity.hashCode();
        result = 31 * result + (certificate != null ? certificate.hashCode() : 0);
        result = 31 * result + (bindAddress != null ? bindAddress.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Endpoint{" +
                "endpointUri=" + endpointUri +
                ", securityPolicy=" + securityPolicy +
                ", messageSecurity=" + messageSecurity +
                ", certificate=" + certificate +
                ", bindAddress='" + bindAddress + '\'' +
                '}';
    }

}
