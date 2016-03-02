package org.apache.tika.parser.zerosize;

import java.io.IOException;
import java.io.InputStream;

import org.apache.tika.detect.Detector;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;

public class ZeroSizeDetector implements Detector{

	public MediaType detect(InputStream input, Metadata metadata)
			throws IOException 
	{
		// Check if we have access to the document
        if (input == null) {
            return MediaType.OCTET_STREAM;
        }
        else
        {
        	if(input.available() == 0)
        		return MediaType.ZERO_SIZE;
        	else
        		return MediaType.OCTET_STREAM;
        }
	}

}
