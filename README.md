# Sound Categories `v2`

Fabric library that allows mods to add more sound categories, and modifies the Minecraft sound settings menu to fit as
many categories as required.

Originally created by the great Stashymane, which has sadly been discontinued.

## Adding to your project

The build artifact is hosted on [my personal Maven server](https://artifactory.kow08absty.com/ui/repos/tree/General/fabricmc/dev/stashy/sound-categories). Add the following to your
repositories block:

```groovy
maven {
    name = "lonefelidae16's repo"
    url = "https://artifactory.kow08absty.com/artifactory/fabricmc"
}
```

And the following to your dependencies:

```groovy
modImplementation include("dev.stashy:sound-categories:${project.soundcategories_version}")
```

Then add the mod version to your gradle.properties. Make sure to `include()` to embed the library into your mod, as otherwise the user will have to download
it separately.

## Adding a category

Classes that contain sound categories must implement `CategoryLoader` so that they are picked up by the loader. Every
SoundCategory you define must have the `Register` annotation - they will have the category reference injected once it is
created.

Example class:

```java
import dev.stashy.soundcategories.CategoryLoader;
import net.minecraft.sound.SoundCategory;

public class CustomCategories implements CategoryLoader
{
    @Register(master = true)
    public static SoundCategory MOD_MAIN;
    @Register
    public static SoundCategory SUBCATEGORY;
    @Register(name = "your_custom_name")
    public static SoundCategory CUSTOM_NAME;
    @Register(defaultLevel = 0f)
    public static SoundCategory OFF_BY_DEFAULT;
    @Register(toggle = true)
    public static SoundCategory TOGGLEABLE;
    @Register(tooltip = "tooltip.soundCategories.customCategories.showTooltipOnMouseHover")
    public static SoundCategory SHOW_TOOLTIP_ON_MOUSE_HOVER;
}
```

The register annotation has a few attributes:

* `master` will group all categories defined underneath it under one, accessible with a button next to the master
  category's slider.
* `name` allows you to set a custom name the category will be created under.
* `defaultLevel`, quite obviously, is used as the default level the game creates your category with.
* `toggle` allows you to replace Slider widget with Button widget - useful if you want
  to limit simply on (100%) or off (0%).
* `tooltip` displays the specified text on mouse hover, the String is interpreted as a translation key and
  holds the result of the translation by `net.minecraft.text.Text#translatable()` method or equivalent functionality.
  Make sure your language JSON files contain its key:value pair as follows:

`assets/modid/lang/en_us.json`
```json
{
  "tooltip.soundCategories.customCategories.showTooltipOnMouseHover": "It's tooltip test!"
}
```

After implementing the loader, make sure you add the class as an entrypoint for `sound-categories` in
your `fabric.mod.json`.

```json
{
  "entrypoints": {
    "sound-categories": [ "com.package.your.mod.CustomCategories" ]
  }
}
```

## Localization

The newly added category needs language definitions to show any name, apart from its key. You can do so in your `lang`
folder. For an example, you can check the
[ExtraSounds](https://github.com/extrasounds/extra-sounds/) language files.
