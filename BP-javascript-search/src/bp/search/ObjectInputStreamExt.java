package bp.search;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;
import java.lang.reflect.Proxy;

public class ObjectInputStreamExt extends ObjectInputStream {

	private ClassLoader classloader;

	public ObjectInputStreamExt(InputStream in, ClassLoader loader)
			throws IOException {
		super(in);
		this.classloader = loader;
	}

	protected Class resolveClass(ObjectStreamClass classDesc)
			throws IOException, ClassNotFoundException {

		return Class.forName(classDesc.getName(), true, classloader);
	}

	protected Class resolveProxyClass(String[] interfaces) throws IOException,
			ClassNotFoundException {
		Class[] cinterfaces = new Class[interfaces.length];
		for (int i = 0; i < interfaces.length; i++) {
			cinterfaces[i] = Class.forName(interfaces[i], true, classloader);
		}

		try {
			return Proxy.getProxyClass(classloader, cinterfaces);
		} catch (IllegalArgumentException e) {
			throw new ClassNotFoundException(null, e);
		}
 	}
}
