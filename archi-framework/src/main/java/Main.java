import provider.Prototype;
import provider.Singleton;
import scope.DummyScope;
import toto.Blob;
import toto.Blob1;
import toto.Blob2;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by val on 08/12/17.
 */
public class Main {
    public static void main(final String[] args) {
        MyFramework framework = new MyFramework();
        testSingleton(framework);

    }

    private static void testPrototype(MyFramework framework) {
        try {
            framework.addProvider(new Prototype(Blob.class, Blob1.class, null));
        } catch (NoSuchMethodException|IllegalAccessException|InvocationTargetException|InstantiationException e) {
            e.printStackTrace();
        }

        Blob blob = (Blob) framework.getProviderInstance(Blob.class);

        System.out.println(blob.getBlob());

        framework.enterScope(new DummyScope());

        try {
            framework.addProvider(new Prototype(Blob.class, Blob2.class, "Je suis Blob2"));
        } catch (NoSuchMethodException|IllegalAccessException|InvocationTargetException|InstantiationException e) {
            e.printStackTrace();
        }

        blob = (Blob) framework.getProviderInstance(Blob.class);

        System.out.println(blob.getBlob());

        framework.leaveScope();

        blob = (Blob) framework.getProviderInstance(Blob.class);

        System.out.println(blob.getBlob());
    }

    private static void testSingleton(MyFramework framework) {
        framework.addProvider(new Singleton(Blob.class, new Blob1()));

        Blob blob = (Blob) framework.getProviderInstance(Blob.class);

        System.out.println(blob.getBlob());

        framework.enterScope(new DummyScope());

        framework.addProvider(new Singleton(Blob.class, new Blob2("Je suis Blob2")));

        blob = (Blob) framework.getProviderInstance(Blob.class);

        System.out.println(blob.getBlob());

        framework.leaveScope();

        blob = (Blob) framework.getProviderInstance(Blob.class);

        System.out.println(blob.getBlob());
    }
}
