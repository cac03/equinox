# Equinox view

This project _bridges_ [Kotlin DSL for HTML](https://github.com/Kotlin/kotlinx.html) and [Spring Framework](https://spring.io/projects/spring-framework)

Currently it allows to define an html template as a kotlin function:

```kotlin
fun renderHelloWorld(
        create: TagConsumer<*>,
) = create.html {
    head {
        title = "Hello"
    }
    body {
        p { + "Hello world" }
    }
}
```

To render the template return a reference to the template function from `@Controller`:

```kotlin
@Controller
class HelloController {
    @RequestMapping("/")
    fun hello(): KFunction<*> {
        return ::renderHelloWorld
    }
}
```

Template functions may declare a `@ModelAttribute` parameter to be resolved against prepared model.
Note that `@ModelAttribute` annotation is **mandatory**

For a comprehensive example please refer to the [sample application](https://github.com/cac03/equinox/tree/master/equinox-samples/musicservice)

## This project is highly experimential

There is a lot of work to be done:

* i18n support
* fragments
* interception
* form binding

## Contributing

Since this project in its early stage the best way to contribute is to provide feedback.

Feel free to open an issue or ping me on [twitter](https://twitter.com/2caco3)

