package net.byte2data.linxa;

import com.esotericsoftware.kryo.io.Input;

import java.io.IOException;
import java.io.InputStream;

public interface ObjectInputStreamProvider {

    public Input getObjectInputStream(InputStream in) throws IOException;

}
