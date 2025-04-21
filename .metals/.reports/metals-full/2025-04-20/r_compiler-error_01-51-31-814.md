file:///C:/Users/TanmoY/Desktop/cimba/backend/src/main/java/com/backend/App.java
### java.util.NoSuchElementException: next on empty iterator

occurred in the presentation compiler.

presentation compiler configuration:


action parameters:
offset: 244
uri: file:///C:/Users/TanmoY/Desktop/cimba/backend/src/main/java/com/backend/App.java
text:
```scala
package com.backend;

import javax.swing.Spring;

import org.springframework.boot.SpringApplication;
/**
 * Hello world!
 *
 */
@SpringbootApplication
public class App 
{
    public static void main( String[] args )
    {
       A@@ context= SpringApplication.run(App.class, args);
        dev obj = context.getBean(dev.class);
        obj.build();
    }
}

```



#### Error stacktrace:

```
scala.collection.Iterator$$anon$19.next(Iterator.scala:973)
	scala.collection.Iterator$$anon$19.next(Iterator.scala:971)
	scala.collection.mutable.MutationTracker$CheckedIterator.next(MutationTracker.scala:76)
	scala.collection.IterableOps.head(Iterable.scala:222)
	scala.collection.IterableOps.head$(Iterable.scala:222)
	scala.collection.AbstractIterable.head(Iterable.scala:935)
	dotty.tools.dotc.interactive.InteractiveDriver.run(InteractiveDriver.scala:164)
	dotty.tools.pc.CachingDriver.run(CachingDriver.scala:45)
	dotty.tools.pc.completions.CompletionProvider.completions(CompletionProvider.scala:72)
	dotty.tools.pc.ScalaPresentationCompiler.complete$$anonfun$1(ScalaPresentationCompiler.scala:150)
```
#### Short summary: 

java.util.NoSuchElementException: next on empty iterator