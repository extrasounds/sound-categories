package dev.stashy.soundcategories.mc1_14;

import me.lonefelidae16.groominglib.api.AbstractVersionedMixinPlugin;

public final class VersionedMixinPlugin extends AbstractVersionedMixinPlugin {
    @Override
    protected String earlierVersion() {
        return "1.14";
    }

    @Override
    protected String laterVersion() {
        return "1.14.4";
    }
}
