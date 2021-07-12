package io.ace.nordclient.mixin.accessor;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Timer;

public interface IMinecraft {

    Timer getTimer();

    void setRightClickDelayTimer(int delay);

    ResourceLocation setLOCATION_MOJANG_PNG(ResourceLocation logo);

}
