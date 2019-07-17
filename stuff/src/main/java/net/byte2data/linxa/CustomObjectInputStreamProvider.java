package net.byte2data.linxa;

import com.esotericsoftware.kryo.io.Input;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.HashMap;

public class CustomObjectInputStreamProvider implements ObjectInputStreamProvider{

    private HashMap<String, Class<?>> map = new HashMap<>();

    private class CustomObjectInputStream extends Input{



    }

    @Override
    public Input getObjectInputStream(InputStream in) throws IOException {
        return null;
    }
}
