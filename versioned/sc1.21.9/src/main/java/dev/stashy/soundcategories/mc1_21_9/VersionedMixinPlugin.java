package dev.stashy.soundcategories.mc1_21_9;

import me.lonefelidae16.groominglib.api.AbstractVersionedMixinPlugin;

public final class VersionedMixinPlugin extends AbstractVersionedMixinPlugin {
    @Override
    protected String earlierVersion() {
        return "1.21.9";
    }

    @Override
    protected String laterVersion() {
        return "1.21.10";
    }
}
