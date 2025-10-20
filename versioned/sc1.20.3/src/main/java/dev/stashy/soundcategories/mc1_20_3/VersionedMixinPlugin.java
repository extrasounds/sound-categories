package dev.stashy.soundcategories.mc1_20_3;

import me.lonefelidae16.groominglib.api.AbstractVersionedMixinPlugin;

public final class VersionedMixinPlugin extends AbstractVersionedMixinPlugin {
    @Override
    protected String earlierVersion() {
        return "1.20.3";
    }

    @Override
    protected String laterVersion() {
        return "1.20.6";
    }
}
