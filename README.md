This is the original `com.eaio.uuid`, and you can now create pull requests for features that you've implemented yourself. :)

# UUID
## Generate UUIDs (or GUIDs) in Java

This is an implementation of the UUIDs and GUIDs specification in Java. UUIDs are 128 bit long identifiers that are guaranteed to be unique.

## Download

[Download UUID 3.5 (JAR)](https://repo.eaio.com/maven2/com/eaio/uuid/uuid/3.5/uuid-3.5.jar)

[Download UUID 3.5 (Source JAR)](https://repo.eaio.com/maven2/com/eaio/uuid/uuid/3.5/uuid-3.5-sources.jar)

Or get UUID through Maven:

```XML
    <dependencies>
        <dependency>
            <groupId>com.eaio.uuid</groupId>
            <artifactId>uuid</artifactId>
            <version>3.5</version>
        </dependency>
    </dependencies>
…
    <repositories>
        <repository>
            <id>eaio.com</id>
            <url>https://repo.eaio.com/maven2</url>
        </repository>
    </repositories>
```

## Documentation

A UUID (or GUID) is a unique identifier that can be created whithout a central authority. UUIDs can be used if a sequence number is not good enough. This implementation is thread safe and very fast.

UUID generates version 1 UUIDs that contain the the MAC address of a network card.

UUID is licensed under the MIT License (OSI certified).

## Example code

UUID is very easy to use.

```Java
import com.eaio.uuid.UUID;

public class UUIDTest {
 public static void main(String[] args) {
  UUID u = new UUID();
  System.out.println(u);
 }
}
```

You can assemble UUIDs yourself:

```Java
UUID u = new UUID(4242L, 4242L);
```

Of course, comparing and equality testing is fully implemented:

```Java
UUID u1 = new UUID(4242L, 0L);
UUID u2 = new UUID(5203L, 9412);
System.out.println(u1.equals(u2)); // prints out "false"
System.out.println(u1.compareTo(u2)); // prints out "-1"
```

As well as cloning:

```Java
UUID u1 = new UUID(4242L, 0L);
UUID u2 = (UUID) u1.clone();
UUID u3 = new UUID(u1); // or use the clone constructor
```

And parsing from Strings:

```Java
UUID u = new UUID("00000000-0000-002a-0000-00000000002a");
```

Keep in mind that this UUID implementation has public field, so cloning should be used for security purposes between in-VM system boundaries.

## Other resources

* [UUID on johannburkard.de](https://johannburkard.de/software/uuid/)
* [Maven-generated Site](https://johannburkard.de/software/uuid/site/)
* [APIdoc](https://johannburkard.de/software/uuid/site/apidocs/)

[![Analytics](https://ga-beacon.appspot.com/UA-7427410-89/UUID/README.md?pixel)](https://github.com/igrigorik/ga-beacon)