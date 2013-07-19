package com.ausregistry.jtoolkit2.se.tmch;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.ausregistry.jtoolkit2.xml.ParsingException;
import com.ausregistry.jtoolkit2.xml.XMLDocument;
import com.ausregistry.jtoolkit2.xml.XMLParser;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;

/**
 * This defines the operations to facilitate encoding / decoding signed mark data for tmch.
 */
public class TmchXMLUtil {

    public static final String ENCODED_PART_BEGIN_SIGN = "-----BEGIN ENCODED SMD-----\n";
    public static final String ENCODED_PART_END_SIGN = "-----END ENCODED SMD-----";
    public static final int BUFFER_SIZE = 1024;

    private static XMLParser parser = new XMLParser();

    /**
     * Extracts the base64 encoded part from the given SMD file input stream,
     * part that is between delimiters "-----BEGIN ENCODED SMD-----\n" and "-----END ENCODED SMD-----"
     * @param smdFileInputStream Input stream to the SMD file to parse
     * @return The extracted base64 encoded part
     * @throws IOException In case the input stream cannot be read
     */
    public static byte[] extractBase64EncodedPartFromSmdFile(final InputStream smdFileInputStream) throws IOException {
        String smdFile = loadInputStreamIntoString(smdFileInputStream);
        smdFile.replaceAll("\\r\\n", "\n");
        return smdFile.substring(smdFile.indexOf(ENCODED_PART_BEGIN_SIGN) + ENCODED_PART_BEGIN_SIGN.length(),
                smdFile.indexOf(ENCODED_PART_END_SIGN)).getBytes();
    }

    /**
     * Decodes the provided base64-encoded input stream into a decoded XML SMD data
     * @param encodedSignedMarkData Input stream to the base64-encoded data to decode
     * @return The decoded XML SMD data
     * @throws IOException In case the input stream cannot be read
     * @throws DecoderException In case the stream cannot be decoded
     */
    public static byte[] decodeSignedMarkData(final InputStream encodedSignedMarkData) throws DecoderException,
            IOException {
        return Base64.decodeBase64(loadInputStreamIntoString(encodedSignedMarkData));
    }

    /**
     * Decodes and parses the provided dbase64-encoded input stream and loads it into a SignedMarkData bean
     * @param decodedSignedMarkData Input stream to the base64-encoded input stream data to decode and parse
     * @return The loaded SignedMarkData bean
     * @throws IOException In case the input stream cannot be read
     * @throws ParsingException In case an error occurs while parsing
     * @throws DecoderException In case the stream cannot be decoded
     */
    public static SignedMarkData parseEncodedSignedMarkData(final InputStream decodedSignedMarkData) throws
            IOException, ParsingException, DecoderException {
        return parseDecodedSignedMarkData(new ByteArrayInputStream(decodeSignedMarkData(decodedSignedMarkData)));
    }

    /**
     * Parses the provided decoded XML SMD data and loads it into a SignedMarkData bean
     * @param decodedSignedMarkData Input stream to the decoded XML SMD data to parse
     * @return The loaded SignedMarkData bean
     * @throws IOException In case the input stream cannot be read
     * @throws ParsingException In case an error occurs while parsing
     */
    public static SignedMarkData parseDecodedSignedMarkData(final InputStream decodedSignedMarkData) throws
            IOException, ParsingException {
        XMLDocument xmlDocument = parser.parse(loadInputStreamIntoString(decodedSignedMarkData));
        SignedMarkData signedMarkData = new SignedMarkData();
        signedMarkData.fromXML(xmlDocument);
        return signedMarkData;
    }

    private static String loadInputStreamIntoString(InputStream inputStream) throws IOException {
        final char[] buffer = new char[BUFFER_SIZE];
        final StringBuilder out = new StringBuilder();
        final InputStreamReader in = new InputStreamReader(inputStream);
        try {
            int rsz;
            while ((rsz = in.read(buffer, 0, buffer.length)) > 0) {
                out.append(buffer, 0, rsz);
            }
        } finally {
            in.close();
        }
        return out.toString().trim();
    }
}
