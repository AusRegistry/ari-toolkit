package com.ausregistry.jtoolkit2.test.infrastructure;

import com.ausregistry.jtoolkit2.se.InetAddress;
import org.hamcrest.CustomTypeSafeMatcher;

public class InetAddressMatcher extends CustomTypeSafeMatcher<InetAddress> {

    private final InetAddress expectedIpAddress;

    public InetAddressMatcher(InetAddress expectedIpAddress) {
        super(String.format("a type %s IP address [%s]", expectedIpAddress.getVersion(),
                expectedIpAddress.getTextRep()));
        this.expectedIpAddress = expectedIpAddress;
    }

    public static InetAddressMatcher matchIpAddress(InetAddress expectedIpAddress) {
        return new InetAddressMatcher(expectedIpAddress);
    }

    @Override
    protected boolean matchesSafely(InetAddress item) {
        return expectedIpAddress.getVersion().equals(item.getVersion())
                && expectedIpAddress.getTextRep().equals(item.getTextRep());
    }
}
