package dev.stashy.soundcategories;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The enum modifier API that defines your unique {@link net.minecraft.sound.SoundCategory}.
 */
public interface CategoryLoader {
    /**
     * Registers a new {@link net.minecraft.sound.SoundCategory} and injects its reference to the field that has this annotation.<br>
     * The field name will be prefixed your modId and {@code $}, like following:<br>
     * <ul>
     *     <li>Declared by {@code mod-id}
     *     <pre>class CustomCats implements CategoryLoader {<br>    @Register<br>    public static SoundCategory MASTER;<br>}</pre></li>
     *     <li>Generated code at runtime
     *     <pre>SoundCategory.MOD_ID$MASTER("mod_id$master");</pre></li>
     * </ul>
     * In this case, the translation key will be {@code "soundCategory.mod_id$master"}. You can access this SoundCategory using {@code CustomCats.MASTER} directly.
     *
     * @see Register#id
     * @see Register#master
     * @see Register#defaultLevel
     * @see Register#toggle
     * @see Register#tooltip
     * @see Register#preview
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @interface Register {
        /**
         * The ID of the sound category - if omitted, will be automatically set from the field name.
         */
        String id() default "";

        /**
         * Sets the SoundCategory of a field to be the master category.<br>
         * It can be declared only once in your class and grouped within the master.<br>
         * To create multiple master categories, please create separate classes.
         */
        boolean master() default false;

        /**
         * Allows changing the default volume level if one has not yet been set.
         */
        float defaultLevel() default 1.0f;

        /**
         * Sets the SoundCategory as a toggle button.
         */
        boolean toggle() default false;

        /**
         * Sets tooltip to be displayed on mouse hover.<br>
         * This value will be passed to {@link net.minecraft.text.Text#translatable}.
         */
        String tooltip() default "";

        /**
         * Sets preview sound Identifier for MC 1.21.9 or later.<br>
         * This value will be passed to {@link net.minecraft.util.Identifier#of(String)}.
         */
        String[] preview() default "";
    }
}
