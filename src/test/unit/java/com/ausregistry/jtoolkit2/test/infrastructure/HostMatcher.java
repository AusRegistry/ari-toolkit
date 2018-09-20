package com.ausregistry.jtoolkit2.test.infrastructure;

import static com.ausregistry.jtoolkit2.test.infrastructure.InetAddressMatcher.matchIpAddress;

import java.util.Arrays;

import com.ausregistry.jtoolkit2.se.Host;
import org.hamcrest.CustomTypeSafeMatcher;

public class HostMatcher extends CustomTypeSafeMatcher<Host> {

    private final Host expectedHost;

    public HostMatcher(Host expectedHost) {
        super(String.format("a host object named [%s] with ip addresses %s", expectedHost.getName(),
                Arrays.toString(expectedHost.getAddresses())));
        this.expectedHost = expectedHost;
    }

    public static HostMatcher matchHost(Host expectedHost) {
        return new HostMatcher(expectedHost);
    }

    @Override
    protected boolean matchesSafely(Host item) {
        if (!expectedHost.getName().equals(item.getName())) {
            return false;
        } else if (expectedHost.getAddresses().length != item.getAddresses().length) {
            return false;
        } else {
            for (int i = 0; i < expectedHost.getAddresses().length; i++) {
                if (!matchIpAddress(expectedHost.getAddresses()[i]).matchesSafely(item.getAddresses()[i])) {
                    return false;
                }
            }
        }
        return true;
    }
}
