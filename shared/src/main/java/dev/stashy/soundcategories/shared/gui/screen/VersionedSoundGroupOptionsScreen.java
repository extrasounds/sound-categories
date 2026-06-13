package dev.stashy.soundcategories.shared.gui.screen;

import dev.stashy.soundcategories.shared.SoundCategories;
import dev.stashy.soundcategories.shared.gui.widget.VersionedElementListWrapper;
import me.lonefelidae16.groominglib.api.McVersionInterchange;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.GameOptionsScreen;
import net.minecraft.client.option.GameOptions;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Objects;

public abstract class VersionedSoundGroupOptionsScreen extends GameOptionsScreen {
    private static final String METHOD_KEY_CTOR = VersionedSoundGroupOptionsScreen.class.getCanonicalName() + "#<init>";

    protected VersionedElementListWrapper list;

    static {
        try {
            Class<VersionedSoundGroupOptionsScreen> clazz = McVersionInterchange.getCompatibleClass(SoundCategories.BASE_PACKAGE, "gui.screen.SoundGroupOptionsScreen");
            Constructor<VersionedSoundGroupOptionsScreen> constructor = clazz.getConstructor(Screen.class, GameOptions.class, SoundCategory.class);
            SoundCategories.CACHED_INIT_MAP.put(METHOD_KEY_CTOR, Objects.requireNonNull(constructor));
        } catch (Exception ex) {
            SoundCategories.LOGGER.error("Failed to find 'SoundGroupOptionsScreen' class.", ex);
        }
    }

    public VersionedSoundGroupOptionsScreen(Screen parent, GameOptions gameOptions, Text title) {
        super(parent, gameOptions, title);
    }

    @SuppressWarnings("unchecked")
    public static VersionedSoundGroupOptionsScreen newInstance(Screen parent, GameOptions settings, SoundCategory category) {
        try {
            Constructor<VersionedSoundGroupOptionsScreen> constructor = (Constructor<VersionedSoundGroupOptionsScreen>) SoundCategories.CACHED_INIT_MAP.get(METHOD_KEY_CTOR);
            return constructor.newInstance(parent, settings, category);
        } catch (Exception ex) {
            SoundCategories.LOGGER.error("Cannot instantiate 'SoundGroupOptionsScreen'", ex);
        }
        return null;
    }

//    protected void addDoneButton() {
//        addDoneButton(false);
//    }
//
//    protected void addDoneButton(boolean withCancel) {
//        if (withCancel) {
//            this.addDrawableChild(
//                    VersionedButtonWrapper.newInstance(
//                            this.width / 2 - 155, this.height - 27, 150, 20,
//                            VersionedText.INSTANCE.getDoneText(), (button) -> {
//                                this.client.options.write();
//                                this.client.setScreen(this.parent);
//                            }
//                    )
//            );
//            this.addDrawableChild(
//                    VersionedButtonWrapper.newInstance(
//                            this.width / 2 - 155 + 160, this.height - 27, 150, 20,
//                            VersionedText.INSTANCE.getCancelText(), (button) -> this.client.setScreen(this.parent)
//                    )
//            );
//        } else {
//            this.addDrawableChild(
//                    VersionedButtonWrapper.newInstance(
//                            this.width / 2 - 100, this.height - 27, 200, 20,
//                            VersionedText.INSTANCE.getDoneText(), (button) -> {
//                                this.client.options.write();
//                                this.client.setScreen(this.parent);
//                            }
//                    )
//            );
//        }
//    }

    @Override
    protected void init() {
        this.list = VersionedElementListWrapper.newInstance(this.client, this.width, this.height, 32, this.height - 32, 25);
        super.init();
    }

    protected SoundCategory[] filterByParentCategory(SoundCategory parentCategory) {
        return Arrays.stream(SoundCategory.values()).filter(it -> {
            return SoundCategories.PARENTS.containsKey(it) && SoundCategories.PARENTS.get(it) == parentCategory;
        }).toArray(SoundCategory[]::new);
    }
}
